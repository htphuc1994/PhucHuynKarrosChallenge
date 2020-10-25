package com.karros.gpx.services;

import com.karros.config.FileStorageProperties;
import com.karros.gpx.dtos.*;
import com.karros.gpx.models.*;
import com.karros.gpx.repositories.GpxRepository;
import com.karros.gpx.repositories.TrackRepository;
import io.jenetics.jpx.GPX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static com.karros.gpx.utils.GpxConstants.ApiMessage.*;

@Service
public class KarrosGpxServiceImpl implements KarrosGpxService {

    @Autowired
    private GpxRepository gpxRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private FileStorageProperties fileStorageProperties;

    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public UploadFileResponse handleUploadedGpxFile(MultipartFile gpxFile) throws IOException {
        UploadFileResponse response = new UploadFileResponse();
        String gpxFileName = fileStorageService.storeFile(gpxFile);

        Path fileGpxUploadDir = Paths.get(fileStorageProperties.getUploadDir())
                                     .toAbsolutePath().normalize();

        String gpxFilePath = fileGpxUploadDir + "/" + gpxFileName;
        final GPX gpx = GPX.read(gpxFilePath);
        Gpx karrosGpx = updateKarrosGpx(gpx);
        gpxRepository.save(karrosGpx);

        response.setFileName(gpxFileName);
        response.setFileType(gpxFile.getContentType());
        response.setSize(gpxFile.getSize());
        response.setCode(HttpStatus.OK.value());
        response.setMessage(SUCCESS.getValue());
        return response;
    }

    private Gpx updateKarrosGpx(GPX jGpx) {
        Gpx karrosJpx = new Gpx();
        if (null == jGpx) {
            return karrosJpx;
        }
        karrosJpx.setCreator(jGpx.getCreator());
        karrosJpx.setVersion(jGpx.getVersion());
        Metadata metadata = new Metadata();
        karrosJpx.setMetadata(metadata);
        if (jGpx.getMetadata().isPresent()) {
            if (jGpx.getMetadata().get().getName().isPresent()) {
                metadata.setName(jGpx.getMetadata().get().getName().get());
            }
            if (jGpx.getMetadata().get().getDescription().isPresent()) {
                metadata.setName(jGpx.getMetadata().get().getDescription().get());
            }
            if (jGpx.getMetadata().get().getTime().isPresent()) {
                metadata.setTime(jGpx.getMetadata().get().getTime().get().toString());
            }
            if (jGpx.getMetadata().get().getLinks() != null && !jGpx.getMetadata().get().getLinks().isEmpty()) {
                List<Link> links = new LinkedList<>();
                metadata.setMetadataLinks(links);
                for (io.jenetics.jpx.Link jLink : jGpx.getMetadata().get().getLinks()) {
                    Link link = new Link();
                    link.setHref(jLink.getHref().toString());
                    if (jLink.getText().isPresent()) {
                        link.setText(jLink.getText().get());
                    }
                    links.add(link);
                }
            }
        }
        List<WayPoint> wayPoints = updateWayPoints(jGpx.getWayPoints(), null, karrosJpx);
        karrosJpx.setWayPoints(wayPoints);

        List<Track> tracks = new LinkedList<>();
        for (io.jenetics.jpx.Track jTrack : jGpx.getTracks()) {
            Track track = new Track();
            if (jTrack.getName().isPresent()) {
                track.setName(jTrack.getName().get());
            }
            tracks.add(track);
            List<Segment> segments = new LinkedList<>();
            for (io.jenetics.jpx.TrackSegment jSegment : jTrack.getSegments()) {
                Segment segment = new Segment();
                wayPoints = updateWayPoints(jSegment.getPoints(), segment, karrosJpx);
                segment.setWayPoints(wayPoints);
                segment.setTrack(track);
                segments.add(segment);
            }
            track.setGpx(karrosJpx);
            track.setSegments(segments);
        }
        karrosJpx.setTracks(tracks);
        return karrosJpx;
    }

    private List<WayPoint> updateWayPoints(List<io.jenetics.jpx.WayPoint> jWayPoints, Segment segment, Gpx gpx) {
        List<WayPoint> wayPoints = new LinkedList<>();
        if (null == jWayPoints) {
            return wayPoints;
        }
        for (io.jenetics.jpx.WayPoint jWayPoint : jWayPoints) {
            WayPoint wayPoint = new WayPoint();
            wayPoint.setLatitude(jWayPoint.getLatitude().toString());
            wayPoint.setLongitude(jWayPoint.getLongitude().toString());
            if (jWayPoint.getName().isPresent()) {
                wayPoint.setName(jWayPoint.getName().get());
            }
            if (jWayPoint.getSymbol().isPresent()) {
                wayPoint.setSymbol(jWayPoint.getSymbol().get());
            }
            if (null != segment) {
                wayPoint.getSegments().add(segment);
            }
            if (null != gpx) {
                wayPoint.getGpxs().add(gpx);
            }
            wayPoints.add(wayPoint);
        }
        return wayPoints;
    }

    @Override
    public GetLatestTrackResponse getLatestTrack() throws Exception {
        GetLatestTrackResponse response = new GetLatestTrackResponse();
        Track track = trackRepository.findFirstByOrderByIdDesc();
        if (track == null) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(NOT_FOUND.getValue());
        } else {
            response.setTrack(getTractDto(track));
            response.setCode(HttpStatus.OK.value());
            response.setMessage(SUCCESS.getValue());
        }
        return response;
    }

    private TrackDto getTractDto(Track track) {
        TrackDto trackDto = new TrackDto();
        if (null == track) {
            return trackDto;
        }
        trackDto.setCreatedAt(track.getCreatedAt());
        trackDto.setDescription(track.getDescription());
        if (null != track.getGpx()) {
            Gpx gpx = track.getGpx();
            GpxDto gpxDto = new GpxDto();
            trackDto.setGpx(gpxDto);
            gpxDto.setId(gpx.getId());
            gpxDto.setCreator(gpx.getCreator());
            gpxDto.setVersion(gpx.getVersion());
        }
        trackDto.setId(track.getId());
        trackDto.setName(track.getName());
        if (null != track.getSegments()) {
            List<SegmentDto> segmentDtos = new LinkedList<>();
            trackDto.setSegments(segmentDtos);
            for (Segment segment : track.getSegments()) {
                SegmentDto segmentDto = new SegmentDto();
                segmentDto.setId(segment.getId());
                segmentDto.setCreatedAt(segment.getCreatedAt());
                segmentDto.setUpdatedAt(segment.getUpdatedAt());
                List<WayPointDto> wayPointDtos = new LinkedList<>();
                segmentDto.setWayPoints(wayPointDtos);
                if (null != segment.getWayPoints()) {
                    for (WayPoint wayPoint : segment.getWayPoints()) {
                        wayPointDtos.add(getWayPointDto(wayPoint));
                    }
                }
                segmentDtos.add(segmentDto);
            }
        }
        trackDto.setUpdatedAt(track.getUpdatedAt());
        return trackDto;
    }

    private WayPointDto getWayPointDto(WayPoint wayPoint) {
        WayPointDto wayPointDto = new WayPointDto();
        if (null == wayPoint) {
            return wayPointDto;
        }
        wayPointDto.setId(wayPoint.getId());
        wayPointDto.setLatitude(wayPoint.getLatitude());
        wayPointDto.setLongitude(wayPoint.getLongitude());
        wayPointDto.setName(wayPoint.getName());
        wayPointDto.setSymbol(wayPoint.getSymbol());
        wayPointDto.setCreatedAt(wayPoint.getCreatedAt());
        wayPointDto.setUpdatedAt(wayPoint.getUpdatedAt());
        return wayPointDto;
    }

    @Override
    public GetGpxResponse getGpxInfo(Long gpxId) throws Exception {
        GetGpxResponse response = new GetGpxResponse();
        Optional<Gpx> optionalGpx = gpxRepository.findById(gpxId);

        if (!optionalGpx.isPresent()) {
            response.setCode(HttpStatus.BAD_REQUEST.value());
            response.setMessage(NOT_FOUND.getValue());
        } else {
            response.setGpx(getGpxDto(optionalGpx.get()));
            response.setCode(HttpStatus.OK.value());
            response.setMessage(SUCCESS.getValue());
        }
        return response;
    }

    private GpxDto getGpxDto(Gpx gpx) {
        GpxDto gpxDto = new GpxDto();
        if (null == gpx) {
            return gpxDto;
        }
        gpxDto.setCreator(gpx.getCreator());
        gpxDto.setVersion(gpx.getVersion());
        gpxDto.setId(gpx.getId());
        gpxDto.setCreatedAt(gpx.getCreatedAt());
        gpxDto.setSymbol(gpx.getSymbol());
        gpxDto.setUpdatedAt(gpx.getUpdatedAt());
        if (gpx.getMetadata() != null) {
            Metadata metadata = gpx.getMetadata();
            MetadataDto metadataDto = new MetadataDto();
            gpxDto.setMetadata(metadataDto);
            metadataDto.setAuthor(metadata.getAuthor());
            metadataDto.setDescription(metadata.getDescription());
            metadataDto.setId(metadata.getId());
            metadataDto.setLongitude(metadata.getLongitude());
            metadata.setSymbol(metadata.getSymbol());
        }
        if (null != gpx.getTracks()) {
            List<TrackDto> trackDtos = new LinkedList<>();
            gpxDto.setTracks(trackDtos);
            for (Track track : gpx.getTracks()) {
                trackDtos.add(getTractDto(track));
            }
        }
        if (null != gpx.getWayPoints()) {
            List<WayPointDto> wayPointDtos = new LinkedList<>();
            gpxDto.setWayPoints(wayPointDtos);
            for (WayPoint wayPoint : gpx.getWayPoints()) {
                wayPointDtos.add(getWayPointDto(wayPoint));
            }
        }

        return gpxDto;
    }

}

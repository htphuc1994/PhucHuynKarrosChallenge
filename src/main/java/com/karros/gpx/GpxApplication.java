package com.karros.gpx;

import com.karros.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication

@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class GpxApplication {

    public static void main(String[] args) {
        SpringApplication.run(GpxApplication.class, args);
    }

}

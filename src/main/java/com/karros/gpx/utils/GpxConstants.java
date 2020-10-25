package com.karros.gpx.utils;

public final class GpxConstants {

    public enum ApiMessage {
        SUCCESS("Success"),
        NOT_FOUND("Not Found"),
        FAILED("Failed");

        final String value;

        ApiMessage(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}

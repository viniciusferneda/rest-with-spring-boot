package com.vferneda.restwithspringboot.exception;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ExceptionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private final LocalDateTime dateTime;
    private final String message;
    private final String detailMessage;

    public ExceptionResponse(LocalDateTime dateTime, String message, String detailMessage) {
        this.dateTime = dateTime;
        this.message = message;
        this.detailMessage = detailMessage;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getMessage() {
        return message;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}

package com.topicals.topicalsapi.exceptions.helper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class ExceptionDetails {

    private HttpStatus title;
    private int status;
    private List<String> details;
    private String timestamp;

    public ExceptionDetails(HttpStatus title, int status, List<String> details, String timestamp) {
        super();
        this.title = title;
        this.status = status;
        this.details = details;
        this.timestamp = timestamp;
    }

    public ExceptionDetails(HttpStatus title, int status, String detail, String timestamp) {
        super();
        this.title = title;
        this.status = status;
        this.details = Collections.singletonList(detail);
        this.timestamp = timestamp;
    }

}

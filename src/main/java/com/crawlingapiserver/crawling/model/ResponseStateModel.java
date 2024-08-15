package com.crawlingapiserver.crawling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseStateModel {
    private Boolean state;
    private String stringState;
    private String message;
    private HttpStatus httpStatus;
}

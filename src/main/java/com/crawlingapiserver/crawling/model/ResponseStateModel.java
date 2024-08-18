package com.crawlingapiserver.crawling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;


@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseStateModel {
    private Integer state;
    private String stringState;
    private String message;
    private HttpStatus httpStatus;
}

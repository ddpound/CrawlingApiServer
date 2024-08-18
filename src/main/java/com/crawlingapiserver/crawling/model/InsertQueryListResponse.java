package com.crawlingapiserver.crawling.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InsertQueryListResponse extends ResponseStateModel{
    private List<String> insertQueryList;
    private String insertQuery;
}

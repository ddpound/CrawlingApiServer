package com.crawlingapiserver.crawling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TargetParamsSetting {

    private String indexName;
    private Object startParams;
    private Boolean addNumber;
    private Integer addValue;
}

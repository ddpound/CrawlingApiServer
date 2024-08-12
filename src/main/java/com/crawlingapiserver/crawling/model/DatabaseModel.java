package com.crawlingapiserver.crawling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DatabaseModel {
    private String url;
    private String username;
    private String password;
}

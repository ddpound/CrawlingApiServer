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
    private String  url;
    private String  username;
    private String  password;
    private Boolean directInsert;
    private Boolean makeInsertTextFile;
    private String  insertTextFilePhysicalSavePath;
    private String  insertTextFileName;

    // DB에 insert를 진행할때는 이스케이프 처리를 진행하는게 적절하다
    private Boolean escape = true;
}

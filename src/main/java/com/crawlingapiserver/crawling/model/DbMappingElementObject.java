package com.crawlingapiserver.crawling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DbMappingElementObject {
    private String columnName;
    private String element;
    private String elementFind;
    private String elementName;
    private String file;
    private String openWindowDownload;
    private String id;
    private Boolean nullableOrPass;
    private Object nullableOrElseGet;

    // 특별한 설정이 없다면 첫번째 요소만 가져옵니다.
    private Integer elementOrderNumber = 1;

    // 기본적으로 모든 데이터를 가져옵니다.
    private Boolean elementEverything = true;

    // Text만 가져올지 아니면 태그 내용까지 가져올지 선택하는 옵션
    // 게시판 옮기는 걸 목적으로 했기때문에 기본 옵션은 false 입니다.
    private Boolean onlyText = false;
}

package com.crawlingapiserver.crawling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommandModel {

    private String targetURI;
    private List<String> targetURIList;
    private ArrayList<Object> targetList;
    private ArrayList<Object> targetRangeList;
    private String tableName;
    private ArrayList<String> columnList;
    private ArrayList<DbMappingElementObject> dbMappingElementObjectArrayList;
    private Integer targetLoopNumber;
    private ArrayList<TargetParamsSetting> targetParamsSettingList;
    private DatabaseModel database;
    private FileSettingModel fileSetting;

}

package com.crawlingapiserver.crawling.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FileSettingModel {

    /** 파일이 물리적으로 저장되는 경로*/
    private String filePhysicalSaveLocation;

    /** DB에 저장시킬 file path 형식 */
    private String dbFilePathLocation;


}

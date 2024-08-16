package com.crawlingapiserver.crawling.service;

import com.crawlingapiserver.crawling.model.CommandModel;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@Service
public class CrawlingService {

    /**
     * 최종 목적 uri 실행시켜주는 최종 서비스 메소드
     * */
    public List<String> runCrawling(CommandModel commandModel, List<String> finalTargetList, Connection connection){
        Boolean DbSwitch = false;

        if(connection != null){
            DbSwitch = true;
            log.info("DB Direct Insert Mode ON");
        }

        // DB 체크
        if(DbSwitch){

        }

        Boolean textFileBoolean = commandModel.getDatabase().getMakeInsertTextFile();
        if(textFileBoolean != null && textFileBoolean){
            // 텍스트파일 생성
            writeInsertTextFile(commandModel.getDatabase().getInsertTextFilePhysicalSavePath(), new ArrayList<String>());
        }

        return null;
    }

    /**
     * 만들어진 쿼리 리스트를 텍스트파일로 생성해서 저장해줌,
     * div 태그가 들어간 문자열들은 이스케이프 처리를 진행해서 저장해주어야 할 것으로 보임
     *
     * */
    public void writeInsertTextFile(String filePath, List<String> insertQueryList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (String insertQuery : insertQueryList) {
                writer.write(insertQuery);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

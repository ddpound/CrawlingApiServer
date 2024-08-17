package com.crawlingapiserver.crawling.service;

import com.crawlingapiserver.crawling.model.CommandModel;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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

        try {
            Boolean DbSwitch = false;

            if(connection != null){
                DbSwitch = true;
                log.info("DB Direct Insert Mode ON");
            }

            // DB 체크
            if(DbSwitch){

            }

            for (String crawlingUri : finalTargetList) {
                Document doc = Jsoup.connect(crawlingUri).get();
                Elements posts = doc.getElementsByTag("#bo_v");

                log.info(doc.toString());
                if(isPageNotFound(doc)){
                    log.info("Page Not Found");
                }else{
                    for (Element post : posts) {
                        System.out.println(post.text());
                    }

                }

            }



            Boolean textFileBoolean = commandModel.getDatabase().getMakeInsertTextFile();
            if(textFileBoolean != null && textFileBoolean){
                // 텍스트파일 생성
                writeInsertTextFile(commandModel.getDatabase().getInsertTextFilePhysicalSavePath(), new ArrayList<String>());
            }



        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return null;
    }





    /**
     *
     * 이미지 처리 방식
     * 1. src 경로를 원하는대로 수정해주기 (UUID 옵션 선택가능)
     * 2. base64 방식으로 변환해서 데이터 저장해주기
     * */
    public void imageProcessing(CommandModel commandModel, Element element){

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

    private static boolean isPageNotFound(Document doc) {
        // 페이지에 "글이 존재하지 않습니다" 메시지가 포함된 경우 처리
        Elements scriptElements = doc.select("script");
        for (Element script : scriptElements) {
            if (script.toString().contains("alert(\"글이 존재하지 않습니다.\"")) {
                return true;
            }
        }
        Elements noScriptElements = doc.select("noscript");
        for (Element noScript : noScriptElements) {
            if (noScript.toString().contains("글이 존재하지 않습니다.")) {
                return true;
            }
        }
        return false;
    }
}

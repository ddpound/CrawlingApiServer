package com.crawlingapiserver.crawling.service;

import com.crawlingapiserver.crawling.model.CommandModel;
import com.crawlingapiserver.crawling.model.DbMappingElementObject;
import com.crawlingapiserver.crawling.model.ResponseStateModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


@Log4j2
@RequiredArgsConstructor
@Service
public class CrawlingService {

    private final DBService dbService;

    /**
     * 최종 목적 uri 실행시켜주는 최종 서비스 메소드
     * */
    public ResponseStateModel runCrawling(CommandModel commandModel, List<String> finalTargetList, Connection connection){

        ResponseStateModel responseStateModel = null;
        List<String> insertQueryList = new ArrayList<>();
        try {
            ArrayList<StringBuilder> finalContentList = new ArrayList<>();
            StringBuilder finalContent = new StringBuilder();

            // 1. 크롤링 데이터 추출
            for (String crawlingUri : finalTargetList) {
                // 페이지 요청
                Document doc = Jsoup.connect(crawlingUri).get();

                // 한페이지에 mapping element list의 길이만큼 반복
                for (DbMappingElementObject mappingObject : commandModel.getDbMappingElementObjectArrayList()){
                    String cssQuery = "";

                    if(mappingObject.getElementFind().equals("id")) cssQuery = "#" + mappingObject.getElementName();
                    if(mappingObject.getElementFind().equals("class")) cssQuery = "." + mappingObject.getElementName();

                    Elements posts = doc.select(cssQuery);
                    int orderCount = 1;

                    for (Element post : posts) {

                        String content = mappingObject.getOnlyText() ? post.text() : post.toString();

                        if(commandModel.getDatabase().getEscape()){
                            content = StringEscapeUtils.escapeHtml4(content);
                        }

                        if(mappingObject.getElementEverything()){
                            finalContent.append(content);
                        }

                        if(mappingObject.getElementOrderNumber() == orderCount && !mappingObject.getElementEverything()){
                            finalContent.append(content);
                        }

                        orderCount++;
                    }

                    if(!finalContent.isEmpty()) finalContentList.add(finalContent);
                    finalContent = new StringBuilder();
                }

                if(commandModel.getDatabase().getDirectInsert()){
                    responseStateModel = dbService.insertData(connection,commandModel,finalContentList);
                }

                if(!finalContentList.isEmpty()) insertQueryList.add(dbService.makeInsertQueryList(commandModel,finalContentList).getInsertQuery());

                finalContentList.clear();
            }

            // 2. insert 쿼리 추출
            Boolean textFileBoolean = commandModel.getDatabase().getMakeInsertTextFile();
            if(textFileBoolean != null && textFileBoolean){
                // 텍스트파일 생성
                writeInsertTextFile(commandModel.getDatabase().getInsertTextFilePhysicalSavePath(), insertQueryList, commandModel.getDatabase().getInsertTextFileName());
            }

            return responseStateModel == null ? new ResponseStateModel() : responseStateModel;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
    public void writeInsertTextFile(String filePath, List<String> insertQueryList, String fileName) throws IOException {
        if(!fileName.contains(".txt")) fileName += ".txt"; // 확장자 붙여주기
        Files.createDirectories(Paths.get(filePath));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(Paths.get(filePath,fileName)), true))) {
            for (String insertQuery : insertQueryList) {
                writer.write(insertQuery);
                writer.newLine();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
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

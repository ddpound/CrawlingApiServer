package com.crawlingapiserver.crawling.service;


import com.crawlingapiserver.crawling.model.CommandModel;
import com.crawlingapiserver.crawling.model.ResponseStateModel;
import com.crawlingapiserver.crawling.model.TargetParamsSetting;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Service
public class UrlService {


    /**
     * 최종 target url을 만드는 메소드
     *
     * */
    public List<String> makeFinalTargetUrl(CommandModel commandModel) {
        StringBuilder targetName = new StringBuilder();
        List<String> targetNameList = new ArrayList<>();
        List<String> replaceAllTargetNameList = new ArrayList<>();

        HashMap<String, TargetParamsSetting> targetMap = new HashMap<>();
        List<TargetParamsSetting> targetParamsList = commandModel.getTargetParamsSettingList();
        String targetUri = commandModel.getTargetURI();


        // 이름이 key, 변수는 그래서 중복되면 안됨
        if(commandModel.getTargetParamsSettingList() != null  && !commandModel.getTargetParamsSettingList().isEmpty()){
            for (TargetParamsSetting targetParamsSetting : commandModel.getTargetParamsSettingList()) {
                targetMap.put(targetParamsSetting.getIndexName(), targetParamsSetting);
            }
        }


        // { 발견시 } 를 찾을때까지 String 문자열에 담고 변수가 있는지 찾기
        for (int i = 0; i < targetUri.length(); i++) {
            if(targetUri.charAt(i) == '{'){
                for (int j = i+1; j < targetUri.length(); j++) {
                    if(targetUri.charAt(j) == '}') {
                        targetNameList.add(targetName.toString());
                        replaceAllTargetNameList.add("\\{" + targetName + "\\}");
                        targetName = new StringBuilder();
                        break;
                    }

                    targetName.append(targetUri.charAt(j));
                }
            }
        }

        List<String> newTargetUriList = new ArrayList<>();

        int loopNumber = commandModel.getTargetLoopNumber()-1;
        if(commandModel.getTargetLoopNumber() == 1) loopNumber = 1;

        // 입력 한 개수 만큼 반복
        for (int i = 0; i < loopNumber; i++) {
            // 한번 돌린 값은 초기화
            String newTartgetUri = targetUri;

            for (int j = 0; j < replaceAllTargetNameList.size(); j++) {

                int sumValue = 0;

                // add 타입
                if(targetMap.get(targetNameList.get(j)).getAddNumber() && i > 0){
                    if(targetMap.get(targetNameList.get(j)).getStartParams().getClass() == Integer.class){
                        sumValue = ((int) targetMap.get(targetNameList.get(j)).getStartParams() + targetMap.get(targetNameList.get(j)).getAddValue());
                        targetMap.get(targetNameList.get(j)).setStartParams(sumValue);
                    }
                }

                newTartgetUri = newTartgetUri.replaceAll(replaceAllTargetNameList.get(j), targetMap.get(targetNameList.get(j)).getStartParams().toString());
            }

            newTargetUriList.add(newTartgetUri);
        }

        return newTargetUriList;
    }

    /**
     *  객체중 targetURIList 검증진행후 반환하는 메소드
     * */
    public List<String> targetURIListValidationAndReturnData(CommandModel commandModel){

        if(commandModel.getTargetURIList() != null  && !commandModel.getTargetURIList().isEmpty()){
            return commandModel.getTargetURIList();
        }

        // 빈 배열 리스트 반환
        return null;
    }

    /**
     * 타겟 리스트를 검증하기 위한 검증 메소드
     * */
    public ResponseStateModel urlExtractionValidation(CommandModel commandModel){
        // 추출된 타겟 명령어 리스트
        List<String> extractTargetList = new ArrayList<>();
        StringBuilder targetName = new StringBuilder();
        List<TargetParamsSetting> targetParamsList = commandModel.getTargetParamsSettingList();

        String targetUri = commandModel.getTargetURI();

        // { 발견시 } 를 찾을때까지 String 문자열에 담고 변수가 있는지 찾기
        for (int i = 0; i < targetUri.length(); i++) {
            if(targetUri.charAt(i) == '{'){
                for (int j = i+1; j < targetUri.length(); j++) {
                    if(targetUri.charAt(j) == '}') {
                        extractTargetList.add(targetName.toString());
                        targetName = new StringBuilder();
                        break;
                    }

                    // 마지막에 도달했고 } 문자열도 아니라면
                    if(j == targetUri.length()-1 && targetUri.charAt(j) != '}'){
                        // Please put closing parentheses, 괄호를 닫아주세요.
                        return ResponseStateModel.builder()
                                .state(false)
                                .message("Please put closing parentheses")
                                .httpStatus(HttpStatus.BAD_REQUEST)
                                .build();
                    }

                    targetName.append(targetUri.charAt(j));
                }
            }
        }

        // 추출된 리스트와 기존 리스트 길이가 다르면 false 반환
        if(targetParamsList.size() != extractTargetList.size()){
            return ResponseStateModel.builder()
                    .state(false)
                    .message("The number of {} variables in targeturl and targetParamsSettingList size is different")
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .build();
        }

        return ResponseStateModel.builder()
                .state(true)
                .build();
    }





    /**
     * 순회하면서 변수와
     *
     * */
    public List<String> findIndexAndName(String targetList){
        int foundCount = 0;

        // 타겟 리스트 만큼 순회
        for (int i = 0; i < targetList.length(); i++) {

            // 타겟 발견
            if(targetList.charAt(i) == '{'){

            }

        }


        return null;
    }


    /**
     *
     * */
    public int findCountPattern(String text, String pattern){
        Pattern compilePattern = Pattern.compile(pattern);
        Matcher matcher = compilePattern.matcher(text);

        int count = 0;
        while (matcher.find()) {
            count++;
        }

        return count;
    }

}

# 간단한 크롤링 데이터 이전 자동화 API 서버 [beta]
- 마지막 업데이트 날짜 2024-08-17

## 개요
회사에서 이전 사이트의 게시판 게시글 데이터를 마이그레이션해야할 
일이 있었는데 따로 csv나 db 데이터를 제공해주지 않아서
해당 사이트를 크롤링해 데이터를 일괄적으로 옮겨 insert해주는 프로그램을 간단하게 만들었습니다.

따로 작업 공수시간이 주어진것도 아니였고 따로 주말에 시간을 내어 하루정도 만든 프로그램이라
모든 요소들이 하드코딩 해서 만들어두었습니다. (오직 해당 사이트의 글만 이전하는 용도로 제작)

프로토타입 버전을 완성하고 나니 조금 아쉬워 시간을 더 들여서 범용성 또는 확장성을 높여 다른이들도 사용할 수 있으면
좋을 것 같아 추가 개발을 진행했습니다.


## 기능

크롤링으로 얻은 데이터(글,사진,업로드된 파일)들을 
DB에 Insert를 진행하거나 insert query를 생성해 텍스트 파일을 받습니다.


### 지원하는 DB
1. postgresql
2. mysql
3. mariadb
4. oracle
5. mongodb

### 작업시간 및 출시 예정
1. 프로토타입 버전 : 6시간
2. 알파버전 : 3일 [개발중] 
3. 베타버전 : [계획중]
4. 릴리즈 후보 : [계획 중]
5. 정식 릴리즈 : [계획 중]

### 개선점 [2024.07.28]

#### 1. 프로토 타입의 개선점 
      1. 프로젝트에 맞춰 하드코딩되어있는것 모두 수정해야함
      2. 5시간정도 작업을 더 진행하면 크롤링후 DB insert가 가능한 유연한 프로그램이 완성 될것 같습니다.


### 사용방법


command 양식

targetURI : 실행시킬 uri 주소
targetLoopNumber : 반복할 횟수

#### targetParamsSettingList (각 ${} 값에 들어갈 세팅값, ${}값에 맞춰 세팅값을 넣어주세요.)
1. index : 순서 , 몇번째 ${} 인지
2. startParams : 시작값
3. addNumber : 값을 추가 시킬지 판단 여부 false면 addValue 가 있어도 추가가 안된다.
4. addValue : 추가될 값


nullableOrElseGet : null일때 대체할 값을 표기, ()
nullableOrPass : null 이면 insert 없이 pass할지 안할지 체크 (true면 insert 안하고 pass)




```json
{
   "database" : {
      "url": "jdbc:postgresql://127.0.0.1:5432/test_db",
      "username": "testuser",
      "password": "testpass1234!",
      "directInsert": true,
      "makeInsertTextFile" : true,
      "insertTextFilePhysicalSavePath" : ""
   },
   "fileSetting" : {
      "filePhysicalSaveLocation" : "/Users/youseongjung/Ehs/tempfiles",
      "dbFilePathLocation" : "/WebUpload/ispf/20240807/"
   },
   "targetURI": "http://ispf.mworks.kr/notice/{number}",
   "targetLoopNumber" : 130,
   "targetParamsSettingList" : [
      {
         "indexName" : "number",
         "startParams" : 1,
         "addNumber" : true,
         "addValue" : 1
      }
   ],
   "tableName" : "testTable",
   "columnList" : ["board_title", "insert_date","file_group_id"],
   "linkElementObjectList" : [
      {
         "columnName" : "board_title",
         "element" :  "div",
         "elementFind" : "id",
         "elementName" : "board_tit",
         "file" : false,
         "nullableOrElseGet" : "데이터 이전 입니다.",
         "nullableOrPass" : false
      },
      {
         "columnName" : "board_content",
         "element" :  "div",
         "elementFind" : "id",
         "elementName" : "boardContent",
         "file" : false,
         "nullableOrElseGet" : "데이터 이전 입니다."
      },
      {
         "columnName" : "board_file",
         "element" :  "div",
         "elementFind" : "id",
         "elementName" : "board_tit",
         "file" : false,
         "nullableOrElseGet" : "데이터 이전 입니다."
      },
      {
         "columnName" : "file_group_id",
         "element" :  "p",
         "elementFind" : "class",
         "elementName" : "file-list",
         "file" : true,
         "openWindowDownload": true,
         "id" : "1"
      }
   ]
}




```
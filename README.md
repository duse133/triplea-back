Trip:lener
=============
여행 플래너 사이트 테스트용 Trip:lener의 백엔드 프로젝트입니다.

Version
=============
1. Java 17   
2. MySQL 8.0.36   
3. mysql-connector-java 8.0.33   
4. InteliJ 2023.3.4   

How to use
=============
* DB : 데이터 베이스 zip 파일
  * MySQL Workbench를 사용하여 해당 파일 import 가능
  * 먼저 project_db 스키마 생성
* .jar : 실행파일
  * 해당 project_db 스키마가 있어야 함, 따라서 먼저 DB 먼저 import
  * jar 파일은 위에 자바와 MySQL이 깔려 있는 경우에 터미널로 실행(MySQL 유저이름 root, 비밀번호 : 1234)
  * 실행후 http://localhost:8080/swagger-ui/index.html로 이동하여 REST API 통신 테스트 확인 가능
* BackEnd : 프로젝트 폴더
  * InteliJ로 해당 파일 열기
  * InteliJ로 해당 프로젝트 파일 실행
  * 실행후 http://localhost:8080/swagger-ui/index.html로 이동하여 REST API 통신 테스트 확인 가능
  * 해당 프로젝트 폴더에 src/main/resources에 application.yml 파일 내용 MySQL username과 password 수정 가능

Version Up Contents
=============
* BackEnd-0.2.0.jar : 서비스단에 여행지 루트 짜주는 로직 수정
* DB : 부족한 데이터 추가(대구, 대전, 광주, 인천, 제주, 울산, 세종) 예정

Description (src/java/capstone/triplea/backend)
=============
* config - WebConfig.java
  * 크로스 도메인 이슈 해결 코드로 프론트엔드 http://localhost:3000에 모든 요청을 허용
* controller - MainController.java
  * 메인 컨트롤러로 프론트엔드(클라이언트)의 모든 요청을 먼저 받아서 해당 URL로 어떤 서비스를 사용할건지 매핑

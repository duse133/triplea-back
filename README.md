Trip:lenner
=============
여행 플래너 사이트 Trip:lenner의 백엔드 프로젝트입니다.

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

Version Up Contents 0.1.0 -> 0.2.0
=============
* REST API 수정 : POST 방식에서 GET 방식으로 변경(데이터 수정이 아닌 데이터를 가져오기만 하기 때문)
* BackEnd-0.2.0.jar : 서비스단에 여행지 루트 짜주는 로직 수정

Version Up Contents 0.2.0 -> 0.2.1
=============
* DB : 부족한 데이터 추가(대구, 대전, 광주, 인천, 제주, 울산, 세종)
* DB 오타 수정으로 인한 프로젝트 파일의 Entity, Service 파일의 오타 수정
* REST API 수정 : GET 방식으로 하나씩 파라미터를 받는 방식이 아닌 객체를 받도록 수정

Version Up Contents 0.2.1 -> 0.2.2
=============
* REST API 오류 메세지 추가 : E01(파라미터 누락), E02(데이터 부족)
* REST API 성공 메세지 및 코드 추가 예정 : 아직 추가 안되서 성공되면 null값으로 들어옴

Version Up Contents 0.2.2 -> 0.3.0
=============
* UserInput의 데이터 숙소정보(accommodationInfo)객체 추가 : accommodationInfo(위도, 경도, 숙소이름)객체로 구성
* URL "/api/tourlist" 추가 : Area 정보에 따라 DB에서 해당 지역 데이터를 모두 넘겨줌(이름, 위도, 경도, 설명) 
* REST API 오류 메세지 추가 : E03(해당하는 지역 정보가 없습니다.)
* REST API 성공 메세지 및 코드 추가: 성공시 코드 "S01", 메세지 "성공"으로 넘겨줌
* K-Means 군집 알고리즘 추가 : 아직 DB의 가중치값이 미구현이 되었으므로 비활성화 및 일부 미구현(0.4.0 버전때 구현 예정)
* 군집 내 최단거리 알고리즘을 통해 최적의 루트 만들기 : 아직 미구현(0.4.0 버전때에 구현 예정)

Version Up Contents 0.3.0 -> 0.4.0
=============
* 사용자 입력란 강도(Strength) 삭제 예정?
* 루트 생성 갯수 수정 : 루트 3개 형성 -> 루트 1개 형성
* 사용자 입력란 추가 : 숙소정보(accommodationName),숙소위도(accommodate_latitude),숙소경도(accommodate_longitude)
* K-Means 군집 알고리즘 추가 : DB의 중요도가 높은 여행지를 기준으로 군집 형성
* 군집 내 최단거리 알고리즘을 통해 최적의 루트 형성 : 출발점(숙소)에서 가장 가까운 군집의 기준점 선택, 그 군집의 기준점을 출발점으로 최단거리 루트 생성
* 여행 일 수에 맞게 여행루트 분배 : 총거리를 여행 일수로 나눠 나눈 거리만큼 해당 루트를 쪼개어 여행 일수에 맞게 분배, 만약 너무 많이 몰려서 일수가 부족하다면 근처 군집의 루트를 가져와서 다시 분배(여기서 너무 많으면 나머지 루트 버림)

Version Up Contents 0.4.0 -> 0.5.0
=============
* DB 업데이트 : 부족한 관광지 데이터 추가, 불필요한 컬럼 제거, 중요도 설정
* DB 업데이트에 따른 엔티티값 모두 수정
* 관광지 이미지 가져오기 : 한국관광공사_관광사진 정보 API 사용하여 관광지 이미지 링크를 가져와 클라이언트에 넘김, API 오류(E03), 이미지 못 찾을 시(E10)
* 여행루트 생성시 : 데이터가 많기에 한쪽에 쏠리는 현상 발생 -> 일 수를 2일로 잡을 시에 하루에 50곳을 돌아야하는 루트가 만들어지는 문제점(강도로 해결 할 예정)

Version Up Contents 0.5.0 -> 0.6.0
=============
* 관광지 이미지 가져오기 : 한국관광공사_관광사진 정보 API 사용안함(사진이 없는게 많음) -> 한국관광공사 포토코리아에서 크롤링해서 이미지 소스 받음, 이미지 못찾을 시(E10)
* 여행루트 생성시 : 데이터가 많기에 한쪽에 쏠리는 현상 발생 -> 일 수를 2일로 잡을 시에 하루에 50곳을 돌아야하는 루트가 만들어지는 문제점, 강도로 해결함
* 강도 : 약함이랑 중간이랑 차이점이 없음 하나로 합칠 예정, 강함은 무조건 일단 일당 루트 7개 이상, 약함은 일당 루트 4개 이하

Version Up Contents 0.6.0 -> 0.6.1
=============
* 여행루트에 설명 추가
* 강도 : 약함이랑 중간이랑 차이점이 없음 하나로 합칠 예정 보통으로, 강함은 무조건 일단 일당 루트 7개 이상, 보통은 일당 루트 음

Version Up Contents 0.6.0 -> 0.7.0
=============
* 게시판 DB 생성 : id, title, content, password
* 프로젝트 파일 게시판 관련 파일 생성 : Entity, Repository
* 게시판 서비스 로직 구현

Version Up Contents 0.7.0 -> 0.8.0
=============
* 게시판 DB 추가 : 기존에서 date(날짜) 추가
* 외부 서버 띄우기용 설정 : MySQL
* 프로젝트 파일 : 기존 로컬호스트의 MySQL 유지
* Jar(배포 파일) : 외부 서버의 MySQL로 변경
* 루트 형성 알고리즘 변경 예정(0.9.0으로 할 예정)

Version Up Contents 0.8.0 -> 0.8.1
=============
* 예외처리 추가 : 숙소정보가 null이 되면 필수파라미터 누락 예외처리

Description (src/java/capstone/triplea/backend)
=============
* config - WebConfig.java
  * 크로스 도메인 이슈 해결 코드로 프론트엔드 http://localhost:3000 에 모든 요청을 허용
* controller - MainController.java
  * 메인 컨트롤러로 프론트엔드(클라이언트)의 모든 요청을 먼저 받아서 해당 URL로 어떤 서비스를 사용할건지 매핑
* dto
  * TravelPlanerDTO.java - 관광지 루트 정보를 가지고 루트를 짜서 해당 관광지 관광지명,위도,경도 정보를 순서대로 담는 객체
  * TravelPlanerListDTO.java - 짜여진 루트 정보를 3개의 배열로 정보를 가지는 객체
  * UserInputDTO.java - 사용자가 입력한 정보 3개(지역, 일수, 강도)정보를 담는 객체
* entity - 데이터베이스에서 17개의 지역 정보 테이블과 엔티티간 연관관계 정의
* repository - 엔티티로 DB에 접근하여 17개의 지역 정보 테이블에 CRUD를 사용하기 위한 인터페이스로 JPA 상속
* service - MakePlanerService.java
  * 사용자가 입력한 3가지 정보(지역, 일수, 숙소정보)를 가지고 중요한 곳을 기점으로 근처에 루트를 만들어서 추
  

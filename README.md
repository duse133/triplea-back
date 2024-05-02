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
  

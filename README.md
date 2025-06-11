# [실전! 스프링 부트와 JPA 활용1 - 웹 애플리케이션 개발](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-JPA-%ED%99%9C%EC%9A%A9-1/dashboard)

## 2. 프로젝트 환경설정

### 프로젝트 생성

- `Spring Initializr Java Support` extension으로 프로젝트 생성했다.
- groupId
  - 프로젝트를 만든 조직이나 그룹을 식별하는 ID
  - 보통 도메인을 거꾸로 쓴 형식 (예: com.example, org.springframework.boot)
- artifactId
  - 해당 프로젝트(모듈)의 고유 이름을 의미
  - user-api, order-service, auth-core
- 요즘은 `jsp`보다 `thymeleaf` 권장
- DB는 mysql8을 사용함

### 라이브러리 살펴보기

- `./gradlew dependencies`: 의존관계 설명

### View 환경 설정

- `thymeleaf`를 spring에서 밀어줌 + 사용법을 약간 익혀야한다

### H2 데이터베이스 설치

### JPA와 DB 설정, 동작확인

- show_sql속성은 system out을 통해 출력하기에 logging으로 출력 하는게 권장된다.
- `@PersistenceContext`: 부트가 엔티티매니저 객체 주입
- save하고 멤버 대신 id를 반환하는건 커맨드와 쿼리를 분리해라를 따르는 것
  - 명령과 조회의 분리
  - Command 데이터를 변경하는 작업 (예: save(), delete(), update())
  - Query 데이터를 조회하는 작업 (예: findById(), getUser())
- 인텔리제이에 테스트 코드를 생성해주는 기능이 있는 것 같은데 `Symflower` 익스텐션으로 대체
- `@Transactional`는 spring 껄 import할 것
- 왠만한 라이브러리들은 스프링부트에서는 해당 부트버전에 적합한 버전이 이미 정해져있음 그래서 라이브러리 버전을 명시하지 않아도 그 버전을 사용
- `p6spy-spring-boot-starter`는 파라미터까지 sql에 넣어보여줘 좋지만 운영 배포까지는 성능적 문제로 고민 필요

## 3. 도메인 분석 설계

### 요구사항 분석

### 도메인 모델과 테이블 설계

### 엔티티 클래스 개발1

### 엔티티 클래스 개발2

### 엔티티 설계시 주의점

## 4. 애플리케이션 구현 준비

### 구현 요구사항

### 애플리케이션 아키텍처

## 5. 회원 도메인 개발

### 회원 리포지토리 개발

### 회원 서비스 개발

### 회원 기능 테스트

## 6. 상품 도메인 개발

### 상품 엔티티 개발(비즈니스 로직 추가)

### 상품 리포지토리 개발

### 상품 서비스 개발

## 7. 주문 도메인 개발

### 주문, 주문상품 엔티티 개발

### 주문 리포지토리 개발

### 주문 서비스 개발

### 주문 기능 테스트

### 주문 검색 기능 개발

## 8. 웹 계층 개발

### 홈 화면과 레이아웃

### 회원 등록

### 회원 목록 조회

### 상품 등록

### 상품 목록

### 상품 수정

### 변경 감지와 병합(merge)

### 상품 주문

### 주문 목록 검색, 취소

### 다음으로

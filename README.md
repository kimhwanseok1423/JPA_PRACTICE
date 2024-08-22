

# JPA 프로젝트 

---

## 목차
- [개요](#개요)
- [사용기술](#사용기술)
- [느낀 점](#느낀-점)
- [구현 기능](#구현기능)
- [ERD](#erd)
- [영상](#영상)


## 개요

이 프로젝트는 JPA를 활용한 웹 애플리케이션으로, 회원 관리, 상품 관리, 주문 및 결제 시스템을 포함한 기능들을 구현하였습니다. 전 프로젝트에서 구현하지 못한 기능들을 보완하고, 성능 최적화를 위해 Querydsl 및 Spring JPA를 적극 활용하였습니다.

주요 기능으로는 회원가입, 회원 목록 조회, 상품 등록 및 조회, 주문 관리, 결제 시스템이 있으며, 동적 쿼리와 복잡한 쿼리 문제를 해결하기 위해 JPA의 다양한 기능을 적용했습니다. 또한, Querydsl을 통해 가독성 높은 동적 쿼리를 작성하고, Spring JPA의 페이징 및 캐싱 기능을 이용하여 성능을 최적화하였습니다.

이 프로젝트는 데이터베이스 연동과 ORM(Object-Relational Mapping)을 활용한 백엔드 개발에 중점을 두고 있으며, ERD를 통해 데이터 모델링을 시각화하고, 실제 구현된 기능들을 코드와 함께 설명합니다.
 <br>
## 사용기술
 <br>
 Spring Boot , JPA , queryDsl , H2 , Postman , 포트원 , 카카오 Api ,인텔리제이 <br><br>
<img src="https://github.com/kimhwanseok1423/JPA_PRACTICE/blob/master/src/main/resources/static/image/캡처1.PNG"><br>


## 느낀 점 


이번 프로젝트는 JPA와 Spring JPA, 그리고 Querydsl을 실무에 적용해보는 좋은 경험이었습니다.  다양한 인터넷 강의와 자료를 참고하여 실제로 작동하는 시스템을 구축하는 과정을 통해 많은 것을 배울 수 있었습니다.

특히, JPA의 영속성 컨텍스트와 연관관계 매핑, 그리고 동적 쿼리를 처리하는 방법에 대해 깊이 이해하게 되었습니다. 또한, Querydsl을 통해 복잡한 쿼리 문제를 간결하고 효율적으로 해결할 수 있다는 것을 경험할 수 있었습니다.

프로젝트를 진행하며, 기존에는 이해하기 어려웠던 ORM과 데이터베이스 성능 최적화의 중요성을 체감할 수 있었고, 앞으로 실무에 적용할 때 유용할 지식들을 습득하게 되었습니다. 이번 경험을 통해 JPA와 Spring 생태계에 대한 이해도를 한층 높일 수 있었고, 더욱 자신감을 가지게 되었습니다.

## 구현기능 


<details>
  <summary>회원가입 및 회원조회  </summary>
  
  - **구현 기능** <br>
  사용자 회원가입 및 로그인 기능을 구현했습니다.

- **구현 방법** <br>
  
  - 계정 중복 확인
    -`UserRepository`조회하여 중복 시 예외 던집니다.
  - 로그인 기능
    - 로그인 시 사용자가 입력한 정보가 데이터베이스와 일치하는지 확인합니다. 로그인 성공 시 사용자에게 로그인 상태를 유지하는 기능을 제 
      공하며, 로그인 실패 시 적절한 오류 메시지를 표시합니다.<br>
  <img src="https://github.com/kimhwanseok1423/JPA_PRACTICE/blob/master/src/main/resources/static/image/캡처4.PNG"><br>

  - 카카오 로그인 기능
     - 추후 업데이트 예정(오류수정중) <br>
  - 마이 페이지
    - 전체 회원 목록 조회 및 동적쿼리 BooleanBuilder를 활용한 회원 이름, 나이를 검색하여 조회기능 추가 
      
<img src="https://github.com/kimhwanseok1423/JPA_PRACTICE/blob/master/src/main/resources/static/image/캡처2.PNG"><br>


<img src="https://github.com/kimhwanseok1423/JPA_PRACTICE/blob/master/src/main/resources/static/image/캡처3.PNG"><br>


 - 기능 과정 <br>
  https://hwanpaperblog.tistory.com/16  <br>
  https://hwanpaperblog.tistory.com/17  <br>
  https://hwanpaperblog.tistory.com/29  <br>


</details>

<details>
  <summary>쇼핑몰 기능   </summary>


  - **구현 기능** <br>
    - 상품 등록
    - 상품 주문
    - 여러 상품 주문 

  - **구현 방법** <br>
    - 상품 등록
      - 전 프로젝트에서 쇼핑몰을했지만 판매자입장에서 상품을 등록할수가 없다는 생각이 들어서 추가했습니다



    - 상품 주문 
      - 상품을 주문했을때 기존 재고에서 빼주는 함수 설정
        
    - 여러 상품 주문 
      - 장바구니도 여러개의 상품을 결제 하듯이 기존 책 카테고리를 영화 책 앨범등을 세분화 시켜 주문하도록 기능추가  
        
    - 결제 시스템 
      - 주문 내역에서 결제할수있도록 기능 추가
      - 포트원 API를 활용해 카카오페이로 결제가 가능하도록 구현
    - 성능 최적화
     -  용량 데이터 처리 시, 페이징과 정렬을 통해 서버의 부하를 줄이고 응답 속도를 향상
     -   자주 조회되는 데이터는 캐시를 적용하여 데이터베이스 쿼리 횟수를 줄이고 응답 속도 개선
</details>



 ## ERD
<img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처12.PNG">

  ## 영상
   ## [유튜브 링크](https://www.youtube.com/watch?v=ntikFWHEWn4)

   

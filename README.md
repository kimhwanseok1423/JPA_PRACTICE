

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
인텔리제이 , Spring Boot , JPA , queryDsl , 포트원 , 카카오 Api<br><br>



## 느낀 점 


이번 프로젝트는 JPA와 Spring JPA, 그리고 Querydsl을 실무에 적용해보는 좋은 경험이었습니다. 직접 프로젝트를 기획하고 개발한 것은 아니지만, 다양한 인터넷 강의와 자료를 참고하여 실제로 작동하는 시스템을 구축하는 과정을 통해 많은 것을 배울 수 있었습니다.

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
      공하며, 로그인 실패 시 적절한 오류 메시지를 표시합니다.
  
  - 마이 페이지
    - 전체 회원 목록 조회 및 동적쿼리 BooleanBuilder를 활용한 회원 이름, 나이를 검색하여 조회기능 추가 
      
<img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처21.PNG"><br>

<img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처22.PNG"><br>

 - 기능 과정
    https://hwanpaperblog.tistory.com/29


</details>

<details>
  <summary>관리자 페이지 </summary>


  - **구현 기능** <br>
    - 관리자 페이지
    - 회원 삭제 ,리뷰 관리
    - 기간별 매출 현황 

  - **구현 방법** <br>
    - 관리자 페이지 구성
      - 관리자가 쉽게 사용할 수 있도록 UI/UX를 설계했습니다.
    - 회원 삭제
      - 회원 목록을 표시하고, 특정 회원을 선택하여 삭제할 수 있도록 했습니다.
      - 삭제 요청 시 확인 절차를 추가하여 실수로 인한 삭제를 방지합니다.
    - 리뷰 관리
      - 모든 리뷰를 리스트업하여 관리할 수 있는 인터페이스를 제공했습니다.
      - 리뷰를 승인하거나 삭제할 수 있는 기능을 구현했습니다.
    - 기간별 매출 현황
      - 기간을 설정하여 해당 기간의 매출 데이터를 조회할 수 있는 기능을 구현했습니다.
      - 매출 데이터를 차트로 시각화하여 분석할 수 있도록 했습니다.
 <img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처23.PNG"><br>

 <img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처24.PNG"><br>

 <img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처25.PNG"><br>
   
</details>

<details>
  <summary>고객들이 원하는 알고리즘 추천  </summary> <br>

- **구현 기능** <br>
    - 로그인한 회원과 비슷한 취향의 고객이 선택한 책들을 추천해주는 기능 <br>

- **구현 방법**<br>
    - 유사도 높은 순으로 정렬
      - 사용자가 평가 혹은 구매하지 않은 아이템을 유사도에 따라 정렬한다.
    - 인기도 높은 순으로 정렬
      - 각 아이템 간 등급(평점)의 평균을 계산하고 이를 통해 인기도를 결정한다. 그 후 인기도 순으로 정렬하고 상위 N개를 출력한다.<br>
        
      <img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처15.PNG"><br><br>
        <img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처16.PNG"><br><br>
          <img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처17.PNG"><br><br>
            <img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처18.PNG"><br><br>
  <img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처19.PNG"><br><br>
   <img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처20.PNG"><br>

</details>

<details>
  <summary>메인 페이지 기능 구현  </summary>

- **구현 기능** <br>
    - 

- **구현 방법**<br>
   - 메인 페이지 구성
     - 데이터베이스에서 책 데이터를 가져와 select 요소에 동적으로 표시했습니다.
   - 카테고리 페이지
     - 데이터베이스 테이블에 카테고리 컬럼을 추가하여 책들이 해당 카테고리에 맞게 분류되도록 설정했습니다.
       사용자가 메인 페이지에서 선택한 카테고리에 따라 해당 카테고리에 속하는 책들을 조회하고 표시했습니다.
   - 상세 페이지
     - 사용자가 특정 책을 선택하면, 선택된 책의 id 값을 기반으로 데이터베이스에서 해당 책의 상세 정보를 가져와 상세 페이지에 표시했습니다.
       상세 페이지에서는 책의 제목, 저자, 가격, 설명 등의 세부 정보를 제공했습니다.
   - 장바구니 기능
     - 사용자가 원하는 책을 장바구니에 추가할 수 있도록 구현했습니다.
       장바구니에 담긴 책들은 사용자 세션에 저장되어 유지되며, 필요 시 데이터베이스에도 저장됩니다.
   - 결제 기능
     - 장바구니에 담긴 책들을 확인하고 결제할 수 있는 기능을 구현했습니다.
     결제 정보 입력, 결제 처리, 그리고 주문 확인 단계를 거쳐 사용자가 책을 구매할 수 있도록 했습니다.

<img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처26.PNG"><br><br><br><br>
<img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처27.PNG"><br><br><br><br>
<img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처28.PNG"><br><br><br><br>
<img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처29.PNG"><br><br><br><br>
<img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처30.PNG"><br><br><br><br>
<img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처31.PNG"><br><br><br><br>
<img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처32.PNG"><br><br><br><br>

</details>

 ## ERD
<img src="https://github.com/kimhwanseok1423/project_ezenbooks/blob/master/frontend/public/img/캡처12.PNG">

  ## 영상
   ## [유튜브 링크](https://www.youtube.com/watch?v=ntikFWHEWn4)

   

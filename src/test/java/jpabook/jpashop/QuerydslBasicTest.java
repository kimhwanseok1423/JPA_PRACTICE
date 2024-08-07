package jpabook.jpashop;

import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Entity.Member_practice;
import jpabook.jpashop.domain.Entity.QMember_practice;
import jpabook.jpashop.domain.Entity.QTeam_practice;
import jpabook.jpashop.domain.Entity.Team_practice;
import jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import java.util.List;

import static jpabook.jpashop.domain.Entity.QMember_practice.member_practice;
import static jpabook.jpashop.domain.Entity.QTeam_practice.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Commit

public class QuerydslBasicTest {

@Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;
@BeforeEach
    public void before() {
    queryFactory=new JPAQueryFactory(em);
    Team_practice teamA = new Team_practice("teamA");
    Team_practice teamB = new Team_practice("teamB");
    em.persist(teamA);
    em.persist(teamB);

    Member_practice member1 = new Member_practice("member1", 10, teamA);
    Member_practice member2 = new Member_practice("member2", 20, teamA);

    Member_practice member3 = new Member_practice("member3", 30, teamB);
    Member_practice member4 = new Member_practice("member4", 40, teamB);

    em.flush();
    em.clear();

    em.persist(member1);
    em.persist(member2);
    em.persist(member3);
    em.persist(member4);
}
    @Test
            public void startJPQL(){
        Member_practice findMember = em.createQuery("select m from Member_practice m where m.username=: username", Member_practice.class).setParameter("username", "member1")
                .getSingleResult();
        assertThat(findMember.getUsername()).isEqualTo("member1");

    }

@Test
    public void startQuerydsl(){


    Member_practice findMember = queryFactory
            .select(member_practice)
            .from(member_practice)
            .where(member_practice.username.eq("member1")).fetchOne();
assertThat(findMember.getUsername()).isEqualTo("member1");

}
public void search(){
    Member_practice findMember = queryFactory.selectFrom(member_practice).where(member_practice.username.eq("member1")
            .and(member_practice.age.eq(10))).fetchOne();

    assertThat(findMember.getUsername()).isEqualTo("member1");
}


    public void searchAndParam(){  //AND 쓸필요없이 쉽표(,)를 쓰면 됨
        Member_practice findMember = queryFactory.selectFrom(member_practice).where(
                member_practice.username.eq("member1"),
                member_practice.age.eq(10)) .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }



    @Test
    public void resultFetch(){

        //리스트로 조회
        List<Member_practice> fetch = queryFactory.selectFrom(member_practice).fetch();

        // 단건조회
        Member_practice fetchOne = queryFactory.selectFrom(member_practice).fetchOne();

        Member_practice fetchFirst = queryFactory.selectFrom(member_practice).fetchFirst();

        QueryResults<Member_practice> results = queryFactory.selectFrom(member_practice).fetchResults();

        // 패치 리설트는 getTotal , getLimit , offset 제공




    }
    /**
     * 회원 정렬 순서
     * 1. 회원 나이 내림차순(desc)
     * 2. 회원 이름 올림차순(asc)
     * 단 2에서 회원 이름이 없으면 마지막에 출력(nulls last)
     */
    @Test
    public void sort(){
        em.persist(new Member_practice(null,100));
        em.persist(new Member_practice("member5",100));
        em.persist(new Member_practice("member6",100));

        List<Member_practice> result = queryFactory.selectFrom(member_practice).where(member_practice.age.eq(100))
                .orderBy(member_practice.age.desc(), member_practice.username.asc().nullsLast())
                .fetch();

        Member_practice member5=result.get(0);
        Member_practice member6=result.get(1);
        Member_practice memberNull=result.get(2);
        assertThat(member5.getUsername()).isEqualTo("member5");
        assertThat(member6.getUsername()).isEqualTo("member6");
        assertThat(memberNull.getUsername()).isNull();

    }
@Test
    public void paging(){
    List<Member_practice> result = queryFactory.selectFrom(member_practice)
            .orderBy(member_practice.username.desc()).offset(1).limit(2).fetch();
    assertThat(result.size()).isEqualTo(2);
}
@Test
    public void paging2() {
        QueryResults<Member_practice> queryResults = queryFactory
                .selectFrom(member_practice)
                .orderBy(member_practice.username.desc())
                .offset(1)
                .limit(2)
                .fetchResults();
        assertThat(queryResults.getTotal()).isEqualTo(4);
        assertThat(queryResults.getLimit()).isEqualTo(2);
        assertThat(queryResults.getOffset()).isEqualTo(1);
        assertThat(queryResults.getResults().size()).isEqualTo(2);    }

@Test
public void aggregation(){
    List<Tuple> result = queryFactory.select(
                    member_practice.count(),
                    member_practice.age.sum(),
                    member_practice.age.avg(),
                    member_practice.age.max(),
                    member_practice.age.min()
            )
            .from(member_practice)
            .fetch();

    Tuple tuple = result.get(0);
    System.out.println("count123"+
            tuple);


}


    @Test
    public void group() throws Exception {
        //given
        List<Tuple> result = queryFactory.select(team_practice.name, member_practice.age.avg())
                .from(member_practice)
                .join(member_practice.team, team_practice)
                .groupBy(team_practice.name)
                .fetch();
        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);



        //when
        //then

    }
@Test

public void join(){

    List<Member_practice> result = queryFactory.selectFrom(member_practice).join(member_practice.team, team_practice)
            .where(team_practice.name.eq("teamA"))
            .fetch();

    assertThat(result).extracting("username").containsExactly("member1","member2");
}

/**
 * 회원과 팀을 조인하면서 , 팀 이름이 teamA인 팀과 조인 ,회원은 모두 조회
 * JPQL: select m,t from Member m left join m.team on t.name="teamA"
 */

@Test
public void join_on_filtering(){
    List<Tuple> result = queryFactory.select(member_practice, team_practice)
            .from(member_practice)
            .leftJoin(member_practice.team, team_practice).on(team_practice.name.eq("teamA"))
            .fetch();
    for (Tuple tuple : result) {
        System.out.println("답"+tuple);
    }

}

    /**
     * 2. 연관관계 없는 엔티티 외부 조인
     * 예) 회원의 이름과 팀의 이름이 같은 대상 외부 조인
     * JPQL: SELECT m, t FROM Member m LEFT JOIN Team t on m.username = t.name
     * SQL: SELECT m.*, t.* FROM  Member m LEFT JOIN Team t ON m.username = t.name
     */
    @Test
    public void join_on_no_relation() throws Exception {
        em.persist(new Member_practice("teamA"));
        em.persist(new Member_practice("teamB"));
        List<Tuple> result = queryFactory
                .select(member_practice, team_practice)
                .from(member_practice)
                .leftJoin(team_practice).on(member_practice.username.eq(team_practice.name))
                .fetch();
        for (Tuple tuple : result) {
            System.out.println("t=" + tuple);
        }
    }

@PersistenceUnit
    EntityManagerFactory emf;

    @Test
    public void fetchJoinNo(){
        em.flush();
        em.clear();

        Member_practice findMember = queryFactory.selectFrom(member_practice)
                .where(member_practice.username.eq("member1")).fetchOne();
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
assertThat(loaded).as("패치조인미적용").isFalse();
    }
    @Test
    public void fetchJoinUse(){
        em.flush();
        em.clear();

        Member_practice findMember = queryFactory.selectFrom(member_practice)
                .join(member_practice.team,team_practice).fetchJoin()
                .where(member_practice.username.eq("member1")).fetchOne();
        boolean loaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());
        assertThat(loaded).as("패치조인적용").isTrue();
    }

    //서브쿼리
    //나이가 가장많은 회원 조회
    @Test
    public void subQuery(){
        QMember_practice memberSub=new QMember_practice("memberSub");

        List<Member_practice> result = queryFactory.selectFrom(member_practice).where(member_practice.age.eq(JPAExpressions.select(memberSub.age.max())
                .from(memberSub))).fetch();

        for (Member_practice memberPractice : result) {
            System.out.println("memberPractice:"+memberPractice);
        }
    }


    //서브쿼리
    //나이가 평균 이상인 회원
    @Test
    public void subQueryGoe(){
        QMember_practice memberSub=new QMember_practice("memberSub");

        List<Member_practice> result = queryFactory.selectFrom(member_practice).where(member_practice.age.goe(
                JPAExpressions.select(memberSub.age.avg())
                .from(memberSub))).fetch();

        for (Member_practice memberPractice : result) {
            System.out.println("memberage:"+memberPractice);
        }
    }
    //서브쿼리
    //서브쿼리 여러 건 처리, in 사용
    @Test
    public void subQueryIn(){
        QMember_practice memberSub=new QMember_practice("memberSub");

        List<Member_practice> result = queryFactory.selectFrom(member_practice)
                .where(member_practice.age.in(

                JPAExpressions
                        .select(memberSub.age)
                        .from(memberSub)
                        .where(memberSub.age.gt(10)) )).fetch();


    }

    @Test
    public void selectSubQuery(){
        QMember_practice memberSub=new QMember_practice("memberSub");
        List<Tuple> result = queryFactory.select(member_practice.username,
                        JPAExpressions.select(memberSub.age.avg()).from(memberSub)).from(member_practice)
                .fetch();


        for (Tuple tuple : result) {
            System.out.println(tuple);
        }
    }
}
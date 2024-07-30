package jpabook.jpashop;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Entity.Member_practice;
import jpabook.jpashop.domain.Entity.QMember_practice;
import jpabook.jpashop.domain.Entity.Team_practice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional

public class QuerydslBasicTest {

@Autowired
    EntityManager em;

@BeforeEach
    public void before() {
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
    JPAQueryFactory queryFactory=new JPAQueryFactory(em);
    QMember_practice m = new QMember_practice("m");
    queryFactory
            .select(m)
            .from(m)
            .where(m.username.eq("member1")).fetchOne();
}


}

package jpabook.jpashop.domain.Entity;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Commit
@ExtendWith(SpringExtension.class)
public class Member_practiceTest {
@Autowired
    EntityManager em;

@Test
    public void testEntity(){
    Team_practice teamA=new Team_practice("teamA");
    Team_practice teamB=new Team_practice("teamB");
    em.persist(teamA);
    em.persist(teamB);

    Member_practice member1=new Member_practice("member1",10,teamA);
    Member_practice member2=new Member_practice("member2",20,teamA);

    Member_practice member3=new Member_practice("member3",30,teamB);
    Member_practice member4=new Member_practice("member4",40,teamB);

    em.flush();
    em.clear();



}
}
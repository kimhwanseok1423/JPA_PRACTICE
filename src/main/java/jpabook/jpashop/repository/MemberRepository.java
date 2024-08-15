package jpabook.jpashop.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class MemberRepository {
@Autowired
     EntityManager em;
     JPAQueryFactory queryFactory;
    QMember member=new QMember(QMember.member);


    @PostConstruct
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }
    public void save(Member member) {
        em.persist(member);
    } public Member findOne(Long id) {

        return em.find(Member.class, id);





    }
    public List<Member> findAll() {
        queryFactory=new JPAQueryFactory(em);
        return queryFactory.selectFrom(member).fetch();
    }
    public List<Member> findByName(String name) {
        return
                queryFactory.selectFrom(member).where(member.name.eq(name)).fetch();

    }

    public List<Member> searchMembers(String name,Integer age){
        BooleanBuilder builder=new BooleanBuilder();

        if(name !=null){
            builder.and(member.name.eq(name));

        }
        if(age !=null){
            builder.and(member.age.eq(age));
        }
        return queryFactory.selectFrom(member).where(builder).fetch();
    }
}
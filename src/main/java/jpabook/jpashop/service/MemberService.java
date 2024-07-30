package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService{


    private final MemberRepository memberRepository;


// 회원가입
    @Transactional
public Long join(Member member){
    validateDuplicateMember(member); //증복회원 로직넣기
    memberRepository.save(member);
    return member.getId();

}

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }



    }
    //@Transactional(readOnly = true) ture주면 조회하는곳에서 성능최적화함

    public List<Member> findMembers(){


        return memberRepository.findAll();
    }


    public Member findOne(Long memberId){
    return memberRepository.findOne(memberId);
    }
@Transactional
    public void update(Long id, String name) {
    Member member = memberRepository.findOne(id);
    member.setName(name);


    }
}

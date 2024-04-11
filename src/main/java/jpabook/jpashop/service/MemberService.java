package jpabook.jpashop.service;

import jpabook.jpashop.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

@Autowired
    private MemberRepository memberRepository;


// 회원가입
public Long join(Member member){
    validateDuplicateMember(member); //증복회원 로직넣기
    memberRepository.save(member);
    return member.getId();

}

    private void validateDuplicateMember(Member member) {



}


}

package jpabook.jpashop.api;


import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

private final MemberService memberService;

@GetMapping("/api/v1/members")
public List<Member> membersV1(){
  return  memberService.findMembers();
}

@GetMapping("/api/v2/members")
public Result memberV2() {
    List<Member> findMembers = memberService.findMembers();
    List<MemberDTO> collect=new ArrayList<>();
    for (Member member : findMembers) {
        MemberDTO dto = new MemberDTO(member.getName(),member.getId());
        collect.add(dto);


    }
    return new Result(collect.size(),collect);
}

@Data
@AllArgsConstructor
static class Result<T>{
    private int count;
private T data;

}
@AllArgsConstructor
@Data
static class MemberDTO{
    private String name;
    private Long id;
}
@PostMapping("/api/v1/members")
public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member){
    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
}




@PostMapping("/api/v2/members")
public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request){
    Member member=new Member();
    member.setName(request.getName());


    //이렇게 하면 request가 정의가 안됬으므로 Member member 이런식으로
    //memberService.join(request);
    Long id = memberService.join(member);
    return new CreateMemberResponse(id);
}

@PutMapping("/api/v2/members/{id}")
public UpdateMemberResponse updateMemberResponse(@PathVariable("id") Long id,@RequestBody @Valid UpdateRequest request){



    memberService.update(id,request.getName());
    Member findMember = memberService.findOne(id);
    return new UpdateMemberResponse(findMember.getId(),findMember.getName());
}
@Data
    static class UpdateRequest{
    private String name;

    }

    @Data
    @AllArgsConstructor    //롬북어노테이션
    static class UpdateMemberResponse {
    private Long id;
    private String name;
    }


@Data
static class CreateMemberRequest{
    private String name;

}



@Data
    static class CreateMemberResponse {
    private Long id;

    public CreateMemberResponse(Long id) {
        this.id=id;
    }
}
}

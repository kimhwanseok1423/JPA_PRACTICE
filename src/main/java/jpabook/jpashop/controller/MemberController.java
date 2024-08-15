package jpabook.jpashop.controller;

import jpabook.jpashop.controller.MemberForm;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        //BindingResult valid하고 오류가 담기면 이런식으로 return값을 줄거야 할수있게 해주는것
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAge(form.getAge());
        member.setAddress(address);
        memberService.join(member);
        return "redirect:/";
    }

@GetMapping("/members")
    public String List(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        return "members/memberList";
    }

    @GetMapping("/membersearch")
    public String list(@RequestParam(value = "searchType", required = false) String searchType,
                       @RequestParam(value = "searchValue", required = false) String searchValue ,
                       Model model) {

        List<Member> membersearch=null;
        if ("username".equals(searchType)) {
            membersearch = memberService.findSearch(searchValue, null); // 이름으로 검색
        } else if ("age".equals(searchType)) {
            try {
                Integer age = Integer.parseInt(searchValue);
                membersearch = memberService.findSearch(null, age); // 나이로 검색
            } catch (NumberFormatException e) {
                membersearch = List.of(); // 유효하지 않은 나이 값 처리
            }
        } else {
            membersearch = List.of(); // 잘못된 검색 기준 처리
        }

        List<Member> members = memberService.findMembers();
        model.addAttribute("members",members);
        model.addAttribute("membersearch", membersearch);


        return "members/memberList";
}}
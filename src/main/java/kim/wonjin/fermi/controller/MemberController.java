package kim.wonjin.fermi.controller;

import kim.wonjin.fermi.domain.Member;
import kim.wonjin.fermi.service.MvcMemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // 스프링 컨테이너가 bean 생성해서 관리
@RequestMapping("/members")
public class MemberController {

    private final MvcMemberService memberService;

    @Autowired
    public MemberController(MvcMemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    ModelMapper modelMapper;


    @PutMapping("/init")
    @ResponseBody
    public MemberResponse initMember() {
        Member member = memberService.createMember(new Member());
        return modelMapper.map(member, MemberResponse.class);
    }

    @Data
    @AllArgsConstructor
    static class MemberResponse {
        private Long id;
        private String username;
    }

}

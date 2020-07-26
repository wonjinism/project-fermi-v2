package kim.wonjin.fermi.api;

import kim.wonjin.fermi.domain.Member;
import kim.wonjin.fermi.repository.MemberRepository;
import kim.wonjin.fermi.service.ChromeExtensionMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    @Autowired
    ChromeExtensionMemberService chromeExtensionMemberService;

    @Autowired
    MemberRepository memberRepository;

    @PostMapping
    public ResponseEntity createMember(@RequestBody Member member, Errors errors) {
        // 에러 체크
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
        Member createdMember = chromeExtensionMemberService.createMember(member);
        return ResponseEntity.ok().body(createdMember);
    }

    @GetMapping("/{id}")
    public ResponseEntity getMember(@PathVariable("id") Member member, Errors errors) {
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().body(member);
    }

    @PutMapping
    public ResponseEntity updateMember(@RequestBody Member member, Errors errors) {
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }
        Member updatedMember = memberRepository.save(member);
        return ResponseEntity.ok().body(updatedMember);
    }

}

package kim.wonjin.fermi.service;

import kim.wonjin.fermi.domain.Member;
import kim.wonjin.fermi.enums.UserType;
import kim.wonjin.fermi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class MvcMemberService implements MemberService<Member, Long> {

    private final MemberRepository memberRepository;

    @Autowired
    public MvcMemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public Member createMember(Member member) {
        memberRepository.findByUsername(member.getUsername())
                .ifPresent(m -> {
                   throw new IllegalStateException("존재하는 유저네임");
                });
        // 이메일 없는 init 타입
        if(member.getEmail() == null || member.getEmail().trim().isEmpty()) {
            member.setUserType(UserType.ANONYMOUS);
            memberRepository.save(member);
            // Persistent 상태로 update 처리되며, savedMember와 member는 동일함
            member.setUsername("Guest" + member.getId());
        } else {
            memberRepository.findByEmail(member.getEmail())
                    .ifPresent(m -> {
                       throw new IllegalStateException("존재하는 이메일");
                    });
            member.setUserType(UserType.MEMBER);
            memberRepository.save(member);
        }
        return member;
    }

    @Override
    public Member updateMember(Member member) {
        return memberRepository.save(member);
    }

    // 논리 삭제
    @Override
    public Member deleteMember(Member member) {
        Optional<Member> targetMember =
                memberRepository.findByIdAndUsername(member.getId(), member.getUsername());
        if (!targetMember.isPresent()) {
            throw new IllegalStateException("잘못된 유저 정보");
        };
        // 이메일 없는 init 타입
        member.setUseYn("N");
        return memberRepository.save(member);
    }
}

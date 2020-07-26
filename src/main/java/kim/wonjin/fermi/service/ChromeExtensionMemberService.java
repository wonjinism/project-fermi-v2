package kim.wonjin.fermi.service;

import kim.wonjin.fermi.domain.Member;
import kim.wonjin.fermi.enums.UserType;
import kim.wonjin.fermi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChromeExtensionMemberService implements MemberService<Member, Long>{

    @Autowired
    MemberRepository memberRepository;

    /**
     * 익명 멤버 등록
     * - 기본값 username = Guest + {id}
     */
    @Override
    public Member createMember(Member member) {
        member.setUserType(UserType.ANONYMOUS);
        Member savedMember = memberRepository.save(member);
        savedMember.setUsername("Guest" + savedMember.getId());
        return savedMember;
    }

    @Override
    public Member updateMember(Member member) {
        if (!member.getEmail().isEmpty()) {
            member.setUserType(UserType.MEMBER);
        }
        return memberRepository.save(member);
    }

    @Override
    public Member deleteMember(Member member) {
        return null;
    }
}

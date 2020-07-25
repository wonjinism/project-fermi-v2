package kim.wonjin.fermi.service;

import kim.wonjin.fermi.domain.Member;
import kim.wonjin.fermi.enums.UserType;
import kim.wonjin.fermi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberService {

    @Autowired
    MemberRepository memberRepository;

    /**
     * 멤버 등록
     * - 닉네임 필수
     * - 이메일 없으면 무명유저
     */
    public Member createMember(Member member){
        UserType userType = member.getEmail().isEmpty() ? UserType.ANONYMOUS : UserType.MEMBER;
        member.setUserType(userType);
        return memberRepository.save(member);
    }
}

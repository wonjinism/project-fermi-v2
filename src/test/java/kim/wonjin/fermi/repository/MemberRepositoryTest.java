package kim.wonjin.fermi.repository;

import kim.wonjin.fermi.common.TestDescription;
import kim.wonjin.fermi.domain.Member;
import kim.wonjin.fermi.service.MvcMemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    // JPA Repository의 삭제가 member 전체가 일치해야 삭제되는지 테스트 해보자.
    @Test
    @TestDescription("JpaRepository.delete() key check")
    public void delete() {
        // given
        Member member = new Member();
        member.setEmail("wonjinism@gmail.com");
        member.setUsername("Simon");
        member.setPassword("password");
        memberRepository.save(member);

        // when
        Member targetMember = Member.builder()
                .id(member.getId())
                .build();

        memberRepository.delete(targetMember);
        Optional<Member> deletedMember = memberRepository.findById(member.getId());

        // then
        System.out.println("=============");
        System.out.println(deletedMember.isPresent());
        assertThat(deletedMember.isPresent()).isFalse();
    }

}
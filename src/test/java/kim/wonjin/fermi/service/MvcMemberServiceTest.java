package kim.wonjin.fermi.service;

import kim.wonjin.fermi.common.TestDescription;
import kim.wonjin.fermi.domain.Member;
import kim.wonjin.fermi.enums.UserType;
import kim.wonjin.fermi.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MvcMemberServiceTest {

    @Autowired MvcMemberService mvcMemberService;
    @Autowired MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() { }
    @AfterEach
    public void afterEach() { }

    @Test
    @TestDescription("#create-init")
    public void initCreateMember(){
        // Given
        Member member = new Member();
        // When
        Member createdMember = mvcMemberService.createMember(member);
        // Then
        // TODO getOne으로 조회가 되지 않는데, findById는 가능. 무슨 차이가 있는지 학습 필요
//        Member assertmember = memberRepository.getOne(createdMember.getId());
        Optional<Member> optionalMember = memberRepository.findById(createdMember.getId());
        Member assertMember = optionalMember.get();
        assertThat(assertMember).isNotNull();
        assertThat(assertMember.getId()).isEqualTo(createdMember.getId());
        assertThat(assertMember.getUserType()).isEqualTo(UserType.ANONYMOUS);
        assertThat(assertMember.getUsername()).isEqualTo("Guest"+assertMember.getId());

        // Then
        assertThat(createdMember).isNotNull();
        assertThat(createdMember.getId()).isEqualTo(createdMember.getId());
        assertThat(createdMember.getUserType()).isEqualTo(UserType.ANONYMOUS);
        assertThat(createdMember.getUsername()).isEqualTo("Guest"+createdMember.getId());
        assertThat(createdMember.getUseYn()).isEqualTo("Y");
    }

    @Test
    @TestDescription("#create-join")
    public void joinCreateMember(){
        // Given
        // 빌더로 생성하면 필드값이 초기화되지 않는다.
//        Member member = Member.builder()
//                .email("wonjinism@gmail.com")
//                .username("Simon")
//                .password("password")
//                .build();

        // Given
        Member member = new Member();
        member.setEmail("wonjinism@gmail.com");
        member.setUsername("Simon");
        member.setPassword("password");

        // When
        Member createdMember = mvcMemberService.createMember(member);

        // Then
        assertThat(createdMember).isNotNull();
        assertThat(createdMember.getId()).isEqualTo(createdMember.getId());
        assertThat(createdMember.getUserType()).isEqualTo(UserType.MEMBER);
        assertThat(createdMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(createdMember.getPassword()).isEqualTo(member.getPassword());
        assertThat(createdMember.getUseYn()).isEqualTo("Y");

        // given - duplicate email
        Member member2 = new Member();
        member2.setEmail("wonjinism@gmail.com");
        member2.setUsername("Simon2");
        member2.setPassword("password2");
        // When
        try {
            mvcMemberService.createMember(member2);
            fail();
        } catch (IllegalStateException e) {
        // Then
        // java.lang.IllegalStateException: 존재하는 이메일
            assertThat(e.getMessage()).isEqualTo("존재하는 이메일");
        }

        // given - duplicate username
        Member member3 = new Member();
        member3.setEmail("simon@ui2.co.jp");
        member3.setUsername("Simon");
        member3.setPassword("password3");
        // When
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> mvcMemberService.createMember(member3));
        assertThat(e.getMessage()).isEqualTo("존재하는 유저네임");
    }

    @Test
    @TestDescription("update-normal") // UserType.MEMBER only 업데이트 가능
    public void updateMember() {
        // given
        Member newMember = createNewMemberForTest();

        Member targetMember = new Member();
        targetMember.setId(newMember.getId());
        targetMember.setEmail(newMember.getEmail());
        targetMember.setUsername("Simon");
        targetMember.setPassword("password");
    }

    @Test
    @TestDescription("update-wrong email") // 화면에서 생길 수 없는 케이스이지만 대응
    public void updateMemberWrongEmail(){
        // id와 같이 체크해줘야 하지 않을까? return값에 id가 포함되어서 session에서 관리되어야 하겠다.
        // id를 의도적으로 다르게 보낸 경우도 대응 되겠네.
        Member newMember = createNewMemberForTest();
    }

    @Test
    @TestDescription("update-wrong password") // 화면에서 ajax처리 해줄 예정이라 생길 수 없는 케이스일테지만 대응
    public void updateMemberWrongPassword() {
        Member newMember = createNewMemberForTest();
    }


    @Test
    @TestDescription("delete-normal")
    public void deleteMember() {
        // given
        Member newMember = createNewMemberForTest();

        // when
        mvcMemberService.deleteMember(newMember);

        // then
        // TODO JPA repository delete가 ID만 체크해서 삭제 하기 때문에 id와 email을 체크하는 로직 추가 필요
        assertThat(newMember.getUseYn()).isEqualTo("N");
        Optional<Member> deletedMember = memberRepository.findById(newMember.getId());
        assertThat(deletedMember.get().getUseYn()).isEqualTo("N");
    }

    @Test
    @TestDescription("delete-wrong email")
    public void deleteMemberWrongEmail(){
        // given
        Member newMember = createNewMemberForTest();
        Member targetMember = Member.builder()
                .id(newMember.getId())
                .email("wrong@gmail.com")
                .build();

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> mvcMemberService.deleteMember(targetMember));
        assertThat(e.getMessage()).isEqualTo("잘못된 유저 정보");
    }

    public Member createNewMemberForTest() {
        Member member = new Member();
        member.setEmail("wonjinism@gmail.com");
        member.setUsername("Simon");
        member.setPassword("password");
        return mvcMemberService.createMember(member);
    }
}
package kim.wonjin.fermi.common;

import kim.wonjin.fermi.domain.Member;
import kim.wonjin.fermi.domain.Question;
import kim.wonjin.fermi.repository.QuestionRepository;
import kim.wonjin.fermi.repository.MemberRepository;
import kim.wonjin.fermi.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class CommonTestUtil {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    QuestionRepository questionRepository;

    public Member createAdmin() {
        Member member = Member.builder()
                .username("ADMIN")
                .email("wonjinism@gmail.com")
                .userType(UserType.ADMIN)
                .build();

        return memberRepository.save(member);
    }

    public Member createAnonymous(String username) {
        Member member = Member.builder()
                .username(username)
                .userType(UserType.ANONYMOUS)
                .build();

        return memberRepository.save(member);
    }

    public Question createQuestion(Member member) {
        Question question = Question.builder()
                .createdBy(member)
                .questionContent("시카고 피아노 조율사 숫자는?")
                .build();

        if(member.getQuestions() == null) {
            member.setQuestions(new ArrayList<>());
        }
        member.getQuestions().add(question);

        return questionRepository.save(question);
    }
}

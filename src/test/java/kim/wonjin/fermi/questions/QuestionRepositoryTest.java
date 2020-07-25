package kim.wonjin.fermi.questions;

import kim.wonjin.fermi.common.CommonTestUtil;
import kim.wonjin.fermi.common.TestConfig;
import kim.wonjin.fermi.common.TestDescription;
import kim.wonjin.fermi.domain.Member;
import kim.wonjin.fermi.domain.Question;
import kim.wonjin.fermi.repository.QuestionRepository;
import kim.wonjin.fermi.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@Import(TestConfig.class)
public class QuestionRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    CommonTestUtil tu;

    @Test
    @TestDescription("정상계")
    public void save() {
        tu.createAdmin();

        Optional<Member> optionalUser = memberRepository.findById(1l);
        Member admin = optionalUser.get();

        Question question = new Question();
        question.setCreatedBy(admin);
        question.setQuestionContent("시카고 피아노 조율사 숫자는?");

        // 빌더 사용하면 초기화 되지 않음.
//        Question question = Question.builder()
//                .user(admin)
//                .questionContent("시카고 피아노 조율사 숫자는?")
//                .build();

        admin.setQuestions(new ArrayList<>());
        admin.getQuestions().add(question);

        // 유저는 저장해주지 않아도, 질문을 저장 해주면 알아서 처리가 되네?
//        userRepository.save(admin);
        Question savedQuestion = questionRepository.save(question);

        System.out.println("==============");
        System.out.println(savedQuestion.getId());
        assertThat(savedQuestion.getId()).isNotNull();

        List<Question> questions = questionRepository.findAll();
        assertThat(questions).isNotNull();
        assertThat(questions.size()).isEqualTo(1);
        assertThat(questions.get(0).getCreatedBy().getId()).isEqualTo(1);

        // admin
        Optional<Member> foundUser = memberRepository.findById(1l);
        assertThat(foundUser.isPresent());
        assertThat(foundUser.get().getQuestions().size()).isEqualTo(1);

    }
}
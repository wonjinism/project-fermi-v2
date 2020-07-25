package kim.wonjin.fermi.service;

import kim.wonjin.fermi.domain.Member;
import kim.wonjin.fermi.domain.Question;
import kim.wonjin.fermi.enums.QuestionStatus;
import kim.wonjin.fermi.enums.QuestionType;
import kim.wonjin.fermi.enums.UserType;
import kim.wonjin.fermi.repository.QuestionRepository;
import kim.wonjin.fermi.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
@Transactional
public class QuestionService {

    @Autowired QuestionRepository questionRepository;
    @Autowired
    MemberRepository memberRepository;

    public Page<Question> getQuestions(Pageable pageable) {
        Page<Question> questions = questionRepository.findAll(pageable);
        return questions;
    }

    public Question saveQuestion(Question question) {
        // 멤버 타입 체크
        Member member = question.getCreatedBy();
        if(member.getUserType().equals(UserType.ADMIN)){
            question.setQuestionType(QuestionType.ADMIN_CREATED);
            question.setQuestionStatus(QuestionStatus.APPROVED);
        } else {
            question.setQuestionType(QuestionType.USER_CREATED);
            question.setQuestionStatus(QuestionStatus.REQUESTED);
        }
        return questionRepository.save(question);
    }
}

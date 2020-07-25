package kim.wonjin.fermi.controller;

import kim.wonjin.fermi.domain.Question;
import kim.wonjin.fermi.enums.QuestionStatus;
import kim.wonjin.fermi.enums.QuestionType;
import kim.wonjin.fermi.service.QuestionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public Page<Question> getQuestions(Pageable pageable) {
        return questionService.getQuestions(pageable);
    }

    @PostMapping
    public ResponseEntity createQuestion(@RequestBody Question question, Errors errors) {
        // 에러 체크
        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors);
        }

        Question savedQuestion = questionService.saveQuestion(question);
        CreateQuestionResponse createQuestionResponse =
                modelMapper.map(savedQuestion, CreateQuestionResponse.class);

        URI createdUri = linkTo(QuestionController.class).slash(savedQuestion.getId()).toUri();

        return ResponseEntity.created(createdUri).body(new Result(createQuestionResponse));
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    @AllArgsConstructor @NoArgsConstructor
    static class CreateQuestionResponse {
        private Long id;
        private QuestionType questionType;
        private QuestionStatus questionStatus;
    }
}

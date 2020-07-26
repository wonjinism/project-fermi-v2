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
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping("/questions")
public class QuestionController {

}

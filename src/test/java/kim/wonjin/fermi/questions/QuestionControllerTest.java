package kim.wonjin.fermi.questions;

import com.fasterxml.jackson.databind.ObjectMapper;
import kim.wonjin.fermi.common.CommonTestUtil;
import kim.wonjin.fermi.common.TestConfig;
import kim.wonjin.fermi.common.TestDescription;
import kim.wonjin.fermi.domain.Member;
import kim.wonjin.fermi.domain.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // 테스트 application-test.properties 사용할 경우
@Import(TestConfig.class)
public class QuestionControllerTest {

    @Autowired MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @Autowired
    CommonTestUtil tu;

    @Test
    @TestDescription("정상계")
    public void createQuestion() throws Exception {
        Member savedMember = tu.createAdmin();
//        Question savedQuestion = tu.createQuestion(savedAccount);

        Question savedQuestion = Question.builder()
                .createdBy(savedMember)
                .questionContent("시카고 피아노 조율사 숫자는?")
                .build();

        mockMvc.perform(post("/api/questions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .content(objectMapper.writeValueAsString(savedQuestion)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
                ;
    }
}
package kim.wonjin.fermi.domain;

import kim.wonjin.fermi.enums.QuestionStatus;
import kim.wonjin.fermi.enums.QuestionType;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "fermi_question")
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @Enumerated(EnumType.STRING)
    private QuestionStatus questionStatus;

    private String questionTitle;

    @Lob
    private String questionContent;

    private String sourceTitle;

    private String sourceUrl;

    private int likeCount = 0;

    @ManyToOne
    private Member createdBy;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionLike> questionLikes = new ArrayList<>();
}

package kim.wonjin.fermi.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "fermi_answer")
public class Answer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Question question;

    @ManyToOne
    private Member createdBy;

    @Lob
    private String answerContent;

}

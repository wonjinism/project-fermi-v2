package kim.wonjin.fermi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
// 모든 필드를 다 사용하는데 엔티티 간에 연관관계가 상호참조관계가 되버리면 코드 안에서 스택오버플로우가 발생할 수 있음.
// 다른 엔티티와의 묶음을 만드는 것은 좋지 않다. 상호참조 때문에 구현체에서 스택오버플로우 발생할 수 있음
@EqualsAndHashCode(of = "id")
@Entity(name = "fermi_questionlike")
public class QuestionLike {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Question question;

}

package kim.wonjin.fermi.domain;

import kim.wonjin.fermi.enums.UserType;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "fermi_member")
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "createdBy")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy")
    private List<Answer> answers = new ArrayList<>();

    @Column(length = 1)
    private String useYn = "Y";
}

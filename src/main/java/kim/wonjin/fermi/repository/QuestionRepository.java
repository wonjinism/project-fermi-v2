package kim.wonjin.fermi.repository;

import kim.wonjin.fermi.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}

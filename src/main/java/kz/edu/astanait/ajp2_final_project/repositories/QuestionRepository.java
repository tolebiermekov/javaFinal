package kz.edu.astanait.ajp2_final_project.repositories;

import kz.edu.astanait.ajp2_final_project.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findByQuestion(String question);
    boolean existsByQuestion(String question);
}

package kz.edu.astanait.ajp2_final_project.repositories;

import kz.edu.astanait.ajp2_final_project.models.Question;
import kz.edu.astanait.ajp2_final_project.models.User;
import kz.edu.astanait.ajp2_final_project.models.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository

public interface VoteRepository extends JpaRepository<Vote, Long> {
    List<Vote> findAllByQuestion(Question question);
    boolean existsByQuestionAndUser(Question question, User user);

    @Transactional
    @Modifying
    @Query(value = "UPDATE votes SET answer = :answer WHERE question_id = :questionId AND user_id = :userId",nativeQuery = true)
    void updateVote(String answer, Long questionId, Long userId);
}

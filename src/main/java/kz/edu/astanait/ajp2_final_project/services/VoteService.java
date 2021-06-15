package kz.edu.astanait.ajp2_final_project.services;

import kz.edu.astanait.ajp2_final_project.models.Question;
import kz.edu.astanait.ajp2_final_project.models.User;
import kz.edu.astanait.ajp2_final_project.models.Vote;
import kz.edu.astanait.ajp2_final_project.repositories.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class VoteService {
    private final VoteRepository voteRepository;

    @Autowired
    public VoteService(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    public void saveVote(Vote vote) {
        voteRepository.save(vote);
    }

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public List<Vote> getByQuestion(Question question){
        return voteRepository.findAllByQuestion(question);
    }

    public boolean existsByQuestionAndUser(Question question, User user){
        return voteRepository.existsByQuestionAndUser(question,user);
    }

    public void updateVote(String answer, Long questionId, Long userId){
        voteRepository.updateVote(answer,questionId,userId);
    }
}

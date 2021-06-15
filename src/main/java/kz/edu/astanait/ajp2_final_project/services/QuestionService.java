package kz.edu.astanait.ajp2_final_project.services;

import kz.edu.astanait.ajp2_final_project.models.Question;
import kz.edu.astanait.ajp2_final_project.repositories.AnswerRepository;
import kz.edu.astanait.ajp2_final_project.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository){
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public void addQuestion(Question question){
        if (questionRepository.existsByQuestion(question.getQuestion())){
            return;
        }
        questionRepository.save(question);
        answerRepository.save(question.getAnswer());
    }

    public List<Question> getAll(){
        return questionRepository.findAll();
    }

    public Question getQuestionById(long id){
        return questionRepository.getOne(id);
    }

    public Question getQuestionByName(String question){
        return questionRepository.findByQuestion(question);
    }

    public void updateQuestion(Question question){
        questionRepository.save(question);
        answerRepository.save(question.getAnswer());
    }

    public void deleteQuestion(Long id){
        questionRepository.deleteById(id);
    }
}

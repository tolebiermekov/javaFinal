package kz.edu.astanait.ajp2_final_project.rest;


import kz.edu.astanait.ajp2_final_project.models.Question;
import kz.edu.astanait.ajp2_final_project.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionAPI {
    private final QuestionService questionService;

    @Autowired
    public QuestionAPI(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/getAll")
    public @ResponseBody
    List<Question> getAll(){
        return questionService.getAll();
    }
}

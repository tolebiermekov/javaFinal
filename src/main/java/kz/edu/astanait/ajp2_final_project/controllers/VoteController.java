package kz.edu.astanait.ajp2_final_project.controllers;

import kz.edu.astanait.ajp2_final_project.models.*;
import kz.edu.astanait.ajp2_final_project.services.AnswerService;
import kz.edu.astanait.ajp2_final_project.services.QuestionService;
import kz.edu.astanait.ajp2_final_project.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class VoteController {
    private QuestionService questionService;
    private AnswerService answerService;
    private VoteService voteService;

    @Autowired
    public VoteController(QuestionService questionService, AnswerService answerService, VoteService voteService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.voteService = voteService;
    }

    @GetMapping("/vote")
    public ModelAndView showVote(){
        ModelAndView mav = new ModelAndView("vote");
        List<Question> questions = questionService.getAll();
        mav.addObject("votesList", questions);
        return mav;
    }

    @PostMapping("/vote")
    public String addVote(HttpServletRequest request, HttpServletRequest httpServletRequest){
        List<Question> questions = questionService.getAll();
        Question question;
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        for (int i = 0; i < questions.size(); i++) {
            Vote vote = new Vote();
            question = questionService.getQuestionByName(questions.get(i).getQuestion());
            vote.setQuestion(question);
            vote.setAnswer(request.getParameter(questions.get(i).getQuestion()));
            vote.setUser(user);

            if(voteService.existsByQuestionAndUser(question,user)){
                voteService.updateVote(vote.getAnswer(),vote.getQuestion().getId(),vote.getUser().getId());
            }else{
                voteService.saveVote(vote);
            }
        }
        return "redirect:/index";
    }

    @GetMapping("/question/add")
    public ModelAndView showQuestionAddPage() {
        ModelAndView mav = new ModelAndView("addQuestion");
        Question question = new Question();
        question.setAnswer(new Answer());
        mav.addObject("addQuestionForm", question);
        return mav;
    }

    @PostMapping("/question/add")
    public String addQuestion(@ModelAttribute("addQuestionForm") Question questionForm) {
        questionService.addQuestion(questionForm);
        return "redirect:/admin";
    }

    @GetMapping("/question/update/{id}")
    public String updateQuestion(@PathVariable(value = "id") long id, Model model) {
        Question question = questionService.getQuestionById(id);
        model.addAttribute("questionToEdit", question);
        return "updateQuestion";
    }

    @PostMapping("/question/update/")
    public String updateQuestion(HttpServletRequest request) {
        Question question = new Question();
        Answer answer = new Answer();

        answer.setAnswerId(Long.valueOf(request.getParameter("answerId")));
        answer.setAnswerOne(request.getParameter("answerOne"));
        answer.setAnswerTwo(request.getParameter("answerSecond"));
        answer.setAnswerThree(request.getParameter("answerThree"));
        answer.setAnswerFour(request.getParameter("answerFour"));

        question.setId(Long.valueOf(request.getParameter("questionId")));
        question.setQuestion(request.getParameter("questionName"));
        question.setAnswer(answer);

        questionService.updateQuestion(question);

        return "redirect:/admin";
    }

    @GetMapping("/question/delete/{id}")
    public String deleteQuestion(@PathVariable(value = "id") long id) {
        questionService.deleteQuestion(id);

        return "redirect:/admin";
    }

}
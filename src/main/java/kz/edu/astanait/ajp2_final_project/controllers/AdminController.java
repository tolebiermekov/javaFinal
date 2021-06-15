package kz.edu.astanait.ajp2_final_project.controllers;

import kz.edu.astanait.ajp2_final_project.models.Question;
import kz.edu.astanait.ajp2_final_project.models.User;
import kz.edu.astanait.ajp2_final_project.models.Vote;
import kz.edu.astanait.ajp2_final_project.services.AnswerService;
import kz.edu.astanait.ajp2_final_project.services.QuestionService;
import kz.edu.astanait.ajp2_final_project.services.UserService;
import kz.edu.astanait.ajp2_final_project.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private VoteService voteService;
    @Autowired
    private AnswerService answerService;

    @GetMapping(value = {"", "/"})
    public String viewList(Model model) {
        model.addAttribute("listUsers", userService.getAllUsers());
        model.addAttribute("listQuestions", questionService.getAll());
        return "admin";
    }

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {

        // call delete user method for ADMIN
        this.userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") long id, Model model) {

        // get user from the service
        User user = userService.getUserById(id);

        // set user as a model attribute to pre-populate the form
        model.addAttribute("user", user);
        return "update_user";
    }

    @GetMapping("/showQuestions")
    public String showQuestions(Model model) {
        List<Question> questions = questionService.getAll();
        model.addAttribute("questionList",questions);
        return "questionList";
    }

    @GetMapping("/showStats")
    public String showStats(Model model){
        Integer sum=0,a=0,b=0,aPercentage=0,bPercentage=0;
        List<User> users = userService.getAllUsers();
        Map<String,Integer> genderMapping = new HashMap<>();

        for (User u:users) {
            if(u.getGender().equalsIgnoreCase("male")){
                a++;
            }else{
                b++;
            }
        }

        sum = a + b;
        aPercentage = (a * 100)/sum;
        bPercentage = (b * 100)/sum;

        genderMapping.put("Male",aPercentage);
        genderMapping.put("Female",bPercentage);

        model.addAttribute("genders",genderMapping);
        model.addAttribute("users",users);

        return "statistics";
    }

    @GetMapping("/statistics/{id}")
    public String showStats(@PathVariable Long id,Model model){
        Map<String,Integer> answerMapping = new HashMap<>();
        Question question = questionService.getQuestionById(id);
        List<Vote> votes = voteService.getByQuestion(question);
        Integer a=0,b=0,c=0,d=0,sum=0,aPercentage=0,bPercentage=0,cPercentage=0,dPercentage=0;

        for (int i = 0; i < votes.size(); i++) {
            if(votes.get(i).getAnswer().equals(question.getAnswer().getAnswerOne())){
                a++;
            }
            if(votes.get(i).getAnswer().equals(question.getAnswer().getAnswerTwo())){
                b++;
            }
            if(votes.get(i).getAnswer().equals(question.getAnswer().getAnswerThree())){
                c++;
            }
            if(votes.get(i).getAnswer().equals(question.getAnswer().getAnswerFour())){
                d++;
            }
        }
        sum = a + b + c + d;

        aPercentage = (a * 100)/sum;
        bPercentage = (b * 100)/sum;
        cPercentage = (c * 100)/sum;
        dPercentage = (d * 100)/sum;

        answerMapping.put(question.getAnswer().getAnswerOne(),aPercentage);
        answerMapping.put(question.getAnswer().getAnswerTwo(),bPercentage);
        answerMapping.put(question.getAnswer().getAnswerThree(),cPercentage);
        answerMapping.put(question.getAnswer().getAnswerFour(),dPercentage);

        model.addAttribute("question",question);
        model.addAttribute("answerMapping",answerMapping);

        return "questionStats";
    }
}

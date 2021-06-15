package kz.edu.astanait.ajp2_final_project.rest;

import kz.edu.astanait.ajp2_final_project.dto.UserApiDTO;
import kz.edu.astanait.ajp2_final_project.models.User;
import kz.edu.astanait.ajp2_final_project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserAPI {

    private final UserService userService;

    @Autowired
    public UserAPI(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getByUsername/{username}")
    public @ResponseBody
    User getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/getByUsernameForAndroid/{username}")
    public @ResponseBody
    UserApiDTO getByUsernameForAndroid(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return new UserApiDTO(user.getId(), username, user.getPassword());
    }

    @GetMapping("/checkExistence/{username}/{password}")
    public @ResponseBody
    boolean existsByUsernameAndPassword(@PathVariable String username,
                                        @PathVariable String password) {
        return userService.existsForUserAPI(username, password);
    }

}

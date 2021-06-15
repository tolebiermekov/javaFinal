package kz.edu.astanait.ajp2_final_project.services;

import kz.edu.astanait.ajp2_final_project.models.Role;
import kz.edu.astanait.ajp2_final_project.models.User;
import kz.edu.astanait.ajp2_final_project.repositories.RoleRepository;
import kz.edu.astanait.ajp2_final_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleRepository.findByName("USER"));

        userRepository.save(user);
    }

    public void update(User user){

        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        Optional<User> optional = userRepository.findById(id);
        User user;

        if (optional.isPresent()) {
            user = optional.get();
        } else {
            throw new RuntimeException(" User not found for id :: " + id);
        }
        return user;
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }

    public boolean existsForUserAPI(String username, String password) {
        User user = findByUsername(username);
        return user!=null && passwordEncoder.matches(password, user.getPassword());
    }
}

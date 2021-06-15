package kz.edu.astanait.ajp2_final_project.services;

import kz.edu.astanait.ajp2_final_project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("myUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        kz.edu.astanait.ajp2_final_project.models.User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("User with username " + username + " not found");
        return getAuthenticatedUser(user);
    }

    private User getAuthenticatedUser(kz.edu.astanait.ajp2_final_project.models.User user) {
        return new User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                user.getGrantedAuthorities());
    }
}

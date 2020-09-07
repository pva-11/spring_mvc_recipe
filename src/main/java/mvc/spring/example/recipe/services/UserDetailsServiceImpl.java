package mvc.spring.example.recipe.services;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.example.recipe.model.MyAppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MyAppUserService myAppUserService;

    public UserDetailsServiceImpl(MyAppUserService myAppUserService) {
        this.myAppUserService = myAppUserService;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("Start of loading the User");
        MyAppUser myAppUser = myAppUserService.getMyAppUserByUsername(s);
        if (myAppUser == null) {
            throw new UsernameNotFoundException("Couldn't find User with username: " + s);
        }
        log.info("User loaded with name: " + myAppUser.getEmail());

        return User.withDefaultPasswordEncoder()
                .username(myAppUser.getUsername())
                .password(myAppUser.getPassword())
                .roles(myAppUser.getUserRole().name())
                .build();

    }
}

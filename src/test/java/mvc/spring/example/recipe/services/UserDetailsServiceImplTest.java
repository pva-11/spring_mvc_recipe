package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.model.MyAppUser;
import mvc.spring.example.recipe.model.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final UserRole USER_ROLE = UserRole.USER;
    private static final int EXPECTED_SIZE = 1;

    @Mock
    MyAppUserService myAppUserService;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    public void testNotLoadUserByUsername() {
        when(myAppUserService.getMyAppUserByUsername(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(USERNAME));
    }

    @Test
    public void testLoadUserByUsername() {

        MyAppUser testUser = new MyAppUser(USERNAME, PASSWORD, "", "", USER_ROLE);

        when(myAppUserService.getMyAppUserByUsername(anyString())).thenReturn(testUser);

        UserDetails user = userDetailsService.loadUserByUsername(USERNAME);

        assertThat(user).isNotNull();
        assertThat(user.getPassword()).isNotEmpty();
        assertThat(user.getUsername()).isEqualTo(USERNAME);
        assertThat(user.getAuthorities().size()).isEqualTo(EXPECTED_SIZE);

    }
}
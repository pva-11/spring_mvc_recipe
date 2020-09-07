package mvc.spring.example.recipe.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MyAppUser {

    public MyAppUser(String username, String password, String email, String phone, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.userRole = userRole;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String phone;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

}

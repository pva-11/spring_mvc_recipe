package mvc.spring.example.recipe.repositories;

import mvc.spring.example.recipe.model.MyAppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MyAppUserRepository extends CrudRepository<MyAppUser, Long> {

    Optional<MyAppUser> findByUsername(String username);

}

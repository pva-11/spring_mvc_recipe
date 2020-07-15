package mvc.spring.example.recipe.repositories;

import mvc.spring.example.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}

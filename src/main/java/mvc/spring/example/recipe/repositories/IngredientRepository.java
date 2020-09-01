package mvc.spring.example.recipe.repositories;

import mvc.spring.example.recipe.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Long> {

}

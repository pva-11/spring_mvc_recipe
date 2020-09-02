package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.commands.RecipeCommand;
import mvc.spring.example.recipe.model.Recipe;

import java.util.List;
import java.util.Set;

public interface RecipeService {

    List<Recipe> getAllRecipes();

    Recipe getById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand command);

    RecipeCommand findCommandByID(Long ID);

    void deleteById(Long ID);

}

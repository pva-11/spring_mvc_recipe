package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getAllRecipes();

}

package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.model.Recipe;
import mvc.spring.example.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getAllRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe getById(Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (!optionalRecipe.isPresent()) {
            throw new NoSuchElementException("Couldn't find the recipe by id: " + id);
        }
        return optionalRecipe.get();
    }
}

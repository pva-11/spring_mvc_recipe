package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.commands.RecipeCommand;
import mvc.spring.example.recipe.converters.RecipeCommandToRecipe;
import mvc.spring.example.recipe.converters.RecipeToRecipeCommand;
import mvc.spring.example.recipe.model.Recipe;
import mvc.spring.example.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
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

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findCommandByID(Long id) {
        Optional<Recipe> recipeToUpdate = recipeRepository.findById(id);
        if (!recipeToUpdate.isPresent()) {
            throw new NoSuchElementException("Couldn't find the recipe by id: " + id);
        }
        return recipeToRecipeCommand.convert(recipeToUpdate.get());
    }

    @Override
    @Transactional
    public void deleteById(Long ID) {
        recipeRepository.deleteById(ID);
    }
}

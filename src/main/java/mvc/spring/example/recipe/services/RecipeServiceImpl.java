package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.commands.RecipeCommand;
import mvc.spring.example.recipe.converters.RecipeCommandToRecipe;
import mvc.spring.example.recipe.converters.RecipeToRecipeCommand;
import mvc.spring.example.recipe.model.Recipe;
import mvc.spring.example.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    @Transactional(readOnly = true)
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe getById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Couldn't find the recipe by id: " + id));
        return recipe;
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeCommand findCommandByID(Long id) {
        Recipe recipeToUpdate = recipeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Couldn't find the recipe by id: " + id));
        return recipeToRecipeCommand.convert(recipeToUpdate);
    }

    @Override
    @Transactional
    public void deleteById(Long ID) {
        recipeRepository.deleteById(ID);
    }
}

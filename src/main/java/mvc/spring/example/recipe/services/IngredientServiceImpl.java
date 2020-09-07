package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.commands.IngredientCommand;
import mvc.spring.example.recipe.converters.IngredientCommandToIngredient;
import mvc.spring.example.recipe.converters.IngredientToIngredientCommand;
import mvc.spring.example.recipe.model.Ingredient;
import mvc.spring.example.recipe.model.Recipe;
import mvc.spring.example.recipe.repositories.IngredientRepository;
import mvc.spring.example.recipe.repositories.RecipeRepository;
import mvc.spring.example.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;


    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {

        Optional<Recipe> optionalRecipeById = recipeRepository.findById(recipeId);
        if (!optionalRecipeById.isPresent()) {
            throw new NoSuchElementException("Couldn't find the recipe by id: " + recipeId);
        }
        Recipe recipeById = optionalRecipeById.get();

        Optional<IngredientCommand> optionalIngredientCommand = recipeById.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
        if (!optionalIngredientCommand.isPresent()) {
            throw new NoSuchElementException("Couldn't find the ingredient by id: " + ingredientId);
        }
        return optionalIngredientCommand.get();

    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (!optionalRecipe.isPresent()) {
            throw new NoSuchElementException("Couldn't find the recipe by id: " + ingredientCommand.getRecipeId());
        }

        Recipe recipe = optionalRecipe.get();

        Optional<Ingredient> optionalIngredient = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
        if (optionalIngredient.isPresent()) {
            Ingredient ingredient = optionalIngredient.get();
            ingredient.setAmount(ingredientCommand.getAmount());
            ingredient.setDescription(ingredientCommand.getDescription());
            ingredient.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
                    .orElseThrow(() -> new RuntimeException("Unit of measure not found. ID: " + ingredientCommand.getUom().getId())));
        } else {
            Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
            ingredient.setRecipe(recipe);
            recipe.addIngredients(ingredient);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        Optional<Ingredient> savedOptionalIngredient = savedRecipe.getIngredients()
                .stream().filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();

        //todo edit with ID generation before save to DB
        if (!savedOptionalIngredient.isPresent()) {
            savedOptionalIngredient = savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                    .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(ingredient -> ingredient.getUom().getId().equals(ingredientCommand.getUom().getId()))
                    .findFirst();
        }

        return ingredientToIngredientCommand.convert(savedOptionalIngredient.get());

    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Optional<Ingredient> ingredientFound = ingredientRepository.findById(id);
        if (!ingredientFound.isPresent()) {
            throw new NoSuchElementException("Couldn't find the ingredient by id: " + id);
        }
        Recipe recipeToUpdate = ingredientFound.get().getRecipe();
        recipeToUpdate.getIngredients().removeIf(ingredientToDelete -> ingredientToDelete.getId().equals(id));
        recipeRepository.save(recipeToUpdate);

        ingredientRepository.deleteById(id);
    }
}

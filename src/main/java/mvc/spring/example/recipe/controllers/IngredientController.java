package mvc.spring.example.recipe.controllers;

import mvc.spring.example.recipe.commands.IngredientCommand;
import mvc.spring.example.recipe.commands.UnitOfMeasureCommand;
import mvc.spring.example.recipe.services.IngredientService;
import mvc.spring.example.recipe.services.RecipeService;
import mvc.spring.example.recipe.services.UnitsOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitsOfMeasureService uomService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UnitsOfMeasureService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @GetMapping("recipe/{id}/ingredients")
    public String getRecipeIngredientsViewPage(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.getById(Long.valueOf(id)));
        return "ingredient/ingredients";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/view")
    public String getRecipeIngredientById(@PathVariable String recipeId,
                                          @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        return "ingredient/ingredient_view";
    }

    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String newRecipe(@PathVariable String recipeId, Model model) {

        //check valid id value
        recipeService.findCommandByID(Long.valueOf(recipeId));

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", uomService.getAllUoms());

        return "ingredient/ingredient_update";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredientById(@PathVariable String recipeId,
                                            @PathVariable String ingredientId, Model model){
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
        model.addAttribute("uomList", uomService.getAllUoms());
        return "ingredient/ingredient_update";
    }

    @GetMapping("recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteRecipeIngredientById(@PathVariable String recipeId,
                                             @PathVariable String ingredientId){
        ingredientService.deleteById(Long.valueOf(ingredientId));
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String createOrUpdateRecipeIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredients";
    }

}

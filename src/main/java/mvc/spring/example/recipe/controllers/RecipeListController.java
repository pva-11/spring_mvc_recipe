package mvc.spring.example.recipe.controllers;

import mvc.spring.example.recipe.commands.RecipeCommand;
import mvc.spring.example.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeListController {

    private final RecipeService recipeService;

    public RecipeListController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"recipes","recipes/"})
    public String getRecipeListPage(Model model){
        model.addAttribute("recipes",recipeService.getAllRecipes());
        return "recipe/recipes";
    }

    @GetMapping("recipe/{id}/view")
    public String getRecipeViewPage(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.getById(Long.valueOf(id)));
        return "recipe/recipe_view";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipe_update";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandByID(Long.valueOf(id)));
        return "recipe/recipe_update";
    }

    @GetMapping("recipe/{id}/delete")
    public String deleteRecipe(@PathVariable String id, Model model) {
        recipeService.deleteById(Long.valueOf(id));
        model.addAttribute("recipes",recipeService.getAllRecipes());
        return "redirect:/recipes";
    }

    @PostMapping("recipe")
    public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + savedCommand.getId() + "/view";
    }

}

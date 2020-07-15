package mvc.spring.example.recipe.controllers;

import mvc.spring.example.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeListController {

    private final RecipeService recipeService;

    public RecipeListController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"recipes","recipes/"})
    public String getRecipeListPage(Model model){
        model.addAttribute("recipes",recipeService.getAllRecipes());
        return "recipes";
    }

    @RequestMapping("recipe/view/{id}")
    public String getRecipeViewPage(@PathVariable Long id, Model model){
        model.addAttribute("recipe",recipeService.getById(id));
        return "recipe_view";
    }

}

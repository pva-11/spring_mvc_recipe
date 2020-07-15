package mvc.spring.example.recipe.controllers;

import mvc.spring.example.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeListController {

    private final RecipeService recipeService;

    public RecipeListController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"recipes","recipes/"})
    public String getIndexPage(Model model){
        model.addAttribute("recipes",recipeService.getAllRecipes());
        return "recipes";
    }

}

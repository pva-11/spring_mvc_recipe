package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.commands.CategoryCommand;

import java.util.Set;

public interface CategoryService {

    Set<CategoryCommand> getAllCategorites();

}

package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.commands.CategoryCommand;
import mvc.spring.example.recipe.converters.CategoryToCategoryCommand;
import mvc.spring.example.recipe.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final CategoryToCategoryCommand categoryToCategoryCommand;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryToCategoryCommand categoryToCategoryCommand) {
        this.categoryRepository = categoryRepository;
        this.categoryToCategoryCommand = categoryToCategoryCommand;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<CategoryCommand> getAllCategorites() {
        return StreamSupport.stream(categoryRepository.findAll().spliterator(),false)
                .map(categoryToCategoryCommand :: convert).collect(Collectors.toSet());
    }
}

package mvc.spring.example.recipe.converters;


import lombok.Synchronized;
import mvc.spring.example.recipe.model.Category;
import mvc.spring.example.recipe.repositories.CategoryRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CategoryCommandToCategory implements Converter<Long, Category> {

    private final CategoryRepository categoryRepository;

    public CategoryCommandToCategory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Synchronized
    @Nullable
    @Override
    public Category convert(Long categoryId) {

        if (categoryId == null) {
            return null;
        }
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        }
        return null;

    }
}

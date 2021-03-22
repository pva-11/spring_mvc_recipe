package mvc.spring.example.recipe.converters;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import mvc.spring.example.recipe.commands.CategoryCommand;
import mvc.spring.example.recipe.model.Category;
import mvc.spring.example.recipe.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryToCategoryCommandTest {

    CategoryToCategoryCommand categoryToCategoryCommand = new CategoryToCategoryCommand();

    @SneakyThrows
    static Stream<Arguments> getargs() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        InputStream is = Category[].class.getResourceAsStream("/categories.json");

        return Stream.of(mapper.readValue(is, Category[].class))
                .map(category -> Arguments.of(category));

    }

    @ParameterizedTest(name = "{displayName} - [{index}] {arguments}")
    @MethodSource("getargs")
    public void convert(Category category) {
        if (!Objects.isNull(category)) {
            assertThat(categoryToCategoryCommand.convert(category))
                    .isEqualTo(new CategoryCommand(category.getId(), category.getDescription()));
        } else {
            assertThat(categoryToCategoryCommand.convert(category)).isEqualTo(null);
        }
    }
}
package mvc.spring.example.recipe.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import mvc.spring.example.recipe.model.Recipe;
import mvc.spring.example.recipe.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.io.InputStream;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    public static final int FIRST_ELEMENT_INDEX = 0;
    public static final String FIRST_ELEMENT_DESCRIPTION = "Borsh";
    public static final Optional<Recipe> OPTIONAL_NULL = Optional.ofNullable(null);
    public static final int EXPECTED_SIZE_AFTER_REMOVE = 2;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Captor
    ArgumentCaptor<Long> longArgumentCaptor;

    private static List<Recipe>  recipeList = new ArrayList<>();

    @SneakyThrows
    @BeforeAll
    private static void initRecipeList() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        InputStream is = Recipe[].class.getResourceAsStream("/recipes.json");
        recipeList = new ArrayList<>((Arrays.asList(mapper.readValue(is, Recipe[].class))));
    }

    @Test
    public void getAllRecipes() {
        when(recipeRepository.findAll()).thenReturn(recipeList);
        List<Recipe> expextedList = recipeService.getAllRecipes();
        assertThat(recipeList).isEqualTo(expextedList);
        assertThat(recipeList).isNotEqualTo(Collections.emptyList());
    }

    @Test
    public void getById() {
        Recipe recipe = recipeList.get(FIRST_ELEMENT_INDEX);
        when(recipeRepository.findById(any())).thenReturn(Optional.ofNullable(recipe));

        Recipe recipeFoundByID = recipeService.getById(any());
        assertThat(recipeFoundByID).isNotNull();
        assertThat(recipeFoundByID.getDescription()).isEqualTo(FIRST_ELEMENT_DESCRIPTION);
    }

    @Test
    public void getByIdFail() {
        when(recipeRepository.findById(any())).thenReturn(OPTIONAL_NULL);
        assertThrows(NoSuchElementException.class, () -> recipeService.getById(any()));
    }

    @Test
    public void deleteById() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                recipeList.remove(FIRST_ELEMENT_INDEX);
                return null;
            }
        }).when(recipeRepository).deleteById(longArgumentCaptor.capture());

        recipeService.deleteById(Long.valueOf(FIRST_ELEMENT_INDEX));

        assertThat(Long.valueOf(FIRST_ELEMENT_INDEX)).isEqualTo(longArgumentCaptor.getValue());
        assertThat(recipeList.size()).isEqualTo(EXPECTED_SIZE_AFTER_REMOVE);
    }

}
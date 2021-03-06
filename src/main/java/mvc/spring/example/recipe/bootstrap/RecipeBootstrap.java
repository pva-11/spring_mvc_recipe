package mvc.spring.example.recipe.bootstrap;

import lombok.extern.slf4j.Slf4j;
import mvc.spring.example.recipe.model.*;
import mvc.spring.example.recipe.repositories.CategoryRepository;
import mvc.spring.example.recipe.repositories.MyAppUserRepository;
import mvc.spring.example.recipe.repositories.RecipeRepository;
import mvc.spring.example.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final MyAppUserRepository myAppUserRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository,
                           UnitOfMeasureRepository unitOfMeasureRepository, MyAppUserRepository myAppUserRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.myAppUserRepository = myAppUserRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(createRecipes());
        myAppUserRepository.saveAll(createDefaultUsers());
    }

    private List<Recipe> createRecipes() {

        List<Recipe> recipes = new ArrayList<>();

        //get UOMs
        Optional<UnitOfMeasure> peaceUomOptional = unitOfMeasureRepository.findByName("Piece");

        if(!peaceUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> spoonUomOptional = unitOfMeasureRepository.findByName("Spoon");

        if(!spoonUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> sliceUomOptional = unitOfMeasureRepository.findByName("Slice");

        if(!sliceUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        Optional<UnitOfMeasure> gramUomOptional = unitOfMeasureRepository.findByName("Gram");

        if(!gramUomOptional.isPresent()){
            throw new RuntimeException("Expected UOM Not Found");
        }

        //get optionals
        UnitOfMeasure peaceUom = peaceUomOptional.get();
        UnitOfMeasure spoonUom = spoonUomOptional.get();
        UnitOfMeasure sliceUom = sliceUomOptional.get();
        UnitOfMeasure gramUom = gramUomOptional.get();

        //get Categories
        Optional<Category> fastFoodCategoryOptional = categoryRepository.findByDescription("Fast Food");

        if(!fastFoodCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Optional<Category> italianCategoryOptional = categoryRepository.findByDescription("Italian");

        if(!italianCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Category fastFoodCategory = fastFoodCategoryOptional.get();
        Category italianCategory = italianCategoryOptional.get();

        Recipe sandwich = new Recipe();

        sandwich.setDescription("Sandwich");
        sandwich.setPrepTime(1);
        sandwich.setDifficulty(Difficulty.EASY);
        sandwich.setNotes(new Notes("Easy and fast"));

        sandwich.getCategories().add(fastFoodCategory);
        sandwich.addIngredients(new Ingredient("bread", new BigDecimal(2), peaceUom));
        sandwich.addIngredients(new Ingredient("becon", new BigDecimal(1), sliceUom));
        sandwich.addIngredients(new Ingredient("souce", new BigDecimal(1), spoonUom));

        recipes.add(sandwich);
        log.debug("RecipeBootstrap: new Recipe entity created" + sandwich.getDescription());

        Recipe pizza = new Recipe();

        pizza.setDescription("Pizza");
        pizza.setPrepTime(30);
        pizza.setDifficulty(Difficulty.HARD);
        pizza.setNotes(new Notes("Very tasty"));

        pizza.getCategories().add(italianCategory);
        pizza.addIngredients(new Ingredient("bread", new BigDecimal(1), peaceUom));
        pizza.addIngredients(new Ingredient("cheese", new BigDecimal(200), gramUom));
        pizza.addIngredients(new Ingredient("tomato", new BigDecimal(5), sliceUom));
        pizza.addIngredients(new Ingredient("mushroom", new BigDecimal(5), sliceUom));
        pizza.addIngredients(new Ingredient("becon", new BigDecimal(5), sliceUom));

        recipes.add(pizza);
        log.debug("RecipeBootstrap: new Recipe entity created" + pizza.getDescription());

        return recipes;

    }

    private List<MyAppUser> createDefaultUsers() {
        List<MyAppUser> users = new ArrayList<>();
        users.add(new MyAppUser("user", "user", "user@fake-mail.com", "XXX-XXX-XX-XX", UserRole.USER));
        users.add(new MyAppUser("admin", "admin", "admin@fake-mail.com", "YYY-YYY-YY-YY", UserRole.ADMIN));
        return users;
    }

}

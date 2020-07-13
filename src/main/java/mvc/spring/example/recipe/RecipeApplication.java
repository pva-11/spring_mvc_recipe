package mvc.spring.example.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("mvc.spring.example.recipe.model")
public class RecipeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipeApplication.class, args);
    }

}

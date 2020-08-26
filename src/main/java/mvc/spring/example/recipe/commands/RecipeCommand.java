package mvc.spring.example.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mvc.spring.example.recipe.model.Difficulty;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private Long id;
    private String description;
    private Integer prepTime;
    private Difficulty difficulty;

    private NotesCommand notes;
    Set<IngredientCommand> ingredients = new HashSet<>();
    Set<CategoryCommand> categories = new HashSet<>();

}

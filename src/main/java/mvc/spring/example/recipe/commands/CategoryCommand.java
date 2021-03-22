package mvc.spring.example.recipe.commands;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CategoryCommand {

    private Long id;
    private String description;

}

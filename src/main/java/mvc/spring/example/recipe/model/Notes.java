package mvc.spring.example.recipe.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String recipeNotes;

    @OneToOne(mappedBy="notes")
    private Recipe recipe;

    public Notes(String recipeNotes) {
        this.recipeNotes = recipeNotes;
    }

}

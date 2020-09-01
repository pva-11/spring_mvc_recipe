package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.model.Recipe;
import mvc.spring.example.recipe.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {

        try {

            Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

            if (optionalRecipe.isPresent()) {

                Recipe recipe = optionalRecipe.get();

                //wrap byte[] to Byte[]
                Byte[] copyOfFile = new Byte[file.getBytes().length];
                int i = 0;
                for (Byte b : file.getBytes()) {
                    copyOfFile[i++] = b;
                }

                recipe.setImage(copyOfFile);
                recipeRepository.save(recipe);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

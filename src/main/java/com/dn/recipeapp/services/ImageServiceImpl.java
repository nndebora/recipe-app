package com.dn.recipeapp.services;

import com.dn.recipeapp.domain.Recipe;
import com.dn.recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    private RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
            if (!recipeOptional.isPresent()) {
                log.debug("Recipe not fount. Id:" + recipeId);
                //todo handle exception
            } else {

                Recipe recipe = recipeOptional.get();
                Byte[] imageBytes = new Byte[file.getBytes().length];
                int index = 0;
                for (byte b : file.getBytes()) {
                    imageBytes[index++] = b;
                }
                recipe.setImage(imageBytes);

                recipeRepository.save(recipe);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

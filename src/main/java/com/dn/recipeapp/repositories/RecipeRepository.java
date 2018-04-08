package com.dn.recipeapp.repositories;

import com.dn.recipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe,Long>{
}

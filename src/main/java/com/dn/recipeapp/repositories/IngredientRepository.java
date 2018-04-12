package com.dn.recipeapp.repositories;

import com.dn.recipeapp.domain.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient,Long>{
}

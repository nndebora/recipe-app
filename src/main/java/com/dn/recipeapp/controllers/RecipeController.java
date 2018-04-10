package com.dn.recipeapp.controllers;


import com.dn.recipeapp.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"/recipe/show/{id}"})
    public String showRecipeById(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findById(new Long(id)));
        return "recipe/show";
    }
}

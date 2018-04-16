package com.dn.recipeapp.controllers;


import com.dn.recipeapp.commands.RecipeCommand;
import com.dn.recipeapp.services.ImageService;
import com.dn.recipeapp.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @PostMapping("recipe/{id}/image")
    public String imagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(Long.valueOf(id),file);
        return "redirect:/recipe/" + id+ "/show";
    }

    @GetMapping("recipe/{id}/image")
    public String showImageForm(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/imageuploadform";


    }

    @GetMapping("recipe/{id}/recipeimage")
    public void renderRecipeImage(@PathVariable String id, HttpServletResponse response) throws IOException{

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(id));

        if(recipeCommand.getImage()!=null) {
            byte[] imageBytes = new byte[recipeCommand.getImage().length];
            int i = 0;
            for (byte b : recipeCommand.getImage()) {
                imageBytes[i++] = b;
            }
            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(imageBytes);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}

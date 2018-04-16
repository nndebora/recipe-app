package com.dn.recipeapp.controllers;

import com.dn.recipeapp.commands.RecipeCommand;
import com.dn.recipeapp.services.ImageService;
import com.dn.recipeapp.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    ImageController imageController;
    @Mock
    ImageService imageService;
    @Mock
    RecipeService recipeService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        imageController = new ImageController(imageService,recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();

    }

    @Test
    public void imagePost() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Test App recipe".getBytes());
        this.mockMvc.perform(multipart("/recipe/1/image")
                .file (multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header()
                        .string("Location","/recipe/1/show"));

        verify(imageService,times(1)).saveImageFile(anyLong(),any());

    }
    @Test
    public void showImageFormTest() throws Exception {
        RecipeCommand recipeCommand = new RecipeCommand();

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        this.mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/imageuploadform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void renderRecipeImageTest() throws Exception{

        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        String imageTest = "mock image test";
        Byte[] imageBytes = new Byte[imageTest.getBytes().length];
        int i=0;
        for (byte b:imageTest.getBytes() ) {
            imageBytes[i++]=b;
        }
        recipeCommand.setImage(imageBytes);

        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        MockHttpServletResponse response = mockMvc
                .perform(get("/recipe/1/recipeimage"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        byte[] responseBytes = response.getContentAsByteArray();
        assertEquals(imageTest.getBytes().length,responseBytes.length);
    }
}
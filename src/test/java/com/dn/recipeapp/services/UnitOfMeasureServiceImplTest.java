package com.dn.recipeapp.services;

import com.dn.recipeapp.commands.UnitOfMeasureCommand;
import com.dn.recipeapp.converters.IngredientCommandToIngredient;
import com.dn.recipeapp.converters.IngredientToIngredientCommand;
import com.dn.recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.dn.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.dn.recipeapp.domain.UnitOfMeasure;
import com.dn.recipeapp.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    private final UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand;

    @Mock
    UnitOfMeasureRepository repository;


    UnitOfMeasureService unitOfMeasureService;

    public UnitOfMeasureServiceImplTest() {
        uomToUomCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(repository, uomToUomCommand);
    }


    @Test
    public void listUOMsTest() {

        //given
        Set<UnitOfMeasure> uoms = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        uoms.add(uom1);

        //when
        when(repository.findAll()).thenReturn(uoms);

        //then
        Set<UnitOfMeasureCommand> uomCommands = unitOfMeasureService.listUOMs();

        assertEquals(uomCommands.size(), 1);
        verify(repository, times(1)).findAll();
        verify(repository, never()).findById(anyLong());

    }
}
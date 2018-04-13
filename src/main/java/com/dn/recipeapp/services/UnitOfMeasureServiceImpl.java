package com.dn.recipeapp.services;

import com.dn.recipeapp.commands.UnitOfMeasureCommand;
import com.dn.recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.dn.recipeapp.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    private UnitOfMeasureRepository uomRepository;
    private UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand) {
        this.uomRepository = uomRepository;
        this.uomToUomCommand = uomToUomCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listUOMs() {

        return StreamSupport.stream(uomRepository.findAll()
                .spliterator(), false)
                .map(uomToUomCommand::convert)
                .collect(Collectors.toSet());
    }
}

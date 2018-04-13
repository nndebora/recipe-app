package com.dn.recipeapp.services;

import com.dn.recipeapp.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listUOMs();
}

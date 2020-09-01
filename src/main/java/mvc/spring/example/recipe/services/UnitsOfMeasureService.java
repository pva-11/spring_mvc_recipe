package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UnitsOfMeasureService {

    Set<UnitOfMeasureCommand> getAllUoms();

}

package mvc.spring.example.recipe.services;

import mvc.spring.example.recipe.commands.UnitOfMeasureCommand;
import mvc.spring.example.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import mvc.spring.example.recipe.model.UnitOfMeasure;
import mvc.spring.example.recipe.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UnitsOfMeasureServiceImplTest {

    private static final int EXPECTED_SIZE = 1;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Spy
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @InjectMocks
    UnitsOfMeasureServiceImpl unitsOfMeasureService;

    @Test
    public void getAllUoms() {

        List<UnitOfMeasure> unitsOfMeasure = new ArrayList<>();
        long expectedId = 1L;
        final UnitOfMeasure uom = new UnitOfMeasure(expectedId, "UOM");

        unitsOfMeasure.add(uom);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitsOfMeasure);

        Set<UnitOfMeasureCommand> unitOfMeasureCommands = unitsOfMeasureService.getAllUoms();

        assertThat(unitOfMeasureCommands.size()).isEqualTo(EXPECTED_SIZE);
        assertThat(unitOfMeasureToUnitOfMeasureCommand.convert(uom).getId()).isEqualTo(expectedId);
        assertThat(unitOfMeasureCommands.contains(unitOfMeasureToUnitOfMeasureCommand.convert(uom)));

    }
}
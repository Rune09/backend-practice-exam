package ucll.voorbeeldexamen.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


@ExtendWith(MockitoExtension.class)
public class ToyTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }
    
    @Test
    public void givenInvalidName_whenToyIsCreated_thenToyExceptionIsThrown() {
        //Given
        String invalidName = "         ";

        //When
        Toy anInvalidToy = new Toy(invalidName);

        //Then
        Set<ConstraintViolation<Toy>> violations = validator.validate(anInvalidToy);
        assertEquals(violations.size(), 1);
        ConstraintViolation<Toy> violation = violations.iterator().next();
        assertEquals("Name cannot be empty", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals(invalidName, violation.getInvalidValue());
    }
}

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
public class DogTest {

    private String invalidName;

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
    public void givenEmptyName_whenDogIsCreated_thenDogExceptionIsThrown() {
        //Given
        invalidName = "     ";

        //When
        Dog anInvalidDog = new Dog(invalidName);

        //Then
        Set<ConstraintViolation<Dog>> violations = validator.validate(anInvalidDog);
        assertEquals(violations.size(), 1);
        ConstraintViolation<Dog> violation = violations.iterator().next();
        assertEquals("The dog name must count between 3 and 7 characters", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals(invalidName, violation.getInvalidValue());
    }

    @Test
    public void givenNameWithLessThan3Characters_whenDogIsCreated_thenDogExceptionIsThrown() {
        //Given
        invalidName = "ab";

        //When
        Dog anInvalidDog = new Dog(invalidName);

        //Then
        Set<ConstraintViolation<Dog>> violations = validator.validate(anInvalidDog);
        assertEquals(violations.size(), 1);
        ConstraintViolation<Dog> violation = violations.iterator().next();
        assertEquals("The dog name must count between 3 and 7 characters", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals(invalidName, violation.getInvalidValue());
    }

    @Test
    public void givenNameWithMoreThan7Characters_whenDogIsCreated_thenDogExceptionIsThrown() {
        //Given
        invalidName = "123456789";

        //When
        Dog anInvalidDog = new Dog(invalidName);

        //Then
        Set<ConstraintViolation<Dog>> violations = validator.validate(anInvalidDog);
        assertEquals(violations.size(), 1);
        ConstraintViolation<Dog> violation = violations.iterator().next();
        assertEquals("The dog name must count between 3 and 7 characters", violation.getMessage());
        assertEquals("name", violation.getPropertyPath().toString());
        assertEquals(invalidName, violation.getInvalidValue());
    }
}

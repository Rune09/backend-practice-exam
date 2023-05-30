package ucll.voorbeeldexamen.controller;

import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;

import ucll.voorbeeldexamen.model.Dog;
import ucll.voorbeeldexamen.model.Toy;
import ucll.voorbeeldexamen.service.DogService;
import ucll.voorbeeldexamen.service.DogServiceException;
import ucll.voorbeeldexamen.service.ToyService;

@RestController
@RequestMapping("api")
public class Controller {

    @Autowired
    private DogService dogService;

    @Autowired
    private ToyService toyService;

    @PostMapping("dog/add")
    public Dog addDog(@Valid@RequestBody Dog dog) throws DogServiceException {
        return dogService.addDog(dog);
    }

    @PostMapping("toy/add")
    public Toy addToy(@RequestBody Toy toy) {
        return toyService.addToy(toy);
    }

    @PutMapping("toy/{toyId}")
    public Toy updateToy(@PathVariable("toyId") Long toyId, @RequestBody Toy toy){
        return toyService.updateToy(toyId, toy);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DogServiceException.class})
    public Map<String, String> handleServiceException(DogServiceException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(), ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach((error) -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}



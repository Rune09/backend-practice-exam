package ucll.voorbeeldexamen.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import ucll.voorbeeldexamen.model.Dog;
import ucll.voorbeeldexamen.service.DogService;
import ucll.voorbeeldexamen.service.DogServiceException;
import ucll.voorbeeldexamen.service.ServiceException;


@RestController
@RequestMapping("dog")
public class DogController {
    
    @Autowired
    private DogService dogService;

    @GetMapping("sort")
    public List<Dog> sortDogsByName(@RequestParam("order") String order) throws ServiceException {
        return dogService.getDogsSortedBy(order);
    }

    @PostMapping("{dogId}/addToy")
    public Dog addToy(@PathVariable("dogId") Long dogId, @RequestParam("toyId") Long toyId) throws ServiceException {
        return dogService.addToy(dogId, toyId);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DogServiceException.class})
    public Map<String, String> handleServiceException(DogServiceException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getField(), ex.getMessage());
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ServiceException.class})
    public Map<String, String> handleServiceException(ServiceException ex) {
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

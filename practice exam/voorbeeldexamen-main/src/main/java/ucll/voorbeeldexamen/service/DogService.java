package ucll.voorbeeldexamen.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ucll.voorbeeldexamen.model.Dog;
import ucll.voorbeeldexamen.model.Toy;
import ucll.voorbeeldexamen.repo.DogRepo;
import ucll.voorbeeldexamen.repo.ToyRepo;

@Service
public class DogService {
    @Autowired
    private DogRepo dogRepo;

    @Autowired
    private ToyRepo toyRepo;

    public Dog addDog(Dog dog) throws DogServiceException {
        if (dogRepo.findDogByName(dog.getName()) != null) {
            throw new DogServiceException("name", "The dog name must be unique");
        }
        return dogRepo.save(dog);
    }

    public List<Dog> getDogsSortedBy(String order) throws ServiceException {
        if (order.equals("DESC") == false && order.equals("ASC") == false) {
            throw new ServiceException("order", "The order must be ASC or DESC");
        }
        if (order == "ASC") {
            return dogRepo.findAllByOrderByNameAsc();
        }
        return dogRepo.findAllByOrderByNameDesc();
    }

    public Dog addToy(Long dogId, Long toyId) throws ServiceException {
        Dog dog = dogRepo.findDogById(dogId);
        Toy toy = toyRepo.findToyById(toyId);

        for (Toy element : dog.getToys()) {
            if(element.getName() == toy.getName()) {
                throw new ServiceException("toy", "This dog has already a toy with this name");
            }
        }

        dog.addToy(toy);
        dogRepo.save(dog);
        toyRepo.save(toy);
        return dog;
    }
}
package ucll.voorbeeldexamen.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ucll.voorbeeldexamen.model.Dog;

public interface DogRepo extends JpaRepository<Dog, Long> {

    Dog findDogByName(String name);

    List<Dog> findAllByOrderByNameAsc();

    List<Dog> findAllByOrderByNameDesc();

    Dog findDogById(Long dogId);

}

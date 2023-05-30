package ucll.voorbeeldexamen.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "dogs")
public class Dog {

    @ManyToMany
    @JoinTable(
        name = "toys_of_dog",
        joinColumns = @JoinColumn(name = "toy_id"),
        inverseJoinColumns = @JoinColumn(name = "dog_id"))
    private List<Toy> toys = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The dog name must count between 3 and 7 characters")
    @Size(min = 3, max = 7, message = "The dog name must count between 3 and 7 characters")
    private String name;

    public Dog(String name) {
        this.setName(name);
    }

    public Dog() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Toy> getToys() {
        return this.toys;
    }

    public void addToy(Toy toy) {
        this.getToys().add(toy);
    }

}

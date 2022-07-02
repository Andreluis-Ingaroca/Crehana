package pe.edu.upc.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "courses")
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private boolean premium;

    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "multimedia_id",nullable = false)
    private List<Multimedia> multimedia;

//    public boolean hasIngredient(Ingredient ingredient){ return this.getIngredients().contains(ingredient);}
//
//    public Course assignIngredient(Ingredient ingredient){
//        if(!this.hasIngredient(ingredient))
//            this.getIngredients().add(ingredient);
//        return this;
//    }
//
//    public Course unassignIngredient(Ingredient ingredient){
//        if(this.hasIngredient(ingredient))
//            this.getIngredients().remove(ingredient);
//        return this;
//    }


    public Long getId() {
        return id;
    }

    public Course setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Course setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isPremium() {
        return premium;
    }

    public Course setPremium(boolean premium) {
        this.premium = premium;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Course setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void addMultimedia(Multimedia multimedia){
        this.getMultimedia().add(multimedia);
    }


}

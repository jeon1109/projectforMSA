package com.example.nplus1test.domain.country.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class MenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String menu;

    @OneToMany(mappedBy = "menuEntity")
    private List<IngredientEntity> ingredientEntities = new ArrayList<>();

    public void addIngredientEntity(IngredientEntity ingredientEntity) {
        ingredientEntities.add(ingredientEntity);
        ingredientEntity.setMenuEntity(this);
    }
}

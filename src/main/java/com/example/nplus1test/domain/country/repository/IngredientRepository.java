package com.example.nplus1test.domain.country.repository;

import com.example.nplus1test.domain.country.entity.IngredientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientRepository extends JpaRepository<IngredientEntity, Long> {
    Optional<IngredientEntity> findByIngredient(String ingredient);



}

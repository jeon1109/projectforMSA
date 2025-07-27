package com.example.nplus1test.domain.country.repository;

import com.example.nplus1test.domain.country.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<MenuEntity, Long> {
    Optional<MenuEntity> findByMenu(String menu);

    @Query("SELECT me FROM MenuEntity me " +
            "JOIN FETCH me.ingredientEntities ie")
    List<MenuEntity> findAllFetch();

}

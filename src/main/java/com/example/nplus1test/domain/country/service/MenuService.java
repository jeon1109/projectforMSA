package com.example.nplus1test.domain.country.service;


import com.example.nplus1test.domain.country.entity.IngredientEntity;
import com.example.nplus1test.domain.country.entity.MenuEntity;
import com.example.nplus1test.domain.country.repository.IngredientRepository;
import com.example.nplus1test.domain.country.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    private final IngredientRepository ingredientRepository;
    private final MenuRepository menuRepository;

    public MenuService(IngredientRepository ingredientRepository, MenuRepository menuRepository) {
        this.ingredientRepository = ingredientRepository;
        this.menuRepository = menuRepository;
    }

    // 메뉴 추가
    public void createMenu(String menuName) {

        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setMenu(menuName);

        menuRepository.save(menuEntity);
    }

    // 재료 추가 (메뉴 연관)
    public void createIngredient(String menuName, String ingredientName) {

        IngredientEntity ingredientEntity = new IngredientEntity();
        ingredientEntity.setIngredient(ingredientName);
        ingredientRepository.save(ingredientEntity);

        MenuEntity menuEntity = menuRepository.findByMenu(menuName).orElseThrow();
        menuEntity.addIngredientEntity(ingredientEntity);

        menuRepository.save(menuEntity);
    }

    public List<MenuEntity> readCountry() {
        return menuRepository.findAll();
    }

    public IngredientEntity readIngredient(String ingredientName) {
        return ingredientRepository.findByIngredient(ingredientName).orElseThrow();
    }

    public List<MenuEntity> readMenuFetch() {

        return menuRepository.findAllFetch();
    }

}

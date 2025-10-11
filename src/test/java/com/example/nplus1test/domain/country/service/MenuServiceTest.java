package com.example.nplus1test.domain.country.service;

import com.example.nplus1test.domain.country.entity.IngredientEntity;
import com.example.nplus1test.domain.country.entity.MenuEntity;
import com.example.nplus1test.domain.country.repository.IngredientRepository;
import com.example.nplus1test.domain.country.repository.MenuRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    MenuRepository menuRepository;

    @InjectMocks
    MenuService menuService;

    @Test
    @DisplayName("메뉴 생성 시 메뉴명이 저장된다")
    void createMenu() {
        ArgumentCaptor<MenuEntity> captor = ArgumentCaptor.forClass(MenuEntity.class);

        menuService.createMenu("Pasta");

        verify(menuRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue().getMenu()).isEqualTo("Pasta");
    }

    @Test
    @DisplayName("재료 생성 시 메뉴와 양방향 연관관계가 맺어진다")
    void createIngredient() {
        MenuEntity menu = new MenuEntity();
        menu.setMenu("Pasta");

        when(menuRepository.findByMenu("Pasta")).thenReturn(Optional.of(menu));
        when(ingredientRepository.save(any(IngredientEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        menuService.createIngredient("Pasta", "Tomato");

        assertThat(menu.getIngredientEntities())
                .extracting(IngredientEntity::getIngredient)
                .containsExactly("Tomato");

        IngredientEntity saved = menu.getIngredientEntities().get(0);
        assertThat(saved.getMenuEntity()).isSameAs(menu);

        verify(menuRepository, times(1)).save(menu);
    }

    @Test
    @DisplayName("재료 조회 실패 시 NoSuchElementException 발생")
    void readIngredient_notFound() {
        when(ingredientRepository.findByIngredient("Nope")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> menuService.readIngredient("Nope"))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("fetch join 조회 호출")
    void readMenuFetch() {
        when(menuRepository.findAllFetch()).thenReturn(List.of());
        assertThat(menuService.readMenuFetch()).isEmpty();
        verify(menuRepository).findAllFetch();
    }
}
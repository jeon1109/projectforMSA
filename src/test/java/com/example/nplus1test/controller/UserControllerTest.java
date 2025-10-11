package com.example.nplus1test.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class UserControllerTest {

    @Test
    @DisplayName("기본 유저 목록을 조회할 수 있다")
    void getUsers_defaults() {
        UserController controller = new UserController();
        List<String> users = controller.getUsers();
        assertThat(users).containsExactly("Alice", "Bob", "Charlie");
    }

    @Test
    @DisplayName("유저 추가/수정/삭제 플로우")
    void crudFlow() {
        UserController controller = new UserController();

        // add
        String addMsg = controller.addUser("Dave");
        assertThat(addMsg).contains("User added: Dave");
        assertThat(controller.getUsers()).contains("Dave");

        // update
        String updMsg = controller.updateUser(0, "Zed");
        assertThat(updMsg).contains("User updated: Zed");
        assertThat(controller.getUsers().get(0)).isEqualTo("Zed");

        // delete
        String delMsg = controller.deleteUser(1);
        assertThat(delMsg).contains("User deleted:");
        assertThat(controller.getUsers()).doesNotContain("Bob"); // originally index 1 was Bob
    }
}
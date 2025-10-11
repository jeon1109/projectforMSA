package com.example.nplus1test.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private List<String> users = new ArrayList<>(List.of("Alice", "Bob", "Charlie"));

    // 조회 (GET)
    @GetMapping
    public List<String> getUsers() {
        return users;
    }

    // 추가 (POST)
    @PostMapping
    public String addUser(@RequestParam String name) {
        users.add(name);
        return "User added: " + name;
    }

    // 수정 (PUT)
    @PutMapping("/{index}")
    public String updateUser(@PathVariable int index, @RequestParam String name) {
        users.set(index, name);
        return "User updated: " + name;
    }

    // 삭제 (DELETE)
    @DeleteMapping("/{index}")
    public String deleteUser(@PathVariable int index) {
        String removed = users.remove(index);
        return "User deleted: " + removed;
    }

}

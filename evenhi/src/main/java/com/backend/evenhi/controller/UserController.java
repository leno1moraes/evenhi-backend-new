package com.backend.evenhi.controller;

import com.backend.evenhi.dto.UserDTO;
import com.backend.evenhi.model.User;
import com.backend.evenhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity<List<User>> listAllUsers() {
        return ResponseEntity.ok(userService.listAll());
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok("1001");
        }catch (Exception e){
            System.out.println("ERROR create user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("0");
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            userService.updateUser(id, user);
            return ResponseEntity.ok("1001");
        }catch (Exception e){
            System.out.println("ERROR update user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("0");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok("1001");
        }catch (Exception e){
            System.out.println("ERROR delete user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("0");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO dto) {

        System.out.println("#############################################");
        System.out.println("Dados para autenticação - usermail: " + dto.getEmail());
        System.out.println("Dados para autenticação - userpassword: " + dto.getPassword());

        Optional<User> user = userService.authenticate(dto.getEmail(), dto.getPassword());

        try{
            if (user.isPresent()) {
                System.out.println("AUTENTICADO sucesso para o usuario: "+ user.get());
                return ResponseEntity.ok(user.get());
            } else {
                System.out.println("AUTENTICACAO nao achou!");
                return ResponseEntity.status(401).body(0);
            }
        }catch (Exception e){
            System.out.println("ERROR AUTENTICACAO: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("0");
        }

    }

}

package com.example.restapi.controller;

import com.example.restapi.model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Map<Long, User> userRepo = new HashMap<>();
    private long idCounter = 1;

    @PostMapping
    public ResponseEntity<EntityModel<User>> createUser(@Valid @RequestBody User user) {
        user.setId(idCounter++);
        userRepo.put(user.getId(), user);

        EntityModel<User> model = EntityModel.of(user);
        model.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));

        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<User>> getUser(@PathVariable Long id) {
        User user = userRepo.get(id);
        if (user == null) {
            throw new NoSuchElementException("User not found with ID: " + id);
        }

        EntityModel<User> model = EntityModel.of(user);
        model.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"));
        return ResponseEntity.ok(model);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(new ArrayList<>(userRepo.values()));
    }
}

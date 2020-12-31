package ru.gb.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.exception.NotFoundException;
import ru.gb.persist.entity.User;
import ru.gb.persist.repo.UserRepository;

import java.util.List;

@Tag(name = "User resourceAPI", description = "API to manipulate User resource ...")
@RequestMapping("/api/v1/user")
@RestController
public class UserResource {

    private final UserRepository userRepository;

    @Autowired
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @GetMapping(path = "/{id}/id") // значение и название параметра
    public User findById(@PathVariable("id") Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public User createUser(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public User updateUser(@RequestBody User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            userRepository.save(user);
        } else {
            throw new NotFoundException();
        }
        return user;
    }

    @DeleteMapping(path = ("/{id}/id"))
    public void deleteUser(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException e) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }
}

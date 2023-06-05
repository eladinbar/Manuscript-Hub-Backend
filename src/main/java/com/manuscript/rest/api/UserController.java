package com.manuscript.rest.api;

import com.manuscript.rest.forms.request.UserRequest;
import com.manuscript.rest.forms.response.UserResponse;
import com.manuscript.rest.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import static com.manuscript.rest.common.Constants.RESOURCE_USER;

@RestController
@RequestMapping(RESOURCE_USER)
@CrossOrigin()
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/updateUser")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user) {
        checkNotNull(user);
        try {
            return ResponseEntity.ok(this.userService.updateUser(user));
        } catch (Exception e) {
            System.err.println("Error updating this user: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);
    }

    @GetMapping(value = "/getUserById/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        if (id == null)
            throw new IllegalArgumentException("Invalid user ID.");
        UserResponse result = userService.getById(id);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/getUserByUid/{uid}")
    public ResponseEntity<UserResponse> getUserByUid(@PathVariable String uid) {
        if (uid == null)
            throw new IllegalArgumentException("Invalid firebase user ID.");
        UserResponse result = userService.getByUid(uid);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUserById(@PathVariable UUID id) {
        if (id == null)
            throw new IllegalArgumentException("Invalid user ID.");
        userService.deleteUser(id);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    private void checkNotNull(UserRequest userRequest) {
        if (Stream.of(userRequest.getUid(), userRequest.getRole(),
                userRequest.getEmail(), userRequest.getName(), userRequest.getPhoneNumber()).anyMatch(Objects::isNull))
            throw new IllegalArgumentException("User request's fields must not be null.");
    }
}

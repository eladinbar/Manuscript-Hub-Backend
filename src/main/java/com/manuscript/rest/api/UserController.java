package com.manuscript.rest.api;

import com.google.firebase.auth.FirebaseAuthException;
import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.infrastructure.firebase.security.Roles.RoleConstants;
import com.manuscript.rest.request.UserRequest;
import com.manuscript.rest.response.ImageResponse;
import com.manuscript.rest.response.UserResponse;
import com.manuscript.rest.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.UUID;

import static com.manuscript.rest.common.Constants.RESOURCE_USER;

@RestController
@RequestMapping(RESOURCE_USER)
@CrossOrigin()
public class UserController {
    private final IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/getUserDetails/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserResponse> getUserById(@PathVariable UUID id) {
        UserResponse result = userService.getById(id);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/updateUser/{id}")
    public ResponseEntity<UserResponse> update(@RequestBody UserRequest user, @PathVariable UUID id) {
        try {
            return ResponseEntity.ok(this.userService.updateUser(user));
        } catch (Exception e) {
            System.err.println("Error updating this user: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);

    }

    @DeleteMapping("/deleteUser/{id}")
    public void deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        //TODO Maybe return an answer
    }
}

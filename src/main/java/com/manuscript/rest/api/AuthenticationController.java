package com.manuscript.rest.api;

import com.google.firebase.auth.FirebaseAuthException;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.infrastructure.firebase.security.Roles.RoleConstants;
import com.manuscript.infrastructure.firebase.service.IAuthenticationService;
import com.manuscript.rest.request.UserRequest;
import com.manuscript.rest.response.UserResponse;
import com.manuscript.rest.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;

@RestController
@RequestMapping("/api/accountController")
@CrossOrigin()
@AllArgsConstructor
public class AuthenticationController {
    private final IAuthenticationService authenticationService;
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest user) {
        try{
            return ResponseEntity.ok(this.userService.getByUid(user.getUid()));
        }
        catch (Exception e) {
            // if in firebase but not in backend
            return ResponseEntity.ok(this.userService.save(user));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest user) {
        //TODO is user exist in userService
        try {
            UserResponse userResponse = this.userService.save(user);
            return ResponseEntity.ok(userResponse);
        }
        catch (Exception e) {
            System.err.println("Error creating new user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);
        }
    }

    @PatchMapping("/changeUserStatus/{id}/{status}")
    public ResponseEntity<UserResponse> changeUserStatus(@PathVariable String id, @PathVariable Status status) {
        throw new RuntimeException("Unimplemented");
    }

    //TODO move to UserController?
    @GetMapping("/addRole/{uid}")
    public ResponseEntity<UserResponse> addRole(@PathVariable String uid) throws NoSuchFieldException, RoleNotFoundException, FirebaseAuthException {
        authenticationService.addRole(uid, RoleConstants.SUPER_USER);
        authenticationService.addRole(uid, RoleConstants.REG_USER);
        throw new NoSuchFieldException();   //todo: need to check this exception
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}

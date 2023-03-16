package com.manuscript.rest.api;

import com.google.firebase.auth.FirebaseAuthException;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.domain.user.repository.IUserRepositoryService;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.infrastructure.firebase.security.Roles.RoleConstants;
import com.manuscript.infrastructure.firebase.service.AuthenticationService;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.UserRegistrationRequest;
import com.manuscript.rest.request.UserRequest;
import com.manuscript.rest.response.UserResponse;
import com.manuscript.rest.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/api/accountController")
@CrossOrigin()
@AllArgsConstructor
public class AccountController {


    private final AuthenticationService authenticationService;
    private final IUserRepositoryService getById;
    private final IRestMapper<UserModel, UserResponse> mapper;

    private final IUserService userService;


    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody UserRequest user) {
        Optional<UserModel> userByUidOpt = getById.findUserByUid(user.getUid());
        if (userByUidOpt.isPresent()) {
            UserModel userModel = userByUidOpt.get();
            return ResponseEntity.ok(mapper.modelToRest(userModel));
        }
        throw new NoUserFoundException();
    }

    @PostMapping("/registerNew")
    public ResponseEntity<UserModel> registerNew(@RequestBody UserRegistrationRequest user) {
        try {
            return ResponseEntity.ok(this.userService.save(user));
//            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
//                    .setEmail(email)
//                    .setPassword(password);
//            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
//            System.out.println("Successfully created new user: " + userRecord.getUid());
//            return ResponseEntity.ok(userRecord);
        } catch (Exception e) {
            System.err.println("Error creating new user: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null);
    }


    @GetMapping("/addRole/{uid}")
    public ResponseEntity<UserResponse> addRole(@PathVariable String uid) throws NoSuchFieldException, RoleNotFoundException, FirebaseAuthException {
        authenticationService.addRole(uid, RoleConstants.SUPER_USER);
        authenticationService.addRole(uid, RoleConstants.REG_USER);
        throw new NoSuchFieldException();   //todo: need to check this exception
    }


}

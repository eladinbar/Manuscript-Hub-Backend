package com.manuscript.rest.api;

import com.manuscript.rest.response.UserResponse;
import com.manuscript.rest.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

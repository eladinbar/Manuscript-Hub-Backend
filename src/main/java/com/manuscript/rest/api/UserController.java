package com.manuscript.rest.api;

import com.manuscript.rest.response.UserResponse;
import com.manuscript.rest.service.IVideoUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.manuscript.rest.common.Constants.RESOURCE_VIDEO;

@RestController
@RequestMapping(RESOURCE_VIDEO)
@CrossOrigin("**")
public class UserController {
    private final IVideoUser userService;

    public UserController(IVideoUser userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> result = userService.getAll();
        return ResponseEntity.ok(result);
    }

}

package com.manuscript.rest.api;

import com.google.firebase.auth.FirebaseAuthException;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.usecase.common.IGetByIdUseCase;
import com.manuscript.infrastructure.firebase.security.Roles.RoleConstants;
import com.manuscript.infrastructure.firebase.service.AuthenticationService;
import com.manuscript.rest.request.UserRequest;
import com.manuscript.rest.response.UserResponse;
import com.sirajtechnologies.iot.dhv.Services.AuthenticationService;
import com.sirajtechnologies.iot.dhv.Services.OrganizationsService;
import com.sirajtechnologies.iot.dhv.Services.UserDataService;
import com.sirajtechnologies.iot.dhv.models.InvitationData;
import com.sirajtechnologies.iot.dhv.models.JsonModels.PackageVM;
import com.sirajtechnologies.iot.dhv.models.JsonModels.User.UserData;
import com.sirajtechnologies.iot.dhv.models.JsonModels.User.UserVM;
import com.sirajtechnologies.iot.dhv.models.User;
import com.sirajtechnologies.iot.dhv.models.enums.PreferencesTypeCommunication;
import com.sirajtechnologies.iot.dhv.repositories.InvitationDataRepository;
import com.sirajtechnologies.iot.dhv.repositories.UserRepository;
import com.sirajtechnologies.iot.dhv.security.Roles.RoleConstants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
@CrossOrigin()
@AllArgsConstructor
public class AccountController {


    private final AuthenticationService  authenticationService;
    private final IGetByIdUseCase<UserModel> getByIdUseCase;


    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequest user) {
        Optional<UserModel> userByUidOpt = getByIdUseCase.findUserByUid(user.getUid());



        }




    @GetMapping("/addRole/{uid}")
    public ResponseEntity<UserResponse> addRole(@PathVariable String uid) throws NoSuchFieldException, RoleNotFoundException, FirebaseAuthException {
        authenticationService.addRole(uid, RoleConstants.SUPER_USER);
        authenticationService.addRole(uid, RoleConstants.REG_USER);
        throw new NoSuchFieldException();   //todo: need to check this exception
    }



}

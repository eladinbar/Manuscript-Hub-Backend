package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.common.enums.Status;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.exceptions.UserAlreadyExistException;
import com.manuscript.core.usecase.custom.user.*;
import com.manuscript.infrastructure.persistence.sql.common.mapping.IRepositoryEntityMapper;
import com.manuscript.infrastructure.persistence.sql.entities.UserEntity;
import com.manuscript.rest.mapping.IRestMapper;
import com.manuscript.rest.request.UserRequest;
import com.manuscript.rest.response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private UserServiceImpl userService;
    @Mock
    private CreateUserImpl createUserUseCase;
    @Mock
    private IRestMapper<UserModel, UserResponse> userResponseMapper;
    @Mock
    private IRestMapper<UserModel, UserRequest> userRequestMapper;
    @Mock
    private IRepositoryEntityMapper<UserModel, UserEntity> mapper;
    @Mock
    private IGetByIdUser getByIdUserUseCase;
    @Mock
    private IGetByUidUser getByUidUserUseCase;
    @Mock
    private IUpdateUser updateUserUseCase;
    @Mock
    private IDeleteUserById deleteUserById;
    @Mock
    private IGetByEmailUser getByEmailUser;
    @BeforeEach
    public void setup(){
        userService = new UserServiceImpl(userResponseMapper, userRequestMapper, getByIdUserUseCase, getByUidUserUseCase, createUserUseCase, updateUserUseCase, deleteUserById, getByEmailUser);
    }
    @Test
    public void getByEmailTest_NonExistent(){
        UserModel userModel = UserModel.builder()
                .email("oz333@gmail.com")
                .uid("123123")
                .name("UidCheck")
                .id(UUID.randomUUID())
                .status(Status.active)
                .createdTime(new Date())
                .updatedTime(new Date())
                .role(Role.User).build();
        Optional<UserModel> opt = Optional.empty();
        Mockito.when(getByEmailUser.getByEmail(any(String.class))).thenReturn(opt);
        assertThrows(NoUserFoundException.class, () -> userService.getByEmail(userModel.getEmail()));
    }
    @Test
    public void getByEmailTest_Exist(){
        UserModel userModel = UserModel.builder()
                .email("ozsderoti@gmail.com")
                .uid("Y082I9QTRIddAXWYkKXKw9DVfeC3")
                .name("Oz")
                .status(Status.active)
                .id(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .role(Role.User).build();

        UserResponse userResponse = UserResponse.builder()
                .email("ozsderoti@gmail.com")
                .uid("Y082I9QTRIddAXWYkKXKw9DVfeC3")
                .id(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .name("Oz")
                .status(Status.active)
                .role(Role.User).build();
        Optional<UserModel> opt = Optional.of(userModel);
        Mockito.when(getByEmailUser.getByEmail(any(String.class))).thenReturn(opt);
        Mockito.when(userResponseMapper.modelToRest(any(UserModel.class))).thenReturn(userResponse);
        UserResponse actual = userService.getByEmail(userModel.getEmail());
        assertEquals(userModel.getUid(), actual.getUid());
        assertEquals(userModel.getEmail(), actual.getEmail());
        assertEquals(userModel.getRole(), actual.getRole());
        assertEquals(userModel.getStatus(), actual.getStatus());
        assertEquals(userModel.getName(), actual.getName());
        assertEquals(userModel.getUpdatedTime(), actual.getUpdatedTime());
        assertEquals(userModel.getCreatedTime(), actual.getCreatedTime());
        assertEquals(userModel.getId(), actual.getId());
        assertEquals(userModel.getPhoneNumber(), actual.getPhoneNumber());
    }
    @Test
    public void getByIdTest_NonExistent(){
        UserModel userModel = UserModel.builder()
                .email("oz333@gmail.com")
                .uid("123123")
                .name("UidCheck")
                .id(UUID.randomUUID())
                .status(Status.active)
                .createdTime(new Date())
                .updatedTime(new Date())
                .role(Role.User).build();
        Optional<UserModel> opt = Optional.empty();
        Mockito.when(getByIdUserUseCase.getById(any(UUID.class))).thenReturn(opt);
        assertThrows(NoUserFoundException.class, () -> userService.getById(userModel.getId()));
    }
    @Test
    public void getByIdTest_Exist(){
        UserModel userModel = UserModel.builder()
                .email("ozsderoti@gmail.com")
                .uid("Y082I9QTRIddAXWYkKXKw9DVfeC3")
                .name("Oz")
                .status(Status.active)
                .id(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .role(Role.User).build();

        UserResponse userResponse = UserResponse.builder()
                .email("ozsderoti@gmail.com")
                .uid("Y082I9QTRIddAXWYkKXKw9DVfeC3")
                .id(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .name("Oz")
                .status(Status.active)
                .role(Role.User).build();
        Optional<UserModel> opt = Optional.of(userModel);
        Mockito.when(getByIdUserUseCase.getById(any(UUID.class))).thenReturn(opt);
        Mockito.when(userResponseMapper.modelToRest(any(UserModel.class))).thenReturn(userResponse);
        UserResponse actual = userService.getById(userModel.getId());
        assertEquals(userModel.getUid(), actual.getUid());
        assertEquals(userModel.getEmail(), actual.getEmail());
        assertEquals(userModel.getRole(), actual.getRole());
        assertEquals(userModel.getStatus(), actual.getStatus());
        assertEquals(userModel.getName(), actual.getName());
        assertEquals(userModel.getUpdatedTime(), actual.getUpdatedTime());
        assertEquals(userModel.getCreatedTime(), actual.getCreatedTime());
        assertEquals(userModel.getId(), actual.getId());
        assertEquals(userModel.getPhoneNumber(), actual.getPhoneNumber());
    }
    @Test
    public void getByUidTest_NonExistent(){
        UserModel userModel = UserModel.builder()
                .email("oz333@gmail.com")
                .uid("123123")
                .name("UidCheck")
                .id(UUID.randomUUID())
                .status(Status.active)
                .createdTime(new Date())
                .updatedTime(new Date())
                .role(Role.User).build();
        Optional<UserModel> opt = Optional.empty();
        Mockito.when(getByUidUserUseCase.getByUid(any(String.class))).thenReturn(opt);
        assertThrows(NoUserFoundException.class, () -> userService.getByUid(userModel.getUid()));
    }
    @Test
    public void getByUidTest_Exist(){
        UserModel userModel = UserModel.builder()
                .email("ozsderoti@gmail.com")
                .uid("Y082I9QTRIddAXWYkKXKw9DVfeC3")
                .name("Oz")
                .status(Status.active)
                .id(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .role(Role.User).build();

        UserResponse userResponse = UserResponse.builder()
                .email("ozsderoti@gmail.com")
                .uid("Y082I9QTRIddAXWYkKXKw9DVfeC3")
                .id(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .name("Oz")
                .status(Status.active)
                .role(Role.User).build();
        Optional<UserModel> opt = Optional.of(userModel);
        Mockito.when(getByUidUserUseCase.getByUid(any(String.class))).thenReturn(opt);
        Mockito.when(userResponseMapper.modelToRest(any(UserModel.class))).thenReturn(userResponse);
        UserResponse actual = userService.getByUid(userModel.getUid());
        assertEquals(userModel.getUid(), actual.getUid());
        assertEquals(userModel.getEmail(), actual.getEmail());
        assertEquals(userModel.getRole(), actual.getRole());
        assertEquals(userModel.getStatus(), actual.getStatus());
        assertEquals(userModel.getName(), actual.getName());
        assertEquals(userModel.getUpdatedTime(), actual.getUpdatedTime());
        assertEquals(userModel.getCreatedTime(), actual.getCreatedTime());
        assertEquals(userModel.getId(), actual.getId());
        assertEquals(userModel.getPhoneNumber(), actual.getPhoneNumber());
    }
    @Test
    public void saveTest_Success() {
        UserRequest userRequest = UserRequest.builder()
                .email("oz123@gmail.com")
                .name("Oz MAD")
                .uid("1234")
                .role(Role.User)
                .phoneNumber("030123012").build();

        UserModel userModel = UserModel.builder()
                .email(userRequest.getEmail())
                .uid(userRequest.getUid())
                .name(userRequest.getName())
                .id(UUID.randomUUID())
                .status(Status.active)
                .createdTime(new Date())
                .updatedTime(new Date())
                .role(userRequest.getRole()).build();

        UserResponse userResponse = UserResponse.builder()
                .email(userRequest.getEmail())
                .uid(userRequest.getUid())
                .name(userRequest.getName())
                .phoneNumber(userRequest.getPhoneNumber())
                .role(userRequest.getRole()).build();

        Mockito.when(getByUidUserUseCase.getByUid(any(String.class))).thenReturn(Optional.empty());
        Mockito.when(getByEmailUser.getByEmail(any(String.class))).thenReturn(Optional.empty());
        Mockito.when(userRequestMapper.restToModel(any(UserRequest.class))).thenReturn(userModel);
        Mockito.when(userResponseMapper.modelToRest(any(UserModel.class))).thenReturn(userResponse);
        Mockito.when(createUserUseCase.create(any(UserModel.class))).thenReturn(userModel);
        assertEquals(userResponse, userService.save(userRequest));
    }
    @Test
    public void saveTestSameUser_Fail() { // Arrange
        UserRequest userRequest = UserRequest.builder()
                .email("oz123@gmail.com")
                .name("Oz MAD")
                .uid("1234")
                .role(Role.User)
                .phoneNumber("030123012").build();

        UserModel userModel = UserModel.builder()
                .email(userRequest.getEmail())
                .uid(userRequest.getUid())
                .name(userRequest.getName())
                .id(UUID.randomUUID())
                .status(Status.active)
                .createdTime(new Date())
                .updatedTime(new Date())
                .role(userRequest.getRole()).build();

        Optional<UserModel> optUserModel = Optional.of(userModel);

        when(getByUidUserUseCase.getByUid(any(String.class))).thenReturn(optUserModel);

        //act
        //assert
        assertThrows(UserAlreadyExistException.class, () -> userService.save(userRequest));

    }
    @Test
    public void updateUserTest_Success() {
        UserRequest userReq = UserRequest.builder()
                .email("oz123@gmail.com")
                .name("Oz MAD")
                .uid("1234")
                .phoneNumber("030123012")
                .role(Role.User)
                .newUser(false).build();


        UserModel userModel = UserModel.builder()
                .email(userReq.getEmail())
                .uid(userReq.getUid())
                .name(userReq.getName())
                .id(UUID.randomUUID())
                .phoneNumber(userReq.getPhoneNumber())
                .status(Status.active)
                .role(userReq.getRole())
                .createdTime(new Date())
                .updatedTime(new Date()).build();

        UserResponse userResponse = UserResponse.builder()
                .email(userReq.getEmail())
                .uid(userReq.getUid())
                .name(userReq.getName())
                .phoneNumber(userReq.getPhoneNumber())
                .role(userReq.getRole()).build();

        Mockito.when(userRequestMapper.restToModel(any(UserRequest.class))).thenReturn(userModel);
        Mockito.when(createUserUseCase.create(any(UserModel.class))).thenReturn(userModel);
        Mockito.when(userResponseMapper.modelToRest(any(UserModel.class))).thenReturn(userResponse);

        //act
        UserResponse userRes = userService.updateUser(userReq);

        //assert
        assertNotNull(userRes);
        assertEquals(userReq.getPhoneNumber(), userRes.getPhoneNumber());
        assertEquals(userReq.getName(), userRes.getName());
        assertEquals(userReq.getUid(), userRes.getUid());
        assertEquals(userReq.getRole(), userRes.getRole());
        assertEquals(userReq.getEmail(), userRes.getEmail());
    }
    @Test
    public void deleteUserTest_Success(){
        UserRequest userRequest = UserRequest.builder()
                .email("oz123@gmail.com")
                .name("Oz MAD")
                .uid("1234")
                .role(Role.User)
                .phoneNumber("030123012").build();

        UserModel userModel = UserModel.builder()
                .email(userRequest.getEmail())
                .uid(userRequest.getUid())
                .name(userRequest.getName())
                .id(UUID.randomUUID())
                .status(Status.active)
                .createdTime(new Date())
                .updatedTime(new Date())
                .role(userRequest.getRole()).build();

        Optional<UserModel> optUserModel = Optional.of(userModel);

        Mockito.when(getByIdUserUseCase.getById(any(UUID.class))).thenReturn(optUserModel);
        doNothing().when(deleteUserById).deleteById(userModel.getId());
        //act
        //assert
        assertDoesNotThrow(() -> userService.deleteUser(userModel.getId()));
    }

    @Test
    public void deleteUserTest_Fail(){
        UserRequest userRequest = UserRequest.builder()
                .email("oz123@gmail.com")
                .name("Oz MAD")
                .uid("1234")
                .role(Role.User)
                .phoneNumber("030123012").build();

        UserModel userModel = UserModel.builder()
                .email(userRequest.getEmail())
                .uid(userRequest.getUid())
                .name(userRequest.getName())
                .id(UUID.randomUUID())
                .status(Status.active)
                .createdTime(new Date())
                .updatedTime(new Date())
                .role(userRequest.getRole()).build();

        Optional<UserModel> optUserModel = Optional.of(userModel);

        Mockito.when(getByIdUserUseCase.getById(any(UUID.class))).thenReturn(Optional.empty());
        //act
        //assert
        assertThrows(NoUserFoundException.class, () -> userService.deleteUser(userModel.getId()));
    }
}

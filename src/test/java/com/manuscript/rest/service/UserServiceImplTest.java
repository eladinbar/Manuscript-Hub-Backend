package com.manuscript.rest.service;

import com.manuscript.core.domain.common.enums.Role;
import com.manuscript.core.domain.user.models.UserModel;
import com.manuscript.core.exceptions.NoUserFoundException;
import com.manuscript.core.usecase.custom.user.*;
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
                .status("active")
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
                .status("active")
                .id(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .role(Role.User).build();

        UserResponse userResponse = UserResponse.builder()
                .email("ozsderoti@gmail.com")
                .uid("Y082I9QTRIddAXWYkKXKw9DVfeC3")
                .userId(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .name("Oz")
                .status("active")
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
        assertEquals(userModel.getId(), actual.getUserId());
        assertEquals(userModel.getPhoneNumber(), actual.getPhoneNumber());
    }
    @Test
    public void getByIdTest_NonExistent(){
        UserModel userModel = UserModel.builder()
                .email("oz333@gmail.com")
                .uid("123123")
                .name("UidCheck")
                .id(UUID.randomUUID())
                .status("active")
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
                .status("active")
                .id(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .role(Role.User).build();

        UserResponse userResponse = UserResponse.builder()
                .email("ozsderoti@gmail.com")
                .uid("Y082I9QTRIddAXWYkKXKw9DVfeC3")
                .userId(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .name("Oz")
                .status("active")
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
        assertEquals(userModel.getId(), actual.getUserId());
        assertEquals(userModel.getPhoneNumber(), actual.getPhoneNumber());
    }
    @Test
    public void getByUidTest_NonExistent(){
        UserModel userModel = UserModel.builder()
                .email("oz333@gmail.com")
                .uid("123123")
                .name("UidCheck")
                .id(UUID.randomUUID())
                .status("active")
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
                .status("active")
                .id(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .role(Role.User).build();

        UserResponse userResponse = UserResponse.builder()
                .email("ozsderoti@gmail.com")
                .uid("Y082I9QTRIddAXWYkKXKw9DVfeC3")
                .userId(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .name("Oz")
                .status("active")
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
        assertEquals(userModel.getId(), actual.getUserId());
        assertEquals(userModel.getPhoneNumber(), actual.getPhoneNumber());
    }
    @Test
    public void saveTest_Success() { // Arrange
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
                .status("active")
                .createdTime(new Date())
                .updatedTime(new Date())
                .role(userRequest.getRole()).build();

        Mockito.when(createUserUseCase.create(any(UserModel.class))).thenReturn(userModel);
        assertEquals(userModel, userService.save(userRequest));
    }
    @Test
    public void updateUserTest_Success() { // Arrange
        UserRequest userReq = UserRequest.builder()
                .email("oz123@gmail.com")
                .name("Oz MAD")
                .uid("1234")
                .phoneNumber("030123012")
                .newUser(false).build();


        UserModel userModel = UserModel.builder()
                .email(userReq.getEmail())
                .uid(userReq.getUid())
                .name(userReq.getName())
                .id(UUID.randomUUID())
                .updatedTime(new Date()).build();

        Mockito.when(updateUserUseCase.update(any(UserModel.class))).thenReturn(userModel);
        assertEquals(userModel, userService.updateUser(userReq));
    }
    @Test
    public void deleteUserTest(){
        UserModel userModel = UserModel.builder()
                .email("ozsderoti@gmail.com")
                .uid("Y082I9QTRIddAXWYkKXKw9DVfeC3")
                .name("Oz")
                .status("active")
                .id(UUID.fromString("711b19f4-104e-4aeb-9439-df29aa2d2dc4"))
                .role(Role.User).build();

        Mockito.doNothing().when(deleteUserById).deleteById(any(UserModel.class));
        Mockito.when(getByIdUserUseCase.getById(any(UUID.class))).thenReturn(Optional.of(userModel));
        userService.deleteUser(userModel.getId());
        assertNull(getByIdUserUseCase.getById(userModel.getId()));
        //TODO its a unit test, cant use real repo to check if deleted.
    }
}

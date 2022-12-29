package com.github.nbaravik.springuser.service;

import com.github.nbaravik.springuser.dto.UserDTO;
import com.github.nbaravik.springuser.dto.UserIdDTO;
import com.github.nbaravik.springuser.exception.UserNotFoundException;
import com.github.nbaravik.springuser.model.StatusMode;
import com.github.nbaravik.springuser.model.User;
import com.github.nbaravik.springuser.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService service;
    @MockBean
    private UserRepository repository;

    @Test
    public void createUserTest_UserIdDTOReturned() {

        User user = User.builder().
                id("63aadae89c5ac54dfcbd88e2").
                name("Alexander").
                email("alex@gmail.com").
                uri("https://www.istockphoto.com/photos/alex").
                status(StatusMode.ONLINE).build();

        UserDTO userDTO = UserDTO.builder().
                name("Alexander").
                email("alex@gmail.com").
                uri("https://www.istockphoto.com/photos/alex").
                status("ONLINE").build();

        Mockito.when(repository.save(Mockito.any())).thenReturn(user);

        UserIdDTO created = service.createUser(userDTO);

        Assert.assertNotNull(created);
        Assert.assertEquals(user.getId(), created.getId());
    }

    @Test
    public void createUserTest_ValidationFailed() {

        UserDTO userDTO = UserDTO.builder().build();
        try {
            service.createUser(userDTO);
            Assert.assertFalse(true);
        } catch (ConstraintViolationException ex) {
            Assert.assertTrue(true);
            Assert.assertEquals(ex.getConstraintViolations().size(), 3);
        }
    }

    @Test
    public void getUserByIdTest_UserDTOShouldBeReturned() {

        String id = "63ab1be6d9f8864ff2fd51c4";
        User user = User.builder().
                id(id).
                name("Daniel").
                email("dani@gmail.com").
                uri("https://www.istockphoto.com/photos/dani").
                status(StatusMode.OFFLINE).build();

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.of(user));

        UserDTO found = service.getUserById(id);

        Assert.assertNotNull(found);
        Assert.assertEquals(user.getName(), found.getName());
        Assert.assertEquals(user.getStatus().toString(), found.getStatus());
    }

    @Test
    public void getUserByIdTest_UserNotFoundExceptionReturned() {

        Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());
        try {
            service.getUserById("63aadae89c5ac54dfcbd88e2");
        } catch (UserNotFoundException ex) {
            Assert.assertTrue(true);
            Assert.assertEquals("User with ID=63aadae89c5ac54dfcbd88e2 does not exist in the DB.", ex.getMessage());
        }
    }
}

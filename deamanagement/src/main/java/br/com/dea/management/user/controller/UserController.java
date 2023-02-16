package br.com.dea.management.user.controller;

import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.dto.UserDto;
import br.com.dea.management.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/user/all")
    public List<User> getUsersAllRaw() {
        return this.userService.findAllUsers();
    }

    @GetMapping(value = "/user/without-pagination")
    public List<UserDto> getUsersWithoutPagination() {
        List<User> users = this.userService.findAllUsers();
        return UserDto.fromUsers(users);
    }

    @GetMapping("/user")
    public Page<UserDto> getUsers(@RequestParam(required = true) Integer page,
                                  @RequestParam(required = true) Integer pageSize) {

        log.info(String.format("Fetching users : page : %s : pageSize", page, pageSize));

        Page<User> usersPaged = this.userService.findAllUsersPaginated(page, pageSize);
        Page<UserDto> users = usersPaged.map(user -> UserDto.fromUser(user));

        log.info(String.format("Users loaded successfully : Users : %s : pageSize", users.getContent()));

        return users;

    }

    @GetMapping("/user/{id}")
    UserDto getUserById(@PathVariable Long id) {

            log.info(String.format("Fetching user by id : Id : %s", id));

            UserDto user = UserDto.fromUser(this.userService.findUserById(id));

            log.info(String.format("User loaded successfully : User : %s", user));

            return user;

        }

}

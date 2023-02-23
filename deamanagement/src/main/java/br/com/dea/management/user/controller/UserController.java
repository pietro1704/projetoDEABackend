package br.com.dea.management.user.controller;

import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.dto.UserDto;
import br.com.dea.management.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "user", description = "The User API")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Load the list of users with all attributes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Error fetching user list"),
    })
    @Deprecated
    @GetMapping(value = "/user/all")
    public List<User> getUsersAllRaw() {
        return this.userService.findAllUsers();
    }

    @Operation(summary = "Load all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "500", description = "Error fetching user list"),
    })
    @Deprecated
    @GetMapping(value = "/user/without-pagination")
    public List<UserDto> getUsersWithoutPagination() {
        List<User> users = this.userService.findAllUsers();
        return UserDto.fromUsers(users);
    }

    @Operation(summary = "Load the list of users paginated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Page or Page Size params not valid"),
            @ApiResponse(responseCode = "500", description = "Error fetching user list"),
    })
    @GetMapping("/user")
    public Page<UserDto> getUsers(@RequestParam(required = true) Integer page,
                                  @RequestParam(required = true) Integer pageSize) {

        log.info(String.format("Fetching users : page : %s : pageSize", page, pageSize));

        Page<User> usersPaged = this.userService.findAllUsersPaginated(page, pageSize);
        Page<UserDto> users = usersPaged.map(user -> UserDto.fromUser(user));

        log.info(String.format("Users loaded successfully : Users : %s : pageSize", users.getContent()));

        return users;

    }

    @Operation(summary = "Load the user by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "user Id invalid"),
            @ApiResponse(responseCode = "404", description = "user Not found"),
            @ApiResponse(responseCode = "500", description = "Error fetching user list"),
    })
    @GetMapping("/user/{id}")
    UserDto getUserById(@PathVariable Long id) {

            log.info(String.format("Fetching user by id : Id : %s", id));

            UserDto user = UserDto.fromUser(this.userService.findUserById(id));

            log.info(String.format("User loaded successfully : User : %s", user));

            return user;

        }

}

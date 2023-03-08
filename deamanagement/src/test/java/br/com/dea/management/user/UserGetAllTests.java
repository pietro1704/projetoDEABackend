package br.com.dea.management.user;

import br.com.dea.management.user.domain.User;
import br.com.dea.management.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class UserGetAllTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void beforeEach() {
        this.userRepository.deleteAll();
        this.createFakeUsers();
        log.info("Before each test in " + UserGetAllTests.class.getSimpleName());
    }

    @BeforeAll
    void beforeSuiteTest() {
        log.info("Before all tests in " + UserGetAllTests.class.getSimpleName());
    }

    @Test
    void whenRequestingUserAllRawList_thenReturnListOfUsers() throws Exception {
        mockMvc.perform(get("/user/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(100)))
                .andExpect(jsonPath("$[0].name", is("name 0")))
                .andExpect(jsonPath("$[99].name", is("name 99")));
    }
    @Test
    void whenRequestingUserAllRawDTOList_thenReturnListOfUsers() throws Exception {
        mockMvc.perform(get("/user/without-pagination"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("name 0")));
    }

    @Test
    void whenRequestingUserList_thenReturnListOfUserPaginatedSuccessfully() throws Exception {
        mockMvc.perform(get("/user?page=0&pageSize=4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.content[0].name", is("name 0")))
                .andExpect(jsonPath("$.content[0].email", is("email 0")))
                .andExpect(jsonPath("$.content[0].linkedin", is("linkedin 0")))
                .andExpect(jsonPath("$.content[1].name", is("name 1")))
                .andExpect(jsonPath("$.content[1].email", is("email 1")))
                .andExpect(jsonPath("$.content[1].linkedin", is("linkedin 1")))
                .andExpect(jsonPath("$.content[2].name", is("name 10")))
                .andExpect(jsonPath("$.content[2].email", is("email 10")))
                .andExpect(jsonPath("$.content[2].linkedin", is("linkedin 10")))
                .andExpect(jsonPath("$.content[3].name", is("name 11")))
                .andExpect(jsonPath("$.content[3].email", is("email 11")))
                .andExpect(jsonPath("$.content[3].linkedin", is("linkedin 11")));
    }

    @Test
    void whenRequestingUserListAndPageQueryParamIsInvalid_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/user?page=xx&pageSize=4"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingUserListAndPageQueryParamIsMissing_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/user?pageSize=4"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingUserListAndPageSizeQueryParamIsInvalid_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/user?pageSize=xx&page=4"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    @Test
    void whenRequestingUserListAndPageSizeQueryParamIsMissing_thenReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/user?page=0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.details").isArray())
                .andExpect(jsonPath("$.details", hasSize(1)));
    }

    private void createFakeUsers() {
        log.info("creating fake users");
        for (int i = 0; i < 100; i++) {
            User u = User.builder()
                    .email("email " + i)
                    .name("name " + i)
                    .linkedin("linkedin " + i)
                    .password("passwordpassword " + i)
                    .build();
            log.info("Saving user # %d", i);
            log.info(String.format("Saving User name : %s", u.getName()));
            log.info(String.format("Saving User email: %s", u.getEmail()));
            log.info(String.format("Saving User linkedin: %s", u.getLinkedin()));
            log.info(String.format("Saving User password: %s", u.getPassword()));
            this.userRepository.save(u);
        }
    }

}


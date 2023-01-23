package com.revature;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.revature.models.User;
import com.revature.repositories.FollowRepository;
import com.revature.repositories.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Optional;

import org.aspectj.lang.annotation.After;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = SocialMediaApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)

public class TestUserFollowService {

    String followUser = "/user/follow/";
    String unFollowUser = "/user/unfollow/";
    String getFollowers = "/user/followers";
    String getFollowing = "/user/followings";

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FollowRepository followRepo;

    HashMap<String, Object> sessionattrUser1;
    HashMap<String, Object> sessionattrUser2;

    User testUser1;
    User testUser2;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .build();

        testUser1 = userRepo.findByUserName("test1").orElse(new User());
        testUser2 = userRepo.findByUserName("test2").orElse(new User());

        sessionattrUser1 = new HashMap<String, Object>();
        sessionattrUser1.put("user", testUser1);

        sessionattrUser2 = new HashMap<String, Object>();
        sessionattrUser2.put("user", testUser2);

    }

    @Test
    @DisplayName("1. Test Follow One User - User Exists")
    public void testFollowUserExists() throws Exception {

        // Test if
        this.mockMvc.perform(post(followUser + testUser2.getUserName()).sessionAttrs(sessionattrUser1)
                .contentType(APPLICATION_JSON)
                .content(""))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$").doesNotExist());

    }

}

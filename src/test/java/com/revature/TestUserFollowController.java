package com.revature;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import com.revature.models.User;
import com.revature.repositories.FollowRepository;
import com.revature.repositories.UserRepository;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.hamcrest.Matchers.*;

@SpringBootTest(classes = SocialMediaApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)

public class TestUserFollowController {

        String followUser = "/user/follow/";
        String unFollowUser = "/user/unfollow/";
        // String getFollowers = "/user/{username}/followers";
        // String getFollowing = "/user/{username}/following";

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

        public String constructGetFollowerUrl(String username) {
                return "/user/" + username + "/followers";
        }

        public String constructGetFollowingUrl(String username) {
                return "/user/" + username + "/followings";
        }

        @Test
        @DisplayName("1. Test Follow One User - User Exists")
        public void testFollowUserExists() throws Exception {

                // Test if
                this.mockMvc.perform(post(followUser + testUser2.getUserName()).sessionAttrs(sessionattrUser1)
                                .contentType(APPLICATION_JSON)
                                .content(""))

                                .andExpect(status().isAccepted()).andExpect(jsonPath("$").doesNotExist());

        }

        @Test
        @DisplayName("2. Test Follow One User - User Not Exists")
        public void testFollowUserNotExists() throws Exception {

                // Test if
                this.mockMvc.perform(post(followUser + "randomusername").sessionAttrs(sessionattrUser1)
                                .contentType(APPLICATION_JSON)
                                .content(""))

                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$").isString());

        }

        @Test
        @DisplayName("3. Test Follow User - Already following")
        public void testFollowUserAlreadyFollowing() throws Exception {

                // Follow user

                this.mockMvc.perform(post(followUser +
                                testUser2.getUserName()).sessionAttrs(sessionattrUser1)
                                .contentType(APPLICATION_JSON)
                                .content(""))

                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$").doesNotExist());

                // Follow again
                this.mockMvc.perform(post(followUser + testUser2.getUserName()).sessionAttrs(sessionattrUser1)
                                .contentType(APPLICATION_JSON)
                                .content(""))

                                .andExpect(status().isConflict())
                                .andExpect(jsonPath("$").isString());

        }

        @Test
        @DisplayName("4. Follow self")
        public void testFollowSelf() throws Exception {

                // Follow self
                this.mockMvc.perform(post(followUser + testUser1.getUserName()).sessionAttrs(sessionattrUser1)
                                .contentType(APPLICATION_JSON)
                                .content(""))

                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$").isString());

        }

        @Test
        @DisplayName("5. Test unfollow - is following")
        public void testUnfollowIsFollowing() throws Exception {
                // Follow user

                this.mockMvc.perform(post(followUser +
                                testUser2.getUserName()).sessionAttrs(sessionattrUser1)
                                .contentType(APPLICATION_JSON)
                                .content(""))

                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$").doesNotExist());

                // unfollow
                this.mockMvc.perform(delete(unFollowUser + testUser2.getUserName()).sessionAttrs(sessionattrUser1)
                                .contentType(APPLICATION_JSON)
                                .content(""))

                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$").doesNotExist());

        }

        @Test
        @DisplayName("6. Test unfollow - not following")
        public void testUnfollowNotFollowing() throws Exception {

                // unfollow
                this.mockMvc.perform(delete(unFollowUser + testUser2.getUserName()).sessionAttrs(sessionattrUser1)
                                .contentType(APPLICATION_JSON)
                                .content(""))

                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath("$").isString());

        }

        @Test
        @DisplayName("7. Test unfollow - unfollow self")
        public void testUnfollowSelf() throws Exception {

                // unfollow
                this.mockMvc.perform(delete(unFollowUser + testUser1.getUserName()).sessionAttrs(sessionattrUser1)
                                .contentType(APPLICATION_JSON)
                                .content(""))

                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath("$").isString());

        }

        @Test
        @DisplayName("8. Test - get followers Empty")
        public void testGetFollowersEmpty() throws Exception {

                this.mockMvc.perform(
                                get(constructGetFollowerUrl(testUser1.getUserName())).sessionAttrs(sessionattrUser1)

                                                .contentType(APPLICATION_JSON)
                                                .content(""))

                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$", hasSize(0)));

        }

        @Test
        @DisplayName("9. Test - get followers size 1")
        public void testGetFollowersOne() throws Exception {

                this.mockMvc.perform(post(followUser +
                                testUser2.getUserName()).sessionAttrs(sessionattrUser1)
                                .contentType(APPLICATION_JSON)
                                .content(""))

                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$").doesNotExist());

                this.mockMvc.perform(
                                get(constructGetFollowerUrl(testUser2.getUserName())).sessionAttrs(sessionattrUser2)

                                                .contentType(APPLICATION_JSON)
                                                .content(""))

                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$", hasSize(1)));
        }

        @Test
        @DisplayName("10. Test - get who im following empty")
        public void testGetFollowings() throws Exception {

                // unfollow
                this.mockMvc.perform(
                                get(constructGetFollowingUrl(testUser1.getUserName())).sessionAttrs(sessionattrUser1)
                                                .param("username", testUser1.getUserName())
                                                .contentType(APPLICATION_JSON)
                                                .content(""))

                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$").isArray());

        }

        @Test
        @DisplayName("11. Test - get who im following size 1")
        public void testGetFollowingsSizeOne() throws Exception {
                // follow
                this.mockMvc.perform(post(followUser +
                                testUser2.getUserName()).sessionAttrs(sessionattrUser1)
                                .contentType(APPLICATION_JSON)
                                .content(""))

                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$").doesNotExist());
                // check size
                this.mockMvc.perform(
                                get(constructGetFollowingUrl(testUser1.getUserName())).sessionAttrs(sessionattrUser1)
                                                .param("username", testUser1.getUserName())
                                                .contentType(APPLICATION_JSON)
                                                .content(""))

                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$", hasSize(1)));

                // make sure followers empty
                // check size
                this.mockMvc.perform(
                                get(constructGetFollowerUrl(testUser1.getUserName())).sessionAttrs(sessionattrUser1)
                                                .param("username", testUser1.getUserName())
                                                .contentType(APPLICATION_JSON)
                                                .content(""))

                                .andExpect(status().isAccepted())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$", hasSize(0)));

        }

}

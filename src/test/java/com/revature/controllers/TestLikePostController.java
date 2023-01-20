package com.revature.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialMediaApplication;
import com.revature.models.Post;
import com.revature.models.PostLike;
import com.revature.models.User;
import com.revature.services.PostService;

@SpringBootTest(classes = SocialMediaApplication.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
class TestLikePostController {
	
	String likeUrl = "/post/like/";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PostService pServ;
    
    PostController pControl;
    
    ObjectMapper om = new ObjectMapper();
    
    User testUser;
    Post testPost;
    PostLike testPostLike;
    
    @BeforeEach
    public void setup() {
    	testUser = new User();
    	testPost = new Post();
    	
    	testPostLike = new PostLike();
    	testPostLike.setPost(testPost);
    	testPostLike.setUser(testUser);
    }
	
    
    @Test
    @DisplayName("Testing postNewLike")
	public void testNewLike() throws Exception {
    	when(pServ.insertLike(testPostLike)).thenReturn(testPostLike);

    	String inputJson = "{\"post\":{\"id\":0,\"text\":null,\"imageUrl\":null,\"likeCount\":0,\"comments\":null,"
    			+ "\"author\":null,\"postType\":null},\"user\":{\"id\":0,\"email\":null,\"firstName\":null,"
    			+ "\"lastName\":null,\"userName\":null,\"aboutMe\":\"About Me\",\"followersCount\":0,\"followingsCount\":0}}";
    	String resultJson = om.writeValueAsString(testPostLike);
    	
    	mockMvc.perform(post(likeUrl).content(inputJson).contentType(APPLICATION_JSON))
    					.andExpect(status().isOk()).andExpect(content().json(resultJson));
    }
//    
//    @Test
//    @DisplayName("1. Test Liking Post -  Post already liked")
//    @Transactional
//    public void testLikePostAlreadyLiked() throws Exception {
//    	Post testPost = postRepo.findById(5).orElse(new Post());
//    	testPostLike.setPost(testPost);
//    	
//    	postRepo
//    	
//    }
    
	//3. Test liking Post - Post doesn't exist
	
	//DELETE /like
	//4. Test unliking Post - Post already liked
	//5. Test unliking Post - Post not already liked
	//6. Test unliking Post - Post doesn't exist
	
	//GET /like/{postId}/{userId}
	//7. Test checking Like - User liked Post
	//8. Test checking Like - User hasn't liked Post
	//9. Test checking Like - Post liked by other User
	
	
}

package com.revature;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.PostController;
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
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    PostService pServ;
    
    PostController pCon;
    
    ObjectMapper om = new ObjectMapper();
    
    User testUser;
    PostLike testPostLike;
    
    @BeforeEach
    public void setup(WebApplicationContext webApplicationContext) {
//    	mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//    	
//    	testUser = userRepo.findByUserName("jcanales").orElse(new User());
//    	
//    	testPostLike = new PostLike();
//    	testPostLike.setUser(testUser);
    }
	
	//POST /like
    
//    @Test
//    @DisplayName("1. Test Liking Post")
//	public void testLikePostNotLiked() throws Exception {
//    	
//    	Post testPost = postRepo.findById(5).orElse(new Post());
//    	testPostLike.setPost(testPost);
//    	
//    	String postLikeJson = om.writeValueAsString(testPostLike);
//    	
//    	mockMvc.perform(post(likeUrl)
//    			.contentType(APPLICATION_JSON).content(postLikeJson))
//    			.andDo(print()).andExpect(status().isOk());
// 
//    }
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

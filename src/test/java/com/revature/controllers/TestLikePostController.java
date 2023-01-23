package com.revature.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.SocialMediaApplication;
import com.revature.models.Post;
import com.revature.models.PostLike;
import com.revature.models.PostLikeKey;
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
    String inputJson = "{\"post\":{\"id\":0,\"text\":null,\"imageUrl\":null,\"likeCount\":0,\"comments\":null,"
			+ "\"author\":null,\"postType\":null},\"user\":{\"id\":0,\"email\":null,\"firstName\":null,"
			+ "\"lastName\":null,\"userName\":null,\"aboutMe\":\"About Me\",\"followersCount\":0,\"followingsCount\":0}}";
    
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

    	String resultJson = om.writeValueAsString(testPostLike);
    	
    	mockMvc.perform(post(likeUrl).content(inputJson).contentType(APPLICATION_JSON))
    					.andExpect(status().isOk()).andExpect(content().json(resultJson));
    }
    
    @Test
    @DisplayName("Testing deleteLike")
    public void testDeleteLike() throws Exception {
    	mockMvc.perform(post(likeUrl).content(inputJson).contentType(APPLICATION_JSON))
		.andExpect(status().isOk());
    }
    
	@Test
	@DisplayName("Testing checkUserLikedPost")
	public void testCheckLike() throws Exception {
		when(pServ.likeExists(new PostLikeKey(0,0))).thenReturn(true);
		
		mockMvc.perform(get(likeUrl + "0/0")).andExpect(status().isOk())
						.andExpect(content().string("true"));
	}
}

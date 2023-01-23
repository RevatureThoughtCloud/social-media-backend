package com.revature.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Post;
import com.revature.models.User;
import com.revature.services.PostService;

@WebMvcTest(PostController.class)
class TestPostController {
	private String postUrl = "/post/";
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService pServ;
    
    private ObjectMapper om = new ObjectMapper();
    
    private Post testPost;
    private User testUser;
    private MockHttpSession session;
    
    @BeforeEach
    public void setup() {
    	testPost = new Post();
    	
    	testUser = new User();
    	testUser.setUserName("CaveJohnson");
    	
    	session = new MockHttpSession();
    	session.setAttribute("user", testUser);
    }
    
	@Test
	@DisplayName("Test getAllPosts")
	void testGetAllPosts() throws Exception {
		when(pServ.getAll()).thenReturn(List.of(testPost));
		
		String resultJson = om.writeValueAsString(List.of(testPost));
		
		mockMvc.perform(get(postUrl)).andExpect(status().isOk()).andExpect(content().json(resultJson));
	}
	
	@Test
	@DisplayName("Test getAllByAuthor")
	void testGetAllAuthor() throws Exception {
		when(pServ.getAllByAuthorId(0)).thenReturn(List.of(testPost));
		
		String resultJson = om.writeValueAsString(List.of(testPost));
		
		mockMvc.perform(get(postUrl + "author/0/")).andExpect(status().isOk()).andExpect(content().json(resultJson));
	}
	
	@Test
	@DisplayName("Test upsertPost")
	void testUpsertPost() throws Exception {
		when(pServ.upsert(testPost)).thenReturn(testPost);
		
		String inputJson = "{\"id\":0,\"text\":null,\"imageUrl\":null,\"likeCount\":0,\"comments\":null,\"author\":null,\"postType\":null}";
		String resultJson = om.writeValueAsString(testPost);
		
		mockMvc.perform(put(postUrl).session(session).content(inputJson).contentType(APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().json(resultJson));
	}
	
	@Test
	@DisplayName("Test getPostById")
	void testGetById() throws Exception {
		Optional<Post> optResult = Optional.of(testPost);
		when(pServ.getById(0)).thenReturn(optResult);
		
		String resultJson = om.writeValueAsString(testPost);
		
		mockMvc.perform(get(postUrl + "/0/")).andExpect(status().isOk())
							.andExpect(content().json(resultJson));
	}
	
	@Test
	@DisplayName("Test getAllTopPosts")
	void testGetAllTop() throws Exception {
		when(pServ.getAllTop()).thenReturn(List.of(testPost));
		
		String resultJson = om.writeValueAsString(List.of(testPost));
		
		mockMvc.perform(get(postUrl + "/feed/")).andExpect(status().isOk())
						.andExpect(content().json(resultJson));
	}
	
	@Test
	@DisplayName("Test deletePostById")
	void testDeletePost() throws Exception {
		mockMvc.perform(delete(postUrl + "/0/")).andExpect(status().isNoContent());
	}
}

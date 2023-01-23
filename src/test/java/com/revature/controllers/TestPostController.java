package com.revature.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.Post;
import com.revature.services.PostService;

class TestPostController {
	private String postUrl = "/post/";
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService pServ;
    
    private ObjectMapper om = new ObjectMapper();
    
    private Post testPost;
    
    @BeforeEach
    public void setup() {
    	testPost = new Post();
    	
    }
    
//	@Test
//	@DisplayName("Test getAllPosts")
//	void testGetAllPosts() throws Exception{
//		when(pServ.getAll()).thenReturn(List.of(testPost));
//		
//		String resultJson = om.writeValueAsString(List.of(testPost));
//		
//		mockMvc.perform(get(postUrl)).andExpect(status().isOk()).andExpect(content().json(resultJson));
//	}
//	
	/*
	 * public ResponseEntity<List<Post>> getAllPosts()
	 * public ResponseEntity<List<Post>> getAllByAuthor(@PathVariable int id)
	 * public ResponseEntity<Post> upsertPost(@RequestBody Post post)
	 * public ResponseEntity<Post> getPostById(@PathVariable int postId) 
	 * public ResponseEntity<List<Post>> getAllTopPosts()
	 * public ResponseEntity deletePostById(@PathVariable int postId)
	 * 
	 */

}

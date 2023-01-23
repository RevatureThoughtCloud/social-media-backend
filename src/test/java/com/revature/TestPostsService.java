package com.revature;

import com.revature.models.Notification;
import com.revature.models.Post;
import com.revature.models.PostLike;
import com.revature.models.PostType;
import com.revature.models.User;
import com.revature.repositories.FollowRepository;
import com.revature.repositories.PostLikeRepository;
import com.revature.repositories.PostRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.PostService;
import com.revature.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TestPostsService {

        @Mock
        private PostRepository postRepo;
        @Mock
        private PostLikeRepository postLikeRepo;
        @InjectMocks
        private PostService postService;

        @BeforeEach
        public void setup() {

        }

        @Test
        void findAllPosts() {
                List<Post> comments = new ArrayList<>();
                Set<PostLike> likes = new HashSet<>();
                List<Notification> noti = new ArrayList<>();
                Post ex1 = new Post(1, "some", "", 0, comments, noti, new User(), PostType.Top, likes);
                Post[] p = { ex1 };
                List<Post> posts = new ArrayList<>(Arrays.asList(p));

                when(postRepo.findAll()).thenReturn(posts);

                List<Post> result = postService.getAll();

                assertEquals(posts, result);
                verify(postRepo, times(1)).findAll();

        }

        @Test
        void upsertTestInsert() {

                User _user = new User("test@gmail", "password", "test", "test", "randomUser");
                List<Post> comments = new ArrayList<>();
                Set<PostLike> likes = new HashSet<>();
                List<Notification> noti = new ArrayList<>();
                Post ex1 = new Post(1, "some", "", 0, comments, noti, _user, PostType.Top, likes);
                // Post[] p = { ex1 };
                // List<Post> posts = new ArrayList<>();

                when(postRepo.save(ex1)).thenReturn(ex1);

                Post result = postService.upsert(ex1);

                assertEquals(ex1, result);
                verify(postRepo, times(1)).save(ex1);

        }

        @Test
        void getTopPostOnly() {

                User _user = new User("test@gmail", "password", "test", "test", "randomUser");
                List<Post> comments = new ArrayList<>();
                Set<PostLike> likes = new HashSet<>();
                List<Notification> noti = new ArrayList<>();
                Post ex1 = new Post(1, "some", "", 0, comments, noti, _user, PostType.Top, likes);
                Post[] p = { ex1 };
                List<Post> posts = new ArrayList<>();
                posts.add(ex1);

                when(postRepo.findAllByPostType(PostType.Top)).thenReturn(posts);

                List<Post> result = postService.getAllTop();

                assertEquals(posts, result);
                verify(postRepo, times(1)).findAllByPostType(PostType.Top);

        }

        @Test
        void getPostByIdUserExists() {

                User _user = new User("test@gmail", "password", "test", "test", "randomUser");
                _user.setId(2);
                List<Post> comments = new ArrayList<>();
                Set<PostLike> likes = new HashSet<>();
                List<Notification> noti = new ArrayList<>();
                Post ex1 = new Post(1, "some", "", 0, comments, noti, _user, PostType.Top, likes);
                Post[] p = { ex1 };
                List<Post> posts = new ArrayList<>();
                posts.add(ex1);

                when(postRepo.findAllByAuthorId(_user.getId())).thenReturn(posts);

                List<Post> result = postService.getAllByAuthorId(_user.getId());

                assertEquals(posts, result);
                verify(postRepo, times(1)).findAllByAuthorId(_user.getId());

        }

        @Test
        void getPostByIdUserNotExists() {

                User _user = new User("test@gmail", "password", "test", "test", "randomUser");
                _user.setId(2);
                List<Post> comments = new ArrayList<>();
                Set<PostLike> likes = new HashSet<>();
                List<Notification> noti = new ArrayList<>();
                Post ex1 = new Post(1, "some", "", 0, comments, noti, _user, PostType.Top, likes);
                Post[] p = { ex1 };
                List<Post> posts = new ArrayList<>();
                posts.add(ex1);
                List<Post> posts2 = new ArrayList<>();
                when(postRepo.findAllByAuthorId(100)).thenReturn(posts2);

                List<Post> result = postService.getAllByAuthorId(100);

                assertEquals(0, result.size());
                verify(postRepo, times(1)).findAllByAuthorId(100);

        }

}

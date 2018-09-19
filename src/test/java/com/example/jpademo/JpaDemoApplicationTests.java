package com.example.jpademo;

import com.example.jpademo.entities.Post;
import com.example.jpademo.entities.User;
import com.example.jpademo.repositories.PostRepository;
import com.example.jpademo.repositories.UserRespoitory;
import javafx.geometry.Pos;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaDemoApplicationTests {

	@Autowired
	public PostRepository postRepo;

	@Autowired
	public UserRespoitory userRepo;

	@After
	public void nukeRepos() {
		try{ userRepo.deleteAll(); } catch(Exception e) {}
		try{ postRepo.deleteAll(); } catch(Exception e) {}
		assertEquals(userRepo.findAll().size(),0);
		assertEquals(postRepo.findAll().size(),0);
	}

	@Test
	public void UserCascadesNewPosts() {
		User user1 = new User();
		Post post1 = new Post();
		Post post2 = new Post();
		user1.id = 1L;
		post1.id = 1L;
		post2.id = 2L;
		user1.name = "Kevin";
		post1.author = user1;
		post2.author = user1;
		post1.contents = "Hello";
		post2.contents = "World";
		user1.posts = new ArrayList<Post>();
		user1.posts.add(post1);
		user1.posts.add(post2);

		userRepo.save(user1);
		//postRepo.save(post1);
		//postRepo.save(post2);

		//List<User> allUsers = userRepo.findAll();
		List<Post> allPosts = postRepo.findAll();

		assertEquals(allPosts.size(),2);
	}

	@Test
	public void PostsCascadeAuthor() {
		User user1 = new User();
		Post post1 = new Post();
		Post post2 = new Post();
		user1.id = 10L;
		post1.id = 10L;
		post2.id = 20L;
		user1.name = "Kevin";
		post1.author = user1;
		post2.author = user1;
		post1.contents = "Hello";
		post2.contents = "World";
		user1.posts = new ArrayList<Post>();
		user1.posts.add(post1);
		user1.posts.add(post2);

		//userRepo.save(user1);
		postRepo.save(post1);
		postRepo.save(post2);

		List<User> allUsers = userRepo.findAll();
		//List<Post> allPosts = postRepo.findAll();

		assertEquals(allUsers.size(),1);
		assertEquals(userRepo.findById(10L).get().posts.size(),2);
	}

	@Test
	public void UserCascadesLikesToPost() {
		User user1 = new User();
		User user2 = new User();
		Post post1 = new Post();

		user1.id = 100L;
		user2.id = 200L;

		post1.id = 100L;

		user1.name = "Kevin";
		user2.name = "Mao";

		post1.author = user1;

		post1.contents = "Hello";

		user1.posts = new ArrayList<Post>();
		user1.posts.add(post1);

		user2.posts = new ArrayList<Post>();

		post1.likes = new ArrayList<User>();
		//post1.likes.add(user1);
		//post1.likes.add(user2);

		user1.likedPosts = new ArrayList<Post>();
		user1.likedPosts.add(post1);

		user2.likedPosts = new ArrayList<Post>();
		user2.likedPosts.add(post1);

		userRepo.save(user1);
		userRepo.save(user2);
		//postRepo.save(post1);

		List<User> allUsers = userRepo.findAll();
		List<Post> allPosts = postRepo.findAll();

		assertEquals(postRepo.findById(100L).get().likes.size(),2);
	}

	@Test
	public void PostsDONTCascadeLikesToUsers() {
		User user1 = new User();
		User user2 = new User();
		Post post1 = new Post();

		user1.id = 1000L;
		user2.id = 2000L;

		post1.id = 1000L;

		user1.name = "Kevin";
		user2.name = "Mao";

		post1.author = user1;

		post1.contents = "Hello";

		user1.posts = new ArrayList<Post>();
		user1.posts.add(post1);

		user2.posts = new ArrayList<Post>();

		post1.likes = new ArrayList<User>();
		post1.likes.add(user1);
		post1.likes.add(user2);

		//user1.likedPosts = new ArrayList<Post>();
		//user1.likedPosts.add(post1);

		//user2.likedPosts = new ArrayList<Post>();
		//user2.likedPosts.add(post1);

		//userRepo.save(user1);
		//userRepo.save(user2);
		postRepo.save(post1);

		List<User> allUsers = userRepo.findAll();
		List<Post> allPosts = postRepo.findAll();

		assertNotEquals(userRepo.findById(1000L).get().likedPosts.size(),1);
	}

	@Test
	public void PostCanUseExistingUser() {
		User user1 = new User();
		Post post1 = new Post();
		Post post2 = new Post();
		user1.id = 1L;
		post1.id = 1L;
		post2.id = 2L;
		user1.name = "Kevin";
		post1.author = user1;
		post1.contents = "Hello";
		post2.contents = "World";
		user1.posts = new ArrayList<Post>();
		user1.posts.add(post1);
		user1.posts.add(post2);

		//userRepo.save(user1);
		postRepo.save(post1);
		post2.author = postRepo.findById(post1.id).get().author;
		postRepo.save(post2);

		List<User> allUsers = userRepo.findAll();
		List<Post> allPosts = postRepo.findAll();

		assertEquals(allPosts.size(),2);
		assertEquals(allUsers.size(),1);
	}
}

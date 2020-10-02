/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BlogPostApp extends WebSecurityConfigurerAdapter {
    @Autowired
    private BlogPostService blogService;

	@RequestMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		return Collections.singletonMap("name", principal.getAttribute("name"));
	}
	
	@RequestMapping(value= {"/posts", "/posts/{numberOfPosts}"})
	public String getBlogPosts(@PathVariable(required = false) Optional<Integer> numberOfPosts, 
							   @RequestParam(required = false) Integer userId) {
		if (numberOfPosts.isPresent()) {
			//System.out.println("numberOfPosts="+numberOfPosts);
			return blogService.getUserBlogPosts(numberOfPosts);
		}
		else if (userId == null){
			//System.out.println("about to getAllBlogPosts...");
	    	return blogService.getAllBlogPosts();
		}
		else {
			//System.out.println("userId="+userId);
			return blogService.getUserIdPosts(userId);
		}
	}

	@RequestMapping("/posts/1/comments")
	public String getAllComments() {
		return blogService.getAllComments();
	}

    @RequestMapping("/comments")
    public String getPostIdComments(@RequestParam int postId) {
    	return blogService.getPostIdComments(postId);
    }
    /*
    @RequestMapping(value="/posts")
    public String getUserIdPosts(@PathVariable int userId) {
    	return blogService.getUserIdPosts(userId);
    }
    */
    @PostMapping("/posts")
    public String createPost(@RequestParam("userId") int userId,	//assumes auto-increment is on so no id needed
    						 @RequestParam("title") String title,
    						 @RequestParam("body") String body,
    						 ModelMap newBlogPost) {
    	System.out.println("userId="+userId+", title="+title +", body="+body);
    	newBlogPost.putIfAbsent("userId",userId);
    	newBlogPost.putIfAbsent("title",title);
    	newBlogPost.putIfAbsent("body",body);
        return blogService.addPost(newBlogPost);
    }
    
    @PutMapping("/posts/{postId}")
    public String putPost(@RequestParam("postId") Integer postId,
    		 			  @RequestParam("userId") Integer userId,
    		 			  @RequestParam("title") String title,
    		 			  @RequestParam("body") String body,
		ModelMap oldBlogPost) {
    	oldBlogPost.put("userId",userId);
    	oldBlogPost.put("title",title);
    	oldBlogPost.put("body",body);
        return blogService.updatePost(oldBlogPost);
    }
    
    @PatchMapping("/posts/{postId}")
    public String patchPost(@RequestParam("postId") Integer postId,
      		 @RequestParam("userId") Integer userId,
			 @RequestParam("title") String title,
			 @RequestParam("body") String body,
			 ModelMap oldBlogPost) {
    	oldBlogPost.put("userId",userId);
    	oldBlogPost.put("title",title);
    	oldBlogPost.put("body",body);
        return blogService.updatePost(oldBlogPost);
    }
    
    @DeleteMapping("/posts/{postId}")
    public String deletePost(@RequestParam("postId") Integer postId) {
        return blogService.deletePost(postId);
    }

    @Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests(a -> a
				.antMatchers("/", "/error", "/webjars/**").permitAll()
				.anyRequest().authenticated()
			)
			.exceptionHandling(e -> e
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
			)
			.csrf(c -> c
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
			)
			.logout(l -> l
				.logoutSuccessUrl("/").permitAll()
			)
			.oauth2Login();
		// @formatter:on
	}

	public static void main(String[] args) {
		SpringApplication.run(BlogPostApp.class, args);
	}

}

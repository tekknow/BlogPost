package com.example;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.client.RestTemplate;

@Service
public class BlogPostService {
    @Autowired
    private RestTemplate restTemplate;
 
    public String getAllBlogPosts() {
    	ResponseEntity<String> resp = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts", String.class);
    	return resp.getStatusCode() == HttpStatus.OK ? resp.getBody() : null;
    }

    public String getUserBlogPosts(Optional<Integer> numberOfPosts) {
    	Integer numPosts = numberOfPosts.get();
    	//System.out.println("numPosts="+numPosts);
    	ResponseEntity<String> resp = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/" +numPosts, String.class);
    	return resp.getStatusCode() == HttpStatus.OK ? resp.getBody() : null;
    }

    public String getAllComments() {
    	ResponseEntity<String> resp = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts/1/comments", String.class);
    	return resp.getStatusCode() == HttpStatus.OK ? resp.getBody() : null;
    }
    
    public String getPostIdComments(int postId) {
    	ResponseEntity<String> resp = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/comments?postId=" +postId, String.class);
    	return resp.getStatusCode() == HttpStatus.OK ? resp.getBody() : null;
    }
    
    public String getUserIdPosts(Integer userId) {
    	ResponseEntity<String> resp = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/posts?userId=" +userId, String.class);
    	return resp.getStatusCode() == HttpStatus.OK ? resp.getBody() : null;
    }
    
    public String addPost(ModelMap newBlogPost) {
    	return "If this were a real post, it would have added the form fields to a repository";
    }
    
    public String updatePost(ModelMap oldBlogPost) {
    	return "If this were a real post, it would have updated the form fields associated with the postId in the repository";
    }
    
    public String patchPost(ModelMap oldBlogPost) {
    	return "If this were a real post, it would have patched the form fields associated with the postId in the repository";
    }
    
    public String deletePost(int postId) {
    	return "If this were a real post, it would have deleted the post associated with the postId from the repository";
    }
    
   @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}

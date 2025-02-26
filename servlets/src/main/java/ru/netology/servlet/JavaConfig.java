package ru.netology.servlet;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.netology.service.PostService;
import ru.netology.repository.PostRepository;
import ru.netology.controller.PostController;

@Configuration
public class JavaConfig {

    @Bean
    public PostController postController(PostService postService) {
        return new PostController(postService);
    }

    @Bean
    public PostService postService(PostRepository postRepository) {
        return new PostService(postRepository);
    }
    @Bean
    public PostRepository postRepository() {
        return new PostRepository();
    }

}

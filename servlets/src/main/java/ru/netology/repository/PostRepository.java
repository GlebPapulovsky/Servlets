package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
@Repository
public class PostRepository {
  private final ConcurrentMap<Long, Post> posts = new ConcurrentHashMap<>();
  private AtomicLong currentId; //


  public PostRepository() {
    currentId = new AtomicLong(0);
  }

  public List<Post> all() {
    List<Post> allPosts = new ArrayList<Post>(posts.values());
    return allPosts;
  }

  public Optional<Post> getById(long id) {
    return Optional.ofNullable(posts.get(id));
  }

  public Post save(Post post) {
    if (post.getId() == 0) {
      long newId = generateNewId();
      post.setId(newId);
      posts.put(newId, post);
      return post;
    } else {
      return updatePost(post);
    }
  }

  private long generateNewId() {
    currentId.set(currentId.longValue() + (long) 1);
    return currentId.longValue();
  }

  private Post updatePost(Post post) {

    return posts.computeIfPresent(post.getId(), (id, existingPost) -> {
      existingPost.setContent(post.getContent());
      return existingPost;
    });
  }

  public boolean removeById(long id) {
    if (posts.containsKey(id)) {
      posts.remove(id);
      return true;
    }
    return false;
  }
}
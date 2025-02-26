package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {

  final String POSTS_PATH = "/api/posts";
  final String POST_WITH_ID_PATH = "/api/posts/\\d+";
  private PostController controller;

  @Override
  public void init() {
    final var context = new AnnotationConfigApplicationContext(JavaConfig.class);

    controller = context.getBean(PostController.class);
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) {

    try {
      final var path = req.getRequestURI();
      final var method = req.getMethod();

      if (path.matches(POST_WITH_ID_PATH)) {
        final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
        System.out.println(id);
        if (method.equals("GET")) {
          controller.getById(id, resp);
          return;
        }
        if (method.equals("DELETE")) {
          controller.removeById(id, resp);
          return;
        }
      }
      if (path.equals(POSTS_PATH)) {
        if (method.equals("GET")) {
          controller.all(resp);
          return;
        }
        if (method.equals("POST")) {
          controller.save(req.getReader(), resp);
          return;
        }
      }


      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
    } catch (Exception e) {
      e.printStackTrace();
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
  }
}


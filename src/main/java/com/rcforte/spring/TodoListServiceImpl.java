package com.rcforte.spring;

import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Profile("default")
public class TodoListServiceImpl implements TodoListService {

  private final RestTemplate restTemplate;

  public TodoListServiceImpl(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<TodoList> findAll() {
    ResponseEntity<List<TodoList>> resp = restTemplate.exchange(
      "http://localhost:8082/todos/api/v1/todo-list/",
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<List<TodoList>>() {}
    );
    return resp.getBody();
  }

  @Override
  public List<TodoList> findByName(String name) {
    return findAll();
  }

  @Override
  public TodoList save(TodoList todoList) {
    HttpHeaders hh = new HttpHeaders();
    hh.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<TodoList> ent = new HttpEntity<>(todoList, hh);
    return restTemplate.postForObject( "http://localhost:8082/todos/api/v1/todo-list/", ent, TodoList.class );
  }
}

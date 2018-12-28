package com.rcforte.spring;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Profile("dev")
@Service
public class TodoListServiceInMemory implements TodoListService {

  private static final Map<Long, TodoList> inMemoryDb = Maps.newHashMap();
  private static long nextId;

  static {
    TodoList todo = new TodoList();
    todo.setId(1L);
    todo.setName("Breakfast List");
    todo.getItems().add("Buy bread");
    todo.getItems().add("Cook eggs");
    inMemoryDb.put(todo.getId(), todo);

    todo = new TodoList();
    todo.setId(2L);
    todo.setName("Dinner List");
    todo.getItems().add("Buy steak");
    todo.getItems().add("Cook steak");
    inMemoryDb.put(todo.getId(), todo);
  }

  @Override
  public synchronized List<TodoList> findAll() {
    return Lists.newArrayList(inMemoryDb.values());
  }

  @Override
  public synchronized List<TodoList> findByName(String name) {
    return Lists.newArrayList(inMemoryDb.values());
  }

  @Override
  public synchronized TodoList save(TodoList todoList) {
    nextId++;
    todoList.setId(nextId);
    inMemoryDb.put(todoList.getId(), todoList);
    return todoList;
  }
}

package com.rcforte.spring;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Profile("dev")
@Service
public class TodoListServiceInMemory implements TodoListService {

  private static final Map<String, TodoList> inMemoryDb = Maps.newHashMap();

  public TodoListServiceInMemory() {
    TodoList todo;

    todo = new TodoList();
    todo.setName("Breakfast List");
    todo.getItems().add(new TodoListItem(null, "Buy bread"));
    todo.getItems().add(new TodoListItem(null, "Cook eggs"));
    save(todo);

    todo = new TodoList();
    todo.setName("Dinner List");
    todo.getItems().add(new TodoListItem(null, "Buy steak"));
    todo.getItems().add(new TodoListItem(null, "Cook steak"));
    save(todo);
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
    // save items
    for(TodoListItem item : todoList.getItems()) {
      if(item.getId() == null) {
        item.setId(UUID.randomUUID().toString());
      }
    }

    // save todo list if needed
    if(todoList.getId() == null) {
      todoList.setId(UUID.randomUUID().toString());
      inMemoryDb.put(todoList.getId(), todoList);
    }

    return todoList;
  }
}

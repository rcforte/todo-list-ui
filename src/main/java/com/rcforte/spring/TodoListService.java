package com.rcforte.spring;

import java.util.List;

public interface TodoListService {
  List<TodoList> findAll();
  List<TodoList> findByName(String name);
  TodoList save(TodoList todoList);
}

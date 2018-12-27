package com.rcforte.spring;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {

  public MainView(TodoListService todoListService) {
    Grid<TodoList> todoListGrid = new Grid<>();
    todoListGrid.addColumn(TodoList::getId).setHeader("Id");
    todoListGrid.addColumn(TodoList::getName).setHeader("Name");
    todoListGrid.setItems(todoListService.findAll());
    add(todoListGrid);
  }
}



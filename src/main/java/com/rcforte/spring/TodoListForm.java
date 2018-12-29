package com.rcforte.spring;

import com.google.common.collect.Lists;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;

public class TodoListForm extends FormLayout {

  private final TextField id = new TextField("Id");
  private final TextField name = new TextField("Name");
  private final TextField item = new TextField("Item");

  private final Button save = new Button("Save");
  private final Grid<TodoListItem> grid = new Grid<>();

  private final TodoListService service;
  private final MainView mainView;

  private TodoList todoList;
  private final Binder<TodoList> binder = new Binder<>(TodoList.class);

  public TodoListForm(MainView mainView, TodoListService service) {
    this.mainView = mainView;
    this.service = service;

    // set up binder
    binder.forField(id)
        .withNullRepresentation("")
        .bind(TodoList::getId, TodoList::setId);
    binder.forField(name).bind(TodoList::getName, TodoList::setName);

    // set up the fields
    id.setPlaceholder("Enter id...");
    name.setPlaceholder("Enter name");

    // set up buttons
    save.getElement().setAttribute("theme", "primary");
    save.addClickListener(e -> save());
    HorizontalLayout buttons = new HorizontalLayout(save);

    // seup todo list item
    item.setPlaceholder("Enter item and press enter...");
    item.getElement().addEventListener("keyup", evt -> {
      addItem(evt.getEventData().getString("element.value"));
    }).addEventData("element.value").setFilter("event.keyCode==13");

    // add fields and buttons to form
    add(id, name, item, grid, buttons);

    // set up grid columns
    grid.addColumn(TodoListItem::getId).setHeader("Id");
    grid.addColumn(TodoListItem::getText).setHeader("Item");

    // set up backing bean to null as we are creating an empty form
    setTodoList(null);
  }

  public void addItem(String item) {
    this.todoList.getItems().add(new TodoListItem(null, item));
    this.grid.setItems(todoList.getItems());
    this.item.clear();
  }

  public void setTodoList(TodoList todoList) {
    this.todoList = todoList;
    this.binder.setBean(todoList);

    boolean enabled = this.todoList != null;
    save.setEnabled(enabled);
    if(enabled) {
      name.focus();
    }

    if(todoList == null) {
      grid.setItems(Lists.newArrayList());
    } else {
      if(todoList.getItems() != null) {
        grid.setItems(todoList.getItems());
      }
    }
  }

  private void save() {
    service.save(todoList);
    mainView.updateList();
    setTodoList(null);
  }
}

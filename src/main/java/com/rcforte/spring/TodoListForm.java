package com.rcforte.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;

public class TodoListForm extends FormLayout {

  private final TextField id = new TextField("Id");
  private final TextField name = new TextField("Name");
  private final Button save = new Button("Save");
  private final Button cancel = new Button("Cancel");

  private final TodoListService service;
  private final MainView mainView;

  private TodoList todoList;
  private final Binder<TodoList> binder = new Binder<>(TodoList.class);

  public TodoListForm(MainView mainView, TodoListService service) {
    this.mainView = mainView;
    this.service = service;

    binder.forField(id).withConverter(new StringToLongConverter("Use a number")).bind(TodoList::getId, TodoList::setId);
    binder.forField(name).bind(TodoList::getName, TodoList::setName);

    id.setPlaceholder("Enter id...");
    name.setPlaceholder("Enter name");
    save.getElement().setAttribute("theme", "primary");
    HorizontalLayout buttons = new HorizontalLayout(save, cancel);

    add(id, name, buttons);

    setTodoList(null);
    save.addClickListener(e -> save());
    cancel.addClickListener(evt -> setVisible(false));
  }

  public void setTodoList(TodoList todoList) {
    this.todoList = todoList;
    this.binder.setBean(todoList);
    boolean enabled = todoList != null;
    save.setEnabled(enabled);
    if(enabled) {
      name.focus();
    }
  }

  private void save() {
    service.save(todoList);
    mainView.updateList();
    setTodoList(null);
  }
}

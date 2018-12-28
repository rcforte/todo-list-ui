package com.rcforte.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PWA(name = "Project Base for Vaadin Flow with Spring", shortName = "Project Base")
public class MainView extends VerticalLayout {

  private final TodoListService service;
  private final TodoListForm todoListForm;
  private final TextField filterText = new TextField();
  private final Grid<TodoList> grid = new Grid<>();

  public MainView(TodoListService service) {
    this.service = service;
    this.todoListForm = new TodoListForm(this, service);

    filterText.setPlaceholder("Filter by name...");

    filterText.setValueChangeMode(ValueChangeMode.EAGER);
    filterText.addValueChangeListener(e -> updateList());
    Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
    clearFilterTextBtn.addClickListener(e -> filterText.clear());
    HorizontalLayout filtering = new HorizontalLayout(filterText, clearFilterTextBtn);
    Button addTodoListBtn = new Button("New Todo List");
    addTodoListBtn.addClickListener(e -> {
      grid.asSingleSelect().clear();
      todoListForm.setTodoList(new TodoList());
    });
    HorizontalLayout toolbar = new HorizontalLayout(filtering, addTodoListBtn);

    grid.setSizeFull();
    grid.addColumn(TodoList::getId).setHeader("Id");
    grid.addColumn(TodoList::getName).setHeader("Name");
    HorizontalLayout main = new HorizontalLayout(grid, todoListForm);
    main.setAlignItems(Alignment.START);
    main.setSizeFull();

    add(toolbar, main);
    setHeight("100vh");
    updateList();

    grid.asSingleSelect().addValueChangeListener(e -> todoListForm.setTodoList(e.getValue()));
  }

  public void updateList() {
    grid.setItems(service.findAll());
  }
}



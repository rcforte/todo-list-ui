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

  private TodoListService service;
  private TodoListForm todoListForm;
  private TextField filterText = new TextField();
  private Grid<TodoList> grid = new Grid<>();

  public MainView(TodoListService service) {
    this.service = service;

    HorizontalLayout toolbar = new HorizontalLayout();
    {
      HorizontalLayout filtering = new HorizontalLayout();
      {
        // set up filter text component
        filterText.setPlaceholder("Filter by name...");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());
        filtering.add(filterText);

        // setup clear filter button
        Button clearFilterTextBtn = new Button(new Icon(VaadinIcon.CLOSE_CIRCLE));
        clearFilterTextBtn.addClickListener(e -> filterText.clear());
        filtering.add(clearFilterTextBtn);
      }
      toolbar.add(filtering);

      // set up add new
      Button addTodoListBtn = new Button("New Todo List");
      addTodoListBtn.addClickListener(e -> {
        grid.asSingleSelect().clear();
        todoListForm.setTodoList(new TodoList());
      });
      toolbar.add(addTodoListBtn);
    }
    add(toolbar);

    HorizontalLayout main = new HorizontalLayout();
    main.setAlignItems(Alignment.START);
    main.setSizeFull();
    {
      grid.setSizeFull();
      grid.addColumn(TodoList::getId).setHeader("Id");
      grid.addColumn(TodoList::getName).setHeader("Name");
      grid.asSingleSelect().addValueChangeListener(e -> todoListForm.setTodoList(e.getValue()));
      main.add(grid);

      todoListForm = new TodoListForm(this, service);
      main.add(todoListForm);
    }
    add(main);

    setHeight("100vh");
    updateList();
  }

  public void updateList() {
    grid.setItems(service.findAll());
  }
}



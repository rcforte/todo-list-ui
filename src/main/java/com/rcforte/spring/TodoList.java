package com.rcforte.spring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown=true)
public class TodoList {

  private Long id;
  private String name;
  private List<TodoListItem> items = Lists.newArrayList();
}

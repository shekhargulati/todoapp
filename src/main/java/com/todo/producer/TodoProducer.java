package com.todo.producer;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import com.todo.domain.Todo;
import com.todo.service.TodoService;


@RequestScoped
public class TodoProducer {

    @Inject
    private TodoService todoService;

    private List<Todo> todos;

    @Produces
    @Named
    public List<Todo> getTodos() {
        return todos;
    }

    public void onTodoListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Todo TodoList) {
        retrieveAllTodosOrderedByCreationDate();
    }

    @PostConstruct
    public void retrieveAllTodosOrderedByCreationDate() {
        todos = todoService.findAllTodosOrderedByCreationDate();
    }
}

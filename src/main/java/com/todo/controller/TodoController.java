package com.todo.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.todo.domain.Todo;
import com.todo.service.TodoService;

@Model
public class TodoController {

	@Inject
	private TodoService todoService;
	
	@Inject
	private FacesContext facesContext;
	
	@Produces
	@Named
	private Todo newTodo;
	
	@PostConstruct
	public void initNewTodo() {
		newTodo = new Todo();
	}

	public void createNewTodo() {
		try {
			todoService.create(newTodo);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Created!", "TodoList creation successful");
			facesContext.addMessage(null, m);
			initNewTodo();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Todo creation unsuccessful");
			facesContext.addMessage(null, m);
		}
	}
	
	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Todo creation failed. See server log for more information";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}
}

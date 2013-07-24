package com.todo.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.todo.domain.Todo;

@Stateless
public class TodoService {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Inject
	Event<Todo> todoEvent;
	

	public Todo create(Todo todo) {
		entityManager.persist(todo);
		todoEvent.fire(todo);
		return todo;
	}

	public Todo find(Long id) {
		Todo todo = entityManager.find(Todo.class, id);
		List<String> tags = todo.getTags();
		System.out.println("Tags : " + tags);
		return todo;
	}

	public List<Todo> findAllTodosOrderedByCreationDate() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Todo> criteria = cb.createQuery(Todo.class);
        Root<Todo> todoList = criteria.from(Todo.class);
        criteria.select(todoList).orderBy(cb.asc(todoList.get("createdOn")));
        return entityManager.createQuery(criteria).getResultList();
	}

}

package com.in28minutes.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class TodoService {
	private static List<Todo> todos = new ArrayList<Todo>();
	private static int todosCount = 0;
	static {
		todos.add(new Todo(++todosCount, "in28minutes", "Learn Aws", 
				LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++todosCount, "in28minutes", "Dev Ops", 
				LocalDate.now().plusYears(1), false));
		todos.add(new Todo(++todosCount, "in28minutes", "Full Stack Developement", 
				LocalDate.now().plusYears(1), false));
	}
	
	public List<Todo> findByUsername(String username) {
		return todos;
	}
	
	public void deleteById(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId() == id;
		todos.removeIf(predicate );
	}
	
	public Todo findById(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId() == id;
		Todo tod = todos.stream().filter(predicate ).findFirst().get();
		return tod;
	}
	
	public void addTodo(String username, String description, LocalDate targetDate, boolean isDone) {
		Todo todo = new Todo(++todosCount, username, description, targetDate, isDone);
		todos.add(todo);
	}
	
	public void updateTodo(@Valid Todo todo) {
		deleteById(todo.getId());
		todos.add(todo);
	}
}

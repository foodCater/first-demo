package com.in28minutes.springboot.myfirstwebapp.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.validation.Valid;

@SessionAttributes("name")
@Controller
public class TodoController {
	
	private TodoService todoSrc;
	
	public TodoController(TodoService todoSrc) {
		super();
		this.todoSrc = todoSrc;
	}


	@RequestMapping("list-todos")
	public String listAllTodos(ModelMap model) {
		List <Todo> todos = todoSrc.findByUsername("in28minutes");
		model.addAttribute("todos", todos);
		return "listtodos";
	}
	
	@RequestMapping("delete-todo")
	public String deleteTodo(@RequestParam int id) {
		todoSrc.deleteById(id);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo", method = RequestMethod.GET)
	public String updateTodo(@RequestParam int id, ModelMap model) {
		Todo tod = todoSrc.findById(id);
		model.addAttribute("todo", tod);
		return "todo";
	}
	
	@RequestMapping(value="add-todo", method = RequestMethod.GET)
	public String showTOdoPage(ModelMap model) {
		String username = (String) model.get("name");
		Todo todo = new Todo(0, username, "test", LocalDate.now().plusYears(1), false);
		model.put("todo", todo);
		return "todo";
	}
	
	@RequestMapping(value="add-todo", method = RequestMethod.POST)
	public String addNewTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		String username = (String) model.get("name");
		todoSrc.addTodo(username, todo.getDescription(), LocalDate.now().plusYears(1), false);
		return "redirect:list-todos";
	}
	
	@RequestMapping(value="update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {
		if (result.hasErrors()) {
			return "todo";
		}
		String username = (String) model.get("name");
		todo.setUsername(username);
		todoSrc.updateTodo(todo);
		return "redirect:list-todos";
	}
	
}

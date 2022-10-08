/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ye.todowebapi.data;

import com.ye.todowebapi.models.ToDo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Yonathan
 */
@Repository
@Profile("Memory") // Makes the DAO identifiable
public class ToDoDaoMem implements ToDoDao {

    private static final List<ToDo> todos = new ArrayList<>();
    
    @Override
    public ToDo add(ToDo todo) {
        int nextId = todos.stream()
                .mapToInt(i -> i.getId())
                // Finds the biggest id being used
                .max()
                // If the largest/only id is 0, or null, add 1
                .orElse(0) + 1;

        todo.setId(nextId);
        // The todo just created will be added to the 'todos' list
        todos.add(todo);
        return todo;
    }

    @Override
    public List<ToDo> getAll() {
        return new ArrayList<>(todos);
    }

    @Override
    public ToDo findById(int id) {
        return todos.stream()
                // Each object is converted 
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean update(ToDo todo) {
        int index = 0;
        // If the id does not match, keep looking 
        while (index < todos.size() && todos.get(index).getId() != todo.getId()) {
            index++;
        }
        if (index < todos.size()) {
            todos.set(index, todo);
        }
        return index < todos.size();
    }

    @Override
    public boolean deleteById(int id) {
        return todos.removeIf(td -> td.getId() == id);
    }
    
}

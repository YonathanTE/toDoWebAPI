/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ye.todowebapi.data;

import com.ye.todowebapi.models.ToDo;
import java.util.List;

/**
 *
 * @author Yonathan
 */
public interface ToDoDao {
    // Returns an object with a generated Id
    ToDo add(ToDo todo);

    List<ToDo> getAll();
    
    ToDo findById(int id);

    // true if item exists and is updated
    boolean update(ToDo todo);

    // true if item exists and is deleted
    boolean deleteById(int id);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ye.todowebapi.data;

/**
 *
 * @author Yonathan
 */
import com.ye.todowebapi.models.ToDo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository 
@Profile("DB") // Makes the DAO identifiable
public class ToDoDBDao implements ToDoDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired 
    public ToDoDBDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ToDo add(ToDo todo) {

        final String sql = "INSERT INTO todo(todo, note) VALUES(?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update((Connection conn) -> {
            // Lambda expression for the 'PreparedStatementCreator' parameter
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, todo.getTodo());
            statement.setString(2, todo.getNote());
            return statement;
            // Once the SQL INSERT executes, we can take the keys made from the 'GeneratedKeyHolder'
        }, keyHolder);

        todo.setId(keyHolder.getKey().intValue());

        return todo;
    }

    @Override
    public List<ToDo> getAll() {
        final String sql = "SELECT id, todo, note, finished FROM todo;";
        return jdbcTemplate.query(sql, new ToDoMapper());
    }

    @Override
    public ToDo findById(int id) {

        final String sql = "SELECT id, todo, note, finished "
                + "FROM todo WHERE id = ?;";

        return jdbcTemplate.queryForObject(sql, new ToDoMapper(), id);
    }

    @Override
    public boolean update(ToDo todo) {

        final String sql = "UPDATE todo SET "
                + "todo = ?, "
                + "note = ?, "
                + "finished = ? "
                + "WHERE id = ?;";

        return jdbcTemplate.update(sql,
                todo.getTodo(),
                todo.getNote(),
                todo.isFinished(),
                // '> 0' is how we return an integer that can fit logically within a boolean statement *
                todo.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        final String sql = "DELETE FROM todo WHERE id = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    private static final class ToDoMapper implements RowMapper<ToDo> {

        @Override
        public ToDo mapRow(ResultSet rs, int index) throws SQLException {
            ToDo td = new ToDo();
            td.setId(rs.getInt("id"));
            td.setTodo(rs.getString("todo"));
            td.setNote(rs.getString("note"));
            td.setFinished(rs.getBoolean("finished"));
            return td;
        }
    }
}

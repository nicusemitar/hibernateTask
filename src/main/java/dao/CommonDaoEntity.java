package dao;

import model.Discipline;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface CommonDaoEntity<T> {
    void add(T entity) throws SQLException;

    void update(T entity  ) throws SQLException;

    void delete(T entity) throws SQLException;

    List<T> getAll() throws SQLException;

}

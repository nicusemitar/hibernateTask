package dao;

import enums.DisciplineType;
import enums.Status;
import model.User;

import java.sql.SQLException;
import java.util.List;

public interface DaoUser extends CommonDaoEntity {

    List<User> getUserByRole(String role) throws SQLException;

    List<User> getUsersByDiscipline(DisciplineType disciplineType) throws SQLException;

    List<User> getUsersByTaskStatus(Status status) throws SQLException;

    List<User> getUserById(int id) throws SQLException;

    List<User> getAll() throws SQLException;

}

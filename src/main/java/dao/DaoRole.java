package dao;

import model.Role;

import java.sql.SQLException;
import java.util.List;

public interface DaoRole extends CommonDaoEntity {

    public List<Role> getRoleByName(String name) throws SQLException;

    List<Role> getAll() throws SQLException;
}

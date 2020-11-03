package service;

import dao.DaoRole;
import model.Role;
import org.hibernate.Query;
import org.hibernate.Session;
import utils.SesionUtils;

import java.sql.SQLException;
import java.util.List;

public class RoleService extends SesionUtils implements DaoRole {

    @Override
    public List<Role> getRoleByName(String name) throws SQLException {
        List<Role> userList;
        openTransactionSession();

        Session session = getSession();
        Query query = session.createQuery("from Role r where r.name=:name");
        query.setParameter("name", name);
        userList = query.list();

        return userList;
    }

    @Override
    public List<Role> getAll() throws SQLException {
        List<Role> userList;
        openTransactionSession();

        Session session = getSession();
        Query query = session.createQuery("from " + Role.class.getCanonicalName());
        userList = query.list();

        return userList;
    }

    @Override
    public void add(Object role) throws SQLException {
        //open session
        openTransactionSession();

        Session session = getSession();
        session.save(role);

        //close session
        closeTransactionSession();
    }

    @Override
    public void update(Object role) throws SQLException {
        //open session
        openTransactionSession();

        Session session = getSession();
        session.update(role);

        //close session
        closeTransactionSession();

    }

    @Override
    public void delete(Object role) throws SQLException {

    }
}

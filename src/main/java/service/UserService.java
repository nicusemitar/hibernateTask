package service;

import dao.DaoUser;
import enums.DisciplineType;
import enums.Status;
import model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import utils.SesionUtils;

import java.sql.SQLException;
import java.util.List;

public class UserService extends SesionUtils implements DaoUser {

    @Override
    public List<User> getUserByRole(String role) throws SQLException {
        //open session
        List<User> userList;
        openTransactionSession();

        Session session = getSession();
        Query query = session.createQuery("from User u join fetch u.roles r where r.name =:role");
        query.setParameter("role", role);
        userList = query.list();

        return userList;
    }

    @Override
    public List<User> getUsersByDiscipline(DisciplineType disciplineType) throws SQLException {
        //open session
        List<User> userList;
        openTransactionSession();

        Session session = getSession();
        Query query = session.createQuery("from User u join fetch u.discipline d where d.disciplineType =:disciplineType");
        query.setParameter("disciplineType", disciplineType);
        userList = query.list();

        return userList;
    }

    @Override
    public List<User> getUsersByTaskStatus(Status status) throws SQLException {
        //open session
        List<User> userList;
        openTransactionSession();

        Session session = getSession();
        Query query = session.createQuery("from User u join fetch u.taskList t where t.status =:status");
        query.setParameter("status", status);
        userList = query.list();

        return userList;
    }

    @Override
    public List<User> getUserById(int id) throws SQLException {
        List<User> userList;
        openTransactionSession();

        Session session = getSession();
        Query query = session.createQuery("from User where id=:id");
        query.setParameter("id", id);
        userList = query.list();

        return userList;
    }

    @Override
    public List<User> getAll() throws SQLException {
        //open session
        List<User> userList;
        openTransactionSession();

        Session session = getSession();
        Query query = session.createQuery("from " + User.class.getCanonicalName());
        userList = query.list();

        return userList;
    }

    @Override
    public void add(Object user) throws SQLException {
        //open session with a transaction
        openTransactionSession();

        Session session = getSession();
        session.save(user);

        //close session with a transaction
        closeTransactionSession();

    }

    @Override
    public void update(Object user) throws SQLException {
        //open session
        openTransactionSession();

        Session session = getSession();
        session.update(user);

        //close session
        closeTransactionSession();

    }

    @Override
    public void delete(Object user) throws SQLException {
        //open session
        openTransactionSession();

        Session session = getSession();
        session.delete(user);

        //close session
        closeTransactionSession();

    }
}



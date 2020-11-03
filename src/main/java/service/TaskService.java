package service;

import dao.DaoTask;
import model.Task;
import org.hibernate.Query;
import org.hibernate.Session;
import utils.SesionUtils;

import java.sql.SQLException;
import java.util.List;

public class TaskService extends SesionUtils implements DaoTask {


    @Override
    public List<Task> getAll() throws SQLException {
        List<Task> userList;
        openTransactionSession();

        Session session = getSession();
        Query query  = session.createQuery("from "+ Task.class.getCanonicalName());
        userList = query.list();

        return userList;
    }

    @Override
    public void add(Object task) throws SQLException {
        //open session
        openTransactionSession();

        Session session = getSession();
        session.save(task);

        //close session
        closeTransactionSession();

    }

    @Override
    public void update(Object task) throws SQLException {
        //open session
        openTransactionSession();

        Session session = getSession();
        session.update(task);

        //close session
        closeTransactionSession();

    }

    @Override
    public void delete(Object task) throws SQLException {

    }
}


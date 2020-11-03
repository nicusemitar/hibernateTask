package service;

import dao.DaoDiscipline;
import enums.DisciplineType;
import model.Discipline;
import org.hibernate.Query;
import org.hibernate.Session;
import utils.SesionUtils;

import java.sql.SQLException;
import java.util.List;

public class DisciplineService extends SesionUtils implements DaoDiscipline {

    @Override
    public Discipline getDiscipline(DisciplineType disciplineType) throws SQLException {
        //open session
        List<Discipline> discipline;
        openTransactionSession();

        Session session = getSession();
        Query query = session.createQuery("from Discipline d where d.disciplineType=:disciplineType");
        query.setParameter("disciplineType", disciplineType);
        discipline = query.list();

        return discipline.get(0);
    }

    @Override
    public List<Discipline> getAll() throws SQLException {
        return null;
    }

    @Override
    public void add(Object discipline) throws SQLException {
        //open session
        openTransactionSession();

        Session session = getSession();
        session.save(discipline);

        //close session
        closeTransactionSession();
    }

    @Override
    public void update(Object discipline) throws SQLException {
        openTransactionSession();

        Session session = getSession();
        session.update(discipline);

        //close session
        closeTransactionSession();
    }

    @Override
    public void delete(Object discipline) throws SQLException {

    }
}

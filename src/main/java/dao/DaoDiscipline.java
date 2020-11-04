package dao;

import enums.DisciplineType;
import model.Discipline;

import java.sql.SQLException;
import java.util.List;

public interface DaoDiscipline extends CommonDaoEntity {

    Discipline getDiscipline(DisciplineType disciplineType) throws SQLException;

}

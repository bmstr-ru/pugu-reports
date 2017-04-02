package ru.bmstr.pugu.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;
import ru.bmstr.pugu.beans.AllBeans;
import ru.bmstr.pugu.domain.*;
import ru.bmstr.pugu.dto.AllContent;
import ru.bmstr.pugu.properties.EnumNameHelper;
import ru.bmstr.pugu.properties.PropertyLoader;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.bmstr.pugu.properties.PropertyNames.DATA_FOLDER;

/**
 * Created by bmstr on 05.03.2017.
 */
@Component
public class DatabaseManager {
    @Autowired
    private PropertyLoader propertyLoader;

    @Autowired
    private AllContent allContent;

    private Map<Class, Dao> daos;
    private ConnectionSource dataConnection;

    @PostConstruct
    private void establishConnections() {
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.setEncoding(SQLiteConfig.Encoding.UTF8);
            Class.forName("org.sqlite.JDBC");
            // create a database connection
            dataConnection = new JdbcConnectionSource("jdbc:sqlite:" + propertyLoader.getProperty(DATA_FOLDER) + "/allData.sqlite");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        daos = new HashMap<>();
        try {
            daos.put(SuitType.class, DaoManager.createDao(dataConnection, SuitType.class));
            daos.put(Defendant.class, DaoManager.createDao(dataConnection, Defendant.class));
            daos.put(Representative.class, DaoManager.createDao(dataConnection, Representative.class));
            daos.put(Result.class, DaoManager.createDao(dataConnection, Result.class));
            daos.put(Category.class, DaoManager.createDao(dataConnection, Category.class));
            daos.put(Suit.class, DaoManager.createDao(dataConnection, Suit.class));
            daos.put(Cassation.class, DaoManager.createDao(dataConnection, Cassation.class));
            daos.put(Appeal.class, DaoManager.createDao(dataConnection, Appeal.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createStaticDatabase() throws SQLException {
//        TableUtils.dropTable(dataConnection, SuitType.class, false);
//        TableUtils.dropTable(dataConnection, Category.class, false);
//        TableUtils.dropTable(dataConnection, Defendant.class, false);
//        TableUtils.dropTable(dataConnection, Representative.class, false);
//        TableUtils.dropTable(dataConnection, Result.class, false);
        TableUtils.dropTable(dataConnection, Cassation.class, false);
//        TableUtils.dropTable(dataConnection, Appeal.class, false);
//        TableUtils.dropTable(dataConnection, Suit.class, false);

//
//        TableUtils.createTable(dataConnection, SuitType.class);
//        TableUtils.createTable(dataConnection, Category.class);
//        TableUtils.createTable(dataConnection, Defendant.class);
//        TableUtils.createTable(dataConnection, Representative.class);
//        TableUtils.createTable(dataConnection, Result.class);
        TableUtils.createTable(dataConnection, Cassation.class);
//        TableUtils.createTable(dataConnection, Appeal.class);
//        TableUtils.createTable(dataConnection, Suit.class);
    }

    public void update(Object obj) {
        try {
            Dao dao = daos.get(obj.getClass());
            dao.update(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            allContent.fireChanged();
        }
    }

    public void create(Object obj) {
        try {
            Dao dao = daos.get(obj.getClass());
            dao.create(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            allContent.fireChanged();
        }
    }

    public void delete(Object obj) {
        try {
            Dao dao = daos.get(obj.getClass());
            dao.delete(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            allContent.fireChanged();
        }
    }

    public Object retrive(Object obj) {
        try {
            Dao dao = daos.get(obj.getClass());
            return dao.query(dao.queryBuilder().where().eq("id", 1).prepare()).get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List retriveAll(Class cls) {
        try {
            Dao dao = daos.get(cls);
            return dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List retriveAllWithEmpty(Class cls) {
        try {
            Dao dao = daos.get(cls);
            List result = new ArrayList();
            result.add(cls.newInstance());
            result.addAll(dao.queryForAll());
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void fetchPredefinedData() {
        List<Defendant> defendants = retriveAll(Defendant.class);
        Defendant.GU = defendants.stream().filter(d -> d.getId() == 1).findFirst().get();
        Defendant.KAZNA = defendants.stream().filter(d -> d.getId() == 2).findFirst().get();
        Defendant.MVD = defendants.stream().filter(d -> d.getId() == 3).findFirst().get();

        List<Result> results = retriveAll(Result.class);
        Result.APPROVED = results.stream().filter(d -> d.getId() == 1).findFirst().get();
        Result.DECLINED = results.stream().filter(d -> d.getId() == 2).findFirst().get();
        Result.UNRESOLVED = results.stream().filter(d -> d.getId() == 3).findFirst().get();
        Result.AGREED = results.stream().filter(d -> d.getId() == 4).findFirst().get();

        List<SuitType> suitTypes = retriveAll(SuitType.class);
        SuitType.TO_US = suitTypes.stream().filter(d -> d.getId() == 1).findFirst().get();
        SuitType.SPECIAL = suitTypes.stream().filter(d -> d.getId() == 2).findFirst().get();
        SuitType.THIRD_PARTY = suitTypes.stream().filter(d -> d.getId() == 3).findFirst().get();
        SuitType.OUR = suitTypes.stream().filter(d -> d.getId() == 4).findFirst().get();
    }

    public Category categoryOfCode(String code) {
        return ((List<Category>) retriveAll(Category.class)).stream()
                .filter(category -> category.getCode().equals(code))
                .findFirst().get();
    }

    public List<Category> childsOf(Category category) {
        return ((List<Category>) this.retriveAll(Category.class)).stream()
                .filter(child -> child.hasParent(category))
                .collect(Collectors.toList());
    }
}

package ru.bmstr.pugu.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;
import ru.bmstr.pugu.beans.AllBeans;
import ru.bmstr.pugu.domain.Category;
import ru.bmstr.pugu.domain.Defendant;
import ru.bmstr.pugu.domain.Representative;
import ru.bmstr.pugu.domain.Result;
import ru.bmstr.pugu.domain.SuitType;
import ru.bmstr.pugu.properties.EnumNameHelper;
import ru.bmstr.pugu.properties.PropertyLoader;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.bmstr.pugu.properties.PropertyNames.DATA_FOLDER;

/**
 * Created by bmstr on 05.03.2017.
 */
@Component
public class DatabaseManager {
    @Autowired
    private PropertyLoader propertyLoader;

    private Map<Class, Dao> daos;
    private ConnectionSource dataConnection;

    @PostConstruct
    private void establishConnections() {
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.setEncoding(SQLiteConfig.Encoding.UTF8);
            Class.forName("org.sqlite.JDBC");
            // create a database connection
            dataConnection = new JdbcConnectionSource("jdbc:sqlite:" + propertyLoader.getProperty(DATA_FOLDER) + "/static.db");
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
//
//        TableUtils.createTable(dataConnection, SuitType.class);
//        TableUtils.createTable(dataConnection, Category.class);
//        TableUtils.createTable(dataConnection, Defendant.class);
//        TableUtils.createTable(dataConnection, Representative.class);
//        TableUtils.createTable(dataConnection, Result.class);
//
//        Dao<SuitType, Integer> dao1 = DaoManager.createDao(dataConnection, SuitType.class);
//        for (ru.bmstr.pugu.domain.SuitType obj : ru.bmstr.pugu.domain.SuitType.values()) {
//            dao1.create(new SuitType(obj));
//        }
//        Dao<Defendant, Integer> dao2 = DaoManager.createDao(dataConnection, Defendant.class);
//        for (ru.bmstr.pugu.domain.Defendant obj : ru.bmstr.pugu.domain.Defendant.values()) {
//            dao2.create(new Defendant(obj));
//        }
//        Dao<Representative, Integer> dao3 = DaoManager.createDao(dataConnection, Representative.class);
//        for (ru.bmstr.pugu.domain.Representative obj : ru.bmstr.pugu.domain.Representative.values()) {
//            dao3.create(new Representative(obj));
//        }
//        Dao<Result, Integer> dao4 = DaoManager.createDao(dataConnection, Result.class);
//        for (ru.bmstr.pugu.domain.Result obj : ru.bmstr.pugu.domain.Result.values()) {
//            dao4.create(new Result(obj));
//        }
//        Dao<Category, Integer> dao5 = DaoManager.createDao(dataConnection, Category.class);
//        for (ru.bmstr.pugu.domain.Category obj : ru.bmstr.pugu.domain.Category.values()) {
//            dao5.create(new Category(obj));
//        }

    }

    public void update(Object obj) {
        try {
            Dao dao = daos.get(obj.getClass());
            dao.update(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void create(Object obj) {
        try {
            Dao dao = daos.get(obj.getClass());
            dao.create(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Object obj) {
        try {
            Dao dao = daos.get(obj.getClass());
            dao.delete(obj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
}

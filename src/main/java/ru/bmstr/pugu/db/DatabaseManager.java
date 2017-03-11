package ru.bmstr.pugu.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteConfig;
import ru.bmstr.pugu.properties.PropertyLoader;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static ru.bmstr.pugu.properties.PropertyNames.DATA_FOLDER;

/**
 * Created by bmstr on 05.03.2017.
 */
@Component
public class DatabaseManager {
    private Connection staticDataConnection;
    private Connection dynamicDataConnection;

    @Autowired
    private PropertyLoader propertyLoader;

    @PostConstruct
    private void establishConnections() {
        try {
            SQLiteConfig config = new SQLiteConfig();
            config.setEncoding(SQLiteConfig.Encoding.UTF8);
            Class.forName("org.sqlite.JDBC");
            // create a database connection
            staticDataConnection = DriverManager.getConnection("jdbc:sqlite:"+propertyLoader.getProperty(DATA_FOLDER)+"/static.db", config.toProperties());
            dynamicDataConnection = DriverManager.getConnection("jdbc:sqlite:"+propertyLoader.getProperty(DATA_FOLDER)+"/dynamic.db", config.toProperties());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

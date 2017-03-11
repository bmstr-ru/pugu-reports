package ru.bmstr.pugu.sqlite;

import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by bmstr on 20.02.2017.
 */
public class InsertRunner implements Runnable {
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet rs;

    @Override
    public void run() {
        while (true) {
            try {
                SQLiteConfig config = new SQLiteConfig();
                config.setEncoding(SQLiteConfig.Encoding.UTF8);
                Class.forName("org.sqlite.JDBC");
                // create a database connection
                connection = DriverManager.getConnection("jdbc:sqlite:sample.db", config.toProperties());

                statement = connection.prepareStatement("insert into dima values (?)");
                int randomNum = ThreadLocalRandom.current().nextInt(0, 1000);
                statement.setInt(1, randomNum);
                execute();
                statement.close();
                System.out.println("Inserted row "+randomNum);

                statement = connection.prepareStatement("update dima set i = ?");
                statement.setInt(1, randomNum);
                execute();
                statement.close();
                System.out.println("Updated rows with value "+randomNum);

                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void execute() {
        boolean done = false;
        while (!done) {
            try {
                statement.executeUpdate();
                done = true;
            } catch (SQLException sqlE) {

            }
        }
    }
}

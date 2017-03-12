package ru.bmstr.pugu;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bmstr.pugu.beans.AllBeans;
import ru.bmstr.pugu.db.DatabaseManager;
import ru.bmstr.pugu.sqlite.InsertRunner;
import ru.bmstr.pugu.ui.MainFrame;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Created by bmstr on 19.11.2016.
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        doRun();
    }

    public static void doRun() throws SQLException {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AllBeans.class);
        AllBeans.setContext(ctx);
        final DatabaseManager dbManager = ctx.getBean(DatabaseManager.class);
        dbManager.createStaticDatabase();
//        final MainFrame window = ctx.getBean(MainFrame.class);
//        SwingUtilities.invokeLater(() -> window.initialize());
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> ctx.close()));
    }

    public static void testSqlite() {
        for (int i = 0; i < 20; i++) {
            new Thread(new InsertRunner()).start();
        }
    }
}

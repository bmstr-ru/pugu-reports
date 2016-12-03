package ru.bmstr.pugu;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bmstr.pugu.beans.AllBeans;
import ru.bmstr.pugu.ui.MainFrame;

import javax.swing.*;

/**
 * Created by bmstr on 19.11.2016.
 */
public class Main {
    public static void main(String[] args) {
        doRun();
    }

    public static void doRun() {
        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AllBeans.class);
        final MainFrame window = ctx.getBean(MainFrame.class);
        SwingUtilities.invokeLater( () -> window.initialize() );
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                ctx.close();
            }
        });
    }
}

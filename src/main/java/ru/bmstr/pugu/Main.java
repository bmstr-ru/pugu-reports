package ru.bmstr.pugu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.bmstr.pugu.beans.AllBeans;
import ru.bmstr.pugu.beans.SimpleBean;
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
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AllBeans.class);
        final MainFrame window = ctx.getBean(MainFrame.class);
        SwingUtilities.invokeLater( () -> window.setVisible(true) );
    }
}

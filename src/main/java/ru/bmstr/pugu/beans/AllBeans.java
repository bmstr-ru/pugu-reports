package ru.bmstr.pugu.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.bmstr.pugu.ui.RowModifyDialog;
import ru.bmstr.pugu.ui.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * Created by bmstr on 19.11.2016.
 */
@Configuration
@ComponentScan("ru.bmstr")
public class AllBeans {
    @Autowired
    private MainFrame mainFrame;

    private static AnnotationConfigApplicationContext context;

    public static AnnotationConfigApplicationContext getContext() {
        return context;
    }

    public static void setContext(AnnotationConfigApplicationContext context) {
        AllBeans.context = context;
    }

    @Bean
    public RowModifyDialog getAddRowDialog(){
        return new RowModifyDialog(mainFrame);
    }

    @Bean
    public JFileChooser getFileChooser() {
        JFileChooser fileChooser = new JFileChooser(new File(".").getAbsolutePath());
        fileChooser.setFileFilter(new FileNameExtensionFilter("Report file data","json"));
        return fileChooser;
    }
}

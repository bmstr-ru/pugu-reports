package ru.bmstr.pugu.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.bmstr.pugu.ui.RowModifyDialog;
import ru.bmstr.pugu.ui.MainFrame;

/**
 * Created by bmstr on 19.11.2016.
 */
@Configuration
@ComponentScan("ru.bmstr")
public class AllBeans {

    @Autowired
    private MainFrame mainFrame;

    @Bean
    public RowModifyDialog getAddRowDialog(){
        return new RowModifyDialog(mainFrame, "Введите данные иска", true);
    }
}

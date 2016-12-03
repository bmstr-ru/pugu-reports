package ru.bmstr.pugu.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * Created by bmstr on 03.12.2016.
 */
@Component
public class TableData extends JTable {

    @Autowired
    private MyTableModel tableModel;

    @PostConstruct
    private void loadDefaultValues() {
        this.setModel(tableModel);
    }

}

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

    public void deleteSelected() {
        if (this.getSelectedRows().length != 0) {
            tableModel.deleteSelected(this.getSelectedRows());
        }
    }

    public boolean noRowsSelected() {
        return this.getSelectedRows().length == 0;
    }

}

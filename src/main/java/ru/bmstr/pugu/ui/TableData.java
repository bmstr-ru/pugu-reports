package ru.bmstr.pugu.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bmstr.pugu.domain.Representative;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by bmstr on 03.12.2016.
 */
@Component
public class TableData extends JTable {

    @Autowired
    private MyTableModel tableModel;

    @Autowired
    private RowModifyDialog rowModifyDialog;

    @PostConstruct
    private void loadDefaultValues() {
        this.setModel(tableModel);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent event) {
                JTable table = (JTable) event.getSource();
                Point p = event.getPoint();
                int rowId = table.rowAtPoint(p);
                if (event.getClickCount() == 2) {
                    SwingUtilities.invokeLater(() -> rowModifyDialog.showModifyRow(tableModel.getRow(rowId)));
                }
            }
        });
    }

    public void deleteSelected() {
        if (this.getSelectedRows().length != 0) {
            tableModel.deleteSelected(this.getSelectedRows());
        }
    }

    public boolean noRowsSelected() {
        return this.getSelectedRows().length == 0;
    }

    public void reDraw() {
        tableModel.reDraw();
    }

    public void unFilter() {
        tableModel.unFilter();
    }

    public void filter(Representative representative, String subString) {
        tableModel.filter(representative, subString);
    }
}

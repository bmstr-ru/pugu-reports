package ru.bmstr.pugu.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bmstr.pugu.domain.Suit;
import ru.bmstr.pugu.dto.AllContent;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

/**
 * Created by bmstr on 03.12.2016.
 */
@Component
public class MyTableModel extends AbstractTableModel {

    @Autowired
    private AllContent allContent;

    private final static String[] COLUMN_NAMES = {"№", "Категория", "Истец", "Сумма", "Ответчик", "Представитель", "Решение", "Удовлетворённая сумма"};

    @Override
    public int getRowCount() {
        return allContent.getRowCount();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return allContent.getValueAt(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int col) {
        return COLUMN_NAMES[col];
    }

    public void addRow(Suit suit) {
        allContent.addRow(suit);
        reDraw();
    }

    public void deleteSelected(int[] selected) {
        allContent.deleteRows(selected);
        reDraw();
    }

    public void reDraw() {
        SwingUtilities.invokeLater(() -> this.fireTableDataChanged() );
    }
}

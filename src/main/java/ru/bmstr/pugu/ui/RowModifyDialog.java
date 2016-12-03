package ru.bmstr.pugu.ui;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bmstr.pugu.domain.*;
import ru.bmstr.pugu.dto.AllContent;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bmstr on 27.11.2016.
 */
public class RowModifyDialog extends JDialog {

    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 300;
    private JComboBox categoryChoise;
    private JComboBox defendantChoise;
    private JTextField plaintiffChoise;
    private JTextField initialSummChoise;
    private JTextField agreedSummChoise;
    private JComboBox resultChoise;
    private JComboBox representativeChoise;

    @Autowired
    private MyTableModel tableModel;

    public RowModifyDialog(Frame owner, String title, boolean isModal) {
        super(owner, title, isModal);
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setLocationRelativeTo(this.getOwner());
        Container pane = this.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(getInputDataPanel());
        pane.add(getControlButtonsPanel());
    }

    public void showAddRow() {
        this.setTitle("Введите данные иска");
        categoryChoise.setSelectedItem(Category.EMPTY);
        defendantChoise.setSelectedItem(Defendant.EMPTY);
        plaintiffChoise.setText("");
        initialSummChoise.setText("");
        agreedSummChoise.setText("");
        resultChoise.setSelectedItem(Result.EMPTY);
        representativeChoise.setSelectedItem(Representative.EMPTY);
        this.setVisible(true);
    }

    public void showModifyRow(Suit suit) {
        this.setTitle("Иск от гр. " + suit.getPlaintiff());
        this.setVisible(true);
    }

    private JPanel getInputDataPanel() {
        GridLayout layout = new GridLayout(7,2);
        JPanel dataPanel = new JPanel(layout);

        JLabel representativeLabel = new JLabel("Представитель:");
        JLabel categoryLabel = new JLabel("Категория:");
        JLabel defendantLabel = new JLabel("Ответчик:");
        JLabel plaintiffLabel = new JLabel("Истец:");
        JLabel initialSummLabel = new JLabel("Сумма иска:");
        JLabel resultLabel = new JLabel("Решение:");
        JLabel agreedSummLabel = new JLabel("Сумма удовлетворённых требований:");

        categoryChoise = new JComboBox(Category.values());
        defendantChoise = new JComboBox(Defendant.values());
        plaintiffChoise = new JTextField();
        initialSummChoise = new JTextField();
        agreedSummChoise = new JTextField();
        resultChoise = new JComboBox(Result.values());
        representativeChoise = new JComboBox(Representative.values());

        dataPanel.add(representativeLabel);
        dataPanel.add(representativeChoise);

        dataPanel.add(categoryLabel);
        dataPanel.add(categoryChoise);

        dataPanel.add(defendantLabel);
        dataPanel.add(defendantChoise);

        dataPanel.add(plaintiffLabel);
        dataPanel.add(plaintiffChoise);

        dataPanel.add(initialSummLabel);
        dataPanel.add(initialSummChoise);

        dataPanel.add(resultLabel);
        dataPanel.add(resultChoise);

        dataPanel.add(agreedSummLabel);
        dataPanel.add(agreedSummChoise);

        return dataPanel;
    }

    private JPanel getControlButtonsPanel() {
        JPanel controlPanel = new JPanel();
        JButton save = new JButton("Сохранить");

        final JDialog thisDialog = this;
        save.addActionListener( action -> {
            Suit suit = new Suit(
                    (Category) categoryChoise.getSelectedItem(),
                    new Plaintiff(plaintiffChoise.getText()),
                    (Defendant) defendantChoise.getSelectedItem(),
                    new SuitSumm(Double.valueOf(initialSummChoise.getText())),
                    new SuitSumm(Double.valueOf(agreedSummChoise.getText())),
                    (Result) resultChoise.getSelectedItem(),
                    (Representative) representativeChoise.getSelectedItem()
            );
            tableModel.addRow(suit);
            thisDialog.setVisible(false);
        });
        controlPanel.add(save);
        return controlPanel;
    }
}

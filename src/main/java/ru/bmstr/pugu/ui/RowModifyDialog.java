package ru.bmstr.pugu.ui;

import ru.bmstr.pugu.domain.*;

import javax.swing.*;
import java.awt.*;

/**
 * Created by bmstr on 27.11.2016.
 */
public class RowModifyDialog extends JDialog {

    private JComboBox categoryChoise;
    private JComboBox defendantChoise;
    private JTextField plaintiffChoise;
    private JTextField initialSummChoise;
    private JTextField agreedSummChoise;
    private JComboBox resultChoise;
    private JComboBox representativeChoise;

    public RowModifyDialog(Frame owner, String title, boolean isModal) {
        super(owner, title, isModal);
        this.setSize(new Dimension(400, 500));
        this.setLocationRelativeTo(this.getOwner());
        initComponents();
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

    private void initComponents() {
        GridBagLayout layout = new GridBagLayout();
        JPanel dataPanel = new JPanel(layout);
//        Container contentPane = getContentPane();
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 5;
        c.ipady = 5;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.fill = GridBagConstraints.HORIZONTAL;

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

        c.gridx = 0;
        c.gridy = 0;
        layout.setConstraints(categoryLabel, c);
        dataPanel.add(categoryLabel);
        c.gridx = 1;
        layout.setConstraints(categoryChoise, c);
        dataPanel.add(categoryChoise);

        c.gridx = 0;
        c.gridy = 1;
        layout.setConstraints(defendantLabel, c);
        dataPanel.add(defendantLabel);
        c.gridx = 1;
        layout.setConstraints(defendantChoise, c);
        dataPanel.add(defendantChoise);

        c.gridx = 0;
        c.gridy = 2;
        layout.setConstraints(plaintiffLabel, c);
        dataPanel.add(plaintiffLabel);
        c.gridx = 1;
        layout.setConstraints(plaintiffChoise, c);
        dataPanel.add(plaintiffChoise);

        c.gridx = 0;
        c.gridy = 3;
        layout.setConstraints(initialSummLabel, c);
        dataPanel.add(initialSummLabel);
        c.gridx = 1;
        layout.setConstraints(initialSummChoise, c);
        dataPanel.add(initialSummChoise);

        c.gridx = 0;
        c.gridy = 4;
        layout.setConstraints(resultLabel, c);
        dataPanel.add(resultLabel);
        c.gridx = 1;
        layout.setConstraints(resultChoise, c);
        dataPanel.add(resultChoise);

        c.gridx = 0;
        c.gridy = 5;
        layout.setConstraints(agreedSummLabel, c);
        dataPanel.add(agreedSummLabel);
        c.gridx = 1;
        layout.setConstraints(agreedSummChoise, c);
        dataPanel.add(agreedSummChoise);

        this.add(dataPanel);
    }
}

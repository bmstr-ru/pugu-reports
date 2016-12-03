package ru.bmstr.pugu.ui;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bmstr.pugu.domain.*;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by bmstr on 27.11.2016.
 */
public class RowModifyDialog extends JDialog {

    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 300;
    private JComboBox categoryChoise;
    private JComboBox defendantChoise;
    private JTextField plaintiffInput;
    private JFormattedTextField initialSummInput;
    private JFormattedTextField agreedSummInput;
    private JComboBox resultChoise;
    private JComboBox representativeChoise;

    private Suit modifiableSuit;

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
        modifiableSuit = null;
        this.setTitle("Введите данные иска");
        categoryChoise.setSelectedItem(Category.EMPTY);
        defendantChoise.setSelectedItem(Defendant.EMPTY);
        plaintiffInput.setText("");
        initialSummInput.setValue(new Double(0));
        agreedSummInput.setValue(new Double(0));
        resultChoise.setSelectedItem(Result.EMPTY);
        representativeChoise.setSelectedItem(Representative.EMPTY);
        this.setVisible(true);
    }

    public void showModifyRow(Suit suit) {
        modifiableSuit = suit;
        this.setTitle("Иск от гр. " + suit.getPlaintiff());
        categoryChoise.setSelectedItem(suit.getCategory());
        defendantChoise.setSelectedItem(suit.getDefendant());
        plaintiffInput.setText(suit.getPlaintiff().getName());
        initialSummInput.setValue(suit.getInitialSumm().getSumm());
        agreedSummInput.setValue(suit.getAgreedSumm().getSumm());
        resultChoise.setSelectedItem(suit.getResult());
        representativeChoise.setSelectedItem(suit.getRepresentative());
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

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(2);
        categoryChoise = new JComboBox(Category.values());
        defendantChoise = new JComboBox(Defendant.values());
        plaintiffInput = new JTextField();
        initialSummInput = new JFormattedTextField(numberFormat);
        agreedSummInput = new JFormattedTextField(numberFormat);
        resultChoise = new JComboBox(Result.values());
        representativeChoise = new JComboBox(Representative.values());

        dataPanel.add(representativeLabel);
        dataPanel.add(representativeChoise);

        dataPanel.add(categoryLabel);
        dataPanel.add(categoryChoise);

        dataPanel.add(defendantLabel);
        dataPanel.add(defendantChoise);

        dataPanel.add(plaintiffLabel);
        dataPanel.add(plaintiffInput);

        dataPanel.add(initialSummLabel);
        dataPanel.add(initialSummInput);

        dataPanel.add(resultLabel);
        dataPanel.add(resultChoise);

        dataPanel.add(agreedSummLabel);
        dataPanel.add(agreedSummInput);

        return dataPanel;
    }

    private JPanel getControlButtonsPanel() {
        JPanel controlPanel = new JPanel();
        JButton save = new JButton("Сохранить");

        final JDialog thisDialog = this;
        save.addActionListener( action -> {
            if (modifiableSuit != null) {
                modifiableSuit.setCategory((Category) categoryChoise.getSelectedItem());
                modifiableSuit.setPlaintiff(new Plaintiff(plaintiffInput.getText()));
                modifiableSuit.setDefendant((Defendant) defendantChoise.getSelectedItem());
                modifiableSuit.setInitialSumm(new SuitSumm(((Number) initialSummInput.getValue()).doubleValue()));
                modifiableSuit.setAgreedSumm(new SuitSumm(((Number) agreedSummInput.getValue()).doubleValue()));
                modifiableSuit.setResult((Result) resultChoise.getSelectedItem());
                modifiableSuit.setRepresentative((Representative) representativeChoise.getSelectedItem());
                tableModel.reDraw();
            } else {
                Suit suit = new Suit(
                        (Category) categoryChoise.getSelectedItem(),
                        new Plaintiff(plaintiffInput.getText()),
                        (Defendant) defendantChoise.getSelectedItem(),
                        new SuitSumm(((Number) initialSummInput.getValue()).doubleValue()),
                        new SuitSumm(((Number) agreedSummInput.getValue()).doubleValue()),
                        (Result) resultChoise.getSelectedItem(),
                        (Representative) representativeChoise.getSelectedItem()
                );
                tableModel.addRow(suit);
            }
            thisDialog.setVisible(false);
        });
        controlPanel.add(save);
        return controlPanel;
    }
}

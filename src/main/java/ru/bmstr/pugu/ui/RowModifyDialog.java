package ru.bmstr.pugu.ui;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bmstr.pugu.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * Created by bmstr on 27.11.2016.
 */
public class RowModifyDialog extends JDialog {

    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 300;
    private JComboBox yearChoise;
    private JComboBox categoryChoise;
    private JComboBox defendantChoise;
    private JTextField plaintiffInput;
    private JFormattedTextField initialSummInput;
    private JFormattedTextField agreedSummInput;
    private JComboBox resultChoise;
    private ButtonGroup buttonGroup;
    JRadioButton usualButton;
    JRadioButton ourButton;
    JRadioButton appelationButton;
    JRadioButton cassationButton;

    private Suit modifiableSuit;

    @Autowired
    private MyTableModel tableModel;

    public RowModifyDialog(Frame owner, java.lang.String title, boolean isModal) {
        super(owner, title, isModal);
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setLocationRelativeTo(this.getOwner());
        Container pane = this.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(getTypeChoisePanel());
        pane.add(getInputDataPanel());
        pane.add(getControlButtonsPanel());
    }

    public void showAddRow() {
        modifiableSuit = null;
        this.setTitle("Введите данные иска");
        usualButton.setSelected(true);
        yearChoise.setSelectedItem(Year.Y2016);
        categoryChoise.setSelectedItem(Category.EMPTY);
        defendantChoise.setSelectedItem(Defendant.EMPTY);
        plaintiffInput.setText("");
        initialSummInput.setValue(0);
        agreedSummInput.setValue(0);
        resultChoise.setSelectedItem(Result.EMPTY);
        this.setVisible(true);
    }

    public void showModifyRow(Suit suit) {
        modifiableSuit = suit;
        this.setTitle("Иск от гр. " + suit.getPlaintiff());
        yearChoise.setSelectedItem(suit.getYear());
        categoryChoise.setSelectedItem(suit.getCategory());
        defendantChoise.setSelectedItem(suit.getDefendant());
        plaintiffInput.setText(suit.getPlaintiff());
        initialSummInput.setValue(suit.getInitialSumm());
        agreedSummInput.setValue(suit.getAgreedSumm());
        resultChoise.setSelectedItem(suit.getResult());
        switch (suit.getType()) {
            case USUAL: usualButton.setSelected(true); break;
            case OUR: ourButton.setSelected(true); break;
            case APPELATION: appelationButton.setSelected(true); break;
            case CASSATION: cassationButton.setSelected(true); break;
        }
        this.setVisible(true);
    }

    private JPanel getTypeChoisePanel() {
        JPanel panel = new JPanel();

        ActionListener listener = event -> {
            SwingUtilities.invokeLater( () -> {
                String command = event.getActionCommand();
                switch(SuitType.valueOf(command)) {
                    case USUAL:

                        break;
                    case OUR:
                        break;
                    case APPELATION:
                        break;
                    case CASSATION:
                        break;
                }
            });
        };

        usualButton = new JRadioButton(SuitType.USUAL.getName());
        usualButton.setActionCommand(SuitType.USUAL.name());
        usualButton.setSelected(true);
        usualButton.addActionListener(listener);

        ourButton = new JRadioButton(SuitType.OUR.getName());
        ourButton.setActionCommand(SuitType.OUR.name());
        ourButton.addActionListener(listener);

        appelationButton = new JRadioButton(SuitType.APPELATION.getName());
        appelationButton.setActionCommand(SuitType.APPELATION.name());
        appelationButton.addActionListener(listener);

        cassationButton = new JRadioButton(SuitType.CASSATION.getName());
        cassationButton.setActionCommand(SuitType.CASSATION.name());
        cassationButton.addActionListener(listener);

        buttonGroup = new ButtonGroup();
        buttonGroup.add(usualButton);
        buttonGroup.add(ourButton);
        buttonGroup.add(appelationButton);
        buttonGroup.add(cassationButton);

        panel.add(usualButton);
        panel.add(ourButton);
        panel.add(appelationButton);
        panel.add(cassationButton);

        return panel;
    }

    private JPanel getInputDataPanel() {
        GridLayout layout = new GridLayout(7,2);
        JPanel dataPanel = new JPanel(layout);

        JLabel yearLabel = new JLabel("Год:");
        JLabel categoryLabel = new JLabel("Категория:");
        JLabel plaintiffLabel = new JLabel("Истец:");
        JLabel defendantLabel = new JLabel("Ответчик:");
        JLabel initialSummLabel = new JLabel("Сумма иска:");
        JLabel resultLabel = new JLabel("Решение:");
        JLabel agreedSummLabel = new JLabel("Сумма удовлетворённых требований:");

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(0);
        yearChoise = new JComboBox(Year.values());
        categoryChoise = new JComboBox(Category.values());
        defendantChoise = new JComboBox(Defendant.values());
        plaintiffInput = new JTextField();
        initialSummInput = new JFormattedTextField(numberFormat);
        agreedSummInput = new JFormattedTextField(numberFormat);
        resultChoise = new JComboBox(Result.values());

        dataPanel.add(yearLabel);
        dataPanel.add(yearChoise);

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
                modifiableSuit.setType(SuitType.valueOf(buttonGroup.getSelection().getActionCommand()));
                modifiableSuit.setYear((Year) yearChoise.getSelectedItem());
                modifiableSuit.setCategory((Category) categoryChoise.getSelectedItem());
                modifiableSuit.setPlaintiff(new String(plaintiffInput.getText()));
                modifiableSuit.setDefendant((Defendant) defendantChoise.getSelectedItem());
                modifiableSuit.setInitialSumm(((Number) initialSummInput.getValue()).intValue());
                modifiableSuit.setAgreedSumm(((Number) agreedSummInput.getValue()).intValue());
                modifiableSuit.setResult((Result) resultChoise.getSelectedItem());
                tableModel.reDraw();
            } else {
                Suit suit = Suit.getBuilder()
                        .withYear((Year) yearChoise.getSelectedItem())
                        .withType(buttonGroup.getSelection().getActionCommand())
                        .withCategory((Category) categoryChoise.getSelectedItem())
                        .withDefendant((Defendant) defendantChoise.getSelectedItem())
                        .withPlaintiff(plaintiffInput.getText())
                        .withInitialSumm(((Number) initialSummInput.getValue()).intValue())
                        .withAgreedSumm(((Number) agreedSummInput.getValue()).intValue())
                        .withResult((Result) resultChoise.getSelectedItem())
                        .getSuit();
                tableModel.addRow(suit);
            }
            thisDialog.setVisible(false);
        });
        controlPanel.add(save);
        return controlPanel;
    }
}

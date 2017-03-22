package ru.bmstr.pugu.ui;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bmstr.pugu.db.DatabaseManager;
import ru.bmstr.pugu.domain.*;
import ru.bmstr.pugu.properties.PropertyLoader;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import static ru.bmstr.pugu.properties.PropertyNames.*;

/**
 * Created by bmstr on 27.11.2016.
 */
public class RowModifyDialog extends JDialog {

    private static final int DEFAULT_WIDTH = 500;
    private static final int DEFAULT_HEIGHT = 350;

    private JComboBox typeChoise;
    private JComboBox representativeChoise;
    private JComboBox yearChoise;
    private JComboBox categoryChoise;
    private DefaultComboBoxModel usualCategories;
    private DefaultComboBoxModel ourCategories;
    private DefaultComboBoxModel emptyCategory;
    private JComboBox defendantChoise;
    private JTextField plaintiffInput;
    private JFormattedTextField initialSummInput;
    private JFormattedTextField agreedSummInput;
    private JComboBox resultChoise;
    private JLabel typeLabel;
    private JLabel representativeLabel;
    private JLabel yearLabel;
    private JLabel categoryLabel;
    private JLabel plaintiffLabel;
    private JLabel defendantLabel;
    private JLabel initialSummLabel;
    private JLabel resultLabel;
    private JLabel agreedSummLabel;

    JButton save;

    private Suit modifiableSuit;

    @Autowired
    private MyTableModel tableModel;

    @Autowired
    private PropertyLoader propertyLoader;

    @Autowired
    private DatabaseManager databaseManager;

    private boolean labelsInitialized = false;

    private final RowModifyDialog iAm;

    public RowModifyDialog(Frame owner) {
        super(owner, "", true);
        iAm = this;
    }

    public void initialize() {
        usualCategories = new DefaultComboBoxModel(
                ((ArrayList<Category>) databaseManager.retriveAll(Category.class))
                        .stream()
                        .filter(category -> category.getType().getDirection() == Direction.TO_US)
                        .toArray(size -> new Category[size])
        );
        ourCategories = new DefaultComboBoxModel(
                ((ArrayList<Category>) databaseManager.retriveAll(Category.class))
                        .stream()
                        .filter(category -> category.getType().getDirection() == Direction.OUR)
                        .toArray(size -> new Category[size])
        );
        emptyCategory = new DefaultComboBoxModel(new Category[] {Category.EMPTY_CATEGORY});
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setLocationRelativeTo(this.getOwner());
        Container pane = this.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(getInputDataPanel());
        pane.add(getControlButtonsPanel());
    }

    private void setLabels() {
        if (!labelsInitialized) {
            typeLabel.setText(propertyLoader.getProperty(LABEL_TYPE));
            representativeLabel.setText(propertyLoader.getProperty(LABEL_REPRESENTATIVE));
            yearLabel.setText(propertyLoader.getProperty(LABEL_YEAR));
            categoryLabel.setText(propertyLoader.getProperty(LABEL_CATEGORY));
            plaintiffLabel.setText(propertyLoader.getProperty(LABEL_PLAINTIFF));
            defendantLabel.setText(propertyLoader.getProperty(LABEL_DEFFENDER));
            initialSummLabel.setText(propertyLoader.getProperty(LABEL_INITIAL_SUMM));
            resultLabel.setText(propertyLoader.getProperty(LABEL_RESULT));
            agreedSummLabel.setText(propertyLoader.getProperty(LABEL_AGREED_SUMM));

            save.setText(propertyLoader.getProperty(BUTTON_SAVE));

            labelsInitialized = true;
        }
    }

    public void showAddRow() {
        setLabels();
        modifiableSuit = null;
        this.setTitle(propertyLoader.getProperty(SUIT_ENTER));
        typeChoise.setSelectedIndex(0);
        representativeChoise.setSelectedIndex(0);
        yearChoise.setSelectedItem(2017);
        categoryChoise.setSelectedIndex(0);
        defendantChoise.setSelectedIndex(0);
        plaintiffInput.setText("");
        initialSummInput.setValue(0);
        agreedSummInput.setValue(0);
        resultChoise.setSelectedIndex(0);
        this.setVisible(true);
    }

    public void showModifyRow(Suit suit) {
        setLabels();
        modifiableSuit = suit;
        this.setTitle(propertyLoader.getProperty(SUIT_FROM) + suit.getPlaintiff());
        typeChoise.setSelectedItem(suit.getType());
        representativeChoise.setSelectedItem(suit.getRepresentative());
        yearChoise.setSelectedItem(suit.getYear());
        categoryChoise.setSelectedItem(suit.getCategory());
        defendantChoise.setSelectedItem(suit.getDefendant());
        plaintiffInput.setText(suit.getPlaintiff());
        initialSummInput.setValue(suit.getInitialSumm());
        agreedSummInput.setValue(suit.getAgreedSumm());
        resultChoise.setSelectedItem(suit.getResult());
        setDependantComponents(suit.getType());
        this.setVisible(true);
    }

    private void setDependantComponents(SuitType type) {
        if (type.getDirection() == null) {
            plaintiffLabel.setText(propertyLoader.getProperty(LABEL_PLAINTIFF));
            defendantLabel.setText(propertyLoader.getProperty(LABEL_DEFFENDER));
            categoryChoise.setModel(emptyCategory);
        } else {
            switch (type.getDirection()) {
                case TO_US:
                    plaintiffLabel.setText(propertyLoader.getProperty(LABEL_PLAINTIFF));
                    defendantLabel.setText(propertyLoader.getProperty(LABEL_DEFFENDER));
                    categoryChoise.setModel(usualCategories);
                    break;
                case OUR:
                    plaintiffLabel.setText(propertyLoader.getProperty(LABEL_DEFFENDER));
                    defendantLabel.setText(propertyLoader.getProperty(LABEL_PLAINTIFF));
                    categoryChoise.setModel(ourCategories);
                    break;
                default:
                    plaintiffLabel.setText(propertyLoader.getProperty(LABEL_PLAINTIFF));
                    defendantLabel.setText(propertyLoader.getProperty(LABEL_DEFFENDER));
                    categoryChoise.setModel(emptyCategory);
                    break;
            }
        }
    }

    private JPanel getInputDataPanel() {
        GridLayout layout = new GridLayout(9, 2);
        JPanel dataPanel = new JPanel(layout);

        typeLabel = new JLabel();
        representativeLabel = new JLabel();
        yearLabel = new JLabel();
        categoryLabel = new JLabel();
        plaintiffLabel = new JLabel();
        defendantLabel = new JLabel();
        initialSummLabel = new JLabel();
        resultLabel = new JLabel();
        agreedSummLabel = new JLabel();

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(0);
        typeChoise = new JComboBox(databaseManager.retriveAllWithEmpty(SuitType.class).toArray());
        representativeChoise = new JComboBox(databaseManager.retriveAllWithEmpty(Representative.class).toArray());
        yearChoise = new JComboBox(new Integer[] {2015, 2016, 2017});
        categoryChoise = new JComboBox(usualCategories);
        defendantChoise = new JComboBox(databaseManager.retriveAllWithEmpty(Defendant.class).toArray());
        plaintiffInput = new JTextField();
        initialSummInput = new JFormattedTextField(numberFormat);
        agreedSummInput = new JFormattedTextField(numberFormat);
        resultChoise = new JComboBox(databaseManager.retriveAllWithEmpty(Result.class).toArray());

        typeChoise.addActionListener(event -> {
            setDependantComponents((SuitType) typeChoise.getSelectedItem());
        });

        dataPanel.add(typeLabel);
        dataPanel.add(typeChoise);

        dataPanel.add(representativeLabel);
        dataPanel.add(representativeChoise);

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
        save = new JButton();

        final JDialog thisDialog = this;
        save.addActionListener(action -> {
            if (SuitType.isEmpty((SuitType) typeChoise.getSelectedItem())) {
                JOptionPane.showMessageDialog(iAm,
                        propertyLoader.getProperty(SAVE_ERROR_TYPE_EMPTY),
                        propertyLoader.getProperty(SAVE_ERROR_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } else if (!(sumIsValid(initialSummInput))) {
                JOptionPane.showMessageDialog(iAm,
                        propertyLoader.getProperty(SAVE_ERROR_INVALID_INITIAL_SUMM),
                        propertyLoader.getProperty(SAVE_ERROR_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } else if (!(sumIsValid(agreedSummInput))) {
                JOptionPane.showMessageDialog(iAm,
                        propertyLoader.getProperty(SAVE_ERROR_INVALID_AGREED_SUMM),
                        propertyLoader.getProperty(SAVE_ERROR_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } else {
                if (modifiableSuit != null) {
                    modifiableSuit.setRepresentative(Representative.isEmpty((Representative) representativeChoise.getSelectedItem()) ? null : (Representative) representativeChoise.getSelectedItem());
                    modifiableSuit.setType((SuitType.isEmpty((SuitType) typeChoise.getSelectedItem())) ? null : (SuitType) typeChoise.getSelectedItem());
                    modifiableSuit.setYear((Integer) yearChoise.getSelectedItem());
                    modifiableSuit.setCategory((Category.isEmpty((Category) categoryChoise.getSelectedItem())) ? null : (Category) categoryChoise.getSelectedItem());
                    modifiableSuit.setPlaintiff(plaintiffInput.getText() == null ? "" : plaintiffInput.getText());
                    modifiableSuit.setDefendant((Defendant.isEmpty((Defendant) defendantChoise.getSelectedItem())) ? null : (Defendant) defendantChoise.getSelectedItem());
                    modifiableSuit.setInitialSumm(initialSummInput.getValue() == null ? 0 : ((Number) initialSummInput.getValue()).intValue());
                    modifiableSuit.setAgreedSumm(agreedSummInput.getValue() == null ? 0 : ((Number) agreedSummInput.getValue()).intValue());
                    modifiableSuit.setResult((Result.isEmpty((Result) resultChoise.getSelectedItem())) ? null : (Result) resultChoise.getSelectedItem());
                    databaseManager.update(modifiableSuit);
                    tableModel.reDraw();
                } else {
                    Suit suit = Suit.getBuilder()
                            .withRepresentative(Representative.isEmpty((Representative) representativeChoise.getSelectedItem()) ? null : (Representative) representativeChoise.getSelectedItem())
                            .withType((SuitType.isEmpty((SuitType) typeChoise.getSelectedItem())) ? null : (SuitType) typeChoise.getSelectedItem())
                            .withYear((Integer) yearChoise.getSelectedItem())
                            .withCategory((Category.isEmpty((Category) categoryChoise.getSelectedItem())) ? null : (Category) categoryChoise.getSelectedItem())
                            .withDefendant((Defendant.isEmpty((Defendant) defendantChoise.getSelectedItem())) ? null : (Defendant) defendantChoise.getSelectedItem())
                            .withPlaintiff(plaintiffInput.getText() == null ? "" : plaintiffInput.getText())
                            .withInitialSumm(initialSummInput.getValue() == null ? 0 : ((Number) initialSummInput.getValue()).intValue())
                            .withAgreedSumm(agreedSummInput.getValue() == null ? 0 :((Number) agreedSummInput.getValue()).intValue())
                            .withResult((Result.isEmpty((Result) resultChoise.getSelectedItem())) ? null : (Result) resultChoise.getSelectedItem())
                            .getSuit();
                    tableModel.addRow(suit);
                }
                thisDialog.setVisible(false);
            }
        });
        controlPanel.add(save);
        return controlPanel;
    }

    private static boolean sumIsValid(JFormattedTextField field) {
        try {
            ((Number) field.getValue()).intValue();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

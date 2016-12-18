package ru.bmstr.pugu.ui;

import org.springframework.beans.factory.annotation.Autowired;
import ru.bmstr.pugu.domain.*;
import ru.bmstr.pugu.properties.PropertyLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
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
    private DefaultComboBoxModel emptyCategories;
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

    private boolean labelsInitialized = false;

    public RowModifyDialog(Frame owner) {
        super(owner, "", true);
        usualCategories = new DefaultComboBoxModel(
                Arrays.asList(Category.values())
                        .stream()
                        .filter(category -> category.getType() == SuitType.USUAL || category == Category.EMPTY)
                        .toArray(size -> new Category[size])
        );
        ourCategories = new DefaultComboBoxModel(
                Arrays.asList(Category.values())
                        .stream()
                        .filter(category -> category.getType() == SuitType.OUR || category == Category.EMPTY)
                        .toArray(size -> new Category[size])
        );
        emptyCategories = new DefaultComboBoxModel(new Category[]{});
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
        typeChoise.setSelectedItem(SuitType.EMPTY);
        representativeChoise.setSelectedItem(Representative.EMPTY);
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
        switch (type) {
            case USUAL:
                plaintiffLabel.setText(propertyLoader.getProperty(LABEL_PLAINTIFF));
                defendantLabel.setText(propertyLoader.getProperty(LABEL_DEFFENDER));
                categoryChoise.setModel(usualCategories);
                break;
            case OUR:
                plaintiffLabel.setText(propertyLoader.getProperty(LABEL_DEFFENDER));
                defendantLabel.setText(propertyLoader.getProperty(LABEL_PLAINTIFF));
                categoryChoise.setModel(ourCategories);
                break;
            case APPELATION:
            case CASSATION:
            case SPECIAL:
            case THIRD_PARTY:
                plaintiffLabel.setText(propertyLoader.getProperty(LABEL_PLAINTIFF));
                defendantLabel.setText(propertyLoader.getProperty(LABEL_DEFFENDER));
                categoryChoise.setModel(emptyCategories);
                break;
            case OUR_APPELATION:
            case OUR_CASSATION:
                plaintiffLabel.setText(propertyLoader.getProperty(LABEL_DEFFENDER));
                defendantLabel.setText(propertyLoader.getProperty(LABEL_PLAINTIFF));
                categoryChoise.setModel(emptyCategories);
                break;
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
        typeChoise = new JComboBox(SuitType.values());
        representativeChoise = new JComboBox(Representative.values());
        yearChoise = new JComboBox(Year.values());
        categoryChoise = new JComboBox(usualCategories);
        defendantChoise = new JComboBox(Defendant.values());
        plaintiffInput = new JTextField();
        initialSummInput = new JFormattedTextField(numberFormat);
        agreedSummInput = new JFormattedTextField(numberFormat);
        resultChoise = new JComboBox(Result.values());

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
            if (modifiableSuit != null) {
                modifiableSuit.setType((SuitType) typeChoise.getSelectedItem());
                modifiableSuit.setYear((Year) yearChoise.getSelectedItem());
                modifiableSuit.setCategory((Category) categoryChoise.getSelectedItem());
                modifiableSuit.setPlaintiff(new String(plaintiffInput.getText()));
                modifiableSuit.setDefendant((Defendant) defendantChoise.getSelectedItem());
                modifiableSuit.setInitialSumm(((Number) initialSummInput.getValue()).intValue());
                modifiableSuit.setAgreedSumm(((Number) agreedSummInput.getValue()).intValue());
                modifiableSuit.setResult((Result) resultChoise.getSelectedItem());
                modifiableSuit.setRepresentative((Representative) representativeChoise.getSelectedItem());
                tableModel.reDraw();
            } else {
                Suit suit = Suit.getBuilder()
                        .withYear((Year) yearChoise.getSelectedItem())
                        .withType((SuitType) typeChoise.getSelectedItem())
                        .withCategory((Category) categoryChoise.getSelectedItem())
                        .withDefendant((Defendant) defendantChoise.getSelectedItem())
                        .withPlaintiff(plaintiffInput.getText())
                        .withInitialSumm(((Number) initialSummInput.getValue()).intValue())
                        .withAgreedSumm(((Number) agreedSummInput.getValue()).intValue())
                        .withResult((Result) resultChoise.getSelectedItem())
                        .withRepresentative((Representative) representativeChoise.getSelectedItem())
                        .getSuit();
                tableModel.addRow(suit);
            }
            thisDialog.setVisible(false);
        });
        controlPanel.add(save);
        return controlPanel;
    }
}

package ru.bmstr.pugu.ui;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import ru.bmstr.pugu.beans.AllBeans;
import ru.bmstr.pugu.db.DatabaseManager;
import ru.bmstr.pugu.domain.*;
import ru.bmstr.pugu.properties.PropertyLoader;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static ru.bmstr.pugu.properties.PropertyNames.*;

/**
 * @author bmstr
 */
@Component
public class RowModifyDialog extends JDialog {

    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 350;

    private DefaultComboBoxModel usualCategories;
    private DefaultComboBoxModel ourCategories;
    private DefaultComboBoxModel emptyCategory;


    private Suit modifiableSuit;

    @Autowired
    private MyTableModel tableModel;

    @Autowired
    private PropertyLoader propertyLoader;

    @Autowired
    private DatabaseManager databaseManager;

    private final RowModifyDialog iAm;

    @Autowired
    public RowModifyDialog(Frame parent) {
        super(parent, true);
        iAm = this;
    }

    public void initialize() {
        this.setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        this.setLocationRelativeTo(this.getOwner());

        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMinimumFractionDigits(0);

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
        emptyCategory = new DefaultComboBoxModel(new Category[]{Category.EMPTY_CATEGORY});
        suitDataLabel = new JLabel();
        suitPanel = new JPanel();
        suitTypePanel = new JPanel();
        suitTypeLabel = new JLabel();
        suitTypeChoise = new JComboBox(databaseManager.retriveAllWithEmpty(SuitType.class).toArray());
        representativePanel = new JPanel();
        representativeLabel = new JLabel();
        representativeChoise = new JComboBox(databaseManager.retriveAllWithEmpty(Representative.class).toArray());
        yearPanel = new JPanel();
        yearLabel = new JLabel();
        Integer currentYear = LocalDateTime.now().getYear();
        yearChoise = new JComboBox(new Integer[]{currentYear - 1, currentYear});
        categoryPanel = new JPanel();
        categoryLabel = new JLabel();
        categoryChoise = new JComboBox(usualCategories);
        defendantPanel = new JPanel();
        defendantLabel = new JLabel();
        defendantChoise = new JComboBox(databaseManager.retriveAllWithEmpty(Defendant.class).toArray());
        plaintiffPanel = new JPanel();
        plaintiffLabel = new JLabel();
        plaintiffInput = new JTextField();
        initialSumPanel = new JPanel();
        initialSumLabel = new JLabel();
        initialSumInput = new JFormattedTextField(numberFormat);
        resultPanel = new JPanel();
        resultLabel = new JLabel();
        resultChoise = new JComboBox(databaseManager.retriveAllWithEmpty(Result.class).toArray());
        agreedSumPanel = new JPanel();
        agreedSumLabel = new JLabel();
        agreedSumInput = new JFormattedTextField(numberFormat);
        appealPanel = new JPanel();
        appealResultPanel = new JPanel();
        appealResultLabel = new JLabel();
        appealResultChoise = new JComboBox(databaseManager.retriveAllWithEmpty(Result.class).toArray());
        appealAgreedSumPanel = new JPanel();
        appealAgreedSumLabel = new JLabel();
        appealAgreedSumInput = new JFormattedTextField(numberFormat);
        appealYearLabel = new JLabel("appealYearLabel");
        appealYearPanel = new JPanel();
        appealYearChoise = new JComboBox(new Integer[]{currentYear - 1, currentYear});
        hasApeal = new JCheckBox();
        hasCassation = new JCheckBox();
        cassationPanel = new JPanel();
        cassationResultPanel = new JPanel();
        cassationResultLabel = new JLabel("cassationYearLabel");
        cassationResultChoise = new JComboBox(databaseManager.retriveAllWithEmpty(Result.class).toArray());
        cassationAgreedSumPanel = new JPanel();
        cassationAgreedSumLabel = new JLabel();
        cassationAgreedSumInput = new JFormattedTextField(numberFormat);
        cassationYearLabel = new JLabel("appealYearLabel");
        cassationYearPanel = new JPanel();
        cassationYearChoise = new JComboBox(new Integer[]{currentYear - 1, currentYear});
        inSupremeCourt = new JCheckBox();
        saveButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        suitDataLabel.setText(propertyLoader.getProperty(LABEL_SUIT_DATA));

        suitTypeLabel.setText(propertyLoader.getProperty(LABEL_TYPE));

        suitTypeChoise.addActionListener(event -> {
            setDependantComponents((SuitType) suitTypeChoise.getSelectedItem());
        });

        GroupLayout suitTypePanelLayout = new GroupLayout(suitTypePanel);
        suitTypePanel.setLayout(suitTypePanelLayout);
        suitTypePanelLayout.setHorizontalGroup(
                suitTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(suitTypePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(suitTypeLabel)
                                .addGap(18, 18, 18)
                                .addComponent(suitTypeChoise, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        suitTypePanelLayout.setVerticalGroup(
                suitTypePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(suitTypePanelLayout.createSequentialGroup()
                                .addGroup(suitTypePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(suitTypeLabel)
                                        .addComponent(suitTypeChoise, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
        );

        representativeLabel.setText(propertyLoader.getProperty(LABEL_REPRESENTATIVE));

        GroupLayout representativePanelLayout = new GroupLayout(representativePanel);
        representativePanel.setLayout(representativePanelLayout);
        representativePanelLayout.setHorizontalGroup(
                representativePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(representativePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(representativeLabel)
                                .addGap(18, 18, 18)
                                .addComponent(representativeChoise, 0, 241, Short.MAX_VALUE))
        );
        representativePanelLayout.setVerticalGroup(
                representativePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(representativePanelLayout.createSequentialGroup()
                                .addGroup(representativePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(representativeLabel)
                                        .addComponent(representativeChoise, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
        );

        yearLabel.setText(propertyLoader.getProperty(LABEL_YEAR));

        GroupLayout yearPanelLayout = new GroupLayout(yearPanel);
        yearPanel.setLayout(yearPanelLayout);
        yearPanelLayout.setHorizontalGroup(
                yearPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(yearPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(yearLabel)
                                .addGap(18, 18, 18)
                                .addComponent(yearChoise, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        yearPanelLayout.setVerticalGroup(
                yearPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(yearPanelLayout.createSequentialGroup()
                                .addGroup(yearPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(yearLabel)
                                        .addComponent(yearChoise, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
        );

        categoryLabel.setText(propertyLoader.getProperty(LABEL_CATEGORY));

        GroupLayout categoryPanelLayout = new GroupLayout(categoryPanel);
        categoryPanel.setLayout(categoryPanelLayout);
        categoryPanelLayout.setHorizontalGroup(
                categoryPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(categoryPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(categoryLabel)
                                .addGap(18, 18, 18)
                                .addComponent(categoryChoise, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        categoryPanelLayout.setVerticalGroup(
                categoryPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(categoryPanelLayout.createSequentialGroup()
                                .addGroup(categoryPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(categoryLabel)
                                        .addComponent(categoryChoise, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
        );

        defendantPanel.setPreferredSize(new Dimension(139, 25));

        defendantLabel.setText(propertyLoader.getProperty(LABEL_DEFFENDER));

        GroupLayout defendantPanelLayout = new GroupLayout(defendantPanel);
        defendantPanel.setLayout(defendantPanelLayout);
        defendantPanelLayout.setHorizontalGroup(
                defendantPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(defendantPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(defendantLabel)
                                .addGap(18, 18, 18)
                                .addComponent(defendantChoise, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        defendantPanelLayout.setVerticalGroup(
                defendantPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(defendantPanelLayout.createSequentialGroup()
                                .addGroup(defendantPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(defendantLabel)
                                        .addComponent(defendantChoise, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 5, Short.MAX_VALUE))
        );

        plaintiffLabel.setText(propertyLoader.getProperty(LABEL_PLAINTIFF));

        GroupLayout plaintiffPanelLayout = new GroupLayout(plaintiffPanel);
        plaintiffPanel.setLayout(plaintiffPanelLayout);
        plaintiffPanelLayout.setHorizontalGroup(
                plaintiffPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(plaintiffPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(plaintiffLabel)
                                .addGap(18, 18, 18)
                                .addComponent(plaintiffInput))
        );
        plaintiffPanelLayout.setVerticalGroup(
                plaintiffPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(plaintiffPanelLayout.createSequentialGroup()
                                .addGroup(plaintiffPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(plaintiffLabel)
                                        .addComponent(plaintiffInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
        );

        initialSumPanel.setPreferredSize(new Dimension(0, 25));

        initialSumLabel.setText(propertyLoader.getProperty(LABEL_INITIAL_SUMM));

        GroupLayout initialSumPanelLayout = new GroupLayout(initialSumPanel);
        initialSumPanel.setLayout(initialSumPanelLayout);
        initialSumPanelLayout.setHorizontalGroup(
                initialSumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(initialSumPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(initialSumLabel)
                                .addGap(18, 18, 18)
                                .addComponent(initialSumInput))
        );
        initialSumPanelLayout.setVerticalGroup(
                initialSumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(initialSumPanelLayout.createSequentialGroup()
                                .addGroup(initialSumPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(initialSumLabel)
                                        .addComponent(initialSumInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 5, Short.MAX_VALUE))
        );

        resultPanel.setPreferredSize(new Dimension(0, 25));

        resultLabel.setText(propertyLoader.getProperty(LABEL_RESULT));

        GroupLayout resultPanelLayout = new GroupLayout(resultPanel);
        resultPanel.setLayout(resultPanelLayout);
        resultPanelLayout.setHorizontalGroup(
                resultPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(resultPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(resultLabel)
                                .addGap(18, 18, 18)
                                .addComponent(resultChoise, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        resultPanelLayout.setVerticalGroup(
                resultPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(resultPanelLayout.createSequentialGroup()
                                .addGroup(resultPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(resultLabel)
                                        .addComponent(resultChoise, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 5, Short.MAX_VALUE))
        );

        agreedSumPanel.setPreferredSize(new Dimension(0, 25));

        agreedSumLabel.setText(propertyLoader.getProperty(LABEL_AGREED_SUMM));

        GroupLayout agreedSumPanelLayout = new GroupLayout(agreedSumPanel);
        agreedSumPanel.setLayout(agreedSumPanelLayout);
        agreedSumPanelLayout.setHorizontalGroup(
                agreedSumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(agreedSumPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(agreedSumLabel)
                                .addGap(18, 18, 18)
                                .addComponent(agreedSumInput))
        );
        agreedSumPanelLayout.setVerticalGroup(
                agreedSumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(agreedSumPanelLayout.createSequentialGroup()
                                .addGroup(agreedSumPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(agreedSumLabel)
                                        .addComponent(agreedSumInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 5, Short.MAX_VALUE))
        );

        GroupLayout suitPanelLayout = new GroupLayout(suitPanel);
        suitPanel.setLayout(suitPanelLayout);
        suitPanel.setPreferredSize(new Dimension(400, 320));
        suitPanelLayout.setHorizontalGroup(
                suitPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(suitTypePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(representativePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(yearPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(categoryPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(defendantPanel, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                        .addComponent(plaintiffPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(initialSumPanel, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                        .addComponent(resultPanel, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                        .addComponent(agreedSumPanel, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
        );
        suitPanelLayout.setVerticalGroup(
                suitPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(suitPanelLayout.createSequentialGroup()
                                .addComponent(suitTypePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(representativePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(yearPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(categoryPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(defendantPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(plaintiffPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(initialSumPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(resultPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(agreedSumPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        appealResultLabel.setText(propertyLoader.getProperty(LABEL_RESULT));

        GroupLayout appealResultPanelLayout = new GroupLayout(appealResultPanel);
        appealResultPanel.setLayout(appealResultPanelLayout);
        appealResultPanelLayout.setHorizontalGroup(
                appealResultPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(appealResultPanelLayout.createSequentialGroup()
                                .addComponent(appealResultLabel)
                                .addGap(18, 18, 18)
                                .addComponent(appealResultChoise, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        appealResultPanelLayout.setVerticalGroup(
                appealResultPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(appealResultPanelLayout.createSequentialGroup()
                                .addGroup(appealResultPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(appealResultLabel)
                                        .addComponent(appealResultChoise, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
        );

        appealAgreedSumLabel.setText(propertyLoader.getProperty(LABEL_AGREED_SUMM));

        GroupLayout appealAgreedSumPanelLayout = new GroupLayout(appealAgreedSumPanel);
        appealAgreedSumPanel.setLayout(appealAgreedSumPanelLayout);
        appealAgreedSumPanelLayout.setHorizontalGroup(
                appealAgreedSumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(appealAgreedSumPanelLayout.createSequentialGroup()
                                .addComponent(appealAgreedSumLabel)
                                .addGap(18, 18, 18)
                                .addComponent(appealAgreedSumInput))
        );
        appealAgreedSumPanelLayout.setVerticalGroup(
                appealAgreedSumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(appealAgreedSumPanelLayout.createSequentialGroup()
                                .addGroup(appealAgreedSumPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(appealAgreedSumLabel)
                                        .addComponent(appealAgreedSumInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
        );

        GroupLayout appealYearPanelLayout = new GroupLayout(appealYearPanel);
        appealYearPanel.setLayout(appealYearPanelLayout);
        appealYearPanelLayout.setHorizontalGroup(
                appealYearPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(appealYearPanelLayout.createSequentialGroup()
                                .addComponent(appealYearLabel)
                                .addGap(18, 18, 18)
                                .addComponent(appealYearChoise))
        );
        appealYearPanelLayout.setVerticalGroup(
                appealYearPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(appealYearPanelLayout.createSequentialGroup()
                                .addGroup(appealYearPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(appealYearLabel)
                                        .addComponent(appealYearChoise, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
        );

        GroupLayout appealPanelLayout = new GroupLayout(appealPanel);
        appealPanel.setLayout(appealPanelLayout);
        appealPanelLayout.setHorizontalGroup(
                appealPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(appealResultPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(appealAgreedSumPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(appealYearPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        appealPanelLayout.setVerticalGroup(
                appealPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(appealPanelLayout.createSequentialGroup()
                                .addComponent(appealResultPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(appealAgreedSumPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(appealYearPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        hasApeal.setText(propertyLoader.getProperty(HAS_APPEAL));
        hasApeal.addItemListener(event -> {
            if (hasApeal.isSelected()) {
                appealAgreedSumLabel.setEnabled(true);
                appealAgreedSumInput.setEnabled(true);
                appealResultLabel.setEnabled(true);
                appealResultChoise.setEnabled(true);
                appealYearLabel.setEnabled(true);
                appealYearChoise.setEnabled(true);
            } else {
                appealAgreedSumLabel.setEnabled(false);
                appealAgreedSumInput.setEnabled(false);
                appealResultLabel.setEnabled(false);
                appealResultChoise.setEnabled(false);
                appealYearLabel.setEnabled(false);
                appealYearChoise.setEnabled(false);
            }
        });

        hasCassation.setText(propertyLoader.getProperty(HAS_CASSATION));
        hasCassation.addItemListener(event -> {
            if (hasCassation.isSelected()) {
                cassationAgreedSumLabel.setEnabled(true);
                cassationAgreedSumInput.setEnabled(true);
                cassationResultLabel.setEnabled(true);
                cassationResultChoise.setEnabled(true);
                cassationYearLabel.setEnabled(true);
                cassationYearChoise.setEnabled(true);
                inSupremeCourt.setEnabled(true);
            } else {
                cassationAgreedSumLabel.setEnabled(false);
                cassationAgreedSumInput.setEnabled(false);
                cassationResultLabel.setEnabled(false);
                cassationResultChoise.setEnabled(false);
                cassationYearLabel.setEnabled(false);
                cassationYearChoise.setEnabled(false);
                inSupremeCourt.setEnabled(false);
            }
        });


        cassationResultLabel.setText(propertyLoader.getProperty(LABEL_RESULT));

        GroupLayout cassationResultPanelLayout = new GroupLayout(cassationResultPanel);
        cassationResultPanel.setLayout(cassationResultPanelLayout);
        cassationResultPanelLayout.setHorizontalGroup(
                cassationResultPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cassationResultPanelLayout.createSequentialGroup()
                                .addComponent(cassationResultLabel)
                                .addGap(18, 18, 18)
                                .addComponent(cassationResultChoise, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        cassationResultPanelLayout.setVerticalGroup(
                cassationResultPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cassationResultPanelLayout.createSequentialGroup()
                                .addGroup(cassationResultPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(cassationResultLabel)
                                        .addComponent(cassationResultChoise, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
        );

        cassationAgreedSumPanel.setPreferredSize(new Dimension(0, 25));

        cassationAgreedSumLabel.setText(propertyLoader.getProperty(LABEL_AGREED_SUMM));

        GroupLayout cassationAgreedSumPanelLayout = new GroupLayout(cassationAgreedSumPanel);
        cassationAgreedSumPanel.setLayout(cassationAgreedSumPanelLayout);
        cassationAgreedSumPanelLayout.setHorizontalGroup(
                cassationAgreedSumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cassationAgreedSumPanelLayout.createSequentialGroup()
                                .addComponent(cassationAgreedSumLabel)
                                .addGap(18, 18, 18)
                                .addComponent(cassationAgreedSumInput, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
        );
        cassationAgreedSumPanelLayout.setVerticalGroup(
                cassationAgreedSumPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cassationAgreedSumPanelLayout.createSequentialGroup()
                                .addGroup(cassationAgreedSumPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(cassationAgreedSumLabel)
                                        .addComponent(cassationAgreedSumInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 5, Short.MAX_VALUE))
        );

        GroupLayout cassationYearPanelLayout = new GroupLayout(cassationYearPanel);
        cassationYearPanel.setLayout(cassationYearPanelLayout);
        cassationYearPanelLayout.setHorizontalGroup(
                cassationYearPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cassationYearPanelLayout.createSequentialGroup()
                                .addComponent(cassationYearLabel)
                                .addGap(18, 18, 18)
                                .addComponent(cassationYearChoise))
        );
        cassationYearPanelLayout.setVerticalGroup(
                cassationYearPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cassationYearPanelLayout.createSequentialGroup()
                                .addGroup(cassationYearPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(cassationYearLabel)
                                        .addComponent(cassationYearChoise, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 11, Short.MAX_VALUE))
        );


        inSupremeCourt.setText(propertyLoader.getProperty(IN_SUPREME_COURT));

        GroupLayout cassationPanelLayout = new GroupLayout(cassationPanel);
        cassationPanel.setLayout(cassationPanelLayout);
        cassationPanelLayout.setHorizontalGroup(
                cassationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(cassationResultPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cassationAgreedSumPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cassationYearPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(inSupremeCourt, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        cassationPanelLayout.setVerticalGroup(
                cassationPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(cassationPanelLayout.createSequentialGroup()
                                .addComponent(cassationResultPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cassationAgreedSumPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cassationYearPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inSupremeCourt)
                                .addContainerGap(24, Short.MAX_VALUE))
        );

        saveButton.setText(propertyLoader.getProperty(BUTTON_SAVE));
        saveButton.addActionListener(action -> {
            if (SuitType.isEmpty((SuitType) suitTypeChoise.getSelectedItem())) {
                JOptionPane.showMessageDialog(iAm,
                        propertyLoader.getProperty(SAVE_ERROR_TYPE_EMPTY),
                        propertyLoader.getProperty(SAVE_ERROR_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } else if (!(sumIsValid(initialSumInput))) {
                JOptionPane.showMessageDialog(iAm,
                        propertyLoader.getProperty(SAVE_ERROR_INVALID_INITIAL_SUMM),
                        propertyLoader.getProperty(SAVE_ERROR_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } else if (!(sumIsValid(agreedSumInput))) {
                JOptionPane.showMessageDialog(iAm,
                        propertyLoader.getProperty(SAVE_ERROR_INVALID_AGREED_SUMM),
                        propertyLoader.getProperty(SAVE_ERROR_TITLE),
                        JOptionPane.ERROR_MESSAGE);
            } else {
                if (modifiableSuit != null) {
                    modifiableSuit.setRepresentative(Representative.isEmpty((Representative) representativeChoise.getSelectedItem()) ? null : (Representative) representativeChoise.getSelectedItem());
                    modifiableSuit.setType((SuitType.isEmpty((SuitType) suitTypeChoise.getSelectedItem())) ? null : (SuitType) suitTypeChoise.getSelectedItem());
                    modifiableSuit.setYear((Integer) yearChoise.getSelectedItem());
                    modifiableSuit.setCategory((Category.isEmpty((Category) categoryChoise.getSelectedItem())) ? null : (Category) categoryChoise.getSelectedItem());
                    modifiableSuit.setPlaintiff(plaintiffInput.getText() == null ? "" : plaintiffInput.getText());
                    modifiableSuit.setDefendant((Defendant.isEmpty((Defendant) defendantChoise.getSelectedItem())) ? null : (Defendant) defendantChoise.getSelectedItem());
                    modifiableSuit.setInitialSumm(initialSumInput.getValue() == null ? 0 : ((Number) initialSumInput.getValue()).intValue());
                    modifiableSuit.setAgreedSumm(agreedSumInput.getValue() == null ? 0 : ((Number) agreedSumInput.getValue()).intValue());
                    modifiableSuit.setResult((Result.isEmpty((Result) resultChoise.getSelectedItem())) ? null : (Result) resultChoise.getSelectedItem());
                    if (hasApeal.isSelected()) {
                        if (modifiableSuit.getAppeal() == null) {
                            Appeal appeal = Appeal.getBuilder()
                                    .withResult((Result.isEmpty((Result) appealResultChoise.getSelectedItem())) ? null : (Result) appealResultChoise.getSelectedItem())
                                    .withAgreedSum(appealAgreedSumInput.getValue() == null ? 0 : ((Number) appealAgreedSumInput.getValue()).intValue())
                                    .withYear((Integer)appealYearChoise.getSelectedItem())
                                    .build();
                            databaseManager.create(appeal);
                            modifiableSuit.setAppeal(appeal);
                        } else {
                            modifiableSuit.getAppeal().setResult((Result.isEmpty((Result) appealResultChoise.getSelectedItem())) ? null : (Result) appealResultChoise.getSelectedItem());
                            modifiableSuit.getAppeal().setAgreedSum(appealAgreedSumInput.getValue() == null ? 0 : ((Number) appealAgreedSumInput.getValue()).intValue());
                            databaseManager.update(modifiableSuit.getAppeal());
                        }
                    } else if (modifiableSuit.getAppeal() != null) {
                        databaseManager.delete(modifiableSuit.getAppeal());
                        modifiableSuit.setAppeal(null);
                    }
                    if (hasCassation.isSelected()) {
                        if (modifiableSuit.getCassation() == null) {
                            Cassation cassation = Cassation.getBuilder()
                                    .withResult((Result.isEmpty((Result) cassationResultChoise.getSelectedItem())) ? null : (Result) cassationResultChoise.getSelectedItem())
                                    .withAgreedSum(cassationAgreedSumInput.getValue() == null ? 0 : ((Number) cassationAgreedSumInput.getValue()).intValue())
                                    .withYear((Integer) cassationYearChoise.getSelectedItem())
                                    .withInSupremeCourt(inSupremeCourt.isSelected())
                                    .build();
                            databaseManager.create(cassation);
                            modifiableSuit.setCassation(cassation);
                        } else {
                            modifiableSuit.getCassation().setResult((Result.isEmpty((Result) cassationResultChoise.getSelectedItem())) ? null : (Result) cassationResultChoise.getSelectedItem());
                            modifiableSuit.getCassation().setAgreedSum(cassationAgreedSumInput.getValue() == null ? 0 : ((Number) cassationAgreedSumInput.getValue()).intValue());
                            databaseManager.update(modifiableSuit.getCassation());
                        }
                    } else if (modifiableSuit.getCassation() != null) {
                        databaseManager.delete(modifiableSuit.getCassation());
                        modifiableSuit.setCassation(null);
                    }
                    databaseManager.update(modifiableSuit);
                    tableModel.reDraw();
                } else {
                    Appeal appeal = null;
                    Cassation cassation = null;
                    if (hasApeal.isSelected()) {
                        appeal = Appeal.getBuilder()
                                .withResult((Result.isEmpty((Result) appealResultChoise.getSelectedItem())) ? null : (Result) appealResultChoise.getSelectedItem())
                                .withAgreedSum(appealAgreedSumInput.getValue() == null ? 0 : ((Number) appealAgreedSumInput.getValue()).intValue())
                                .withYear((Integer)appealYearChoise.getSelectedItem())
                                .build();
                        databaseManager.create(appeal);
                    }

                    if (hasCassation.isSelected()) {
                        cassation = Cassation.getBuilder()
                                .withResult((Result.isEmpty((Result) cassationResultChoise.getSelectedItem())) ? null : (Result) cassationResultChoise.getSelectedItem())
                                .withAgreedSum(cassationAgreedSumInput.getValue() == null ? 0 : ((Number) cassationAgreedSumInput.getValue()).intValue())
                                .withYear((Integer) cassationYearChoise.getSelectedItem())
                                .withInSupremeCourt(inSupremeCourt.isSelected())
                                .build();
                        databaseManager.create(cassation);
                    }

                    Suit suit = Suit.getBuilder()
                            .withRepresentative(Representative.isEmpty((Representative) representativeChoise.getSelectedItem()) ? null : (Representative) representativeChoise.getSelectedItem())
                            .withType((SuitType.isEmpty((SuitType) suitTypeChoise.getSelectedItem())) ? null : (SuitType) suitTypeChoise.getSelectedItem())
                            .withYear((Integer) yearChoise.getSelectedItem())
                            .withCategory((Category.isEmpty((Category) categoryChoise.getSelectedItem())) ? null : (Category) categoryChoise.getSelectedItem())
                            .withDefendant((Defendant.isEmpty((Defendant) defendantChoise.getSelectedItem())) ? null : (Defendant) defendantChoise.getSelectedItem())
                            .withPlaintiff(plaintiffInput.getText() == null ? "" : plaintiffInput.getText())
                            .withInitialSumm(initialSumInput.getValue() == null ? 0 : ((Number) initialSumInput.getValue()).intValue())
                            .withAgreedSumm(agreedSumInput.getValue() == null ? 0 : ((Number) agreedSumInput.getValue()).intValue())
                            .withResult((Result.isEmpty((Result) resultChoise.getSelectedItem())) ? null : (Result) resultChoise.getSelectedItem())
                            .withAppeal(appeal)
                            .withCassation(cassation)
                            .build();
                    tableModel.addRow(suit);
                }
                iAm.setVisible(false);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(suitDataLabel)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(suitPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(hasApeal)
                                        .addComponent(hasCassation)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(appealPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(cassationPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE)
                                .addGap(173, 173, 173))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(suitDataLabel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(suitPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(hasApeal)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(appealPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(hasCassation)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cassationPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(saveButton)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
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

    public void showAddRow(Representative representative) {
        modifiableSuit = null;
        this.setTitle(propertyLoader.getProperty(SUIT_ENTER));
        suitTypeChoise.setSelectedIndex(0);
        if (Representative.isEmpty(representative)) {
            representativeChoise.setSelectedIndex(0);
        } else {
            representativeChoise.setSelectedItem(representative);
        }
        yearChoise.setSelectedItem(LocalDateTime.now().getYear());
        categoryChoise.setSelectedIndex(0);
        defendantChoise.setSelectedIndex(0);
        plaintiffInput.setText("");
        initialSumInput.setValue(0);
        agreedSumInput.setValue(0);
        resultChoise.setSelectedIndex(0);
        hasApeal.setSelected(false);
        appealAgreedSumLabel.setEnabled(false);
        appealAgreedSumInput.setEnabled(false);
        appealAgreedSumInput.setValue(0);
        appealResultLabel.setEnabled(false);
        appealResultChoise.setEnabled(false);
        appealResultChoise.setSelectedIndex(0);
        appealYearLabel.setEnabled(false);
        appealYearChoise.setEnabled(false);
        appealYearChoise.setSelectedItem(LocalDateTime.now().getYear());
        hasCassation.setSelected(false);
        cassationAgreedSumLabel.setEnabled(false);
        cassationAgreedSumInput.setEnabled(false);
        cassationAgreedSumInput.setValue(0);
        cassationResultLabel.setEnabled(false);
        cassationResultChoise.setEnabled(false);
        cassationResultChoise.setSelectedIndex(0);
        cassationYearLabel.setEnabled(false);
        cassationYearChoise.setEnabled(false);
        cassationYearChoise.setSelectedItem(LocalDateTime.now().getYear());
        inSupremeCourt.setEnabled(false);
        this.setVisible(true);
    }

    public void showModifyRow(Suit suit) {
        modifiableSuit = suit;
        this.setTitle(propertyLoader.getProperty(SUIT_FROM) + suit.getPlaintiff());
        suitTypeChoise.setSelectedItem(suit.getType());
        representativeChoise.setSelectedItem(suit.getRepresentative());
        yearChoise.setSelectedItem(suit.getYear());
        categoryChoise.setSelectedItem(suit.getCategory());
        defendantChoise.setSelectedItem(suit.getDefendant());
        plaintiffInput.setText(suit.getPlaintiff());
        initialSumInput.setValue(suit.getInitialSumm());
        agreedSumInput.setValue(suit.getAgreedSumm());
        resultChoise.setSelectedItem(suit.getResult());
        setDependantComponents(suit.getType());

        if (suit.getAppeal() == null) {
            hasApeal.setSelected(false);
            appealAgreedSumLabel.setEnabled(false);
            appealAgreedSumInput.setEnabled(false);
            appealAgreedSumInput.setValue(0);
            appealResultLabel.setEnabled(false);
            appealResultChoise.setEnabled(false);
            appealResultChoise.setSelectedIndex(0);
            appealYearLabel.setEnabled(false);
            appealYearChoise.setEnabled(false);
            appealYearChoise.setSelectedItem(LocalDateTime.now().getYear());
        } else {
            hasApeal.setSelected(true);
            appealAgreedSumLabel.setEnabled(true);
            appealAgreedSumInput.setEnabled(true);
            appealAgreedSumInput.setValue(suit.getAppeal().getAgreedSum());
            appealResultLabel.setEnabled(true);
            appealResultChoise.setEnabled(true);
            if (Result.isEmpty(suit.getAppeal().getResult())) {
                appealResultChoise.setSelectedIndex(0);
            } else {
                appealResultChoise.setSelectedItem(suit.getAppeal().getResult());
            }
            appealYearLabel.setEnabled(true);
            appealYearChoise.setEnabled(true);
            appealYearChoise.setSelectedItem(suit.getAppeal().getYear());
        }

        if (suit.getCassation() == null) {
            hasCassation.setSelected(false);
            cassationAgreedSumLabel.setEnabled(false);
            cassationAgreedSumInput.setEnabled(false);
            cassationAgreedSumInput.setValue(0);
            cassationResultLabel.setEnabled(false);
            cassationResultChoise.setEnabled(false);
            cassationResultChoise.setSelectedIndex(0);
            cassationYearLabel.setEnabled(false);
            cassationYearChoise.setEnabled(false);
            cassationYearChoise.setSelectedItem(LocalDateTime.now().getYear());
        } else {
            hasCassation.setSelected(true);
            cassationAgreedSumLabel.setEnabled(true);
            cassationAgreedSumInput.setEnabled(true);
            cassationAgreedSumInput.setValue(suit.getCassation().getAgreedSum());
            cassationResultLabel.setEnabled(true);
            cassationResultChoise.setEnabled(true);
            if (Result.isEmpty(suit.getCassation().getResult())) {
                cassationResultChoise.setSelectedIndex(0);
            } else {
                cassationResultChoise.setSelectedItem(suit.getCassation().getResult());
            }
            cassationYearLabel.setEnabled(true);
            cassationYearChoise.setEnabled(true);
            cassationYearChoise.setSelectedItem(suit.getCassation().getYear());
        }
        this.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AllBeans.class);
        AllBeans.setContext(ctx);
        final RowModifyDialog window = ctx.getBean(RowModifyDialog.class);
        SwingUtilities.invokeLater(() -> {
            window.initialize();
            window.setVisible(true);
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> ctx.close()));

    }

    private static boolean sumIsValid(JFormattedTextField field) {
        try {
            ((Number) field.getValue()).intValue();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private JPanel suitPanel;

    private JPanel suitTypePanel;
    private JLabel suitTypeLabel;
    private JComboBox suitTypeChoise;

    private JPanel representativePanel;
    private JLabel representativeLabel;
    private JComboBox representativeChoise;

    private JPanel yearPanel;
    private JLabel yearLabel;
    private JComboBox yearChoise;

    private JPanel categoryPanel;
    private JLabel categoryLabel;
    private JComboBox categoryChoise;

    private JPanel defendantPanel;
    private JLabel defendantLabel;
    private JComboBox defendantChoise;

    private JCheckBox hasApeal;
    private JCheckBox hasCassation;
    private JCheckBox inSupremeCourt;

    private JPanel plaintiffPanel;
    private JLabel plaintiffLabel;
    private JTextField plaintiffInput;

    private JPanel initialSumPanel;
    private JLabel initialSumLabel;
    private JFormattedTextField initialSumInput;

    private JPanel resultPanel;
    private JLabel resultLabel;
    private JComboBox resultChoise;

    private JPanel agreedSumPanel;
    private JLabel agreedSumLabel;
    private JFormattedTextField agreedSumInput;

    private JPanel appealPanel;
    private JComboBox appealResultChoise;
    private JComboBox appealYearChoise;

    private JComboBox cassationYearChoise;
    private JComboBox cassationResultChoise;
    private JLabel appealResultLabel;
    private JLabel suitDataLabel;
    private JLabel cassationResultLabel;
    private JLabel cassationAgreedSumLabel;
    private JLabel cassationYearLabel;

    private JLabel appealAgreedSumLabel;
    private JLabel appealYearLabel;
    private JPanel appealResultPanel;
    private JPanel appealAgreedSumPanel;
    private JPanel appealYearPanel;
    private JPanel cassationPanel;
    private JPanel cassationResultPanel;
    private JPanel cassationAgreedSumPanel;
    private JPanel cassationYearPanel;

    private JFormattedTextField appealAgreedSumInput;
    private JFormattedTextField cassationAgreedSumInput;

    private JButton saveButton;
}

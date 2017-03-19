package ru.bmstr.pugu.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.bmstr.pugu.db.DatabaseManager;
import ru.bmstr.pugu.domain.Representative;
import ru.bmstr.pugu.domain.Suit;
import ru.bmstr.pugu.dto.AllContent;
import ru.bmstr.pugu.properties.PropertyLoader;
import ru.bmstr.pugu.reports.ReportGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

import static ru.bmstr.pugu.properties.PropertyNames.*;

/**
 * Created by bmstr on 19.11.2016.
 */
@Component
public class MainFrame extends JFrame {

    private static final Logger log = LogManager.getLogger(MainFrame.class);
    private final JFrame window = this;
    private final static int DEFAULT_WIDTH = 800;
    private final static int DEFAULT_HEIGHT = 600;
    JComboBox representativeChoise;
    JTextField searchTextField;

    @Autowired
    private PropertyLoader propertyLoader;

    @Autowired
    private TableData contentTable;

    @Autowired
    private AllContent allContent;

    @Autowired
    private RowModifyDialog rowModifyDialog;

    @Autowired
    private JFileChooser saveAsFileChooser;

    @Autowired
    private ReportGenerator reportGenerator;

    @Autowired
    private DatabaseManager databaseManager;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            log.error("Cannot set look and feel", e);
        }
    }

    public void initialize() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle(propertyLoader.getProperty(FRAME_TITLE));
        addMenuBar();
        adjustSize();
        addMainContentArea(window.getContentPane());

        rowModifyDialog.initialize();

        this.setVisible(true);
    }

    private void adjustSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setLocation(
                (screenSize.width - this.getWidth()) / 2,
                (screenSize.height - this.getHeight()) / 2);
    }

    private JMenuBar addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu(propertyLoader.getProperty(MENU_FILE));
        menuBar.add(menu);

        // Выход
        JMenuItem menuItem = new JMenuItem(propertyLoader.getProperty(MENU_EXIT));
        menuItem.addActionListener(event -> {
            window.dispatchEvent(
                    new WindowEvent(window, WindowEvent.WINDOW_CLOSING)
            );
        });
        menu.add(menuItem);

        menu = new JMenu(propertyLoader.getProperty(MENU_REPORT));
        menuItem = new JMenuItem(propertyLoader.getProperty(MENU_REPORT_GENERATE));
        menuItem.addActionListener(event -> {
            SwingUtilities.invokeLater(() -> {
                window.setEnabled(false);
                reportGenerator.generateReport();
                window.setEnabled(true);
                JOptionPane.showMessageDialog(window,
                        propertyLoader.getProperty(REPORT_INFO_MESSAGE).replace("filename", propertyLoader.getProperty(REPORT_FILENAME)),
                        propertyLoader.getProperty(REPORT_INFO_TITLE),
                        JOptionPane.INFORMATION_MESSAGE);
            });
        });
        menu.add(menuItem);
        menuBar.add(menu);


        this.setJMenuBar(menuBar);

        return menuBar;
    }

    private JTabbedPane addMainContentArea(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab(propertyLoader.getProperty(REPORT_TITLE), createSuitReportPanel());
//        tabbedPane.addTab("Заявительские", new JPanel());
        pane.add(tabbedPane);
        return tabbedPane;
    }

    private JPanel createSuitReportPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder());
        panel.setLayout(new BorderLayout());
        panel.add(createControlPanel(), BorderLayout.NORTH);
        panel.add(createTablePanel());
        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        representativeChoise = new JComboBox(databaseManager.retriveAllWithEmpty(Representative.class).toArray());
        representativeChoise.addActionListener(event -> {
            applyFilter();
        });
        panel.add(representativeChoise);
        JButton add = new JButton("+");
        add.addActionListener(actionEvent -> {
            SwingUtilities.invokeLater(() -> rowModifyDialog.showAddRow());
        });
        JButton substract = new JButton("-");
        substract.addActionListener(action -> {
            if (contentTable.noRowsSelected()) {
                JOptionPane.showMessageDialog(window, propertyLoader.getProperty(CHOOSE_AT_LEAST_ONE));
            } else {
                Object[] choices = {propertyLoader.getProperty(BUTTON_DELETE), propertyLoader.getProperty(BUTTON_CANCEL)};
                Object defaultChoice = choices[1];
                int n = JOptionPane.showOptionDialog(
                        window,
                        propertyLoader.getProperty(DELETE_QUESTION),
                        propertyLoader.getProperty(BUTTON_DELETE),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        choices,
                        defaultChoice);
                if (n == JOptionPane.YES_OPTION) {
                    contentTable.deleteSelected();
                }
            }
        });
        panel.add(add);
        panel.add(substract);
        panel.add(new JLabel("                               "));
        searchTextField = new JTextField();
        searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (propertyLoader.getProperty(SEARCH_TEXT_FIELD).equals(searchTextField.getText())) {
                    searchTextField.setText("");
                    searchTextField.setForeground(Color.BLACK);
                    searchTextField.setFont(new Font("searchText", Font.PLAIN, 11));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchTextField.getText().isEmpty()) {
                    searchTextField.setText(propertyLoader.getProperty(SEARCH_TEXT_FIELD));
                    searchTextField.setForeground(Color.GRAY);
                    searchTextField.setFont(new Font("stub", Font.ITALIC, 11));
                }
            }
        });
        searchTextField.setText(propertyLoader.getProperty(SEARCH_TEXT_FIELD));
        searchTextField.setForeground(Color.GRAY);
        searchTextField.setFont(new Font("stub", Font.ITALIC, 11));

        searchTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                applyFilter();
            }
        });

        searchTextField.setPreferredSize(new Dimension(200, 23));
        panel.add(searchTextField);

        return panel;
    }

    private void applyFilter() {
        if (searchTextField.getText().isEmpty() && StringUtils.isEmpty(((Representative)representativeChoise.getSelectedItem()).getSurname())) {
            contentTable.unFilter();
        } else {
            String searchText = searchTextField.getText();
            if (propertyLoader.getProperty(SEARCH_TEXT_FIELD).equals(searchText)) {
                searchText = "";
            }
            contentTable.filter(((Representative)representativeChoise.getSelectedItem()), searchText);
        }
    }

    private JScrollPane createTablePanel() {
        JScrollPane scrollPane = new JScrollPane(contentTable);
        return scrollPane;
    }

}

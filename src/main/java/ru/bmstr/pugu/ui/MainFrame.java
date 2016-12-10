package ru.bmstr.pugu.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bmstr.pugu.dto.AllContent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.File;

/**
 * Created by bmstr on 19.11.2016.
 */
@Component
public class MainFrame extends JFrame {

    private static final Logger log = LogManager.getLogger(MainFrame.class);
    private final JFrame window = this;
    private final static int DEFAULT_WIDTH = 800;
    private final static int DEFAULT_HEIGHT = 600;

    @Autowired
    private TableData contentTable;

    @Autowired
    private AllContent allContent;

    @Autowired
    private RowModifyDialog rowModifyDialog;

    @Autowired
    private JFileChooser saveAsFileChooser;

    static {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            log.error("Cannot set look and feel", e);
        }
    }

    public void initialize () {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Отчёты ПУГУ");
        addMenuBar();
        adjustSize();
        addMainContentArea(window.getContentPane());
        this.setVisible(true);
    }

    private void adjustSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        this.setLocation(
                (screenSize.width - this.getWidth())/2,
                (screenSize.height - this.getHeight())/2);
    }

    private JMenuBar addMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Файл");
        menuBar.add(menu);

        // Сохранить данные
        JMenuItem menuItem = new JMenuItem("Сохранить данные");
        menuItem.addActionListener( action -> {
            SwingUtilities.invokeLater( () -> {
                int saveChoise = saveAsFileChooser.showSaveDialog(window);
                if (saveChoise == JFileChooser.APPROVE_OPTION) {
                    File file = saveAsFileChooser.getSelectedFile();
                    if (!file.getName().endsWith(".json")) {
                        file = new File(file.getAbsolutePath()+".json");
                    }
                    allContent.store(file);
                }
            });
        });
        menu.add(menuItem);

        // Сохранить данные
        menuItem = new JMenuItem("Восстановить данные");
        menuItem.addActionListener( action -> {
            SwingUtilities.invokeLater( () -> {
                int openChoise = saveAsFileChooser.showOpenDialog(window);
                if (openChoise == JFileChooser.APPROVE_OPTION) {
                    String[] choiseButtons = {"Перезаписать",
                            "Добавить"};
                    int choise = JOptionPane.showOptionDialog(window,
                            "Перезаписать  или добавить к имеющимся данным?",
                            "Перезаписать",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,     //do not use a custom Icon
                            choiseButtons,  //the titles of buttons
                            choiseButtons[0]);
                    File file = saveAsFileChooser.getSelectedFile();
                    if (choise == 0) {
                        allContent.restoreAndOverwrite(file);
                    } else {
                        allContent.restoreAndAdd(file);
                    }
                    contentTable.reDraw();
                }
            });
        });
        menu.add(menuItem);

        menu.addSeparator();
        // Выход
        menuItem = new JMenuItem("Выход");
        menuItem.addActionListener( event -> {
            window.dispatchEvent(
                    new WindowEvent(window, WindowEvent.WINDOW_CLOSING)
            );
        });
        menu.add(menuItem);

        this.setJMenuBar(menuBar);

        return menuBar;
    }

    private JTabbedPane addMainContentArea(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Судебно-исковая работа", createSuitReportPanel());
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
        JButton add = new JButton("+");
        add.addActionListener( actionEvent -> {
            SwingUtilities.invokeLater(() -> rowModifyDialog.showAddRow());
        });
        JButton substract = new JButton("-");
        substract.addActionListener( action -> {
            if (contentTable.noRowsSelected()) {
                JOptionPane.showMessageDialog(window, "Выберете хотя бы один иск для удаления");
            } else {
                Object[] choices = {"Удалить", "Отмена"};
                Object defaultChoice = choices[1];
                int n = JOptionPane.showOptionDialog(
                        window,
                        "Вы действительно хотите удалить выбранные иски?",
                        "Удалить",
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
        return panel;
    }

    private JScrollPane createTablePanel() {
        JScrollPane scrollPane = new JScrollPane(contentTable);
        return scrollPane;
    }

}

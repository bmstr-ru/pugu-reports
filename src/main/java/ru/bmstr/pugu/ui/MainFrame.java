package ru.bmstr.pugu.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bmstr.pugu.domain.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

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

        // Сохранить как
        JMenuItem menuItem = new JMenuItem("Сохранить как...");
        menu.add(menuItem);

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
        JButton substract = new JButton("-");
        substract.addActionListener( action -> {

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

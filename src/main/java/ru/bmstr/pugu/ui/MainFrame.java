package ru.bmstr.pugu.ui;

import org.springframework.stereotype.Component;

import javax.swing.*;

/**
 * Created by bmstr on 19.11.2016.
 */
@Component
public class MainFrame extends JFrame {
    MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(200,200);
    }
}

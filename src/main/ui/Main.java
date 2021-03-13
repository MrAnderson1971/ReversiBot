package ui;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        init();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame ex = new Main();
            ex.setVisible(true);
        });
    }

    public void init() {
        add(new Othello());

        setResizable(false);
        pack();

        setTitle("ReversiBot");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

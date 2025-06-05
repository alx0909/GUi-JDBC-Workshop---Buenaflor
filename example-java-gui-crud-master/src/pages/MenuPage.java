package pages;

import javax.swing.*;
import java.awt.*;

public class MenuPage extends JFrame {

    public MenuPage() {
        setTitle("Select Page");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        getContentPane().setBackground(Color.decode("#EDF2FB"));

        JButton facultyButton = new JButton("Faculty");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(facultyButton, gbc);
        facultyButton.setBackground(Color.decode("#395A7F"));
        facultyButton.setForeground(Color.WHITE);

        JButton studentButton = new JButton("Student");
        gbc.gridy = 1;
        add(studentButton, gbc);
        studentButton.setBackground(Color.decode("#395A7F"));
        studentButton.setForeground(Color.WHITE);

        facultyButton.addActionListener(e -> {
            new FacultyPage();
            dispose();
        });

        studentButton.addActionListener(e -> {
            new StudentPage();
            dispose();
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }
}

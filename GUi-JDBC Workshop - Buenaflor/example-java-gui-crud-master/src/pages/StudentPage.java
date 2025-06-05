package pages;

import dal.students.StudentDAO;
import models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class StudentPage extends JFrame {
    private final StudentDAO studentDao = new StudentDAO();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField studentNumberField;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField programField;
    private final JSpinner levelSpinner;
    private JButton addButton, updateButton, deleteButton, clearButton;

    public StudentPage() {
        setTitle("Student CRUD");
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.decode("#EDF2FB"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        getContentPane().setBackground(Color.decode("#EDF2FB"));

        studentNumberField = new JTextField(15);
        studentNumberField.setBackground(Color.decode("#BAE0F3"));

        firstNameField = new JTextField(15);
        firstNameField.setBackground(Color.decode("#BAE0F3"));

        lastNameField = new JTextField(15);
        lastNameField.setBackground(Color.decode("#BAE0F3"));

        programField = new JTextField(15);
        programField.setBackground(Color.decode("#BAE0F3"));

        levelSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
        levelSpinner.getEditor().getComponent(0).setBackground(Color.decode("#BAE0F3"));

        int row = 0;

        addFormRow(formPanel, gbc, row++, "Student Number:", studentNumberField);
        addFormRow(formPanel, gbc, row++, "First Name:", firstNameField);
        addFormRow(formPanel, gbc, row++, "Last Name:", lastNameField);
        addFormRow(formPanel, gbc, row++, "Program:", programField);
        addFormRow(formPanel, gbc, row++, "Year:", levelSpinner);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.decode("#EDF2FB"));

        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        clearButton = new JButton("Clear");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        addButton.setBackground(Color.decode("#395A7F"));
        addButton.setForeground(Color.WHITE);


        updateButton.setBackground(Color.decode("#395A7F"));
        updateButton.setForeground(Color.WHITE);

        deleteButton.setBackground(Color.decode("#395A7F"));
        deleteButton.setForeground(Color.WHITE);

        clearButton.setBackground(Color.decode("#395A7F"));
        clearButton.setForeground(Color.WHITE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.decode("#EDF2FB"));
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Student Number", "First Name", "Last Name", "Program", "Year"}, 0
        );
        table = new JTable(tableModel);
        table.getTableHeader().setBackground(Color.decode("#B373BD"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setBackground(Color.decode("#E6E6FA"));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Actions
        loadStudents();

        addButton.addActionListener(e -> addStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
        clearButton.addActionListener(e -> clearFields());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                studentNumberField.setText(tableModel.getValueAt(row, 1).toString());
                firstNameField.setText(tableModel.getValueAt(row, 2).toString());
                lastNameField.setText(tableModel.getValueAt(row, 3).toString());
                programField.setText(tableModel.getValueAt(row, 4).toString());
                levelSpinner.setValue((int) tableModel.getValueAt(row, 5));
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labels, JComponent field) {
        JLabel label = new JLabel(labels);
        label.setForeground(Color.decode("#395A7F"));
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        List<Student> students = studentDao.getAllStudents();
        for (Student student : students) {
            tableModel.addRow(new Object[]{
                    student.getId(),
                    student.getStudentNumber(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getProgram(),
                    student.getLevel()
            });
        }
    }

    private void addStudent() {
        String studentNumber = studentNumberField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String program = programField.getText();
        int level = (int) levelSpinner.getValue();

        if (
                !studentNumber.isEmpty() &&
                        !firstName.isEmpty() &&
                        !lastName.isEmpty() &&
                        !program.isEmpty()
        ) {
            studentDao.addStudent(new Student(0, studentNumber, firstName, lastName, program, level));
            loadStudents();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }
    }

    private void updateStudent() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            String studentNumber = studentNumberField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String program = programField.getText();
            int level = (int) levelSpinner.getValue();

            if (
                    !studentNumber.isEmpty() &&
                            !firstName.isEmpty() &&
                            !lastName.isEmpty() &&
                            !program.isEmpty()
            ) {
                studentDao.updateStudent(new Student(id, studentNumber, firstName, lastName, program, level));
                loadStudents();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        }
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            studentDao.deleteStudent(id);
            loadStudents();
            clearFields();
        }
    }

    private void clearFields() {
        studentNumberField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        programField.setText("");
        levelSpinner.setValue(1);
    }
}

package pages;

import dal.faculty.FacultyDAO;
import models.Faculty;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class FacultyPage extends JFrame {
    private final FacultyDAO facultyDao = new FacultyDAO();
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final JTextField facultyNumberField;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField subjectsField;
    private final JTextField yearStartedField;
    private final JSpinner unitsSpinner;
    private JButton addButton, updateButton, deleteButton, clearButton;

    public FacultyPage() {
        setTitle("Faculty CRUD");
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

        facultyNumberField = new JTextField(15);
        facultyNumberField.setBackground(Color.decode("#BAE0F3"));

        firstNameField = new JTextField(15);
        firstNameField.setBackground(Color.decode("#BAE0F3"));

        lastNameField = new JTextField(15);
        lastNameField.setBackground(Color.decode("#BAE0F3"));

        subjectsField = new JTextField(15);
        subjectsField.setBackground(Color.decode("#BAE0F3"));

        unitsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 30, 1));
        unitsSpinner.getEditor().getComponent(0).setBackground(Color.decode("#BAE0F3"));

        yearStartedField = new JTextField(15);
        yearStartedField.setBackground(Color.decode("#BAE0F3"));

        int row = 0;

        addFormRow(formPanel, gbc, row++, "Faculty Number:", facultyNumberField);
        addFormRow(formPanel, gbc, row++, "First Name:", firstNameField);
        addFormRow(formPanel, gbc, row++, "Last Name:", lastNameField);
        addFormRow(formPanel, gbc, row++, "Subjects:", subjectsField);
        addFormRow(formPanel, gbc, row++, "Total Units:", unitsSpinner);
        addFormRow(formPanel, gbc, row++, "Year Started:", yearStartedField);

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
                new String[]{"ID", "Faculty Number", "First Name", "Last Name", "Subjects", "Units", "Year Started"}, 0
        );
        table = new JTable(tableModel);
        table.getTableHeader().setBackground(Color.decode("#B373BD"));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setBackground(Color.decode("#E6E6FA"));

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Actions
        loadFaculty();

        addButton.addActionListener(e -> addFaculty());
        updateButton.addActionListener(e -> updateFaculty());
        deleteButton.addActionListener(e -> deleteFaculty());
        clearButton.addActionListener(e -> clearFields());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                facultyNumberField.setText(tableModel.getValueAt(row, 1).toString());
                firstNameField.setText(tableModel.getValueAt(row, 2).toString());
                lastNameField.setText(tableModel.getValueAt(row, 3).toString());
                subjectsField.setText(tableModel.getValueAt(row, 4).toString());
                unitsSpinner.setValue((int) tableModel.getValueAt(row, 5));
                yearStartedField.setText(tableModel.getValueAt(row, 6).toString());
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

    private void loadFaculty() {
        tableModel.setRowCount(0);
        List<Faculty> facultyList = FacultyDAO.getAllFaculty();
        for (Faculty faculty : facultyList) {
            tableModel.addRow(new Object[]{
                    faculty.getId(),
                    faculty.getFacultyNumber(),
                    faculty.getFirstName(),
                    faculty.getLastName(),
                    faculty.getSubjects(),
                    faculty.getUnits(),
                    faculty.getYearStarted()
            });
        }
    }

    private void addFaculty() {
        String facultyNumber = facultyNumberField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String subjects = subjectsField.getText();
        int units = (int) unitsSpinner.getValue();
        String yearStarted = yearStartedField.getText();

        if (
                !facultyNumber.isEmpty() &&
                        !firstName.isEmpty() &&
                        !lastName.isEmpty() &&
                        !subjects.isEmpty()
        ) {
            facultyDao.addFaculty(new Faculty(0, facultyNumber, firstName, lastName, subjects, units, yearStarted));
            loadFaculty();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
        }
    }

    private void updateFaculty() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            String facultyNumber = facultyNumberField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String subjects = subjectsField.getText();
            int units = (int) unitsSpinner.getValue();
            String yearStarted = yearStartedField.getText();

            if (
                    !facultyNumber.isEmpty() &&
                            !firstName.isEmpty() &&
                            !lastName.isEmpty() &&
                            !subjects.isEmpty()
            ) {
                facultyDao.updateFaculty(new Faculty(id, facultyNumber, firstName, lastName, subjects, units, yearStarted));
                loadFaculty();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            }
        }
    }

    private void deleteFaculty() {
        int row = table.getSelectedRow();
        if (row != -1) {
            int id = (int) tableModel.getValueAt(row, 0);
            facultyDao.deleteFaculty(id);
            loadFaculty();
            clearFields();
        }
    }

    private void clearFields() {
        facultyNumberField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        subjectsField.setText("");
        unitsSpinner.setValue(1);
        yearStartedField.setText("");
    }
}

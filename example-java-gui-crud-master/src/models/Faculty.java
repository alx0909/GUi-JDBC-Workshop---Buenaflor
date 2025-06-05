package models;

public class Faculty {
    private int id;
    private String facultyNumber;
    private String firstName;
    private String lastName;
    private String subjects;
    private int units;
    private String yearStarted;

    public Faculty() {}

    public Faculty(
            int id,
            String facultyNumber,
            String firstName,
            String lastName,
            String subjects,
            int units,
            String yearStarted
    ) {
        this.id = id;
        this.facultyNumber = facultyNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subjects = subjects;
        this.units = units;
        this.yearStarted = yearStarted;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(String FacultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        this.units = units;
    }

    public String getYearStarted() {
        return yearStarted;
    }

    public void setYearStarted(String yearStarted) {
        this.yearStarted = yearStarted;
    }
}

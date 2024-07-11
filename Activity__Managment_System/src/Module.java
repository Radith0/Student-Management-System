public class Module {
    private int marks; // Marks obtained in the module
    private String grade; // Grade achieved in the module

    //Constructor to initialize a Module object with marks and grade.
    public Module(int marks, String grade) {
        this.marks = marks;
        this.grade = grade;
    }

    // Getter method to retrieve the marks obtained in the module.
    public int getMarks() {
        return marks;
    }

    //Setter method to set the marks obtained in the module.
    public void setMarks(int marks) {
        this.marks = marks;
    }

    //Getter method to retrieve the grade achieved in the module.
    public String getGrade() {
        return grade;
    }

    //Setter method to set the grade achieved in the module.
    public void setGrade(String grade) {
        this.grade = grade;
    }
}

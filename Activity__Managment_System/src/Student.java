public class Student {
    private String id; // Student ID
    private String name; // Student name
    private Module[] modules = new Module[3]; // Array of modules for the student

    //Constructor to initialize a Student object with ID, name, and modules.
    public Student(String id, String name, Module[] modules) {
        this.id = id;
        this.name = name;
        this.modules = modules;
    }

    //Retrieves the student's ID.
    public String getId() {
        return id;
    }

    //Sets the student's ID.
    public void setId(String id) {
        this.id = id;
    }

    //Retrieves the student's name.
    public String getName() {
        return name;
    }

    //Sets the student's name.
    public void setName(String name) {
        this.name = name;
    }

    //Retrieves the array of modules for the student.
    public Module[] getModules() {
        return modules;
    }

    //Sets the array of modules for the student.
    public void setModules(Module[] modules) {
        this.modules = modules;
    }
}

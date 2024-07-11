import java.io.*;
import java.util.*;

public class Main {
    // Array to store student objects
    static Student[] students = new Student[100];
    // Counter to keep track of the number of students
    static int studentCount = 0;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice = -1; // Initialize choice with an invalid value

        // Load students from file at the start
        loadStudentsFromFile();

        // Display menu options in a loop until the user decides to exit
        do {
            System.out.println("\nStudent Activity Management Menu:");
            System.out.println("1. Check available seats");
            System.out.println("2. Register student");
            System.out.println("3. Delete student");
            System.out.println("4. Find student");
            System.out.println("5. Store student details to file");
            System.out.println("6. Load student details from file");
            System.out.println("7. View student list (sorted)");
            System.out.println("8. Manage student results");
            System.out.println("9. Generate Reports");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = input.nextInt();
                input.nextLine(); // Consume the newline character

                // Handle user choice by calling appropriate methods
                switch (choice) {
                    case 1:
                        checkAvailableSeats();
                        break;
                    case 2:
                        registerStudent();
                        break;
                    case 3:
                        deleteStudent();
                        break;
                    case 4:
                        findStudent();
                        break;
                    case 5:
                        storeStudentsToFile();
                        break;
                    case 6:
                        loadStudentsFromFile();
                        break;
                    case 7:
                        viewSortedStudentList();
                        break;
                    case 8:
                        manageStudentResults(input);
                        break;
                    case 9:
                        generateReports(input);
                        break;
                    case 0:
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 9.");
                input.next(); // Consume the invalid input
            }
        } while (choice != 0);
    }

    // Method to check available seats
    public static void checkAvailableSeats() {
        System.out.println("Available seats: " + (students.length - studentCount));
    }

    // Method to register a new student
    public static void registerStudent() {
        Scanner input = new Scanner(System.in);
        if (studentCount == students.length) {
            System.out.println("No more seats available.");
            return;
        }

        String id;
        do {
            System.out.print("Enter student ID (8 characters starting with 'w'): ");
            id = input.nextLine();

            if (isDuplicateId(id)) {
                System.out.println("ID already exists. Please enter a unique ID.");
            } else if (!id.matches("w\\d{7}")) {
                System.out.println("Invalid ID format. Please enter a valid ID starting with 'w' followed by 7 digits.");
            }
        } while (isDuplicateId(id) || !id.matches("w\\d{7}"));

        System.out.print("Enter student name: ");
        String name = input.nextLine();

        students[studentCount] = new Student(id, name, new Module[3]);
        studentCount++;
        System.out.println("Student registered successfully!");
    }

    // Method to check if a student ID already exists
    public static boolean isDuplicateId(String id) {
        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    // Method to delete a student by ID
    public static void deleteStudent() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter student ID to delete: ");
        String idToDelete = input.nextLine();

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(idToDelete)) {
                // Shift elements to remove the student
                for (int j = i; j < studentCount - 1; j++) {
                    students[j] = students[j + 1];
                }
                students[studentCount - 1] = null;
                studentCount--;
                System.out.println("Student deleted successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Method to find a student by ID
    public static void findStudent() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter student ID to find: ");
        String idToFind = input.nextLine();

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(idToFind)) {
                System.out.println("Student found:");
                System.out.println("ID: " + students[i].getId());
                System.out.println("Name: " + students[i].getName());
                // Display module marks and grades if available
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Method to store student details to a file
    public static void storeStudentsToFile() {
        try (FileWriter fw = new FileWriter("students.txt");
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (int i = 0; i < studentCount; i++) {
                Student student = students[i];
                bw.write(student.getId() + "," + student.getName());
                for (Module module : student.getModules()) {
                    if (module != null) {
                        bw.write("," + module.getMarks() + "," + module.getGrade());
                    }
                }
                bw.newLine();
            }
            System.out.println("Student details stored successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while storing student details.");
        }
    }

    // Method to load student details from a file
    public static void loadStudentsFromFile() {
        try (FileReader fr = new FileReader("students.txt");
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            studentCount = 0;

            System.out.println("Loading student details from file...");

            // Read each line from the file
            while ((line = br.readLine()) != null) {
                // Split the line into parts based on commas
                String[] parts = line.split(",");
                String id = parts[0];
                String name = parts[1];
                Module[] modules = new Module[3];

                // Loop to extract module marks and grades
                for (int i = 2, j = 0; i < parts.length; i += 2, j++) {
                    int marks = Integer.parseInt(parts[i]);
                    String grade = parts[i + 1];
                    modules[j] = new Module(marks, grade);
                }

                // Create a new Student object and add it to the array
                students[studentCount] = new Student(id, name, modules);
                studentCount++;

                // Print student details to the console
                System.out.printf("Loaded: ID=%s, Name=%s, M1=%d(%s), M2=%d(%s), M3=%d(%s)\n",
                        id, name,
                        modules[0] != null ? modules[0].getMarks() : 0, modules[0] != null ? modules[0].getGrade() : "N/A",
                        modules[1] != null ? modules[1].getMarks() : 0, modules[1] != null ? modules[1].getGrade() : "N/A",
                        modules[2] != null ? modules[2].getMarks() : 0, modules[2] != null ? modules[2].getGrade() : "N/A");
            }
            if (studentCount == 0) {
                System.out.println("No student details found.");
            } else {
                System.out.println("All student details loaded successfully!");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File 'students.txt' not found. No student details loaded.");
        } catch (IOException e) {
            System.out.println("An error occurred while loading student details: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("An error occurred while parsing student marks: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("The file format is incorrect or there are missing details: " + e.getMessage());
        }
    }

    // Method to view the sorted student list by name
    public static void viewSortedStudentList() {
        if (studentCount == 0) {
            System.out.println("Student details not found.");
            return;
        }

        // Sort students by name
        Arrays.sort(students, 0, studentCount, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                if (s1 == null || s2 == null) {
                    return 0; // Handle null values
                }
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });

        // Display sorted student list
        System.out.println("\nSorted Student List:");
        System.out.printf("%-10s %-15s %-10s %-10s %-10s\n", "ID", "Name", "Module 1", "Module 2", "Module 3");
        for (int i = 0; i < studentCount; i++) {
            Student student = students[i];
            System.out.printf("%-10s %-15s %-10s %-10s %-10s\n", student.getId(), student.getName(),
                    student.getModules()[0] != null ? student.getModules()[0].getMarks() + " (" + student.getModules()[0].getGrade() + ")" : "N/A",
                    student.getModules()[1] != null ? student.getModules()[1].getMarks() + " (" + student.getModules()[1].getGrade() + ")" : "N/A",
                    student.getModules()[2] != null ? student.getModules()[2].getMarks() + " (" + student.getModules()[2].getGrade() + ")" : "N/A");
        }
    }

    // Method to manage student results
    public static void manageStudentResults(Scanner input) {
        System.out.print("Enter student ID to manage results: ");
        String id = input.nextLine();

        for (int i = 0; i < studentCount; i++) {
            if (students[i].getId().equals(id)) {
                // Manage results for the found student
                System.out.println("Managing results for student: " + students[i].getName());
                for (int j = 0; j < 3; j++) {
                    System.out.print("Enter marks for Module " + (j + 1) + ": ");
                    int marks = input.nextInt();
                    input.nextLine(); // Consume the newline character

                    String grade = calculateGrade(marks);
                    students[i].getModules()[j] = new Module(marks, grade);
                }
                System.out.println("Results updated successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Method to generate reports
    public static void generateReports(Scanner input) {
        System.out.println("Select report type:");
        System.out.println("1. Students passed in all modules");
        System.out.println("2. Students failed in one or more modules");
        System.out.println("3. Students failed in all modules");
        System.out.println("4. Students with the highest marks in a module");
        System.out.println("5. Students with the lowest marks in a module");

        int reportChoice = input.nextInt();
        input.nextLine(); // Consume the newline character

        switch (reportChoice) {
            case 1:
                reportStudentsPassedAllModules();
                break;
            case 2:
                reportStudentsFailedOneOrMoreModules();
                break;
            case 3:
                reportStudentsFailedAllModules();
                break;
            case 4:
                reportHighestMarksInModule(input);
                break;
            case 5:
                reportLowestMarksInModule(input);
                break;
            default:
                System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    // Method to report students who passed all modules
    public static void reportStudentsPassedAllModules() {
        System.out.println("Students who passed all modules:");
        for (int i = 0; i < studentCount; i++) {
            boolean passedAll = true;
            for (Module module : students[i].getModules()) {
                if (module == null || !module.getGrade().equals("Pass")) {
                    passedAll = false;
                    break;
                }
            }
            if (passedAll) {
                System.out.println(students[i].getName());
            }
        }
    }

    // Method to report students who failed one or more modules
    public static void reportStudentsFailedOneOrMoreModules() {
        System.out.println("Students who failed one or more modules:");
        for (int i = 0; i < studentCount; i++) {
            for (Module module : students[i].getModules()) {
                if (module == null || !module.getGrade().equals("Pass")) {
                    System.out.println(students[i].getName());
                    break;
                }
            }
        }
    }

    // Method to report students who failed all modules
    public static void reportStudentsFailedAllModules() {
        System.out.println("Students who failed all modules:");
        for (int i = 0; i < studentCount; i++) {
            boolean failedAll = true;
            for (Module module : students[i].getModules()) {
                if (module != null && module.getGrade().equals("Pass")) {
                    failedAll = false;
                    break;
                }
            }
            if (failedAll) {
                System.out.println(students[i].getName());
            }
        }
    }

    // Method to report students with the highest marks in a module
    public static void reportHighestMarksInModule(Scanner input) {
        System.out.print("Enter module number (1-3) to find the highest marks: ");
        int moduleNumber = input.nextInt();
        input.nextLine(); // Consume the newline character

        if (moduleNumber < 1 || moduleNumber > 3) {
            System.out.println("Invalid module number. Returning to main menu.");
            return;
        }

        int highestMarks = -1;
        String highestStudent = "N/A";
        for (int i = 0; i < studentCount; i++) {
            Module module = students[i].getModules()[moduleNumber - 1];
            if (module != null && module.getMarks() > highestMarks) {
                highestMarks = module.getMarks();
                highestStudent = students[i].getName();
            }
        }

        System.out.printf("Student with the highest marks in Module %d: %s (%d marks)\n",
                moduleNumber, highestStudent, highestMarks);
    }

    // Method to report students with the lowest marks in a module
    public static void reportLowestMarksInModule(Scanner input) {
        System.out.print("Enter module number (1-3) to find the lowest marks: ");
        int moduleNumber = input.nextInt();
        input.nextLine(); // Consume the newline character

        if (moduleNumber < 1 || moduleNumber > 3) {
            System.out.println("Invalid module number. Returning to main menu.");
            return;
        }

        int lowestMarks = Integer.MAX_VALUE;
        String lowestStudent = "N/A";
        for (int i = 0; i < studentCount; i++) {
            Module module = students[i].getModules()[moduleNumber - 1];
            if (module != null && module.getMarks() < lowestMarks) {
                lowestMarks = module.getMarks();
                lowestStudent = students[i].getName();
            }
        }

        System.out.printf("Student with the lowest marks in Module %d: %s (%d marks)\n",
                moduleNumber, lowestStudent, lowestMarks);
    }

    // Method to calculate grade based on marks
    public static String calculateGrade(int marks) {
        if (marks >= 50) {
            return "Pass";
        } else {
            return "Fail";
        }
    }
}
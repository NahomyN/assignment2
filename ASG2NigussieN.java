import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Nahom Nigussie
 * Project 2
 * 2024-03-06
 * This document contains all classes required for Project 2.
 */

/* Student Class */
class Student {
    private String name;
    private int creditHours;
    private double qualityPoints;
    private static double gpaThreshold;

    public Student(String name, int creditHours, double qualityPoints) {
        this.name = name;
        this.creditHours = creditHours;
        this.qualityPoints = qualityPoints;
    }

    public double gpa() {
        return qualityPoints / creditHours;
    }

    public boolean eligibleForHonorSociety() {
        return gpa() >= gpaThreshold;
    }

    public static double getGpaThreshold() {
        return gpaThreshold;
    }

    public static void setGpaThreshold(double threshold) {
        gpaThreshold = threshold;
    }

    @Override
    public String toString() {
        return name + " has a GPA of " + gpa();
    }
}

/* Undergraduate Class */
class Undergraduate extends Student {
    private String year;

    public Undergraduate(String name, int creditHours, double qualityPoints, String year) {
        super(name, creditHours, qualityPoints);
        this.year = year;
    }

    @Override
    public boolean eligibleForHonorSociety() {
        boolean isUpperclassman = "junior".equalsIgnoreCase(year) || "senior".equalsIgnoreCase(year);
        return isUpperclassman && super.eligibleForHonorSociety();
    }

    @Override
    public String toString() {
        return super.toString() + " and is a " + year;
    }
}

/* Graduate Class */
class Graduate extends Student {
    private String degreeSought;

    public Graduate(String name, int creditHours, double qualityPoints, String degreeSought) {
        super(name, creditHours, qualityPoints);
        this.degreeSought = degreeSought;
    }

    @Override
    public boolean eligibleForHonorSociety() {
        return "Masters".equalsIgnoreCase(degreeSought) && super.eligibleForHonorSociety();
    }

    @Override
    public String toString() {
        return super.toString() + " and is seeking a " + degreeSought + " degree";
    }
}

/* Project2 Class */
public class ASG2NigussieN {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();
        double totalGpa = 0.0;
        int studentCount = 0;

        try {
            File file = new File("C:\\Users\\yirna\\eclipse-workspace\\Project 2\\src\\students.txt");
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ", 4); // Split into 4 parts: Name, CreditHours, QualityPoints, AdditionalInfo

                String name = parts[0].trim(); // This will include the last name and first name separated by a comma
                int creditHours = Integer.parseInt(parts[1].trim());
                double qualityPoints = Double.parseDouble(parts[2].trim());
                String additionalInfo = parts[3].trim();

                Student student;
                if ("Freshman".equalsIgnoreCase(additionalInfo) || 
                    "Sophomore".equalsIgnoreCase(additionalInfo) ||
                    "Junior".equalsIgnoreCase(additionalInfo) ||
                    "Senior".equalsIgnoreCase(additionalInfo)) {
                    student = new Undergraduate(name, creditHours, qualityPoints, additionalInfo);
                } else {
                    student = new Graduate(name, creditHours, qualityPoints, additionalInfo);
                }
                
                students.add(student);
                totalGpa += student.gpa();
                studentCount++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: The file 'students.txt' does not exist.");
            System.exit(1);
        }
 
        double averageGpa = studentCount > 0 ? totalGpa / studentCount : 0;
        Student.setGpaThreshold((averageGpa + 4.0) / 2);
        System.out.println("The GPA threshold for honor society is " + Student.getGpaThreshold());

        System.out.println("Students eligible for the honor society:");
        for (Student student : students) {
            if (student.eligibleForHonorSociety()) {
                System.out.println(student);
            }
        }
    }
}

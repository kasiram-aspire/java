package java_training;
import java.io.*;
import java.util.Scanner;

class user1 {
    String name;
    String role;  

    public user1(String name, String role) {
        this.name = name;
        this.role = role;
    }

   
    public void sendMessage(String message) {
        System.out.println(name + ": " + message);
    }
}


class Exam {
    String studentName;
    String answers;
    String grade;

    public Exam(String studentName, String answers) {
        this.studentName = studentName;
        this.answers = answers;
        this.grade = "Not Graded";
    }

   
    public void gradeExam(String grade) {
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }
}


class GradingThread extends Thread {
    Exam exam;
    String grade;

    public GradingThread(Exam exam, String grade) {
        this.exam = exam;
        this.grade = grade;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000); 
            exam.gradeExam(grade);
            System.out.println("Teacher has graded the exam!");
        } catch (InterruptedException e) {
            System.err.println("Grading process interrupted.");
        }
    }
}

class ResultNotificationThread extends Thread {
	user1 student;
    Exam exam;

    public ResultNotificationThread(user1 student, Exam exam) {
        this.student = student;
        this.exam = exam;
    }

    @Override
    public void run() {
        try {
            
            Thread.sleep(2500); 
            student.sendMessage("Your exam grade: " + exam.getGrade());
        } catch (InterruptedException e) {
            System.err.println("Notification interrupted.");
        }
    }
}


public class SchoolExamSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        user1 student = new user1("kasiram", "student");
        user1 teacher = new user1("karthik", "teacher");
        System.out.println("Student Exam Submission:");
        System.out.print(student.name + ", enter your exam answers: ");
        String studentAnswers = scanner.nextLine();
        Exam exam = new Exam(student.name, studentAnswers);
        System.out.println(student.name + " has submitted the exam.");
        System.out.print("Teacher " + teacher.name + ", enter the grade for the exam  ");
        String grade = scanner.nextLine();
        GradingThread gradingThread = new GradingThread(exam, grade);
        gradingThread.start();
        ResultNotificationThread resultThread = new ResultNotificationThread(student, exam);
        resultThread.start();
        try {
            gradingThread.join();
            resultThread.join();
        } catch (InterruptedException e) {
            System.out.println("Error waiting for threads to complete.");
        }

        System.out.println("Exam grading and notification process completed.");
    }
}

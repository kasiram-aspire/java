package java_training;

import java.util.ArrayList;
import java.util.Scanner;
public class BankApplication {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Integer> Account_no = new ArrayList<>();
        ArrayList<String> Name = new ArrayList<>();
        ArrayList<Integer> Amount = new ArrayList<>();
        System.out.println("----------------------------------------------");

        while (true) {
            System.out.println("Press 1 to create an account");
            System.out.println("Press 2 to Deposit money");
            System.out.println("Press 3 to Withdraw money");
            System.out.println("Press 4 to Generate Account statement");
            System.out.println("Press 5 to Exit the application");
            System.out.println("----------------------------------------------");
            System.out.println(" ");

            int pressed_number = sc.nextInt();
            sc.nextLine();  

            switch (pressed_number) {
                case 1:
                    
                        int rand_acc_num = (int) (Math.random() * 400000) + 500000;
                        Account_no.add(rand_acc_num);  
                        System.out.println("Enter the name:");
                        String name = sc.nextLine();
                        Name.add(name);  
                        Amount.add(0);  
                    
                    System.out.println(" accounts created successfully. Account no:"+rand_acc_num);
                    break;

                case 2:
                    System.out.println("-------------Deposit the amount---------------------");
                    System.out.println("Enter Your Account number:");
                    int acc = sc.nextInt();
                    int index = -1;

                 
                    for (int i = 0; i < Account_no.size(); i++) {
                        if (Account_no.get(i) == acc) {
                            index = i;
                            break;
                        }
                    }

                    if (index != -1) {
                        System.out.println("Enter the Amount you want to deposit:");
                        int amnt = sc.nextInt();
                        Amount.set(index, Amount.get(index) +amnt);  
                        System.out.println("Amount deposited successfully.");
                    } 
                    else {
                        System.out.println("Account number not found.");
                    }
                    System.out.println("----------------------------------------------");
                    break;

                case 3:
                    System.out.println("-------------Withdraw the amount---------------------");
                    System.out.println("Enter Your Account number:");
                    int accn = sc.nextInt();
                    int index1 = -1;

                    
                    for (int i = 0; i < Account_no.size(); i++) {
                        if (Account_no.get(i) == accn) {
                            index1 = i;
                            break;
                        }
                    }

                    if (index1 != -1) {
                        System.out.println("Enter the Amount you want to withdraw:");
                        int amnt = sc.nextInt();
                        if (Amount.get(index1) >= amnt) {
                            Amount.set(index1, Amount.get(index1) - amnt);  
                            System.out.println("The amount has been withdrawn successfully.");
                            System.out.println("The balance amount is: " + Amount.get(index1));
                        } 
                        else {
                            System.out.println("Insufficient balance");
                        }
                    } 
                    else {
                        System.out.println("Account number not found.");
                    }
                    System.out.println("----------------------------------------------");
                    break;

                case 4:
                    System.out.println("-------------Account Statement--------------------");
                    for (int i = 0; i < Account_no.size(); i++) {
                        System.out.println("Account No: " + Account_no.get(i));
                        System.out.println("Name: " + Name.get(i));
                        System.out.println("Amount: " + Amount.get(i));
                        System.out.println("----------------------------------------------");
                    }
                    break;

                case 5:
                    System.out.println("Exiting the application...");
                    sc.close();
                    return;

                default:
                    System.out.println("You pressed an invalid number.");
            }
        }
    }
}

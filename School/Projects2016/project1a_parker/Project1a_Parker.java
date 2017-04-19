package project1a_parker;

import java.util.Scanner;

public class Project1a_Parker {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int correct = 0;
        int attempt = 0;
        while(true){  //loop entire quiz
            System.out.print("Please choose one of the following options for your math quiz:\n"
                    + "1.  Addition with numbers 1-10\n"
                    + "2.  Addition with numbers 1-100\n"
                    + "3.  Subtraction with numbers 1-10\n"
                    + "4.  Subtraction with numbers 1-100\n"
                    + "5.  Multiplication with numbers 1-10\n"
                    + "6.  Exit the program\n\n");
            int choice = input.nextInt();
            while(choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6){  //incorrect input, re-enter
                System.out.println("Valid choices are 1-6;  please re-enter");
                choice = input.nextInt();
            }
            if(choice == 6){  //exit program
                double percent = 100* ((double)correct) / attempt;  
                if(attempt == 0)
                    System.out.println("You answered 0 problems. Goodbye!");  //prevent NaN percent due to 0 attempts
                else
                    System.out.printf("\nYou got %d problems correct out of %d problems attempted. That is %.2f percent correct. Goodbye!\n",correct,attempt,percent);
                break;
            }
            for(int i = 1; i <= 5; i++){  //loop for all of the quizzes
                int rand1, rand2, sum = 0;
                switch(choice){
                    case 1: //addition 10
                            rand1 = (int)(1+ Math.random()*10);
                            rand2 = (int)(1+ Math.random()*10);
                            sum = rand1 + rand2;
                            System.out.printf("Enter the answer to the following problem:  %d + %d = ",rand1,rand2);
                            break;
                    case 2:  //addition 100
                            rand1 = (int)(1+ Math.random()*100);
                            rand2 = (int)(1+ Math.random()*100);
                            sum = rand1 + rand2;
                            System.out.printf("Enter the answer to the following problem:  %d + %d = ",rand1,rand2);
                            break;
                    case 3:  //subtraction 10
                            rand1 = (int)(1+ Math.random()*10);
                            rand2 = (int)(1+ Math.random()*10);
                            sum = rand1 - rand2;
                            System.out.printf("Enter the answer to the following problem:  %d - %d = ",rand1,rand2);
                            break;
                    case 4:  //subtraction 100
                            rand1 = (int)(1+ Math.random()*100);
                            rand2 = (int)(1+ Math.random()*100);
                            sum = rand1 - rand2;
                            System.out.printf("Enter the answer to the following problem:  %d - %d = ",rand1,rand2);
                            break;
                    case 5:  //multiplication
                            rand1 = (int)(1+ Math.random()*10);
                            rand2 = (int)(1+ Math.random()*10);
                            sum = rand1 * rand2;
                            System.out.printf("Enter the answer to the following problem:  %d * %d = ",rand1,rand2);
                            break;
                }
                int answer = input.nextInt();
                if(answer == sum){
                    System.out.println("That is the correct answer!\n");
                    correct++;
                }
                else
                    System.out.println("Sorry, that is incorrect. The correct answer is "+sum+"\n");
            }
            attempt += 5;
        }
    }
}
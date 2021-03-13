import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // створюємо ApplicationContext, з якого будемо витягувати наші beans (об'єкти)
        ApplicationContext context = new ClassPathXmlApplicationContext("gladiators.xml");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Who is your gladiator?");
        System.out.println("1.Jason, 2.Raoh, 3.Arnold, 4.Ein.");

        // вибір гладіатора з перевіркою
        int choice;
        Gladiator playerGladiator;
        try {
            choice = scanner.nextInt();
            while (choice < 1 || choice > 4){
                System.out.println("Enter number 1-4.");
                choice = scanner.nextInt();
            }
        } catch (InputMismatchException inputMismatchException){
            System.out.println("Error during input.");
            return;
        }
        playerGladiator = createGladiator(choice, context);

        // створення ворожого гладіатора
        Gladiator enemyGladiator;
        byte foeNumber = generateRandomForGladiator();
        while (foeNumber == choice){
            foeNumber = generateRandomForGladiator();
        }
        enemyGladiator = createGladiator(foeNumber, context);

        // битва
        while (enemyGladiator.getHealth() > 0 && playerGladiator.getHealth() > 0){
            playerGladiator.strike(enemyGladiator);
            System.out.println("Foe has " + enemyGladiator.getHealth() + " health remaining." + "\n");
            TimeUnit.SECONDS.sleep(1);

            if (enemyGladiator.getHealth() > 0) {
                enemyGladiator.strike(playerGladiator);
                System.out.println("Your gladiator's health: " + playerGladiator.getHealth() + "\n");
                TimeUnit.SECONDS.sleep(1);
            }
        }

        // декларуємо переможця
        if (enemyGladiator.getHealth() > 0)
            System.out.println("Your gladiator have lost.");
        else
            System.out.println("You and " + playerGladiator.getName() + " have won honour and GLORY!");

    }

    // створюємо гладіатора
    public static Gladiator createGladiator(int choice, ApplicationContext context){
        Gladiator gladiator;
        switch (choice){
            case 1 -> gladiator = (Gladiator) context.getBean("jason"); // IoC
            case 2 -> gladiator = (Gladiator) context.getBean("raoh");
            case 3 -> gladiator = (Gladiator) context.getBean("arnold");
            default -> gladiator = (Gladiator) context.getBean("ein");
        }

        return gladiator;
    }

    // генеруємо випадковий номер для вибору ворога-гладіатора
    public static byte generateRandomForGladiator(){
        return (byte) (((Math.random()*100)%4) + 1);
    }
}

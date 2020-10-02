package com.basepackage;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        // system objects
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();

        // game variables
        //dungeon type game
        String[] enemies = {"Skeleton", "Zombie", "Warrior", "Assassin"};
        int maxEnemyHealth = 75; // can be tweaked
        int enemyAttackDamage = 25; // can be tweaked

        // player variables, can be tweaked
        int health = 100;
        int attackDamage = 50;
        int numHealthPotion = 3;
        int healthPotionHealAmount = 30;
        int healthPotionDropChance = 50; // percentage

        boolean running = true;
        System.out.println("Welcome to the Dungeon!");

        // game starts
        GAME: // label
        while(running){
            System.out.println("---------------------------------------");
            int enemyHealth = rand.nextInt(maxEnemyHealth);
            String enemy = enemies[rand.nextInt(enemies.length)];
            System.out.println("\t# " + enemy + " has appeared! #\n");

            while(enemyHealth > 0){
                System.out.println("\tYour HP: " + health);
                System.out.println("\t" + enemy + "'s HP: " + enemyHealth);
                System.out.println("\n\t What would you like to do");
                System.out.println("\t1. Attack!");
                System.out.println("\t2. Drink Health Potion");
                System.out.println("\t3. Flee!");

                String input = sc.nextLine();
                if(input.equals("1")){
                    // attack
                    int damageDealt = rand.nextInt(attackDamage);
                    int damageTaken = rand.nextInt(enemyAttackDamage);

                    enemyHealth -= damageDealt;
                    health -= damageTaken;

                    System.out.println("\t> You strike the " + enemy + " for " + damageDealt + " damage");
                    System.out.println("\t> You receive " + damageTaken + " from the" + enemy);
                    if(health < 1){
                        System.out.println("\t> You have taken too much damage!");
                        break;
                    }
                } else if(input.equals("2")){
                    // i need healing
                    if(numHealthPotion > 0){
                        health += healthPotionHealAmount;
                        numHealthPotion--;
                        System.out.println("\t> You drink health Potion for " + healthPotionHealAmount + " HP!"
                                + "\n\t> You now have " + health + " HP!"
                                + "\n\t> You now have " + numHealthPotion + " potions left!");

                    } else{
                        System.out.println("\t> You don't have health potions left. Defeat enemies for chance to drop!");
                    }
                } else if (input.equals("3")){
                    // live and let live
                    System.out.println("\t> You ran away from " + enemy + "!");
                    continue GAME;
                } else{
                    // invalid input
                    System.out.println("\t Invalid Command!");
                }
            }
            // combat finished
            if(health < 1){
                System.out.println("YOU DIED!");
                break;
            }
            System.out.println("---------------------------------------");
            System.out.println(" # " + enemy + " was defeated! #");
            System.out.println(" # You have " + health + " HP left! # ");

            if(rand.nextInt(100) < healthPotionDropChance){
                numHealthPotion++;
                System.out.println(" # The enemy dropped a health potion! #");
                System.out.println(" # You have " + numHealthPotion + " health potion(s)! #");
            }
            System.out.println("---------------------------------------");

            System.out.println("What would you like to do now?");
            System.out.println("1. Continue Fighting?");
            System.out.println("2. Exit dungeon!");

            String input = sc.nextLine();

            while(!input.equals("1") && !input.equals("2")){
                System.out.println("Invalid Command!");
                input = sc.nextLine();
            }
            if(input.equals("1")){
                System.out.println("You dive deeper in the dungeon!");
            } else if (input.equals("2")){
                System.out.println("You exit the dungeon!");
            }
        }

        System.out.println("########################");
        System.out.println("Thanks for playing!");
        System.out.println("########################");

    }
}

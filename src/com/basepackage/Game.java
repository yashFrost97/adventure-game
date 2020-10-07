package com.basepackage;


import java.util.Random;
import java.util.Scanner;

public class Game {
	private Scanner sc = new Scanner(System.in);
	private Random rand = new Random();

	// game variables
	//dungeon type game
	private String[] enemies = {"Skeleton", "Zombie", "Warrior", "Assassin"};
	private int maxEnemyHealth = 75; // can be tweaked
	private int enemyAttackDamage = 25; // can be tweaked

	// player variables, can be tweaked
	private int health = 100;
	private int attackDamage = 50;
	private int numHealthPotion = 3;
	private int healthPotionHealAmount = 30;
	private int healthPotionDropChance = 50; // percentage


	boolean gameRunning = true;

	public void gameStart(){
		System.out.println("-----------------------------------");
		System.out.println("Welcome to TexAd!");

		while(gameRunning){
			enemyAppeared();
			gameRunning = endGame();
		}
	}

	private void enemyAppeared(){
		int enemyHealth = rand.nextInt(maxEnemyHealth);
		String enemy = enemies[rand.nextInt(enemies.length)];
		System.out.println("\t# " + enemy + " has appeared! #\n");
		enemyCombat(enemyHealth, enemy);
		afterCombat(enemy);
	}

	private void enemyCombat(int enemyHealth, String enemy){
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
				continue;
			} else{
				// invalid input
				System.out.println("\t Invalid Command!");
			}
		}

	}

	private void afterCombat(String enemy){
		// combat finished
		if(health < 1){
			System.out.println("YOU DIED!");
		}
		System.out.println("---------------------------------------");
		System.out.println(" # " + enemy + " was defeated! #");
		System.out.println(" # You have " + health + " HP left! # ");

		if(rand.nextInt(100) < healthPotionDropChance){
			numHealthPotion++;
			System.out.println(" # The enemy dropped a health potion! #");
			System.out.println(" # You have " + numHealthPotion + " health potion(s)! #");
		}

	}

	private boolean endGame(){
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
			return true;
		} else if (input.equals("2")){
			System.out.println("You exit the dungeon!");
			System.out.println("##########################");
			System.out.println("Thanks for Playing!");
			System.out.println("##########################");
		}
		return false;
	}

}


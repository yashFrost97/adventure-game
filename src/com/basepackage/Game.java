package com.basepackage;


import java.util.Random;
import java.util.Scanner;

/* TODO
* 1. add a linear rudimentary storyline
* 2. add a flee chance. Cannot skip the battle always
* 3. add a final boss
* 4. add critical chance to deal damage and take damage
* 5. add some type of skills/magicka instead of the usual attack
* 6. Health Potion should not exceed max player health, now 100hp
*/

public class Game {
	private Scanner sc = new Scanner(System.in);
	private Random rand = new Random();

	// game variables
	//dungeon type game
	// Enemy Variables
	private String[] enemies = {"Skeleton Watchguard", "Soulguard Warlock", "DeathKnight", "Dark Mage"};
	private int maxEnemyHealth = 75; // can be tweaked
	private int enemyAttackDamage = 25; // can be tweaked

	// Boss Variables
	private String[] finalBosses = {"Devourer of Souls"};
	private int bossHealth = 100;
	private int bossAttackDamage = 40;

	// player variables, can be tweaked
	private int health = 100;
	private int attackDamage = 50;
	private int numHealthPotion = 3;
	private int healthPotionHealAmount = 30;
	private int healthPotionDropChance = 50; // percentage


	boolean gameRunning = true;

	public void gameStart(){
		System.out.println("-----------------------------------");
		System.out.println("Welcome to TexAd Dungeon! ");
		System.out.println("-----------------------------------");
		// game start loop
		System.out.println("> So, You are the brave warrior that wishes to clear this Cave of Souls and get rid of The Scourge\n" +
				"> You better be prepared Warrior! For this is just the beginning in your quest for greatness!\n");
		System.out.println("> Press any key to enter!");
		String dummy = sc.nextLine();
		// enter the dungeon
		System.out.println("> You enter the dungeon ");
		System.out.println("> You hear the screams of lost souls confined to suffer for eternity here by The Scourge ");
		System.out.println("> Press any key to continue!");
		dummy = sc.nextLine();
		while(gameRunning){
			System.out.println("> The walls are lit with flames. Just like medieval castles had!");
			System.out.println("> * You encounter an enemy *");
			// First enemy
			enemyAppeared();

			// Second Enemy
			System.out.println("> You receive your spoils of war!");
			System.out.println("> You move on to the metal ramp you see in front of you!\n" +
					"> It suddenly gets dark. And very quiet!\n" +
					"> You cast a small fire to show you the way.\n" +
					"> You continue on with your small flame to guide you!" +
					"> You see an outline of an ominous figure\n" +
					"> It lunges at shouting, weapon drawn with a bloodcurdling scream!\n");
			enemyAppeared();

			// Third Enemy
			System.out.println("> You defeated it!\n" +
					"> You might just be the Chosen One after all!\n" +
					"> You press on!\n" +
					"> The metal ramp you took brings you to a cave!\n" +
					"> You enter with stealth and dash with caution. \n" +
					"> The cave is warm and the darkness works to your advantage.\n" +
					"> You stop and examine the walls of cave. The walls have witnessed lots of Dark Magic. You move ahead!\n" +
					"> You come into a chamber. You feel a breeze of wind. *That's not good*\n");
			enemyAppeared();
			// Fourth Enemy
			System.out.println("> You inspect the creep's body.\n" +
					"> *Whooosh* You hear the sound as you dodge a projectile just in time");
			enemyAppeared();

			System.out.println("> You check the chamber for any more surprises. It's clear\n" +
					"> You inspect both the bodies. You find a map and a weirdly glowing stone!\n" +
					"> You pocket the stone for later use.\n" +
					"> You follow the map!\n" +
					"> The map leads you deeper into the cave.\n" +
					"> You press on!\n" +
					"> You enter a chamber, larger then the previous one. It looks like a laboratory.\n" +
					"> You see shelves lined with books, tables engraved with transmutation circles, multiple cupboards.\n" +
					"> In the centre, there's a platform. There's blood all over it. The blood and flesh of souls trapped" +
					"and aching for release from torment!\n" +
					"> While inspecting, you hear some shuffling at the Transmutation Table. It's the 'Devourer of Souls'\n" +
					"> \" You Dare Enter my lab, puny mortal! I SHALL DEVOUR YOU WHOLE!!\"");
			System.out.println("> Boss Encounter Begins! Good Luck!");

			int bossOutcome = bossCombat(); // 0 or 1 / death or victory!
			if(bossOutcome == 1){
				// victory
				System.out.println("> The Devourer of Souls let's out an anguished scream one last time as his body disintegrates into dust\n" +
						"> Such beings that experiment with souls are placed neither in Hell nor in Heaven.\n" +
						"> They simply stop existing.");
				System.out.println("> The stone in your pocket starts glowing and gravitating towards a receptacle at the corner of the chamber.\n" +
						"> You move towards the corner and offer the stone. You realized it's a Portal Stone.\n" +
						"> It creates a portal to Shadow's Watch! You step into the portal\n" +
						"> Some villagers notice you stepping out of the Portal. Realizing that their days of torment and fear are over.\n" +
						"> They cheer you on, their Savior, their Hero, their Hope!");
				break; // end game
			} else if(bossOutcome == 0){
				System.out.println("> The Devourer of Souls casts a spell and binds you to the ground!\n" +
						"> He gets closer and slowly starts sucking your soul out. You scream in agony, struggling to escape," +
						"alas you can't for the spell is too strong, you're too weak from the wounds.\n" +
						"> The Devourer gains a soul and continues on his experimentation, leaving your carcass there to be fed to chimeras.");
				System.out.println("> YOU DIED!");
			}

		}
		System.out.println("##########################");
		System.out.println("Thanks for Playing!");
		System.out.println("##########################");
	}

	private void enemyAppeared(){
		// this function spawns an enemy creep
		int enemyHealth = rand.nextInt(maxEnemyHealth);
		String enemy = enemies[rand.nextInt(enemies.length)];
		System.out.println("\t# " + enemy + " has appeared! #\n");
		int combat = enemyCombat(enemyHealth, enemy);
		switch(combat){
			case 0:
				System.out.println("> You succumb to your wounds!");
				System.out.println("> Your soul is claimed by The Scourge!");
				return;
			case 1:
				// our player fought with the creep
				afterCombat(enemy, false);
				break;
			case 3:
				// our player fled from the battle with enemy creep!
				afterCombat(enemy, true);
				break;
		}

		// enemy defeated
	}

	private int enemyCombat(int enemyHealth, String enemy){
		// this function engages combat with the eemy creep
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
					return 0; // player death
				}
			} else if(input.equals("2")){
				// i need healing
				drinkPotion();
			} else if (input.equals("3")){
				// live and let live
				System.out.println("\t> You ran away from " + enemy + "!");
				return 3;
			} else{
				// invalid input
				System.out.println("\t Invalid Command!");
			}
		}
		return 1; // all goes well. Player defeats the creep
	}

	private void afterCombat(String enemy, boolean flee){
		// combat finished
		// this function deals with item drops
		if(health < 1){
			System.out.println("YOU DIED!");
		}
		System.out.println("---------------------------------------");
		System.out.println(" # " + enemy + " was defeated! #");
		System.out.println(" # You have " + health + " HP left! # ");
		if(!flee){
			if(rand.nextInt(100) < healthPotionDropChance){
				numHealthPotion++;
				System.out.println(" # The enemy dropped a health potion! #");
				System.out.println(" # You have " + numHealthPotion + " health potion(s)! #");
			}
		}
	}

	private int bossCombat(){
		String finalBoss = finalBosses[0]; // can add other bosses here later on
		int finalBossHealth = bossHealth;
		while(finalBossHealth > 0){
			System.out.println("\tYour HP: " + health);
			System.out.println("\t" + finalBoss + "'s HP: " + finalBossHealth);
			System.out.println("\n\t What would you like to do");
			System.out.println("\t1. Attack!");
			System.out.println("\t2. Drink Health Potion");

			String input = sc.nextLine();
			if(input.equals("1")){
				// attack
				int damageDealt = rand.nextInt(attackDamage);
				int damageTaken = rand.nextInt(bossAttackDamage);

				finalBossHealth -= damageDealt;
				health -= damageTaken;

				System.out.println("\t> You strike the " + finalBoss + " for " + damageDealt + " damage");
				System.out.println("\t> You receive " + damageTaken + " from the" + finalBoss);
				if(health < 1){
					System.out.println("\t> You have taken too much damage!");
					return 0; // player death
				}
			} else if(input.equals("2")){
				// i need healing
				drinkPotion();
			} else{
				// invalid input
				System.out.println("\t Invalid Command!");
			}
		}
		return 1;
	}

	private void drinkPotion(){
		if(numHealthPotion > 0){
			health += healthPotionHealAmount;
			if(health > 100)
				health = 100; // health cannot exceed maximum
			numHealthPotion--;
			System.out.println("\t> You drink health Potion for " + healthPotionHealAmount + " HP!"
					+ "\n\t> You now have " + health + " HP!"
					+ "\n\t> You now have " + numHealthPotion + " potions left!");

		} else{
			System.out.println("\t> You don't have health potions left. Defeat enemies for chance to drop!");
		}

	}

	private boolean endGame(){
		// this function gives the player option to end the game
		// might not need this though, let's see
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


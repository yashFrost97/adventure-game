import random

ENEMIES = ["Skeleton Watchguard", "Soulguard Warlock", "DeathKnight", "Dark Mage"]
FINAL_BOSS = "Devourer of Souls"

STORY_SEGMENTS = [
    "The walls are lit with flames, just like medieval castles had. You encounter an enemy!",
    "You receive your spoils of war. You move on to a metal ramp ahead. It suddenly gets dark and very quiet. You cast a small fire to light your way. An ominous figure lunges at you with a bloodcurdling scream!",
    "You defeated it! You might just be the Chosen One. You press on into a cave, warm and dark. You enter a chamber and feel a strange breeze of wind. That's not good.",
    "You inspect the creep's body. *Whoosh* — you dodge a projectile just in time!",
]

BOSS_INTRO = (
    "You check the chamber. Clear. You find a map and a glowing stone — you pocket it.\n"
    "Following the map deeper into the cave, you enter a large chamber that looks like a laboratory.\n"
    "Shelves lined with books, transmutation circles carved into tables, blood on a central platform.\n"
    "You hear shuffling. It's the Devourer of Souls.\n"
    "\"You dare enter my lab, puny mortal?! I SHALL DEVOUR YOU WHOLE!\""
)

VICTORY_TEXT = (
    "The Devourer of Souls lets out an anguished scream as his body disintegrates into dust.\n"
    "Such beings are placed neither in Hell nor Heaven — they simply stop existing.\n"
    "The stone in your pocket glows and gravitates toward a receptacle in the corner.\n"
    "You offer it. A portal opens to Shadow's Watch. You step through.\n"
    "Villagers cheer as they realize their days of torment are over. You are their Hero."
)

DEATH_TEXT = (
    "The Devourer casts a spell and binds you to the ground.\n"
    "He slowly starts sucking your soul out. You scream in agony, struggling to escape — but you can't.\n"
    "You are too weak. Your soul is claimed. YOUR CARCASS IS LEFT FOR THE CHIMERAS.\n"
    "YOU DIED."
)


class Game:
    def __init__(self):
        # player stats
        self.health = 100
        self.max_health = 100
        self.attack_damage = 50
        self.num_potions = 3
        self.potion_heal = 30

        # enemy stats
        self.max_enemy_health = 75
        self.enemy_attack_damage = 25
        self.potion_drop_chance = 50

        # boss stats
        self.boss_health = 100
        self.boss_max_health = 100
        self.boss_attack_damage = 40

        # progression: tracks which of the 4 enemy encounters we're on (0-3)
        self.encounter = 0
        # phases: "story" -> "enemy" -> "boss_intro" -> "boss" -> "game_over" / "victory"
        self.phase = "story"

        self.current_enemy = None
        self.current_enemy_health = 0
        self.messages = []  # messages to display to the player

    def start(self):
        self.messages = [
            "Welcome to TexAd Dungeon!",
            "You are the brave warrior tasked with clearing the Cave of Souls and defeating The Scourge.",
            "You enter the dungeon. The screams of lost souls echo around you.",
        ]
        self._spawn_next_encounter()
        return self._state()

    def action(self, choice):
        """
        Process a player action and return the new game state.
        choice: "1" = attack, "2" = potion, "3" = flee (enemies only)
        """
        self.messages = []

        if self.phase == "enemy":
            return self._handle_enemy_action(choice)
        elif self.phase == "boss":
            return self._handle_boss_action(choice)

        return self._state()

    # ------------------------------------------------------------------
    # Internal helpers
    # ------------------------------------------------------------------

    def _spawn_next_encounter(self):
        if self.encounter < 4:
            self.current_enemy = random.choice(ENEMIES)
            self.current_enemy_health = random.randint(20, self.max_enemy_health)
            self.messages.append(STORY_SEGMENTS[self.encounter])
            self.messages.append(f"{self.current_enemy} has appeared!")
            self.phase = "enemy"
        else:
            self.messages.append(BOSS_INTRO)
            self.phase = "boss"
            self.boss_health = self.boss_max_health

    def _handle_enemy_action(self, choice):
        enemy = self.current_enemy

        if choice == "1":  # attack
            dealt = random.randint(1, self.attack_damage)
            taken = random.randint(1, self.enemy_attack_damage)
            self.current_enemy_health -= dealt
            self.health -= taken
            self.messages.append(f"You strike {enemy} for {dealt} damage.")
            self.messages.append(f"{enemy} hits you for {taken} damage.")

            if self.health <= 0:
                self.health = 0
                self.phase = "game_over"
                self.messages.append("You have taken too much damage. You succumb to your wounds.")
                self.messages.append("YOUR SOUL IS CLAIMED BY THE SCOURGE. YOU DIED.")
            elif self.current_enemy_health <= 0:
                self._after_enemy_defeated(fled=False)

        elif choice == "2":  # potion
            self._drink_potion()

        elif choice == "3":  # flee
            fled = random.random() < 0.4  # 40% chance to flee successfully
            if fled:
                self.messages.append(f"You successfully fled from {enemy}!")
                self._after_enemy_defeated(fled=True)
            else:
                taken = random.randint(1, self.enemy_attack_damage)
                self.health -= taken
                self.messages.append(f"You failed to flee! {enemy} hits you for {taken} damage as you retreat.")
                if self.health <= 0:
                    self.health = 0
                    self.phase = "game_over"
                    self.messages.append("You have taken too much damage. YOU DIED.")

        return self._state()

    def _after_enemy_defeated(self, fled):
        enemy = self.current_enemy
        if not fled:
            self.messages.append(f"{enemy} was defeated! You have {self.health} HP left.")
            if random.randint(0, 99) < self.potion_drop_chance:
                self.num_potions += 1
                self.messages.append(f"{enemy} dropped a health potion! You now have {self.num_potions}.")
        self.encounter += 1
        self._spawn_next_encounter()

    def _handle_boss_action(self, choice):
        boss = FINAL_BOSS

        if choice == "1":  # attack
            dealt = random.randint(1, self.attack_damage)
            taken = random.randint(1, self.boss_attack_damage)
            self.boss_health -= dealt
            self.health -= taken
            self.messages.append(f"You strike {boss} for {dealt} damage.")
            self.messages.append(f"{boss} hits you for {taken} damage.")

            if self.health <= 0:
                self.health = 0
                self.phase = "game_over"
                self.messages.append(DEATH_TEXT)
            elif self.boss_health <= 0:
                self.boss_health = 0
                self.phase = "victory"
                self.messages.append(VICTORY_TEXT)

        elif choice == "2":  # potion
            self._drink_potion()

        return self._state()

    def _drink_potion(self):
        if self.num_potions > 0:
            self.health = min(self.health + self.potion_heal, self.max_health)
            self.num_potions -= 1
            self.messages.append(f"You drink a potion and heal to {self.health} HP. {self.num_potions} potion(s) left.")
        else:
            self.messages.append("You have no potions left. Defeat enemies for a chance to get one.")

    def to_dict(self):
        """Serialize game state to a plain dict so Flask can store it in the session cookie."""
        return {
            "health": self.health,
            "max_health": self.max_health,
            "attack_damage": self.attack_damage,
            "num_potions": self.num_potions,
            "potion_heal": self.potion_heal,
            "max_enemy_health": self.max_enemy_health,
            "enemy_attack_damage": self.enemy_attack_damage,
            "potion_drop_chance": self.potion_drop_chance,
            "boss_health": self.boss_health,
            "boss_max_health": self.boss_max_health,
            "boss_attack_damage": self.boss_attack_damage,
            "encounter": self.encounter,
            "phase": self.phase,
            "current_enemy": self.current_enemy,
            "current_enemy_health": self.current_enemy_health,
        }

    @classmethod
    def from_dict(cls, data):
        """Rebuild a Game instance from a session-stored dict."""
        g = cls()
        for key, value in data.items():
            setattr(g, key, value)
        g.messages = []
        return g

    def _state(self):
        """Returns the full game state as a dict — this is what Flask sends to the browser."""
        return {
            "phase": self.phase,
            "messages": self.messages,
            "player": {
                "health": self.health,
                "max_health": self.max_health,
                "potions": self.num_potions,
            },
            "enemy": {
                "name": self.current_enemy if self.phase == "enemy" else (FINAL_BOSS if self.phase == "boss" else None),
                "health": self.current_enemy_health if self.phase == "enemy" else (self.boss_health if self.phase == "boss" else 0),
                "max_health": self.max_enemy_health if self.phase == "enemy" else (self.boss_max_health if self.phase == "boss" else 0),
            },
        }

# Battleship Game Collection

A collection of Java-based naval combat and strategy games featuring GUI implementations using Java Swing.

## Games Included

### 1. Naval Battleship - Player vs Enemy
A grid-based naval combat game where you command a battleship trying to survive enemy missile attacks on a 20x20 ocean battlefield.

**Features:**
- Console-based and GUI versions available
- Strategic movement across the naval grid
- Tactical manual positioning or evasive auto-maneuvers
- Damage system with direct hits and near-miss explosions
- Real-time battlefield visualization

**Storyline:**
You are the captain of a lone battleship under attack by an enemy destroyer. Navigate the 20x20 ocean grid to evade 15 incoming missiles. Use your tactical skills to position your ship away from danger zones!

**How to Play:**
- **Manual Mode:** Click grid coordinates to navigate your battleship to safety
- **Auto-Evasion:** Use automatic evasive maneuvers to dodge incoming fire
- **Direct Hit:** Missile strikes your position - Critical damage! (-3 hull integrity)
- **Near Miss:** Missile explodes adjacent to your ship - Splash damage! (-2 hull integrity)
- **Mission:** Survive all 15 enemy missile salvos to achieve victory

**Game Mechanics:**
- Starting Hull Integrity: 10 points
- Enemy Arsenal: 15 missiles
- Battlefield: 20×20 naval grid
- Win Condition: Outlast the enemy's ammunition
- Loss Condition: Hull integrity reaches zero

**Files:**
- `PlayerAndEnemyMain.java` - Command-line naval combat
- `GameGUI.java` - Basic radar display GUI
- `ModernGameGUI.java` - Enhanced tactical display
- `Player.java` - Battleship class with navigation
- `Enemy.java` - Enemy destroyer with missile systems

### 2. Hopscotch Training Simulator
A recreational minigame for crew morale - a digital recreation of the classic deck game.

**Features:**
- Traditional 10-square hopscotch court
- Stone throwing mechanics
- Progressive difficulty through 10 rounds
- Crew morale score tracking

**How to Play:**
1. Throw your stone on the target square
2. Complete the hopping sequence
3. Progress through all 10 rounds
4. Earn points based on performance

**Files:**
- `HopscotchGame.java` - Main simulator with GUI
- `HopscotchPlayer.java` - Player mechanics

## Requirements

- Java Development Kit (JDK) 8 or higher
- Java Swing (included in JDK)

## Quick Start

### Compile All Games
```bash
cd Battleship
javac src/*.java
```

### Launch Games

**Battleship - Command Line Interface:**
```bash
java -cp src PlayerAndEnemyMain
```

**Battleship - Tactical Radar Display:**
```bash
java -cp src GameGUI
```

**Battleship - Modern Combat Display:**
```bash
java -cp src ModernGameGUI
```

**Hopscotch Training Simulator:**
```bash
java -cp src HopscotchGame
```

## Game Controls

### Naval Battleship Combat

**Console Version:**
- Enter X,Y coordinates to navigate your battleship
- Choose between manual navigation or auto-evasion mode
- Monitor incoming missile warnings
- Track hull integrity and remaining enemy missiles

**GUI Version:**
- **Left-click** grid cells to move your battleship
- **Auto Move button:** Engage automatic evasive maneuvers
- **New Game button:** Start a new naval engagement
- Blue marker (P): Your battleship position
- Red marker (M): Enemy missile impact location

**Strategic Tips:**
- Keep moving - stationary ships are easy targets
- Watch the pattern of enemy fire
- Use auto-evasion when overwhelmed
- Maximize distance from last impact zone
- Manage your hull integrity carefully

### Hopscotch Controls
- **Throw Stone:** Launch stone to target square
- **Start Hopping:** Execute hopping sequence
- **New Game:** Reset simulator

## Project Structure

```
Battleship/
├── src/
│   ├── PlayerAndEnemyMain.java   # CLI naval combat
│   ├── GameGUI.java               # Basic tactical display
│   ├── ModernGameGUI.java         # Enhanced combat GUI
│   ├── Player.java                # Battleship command system
│   ├── Enemy.java                 # Enemy missile launcher
│   ├── HopscotchGame.java         # Recreation simulator
│   └── HopscotchPlayer.java       # Simulator mechanics
├── bin/                           # Compiled classes
└── README.md                      # Mission briefing
```

## Tactical Information

### Battleship Combat Specifications

**Battlefield Grid:** 20×20 Naval Sectors
**Starting Position:** Sector (1,1) - Port Corner
**Enemy Firepower:** 15 Guided Missiles

**Hull Integrity System:**
- Maximum Hull Strength: 10 points
- **Direct Hit (0 distance):** -3 integrity
  - Missile directly strikes your battleship
  - Critical structural damage
- **Near Miss (1 cell away):** -2 integrity
  - Explosion in adjacent sector
  - Shrapnel and blast wave damage
- **Safe Distance (2+ cells):** No damage
  - Successfully evaded enemy fire

**Victory Conditions:**
- ✓ Survive all 15 missile attacks
- ✓ Maintain hull integrity above 0
- ✗ Hull integrity depleted = Ship sunk

**Combat Phases (Each Round):**
1. Intelligence Phase: Assess battlefield situation
2. Movement Phase: Reposition your battleship
3. Attack Phase: Enemy launches missile
4. Damage Assessment: Check for hits
5. Status Update: Evaluate hull integrity

## Development Notes

This project demonstrates:
- Object-oriented design patterns (Ship, Enemy, Combat systems)
- Event-driven GUI programming with Java Swing
- Game state management
- Collision detection algorithms
- Strategic AI for enemy behavior
- Grid-based coordinate systems
- Real-time visual updates

**Educational Focus:**
- Class inheritance and encapsulation
- Method overloading and polymorphism
- Event listeners and handlers
- 2D array manipulation (grid systems)
- Random number generation (missile targeting)
- Conditional logic (hit detection)

## Future Enhancements

Potential features for future versions:
- [ ] Multiple ship types with different sizes
- [ ] Ship placement phase (traditional Battleship)
- [ ] Turn-based combat with player firing back
- [ ] Multiple enemy ships
- [ ] Power-ups (shields, repairs, countermeasures)
- [ ] Difficulty levels
- [ ] High score tracking
- [ ] Sound effects and animations
- [ ] Network multiplayer mode

## Author

Developed by Charlie
College Programming Project

## License

Educational use - College coursework project

---

**Good luck, Captain! The fleet is counting on you!**

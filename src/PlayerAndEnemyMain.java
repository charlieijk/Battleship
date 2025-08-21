import java.util.Scanner;

public class PlayerAndEnemyMain {
	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Welcome to Player vs Enemy!");
		System.out.print("Enter your player name: ");
		String playerName = sc.nextLine();
		
		Player player = new Player(playerName);
		Enemy enemy = new Enemy();
		
		System.out.println("\nGame Started! You need to avoid the enemy's missiles.");
		System.out.println("The game field is 20x20. You start at position (1,1).");
		
		while (enemy.getNumMissiles() > 0 && player.getHealth() > 0) {
			System.out.println("\n" + "=".repeat(50));
			System.out.println("ROUND " + (16 - enemy.getNumMissiles()));
			player.printDetails();
			System.out.println("Enemy missiles remaining: " + enemy.getNumMissiles());
			
			System.out.println("\nChoose your action:");
			System.out.println("1. Move manually");
			System.out.println("2. Run automatically");
			System.out.print("Enter choice (1 or 2): ");
			
			int choice = sc.nextInt();
			if (choice == 1) {
				movePlayerManually(player);
			} else {
				player.run();
			}
			
			player.printDetails();
			
			System.out.println("\nEnemy is launching a missile...");
			enemy.throwMissile();
			
			checkHit(player, enemy);
		}
		
		if (player.getHealth() <= 0) {
			System.out.println("\n" + "=".repeat(50));
			System.out.println("GAME OVER - " + player.getName() + " has been defeated!");
		} else {
			System.out.println("\n" + "=".repeat(50));
			System.out.println("VICTORY! " + player.getName() + " survived all enemy missiles!");
		}
		
		sc.close();
	}
	
	private static void movePlayerManually(Player player) {
		System.out.println("\nManual Movement:");
		System.out.println("Current position: (" + player.getXLocation() + ", " + player.getYLocation() + ")");
		System.out.print("Enter new X position (1-20): ");
		byte newX = sc.nextByte();
		System.out.print("Enter new Y position (1-20): ");
		byte newY = sc.nextByte();
		player.moveTo(newX, newY);
	}
	
	private static void checkHit(Player player, Enemy enemy) {
		int playerX = player.getXLocation();
		int playerY = player.getYLocation();
		int missileX = enemy.getXLocation();
		int missileY = enemy.getYLocation();
		
		if (playerX == missileX && playerY == missileY) {
			player.directHit();
		} else if (Math.abs(playerX - missileX) <= 1 && Math.abs(playerY - missileY) <= 1) {
			player.grazed();
		} else {
			System.out.println("\nMissile missed! " + player.getName() + " is safe this round.");
		}
	}
}

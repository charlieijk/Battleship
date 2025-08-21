import java.util.Random;

public class Player {
	private String name;
	private byte health;
	private byte xLocation, yLocation;
	
	public Player (String playerName) {
		this.name = playerName;
		this.health = 10;
		this.xLocation = this.yLocation = 1;
	}
	
	public void run () {
		Random rand = new Random();
		for (int i = 0; i < 3; i++) {
			int direction = rand.nextInt(4);
			int numToRun = rand.nextInt (20);
		
			System.out.printf("\n\tRunning %d steps ", numToRun);
			switch (direction) {
			case 0:
				System.out.println("West.");
				if (yLocation - numToRun >= 1)
					yLocation -= numToRun;
				break;
			case 1:
				System.out.println("East.");
				if (yLocation + numToRun <= 20)
					yLocation += numToRun;
				break;
			case 2:
				System.out.println("South.");
				if (xLocation - numToRun >= 1)
					xLocation -= numToRun;
				break;
			case 3:
				System.out.println("North.");
				if (xLocation + numToRun <= 20)
					xLocation += numToRun;
			}
		}

	}
	
	public void printDetails () {
		System.out.println ("\n\t" + this.name + " is at position (" 
					+ this.xLocation + ", " + this.yLocation + ") and has " + this.health + " health units.");
	}
	
	public void directHit () {
		System.out.println ("\n\tOh no " + this.name + ", you got a direct hit.");
		health -= 3;
	}
	
	public void grazed () {
		System.out.println ("\n\tOh no " + this.name + ", you got grazed by a missile.");
		health -= 2;
	}
	
	public String getName() {
		return name;
	}
	
	public byte getHealth() {
		return health;
	}
	
	public byte getXLocation() {
		return xLocation;
	}
	
	public byte getYLocation() {
		return yLocation;
	}
	
	public void moveTo(byte newX, byte newY) {
		if (newX >= 1 && newX <= 20 && newY >= 1 && newY <= 20) {
			this.xLocation = newX;
			this.yLocation = newY;
		}
	}
}

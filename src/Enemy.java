public class Enemy {
	private byte numMissiles;
	private byte xLocation, yLocation;
	
	public Enemy () {
		this.numMissiles = 15;
	}
	
	public byte getNumMissiles() {
		return numMissiles;
	}
	
	public byte getXLocation() {
		return xLocation;
	}
	
	public byte getYLocation() {
		return yLocation;
	}
	
	public void throwMissile () {
		System.out.print("\n\tEnter the x and y value for where you're throwing the missile: ");
		this.xLocation = PlayerAndEnemyMain.sc.nextByte();
		this.yLocation = PlayerAndEnemyMain.sc.nextByte();
		this.numMissiles--;
	}
	
	public void printNumRoundsRemaining () {
		System.out.println("Missiles remaining: " + numMissiles);
	}
	
	public void autoThrowMissile() {
		java.util.Random rand = new java.util.Random();
		this.xLocation = (byte)(rand.nextInt(20) + 1);
		this.yLocation = (byte)(rand.nextInt(20) + 1);
		this.numMissiles--;
	}
}

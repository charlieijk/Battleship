import java.util.Random;

public class HopscotchPlayer {
    private String name;
    private int currentSquare;
    private boolean isHopping;
    private Random random;
    
    public HopscotchPlayer(String name) {
        this.name = name;
        this.currentSquare = 0;
        this.isHopping = false;
        this.random = new Random();
    }
    
    public boolean hopThroughCourt(int stoneSquare) {
        isHopping = true;
        currentSquare = 0;
        
        // Simulate hopping through the court
        // In a real game, this would be more interactive
        boolean success = simulateHoppingSeries(stoneSquare);
        
        isHopping = false;
        currentSquare = 0;
        
        return success;
    }
    
    private boolean simulateHoppingSeries(int stoneSquare) {
        // Simulate the player's skill level (85% success rate)
        double successRate = 0.85;
        
        // Hop from square 1 to 10, skipping the stone square
        for (int square = 1; square <= 10; square++) {
            if (square == stoneSquare) {
                continue; // Skip the square with the stone
            }
            
            currentSquare = square;
            
            // Check if hop is successful
            if (random.nextDouble() > successRate) {
                return false; // Failed the hop
            }
            
            // Brief pause to simulate hopping time
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        // Hop back down, picking up the stone
        for (int square = 10; square >= 1; square--) {
            currentSquare = square;
            
            if (square == stoneSquare) {
                // Pick up the stone - this requires more skill
                if (random.nextDouble() > 0.9) {
                    return false; // Failed to pick up stone while balancing
                }
                continue;
            }
            
            // Check if hop back is successful
            if (random.nextDouble() > successRate) {
                return false; // Failed the return hop
            }
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        
        return true; // Successfully completed the round
    }
    
    public boolean canHopOn(int squareNumber, int stoneSquare) {
        // Player can hop on any square except where the stone is
        return squareNumber != stoneSquare;
    }
    
    public boolean isSingleFootSquare(int squareNumber) {
        // Squares 1, 4, 7, 10 are single-foot squares
        // Squares 2-3, 5-6, 8-9 are double-foot squares (side by side)
        return squareNumber == 1 || squareNumber == 4 || squareNumber == 7 || squareNumber == 10;
    }
    
    public boolean isDoubleFootSquare(int squareNumber) {
        // Squares 2-3, 5-6, 8-9 allow both feet
        return (squareNumber >= 2 && squareNumber <= 3) ||
               (squareNumber >= 5 && squareNumber <= 6) ||
               (squareNumber >= 8 && squareNumber <= 9);
    }
    
    public String getName() {
        return name;
    }
    
    public int getCurrentSquare() {
        return currentSquare;
    }
    
    public boolean isHopping() {
        return isHopping;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    // Method to get hopping instructions for the UI
    public String getHoppingInstructions(int stoneSquare) {
        StringBuilder instructions = new StringBuilder();
        instructions.append("Hopping instructions for ").append(name).append(":\n");
        instructions.append("1. Hop through squares 1-10, skipping square ").append(stoneSquare).append("\n");
        instructions.append("2. Single foot on squares: 1, 4, 7, 10\n");
        instructions.append("3. Both feet on squares: 2-3, 5-6, 8-9\n");
        instructions.append("4. Turn around at square 10\n");
        instructions.append("5. Hop back, picking up stone from square ").append(stoneSquare).append("\n");
        instructions.append("6. Complete the return journey to start\n");
        
        return instructions.toString();
    }
    
    // Method to simulate throwing accuracy (for future enhancement)
    public boolean throwStone(int targetSquare) {
        // 95% accuracy for throwing the stone
        return random.nextDouble() <= 0.95;
    }
}
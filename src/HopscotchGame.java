import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class HopscotchGame extends JFrame {
    private static final int COURT_WIDTH = 400;
    private static final int COURT_HEIGHT = 600;
    private static final Color COURT_COLOR = new Color(240, 230, 140);
    private static final Color SQUARE_COLOR = new Color(220, 220, 220);
    private static final Color PLAYER_COLOR = new Color(0, 120, 215);
    private static final Color STONE_COLOR = new Color(139, 69, 19);
    private static final Color ACTIVE_SQUARE_COLOR = new Color(144, 238, 144);
    
    private HopscotchCourt court;
    private HopscotchPlayer player;
    private JLabel statusLabel;
    private JLabel scoreLabel;
    private JLabel roundLabel;
    private JButton throwStoneButton;
    private JButton hopButton;
    private JButton newGameButton;
    
    private int currentRound = 1;
    private int score = 0;
    private int stonePosition = 0;
    private boolean stoneThrown = false;
    private boolean gameWon = false;
    
    public HopscotchGame() {
        setupGame();
        setupGUI();
        updateDisplay();
    }
    
    private void setupGame() {
        player = new HopscotchPlayer("Player");
        currentRound = 1;
        score = 0;
        stonePosition = 0;
        stoneThrown = false;
        gameWon = false;
    }
    
    private void setupGUI() {
        setTitle("🪨 Hopscotch Game 🪨");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));
        
        createCourt();
        createControlPanel();
        createStatusPanel();
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void createCourt() {
        court = new HopscotchCourt();
        court.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Hopscotch Court"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        add(court, BorderLayout.CENTER);
    }
    
    private void createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBackground(new Color(245, 245, 245));
        
        throwStoneButton = new JButton("🪨 Throw Stone");
        throwStoneButton.setFont(new Font("Arial", Font.BOLD, 12));
        throwStoneButton.addActionListener(e -> throwStone());
        
        hopButton = new JButton("🦘 Start Hopping");
        hopButton.setFont(new Font("Arial", Font.BOLD, 12));
        hopButton.addActionListener(e -> startHopping());
        hopButton.setEnabled(false);
        
        newGameButton = new JButton("🔄 New Game");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 12));
        newGameButton.addActionListener(e -> startNewGame());
        
        controlPanel.add(throwStoneButton);
        controlPanel.add(hopButton);
        controlPanel.add(newGameButton);
        
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void createStatusPanel() {
        JPanel statusPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Game Status"));
        statusPanel.setBackground(new Color(245, 245, 245));
        
        roundLabel = new JLabel("Round: " + currentRound + "/10");
        roundLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        statusLabel = new JLabel("Throw your stone on square " + currentRound);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        statusPanel.add(roundLabel);
        statusPanel.add(scoreLabel);
        statusPanel.add(statusLabel);
        
        add(statusPanel, BorderLayout.NORTH);
    }
    
    private void throwStone() {
        if (!stoneThrown && !gameWon) {
            stonePosition = currentRound;
            stoneThrown = true;
            throwStoneButton.setEnabled(false);
            hopButton.setEnabled(true);
            statusLabel.setText("Stone thrown on square " + stonePosition + ". Click 'Start Hopping'!");
            court.repaint();
        }
    }
    
    private void startHopping() {
        if (stoneThrown && !gameWon) {
            boolean success = player.hopThroughCourt(stonePosition);
            
            if (success) {
                score += currentRound * 10;
                statusLabel.setText("Great job! You completed round " + currentRound);
                
                if (currentRound >= 10) {
                    gameWon = true;
                    statusLabel.setText("🏆 CONGRATULATIONS! You won the game! 🏆");
                    JOptionPane.showMessageDialog(this, 
                        "Amazing! You completed all 10 rounds!\nFinal Score: " + score,
                        "Victory!", JOptionPane.INFORMATION_MESSAGE);
                    throwStoneButton.setEnabled(false);
                } else {
                    currentRound++;
                    throwStoneButton.setEnabled(true);
                }
            } else {
                statusLabel.setText("Oops! Try again this round.");
                throwStoneButton.setEnabled(true);
            }
            
            stoneThrown = false;
            hopButton.setEnabled(false);
            updateDisplay();
        }
    }
    
    private void startNewGame() {
        setupGame();
        throwStoneButton.setEnabled(true);
        hopButton.setEnabled(false);
        updateDisplay();
        court.repaint();
    }
    
    private void updateDisplay() {
        roundLabel.setText("Round: " + currentRound + "/10");
        scoreLabel.setText("Score: " + score);
        
        if (!gameWon && !stoneThrown) {
            statusLabel.setText("Throw your stone on square " + currentRound);
        }
    }
    
    private class HopscotchCourt extends JPanel {
        public HopscotchCourt() {
            setPreferredSize(new Dimension(COURT_WIDTH, COURT_HEIGHT));
            setBackground(COURT_COLOR);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            drawCourt(g2d);
            
            if (stoneThrown) {
                drawStone(g2d);
            }
            
            g2d.dispose();
        }
        
        private void drawCourt(Graphics2D g2d) {
            int squareWidth = 120;
            int squareHeight = 50;
            int startX = (COURT_WIDTH - squareWidth) / 2;
            int startY = COURT_HEIGHT - 60;
            
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.setStroke(new BasicStroke(3));
            
            // Square 1
            drawSquare(g2d, startX, startY, squareWidth, squareHeight, 1);
            startY -= squareHeight;
            
            // Squares 2-3 (side by side)
            drawSquare(g2d, startX - squareWidth/2, startY, squareWidth/2, squareHeight, 2);
            drawSquare(g2d, startX + squareWidth/2, startY, squareWidth/2, squareHeight, 3);
            startY -= squareHeight;
            
            // Square 4
            drawSquare(g2d, startX, startY, squareWidth, squareHeight, 4);
            startY -= squareHeight;
            
            // Squares 5-6 (side by side)
            drawSquare(g2d, startX - squareWidth/2, startY, squareWidth/2, squareHeight, 5);
            drawSquare(g2d, startX + squareWidth/2, startY, squareWidth/2, squareHeight, 6);
            startY -= squareHeight;
            
            // Square 7
            drawSquare(g2d, startX, startY, squareWidth, squareHeight, 7);
            startY -= squareHeight;
            
            // Squares 8-9 (side by side)
            drawSquare(g2d, startX - squareWidth/2, startY, squareWidth/2, squareHeight, 8);
            drawSquare(g2d, startX + squareWidth/2, startY, squareWidth/2, squareHeight, 9);
            startY -= squareHeight;
            
            // Square 10 (semicircle at top)
            drawSquare(g2d, startX, startY, squareWidth, squareHeight, 10);
        }
        
        private void drawSquare(Graphics2D g2d, int x, int y, int width, int height, int number) {
            Color fillColor = SQUARE_COLOR;
            if (number == currentRound && !gameWon) {
                fillColor = ACTIVE_SQUARE_COLOR;
            }
            
            g2d.setColor(fillColor);
            g2d.fillRect(x, y, width, height);
            
            g2d.setColor(Color.BLACK);
            g2d.drawRect(x, y, width, height);
            
            FontMetrics fm = g2d.getFontMetrics();
            String numberStr = String.valueOf(number);
            int textX = x + (width - fm.stringWidth(numberStr)) / 2;
            int textY = y + (height + fm.getAscent()) / 2;
            g2d.drawString(numberStr, textX, textY);
        }
        
        private void drawStone(Graphics2D g2d) {
            int squareWidth = 120;
            int squareHeight = 50;
            int startX = (COURT_WIDTH - squareWidth) / 2;
            int startY = COURT_HEIGHT - 60;
            
            int stoneX = 0, stoneY = 0;
            
            switch (stonePosition) {
                case 1:
                    stoneX = startX + squareWidth/2;
                    stoneY = startY + squareHeight/2;
                    break;
                case 2:
                    stoneX = startX - squareWidth/4;
                    stoneY = startY - squareHeight/2;
                    break;
                case 3:
                    stoneX = startX + 3*squareWidth/4;
                    stoneY = startY - squareHeight/2;
                    break;
                case 4:
                    stoneX = startX + squareWidth/2;
                    stoneY = startY - 3*squareHeight/2;
                    break;
                case 5:
                    stoneX = startX - squareWidth/4;
                    stoneY = startY - 5*squareHeight/2;
                    break;
                case 6:
                    stoneX = startX + 3*squareWidth/4;
                    stoneY = startY - 5*squareHeight/2;
                    break;
                case 7:
                    stoneX = startX + squareWidth/2;
                    stoneY = startY - 7*squareHeight/2;
                    break;
                case 8:
                    stoneX = startX - squareWidth/4;
                    stoneY = startY - 9*squareHeight/2;
                    break;
                case 9:
                    stoneX = startX + 3*squareWidth/4;
                    stoneY = startY - 9*squareHeight/2;
                    break;
                case 10:
                    stoneX = startX + squareWidth/2;
                    stoneY = startY - 11*squareHeight/2;
                    break;
            }
            
            g2d.setColor(STONE_COLOR);
            g2d.fillOval(stoneX - 8, stoneY - 8, 16, 16);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(stoneX - 8, stoneY - 8, 16, 16);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HopscotchGame().setVisible(true);
        });
    }
}
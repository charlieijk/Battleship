import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameGUI extends JFrame {
    private static final int BOARD_SIZE = 20;
    private static final int CELL_SIZE = 25;
    
    private Player player;
    private Enemy enemy;
    private JPanel gameBoard;
    private JLabel statusLabel;
    private JLabel healthLabel;
    private JLabel missilesLabel;
    private JButton autoMoveButton;
    private JButton newGameButton;
    private JPanel[][] cells;
    private boolean gameOver = false;
    private int round = 1;
    
    public GameGUI() {
        initializeGame();
        setupGUI();
        updateDisplay();
    }
    
    private void initializeGame() {
        String playerName = JOptionPane.showInputDialog(this, 
            "Enter your player name:", "Player vs Enemy", JOptionPane.PLAIN_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player";
        }
        
        player = new Player(playerName);
        enemy = new Enemy();
        round = 1;
        gameOver = false;
    }
    
    private void setupGUI() {
        setTitle("Player vs Enemy - GUI Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        createGameBoard();
        createControlPanel();
        createStatusPanel();
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void createGameBoard() {
        gameBoard = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE, 1, 1));
        gameBoard.setBackground(Color.BLACK);
        gameBoard.setBorder(BorderFactory.createTitledBorder("Game Field (20x20)"));
        
        cells = new JPanel[BOARD_SIZE][BOARD_SIZE];
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                JPanel cell = new JPanel();
                cell.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cell.setBackground(Color.LIGHT_GRAY);
                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                
                final int x = i + 1;
                final int y = j + 1;
                
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!gameOver) {
                            movePlayerTo(x, y);
                        }
                    }
                });
                
                cells[i][j] = cell;
                gameBoard.add(cell);
            }
        }
        
        add(gameBoard, BorderLayout.CENTER);
    }
    
    private void createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout());
        
        autoMoveButton = new JButton("Auto Move (Run)");
        autoMoveButton.addActionListener(e -> autoMovePlayer());
        
        newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> startNewGame());
        
        controlPanel.add(autoMoveButton);
        controlPanel.add(newGameButton);
        
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void createStatusPanel() {
        JPanel statusPanel = new JPanel(new GridLayout(3, 1));
        statusPanel.setBorder(BorderFactory.createTitledBorder("Game Status"));
        
        statusLabel = new JLabel("Round: " + round);
        healthLabel = new JLabel("Health: " + player.getHealth());
        missilesLabel = new JLabel("Enemy Missiles: " + enemy.getNumMissiles());
        
        statusPanel.add(statusLabel);
        statusPanel.add(healthLabel);
        statusPanel.add(missilesLabel);
        
        add(statusPanel, BorderLayout.NORTH);
    }
    
    private void movePlayerTo(int x, int y) {
        if (x >= 1 && x <= BOARD_SIZE && y >= 1 && y <= BOARD_SIZE) {
            player.moveTo((byte)x, (byte)y);
            enemyTurn();
        }
    }
    
    private void autoMovePlayer() {
        player.run();
        enemyTurn();
    }
    
    private void enemyTurn() {
        if (gameOver) return;
        
        enemy.autoThrowMissile();
        checkHit();
        updateDisplay();
        
        if (player.getHealth() <= 0) {
            gameOver = true;
            JOptionPane.showMessageDialog(this, 
                "GAME OVER - " + player.getName() + " has been defeated!", 
                "Game Over", JOptionPane.ERROR_MESSAGE);
            autoMoveButton.setEnabled(false);
        } else if (enemy.getNumMissiles() <= 0) {
            gameOver = true;
            JOptionPane.showMessageDialog(this, 
                "VICTORY! " + player.getName() + " survived all enemy missiles!", 
                "Victory!", JOptionPane.INFORMATION_MESSAGE);
            autoMoveButton.setEnabled(false);
        } else {
            round++;
        }
    }
    
    private void checkHit() {
        int playerX = player.getXLocation();
        int playerY = player.getYLocation();
        int missileX = enemy.getXLocation();
        int missileY = enemy.getYLocation();
        
        if (playerX == missileX && playerY == missileY) {
            player.directHit();
            JOptionPane.showMessageDialog(this, 
                "Direct Hit! " + player.getName() + " lost 3 health!", 
                "Hit!", JOptionPane.WARNING_MESSAGE);
        } else if (Math.abs(playerX - missileX) <= 1 && Math.abs(playerY - missileY) <= 1) {
            player.grazed();
            JOptionPane.showMessageDialog(this, 
                "Grazed! " + player.getName() + " lost 2 health!", 
                "Grazed!", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void updateDisplay() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                cells[i][j].setBackground(Color.LIGHT_GRAY);
                cells[i][j].removeAll();
            }
        }
        
        int playerX = player.getXLocation() - 1;
        int playerY = player.getYLocation() - 1;
        cells[playerX][playerY].setBackground(Color.BLUE);
        JLabel playerLabel = new JLabel("P", SwingConstants.CENTER);
        playerLabel.setForeground(Color.WHITE);
        playerLabel.setFont(new Font("Arial", Font.BOLD, 12));
        cells[playerX][playerY].add(playerLabel);
        
        if (enemy.getXLocation() > 0 && enemy.getYLocation() > 0) {
            int missileX = enemy.getXLocation() - 1;
            int missileY = enemy.getYLocation() - 1;
            cells[missileX][missileY].setBackground(Color.RED);
            JLabel missileLabel = new JLabel("M", SwingConstants.CENTER);
            missileLabel.setForeground(Color.WHITE);
            missileLabel.setFont(new Font("Arial", Font.BOLD, 12));
            cells[missileX][missileY].add(missileLabel);
        }
        
        statusLabel.setText("Round: " + round + " | " + player.getName() + " at (" + 
                           player.getXLocation() + "," + player.getYLocation() + ")");
        healthLabel.setText("Health: " + player.getHealth());
        missilesLabel.setText("Enemy Missiles: " + enemy.getNumMissiles());
        
        repaint();
    }
    
    private void startNewGame() {
        initializeGame();
        autoMoveButton.setEnabled(true);
        updateDisplay();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GameGUI().setVisible(true);
        });
    }
}
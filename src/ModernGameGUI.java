import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ModernGameGUI extends JFrame {
    private static final int BOARD_SIZE = 20;
    private static final int CELL_SIZE = 30;
    private static final Color DARK_BG = new Color(25, 25, 35);
    private static final Color CELL_BG = new Color(45, 45, 55);
    private static final Color CELL_HOVER = new Color(65, 65, 75);
    private static final Color PLAYER_COLOR = new Color(0, 150, 255);
    private static final Color MISSILE_COLOR = new Color(255, 50, 50);
    private static final Color EXPLOSION_COLOR = new Color(255, 165, 0);
    private static final Color ACCENT_COLOR = new Color(100, 200, 255);
    
    private Player player;
    private Enemy enemy;
    private ModernGameBoard gameBoard;
    private JLabel statusLabel;
    private JLabel healthLabel;
    private JLabel missilesLabel;
    private JLabel scoreLabel;
    private ModernButton autoMoveButton;
    private ModernButton newGameButton;
    private ModernButton difficultyButton;
    private boolean gameOver = false;
    private int round = 1;
    private int score = 0;
    private int difficulty = 1;
    private Timer animationTimer;
    private List<Particle> particles = new ArrayList<>();
    
    public ModernGameGUI() {
        setupModernLookAndFeel();
        initializeGame();
        setupModernGUI();
        startAnimationTimer();
        updateDisplay();
    }
    
    private void setupModernLookAndFeel() {
        // Use default look and feel
        
        // Custom UI properties for modern look
        UIManager.put("OptionPane.background", DARK_BG);
        UIManager.put("Panel.background", DARK_BG);
        UIManager.put("OptionPane.messageForeground", Color.WHITE);
    }
    
    private void initializeGame() {
        String playerName = showModernInputDialog("Enter your warrior name:", "BATTLE ARENA");
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Player";
        }
        
        player = new Player(playerName);
        enemy = new Enemy();
        round = 1;
        score = 0;
        gameOver = false;
        particles.clear();
    }
    
    private String showModernInputDialog(String message, String title) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel label = new JLabel(message);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JTextField textField = new JTextField(15);
        textField.setBackground(CELL_BG);
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(ACCENT_COLOR);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        
        int result = JOptionPane.showConfirmDialog(this, panel, title, 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        return result == JOptionPane.OK_OPTION ? textField.getText() : null;
    }
    
    private void setupModernGUI() {
        setTitle("⚔ BATTLE ARENA - Modern Combat ⚔");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(DARK_BG);
        
        createModernGameBoard();
        createModernControlPanel();
        createModernStatusPanel();
        
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void createModernGameBoard() {
        gameBoard = new ModernGameBoard();
        gameBoard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        add(gameBoard, BorderLayout.CENTER);
    }
    
    private void createModernControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        controlPanel.setBackground(DARK_BG);
        
        autoMoveButton = new ModernButton("🎯 AUTO MOVE", ACCENT_COLOR);
        autoMoveButton.addActionListener(e -> autoMovePlayer());
        
        newGameButton = new ModernButton("🔄 NEW GAME", new Color(50, 205, 50));
        newGameButton.addActionListener(e -> startNewGame());
        
        difficultyButton = new ModernButton("⚡ DIFFICULTY: " + difficulty, new Color(255, 140, 0));
        difficultyButton.addActionListener(e -> changeDifficulty());
        
        controlPanel.add(autoMoveButton);
        controlPanel.add(newGameButton);
        controlPanel.add(difficultyButton);
        
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void createModernStatusPanel() {
        JPanel statusPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        statusPanel.setBackground(DARK_BG);
        statusPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        statusLabel = createModernLabel("🎮 Round: " + round, 16);
        healthLabel = createModernLabel("❤️ Health: " + player.getHealth(), 16);
        missilesLabel = createModernLabel("💥 Enemy Missiles: " + enemy.getNumMissiles(), 16);
        scoreLabel = createModernLabel("⭐ Score: " + score, 16);
        
        statusPanel.add(statusLabel);
        statusPanel.add(healthLabel);
        statusPanel.add(missilesLabel);
        statusPanel.add(scoreLabel);
        
        add(statusPanel, BorderLayout.NORTH);
    }
    
    private JLabel createModernLabel(String text, int fontSize) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        return label;
    }
    
    private void startAnimationTimer() {
        animationTimer = new Timer(16, e -> {
            updateParticles();
            gameBoard.repaint();
        });
        animationTimer.start();
    }
    
    private void updateParticles() {
        particles.removeIf(p -> p.update());
    }
    
    private void addExplosion(int x, int y, Color color) {
        Random rand = new Random();
        for (int i = 0; i < 15; i++) {
            particles.add(new Particle(
                x * CELL_SIZE + CELL_SIZE / 2,
                y * CELL_SIZE + CELL_SIZE / 2,
                rand.nextDouble() * 6 - 3,
                rand.nextDouble() * 6 - 3,
                color
            ));
        }
        playSound(); // Simple beep sound
    }
    
    private void playSound() {
        try {
            Toolkit.getDefaultToolkit().beep();
        } catch (Exception e) {
            // Silent fail if sound not available
        }
    }
    
    private void movePlayerTo(int x, int y) {
        if (x >= 1 && x <= BOARD_SIZE && y >= 1 && y <= BOARD_SIZE && !gameOver) {
            player.moveTo((byte)x, (byte)y);
            addExplosion(x - 1, y - 1, PLAYER_COLOR);
            enemyTurn();
        }
    }
    
    private void autoMovePlayer() {
        if (!gameOver) {
            player.run();
            addExplosion(player.getXLocation() - 1, player.getYLocation() - 1, PLAYER_COLOR);
            enemyTurn();
        }
    }
    
    private void enemyTurn() {
        if (gameOver) return;
        
        enemy.autoThrowMissile();
        addExplosion(enemy.getXLocation() - 1, enemy.getYLocation() - 1, MISSILE_COLOR);
        
        Timer hitCheckTimer = new Timer(300, e -> {
            checkHit();
            updateDisplay();
            ((Timer)e.getSource()).stop();
        });
        hitCheckTimer.setRepeats(false);
        hitCheckTimer.start();
    }
    
    private void checkHit() {
        int playerX = player.getXLocation();
        int playerY = player.getYLocation();
        int missileX = enemy.getXLocation();
        int missileY = enemy.getYLocation();
        
        if (playerX == missileX && playerY == missileY) {
            player.directHit();
            addExplosion(playerX - 1, playerY - 1, EXPLOSION_COLOR);
            showModernMessage("💥 DIRECT HIT! -3 Health", "DAMAGE TAKEN", JOptionPane.WARNING_MESSAGE);
        } else if (Math.abs(playerX - missileX) <= 1 && Math.abs(playerY - missileY) <= 1) {
            player.grazed();
            addExplosion(playerX - 1, playerY - 1, new Color(255, 200, 0));
            showModernMessage("⚡ GRAZED! -2 Health", "MINOR DAMAGE", JOptionPane.WARNING_MESSAGE);
        } else {
            score += 10 * difficulty;
            showModernMessage("✅ MISSILE AVOIDED! +" + (10 * difficulty) + " points", "SAFE!", JOptionPane.INFORMATION_MESSAGE);
        }
        
        if (player.getHealth() <= 0) {
            gameOver = true;
            showModernMessage("💀 GAME OVER!\nFinal Score: " + score, "DEFEAT", JOptionPane.ERROR_MESSAGE);
            autoMoveButton.setEnabled(false);
        } else if (enemy.getNumMissiles() <= 0) {
            gameOver = true;
            score += 100 * difficulty;
            showModernMessage("🏆 VICTORY!\nFinal Score: " + score + "\nYou survived the onslaught!", "TRIUMPH", JOptionPane.INFORMATION_MESSAGE);
            autoMoveButton.setEnabled(false);
        } else {
            round++;
        }
    }
    
    private void showModernMessage(String message, String title, int messageType) {
        // Create a delayed message to allow animations to play
        Timer messageTimer = new Timer(500, e -> {
            JOptionPane.showMessageDialog(this, message, title, messageType);
            ((Timer)e.getSource()).stop();
        });
        messageTimer.setRepeats(false);
        messageTimer.start();
    }
    
    private void changeDifficulty() {
        difficulty = (difficulty % 3) + 1;
        difficultyButton.setText("⚡ DIFFICULTY: " + difficulty);
        String[] levels = {"EASY", "NORMAL", "HARD"};
        showModernMessage("Difficulty set to: " + levels[difficulty - 1], "DIFFICULTY CHANGED", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateDisplay() {
        statusLabel.setText("🎮 Round: " + round + " | " + player.getName() + " at (" + 
                           player.getXLocation() + "," + player.getYLocation() + ")");
        healthLabel.setText("❤️ Health: " + player.getHealth());
        missilesLabel.setText("💥 Enemy Missiles: " + enemy.getNumMissiles());
        scoreLabel.setText("⭐ Score: " + score);
        
        gameBoard.repaint();
    }
    
    private void startNewGame() {
        initializeGame();
        autoMoveButton.setEnabled(true);
        updateDisplay();
    }
    
    private class ModernGameBoard extends JPanel {
        public ModernGameBoard() {
            setPreferredSize(new Dimension(BOARD_SIZE * CELL_SIZE + 20, BOARD_SIZE * CELL_SIZE + 20));
            setBackground(DARK_BG);
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int x = (e.getX() - 10) / CELL_SIZE + 1;
                    int y = (e.getY() - 10) / CELL_SIZE + 1;
                    movePlayerTo(x, y);
                }
            });
            
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    repaint();
                }
            });
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw grid
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    int x = i * CELL_SIZE + 10;
                    int y = j * CELL_SIZE + 10;
                    
                    // Gradient background for cells
                    GradientPaint gradient = new GradientPaint(
                        x, y, CELL_BG,
                        x + CELL_SIZE, y + CELL_SIZE, CELL_BG.brighter()
                    );
                    g2d.setPaint(gradient);
                    g2d.fillRoundRect(x, y, CELL_SIZE - 2, CELL_SIZE - 2, 8, 8);
                    
                    // Border
                    g2d.setColor(CELL_BG.brighter());
                    g2d.drawRoundRect(x, y, CELL_SIZE - 2, CELL_SIZE - 2, 8, 8);
                }
            }
            
            // Draw player with glow effect
            int playerX = (player.getXLocation() - 1) * CELL_SIZE + 10;
            int playerY = (player.getYLocation() - 1) * CELL_SIZE + 10;
            
            // Player glow
            g2d.setColor(new Color(PLAYER_COLOR.getRed(), PLAYER_COLOR.getGreen(), PLAYER_COLOR.getBlue(), 50));
            g2d.fillOval(playerX - 5, playerY - 5, CELL_SIZE + 8, CELL_SIZE + 8);
            
            // Player body
            GradientPaint playerGradient = new GradientPaint(
                playerX, playerY, PLAYER_COLOR,
                playerX + CELL_SIZE, playerY + CELL_SIZE, PLAYER_COLOR.darker()
            );
            g2d.setPaint(playerGradient);
            g2d.fillOval(playerX + 3, playerY + 3, CELL_SIZE - 8, CELL_SIZE - 8);
            
            // Player symbol
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2d.getFontMetrics();
            String playerSymbol = "⚔";
            int symbolX = playerX + (CELL_SIZE - fm.stringWidth(playerSymbol)) / 2;
            int symbolY = playerY + (CELL_SIZE + fm.getAscent()) / 2;
            g2d.drawString(playerSymbol, symbolX, symbolY);
            
            // Draw missile if exists
            if (enemy.getXLocation() > 0 && enemy.getYLocation() > 0) {
                int missileX = (enemy.getXLocation() - 1) * CELL_SIZE + 10;
                int missileY = (enemy.getYLocation() - 1) * CELL_SIZE + 10;
                
                // Missile glow
                g2d.setColor(new Color(MISSILE_COLOR.getRed(), MISSILE_COLOR.getGreen(), MISSILE_COLOR.getBlue(), 80));
                g2d.fillOval(missileX - 3, missileY - 3, CELL_SIZE + 4, CELL_SIZE + 4);
                
                // Missile body
                GradientPaint missileGradient = new GradientPaint(
                    missileX, missileY, MISSILE_COLOR,
                    missileX + CELL_SIZE, missileY + CELL_SIZE, MISSILE_COLOR.darker()
                );
                g2d.setPaint(missileGradient);
                g2d.fillOval(missileX + 5, missileY + 5, CELL_SIZE - 12, CELL_SIZE - 12);
                
                // Missile symbol
                g2d.setColor(Color.WHITE);
                String missileSymbol = "💥";
                int mSymbolX = missileX + (CELL_SIZE - fm.stringWidth(missileSymbol)) / 2;
                int mSymbolY = missileY + (CELL_SIZE + fm.getAscent()) / 2;
                g2d.drawString(missileSymbol, mSymbolX, mSymbolY);
            }
            
            // Draw particles
            for (Particle particle : particles) {
                particle.draw(g2d);
            }
            
            g2d.dispose();
        }
    }
    
    private class ModernButton extends JButton {
        private Color baseColor;
        
        public ModernButton(String text, Color color) {
            super(text);
            this.baseColor = color;
            setupModernStyle();
        }
        
        private void setupModernStyle() {
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            setForeground(Color.WHITE);
            setBackground(baseColor);
            setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(baseColor.brighter());
                }
                
                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(baseColor);
                }
                
                @Override
                public void mousePressed(MouseEvent e) {
                    setBackground(baseColor.darker());
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    setBackground(baseColor.brighter());
                }
            });
        }
    }
    
    private class Particle {
        private double x, y, vx, vy;
        private Color color;
        private int life = 30;
        private int maxLife = 30;
        
        public Particle(double x, double y, double vx, double vy, Color color) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.color = color;
        }
        
        public boolean update() {
            x += vx;
            y += vy;
            vx *= 0.98;
            vy *= 0.98;
            life--;
            return life <= 0;
        }
        
        public void draw(Graphics2D g2d) {
            float alpha = (float) life / maxLife;
            Color drawColor = new Color(
                color.getRed() / 255f,
                color.getGreen() / 255f,
                color.getBlue() / 255f,
                alpha
            );
            g2d.setColor(drawColor);
            g2d.fillOval((int)x - 2, (int)y - 2, 4, 4);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ModernGameGUI().setVisible(true);
        });
    }
}
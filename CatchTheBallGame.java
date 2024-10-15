import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class CatchTheBallGame extends JFrame {

    private BallPanel ballPanel;
    private int ballX, ballY, ballSize = 50;
    private int score = 0;
    private int missedCatches = 0;
    private Timer moveTimer;
    private Random random;
    private final int MAX_MISSES = 3;

    public CatchTheBallGame() {
        setTitle("Catch the Moving Ball");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        random = new Random();
        resetBallPosition();

        ballPanel = new BallPanel();
        add(ballPanel);

        ballPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int clickX = e.getX();
                int clickY = e.getY();

                if (isClickInsideBall(clickX, clickY)) {
                    score++;
                    missedCatches = 0;
                    moveBall();
                } else {
                    missedCatches++;
                    if (missedCatches >= MAX_MISSES) {
                        gameOver();
                    }
                }
            }
        });

        moveTimer = new Timer(750, e -> moveBall());
        moveTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CatchTheBallGame game = new CatchTheBallGame();
            game.setVisible(true);
        });
    }

    class BallPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.WHITE);

            g.setColor(Color.RED);
            g.fillOval(ballX, ballY, ballSize, ballSize);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score: " + score, 20, 30);
            g.drawString("Missed: " + missedCatches, 20, 60);
        }
    }

    private boolean isClickInsideBall(int clickX, int clickY) {
        int radius = ballSize / 2;
        int centerX = ballX + radius;
        int centerY = ballY + radius;
        return Math.pow(clickX - centerX, 2) + Math.pow(clickY - centerY, 2) <= Math.pow(radius, 2);
    }

    private void moveBall() {
        resetBallPosition();
        ballPanel.repaint();
    }

    private void resetBallPosition() {
        ballX = random.nextInt(750);
        ballY = random.nextInt(550);
    }

    private void gameOver() {
        moveTimer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + score);
        System.exit(0); // Exit the game or restart it as per your requirement
    }
}

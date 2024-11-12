import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CombinedApplication extends JFrame {
    public CombinedApplication() {
        setTitle("Combined Java Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Tasks");

        JMenuItem aquariumItem = new JMenuItem("Aquarium Simulation");
        JMenuItem matematikoItem = new JMenuItem("Matematiko Game");
        JMenuItem snailItem = new JMenuItem("Snail Drawing");

        aquariumItem.addActionListener(e -> openAquariumSimulation());
        matematikoItem.addActionListener(e -> openMatematikoGame());
        snailItem.addActionListener(e -> openSnailDrawing());

        menu.add(aquariumItem);
        menu.add(matematikoItem);
        menu.add(snailItem);
        menuBar.add(menu);

        setJMenuBar(menuBar);
        setVisible(true);
    }

    private void openAquariumSimulation() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Aquarium Simulation");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new Aquarium(5, 20, 30));
            frame.setVisible(true);
        });
    }

    private void openMatematikoGame() {
        SwingUtilities.invokeLater(() -> new MatematikoGame());
    }

    private void openSnailDrawing() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Рисунок улитки");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(new SnailDrawing());
            frame.setSize(350, 250);
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CombinedApplication());
    }

    public class SnailDrawing extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Фон
            setBackground(Color.GREEN);

            // Тело (нижняя линия)
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(8));
            g2d.drawLine(50, 180, 148, 180);

            // Раковина
            Ellipse2D.Double shell = new Ellipse2D.Double(50, 100, 95, 80);
            g2d.fill(shell);

            // Внутренний круг раковины
            g2d.setColor(Color.WHITE);
            Ellipse2D.Double innerShell = new Ellipse2D.Double(65, 120, 65, 55);
            g2d.fill(innerShell);

            // Шея
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(11));
            g2d.drawLine(150, 174, 180, 115);

            // Голова
            Ellipse2D.Double head = new Ellipse2D.Double(175, 90, 40, 40);
            g2d.fill(head);

            // Глаза
            g2d.setColor(Color.WHITE);
            Ellipse2D.Double leftEye = new Ellipse2D.Double(185, 102, 10, 10);
            Ellipse2D.Double rightEye = new Ellipse2D.Double(200, 102, 10, 10);
            g2d.fill(leftEye);
            g2d.fill(rightEye);

            // Зрачки
            g2d.setColor(Color.BLACK);
            Ellipse2D.Double leftPupil = new Ellipse2D.Double(188, 105, 4, 4);
            Ellipse2D.Double rightPupil = new Ellipse2D.Double(203, 105, 4, 4);
            g2d.fill(leftPupil);
            g2d.fill(rightPupil);

            // Улыбка
            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(2));
            Arc2D.Double smile = new Arc2D.Double(188, 116, 15, 7, 0, -188, Arc2D.OPEN);
            g2d.draw(smile);

            // Усики
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawLine(190, 90, 180, 80);
            g2d.drawLine(203, 90, 210, 75);
        }

        public void main(String[] args) {
            JFrame frame = new JFrame("Рисунок улитки");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SnailDrawing());
            frame.setSize(350, 250);
            frame.setVisible(true);
        }
    }
}


abstract class TFish {
    protected double x, y, speed, size, direction;
    protected Color color;

    public TFish(double x, double y, double speed, double size, Color color, double direction) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.color = color;
        this.direction = direction;
    }

    public abstract void draw(Graphics g);

    public void run(int width, int height) {
        x += Math.cos(direction) * speed;
        y += Math.sin(direction) * speed;

        if (x < 0 || x > width || y < 0 || y > height) {
            direction = Math.random() * 2 * Math.PI;
        }

        if (Math.random() < 0.05) {
            direction += (Math.random() - 0.5) * Math.PI / 2;
        }
    }

    public double getX() { return x; }
    public double getY() { return y; }
}

class TPike extends TFish {
    public TPike(double x, double y, double speed, double size, double direction) {
        super(x, y, speed, size, Color.GREEN, direction);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int arrowLength = (int)size;
        int[] xPoints = {
                (int)x,
                (int)(x - arrowLength * Math.cos(direction - Math.PI/6)),
                (int)(x - arrowLength * 0.8 * Math.cos(direction)),
                (int)(x - arrowLength * Math.cos(direction + Math.PI/6))
        };
        int[] yPoints = {
                (int)y,
                (int)(y - arrowLength * Math.sin(direction - Math.PI/6)),
                (int)(y - arrowLength * 0.8 * Math.sin(direction)),
                (int)(y - arrowLength * Math.sin(direction + Math.PI/6))
        };
        g.fillPolygon(xPoints, yPoints, 4);
    }

    public void eat(List<TKarp> carps, double eatingDistance) {
        for (TKarp carp : new ArrayList<>(carps)) {
            double distance = Math.sqrt(Math.pow(x - carp.getX(), 2) + Math.pow(y - carp.getY(), 2));
            if (distance < eatingDistance) {
                carps.remove(carp);
                break;
            }
        }
    }
}

class TKarp extends TFish {
    public TKarp(double x, double y, double speed, double size, double direction) {
        super(x, y, speed, size, Color.RED, direction);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        int[] xPoints = {
                (int)x,
                (int)(x - size * Math.cos(direction - Math.PI/6)),
                (int)(x - size * Math.cos(direction + Math.PI/6))
        };
        int[] yPoints = {
                (int)y,
                (int)(y - size * Math.sin(direction - Math.PI/6)),
                (int)(y - size * Math.sin(direction + Math.PI/6))
        };
        g.fillPolygon(xPoints, yPoints, 3);
    }
}

class Bubble {
    private double x, y, size, speed;

    public Bubble(int width) {
        Random rand = new Random();
        this.x = rand.nextDouble() * width;
        this.y = rand.nextDouble() * 600 + 600;
        this.size = rand.nextDouble() * 10 + 5;
        this.speed = rand.nextDouble() * 2 + 1;
    }

    public void move() {
        y -= speed;
        if (y + size < 0) {
            y = 600 + size;
        }
    }

    public void draw(Graphics g) {
        g.setColor(new Color(255, 255, 255, 100));
        g.fillOval((int)(x - size/2), (int)(y - size/2), (int)size, (int)size);
    }
}

class Seaweed {
    private int x, height;
    private Color color;
    private List<Point> points;

    public Seaweed(int x, int height) {
        this.x = x;
        this.height = height;
        this.color = new Color(0, 100 + new Random().nextInt(155), 0);
        this.points = new ArrayList<>();
        generatePoints();
    }

    private void generatePoints() {
        Random rand = new Random();
        int segments = height / 20;
        for (int i = 0; i <= segments; i++) {
            points.add(new Point(x + rand.nextInt(21) - 10, 600 - i * 20));
        }
    }

    public void sway() {
        Random rand = new Random();
        for (Point p : points) {
            p.x += rand.nextInt(3) - 1;
        }
    }

    public void draw(Graphics g) {
        g.setColor(color);
        for (int i = 1; i < points.size(); i++) {
            Point p1 = points.get(i - 1);
            Point p2 = points.get(i);
            g.drawLine(p1.x, p1.y, p2.x, p2.y);
        }
    }
}

class Aquarium extends JPanel {
    private List<TPike> pikes;
    private List<TKarp> carps;
    private List<Bubble> bubbles;
    private List<Seaweed> seaweeds;
    private double eatingDistance;

    public Aquarium(int pikeCount, int carpCount, double eatingDistance) {
        this.eatingDistance = eatingDistance;
        pikes = new ArrayList<>();
        carps = new ArrayList<>();
        bubbles = new ArrayList<>();
        seaweeds = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < pikeCount; i++) {
            pikes.add(new TPike(random.nextDouble() * 800, random.nextDouble() * 600,
                    random.nextDouble() * 5 + 1, random.nextDouble() * 20 + 10,
                    random.nextDouble() * 2 * Math.PI));
        }
        for (int i = 0; i < carpCount; i++) {
            carps.add(new TKarp(random.nextDouble() * 800, random.nextDouble() * 600,
                    random.nextDouble() * 3 + 1, random.nextDouble() * 15 + 5,
                    random.nextDouble() * 2 * Math.PI));
        }
        for (int i = 0; i < 50; i++) {
            bubbles.add(new Bubble(800));
        }
        for (int i = 0; i < 5; i++) {
            seaweeds.add(new Seaweed(random.nextInt(800), random.nextInt(200) + 100));
        }

        Timer timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                update();
                repaint();
            }
        });
        timer.start();
    }

    public void update() {
        for (TPike pike : pikes) {
            pike.run(getWidth(), getHeight());
            pike.eat(carps, eatingDistance);
        }

        for (TKarp carp : carps) {
            carp.run(getWidth(), getHeight());
        }

        for (Bubble bubble : bubbles) {
            bubble.move();
        }

        for (Seaweed seaweed : seaweeds) {
            seaweed.sway();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(66, 240, 255));
        g.fillRect(0, 0, getWidth(), getHeight());

        for (Seaweed seaweed : seaweeds) {
            seaweed.draw(g);
        }

        for (TFish fish : pikes) {
            fish.draw(g);
        }
        for (TFish fish : carps) {
            fish.draw(g);
        }

        for (Bubble bubble : bubbles) {
            bubble.draw(g);
        }

        g.setColor(Color.BLACK);
        g.drawString("Pikes: " + pikes.size(), 10, 20);
        g.drawString("Carps: " + carps.size(), 10, 40);
    }
}


class MatematikoGame extends JFrame {
    private JButton[][] gameBoard;
    private JLabel currentNumberLabel;
    private JLabel playerScoreLabel;
    private JLabel computerScoreLabel;
    private JButton drawCardButton;

    private int currentNumber;
    private int playerScore = 0;
    private int computerScore = 0;
    private Random random = new Random();
    private ComputerPlayer computerPlayer = new ComputerPlayer();
    private String[][] boardState = new String[5][5];

    public MatematikoGame() {
        setTitle("Математико");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 600);
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(5, 5));
        gameBoard = new JButton[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                gameBoard[i][j] = new JButton();
                gameBoard[i][j].setFont(new Font("Arial", Font.BOLD, 20));
                gameBoard[i][j].addActionListener(new CellClickListener(i, j));
                boardPanel.add(gameBoard[i][j]);
                boardState[i][j] = "";
            }
        }
        add(boardPanel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel(new GridLayout(4, 1));
        currentNumberLabel = new JLabel("Текущее число: ", JLabel.CENTER);
        playerScoreLabel = new JLabel("Счет игрока: 0", JLabel.CENTER);
        computerScoreLabel = new JLabel("Счет компьютера: 0", JLabel.CENTER);
        drawCardButton = new JButton("Начать игру");
        drawCardButton.addActionListener(e -> {
            drawCard();
            drawCardButton.setEnabled(false);
        });

        infoPanel.add(currentNumberLabel);
        infoPanel.add(playerScoreLabel);
        infoPanel.add(computerScoreLabel);
        infoPanel.add(drawCardButton);
        add(infoPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void drawCard() {
        currentNumber = random.nextInt(13) + 1;
        currentNumberLabel.setText("Текущее число: " + currentNumber);

        if (isGameOver()) {
            endGame();
        }
    }

    private class CellClickListener implements ActionListener {
        private int row, col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (boardState[row][col].isEmpty()) {
                boardState[row][col] = String.valueOf(currentNumber);
                gameBoard[row][col].setText(String.valueOf(currentNumber));
                playerScore = ScoringLogic.calculateTotalScore(boardState);
                playerScoreLabel.setText("Счет игрока: " + playerScore);

                if (!isGameOver()) {
                    computerMove();
                } else {
                    endGame();
                }
            }
        }
    }

    private void computerMove() {
        drawCard();
        int[] move = computerPlayer.makeMove(boardState, currentNumber);
        boardState[move[0]][move[1]] = String.valueOf(currentNumber);
        gameBoard[move[0]][move[1]].setText(String.valueOf(currentNumber));
        computerScore = ScoringLogic.calculateTotalScore(boardState);
        computerScoreLabel.setText("Счет компьютера: " + computerScore);

        if (isGameOver()) {
            endGame();
        } else {
            drawCard(); // Получаем новое число для игрока
        }
    }

    private boolean isGameOver() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (boardState[i][j].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void endGame() {
        String message;
        if (playerScore > computerScore) {
            message = "Вы выиграли!\nВаш счет: " + playerScore + "\nСчет компьютера: " + computerScore;
        } else if (computerScore > playerScore) {
            message = "Компьютер выиграл!\nВаш счет: " + playerScore + "\nСчет компьютера: " + computerScore;
        } else {
            message = "Ничья!\nВаш счет: " + playerScore + "\nСчет компьютера: " + computerScore;
        }

        int choice = JOptionPane.showConfirmDialog(this, message + "\nХотите начать новую игру?", "Игра окончена", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            System.exit(0);
        }
    }

    private void resetGame() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                boardState[i][j] = "";
                gameBoard[i][j].setText("");
            }
        }
        playerScore = 0;
        computerScore = 0;
        playerScoreLabel.setText("Счет игрока: 0");
        computerScoreLabel.setText("Счет компьютера: 0");
        drawCardButton.setEnabled(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MatematikoGame());
    }
}

class ScoringLogic {
    public static int calculateScore(String[] line, boolean isDiagonal) {
        int[] counts = new int[14]; // Индексы 1-13 для чисел, 0 не используется
        for (String cell : line) {
            if (!cell.isEmpty()) {
                counts[Integer.parseInt(cell)]++;
            }
        }

        int maxCount = 0;
        int secondMaxCount = 0;
        for (int count : counts) {
            if (count > maxCount) {
                secondMaxCount = maxCount;
                maxCount = count;
            } else if (count > secondMaxCount) {
                secondMaxCount = count;
            }
        }

        if (maxCount == 5) {
            return isDiagonal ? 60 : 50;
        } else if (maxCount == 4) {
            return isDiagonal ? 170 : 160;
        } else if (maxCount == 3 && secondMaxCount == 2) {
            return isDiagonal ? 90 : 80;
        } else if (maxCount == 3) {
            return isDiagonal ? 50 : 40;
        } else if (maxCount == 2 && secondMaxCount == 2) {
            return isDiagonal ? 30 : 20;
        } else if (maxCount == 2) {
            return isDiagonal ? 20 : 10;
        }

        return 0;
    }

    public static int calculateTotalScore(String[][] board) {
        int totalScore = 0;

        // Проверка рядов
        for (String[] row : board) {
            totalScore += calculateScore(row, false);
        }

        // Проверка столбцов
        for (int col = 0; col < 5; col++) {
            String[] column = new String[5];
            for (int row = 0; row < 5; row++) {
                column[row] = board[row][col];
            }
            totalScore += calculateScore(column, false);
        }

        // Проверка диагоналей
        String[] mainDiagonal = new String[5];
        String[] antiDiagonal = new String[5];
        for (int i = 0; i < 5; i++) {
            mainDiagonal[i] = board[i][i];
            antiDiagonal[i] = board[i][4-i];
        }
        totalScore += calculateScore(mainDiagonal, true);
        totalScore += calculateScore(antiDiagonal, true);

        return totalScore;
    }
}

class ComputerPlayer {
    private Random random = new Random();

    public int[] makeMove(String[][] board, int currentNumber) {
        int bestScore = -1;
        int[] bestMove = new int[2];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i][j].isEmpty()) {
                    // Пробуем сделать ход
                    board[i][j] = String.valueOf(currentNumber);
                    int score = ScoringLogic.calculateTotalScore(board);
                    // Отменяем ход
                    board[i][j] = "";

                    // Если это лучший ход, запоминаем его
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        // Если все клетки одинаково хороши, выбираем случайную
        if (bestScore == -1) {
            do {
                bestMove[0] = random.nextInt(5);
                bestMove[1] = random.nextInt(5);
            } while (!board[bestMove[0]][bestMove[1]].isEmpty());
        }

        return bestMove;
    }
}

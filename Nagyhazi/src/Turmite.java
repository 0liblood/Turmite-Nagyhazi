import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * The Turmite class represents a JFrame displaying a Turmite simulation based on user-provided configurations.
 */
public class Turmite extends JFrame {
    private final JPanel gridPanel;
    private final SelectedValues[] valueArray;
    boolean[][] grid;
    private int gridSize = 100;
    private int currentState = 0;
    int currentDir;
    int row;
    int col;
    private int stepRate = 10;
    private JComboBox<Integer> stepRateField;
    private Timer timer;
    private Component[] components;

    /**
     * Constructs a Turmite object with the specified SelectedValues array and MainMenu.
     *
     * @param valueArray The array of SelectedValues representing the Turmite configuration.
     * @param mainMenu   The MainMenu instance associated with this Turmite.
     */
    public Turmite(SelectedValues[] valueArray, MainMenu mainMenu) {
        this.gridPanel = mainMenu.getStartGrid();
        components = gridPanel.getComponents();
        this.valueArray = valueArray;
        currentDir = 0;  // 0 = up, 1 = right, 2 = down, 3 = left
        grid = new boolean[gridSize][gridSize];
        row = gridSize / 2;
        col = gridSize / 2;
        setTitle("Turmesz Program");
        setSize(800, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        initializeComponents();

        JLabel stepRateLabel = new JLabel("Lépések száma másodpercenként: ");
        JPanel stepRatePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        stepRatePanel.add(stepRateLabel);
        stepRatePanel.add(stepRateField);

        ReturnButton returnButton = new ReturnButton(mainMenu);

        add(stepRatePanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        add(returnButton, BorderLayout.SOUTH);

        timer = new Timer(1000 / stepRate, e -> run());
        timer.start();
    }

    private void initializeComponents() {
        stepRateField = new JComboBox<>(new Integer[]{1, 5, 10, 20, 50, 100, 200});
        stepRateField.setSelectedIndex(2);
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JPanel panel = (JPanel) components[i * gridSize + j];
                grid[i][j] = panel.getBackground().equals(Color.BLACK);
            }
        }
    }

    void paintCurrent() {
        JPanel panel = (JPanel) components[row * gridSize + col];
        if (grid[row][col]) {
            panel.setBackground(Color.BLACK);
        } else {
            panel.setBackground(Color.WHITE);
        }
    }

    /**
     * Finds the current panel's corresponding state and color and gives it newColor and newState.
     * Paints the current panel to newColor and calls the turning functions for the next panel.
     * If the stepRate has changed, change the rate of run() calls per second.
     */
    private void run() {
        for (int j = 0; j < 4; j++) {
            if (valueArray[j] != null && currentState == valueArray[j].state && grid[row][col] == (valueArray[j].color == 1)) {
                currentState = valueArray[j].newState;
                grid[row][col] = valueArray[j].newColor == 1;
                paintCurrent();
                switch (valueArray[j].direction) {
                    case 'L':
                        turn('L');
                        move();
                        break;
                    case 'R':
                        turn('R');
                        move();
                        break;
                    case 'N':
                        move();
                        break;
                    case 'U':
                        turn('U');
                        move();
                        break;
                }
                if (stepRate != (Integer) stepRateField.getSelectedItem()) {
                    timer.stop();
                    stepRate = (Integer) stepRateField.getSelectedItem();
                    timer = new Timer(1000 / stepRate, e -> run());
                    timer.start();
                }
                break;
            }
        }
    }

    /**
     * Implements left, right and U turns
     * @param direction The direction it will be turning.
     */
    void turn(char direction) {
        if (direction == 'L') {
            currentDir = (currentDir + 3) % 4;
        } else if (direction == 'R') {
            currentDir = (currentDir + 1) % 4;
        }else if (direction == 'U'){
                currentDir = (currentDir + 2) % 4;
        }
    }

    /**
     * Moves the current row and col intigers to their next position.
     */
    void move() {
        if (currentDir == 0)
            col = col - 1;
            //facing up
        else if (currentDir == 1)
            row = row + 1;
            // facing right
        else if (currentDir == 2)
            col = col + 1;
            // facing down
        else if (currentDir == 3)
            row = row - 1;

        //wrap around if goes off the screen
        if (row >= gridSize)
            row = 0;
        if (col >= gridSize)
            col = 0;
        if (row < 0)
            row = gridSize - 1;
        if (col < 0)
            col = gridSize - 1;
    }

    /**
     * ReturnButton is a private class extending JButton and implementing ActionListener
     * to handle the "Vissza" button.
     */
    public class ReturnButton extends JButton implements java.awt.event.ActionListener {
        private final MainMenu mainMenu;

        /**
         * Constructs a ReturnButton with the specified MainMenu.
         *
         * @param mainMenu The MainMenu instance associated with this ReturnButton.
         */
        public ReturnButton(MainMenu mainMenu) {
            super("Vissza");
            this.mainMenu = mainMenu;
            addActionListener(this);
        }

        /**
         * Invoked when the "Vissza" button is clicked.
         * Stops the timer, resets the grid, updates MainMenu, and closes the Turmite window.
         *
         * @param e The ActionEvent that occurred.
         */
        public void actionPerformed(ActionEvent e) {
            timer.stop();
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    JPanel panel = (JPanel) components[i * gridSize + j];
                    if (i == gridSize / 2 && j == gridSize / 2) {
                        panel.setBackground(Color.BLACK);
                    } else {
                        panel.setBackground(Color.WHITE);
                    }
                }
            }
            mainMenu.setStartGrid(gridPanel);
            mainMenu.setVisible(true);
            mainMenu.setLocationRelativeTo(null); // Center the MainMenu window
            dispose(); // Close the Turmite window
        }
    }
}

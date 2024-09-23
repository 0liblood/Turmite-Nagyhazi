import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * The MainMenu class represents the main user interface for the Turmite program.
 * It provides options to configure Turmite settings, run simulations, and manage saved configurations.
 */
public class MainMenu extends JFrame {
    private final JComboBox<Character>[] directionFields = new JComboBox[4];
    private final JComboBox<Integer>[] newDirectionFields = new JComboBox[4];
    private final JComboBox<Integer>[] newColorFields = new JComboBox[4];
    private JButton runButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton startGridButton;
    private final JPanel menuPanel = new JPanel();
    private final JPanel configPanel = new JPanel();
    private JPanel startGrid;

    /**
     * Constructs a new MainMenu instance with the specified title and size.
     *
     * @throws IOException if an I/O error occurs while initializing components.
     */
    public MainMenu() throws IOException {
        setTitle("Turmesz Program");
        setSize(550, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        initializeComponents();
        arrangeComponents();
    }

    /**
     * Enumerates file operations for loading and saving configurations.
     */
    enum FileOperation {
        LOAD,
        SAVE
    }

    /**
     * Initializes the GUI components, including buttons, panels, and image displays.
     *
     * @throws IOException if an I/O error occurs while loading images.
     */
    private void initializeComponents() {
        startGrid = new JPanel(new GridLayout(100, 100));
        int gridSize = 100;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JPanel panel = new JPanel();
                if (i == gridSize / 2 && j == gridSize / 2)
                    panel.setBackground(Color.BLACK);
                else
                    panel.setBackground(Color.WHITE);
                startGrid.add(panel);
            }
        }
        runButton = new JButton("Futtatás!");
        runButton.addActionListener(new RunButton());

        loadButton = new JButton("Betöltés!");
        loadButton.addActionListener(new FileButton(this, FileOperation.LOAD));
        saveButton = new JButton("Mentés!");
        saveButton.addActionListener(new FileButton(this, FileOperation.SAVE));
        startGridButton = new JButton("Kezdőrács!");
        startGridButton.addActionListener(new StartGridButton());

        String[] states = {"Állapot: 0, Cellaérték: 0", "Állapot: 0, Cellaérték: 1", "Állapot: 1, Cellaérték: 0", "Állapot: 1, Cellaérték: 1"};

        for (int i = 0; i < 4; i++) {
            addStatePanel(states[i], i);
        }
    }

    /**
     * Adds a panel for a specific state configuration to the configuration panel.
     *
     * @param stateLabel the label representing the state configuration.
     * @param index      the index of the state configuration.
     */
    private void addStatePanel(String stateLabel, int index) {
        JPanel statePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        directionFields[index] = new JComboBox<>(new Character[]{'N', 'L', 'R', 'U'});
        directionFields[index].setSelectedIndex(0);
        newDirectionFields[index] = new JComboBox<>(new Integer[]{0, 1});
        newDirectionFields[index].setSelectedIndex(0);
        newColorFields[index] = new JComboBox<>(new Integer[]{0, 1});
        newColorFields[index].setSelectedIndex(0);

        statePanel.add(new JLabel(stateLabel + ": Irány: "));
        statePanel.add(directionFields[index]);
        statePanel.add(new JLabel("Új cellaérték:"));
        statePanel.add(newColorFields[index]);
        statePanel.add(new JLabel("Új állapot: "));
        statePanel.add(newDirectionFields[index]);
        configPanel.add(statePanel);

    }

    /**
     * Arranges the GUI components in the main frame using layout managers.
     *
     * @throws IOException if an I/O error occurs while arranging components.
     */
    private void arrangeComponents() throws IOException {
        setLayout(new BorderLayout());

        JPanel imgPanel = new JPanel();
        imgPanel.setLayout(new GridLayout(2, 1));

        menuPanel.setLayout(new GridLayout(2, 1));
        configPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JPanel runPanel = new JPanel();
        runPanel.add(runButton);
        runPanel.add(loadButton);
        runPanel.add(saveButton);
        runPanel.add(startGridButton);

        BufferedImage turmite1 = ImageIO.read(new File("turmite.png"));
        JLabel turmite1Label = new JLabel(new ImageIcon(turmite1));
        imgPanel.add(turmite1Label);
        BufferedImage turmite2 = ImageIO.read(new File("turmite2.png"));
        JLabel turmite2Label = new JLabel(new ImageIcon(turmite2));
        imgPanel.add(turmite2Label);

        configPanel.add(loadButton);
        configPanel.add(saveButton);
        configPanel.add(startGridButton);

        menuPanel.add(imgPanel);
        menuPanel.add(configPanel);

        add(menuPanel, BorderLayout.NORTH);
        add(runPanel, BorderLayout.CENTER);
    }

    /**
     * Retrieves an array of SelectedValues based on the user-selected configurations.
     *
     * @return an array of SelectedValues representing user configurations.
     */
    SelectedValues[] getAllSelectedValues() {
        SelectedValues[] valueArray = new SelectedValues[4];
        int[] states = {0, 0, 1, 1};
        int[] colors = {0, 1, 0, 1};
        for (int i = 0; i < 4; i++) {
            char direction = (Character) directionFields[i].getSelectedItem();
            int newState = (Integer) newDirectionFields[i].getSelectedItem();
            int newColor = (Integer) newColorFields[i].getSelectedItem();

            valueArray[i] = new SelectedValues(states[i], colors[i], direction, newState, newColor);
        }
        return valueArray;
    }

    /**
     * ActionListener for the "Run" button, initiates the Turmite simulation.
     */
    class RunButton implements java.awt.event.ActionListener {
        public void actionPerformed(ActionEvent e) {
            Turmite turmite = new Turmite(getAllSelectedValues(), MainMenu.this);
            dispose();
            turmite.setVisible(true);
        }
    }

    /**
     * ActionListener for the "Start Grid" button, opens the StartGrid configuration window.
     */
    class StartGridButton implements java.awt.event.ActionListener {
        public void actionPerformed(ActionEvent e) {
            StartGrid startGridFrame = new StartGrid(MainMenu.this);
            startGridFrame.setVisible(true);
        }
    }

    /**
     * Sets the start grid panel for the Turmite simulation.
     *
     * @param startGrid the panel representing the start grid.
     */
    public void setStartGrid(JPanel startGrid) {
        this.startGrid = startGrid;
    }

    /**
     * Retrieves the start grid panel for the Turmite simulation.
     *
     * @return the panel representing the start grid.
     */
    public JPanel getStartGrid() {
        return startGrid;
    }

    /**
     * ActionListener for file-related operations (Load and Save).
     */
    class FileButton implements java.awt.event.ActionListener {
        private MainMenu parent;
        private FileOperation operation;

        /**
         * Constructs a FileButton with the specified parent and file operation.
         *
         * @param parent    the parent MainMenu instance.
         * @param operation the file operation to be performed.
         */
        public FileButton(MainMenu parent, FileOperation operation) {
            this.parent = parent;
            this.operation = operation;
        }

        /**
         * Handles the action event triggered by the user for file-related operations.
         *
         * @param e the ActionEvent representing the user's action.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser("./saves");
            fileChooser.setDialogTitle((operation == FileOperation.LOAD) ? "Load SelectedValues File" : "Save SelectedValues File");
            int userSelection;

            if (operation == FileOperation.LOAD) {
                userSelection = fileChooser.showOpenDialog(null);
            } else {
                userSelection = fileChooser.showSaveDialog(null);
            }

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                if (operation == FileOperation.LOAD) {
                    loadFile(file);
                } else {
                    saveFile(file);
                }
            }
        }

        /**
         * Loads configurations from a selected file.
         *
         * @param fileToLoad the file from which configurations should be loaded.
         */
        private void loadFile(File fileToLoad) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToLoad))) {
                SaveData saveData = (SaveData) ois.readObject();
                SelectedValues[] loadedValues = saveData.values();

                for (int i = 0; i < 4; i++) {
                    directionFields[i].setSelectedItem(loadedValues[i].direction);
                    newDirectionFields[i].setSelectedItem(loadedValues[i].newState);
                    newColorFields[i].setSelectedItem(loadedValues[i].newColor);
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

        /**
         * Saves configurations to a selected file.
         *
         * @param fileToSave the file to which configurations should be saved.
         */
        private void saveFile(File fileToSave) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileToSave))) {
                SaveData saveData = new SaveData(parent.getAllSelectedValues());
                oos.writeObject(saveData);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}


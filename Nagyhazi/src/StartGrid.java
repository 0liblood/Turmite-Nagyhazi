import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The StartGrid class represents a JFrame displaying a grid that can be interactively modified.
 * It is typically used in conjunction with the MainMenu class.
 */
public class StartGrid extends JFrame {

    /**
     * The gridPanel is a static variable representing the main panel containing the grid.
     */
    private static JPanel gridPanel;

    /**
     * Constructs a StartGrid object with the specified MainMenu.
     *
     * @param mainMenu The MainMenu instance associated with this StartGrid.
     */
    public StartGrid(MainMenu mainMenu) {
        gridPanel = mainMenu.getStartGrid();
        Component[] components = gridPanel.getComponents();
        int gridSize = 100;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                JPanel panel = (JPanel) components[i * gridSize + j];
                panel.addMouseListener(new PanelClickListener(panel));
            }
        }

        add(gridPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Kezdeti Rács");
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
        ConfirmButton confirmButton = new ConfirmButton(mainMenu);
        add(confirmButton, BorderLayout.SOUTH);
    }

    /**
     * PanelClickListener is a private class implementing the MouseAdapter interface
     * to handle mouse click events on the panels in the grid.
     */
    private static class PanelClickListener extends MouseAdapter {
        private final JPanel panel;

        /**
         * Constructs a PanelClickListener with the specified panel.
         *
         * @param panel The JPanel to be associated with this listener.
         */
        public PanelClickListener(JPanel panel) {
            this.panel = panel;
        }

        /**
         * Invoked when a mouse button has been clicked on a component.
         * Toggles the background color of the associated panel.
         *
         * @param e The MouseEvent that occurred.
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            Color currentColor = panel.getBackground();
            panel.setBackground(currentColor.equals(Color.WHITE) ? Color.BLACK : Color.WHITE);
        }
    }

    /**
     * Gets the static gridPanel.
     *
     * @return The gridPanel representing the main panel containing the grid.
     */
    public static JPanel getGridPanel() {
        return gridPanel;
    }

    /**
     * ConfirmButton is a private class extending JButton and implementing ActionListener
     * to handle the "Megerősítés" button.
     */
    class ConfirmButton extends JButton implements java.awt.event.ActionListener {
        private final MainMenu mainMenu;

        /**
         * Constructs a ConfirmButton with the specified MainMenu.
         *
         * @param mainMenu The MainMenu instance associated with this ConfirmButton.
         */
        public ConfirmButton(MainMenu mainMenu) {
            super("Megerősítés");
            this.mainMenu = mainMenu;
            addActionListener(this);
        }

        /**
         * Invoked when the "Megerősítés" button is clicked.
         * Updates the gridPanel in MainMenu and removes the mouse listeners from the panels.
         *
         * @param e The ActionEvent that occurred.
         */
        public void actionPerformed(ActionEvent e) {
            mainMenu.setStartGrid(StartGrid.getGridPanel());
            gridPanel = mainMenu.getStartGrid();
            Component[] components = gridPanel.getComponents();
            int gridSize = 100;
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    JPanel panel = (JPanel) components[i * gridSize + j];
                    panel.removeMouseListener(panel.getMouseListeners()[0]);
                }
            }
            dispose();
        }
    }
}

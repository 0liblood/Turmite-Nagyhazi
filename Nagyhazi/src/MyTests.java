import org.junit.Test;

import javax.swing.*;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * MyTests class contains JUnit tests for the Turmite program components.
 */
public class MyTests {

    /**
     * Tests the initialization of the MainMenu.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testMainMenuInitialization() throws IOException {
        MainMenu mainMenu = new MainMenu();
        assertNotNull(mainMenu);
    }

    /**
     * Tests the initialization of the StartGrid.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testStartGridInitialization() throws IOException {
        MainMenu mainMenu = new MainMenu();
        StartGrid startGrid = new StartGrid(mainMenu);
        assertNotNull(startGrid);
    }

    /**
     * Tests the initialization of the Turmite.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testTurmiteInitialization() throws IOException {
        MainMenu mainMenu = new MainMenu();
        Turmite turmite = new Turmite(new SelectedValues[4], mainMenu);
        assertNotNull(turmite);
    }

    /**
     * Tests the setStartGrid method in MainMenu.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testMainMenuSetStartGrid() throws IOException {
        MainMenu mainMenu = new MainMenu();
        JPanel gridPanel = new JPanel();
        mainMenu.setStartGrid(gridPanel);
        assertEquals(gridPanel, mainMenu.getStartGrid());
    }

    /**
     * Tests the getAllSelectedValues method in MainMenu.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testMainMenuGetAllSelectedValues() throws IOException {
        MainMenu mainMenu = new MainMenu();
        SelectedValues[] values = mainMenu.getAllSelectedValues();
        assertNotNull(values);
        assertEquals(4, values.length);
    }

    /**
     * Tests the getGridPanel method in StartGrid.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testStartGridGetGridPanel() throws IOException {
        MainMenu mainMenu = new MainMenu();
        StartGrid startGrid = new StartGrid(mainMenu);
        assertNotNull(startGrid.getGridPanel());
    }

    /**
     * Tests the ConfirmButtonActionPerformed method in StartGrid.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testStartGridConfirmButtonActionPerformed() throws IOException {
        MainMenu mainMenu = new MainMenu();
        StartGrid.ConfirmButton confirmButton = new StartGrid(mainMenu).new ConfirmButton(mainMenu);
        confirmButton.actionPerformed(null);
        assertEquals(mainMenu.getStartGrid(), StartGrid.getGridPanel());
    }

    /**
     * Tests the turn method in Turmite.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testTurmiteTurn() throws IOException {
        MainMenu mainMenu = new MainMenu();
        Turmite turmite = new Turmite(new SelectedValues[4], mainMenu);
        turmite.turn('L');
        assertEquals(3, turmite.currentDir);
    }

    /**
     * Tests the move method in Turmite.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testTurmiteMove() throws IOException {
        MainMenu mainMenu = new MainMenu();
        Turmite turmite = new Turmite(new SelectedValues[4], mainMenu);

        int initialRow = turmite.row;
        int initialCol = turmite.col;

        turmite.move();

        if (turmite.currentDir == 0) {
            assertEquals(initialCol - 1, turmite.col);
            assertEquals(initialRow, turmite.row);
        }
        else if (turmite.currentDir == 1) {
            assertEquals(initialCol, turmite.col);
            assertEquals(initialRow + 1, turmite.row);
        }
        else if (turmite.currentDir == 2) {
            assertEquals(initialCol + 1, turmite.col);
            assertEquals(initialRow, turmite.row);
        }
        else if (turmite.currentDir == 3) {
            assertEquals(initialCol, turmite.col);
            assertEquals(initialRow - 1, turmite.row);
        }
    }

    /**
     * Tests the StartGridButtonActionPerformed method in MainMenu.
     *
     * @throws IOException if an I/O error occurs.
     */
    @Test
    public void testMainMenuStartGridButtonActionPerformed() throws IOException {
        MainMenu mainMenu = new MainMenu();
        MainMenu.StartGridButton startGridButton = mainMenu.new StartGridButton();
        startGridButton.actionPerformed(null);
        assertNotNull(mainMenu.getStartGrid());
    }
}

import javax.swing.*;
import java.io.IOException;

/**
 * The Main class contains the main method to start the Turmite program.
 */
public class Main {
    public static void main(String[] args) {
        // Run JUnit tests
        org.junit.runner.JUnitCore.main("MyTests");

        SwingUtilities.invokeLater(() -> {
            try {
                new MainMenu().setVisible(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


}

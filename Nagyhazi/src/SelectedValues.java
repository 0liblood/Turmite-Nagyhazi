import java.io.Serializable;

/**
 * The SelectedValues class represents the configuration of a Turmite state.
 * It implements the Serializable interface to support object serialization.
 */
public class SelectedValues implements Serializable {
    int state;
    int color;
    char direction;
    int newState;
    int newColor;

    /**
     * Constructs a SelectedValues object with the specified parameters.
     *
     * @param state    The current state of the Turmite.
     * @param color    The color of the cell at the Turmite's current position.
     * @param direction The direction in which the Turmite should move.
     * @param newState The new state to transition to based on the current state and cell color.
     * @param newColor The new color to set the cell at the Turmite's current position.
     */
    public SelectedValues(int state, int color, char direction, int newState, int newColor) {
        this.state = state;
        this.color = color;
        this.direction = direction;
        this.newState = newState;
        this.newColor = newColor;
    }
}
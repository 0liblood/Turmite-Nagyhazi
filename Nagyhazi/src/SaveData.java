import java.io.Serializable;

/**
 * The SaveData class represents the data structure for saving and loading Turmite configurations.
 * It implements the Serializable interface to support object serialization.
 */
public record SaveData(SelectedValues[] values) implements Serializable {
}

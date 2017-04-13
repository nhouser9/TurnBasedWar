package gui;

import javafx.scene.control.Alert;

/**
 * Extension of Alert which contains a default title and header and is used for
 * displaying error information.
 *
 * @author Nick Houser
 */
public class ErrorAlert extends Alert {

    /**
     * Constructor which initializes the Alert.
     *
     * @param message the error message to display
     */
    public ErrorAlert(String message) {
        super(AlertType.ERROR);
        setTitle("Error");
        setHeaderText("Error performing the selected action");
        setContentText(message);
    }
}

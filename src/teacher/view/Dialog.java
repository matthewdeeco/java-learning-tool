package teacher.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import static javax.swing.JOptionPane.*;

/** Utility dialogs to input data into the program. */
public class Dialog {
	private static JFrame parent;

	private Dialog() {
	}

	public static void setParent(JFrame frame) {
		parent = frame;
	}

	/** Asks the user to input a string with the default message.
	 * @return a non-empty string. */
	public static String inputString() {
		return inputString("Enter a sequence of characters: ");
	}

	/** Asks the user to input a string.
	 * @param message the desired message to be displayed.
	 * @return a non-empty string. */
	public static String inputString(String message) {
		while (true) {
			try {
				String currentString = showInputDialog(parent, message, "Input", QUESTION_MESSAGE);
				if (!currentString.isEmpty()) {
					return currentString;
				}
				errorMessage("Your input was invalid!");
			} catch (NullPointerException ex) {
				return null;
			}
		}
	}

	/** Asks the user to input a string with a maximum length.
	 * @param message the desired message to be displayed.
	 * @param maxLength the maximum number of characters in the string.
	 * @return a string with length less than or equal to the maxLength. */
	public static String inputString(String message, int maxLength) {
		while (true) {
			String currentString = inputString(message + " (" + maxLength + " letters max)");

			if (currentString == null)
				return null;
			else if (currentString.length() <= maxLength) {
				return currentString;
			} else
				errorMessage("Length exceeded " + maxLength + " letters!");
		}
	}

	/** Prompts the user with a yes / no option.
	 * @param message the message to be displayed.
	 * @return true if yes, false if no. */
	public static boolean confirmYesNo(String message) {
		return showConfirmDialog(parent, message, "Confirm", YES_NO_OPTION, WARNING_MESSAGE) == YES_OPTION;
	}

	/** Prompts the user with a yes / no option.
	 * @param message the message to be displayed.
	 * @param icon the icon to be displayed.
	 * @return true if yes, false if no. */
	public static boolean confirmYesNo(String message, Icon icon) {
		return showConfirmDialog(parent, message, "Confirm", YES_NO_OPTION, PLAIN_MESSAGE, icon) == YES_OPTION;
	}

	/** Gets user input as save file path.
	 * @param description the save file description.
	 * @param format the save file format.
	 * @return the save file path string, null if the operation was aborted. */
	public static String inputSaveFilePath(String description, String format) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(description, format));
		int userChoice = fileChooser.showSaveDialog(parent);

		if (fileChooser.getSelectedFile() == null || userChoice == JFileChooser.CANCEL_OPTION)
			return null;
		else
			return fileChooser.getSelectedFile().getAbsolutePath();
	}

	/** Gets user input as load file path. 
	 * @param description the save file description.
	 * @param format the save file format.
	 * @return the save file path string, null if the operation was aborted. */
	public static String inputLoadFilePath(String description, String format) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileNameExtensionFilter(description, format));
		int userChoice = fileChooser.showOpenDialog(parent);

		if (fileChooser.getSelectedFile() == null || userChoice == JFileChooser.CANCEL_OPTION)
			return null;
		else
			return fileChooser.getSelectedFile().getAbsolutePath();
	}

	/** Displays a plain message dialog.
	 * @param message the message to be displayed. */
	public static void message(String message) {
		showMessageDialog(parent, message, "Message", PLAIN_MESSAGE);
	}

	/** Displays a plain message dialog.
	 * @param message the message to be displayed.
	 * @param icon the icon to be displayed. */
	public static void message(String message, ImageIcon icon) {
		showMessageDialog(parent, message, "Message", PLAIN_MESSAGE, icon);
	}

	/** Displays an information dialog.
	 * @param message the message to be displayed. */
	public static void infoMessage(String message) {
		showMessageDialog(parent, message, "Info", INFORMATION_MESSAGE);
	}

	/** Displays an error dialog.
	 * @param message the message to be displayed. */
	public static void errorMessage(String message) {
		showMessageDialog(parent, message, "Error", ERROR_MESSAGE);
	}
}

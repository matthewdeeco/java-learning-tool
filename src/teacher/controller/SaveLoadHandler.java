package teacher.controller;

import java.io.*;

import teacher.model.Module;
import teacher.view.DialogHandler;

public class SaveLoadHandler {
	private static final String FILE_EXTENSION = "lm";
	private static final String DESCRIPTION = "Learning Module (*." + FILE_EXTENSION + ")";
	private DialogHandler dialogHandler;
	private String lastSavePath = null;
	private String lastLoadPath = null;
	
	public void saveState(Module model) {
		if (lastSavePath == null)
			saveStateAs(model);
		else
			saveState(model, lastSavePath);
	}
	
	public void saveStateAs(Module model) {
		String filePath = dialogHandler.inputSaveFilePath(DESCRIPTION, FILE_EXTENSION);
		if (filePath != null) {
			filePath = addFileExtensionIfNotThere(filePath);
			lastSavePath = filePath;
			saveState(model, filePath);
		}
	}

	private void saveState(Module model, String filePath) {
		ObjectOutputStream writer = null;
		try {
			writer = new ObjectOutputStream(new FileOutputStream(filePath));
			writer.writeObject(model);
		} catch (Exception ex) {
			String message = "Error saving the module!\n" + "Make sure you have the permissions "
					+ "to save to that folder.";
			dialogHandler.errorMessage(message);
			ex.printStackTrace();
		} finally {
			tryToClose(writer);
		}
	}

	private String addFileExtensionIfNotThere(String filePath) {
		if (!filePath.endsWith("." + FILE_EXTENSION))
			filePath += "." + FILE_EXTENSION;
		return filePath;
	}

	private void tryToClose(Closeable closeable) {
		try {
			if (closeable != null)
				closeable.close();
		} catch (IOException ex) {
		}
	}

	public Module loadState() {
		String filePath = dialogHandler.inputLoadFilePath(DESCRIPTION, FILE_EXTENSION);
		if (filePath == null)
			return null;
		else
			return loadState(filePath);
	}

	private Module loadState(String filePath) {
		ObjectInputStream reader = null;
		Module loadedGameModel = null;
		try {
			reader = new ObjectInputStream(new FileInputStream(filePath));
			loadedGameModel = (Module) reader.readObject();
			lastLoadPath = filePath;
			return loadedGameModel;
		} catch (Exception ex) {
			String message = "Error loading your save file!\n" + "Make sure that your module is in the \n"
					+ "correct format and that the file exists.";
			dialogHandler.errorMessage(message);
		} finally {
			tryToClose(reader);
		}
		return loadedGameModel;
	}

	public void setDialogHandler(DialogHandler dialogHandler) {
		this.dialogHandler = dialogHandler;
	}

	public String getLastSavePath() {
		return lastSavePath;
	}

	public void setLastSavePath(String lastSavePath) {
		this.lastSavePath = lastSavePath;
	}
	
	public String getLastLoadPath() {
		return lastLoadPath;
	}
}

package teacher.controller;

import javax.swing.SwingUtilities;

import interpreter.*;
import teacher.model.*;
import teacher.model.Slide.Type;
import teacher.view.*;

abstract class ModuleControllerImp implements ModuleController {
	private Module module;
	private ModuleViewer viewer;
	private SaveLoadHandler saveLoadHandler;
	private DialogHandler dialogHandler;
	
	public ModuleControllerImp(Module module) {
		this.module = module;
		saveLoadHandler = new SaveLoadHandler();
	}
	
	public void setModuleViewer(ModuleViewer viewer) {
		this.viewer = viewer;
	}
	
	public void setDialogHandler(DialogHandler dialogHandler) {
		this.dialogHandler = dialogHandler;
		saveLoadHandler.setDialogHandler(dialogHandler);
	}
	
	public abstract boolean isAdmin();
	
	public void saveModule() {
		saveChanges();
		saveLoadHandler.saveState(module);
		viewer.moduleChanged();
	}

	@Override
	public void saveModuleAs() {
		saveChanges();
		saveLoadHandler.saveStateAs(module);
		viewer.moduleChanged();
	}
	
	public void loadModule() {
		Module newModule = saveLoadHandler.loadState();
		if (newModule != null) {
			if (module.hasModuleLoaded()) {
				newModule.resetObservers();
				openInNewWindow(newModule, saveLoadHandler.getLastLoadPath());
			}
			else {
				setLastSavePath(saveLoadHandler.getLastLoadPath());
				module.loadModule(newModule);
			}
		}
	}
	
	public String getLastSavePath() {
		return saveLoadHandler.getLastSavePath();
	}

	public void createNewModule() {
		String title = dialogHandler.inputString("Enter the module name: ");
		if (title != null) {
			Module newModule = new Module(title);
			if (module.hasModuleLoaded())
				openInNewWindow(newModule, null);
			else
				module.loadModule(newModule);
		}
	}
	
	private void openInNewWindow(final Module module, final String savePath) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ModuleController newController = createSameLevelController(module);
				newController.setLastSavePath(savePath);
				ModuleViewer newViewer = new ModuleViewer(module, newController);
			}
		});
	}
	
	/** Same level means same admin rights. */
	private ModuleController createSameLevelController(Module module) {
		if (isAdmin())
			return new TeacherController(module);
		else
			return new StudentController(module);
	}

	public void createNewTopic() {
		String title = dialogHandler.inputString("Enter the topic name: ");
		if (title != null)
			module.createNewTopic(title);
	}

	public void createNewSlide() {
		saveChanges();
		module.createNewSlide();
	}
	
	public void createNewSlide(Type selectedType) {
		saveChanges();
		module.createNewSlide(selectedType);
	}

	public void deleteCurrentSlide() {
		module.deleteCurrentSlide();
	}
	
	public void exit() {
		boolean shouldExit = !module.hasModuleLoaded() || dialogHandler.confirmYesNo("Are you sure you want to quit?");
		if (shouldExit)
			viewer.dispose();
	}
	
	public void displayHelp() {
		dialogHandler.errorMessage("I AM ERROR");
	}
	
	public void displayAbout() {
		dialogHandler.infoMessage("Learning Module Creator\n" +
				"by: Matthew Co and Harvey Arbas");
	}
	
	/** @return the toString of the Object returned by the method */
	public String testCode(String classCode, String testCode) {
		try {
			CodeBlock code = new CodeBlock(classCode);
			Object result = code.getTestResult(testCode);
			if (result instanceof Boolean) {
				if ((Boolean)result == true)
					return "Correct!!";
				else
					return "Sorry, try again.";
			}
			else
				return "Result is: " + result.toString();
		} catch (CodeException ex) {
			return "Exception thrown from the code:\n" + ex.getMessage();
		} catch (ParseException ex) {
			return "Parsing exception:\n" + ex.getMessage();
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}

	/** Check the user's answers to true or false. */
	public String checkTofAnswers(Object[] correctAnswers, Boolean[] userAnswers) {
		int nCorrect = 0;
		for (int i = 0; i < correctAnswers.length; i++)
			if ((Boolean)correctAnswers[i] == userAnswers[i])
				nCorrect++;
		return String.format("You got %d/%d questions correct!", nCorrect, correctAnswers.length);
	}

	@Override
	public void renameModule() {
		String title = dialogHandler.inputString("Enter new name: ");
		module.renameModule(title);
	}

	@Override
	public void renameCurrentTopic() {
		String title = dialogHandler.inputString("Enter new name: ");
		module.renameCurrentTopic(title);
	}

	@Override
	public void deleteCurrentTopic() {
		if (dialogHandler.confirmYesNo("Delete current topic?\nAll slides in the topic will be deleted."))
			module.deleteCurrentTopic();
	}

	@Override
	public void nextTopic() {
		module.nextTopic();
	}

	@Override
	public void previousTopic() {
		module.previousTopic();
	}

	public void setTopicByIndex(int index) {
		saveChanges();
		module.setTopicByIndex(index);
	}

	public void previousSlide() {
		saveChanges();
		module.previousSlide();
	}
	
	public void nextSlide() {
		saveChanges();
		module.nextSlide();
	}

	private void saveChanges() {
		module.setCurrentSlideText(viewer.getCurrentSlideText());
	}
	
	public void setLastSavePath(String lastSavePath) {
		saveLoadHandler.setLastSavePath(lastSavePath);
	}
}
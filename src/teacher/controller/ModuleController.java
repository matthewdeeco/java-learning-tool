package teacher.controller;

import interpreter.*;
import teacher.model.*;
import teacher.model.Slide.Type;
import teacher.view.*;

public abstract class ModuleController {
	private Module module;
	private ModuleViewer viewer;
	private SaveLoadHandler saveLoadHandler;
	
	public ModuleController(Module module) {
		this.module = module;
		saveLoadHandler = new SaveLoadHandler();
	}
	
	public void setModuleViewer(ModuleViewer viewer) {
		this.viewer = viewer;
	}
	
	public abstract boolean isAdmin();

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
	
	public void saveModule() {
		saveChanges();
		saveLoadHandler.saveState(module);
	}
	
	public void loadModule() {
		Module newModule = saveLoadHandler.loadState();
		if (newModule != null) {
			module.loadModule(newModule);
		}
	}

	public void createNewModule() {
		String title = Dialog.inputString("Enter the module name: ");
		if (title != null) {
			Module newModule = new Module(title);
			module.loadModule(newModule);
		}
	}

	public void createNewSubtopic() {
		String title = Dialog.inputString("Enter the topic name: ");
		if (title != null)
			module.createNewTopic(title);
	}

	public void createNewSlide() {
		saveChanges();
		SlideTypeDialog dialog = new SlideTypeDialog();
		Type selectedType = dialog.getSelectedType();
		if (selectedType != null)
			module.createNewSlide(selectedType);
	}

	public void deleteCurrentSlide() {
		module.deleteCurrentSlide();
	}

	private void saveChanges() {
		module.setCurrentSlideText(viewer.getCurrentSlideText());
	}
	
	public void exit() {
		if (Dialog.confirmYesNo("Are you sure you want to quit?"))
			System.exit(0);
	}
	
	public void displayHelp() {
		Dialog.errorMessage("I AM ERROR");
	}
	
	public void displayAbout() {
		Dialog.infoMessage("Learning Module Creator\n" +
				"by: Matthew Co and Harvey Arbas");
	}
	
	/** @return the toString of the Object returned by the method */
	public String runMethod(String codeBlock, String methodName) {
		try {
			CodeBlock code = new CodeBlock(codeBlock);
			Object result = code.runMethod(methodName);
			return "Result is: " + result.toString();
		} catch (CodeException ex) {
			return "Exception thrown from the code:\n" + ex.getMessage();
		} catch (ParseException ex) {
			return "Parsing exception:\n" + ex.getMessage();
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}
}
package teacher.controller;

import teacher.model.Slide;
import teacher.view.DialogHandler;
import teacher.view.ModuleViewer;

public interface ModuleController {
	public boolean isAdmin();
	
	public void createNewModule();
	public void renameModule();
	public void saveModule();
	public void saveModuleAs();
	public void loadModule();
	public String getLastSavePath();
	public void exit();
	
	public void createNewTopic();
	public void renameCurrentTopic();
	public void deleteCurrentTopic();
	public void nextTopic();
	public void previousTopic();
	
	public void createNewSlide();
	public void createNewSlide(Slide.Type selectedType);
	public void deleteCurrentSlide();
	public void nextSlide();
	public void previousSlide();

	public String checkTofAnswers(Object[] array, Boolean[] userAnswers);
	public void setTopicByIndex(int index);
	public void setModuleViewer(ModuleViewer moduleViewer);
	public String testCode(String text, String text2);

	public void displayHelp();
	public void displayAbout();

	public void setDialogHandler(DialogHandler dialogHandler);
	public void setLastSavePath(String savePath);
}

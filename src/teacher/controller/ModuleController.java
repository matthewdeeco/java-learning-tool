package teacher.controller;

import teacher.model.CodeBlock;
import teacher.model.Module;
import teacher.view.ModuleViewer;

public class ModuleController {
	private Module model;
	private ModuleViewer view;
	
	public ModuleController(Module module) {
		this.model = module;
	}
	
	public void setView(ModuleViewer moduleViewer) {
		this.view = moduleViewer;
	}
	
	public void displayCodeArea() {
		CodeBlock codeBlock = new CodeBlock("");
		CodeController codeController = new CodeController();
		view.displayCodeArea(codeBlock, codeController);
	}
}
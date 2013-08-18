package teacher.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import teacher.controller.CodeController;
import teacher.controller.ModuleController;
import teacher.model.CodeBlock;
import teacher.model.Module;
import teacher.model.ModuleObserver;

public class ModuleViewer implements ModuleObserver {
	private Module module;
	private Module controller;
	private JFrame frame;
	private JPanel currentSlide;
	
	public ModuleViewer(Module module, ModuleController controller) {
		this.module = module;
		this.controller = this.controller;
		controller.setView(this);
		
		frame = new JFrame(module.getTopic());
		currentSlide = new JPanel();
		setSlideView(currentSlide);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		controller.displayCodeArea();
	}
	
	public void displayCodeArea(CodeBlock codeBlock, CodeController codeController) {
		setSlideView(new CodeArea(codeBlock, codeController));
	}
	
	public void setSlideView(JPanel slideView) {
		frame.getContentPane().remove(currentSlide);
		currentSlide = slideView;
		frame.getContentPane().add(currentSlide, BorderLayout.CENTER);
		frame.pack();
	}

	@Override
	public void moduleChanged() {
	}
}
package teacher.view;

import java.awt.BorderLayout;
import java.util.*;
import javax.swing.*; 

import creator.HtmlEditorPanel;
import teacher.controller.*;
import teacher.model.*;

public class ModuleViewer implements ModuleObserver {
	private Module controller;
	private JFrame frame;
	private SlideViewer slideViewer;
	
	public ModuleViewer(Module module, ModuleController controller) {
		this.controller = this.controller;
		controller.setView(this);
		
		frame = new JFrame(module.getTopic());
		slideViewer = new SlideViewer();
		frame.getContentPane().add(slideViewer, BorderLayout.CENTER);
		
		List<String> subtopics = new ArrayList<String>();
		subtopics.add("Subtopic 1");
		subtopics.add("Subtopic 2");
		SubtopicChooser chooser = new SubtopicChooser(subtopics);
		frame.getContentPane().add(chooser, BorderLayout.WEST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		controller.displayCodeArea();
	}
	
	public void displayCodeArea(CodeBlock codeBlock, CodeController codeController) {
		//setSlideView(new JavaEditorPanel(codeBlock, codeController));
		setSlideView(new HtmlEditorPanel());
	}
	
	public void setSlideView(JPanel slideView) {
		slideViewer.setSlideView(slideView);
	}

	@Override
	public void moduleChanged() {
	}
}
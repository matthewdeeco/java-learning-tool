package teacher.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import teacher.controller.ModuleController;
import teacher.model.*;

/** Controls the slide viewer through navigation buttons. */  
public class SlideNavigator extends JPanel implements ModuleObserver {
	private final Dimension size = new Dimension(650, 450);
	
	private ModuleReadOnly module;
	private ModuleController controller;
	
	private SlideViewer slideViewer;
	private JButton prevButton;
	private JButton nextButton;
	private JButton newSlideButton;
	private JButton deleteSlideButton;
	
	public SlideNavigator(ModuleReadOnly module, ModuleController controller) {
		this.module = module;
		module.registerObserver(this);
		this.controller = controller;
		
		setLayout(new BorderLayout());
		
		prevButton = new JButton("<<");
		prevButton.addActionListener(new NavigationListener());
		nextButton = new JButton(">>");
		nextButton.addActionListener(new NavigationListener());
		
		newSlideButton = new JButton("New slide");
		newSlideButton.addActionListener(new NewSlideListener());
		deleteSlideButton = new JButton("Delete slide");
		deleteSlideButton.addActionListener(new DeleteSlideListener());
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
		buttonPanel.add(prevButton);
		buttonPanel.add(nextButton);
		
		if (controller.isAdmin()) {
			buttonPanel.add(newSlideButton);
			buttonPanel.add(deleteSlideButton);
		}
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		slideViewer = new SlideViewer(module, controller);
		add(slideViewer, BorderLayout.CENTER);
		moduleChanged();
	}
	
	public void setSlide(SlideViewer slide) {
		if (slideViewer != null) {
			remove(slideViewer);
		}
		slideViewer = slide;
		add(slideViewer, BorderLayout.CENTER);
		repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return size;
	}
	
	@Override
	public Dimension getMaximumSize() {
		return size;
	}
	
	@Override
	public Dimension getMinimumSize() {
		return size;
	}

	@Override
	public void moduleChanged() {
		if (module.hasNextSlide())
			nextButton.setEnabled(true);
		else
			nextButton.setEnabled(false);
		
		if (module.hasPreviousSlide())
			prevButton.setEnabled(true);
		else
			prevButton.setEnabled(false);
		
		if (module.canDeleteCurrentSlide())
			deleteSlideButton.setEnabled(true);
		else
			deleteSlideButton.setEnabled(false);
	}
	
	public String getText() {
		return slideViewer.getText();
	}
	
	class NavigationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == prevButton)
				controller.previousSlide();
			else
				controller.nextSlide();
		}
	}
	
	class NewSlideListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.createNewSlide();
		}
	}

	class DeleteSlideListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.deleteCurrentSlide();
		}
	}
}

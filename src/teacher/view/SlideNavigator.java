package teacher.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import teacher.controller.ModuleController;
import teacher.model.*;

/** Controls the slide viewer through navigation buttons. */  
public class SlideNavigator extends JPanel implements ModuleObserver {
	private final Dimension size = new Dimension(650, 450);
	private final Color prevButtonColor = new Color(0x2290C1);
	private final Color nextButtonColor = new Color(0xA022D1);
	
	private ModuleReadOnly module;
	private ModuleController controller;
	
	private SlideViewer slideViewer;
	private JButton prevButton;
	private JButton nextButton;
	
	public SlideNavigator(ModuleReadOnly module, ModuleController controller, DialogHandler dialogHandler) {
		this.module = module;
		module.registerObserver(this);
		this.controller = controller;
		
		setLayout(new BorderLayout());
		
		prevButton = new JButton("<<");
		prevButton.setBackground(prevButtonColor);
		prevButton.addActionListener(new NavigationListener());
		nextButton = new JButton(">>");
		nextButton.setBackground(nextButtonColor);
		nextButton.addActionListener(new NavigationListener());
		
		JPanel buttonPanel = new JPanel(new GridLayout(1, 4));
		buttonPanel.add(prevButton);
		buttonPanel.add(nextButton);
		
		add(buttonPanel, BorderLayout.SOUTH);
		
		slideViewer = new SlideViewer(module, controller, dialogHandler);
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
		prevButton.setEnabled(module.hasPreviousSlide());
		nextButton.setEnabled(module.hasNextSlide());
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
}

package teacher.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.*;

public class SlideViewer extends JPanel {
	private final Dimension size = new Dimension(600, 450);
	
	private JPanel currentSlide;
	
	public SlideViewer() {
		setLayout(new BorderLayout());
		currentSlide = new JPanel();
		
		JButton prevButton = new JButton("<<");
		JButton nextButton = new JButton(">>");
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		buttonPanel.add(prevButton);
		buttonPanel.add(nextButton);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public void setSlideView(JPanel slideView) {
		remove(currentSlide);
		currentSlide = slideView;
		add(currentSlide, BorderLayout.CENTER);
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
}

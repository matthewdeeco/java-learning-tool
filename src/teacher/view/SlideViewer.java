package teacher.view;

import javax.swing.*;

public class SlideViewer extends JPanel {
	private JPanel slideView;
	
	public void setSlideView(JPanel slideView) {
		this.slideView = slideView;
		removeAll();
		add(slideView);
		repaint();
	}
}

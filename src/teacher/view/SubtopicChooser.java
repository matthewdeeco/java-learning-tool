package teacher.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

public class SubtopicChooser extends JPanel implements ActionListener {
	private final Color bgColor = new Color(0xA2A2A2);
	private final Color focusColor = new Color(0x22C190);
	
	private List<JButton> buttons;
	
	public SubtopicChooser(List<String> subtopics) {
		buttons = new ArrayList<JButton>();
		setBackground(bgColor);
		setLayout(new GridLayout(0, 1));
		 
		for (String subtopic: subtopics) {
			JButton button = new JButton(subtopic);
			button.addActionListener(this);
			button.setBorderPainted(false);
			button.setFocusPainted(false);
			buttons.add(button);
			this.add(button);
		}
		setFocusOn(buttons.get(0));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		setFocusOn(source);
	}
	
	private void setFocusOn(JButton button) {
		for (JButton b: buttons)
			b.setBackground(bgColor);
		button.setBackground(focusColor);
	}

}

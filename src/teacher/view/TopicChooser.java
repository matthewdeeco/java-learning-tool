package teacher.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;

import teacher.controller.ModuleController;
import teacher.model.*;

public class TopicChooser extends JPanel implements ActionListener, ModuleObserver {
	private final Color bgColor = new Color(0xA2A2A2);
	private final Color focusColor = new Color(0x50B102);
	
	private ModuleReadOnly module;
	private ModuleController controller;
	private JPanel topicsPanel;
	private SlideTracker slideTracker;
	private List<JButton> topicButtons;
	
	public TopicChooser(ModuleReadOnly module, ModuleController controller) {
		this.module = module;
		module.registerObserver(this);
		this.controller = controller;
		
		setLayout(new BorderLayout());
		setBackground(bgColor);

		topicsPanel = new JPanel(new GridLayout(0, 1));
		topicsPanel.setOpaque(false);
		add(topicsPanel, BorderLayout.CENTER);
		
		slideTracker = new SlideTracker(module);
		add(slideTracker, BorderLayout.SOUTH);
		
		moduleChanged();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		int index = topicButtons.indexOf(source);
		controller.setTopicByIndex(index);
	}
	
	private void setFocusOn(int buttonIndex) {
		for (JButton button: topicButtons)
			button.setBackground(bgColor);
		topicButtons.get(buttonIndex).setBackground(focusColor);
	}

	@Override
	public void moduleChanged() {
		topicsPanel.removeAll();
		topicButtons = new ArrayList<JButton>();
		for (String subtopic: module.getTopics()) {
			JButton button = new JButton(subtopic);
			button.addActionListener(this);
			button.setBorderPainted(false);
			button.setFocusPainted(false);
			topicButtons.add(button);
			topicsPanel.add(button);
		}
		setFocusOn(module.getCurrentTopicIndex());
		revalidate();
		repaint();
	}
}

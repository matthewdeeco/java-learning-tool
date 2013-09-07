package teacher.view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

import teacher.controller.ModuleController;
import teacher.model.ModuleReadOnly;
import teacher.model.Slide;
import teacher.model.Slide.Type;

public class NewSlideMenu extends JMenu implements ActionListener {
	
	private Map<JMenuItem, Type> slideTypes;
	private ModuleController controller;
	
	public NewSlideMenu(ModuleReadOnly module, ModuleController controller) {
		this.controller = controller;
		this.setText("New Slide");
		slideTypes = new HashMap<JMenuItem, Type>();
		for (Slide.Type type: Slide.Type.values()) {
			JMenuItem menuItem = createMenuItem(type.getName(), this);
			slideTypes.put(menuItem, type);
			menuItem.setAccelerator(KeyStroke.getKeyStroke(getKeyEvent(slideTypes.size()), Event.CTRL_MASK));
			/* if (type == Slide.DEFAULT_TYPE)
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, Event.CTRL_MASK)); */
		}
	}
	
	private int getKeyEvent(int index) {
		switch (index) {
			case 1: return KeyEvent.VK_1;
			case 2: return KeyEvent.VK_2;
			case 3: return KeyEvent.VK_3;
			default: return KeyEvent.VK_0;
		}
	}
	
	private JMenuItem createMenuItem(String text, JMenu parent) {
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.addActionListener(this);
		parent.add(menuItem);
		return menuItem;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JMenuItem menuPressed = (JMenuItem)e.getSource();
		Slide.Type selectedType = slideTypes.get(menuPressed);
		controller.createNewSlide(selectedType);
	}
}
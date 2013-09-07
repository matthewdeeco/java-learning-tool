package teacher.view;

import java.awt.Event;
import java.awt.event.*;

import javax.swing.*;
import teacher.controller.ModuleController;
import teacher.model.*;

public class ModuleMenuBar extends JMenuBar implements ActionListener, ModuleObserver {
	private JMenu moduleMenu;
	private JMenu topicMenu;
	private JMenu slideMenu;
	private JMenu settingsMenu;
	private JMenu helpMenu;
	
	private JMenuItem newModuleMenuItem;
	private JMenuItem saveModuleMenuItem;
	private JMenuItem saveModuleAsMenuItem;
	private JMenuItem loadModuleMenuItem;
	private JMenuItem renameModuleMenuItem;
	private JMenuItem exitMenuItem;
	
	private JMenuItem newTopicMenuItem;
	private JMenuItem renameTopicMenuItem;
	private JMenuItem deleteTopicMenuItem;
	private JMenuItem prevTopicMenuItem;
	private JMenuItem nextTopicMenuItem;
	
	private JMenuItem newSlideMenu;
	private JMenuItem deleteSlideMenuItem;
	private JMenuItem prevSlideMenuItem;
	private JMenuItem nextSlideMenuItem;
	
	private JMenuItem helpMenuItem;
	private JMenuItem aboutMenuItem;
	
	private ModuleReadOnly module;
	private ModuleController controller;

	public ModuleMenuBar(ModuleReadOnly module, ModuleController controller) {
		this.module = module;
		module.registerObserver(this);
		this.controller = controller;

		initializeMenus();
		initializeMenuItems();
		moduleChanged();
	}

	private void initializeMenus() {
		moduleMenu = createMenu("Module", this);
		topicMenu = createMenu("Topic", this);
		slideMenu = createMenu("Slide", this);
		// settingsMenu = createMenu("Settings", this);
		helpMenu = createMenu("Help", this);
	}

	private void initializeMenuItems() {
		if (controller.isAdmin()) {
			newModuleMenuItem = createMenuItem("New Module", moduleMenu);
			newModuleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
			
			saveModuleMenuItem = createMenuItem("Save Module", moduleMenu);
			saveModuleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
			
			saveModuleAsMenuItem = createMenuItem("Save Module As..", moduleMenu);
			saveModuleAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK + Event.SHIFT_MASK));
		}
		loadModuleMenuItem = createMenuItem("Load Module", moduleMenu);
		loadModuleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		if (controller.isAdmin())
			renameModuleMenuItem = createMenuItem("Rename Module", moduleMenu);
		exitMenuItem = createMenuItem("Quit", moduleMenu);
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));

		if (controller.isAdmin()) {
			newTopicMenuItem = createMenuItem("New Topic", topicMenu);
			newTopicMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK + Event.SHIFT_MASK));
			deleteTopicMenuItem = createMenuItem("Delete Topic", topicMenu);
			renameTopicMenuItem = createMenuItem("Rename Topic", topicMenu);
		}
		prevTopicMenuItem = createMenuItem("Previous Topic", topicMenu);
		prevTopicMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, Event.ALT_MASK));
		nextTopicMenuItem = createMenuItem("Next Topic", topicMenu);
		nextTopicMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, Event.ALT_MASK));
		
		if (controller.isAdmin()) {
			newSlideMenu = new NewSlideMenu(module, controller);
			slideMenu.add(newSlideMenu);
			
			deleteSlideMenuItem = createMenuItem("Delete Slide", slideMenu);
			deleteSlideMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
		}
		prevSlideMenuItem = createMenuItem("Previous Slide", slideMenu);
		prevSlideMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, Event.ALT_MASK));
		nextSlideMenuItem = createMenuItem("Next Slide", slideMenu);
		nextSlideMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, Event.ALT_MASK));
		
		helpMenuItem = createMenuItem("Help Manual", helpMenu);
		helpMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		
		aboutMenuItem = createMenuItem("About", helpMenu);
	}
	
	private JMenu createMenu(String text, JMenuBar menuBar) {
		JMenu menu = new JMenu(text);
		menuBar.add(menu);

		return menu;
	}

	private JMenuItem createMenuItem(String text, JMenu menu) {
		JMenuItem menuItem = new JMenuItem(text);
		menuItem.addActionListener(this);
		menu.add(menuItem);
		return menuItem;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object menuPressed = e.getSource();

		if (menuPressed == newModuleMenuItem)
			controller.createNewModule();
		else if (menuPressed == saveModuleMenuItem)
			controller.saveModule();
		else if (menuPressed == saveModuleAsMenuItem)
			controller.saveModuleAs();
		else if (menuPressed == loadModuleMenuItem)
			controller.loadModule();
		else if (menuPressed == renameModuleMenuItem)
			controller.renameModule();
		else if (menuPressed == exitMenuItem)
			controller.exit();
		else if (menuPressed == newTopicMenuItem)
			controller.createNewTopic();
		else if (menuPressed == deleteTopicMenuItem)
			controller.deleteCurrentTopic();
		else if (menuPressed == renameTopicMenuItem)
			controller.renameCurrentTopic();
		else if (menuPressed == prevTopicMenuItem)
			controller.previousTopic();
		else if (menuPressed == nextTopicMenuItem)
			controller.nextTopic();
		else if (menuPressed == deleteSlideMenuItem)
			controller.deleteCurrentSlide();
		else if (menuPressed == prevSlideMenuItem)
			controller.previousSlide();
		else if (menuPressed == nextSlideMenuItem)
			controller.nextSlide();
		else if (menuPressed == helpMenuItem)
			controller.displayHelp();
		else if (menuPressed == aboutMenuItem)
			controller.displayHelp();
	}

	@Override
	public void moduleChanged() {
		boolean hasModuleLoaded = module.hasModuleLoaded();
		if (controller.isAdmin()) {
			saveModuleMenuItem.setVisible(hasModuleLoaded);
			saveModuleAsMenuItem.setVisible(hasModuleLoaded);
			renameModuleMenuItem.setVisible(hasModuleLoaded);
			deleteTopicMenuItem.setEnabled(module.canDeleteCurrentTopic());
			deleteSlideMenuItem.setEnabled(module.canDeleteCurrentSlide());
		}
		topicMenu.setVisible(hasModuleLoaded);
		slideMenu.setVisible(hasModuleLoaded);
		
		prevTopicMenuItem.setEnabled(module.hasPreviousTopic());
		nextTopicMenuItem.setEnabled(module.hasNextTopic());
		
		prevSlideMenuItem.setEnabled(module.hasPreviousSlide());
		nextSlideMenuItem.setEnabled(module.hasNextSlide());
	}
}
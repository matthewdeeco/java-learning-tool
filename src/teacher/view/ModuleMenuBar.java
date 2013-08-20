package teacher.view;

import java.awt.Event;
import java.awt.event.*;
import javax.swing.*;

import teacher.controller.ModuleController;
import teacher.model.*;

public class ModuleMenuBar extends JMenuBar implements ActionListener, ModuleObserver {
	private JMenu fileMenu;
	private JMenu settingsMenu;
	private JMenu helpMenu;
	private JMenuItem newModuleMenuItem;
	private JMenuItem saveModuleMenuItem;
	private JMenuItem loadModuleMenuItem;
	private JMenuItem exitMenuItem;
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
	}

	private void initializeMenus() {
		fileMenu = createMenu("File", this);
		// settingsMenu = createMenu("Settings", this);
		helpMenu = createMenu("Help", this);
	}

	private void initializeMenuItems() {
		if (controller.isAdmin()) {
			newModuleMenuItem = createMenuItem("New Module", fileMenu);
			newModuleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
			
			saveModuleMenuItem = createMenuItem("Save Module", fileMenu);
			saveModuleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
		}
		loadModuleMenuItem = createMenuItem("Load Module", fileMenu);
		loadModuleMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
		
		exitMenuItem = createMenuItem("Quit", fileMenu);
		exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, Event.CTRL_MASK));

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
		else if (menuPressed == loadModuleMenuItem)
			controller.loadModule();
		else if (menuPressed == exitMenuItem)
			controller.exit();
		else if (menuPressed == helpMenuItem)
			controller.displayHelp();
		else if (menuPressed == aboutMenuItem)
			controller.displayAbout();
	}

	@Override
	public void moduleChanged() {		
	}
}
package teacher.view;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*; 
import javax.swing.UIManager.LookAndFeelInfo;

import teacher.controller.*;
import teacher.model.*;

public class ModuleViewer implements ModuleObserver {
	private ModuleReadOnly module;
	private ModuleController controller;
	private JFrame frame;
	private SlideNavigator slideNavigator;
	private DialogHandler dialogHandler;
	
	public ModuleViewer(ModuleReadOnly module, ModuleController controller) {
		this.module = module;
		module.registerObserver(this);
		this.controller = controller;
		controller.setModuleViewer(this);
		
		tryToInitializeLookAndFeel();
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowCloseListener());
		
		dialogHandler = new DialogHandler(frame);
		controller.setDialogHandler(dialogHandler);
		slideNavigator = new SlideNavigator(module, controller, dialogHandler);
		frame.getContentPane().add(slideNavigator, BorderLayout.CENTER);
		
		TopicChooser chooser = new TopicChooser(module, controller);
		frame.getContentPane().add(chooser, BorderLayout.WEST);
		
		JMenuBar menuBar = new ModuleMenuBar(module, controller);
		frame.setJMenuBar(menuBar);
		
		moduleChanged();
		frame.pack();
		frame.setVisible(true);
	}

	private void tryToInitializeLookAndFeel() {
		try {
			initializeLookAndFeel();
		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(-1);
		}
	}

	/** Sets the look and feel to the Nimbus look and feel.
	 *  If that fails, then sets the look and feel to Java's. */
	private void initializeLookAndFeel() throws Exception {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception ex) {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		}
	}

	@Override
	public void moduleChanged() {
		if (module.hasModuleLoaded()) {
			String title = module.getTitle();
			String lastSavePath = controller.getLastSavePath(); 
			if (lastSavePath != null)
				title += " - " + lastSavePath;
			frame.setTitle(title);
			frame.getContentPane().setVisible(true);
		}
		else {
			frame.setTitle("No module loaded");
			frame.getContentPane().setVisible(false);
		}
	}
	
	public String getCurrentSlideText() {
		return slideNavigator.getText();
	}

	public void dispose() {
		frame.dispose();
	}
	
	class WindowCloseListener implements WindowListener {
		@Override
		public void windowClosing(WindowEvent arg0) {
			controller.exit();
		}

		public void windowActivated(WindowEvent arg0) {}
		public void windowClosed(WindowEvent arg0) {}
		public void windowDeactivated(WindowEvent arg0) {}
		public void windowDeiconified(WindowEvent arg0) {}
		public void windowIconified(WindowEvent arg0) {}
		public void windowOpened(WindowEvent arg0) {}
	}
}
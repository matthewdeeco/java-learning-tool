package teacher.view;

import java.awt.BorderLayout;
import javax.swing.*; 
import javax.swing.UIManager.LookAndFeelInfo;

import teacher.controller.*;
import teacher.model.*;

public class ModuleViewer implements ModuleObserver {
	private ModuleReadOnly module;
	private static JFrame frame;
	private SlideNavigator slideNavigator;
	
	public static JFrame getWindow() {
		return frame;
	}
	
	public ModuleViewer(ModuleReadOnly module, ModuleController controller) {
		this.module = module;
		module.registerObserver(this);
		controller.setModuleViewer(this);
		
		tryToInitializeLookAndFeel();
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dialog.setParent(frame);
		
		slideNavigator = new SlideNavigator(module, controller);
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
		frame.setTitle(module.getTitle());
	}
	
	public String getCurrentSlideText() {
		return slideNavigator.getText();
	}
}
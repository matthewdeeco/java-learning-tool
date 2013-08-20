package teacher;

import javax.swing.SwingUtilities;

import teacher.controller.*;
import teacher.model.Module;
import teacher.view.ModuleViewer;

public class TestDriver {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Module module = new Module("Basic Input and Output");
				ModuleController controller = new TeacherController(module);
				ModuleViewer view = new ModuleViewer(module, controller);
			}
		});
	}
}

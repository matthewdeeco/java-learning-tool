package teacher.controller;

import teacher.model.Module;

public class TeacherController extends ModuleController {

	public TeacherController(Module module) {
		super(module);
	}

	@Override
	public boolean isAdmin() {
		return true;
	}

}

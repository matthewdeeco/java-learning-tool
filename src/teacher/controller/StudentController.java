package teacher.controller;

import teacher.model.Module;

public class StudentController extends ModuleController {

	public StudentController(Module module) {
		super(module);
	}

	@Override
	public boolean isAdmin() {
		return false;
	}

}

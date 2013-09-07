package teacher.controller;

import teacher.model.Module;

public class StudentController extends ModuleControllerImp {

	public StudentController(Module module) {
		super(module);
	}

	@Override
	public boolean isAdmin() {
		return false;
	}

}

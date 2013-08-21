package teacher.model;

import interpreter.*;

public class CodeBlock {
	private String classCode;
	
	public CodeBlock(String code) {
		this.classCode = code;
	}
	
	public Object getTestResult(String testCode) throws CodeException, ParseException {
		Interpreter interpreter = new Interpreter(classCode);
		return interpreter.evaluate(testCode);
	}
	
	public void setText(String text) {
		this.classCode = text;
	}
	
	public String getText() {
		return classCode;
	}
	
	@Override
	public String toString() {
		return getText();
	}
}

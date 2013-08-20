package teacher.model;

import interpreter.CodeException;
import interpreter.Interpreter;
import interpreter.NonExistentMethodException;
import interpreter.ParseException;
import interpreter.VoidMethodException;

public class CodeBlock {
	private String code;
	
	public CodeBlock(String code) {
		this.code = code;
	}
	
	public Object runMethod(String methodName) throws CodeException, ParseException, NonExistentMethodException, VoidMethodException {
		Interpreter interpreter = new Interpreter(code);
		Object result = interpreter.runMethod(methodName);
		return result;
	}
	
	public void setText(String text) {
		this.code = text;
	}
	
	public String getText() {
		return code;
	}
	
	@Override
	public String toString() {
		return getText();
	}
}

package teacher.controller;

import interpreter.*;
import teacher.model.CodeBlock;

public class CodeController {
	
	/** @return the toString of the Object returned by the method */
	public String runMethod(String codeBlock, String methodName) {
		try {
			CodeBlock code = new CodeBlock(codeBlock);
			Object result = code.runMethod(methodName);
			return "Result is: " + result.toString();
		} catch (CodeException ex) {
			return "Exception thrown from the code:\n" + ex.getMessage();
		} catch (ParseException ex) {
			return "Parsing exception:\n" + ex.getMessage();
		} catch (Exception ex) {
			return ex.getMessage();
		}
	}
}

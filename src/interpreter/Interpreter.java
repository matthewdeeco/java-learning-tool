package interpreter;

import bsh.*;
import java.util.*;
import java.util.regex.*;

public class Interpreter {
	private bsh.Interpreter interpreter;
	private compiler.Compiler compiler;
	private String className;
	private List<BshMethod> methods;

	public Interpreter(String code) throws CodeException, ParseException {
		interpreter = new bsh.Interpreter();
		compiler = new compiler.Compiler();

		int openBraceIndex = parseClassName(code);
		if (compiler.compiles(className, code)) {
			code = code.substring(openBraceIndex, code.length() - 2);
			code = String.format("%s() { %s return this;}", className, code);
			evaluate(code);
			evaluate(String.format("__object__ = %s()", className));
			evaluate("__methods__ = __object__.namespace.getMethods()");
			methods = Arrays.asList((BshMethod[]) get("__methods__"));
		} else
			throw new interpreter.ParseException(compiler.getErrors());
	}
	
	/** 
	 * http://stackoverflow.com/questions/14885315/regex-pattern-to-find-java-class-in-java-file 
	 * @return the index of the last matched character 
	 */
	private int parseClassName(String code) {
		Pattern p = Pattern.compile("\\s*((public|private)\\s+)?class\\s+(\\w+)\\s+((extends\\s+\\w+)|(implements\\s+\\w+( ,\\w+)*))?\\s*\\{");
		Matcher m = p.matcher(code);
		try {
			m.find();
			MatchResult mr = m.toMatchResult();
			className = mr.group(3);
			return m.end();
		} catch (Exception e) {
			return -1;
		}
	}
	
	private void evaluate(String code) throws CodeException, ParseException {
		try {
			interpreter.eval(code);
		} catch (TargetError e) { // exception is thrown by codeBody
			throw new CodeException(e);
		} catch (bsh.ParseException e) {
			throw new ParseException(e);
		} catch (EvalError e) {
			throw new RuntimeException(e);
		}
	}
	
	private Object get(String name) throws CodeException, ParseException {
		try {
			return interpreter.get(name);
		} catch (TargetError e) { // exception is thrown by codeBody
			throw new CodeException(e);
		} catch (bsh.ParseException e) {
			throw new ParseException(e);
		} catch (EvalError e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object runMethod(String methodName) throws CodeException, ParseException, NonExistentMethodException, VoidMethodException {
		BshMethod method = getMethod(methodName);
		if (method == null)
			throw new NonExistentMethodException(className, methodName);
		else if (method.getReturnType() == Void.TYPE)
			throw new VoidMethodException(className, methodName);
		
		evaluate(String.format("__result__ = __object__.%s()", methodName));
		Object result = get("__result__");
		
		return result;
	}
	
	private BshMethod getMethod(String methodName) throws CodeException, ParseException {
		for (BshMethod method: methods) {
			if (method.getName().equals(methodName))
				return method;
		}
		return null;
	}
}
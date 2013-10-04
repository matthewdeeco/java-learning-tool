package interpreter;

import bsh.*;
import java.io.PrintStream;

public class Interpreter {
	private bsh.Interpreter interpreter;

	public Interpreter(String code) throws CodeException, ParseException {
		interpreter = new bsh.Interpreter();
		
		evaluate(code);
	}
	
	public Object evaluate(String code) throws CodeException, ParseException {
		try {
			return interpreter.eval(code);
		} catch (TargetError e) { // exception is thrown by codeBody
			throw new CodeException(e);
		} catch (bsh.ParseException e) {
			throw new ParseException(e);
		} catch (EvalError e) {
			throw new RuntimeException(e);
		}
	}
	
	public Object get(String name) throws CodeException, ParseException {
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
	
	public void setOut(PrintStream out) {
		interpreter.setOut(out);
	}
}
package interpreter;

/** Indicates that the method returns void. */
public class VoidMethodException extends Exception {
	private String className;
	private String methodName;
	
	public VoidMethodException(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
	}
	
	@Override
	public String getMessage() {
		return String.format("Method %s in the class %s has return type void", methodName, className);
	}
}

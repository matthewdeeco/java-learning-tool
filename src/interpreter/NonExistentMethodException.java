package interpreter;

/** Indicates that the method called does not exist. */
public class NonExistentMethodException extends Exception {
	private String className;
	private String methodName;
	
	public NonExistentMethodException(String className, String methodName) {
		this.className = className;
		this.methodName = methodName;
	}
	
	@Override
	public String getMessage() {
		return String.format("Method %s does not exist in the class %s", methodName, className);
	}
}

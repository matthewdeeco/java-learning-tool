package compiler;
import java.io.StringWriter;
import java.util.*;
import javax.tools.*;
import javax.tools.JavaCompiler.*;

public class Compiler {
	private JavaCompiler compiler;
	private StandardJavaFileManager fileManager;
	private DiagnosticListener listener;
	private StringWriter stringWriter;
	
	public Compiler() {
		this(null);
	}
	
	public Compiler(DiagnosticListener listener) {
		compiler = ToolProvider.getSystemJavaCompiler();
		fileManager  = compiler.getStandardFileManager(listener, null, null);
		this.listener = listener;
		stringWriter = new StringWriter();
	}

	public boolean compiles(String className, String codeBody) {
		try {
			JavaFileObject javaObjectFromString = new JavaObjectFromString(className, codeBody);
			Iterable<JavaFileObject> fileObjects = Arrays.asList(javaObjectFromString);
			CompilationTask task = compiler.getTask(stringWriter, fileManager, listener, null, null, fileObjects);
			boolean result = task.call();
			return result;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getErrors() {
		return stringWriter.toString();
	}
}
package compiler;
import java.io.IOException;
import java.net.URI;

import javax.tools.SimpleJavaFileObject;

class JavaObjectFromString extends SimpleJavaFileObject {
	private String contents = null;

	public JavaObjectFromString(String className, String contents) throws Exception {
		super(new URI(className + ".java"), Kind.SOURCE);
		this.contents = contents;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return contents;
	}
}
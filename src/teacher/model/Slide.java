package teacher.model;

import java.io.Serializable;

public class Slide implements Serializable {
	private static final long serialVersionUID = 1L;
	/** Used to mark end of a field in the text String. */
	public static final String FIELD_DELIMITER = "\nBBAF029ABD53E88F\n";
	public static final Type DEFAULT_TYPE = Type.TEXT;
	
	
	public enum Type {
		TEXT("Regular slide"),
		CODE("Coding exercise"),
		TRUE_OR_FALSE("True or False exercise");
		
		private String name;
		
		private Type(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
	};
	
	private Type type;
	private String text;
	
	public Slide(Type type) {
		this(type, "");
	}
	
	public Slide(Type type, String text) {
		this.type = type;
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Type getType() {
		return type;
	}
}

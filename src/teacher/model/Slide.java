package teacher.model;

import java.io.Serializable;

public class Slide implements Serializable {
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

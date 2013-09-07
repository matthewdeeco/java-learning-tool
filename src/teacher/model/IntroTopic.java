package teacher.model;

public class IntroTopic extends Topic {
	private static final long serialVersionUID = 1L;
	
	public IntroTopic() {
		super("Intro");
	}
	
	public boolean isIntroTopic() {
		return true;
	}
}

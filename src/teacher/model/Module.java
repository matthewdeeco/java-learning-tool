package teacher.model;

import java.util.*;

public class Module {
	private String topic;
	private List<ModuleObserver> observers;
	
	public Module(String topic) {
		this.topic = topic;
		observers = new ArrayList<ModuleObserver>();
	}
	
	public String getTopic() {
		return topic;
	}
	
	public void registerObserver(ModuleObserver observer) {
		observers.add(observer);
	}
}

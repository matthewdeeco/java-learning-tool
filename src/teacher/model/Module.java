package teacher.model;

import java.io.Serializable;
import java.util.*;

import teacher.model.Slide.Type;

public class Module implements ModuleReadOnly, Serializable {
	private String title;
	private List<Topic> topics;
	private transient List<ModuleObserver> observers;
	private transient int currentTopicIndex;
	
	public Module(String title) {
		this.title = title;
		topics = new ArrayList<Topic>();
		observers = new ArrayList<ModuleObserver>();
		topics.add(new IntroTopic());
	}

	/** Inserts a new topic after the current topic. */
	public void createNewTopic(String title) {
		topics.add(currentTopicIndex + 1, new Topic(title));
		setTopicByIndex(currentTopicIndex + 1);
	}

	public void createNewSlide(Type type) {
		currentTopic().createNewSlide(type);
		nextSlide();
	}

	public void deleteCurrentSlide() {
		if (!canDeleteCurrentSlide())
			return;
		else if (currentTopic().hasNextSlide()) {
			currentTopic().deleteCurrentSlide();
		} else {
			currentTopic().deleteCurrentSlide();

			if (currentTopic().hasSlideCount(0)) {
				topics.remove(currentTopicIndex);
				if (currentTopicIndex == topics.size())
					currentTopicIndex--;
			} else
				currentTopic().previousSlide();
		}
		notifyObservers();
	}
	
	public boolean canDeleteCurrentSlide() {
		boolean isIntro = currentTopic().isIntroTopic();
		boolean isLastSlideInTopic = currentTopic().hasSlideCount(1);
		return !(isIntro && isLastSlideInTopic);
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<String> getTopics() {
		List<String> topicNames = new ArrayList<String>();
		for (Topic topic: topics)
			topicNames.add(topic.getTitle());
		return topicNames;
	}
	
	public Slide getCurrentSlide(){
		return currentTopic().getCurrentSlide();
	}
	
	public void setCurrentSlideText(String text) {
		currentTopic().setCurrentSlide(text);
	}
	
	public void nextSlide() {
		if (currentTopicHasNextSlide())
			currentTopic().nextSlide();
		else
			nextTopic();
		notifyObservers();
	}
	
	private void nextTopic() {
		currentTopicIndex++;
	}
	
	public void previousSlide() {
		if (currentTopicHasPreviousSlide())
			currentTopic().previousSlide();
		else
			previousTopic();
		notifyObservers();
	}
	
	private void previousTopic() {
		currentTopicIndex--;
	}

	public void setTopicByIndex(int index) {
		if (index >= 0 && index < topics.size()) {
			currentTopic().setSlideByIndex(0); // reset slide
			currentTopicIndex = index;
			currentTopic().setSlideByIndex(0); // reset slide
			notifyObservers();
		}
	}
	
	public boolean hasNextSlide() {
		return currentTopicHasNextSlide() || hasNextTopic();
	}
	
	public boolean hasPreviousSlide() {
		return currentTopicHasPreviousSlide() || hasPreviousTopic();
	}
	
	private boolean currentTopicHasPreviousSlide() {
		return currentTopic().hasPreviousSlide();
	}
	
	private boolean hasPreviousTopic() {
		return currentTopicIndex > 0 && topics.size() > 0;
	}
	
	private boolean currentTopicHasNextSlide() {
		return currentTopic().hasNextSlide(); 
	}
	
	private boolean hasNextTopic() {
		return currentTopicIndex < topics.size() - 1;
	}
	
	private Topic currentTopic() {
		return topics.get(currentTopicIndex);
	}
	
	public int getCurrentTopicIndex() {
		return currentTopicIndex;
	}

	public void loadModule(Module other) {
		this.title = other.title;
		this.topics = other.topics;
		this.currentTopicIndex = 0;
		notifyObservers();
	}
	
	public void registerObserver(ModuleObserver observer) {
		observers.add(observer);
	}
	
	private void notifyObservers() {
		for (ModuleObserver observer: observers)
			observer.moduleChanged();
	}
}

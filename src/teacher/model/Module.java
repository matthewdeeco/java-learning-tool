package teacher.model;

import java.io.Serializable;
import java.util.*;

import teacher.model.Slide.Type;

public class Module implements ModuleReadOnly, Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private List<Topic> topics;
	private transient List<ModuleObserver> observers;
	private transient int currentTopicIndex;
	
	public Module() {
		this(null);
	}
	
	public Module(String title) {
		this.title = title;
		topics = new ArrayList<Topic>();
		topics.add(new IntroTopic());
		resetObservers();
	}
	
	public void resetObservers() {
		observers = new ArrayList<ModuleObserver>();
	}

	public void loadModule(Module other) {
		this.title = other.title;
		this.topics = other.topics;
		this.currentTopicIndex = 0;
		notifyObservers();
	}
	
	public void renameModule(String newTitle) {
		this.title = newTitle;
		notifyObservers();
	}
	
	public boolean hasModuleLoaded() {
		return title != null;
	}

	/** Inserts a new topic after the current topic. */
	public void createNewTopic(String title) {
		topics.add(currentTopicIndex + 1, new Topic(title));
		setTopicByIndex(currentTopicIndex + 1);
	}
	
	public void createNewSlide() {
		createNewSlide(Slide.DEFAULT_TYPE);
	}

	public void createNewSlide(Type type) {
		currentTopic().createNewSlide(type);
		nextSlide();
	}
	
	public void deleteCurrentTopic() {
		if (!canDeleteCurrentTopic())
			return;
		topics.remove(currentTopicIndex);
		if (currentTopicIndex == topics.size())
			previousTopic(); // go to last topic
		notifyObservers();
	}
	
	public void renameCurrentTopic(String newTitle) {
		currentTopic().rename(newTitle);
		notifyObservers();
	}
	
	public boolean canDeleteCurrentTopic() {
		boolean isIntro = currentTopic().isIntroTopic();
		return !isIntro;
	}

	public void deleteCurrentSlide() {
		if (!canDeleteCurrentSlide())
			return;
		else if (currentTopic().hasNextSlide()) {
			currentTopic().deleteCurrentSlide();
		} else { // last slide in topic
			currentTopic().deleteCurrentSlide();

			if (currentTopic().hasNoSlides()) // only slide in topic
				deleteCurrentTopic();
			else // go to last slide in topic
				currentTopic().previousSlide();
		}
		notifyObservers();
	}
	
	/** The slide cannot be deleted if it is the only slide remaining in an intro topic. */
	public boolean canDeleteCurrentSlide() {
		boolean canDeleteCurrentTopic = canDeleteCurrentTopic();
		boolean isNotLastSlideInTopic = currentTopic().hasNextSlide() || currentTopic().hasPreviousSlide();
		return canDeleteCurrentTopic || isNotLastSlideInTopic;
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
	
	public void previousTopic() {
		setTopicByIndex(--currentTopicIndex);
		notifyObservers();
	}
	
	public void nextTopic() {
		setTopicByIndex(++currentTopicIndex);
		notifyObservers();
	}
	
	public void previousSlide() {
		if (currentTopic().hasPreviousSlide())
			currentTopic().previousSlide();
		else
			currentTopicIndex--;
		notifyObservers();
	}
	
	public void nextSlide() {
		if (currentTopic().hasNextSlide())
			currentTopic().nextSlide();
		else
			currentTopicIndex++;
		notifyObservers();
	}

	public void setTopicByIndex(int index) {
		if (index >= 0 && index < topics.size()) {
			currentTopic().setSlideByIndex(0); // reset slide
			currentTopicIndex = index;
			currentTopic().setSlideByIndex(0); // reset slide
			notifyObservers();
		}
	}
	
	public boolean hasPreviousTopic() {
		return currentTopicIndex > 0 && topics.size() > 0;
	}
	
	public boolean hasNextTopic() {
		return currentTopicIndex < topics.size() - 1;
	}
	
	public boolean hasPreviousSlide() {
		return currentTopic().hasPreviousSlide() || hasPreviousTopic();
	}
	
	public boolean hasNextSlide() {
		return currentTopic().hasNextSlide() || hasNextTopic();
	}
	
	private Topic currentTopic() {
		return topics.get(currentTopicIndex);
	}
	
	public int getCurrentTopicIndex() {
		return currentTopicIndex;
	}
	
	public void registerObserver(ModuleObserver observer) {
		observers.add(observer);
	}
	
	private void notifyObservers() {
		for (ModuleObserver observer: observers)
			observer.moduleChanged();
	}

	@Override
	public int getCurrentSlideIndex() {
		int currentSlideIndexWithinTopic = currentTopic().getCurrentSlideIndex();
		int prevTopicsSlideTotal = 0;
		for (int i = 0; i < currentTopicIndex; i++)
			prevTopicsSlideTotal += topics.get(i).getSlideCount();
		return prevTopicsSlideTotal + currentSlideIndexWithinTopic;
	}

	@Override
	public int getSlideCount() {
		int slideTotal = 0;
		for (Topic topic: topics)
			slideTotal += topic.getSlideCount();
		return slideTotal;
	}
}

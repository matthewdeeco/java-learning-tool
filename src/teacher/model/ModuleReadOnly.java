package teacher.model;

import java.util.List;

/** None of these methods should change the module data */
public interface ModuleReadOnly {
	public String getTitle();
	public List<String> getTopics();
	public int getSlideCount();
	
	public boolean hasPreviousTopic();
	public boolean hasNextTopic();
	public int getCurrentTopicIndex();
	public boolean canDeleteCurrentTopic();
	
	public boolean hasPreviousSlide();
	public boolean hasNextSlide();
	public Slide getCurrentSlide();
	public int getCurrentSlideIndex();
	public boolean canDeleteCurrentSlide();
	
	public void registerObserver(ModuleObserver observer);
	public boolean hasModuleLoaded();
}

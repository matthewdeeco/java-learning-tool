package teacher.model;

import java.io.Serializable;
import java.util.*;

import teacher.model.Slide.Type;

public class Topic implements Serializable {
	private String title;
	private List<Slide> slides;
	private transient int currentSlideIndex;
	
	public Topic(String title) {
		this.title = title;
		slides = new ArrayList<Slide>(); 
		slides.add(new Slide(Type.TEXT));
	}

	public void createNewSlide(Type type) {
		slides.add(currentSlideIndex + 1, new Slide(type));
	}

	public void deleteCurrentSlide() {
		slides.remove(currentSlideIndex);
	}
	
	public void setCurrentSlide(String text) {
		slides.get(currentSlideIndex).setText(text);
	}
	
	public Slide getCurrentSlide() {
		return slides.get(currentSlideIndex);
	}
	
	public String getTitle() {
		return title;
	}
	
	public boolean hasPreviousSlide() {
		return currentSlideIndex > 0 && slides.size() > 0;
	}
	
	public boolean hasNextSlide() {
		return currentSlideIndex < slides.size() - 1;
	}

	public void previousSlide() {
		currentSlideIndex--;
	}

	public void nextSlide() {
		currentSlideIndex++;
	}

	public void setSlideByIndex(int index) {
		currentSlideIndex = index;
	}

	public boolean hasSlideCount(int count) {
		return slides.size() == count;
	}

	public boolean isIntroTopic() {
		return false;
	}
}

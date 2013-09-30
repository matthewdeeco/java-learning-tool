package teacher.view;

import javax.swing.*;

import teacher.model.ModuleObserver;
import teacher.model.ModuleReadOnly;

public class SlideTracker extends JPanel implements ModuleObserver {
	private ModuleReadOnly module;
	private JLabel text;
	
	public SlideTracker(ModuleReadOnly module) {
		this.module = module;
		module.registerObserver(this);
		
		text = new JLabel("0/0", SwingConstants.CENTER);
		add(text);
		moduleChanged();
	}

	@Override
	public void moduleChanged() {
		text.setText(String.format("%d/%d", module.getCurrentSlideIndex() + 1, module.getSlideCount()));
	}
}

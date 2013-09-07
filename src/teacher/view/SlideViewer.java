package teacher.view;

import java.awt.CardLayout;
import javax.swing.JPanel;

import teacher.controller.ModuleController;
import teacher.model.*;
import teacher.model.Slide.Type;
import teacher.view.editor.*;

public class SlideViewer extends JPanel implements ModuleObserver {
	private HtmlViewerPanel htmlPanel;
	private JavaEditorPanel javaPanel;
	private TrueOrFalsePanel tofPanel;
	
	private TextPanel visiblePanel;
	private CardLayout cards;
	private ModuleReadOnly module;
	private ModuleController controller;

	public SlideViewer(ModuleReadOnly module, ModuleController controller, DialogHandler dialogHandler) {
		this.module = module;
		module.registerObserver(this);
		this.controller = controller;

		if (controller.isAdmin())
			htmlPanel = new HtmlEditorPanel();
		else
			htmlPanel = new HtmlViewerPanel();
		javaPanel = new JavaEditorPanel(controller, dialogHandler);
		tofPanel = new TrueOrFalsePanel(controller, dialogHandler);
		
		cards = new CardLayout();
		setLayout(cards);
		add(htmlPanel, Type.TEXT.toString());
		add(javaPanel, Type.CODE.toString());
		add(tofPanel, Type.TRUE_OR_FALSE.toString());
		moduleChanged();
	}

	@Override
	public void moduleChanged() {
		Slide currentSlide = module.getCurrentSlide();
		Type currentSlideType = currentSlide.getType();
		if (currentSlideType == Type.TEXT)
			visiblePanel = htmlPanel;
		else if (currentSlideType == Type.CODE)
			visiblePanel = javaPanel;
		else
			visiblePanel = tofPanel;
		cards.show(this, currentSlideType.toString());
		setText(currentSlide.getText());
	}

	public String getText() {
		return visiblePanel.getText();
	}

	public void setText(String text) {
		visiblePanel.setText(text);
	}

	public boolean isEmpty() {
		return visiblePanel.isEmpty();
	}
}

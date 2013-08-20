package teacher.view;

import java.awt.CardLayout;
import java.awt.event.*;
import javax.swing.JPanel;

import teacher.controller.ModuleController;
import teacher.model.*;

public class SlideViewer extends JPanel implements ModuleObserver {
	private HtmlViewerPanel htmlPanel;
	private JavaEditorPanel javaPanel;

	private TextPanel visiblePanel;
	private CardLayout cards;
	private ModuleReadOnly module;
	private ModuleController controller;

	public SlideViewer(ModuleReadOnly module, ModuleController controller) {
		this.module = module;
		module.registerObserver(this);
		this.controller = controller;

		if (controller.isAdmin())
			htmlPanel = new HtmlEditorPanel();
		else
			htmlPanel = new HtmlViewerPanel();
		javaPanel = new JavaEditorPanel();
		javaPanel.addEvaluateListener(new EvaluateListener());

		cards = new CardLayout();
		setLayout(cards);
		add(htmlPanel, "HTML");
		add(javaPanel, "Java");
		moduleChanged();
	}

	@Override
	public void moduleChanged() {
		Slide currentSlide = module.getCurrentSlide();
		switch (currentSlide.getType()) {
			case TEXT:
				htmlPanel.setText(currentSlide.getText());
				cards.show(this, "HTML");
				visiblePanel = htmlPanel;
				break;
			case CODE:
				javaPanel.setText(currentSlide.getText());
				cards.show(this, "Java");
				visiblePanel = javaPanel;
				break;
			case TRUE_OR_FALSE:
				break;
			default:
				throw new IllegalArgumentException("Unsupported slide type");
		}

	}

	class EvaluateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String result = controller.runMethod(javaPanel.getText(), javaPanel.getMethodName());
			Dialog.infoMessage(result);
		}
	}

	public String getText() {
		return visiblePanel.getText();
	}

	public void setText(String text) {
		visiblePanel.setText(text);
	}
}

package teacher.view.editor;

import javax.swing.JPanel;

public abstract class TextPanel extends JPanel {
	public abstract String getText();
	public abstract void setText(String text);
}
package teacher.view.editor;

import java.awt.BorderLayout;

import javax.swing.*;

public class HtmlViewerPanel extends TextPanel {
	private JEditorPane editorPane;
	
	public HtmlViewerPanel() {
		setLayout(new BorderLayout());
		editorPane = createHtmlViewerArea();
		add(createScrollPane(editorPane), BorderLayout.CENTER);
	}
	
	private JScrollPane createScrollPane(JEditorPane textArea) {
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return scrollPane;
	}

	private JEditorPane createHtmlViewerArea() {
		JEditorPane editorPane = new JEditorPane();
		editorPane.setContentType("text/html");
		editorPane.setEditable(false);
		editorPane.setOpaque(false);
		editorPane.setCaretPosition(0);
		return editorPane;
	}

	@Override
	public String getText() {
		return editorPane.getText();
	}
	
	@Override
	public void setText(String text) {
		editorPane.setText(text);
	}

	@Override
	public boolean isEmpty() {
		return editorPane.getText().isEmpty();
	}
}
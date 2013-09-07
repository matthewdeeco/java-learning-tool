package teacher.view.editor;

import java.awt.BorderLayout;
import javax.swing.*;

import org.fife.ui.autocomplete.*;
import org.fife.ui.rsyntaxtextarea.*;
import org.fife.ui.rtextarea.RTextScrollPane;

public class CodeEditorPanel extends TextPanel {
	private RSyntaxTextArea textArea;
	
	public CodeEditorPanel(String language) {
		setLayout(new BorderLayout());
		textArea = createTextArea(language);
		add(createScrollPane(textArea), BorderLayout.CENTER);
	}
	
	private RSyntaxTextArea createTextArea(String language) {
		RSyntaxTextArea textArea = new RSyntaxTextArea();
		textArea.setSyntaxEditingStyle(language);
		textArea.setCodeFoldingEnabled(true);
		textArea.setAntiAliasingEnabled(true);
		//changeToDarkTheme(textArea);
		return textArea;
	}
	
	private RTextScrollPane createScrollPane(RSyntaxTextArea textArea) {
		RTextScrollPane scrollPane = new RTextScrollPane(textArea);
		scrollPane.setFoldIndicatorEnabled(true);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		return scrollPane;
	}
	
	@Override
	public void setText(String text) {
		textArea.setText(text);
	}
	
	@Override
	public String getText() {
		return textArea.getText();
	}

	public void installAutoCompletion(CompletionProvider provider) {
		AutoCompletion autoCompletion = new AutoCompletion(provider);
		autoCompletion.install(textArea);
	}
	
	public boolean isEmpty() {
		return textArea.getText().isEmpty();
	}
}

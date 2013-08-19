package creator;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.CompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import teacher.model.JavaCompletionProvider;

public class CodeEditorPanel extends JPanel {
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
	
	public void setText(String text) {
		textArea.setText(text);
	}
	
	public String getText() {
		return textArea.getText();
	}

	private void changeToDarkTheme(RSyntaxTextArea textArea) {
		try {
			Theme theme = Theme.load(getClass().getResourceAsStream("/themes/dark.xml"));
			theme.apply(textArea);
		} catch (IOException ex) {
		}
	}

	public void installAutoCompletion(CompletionProvider provider) {
		AutoCompletion autoCompletion = new AutoCompletion(provider);
		autoCompletion.install(textArea);
	}
}

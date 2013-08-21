package teacher.view.editor;

import java.awt.*;
import net.atlanticbb.tantlinger.shef.HTMLEditorPane;

public class HtmlEditorPanel extends HtmlViewerPanel {
	private HTMLEditorPane codeArea;

	public HtmlEditorPanel() {
		setLayout(new BorderLayout());
		codeArea = new HTMLEditorPane();
		
		/*JMenuBar menuBar = new JMenuBar();
		menuBar.add(codeArea.getFormatMenu());
		menuBar.add(codeArea.getEditMenu());
		menuBar.add(codeArea.getInsertMenu());
		
		add(menuBar, BorderLayout.NORTH);*/
		add(codeArea, BorderLayout.CENTER);
	}
	
	@Override
	public String getText() {
		return codeArea.getText();
	}
	
	@Override
	public void setText(String text) {
		codeArea.setText(text);
	}
}
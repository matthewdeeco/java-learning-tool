package creator;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HtmlEditorPanel extends JPanel implements ChangeListener {
	private JTabbedPane tabbedPane;
	private CodeEditorPanel codeArea;
	private HtmlViewerPanel viewArea;
	
	public HtmlEditorPanel() {
		setLayout(new BorderLayout());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				initTabbedPane();
				revalidate();
			}
		});
	}

	private void initTabbedPane() {
		tabbedPane = new JTabbedPane();
		
		codeArea = new CodeEditorPanel("text/html");
		codeArea.setText("<html>\n\n</html>");
		tabbedPane.addTab("Edit", codeArea);
			
		viewArea = new HtmlViewerPanel();
		tabbedPane.addTab("Preview", viewArea);

		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		tabbedPane.setSelectedIndex(0);
		tabbedPane.addChangeListener(this);
		add(tabbedPane, BorderLayout.CENTER);
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		int index = tabbedPane.getSelectedIndex();
		if (index == 1) { // preview
			viewArea.setText(codeArea.getText());
		}
	}
}
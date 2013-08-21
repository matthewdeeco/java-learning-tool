package teacher.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import teacher.controller.ModuleController;
import teacher.model.*;
import teacher.view.editor.*;

public class JavaEditorPanel extends TextPanel {
	private JTabbedPane tabbedPane;
	private CodeEditorPanel codeArea;
	private CodeEditorPanel testArea;
	private HtmlViewerPanel specs;
	private JButton evaluateButton;
	
	private ModuleReadOnly module;
	private ModuleController controller;

	public JavaEditorPanel(ModuleReadOnly module, ModuleController controller) {
		this.module = module;
		this.controller = controller;
		
		setLayout(new BorderLayout());

		if (controller.isAdmin())
			specs = new HtmlEditorPanel();
		else
			specs = new HtmlViewerPanel();
		
		codeArea = new CodeEditorPanel("text/java");
		codeArea.installAutoCompletion(new JavaCompletionProvider());
		
		evaluateButton = new JButton("Evaluate");
		evaluateButton.addActionListener(new EvaluateListener());
		codeArea.add(evaluateButton, BorderLayout.SOUTH);

		testArea = new CodeEditorPanel("text/java");
		testArea.installAutoCompletion(new JavaCompletionProvider());
		
		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.addTab("Problem specs", specs);
		tabbedPane.addTab("Class code", codeArea);
		if (controller.isAdmin())
			tabbedPane.addTab("Test code", testArea);
		
		add(tabbedPane, BorderLayout.CENTER);
	}

	public void showResult(String result) {
		Dialog.infoMessage(result);
	}

	@Override
	public String getText() {
		StringBuilder sb = new StringBuilder();
		sb.append(specs.getText());
		sb.append(Slide.FIELD_DELIMITER);
		sb.append(codeArea.getText());
		sb.append(Slide.FIELD_DELIMITER);
		sb.append(testArea.getText());
		return sb.toString();
	}

	@Override
	public void setText(String text) {
		if (text.isEmpty())
			return;
		String[] fields = text.split(Slide.FIELD_DELIMITER);
		specs.setText(fields[0]);
		codeArea.setText(fields[1]);
		testArea.setText(fields[2]);
	}

	class EvaluateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String result = controller.testCode(codeArea.getText(), testArea.getText());
			Dialog.infoMessage(result);
		}
	}
}

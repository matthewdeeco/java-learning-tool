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
	
	private ModuleController controller;
	private DialogHandler dialogHandler;

	public JavaEditorPanel(ModuleController controller, DialogHandler dialog) {
		this.controller = controller;
		this.dialogHandler = dialog;
		
		setLayout(new BorderLayout());

		if (controller.isAdmin())
			specs = new HtmlEditorPanel();
		else
			specs = new HtmlViewerPanel();
		
		codeArea = new CodeEditorPanel("text/java");
		installAutoCompletion(codeArea);

		testArea = new CodeEditorPanel("text/java");
		installAutoCompletion(testArea);
		
		evaluateButton = new JButton("Evaluate");
		evaluateButton.addActionListener(new EvaluateListener());
		testArea.add(evaluateButton, BorderLayout.SOUTH);
		
		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.addTab("Problem specs", specs);
		tabbedPane.addTab("Class code", codeArea);
		tabbedPane.addTab("Test code", testArea);
		
		add(tabbedPane, BorderLayout.CENTER);
	}

	private void installAutoCompletion(CodeEditorPanel codeArea) {
		try {
			codeArea.installAutoCompletion(new JavaCompletionProvider());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void showResult(String result) {
		dialogHandler.infoMessage(result);
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
		tabbedPane.setSelectedIndex(0);
		if (text.isEmpty()) {
			specs.setText("");
			codeArea.setText("");
			testArea.setText("");
		} else {
			String[] fields = text.split(Slide.FIELD_DELIMITER);
			if (fields.length > 0)
				specs.setText(fields[0]);
			else
				specs.setText("");
			if (fields.length > 1)
				codeArea.setText(fields[1]);
			else
				codeArea.setText("");
			if (fields.length > 2)
				testArea.setText(fields[2]);
			else
				testArea.setText("");
		}
	}

	class EvaluateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String result = controller.testCode(codeArea.getText(), testArea.getText());
			if (result != null)
				dialogHandler.infoMessage(result);
		}
	}
	
	public boolean isEmpty() {
		return specs.getText().isEmpty();
	}
}

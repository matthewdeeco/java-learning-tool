package teacher.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import teacher.model.*;

public class JavaEditorPanel extends TextPanel {
	private CodeEditorPanel codeArea;
	private JTextField methodName;
	private JButton evaluateButton;

	public JavaEditorPanel() {
		setLayout(new BorderLayout());

		codeArea = new CodeEditorPanel("text/java");
		codeArea.installAutoCompletion(new JavaCompletionProvider());
		add(codeArea, BorderLayout.CENTER);

		evaluateButton = new JButton("Evaluate");

		methodName = new JTextField(15);
		JPanel methodPanel = createPanel(new JLabel("Method to run: "), methodName, evaluateButton);
		add(methodPanel, BorderLayout.SOUTH);
	}
	
	public void addEvaluateListener(ActionListener listener) {
		evaluateButton.addActionListener(listener);
	}

	private JPanel createPanel(JComponent... components) {
		JPanel panel = new JPanel();
		for (int i = 0; i < components.length; i++)
			panel.add(components[i]);
		return panel;
	}

	public void showResult(String result) {
		JOptionPane.showMessageDialog(null, result);
	}

	@Override
	public String getText() {
		return getCodeBody();
	}
	
	public String getCodeBody() {
		return codeArea.getText();
	}
	
	public String getMethodName() {
		return methodName.getText();
	}

	@Override
	public void setText(String text) {
		codeArea.setText(text);
	}
}

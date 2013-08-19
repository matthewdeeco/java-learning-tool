package teacher.view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import creator.CodeEditorPanel;
import teacher.controller.CodeController;
import teacher.model.*;

public class JavaEditorPanel extends JPanel {
	private CodeController controller;
	private CodeEditorPanel codeArea;
	private JTextField methodName;
	private JButton evaluateButton;

	public JavaEditorPanel(final CodeBlock codeBlock, CodeController controller) {
		this.controller = controller;

		setLayout(new BorderLayout());

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				codeArea = new CodeEditorPanel("text/java");
				codeArea.installAutoCompletion(new JavaCompletionProvider());
				
				codeArea.setText(codeBlock.getText());
				add(codeArea, BorderLayout.CENTER);
				revalidate();
			}
		});

		evaluateButton = new JButton("Evaluate");
		evaluateButton.addActionListener(new EvaluateListener());

		methodName = new JTextField(15);
		JPanel methodPanel = createPanel(new JLabel("Method to run: "), methodName, evaluateButton);
		add(methodPanel, BorderLayout.SOUTH);
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

	class EvaluateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String result = controller.runMethod(codeArea.getText(), methodName.getText());
			JOptionPane.showMessageDialog(JavaEditorPanel.this, result);
		}
	}
}

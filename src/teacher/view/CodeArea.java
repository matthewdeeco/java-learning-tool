package teacher.view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.rtextarea.*;
import org.fife.ui.rsyntaxtextarea.*;

import teacher.controller.CodeController;
import teacher.model.CodeBlock;
import teacher.model.JavaCompletionProvider;

public class CodeArea extends JPanel {
	private CodeController controller;
	private RSyntaxTextArea codeArea;
	private JTextField methodName;
	private JButton evaluateButton;

	public CodeArea(CodeBlock codeBlock, CodeController controller) {
		this.controller = controller;

		setLayout(new BorderLayout());

		codeArea = createTextArea();
		codeArea.setText(codeBlock.getText());
		add(createScrollPane(codeArea), BorderLayout.CENTER);

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
	
	private RSyntaxTextArea createTextArea() {
		RSyntaxTextArea textArea = new RSyntaxTextArea();
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		textArea.setCodeFoldingEnabled(true);
		textArea.setAntiAliasingEnabled(true);
		// changeToDarkTheme(textArea);
		
		AutoCompletion autoCompletion = new AutoCompletion(new JavaCompletionProvider());
		autoCompletion.install(textArea);
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

	public void showResult(String result) {
		JOptionPane.showMessageDialog(null, result);
	}

	private void changeToDarkTheme(RSyntaxTextArea textArea) {
		try {
			Theme theme = Theme.load(getClass().getResourceAsStream("/themes/dark.xml"));
			theme.apply(textArea);
		} catch (IOException ex) {
		}
	}

	class EvaluateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String result = controller.runMethod(codeArea.getText(), methodName.getText());
			JOptionPane.showMessageDialog(CodeArea.this, result);
		}
	}
}

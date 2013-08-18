package teacher.view;

import interpreter.*;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.Locale;

import javax.swing.*;
import javax.tools.*;

import teacher.controller.CodeController;
import teacher.model.CodeBlock;

public class CodeArea extends JPanel {
	private CodeBlock model;
	private CodeController controller;
	private JTextArea codeArea;
	private JTextField methodName;
	private JButton evaluateButton;
	
	public CodeArea(CodeBlock codeBlock, CodeController controller) {
		this.model = codeBlock;
		this.controller = controller;
		
		setLayout(new BorderLayout());
		codeArea = new JTextArea(20, 60);
		codeArea.setText(codeBlock.getText());
		JScrollPane scrollPane = new JScrollPane(codeArea);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scrollPane, BorderLayout.CENTER);
		
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
			JOptionPane.showMessageDialog(CodeArea.this, result);
		}	
	}
	
	class MyDiagnosticListener implements DiagnosticListener {
		public void report(Diagnostic diagnostic) {
			System.out.println("Kind->" + diagnostic.getKind());
			System.out.println("Line Number->" + diagnostic.getLineNumber());
			System.out.println("Column Number->" + diagnostic.getColumnNumber());
			System.out.println("Message->"+ diagnostic.getMessage(Locale.ENGLISH));
			System.out.println("\n");
		}
	}
}

package teacher.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

import teacher.controller.ModuleController;
import teacher.model.*;
import teacher.view.editor.TextPanel;

public class TrueOrFalsePanel extends TextPanel implements ModuleObserver {
	private static final String TRUE_ANSWER = "T";
	private static final String FALSE_ANSWER = "F";
	
	private JPanel itemsPanel;
	private List<JTextField> questions;
	private List<ButtonGroup> groups;
	private List<Boolean> answers;
	
	private JButton newItemButton;
	private JButton evaluateButton;
	private JScrollPane scrollPane;
	
	private ModuleController controller;
	private DialogHandler dialogHandler;
	
	public TrueOrFalsePanel(ModuleController controller, DialogHandler dialogHandler) {
		this.controller = controller;
		this.dialogHandler = dialogHandler;
		
		setLayout(new BorderLayout());
		
		newItemButton = new JButton("Add new question");
		newItemButton.addActionListener(new NewItemListener());
		evaluateButton = new JButton("Check answers");
		evaluateButton.addActionListener(new EvaluateListener());
		
		if (controller.isAdmin())
			add(newItemButton, BorderLayout.SOUTH);
		else
			add(evaluateButton, BorderLayout.SOUTH);
		
		itemsPanel = new JPanel();
		itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
		
		scrollPane = new JScrollPane(itemsPanel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);
		
		questions = new ArrayList<JTextField>();
		groups = new ArrayList<ButtonGroup>();
		answers = new ArrayList<Boolean>();
	}
	
	@Override
	public String getText() {
		int n = groups.size();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			ButtonModel selection = groups.get(i).getSelection();
			if (selection == null)
				sb.append(FALSE_ANSWER);
			else
				sb.append(selection.getActionCommand());
			sb.append(" ");
			sb.append(questions.get(i).getText());
			if (i < n - 1)
				sb.append(Slide.FIELD_DELIMITER);
		}
		return sb.toString();
	}

	@Override
	public void setText(String text) {
		if (text.isEmpty())
			return;
		itemsPanel.removeAll();
		String[] items = text.split(Slide.FIELD_DELIMITER);
		
		questions.clear();
		groups.clear();
		answers.clear();
		
		for (int i = 0; i < items.length; i++) {
			String[] itemFields = items[i].split(" ", 2);
			boolean answer = (itemFields[0].equals(TRUE_ANSWER));
			answers.add(answer);
			
			String questionText = itemFields[1];
			JPanel itemPanel = createItemPanel(answer, questionText);
			itemsPanel.add(itemPanel);
		}
		itemsPanel.revalidate();
		itemsPanel.repaint();
	}
	
	private JPanel createItemPanel(boolean answer, String questionText) {
		JPanel itemPanel = new JPanel(new BorderLayout());
		JTextField question = createTextField(questionText);
		questions.add(question);
		itemPanel.add(question, BorderLayout.CENTER);
		itemPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, question.getPreferredSize().height + 10));
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(new JLabel(String.format("%d", questions.size())));
		ButtonGroup group = new ButtonGroup();
		JRadioButton[] buttons = new JRadioButton[2];
		for (int i = 0; i < 2; i++) {
			buttons[i] = new JRadioButton();
			group.add(buttons[i]);
			if (i == 0)
				buttons[i].setActionCommand(TRUE_ANSWER);
			else
				buttons[i].setActionCommand(FALSE_ANSWER);
			buttonsPanel.add(buttons[i]);
		}
		if (controller.isAdmin()) { // show correct answer
			if (answer == true)
				buttons[0].doClick();
			else
				buttons[1].doClick();
		}
		
		groups.add(group);
		itemPanel.add(buttonsPanel, BorderLayout.WEST);
		
		return itemPanel;
	}
	
	private JTextField createTextField(String text) {
		JTextField textField = new JTextField(text);
		if (!controller.isAdmin())
			textField.setEditable(false);
		return textField;
	}

	@Override
	public void moduleChanged() {
	}

	class NewItemListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			itemsPanel.add(createItemPanel(true, ""));
			questions.get(questions.size() - 1).requestFocus();
			revalidate();
			repaint();
			snapScrollBarDown();
		}
		
		private void snapScrollBarDown() {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());	
				}
			});
		}
	}
	
	class EvaluateListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String result = controller.checkTofAnswers(answers.toArray(), getUserAnswers());
			dialogHandler.infoMessage(result);
		}
		
		private Boolean[] getUserAnswers() {
			int i = 0;
			Boolean[] userAnswers = new Boolean[answers.size()];
			for (ButtonGroup group: groups)
				if (group.getSelection() == null)
					userAnswers[i++] = null;
				else if (group.getSelection().getActionCommand() == TRUE_ANSWER)
					userAnswers[i++] = true;
				else
					userAnswers[i++] = false;
			return userAnswers;
		}
	}

	public boolean isEmpty() {
		return questions.isEmpty();
	}
}
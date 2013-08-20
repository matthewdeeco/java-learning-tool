package teacher.view;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import teacher.model.Slide.Type;

public class SlideTypeDialog implements ActionListener {
	private static final int DEFAULT_TYPE = 0;
	private JDialog dialog;
	private List<JRadioButton> buttons;
	private Type selectedType;
	
	public SlideTypeDialog() {
		dialog = new JDialog();
		dialog.setTitle("Slide Type");
		dialog.setModal(true);
		dialog.addWindowListener(new CancelListener());
		
		ButtonGroup group = new ButtonGroup();
		JPanel radioButtonPanel = new JPanel();
		radioButtonPanel.setLayout(new BoxLayout(radioButtonPanel, BoxLayout.Y_AXIS));
		radioButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 7, 15));
		buttons = new ArrayList<JRadioButton>();
		for (Type type: Type.values())
			createButton(type.getName(), group, radioButtonPanel);
		buttons.get(DEFAULT_TYPE).doClick();
		// TODO implement True-or-False exercise
		buttons.get(2).setEnabled(false);
		
		JPanel actionPanel = new JPanel();
		actionPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JButton confirmButton = new JButton("OK");
		confirmButton.addActionListener(new ConfirmListener());
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new CancelListener());
		actionPanel.add(confirmButton);
		actionPanel.add(cancelButton);
		
		dialog.getContentPane().add(radioButtonPanel, BorderLayout.CENTER);
		dialog.getContentPane().add(actionPanel, BorderLayout.SOUTH);
		dialog.setLocationRelativeTo(ModuleViewer.getWindow());
		dialog.pack();
		dialog.setVisible(true);
	}
	
	private JRadioButton createButton(String text, ButtonGroup group, JPanel parent) {
		JRadioButton button = new JRadioButton(text);
		button.addActionListener(this);
		parent.add(button);
		buttons.add(button);
		group.add(button);
		return button;
	}
	
	public Type getSelectedType() {
		return selectedType;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JRadioButton button = (JRadioButton)e.getSource();
		int index = buttons.indexOf(button);
		selectedType = Type.values()[index];
	}
	
	class ConfirmListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			dialog.dispose();
		}
	}
	
	class CancelListener implements ActionListener, WindowListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			selectedType = null;
			dialog.dispose();
		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			selectedType = null;
			dialog.dispose();
		}
		
		public void windowActivated(WindowEvent arg0) {}
		public void windowClosed(WindowEvent arg0) {}
		public void windowDeactivated(WindowEvent arg0) {}
		public void windowDeiconified(WindowEvent arg0) {}
		public void windowIconified(WindowEvent arg0) {}
		public void windowOpened(WindowEvent arg0) {}
		
	}
}
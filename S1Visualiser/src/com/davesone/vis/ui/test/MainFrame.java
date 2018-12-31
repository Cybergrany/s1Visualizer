package com.davesone.vis.ui.test;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;

import com.davesone.vis.core.Scene;
import javax.swing.ListSelectionModel;

public class MainFrame extends JFrame {
	
	private DefaultListModel<String> sceneString;
	private ArrayList<Scene> sceneList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		//Init lists
		sceneString = new DefaultListModel<String>();
		sceneList = new ArrayList<Scene>();
		JList sceneJlist = new JList(sceneString);
		
		setResizable(false);
		setTitle("S1 VIZ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 430);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{175, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		
		//Delete and add buttons
		JButton btnDeleteScene = new JButton("Delete Scene");
		btnDeleteScene.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = sceneJlist.getSelectedIndex();
				
				sceneString.removeElementAt(index);
				sceneList.remove(index);
				
				if(sceneList.size() == 0)
					btnDeleteScene.setEnabled(false);
			}
		});
		
		JButton btnAddScene = new JButton("Add Scene");
		btnAddScene.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = sceneJlist.getSelectedIndex();
				
				sceneList.add(new Scene());
				sceneString.addElement("Scene " + index);
				btnDeleteScene.setEnabled(true);
			}
		});
		
		GridBagConstraints gbc_btnAddScene = new GridBagConstraints();
		gbc_btnAddScene.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddScene.gridx = 0;
		gbc_btnAddScene.gridy = 0;
		panel.add(btnAddScene, gbc_btnAddScene);
		
		
		GridBagConstraints gbc_btnDeleteScene = new GridBagConstraints();
		gbc_btnDeleteScene.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeleteScene.gridx = 1;
		gbc_btnDeleteScene.gridy = 0;
		panel.add(btnDeleteScene, gbc_btnDeleteScene);
		
		JButton btnEditScene = new JButton("Edit Scene");
		GridBagConstraints gbc_btnEditScene = new GridBagConstraints();
		gbc_btnEditScene.insets = new Insets(0, 0, 5, 0);
		gbc_btnEditScene.gridx = 2;
		gbc_btnEditScene.gridy = 0;
		panel.add(btnEditScene, gbc_btnEditScene);
		
		sceneJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		GridBagConstraints gbc_Scenelist = new GridBagConstraints();
		gbc_Scenelist.gridwidth = 3;
		gbc_Scenelist.insets = new Insets(0, 0, 0, 5);
		gbc_Scenelist.fill = GridBagConstraints.BOTH;
		gbc_Scenelist.gridx = 0;
		gbc_Scenelist.gridy = 1;
		panel.add(sceneJlist, gbc_Scenelist);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		getContentPane().add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JButton button = new JButton("Add Element");
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 0;
		panel_1.add(button, gbc_button);
		
		JButton button_1 = new JButton("Delete Element");
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 0;
		panel_1.add(button_1, gbc_button_1);
		
		JButton button_2 = new JButton("Edit Element");
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.insets = new Insets(0, 0, 5, 0);
		gbc_button_2.gridx = 2;
		gbc_button_2.gridy = 0;
		panel_1.add(button_2, gbc_button_2);
		
		JList ElementList = new JList();
		ElementList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GridBagConstraints gbc_ElementList = new GridBagConstraints();
		gbc_ElementList.gridwidth = 3;
		gbc_ElementList.fill = GridBagConstraints.BOTH;
		gbc_ElementList.insets = new Insets(0, 0, 0, 5);
		gbc_ElementList.gridx = 0;
		gbc_ElementList.gridy = 1;
		panel_1.add(ElementList, gbc_ElementList);
	}
}

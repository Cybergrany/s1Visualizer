package com.davesone.vis.ui;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import com.davesone.vis.core.Scene;
import com.davesone.vis.core.TextAndObjectList;
import com.davesone.vis.core.UIWrapperManager;
import com.davesone.vis.video.VideoFramelet;

import marvin.gui.MarvinAttributesPanel;
import marvin.util.MarvinAttributes;

import java.awt.Panel;

/**
 * TODO This is in desperate need of cleanup
 * @author Owner
 *
 */
public class MainFrame extends JFrame {
	
	private TextAndObjectList<Scene> sceneList;
	private Scene currentScene;//currently selected scene
	private JList currentElementList;
	private MarvinAttributesPanel AttributePanel;
	
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
		setResizable(false);
		
		sceneList = new TextAndObjectList<Scene>();
		currentScene = new Scene();
		sceneList.addElement(currentScene, "Scene");//Add default scene
		
		GridBagConstraints gbc_AttributePanel = new GridBagConstraints();
		
		JList sceneJlist = sceneList.getJList();
		setTitle("S1 VIZ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 415, 0};
		gridBagLayout.rowHeights = new int[]{175, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.WEST;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.VERTICAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{0, 65, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		
		//Delete and add buttons
		JButton btnDeleteScene = new JButton("Delete Scene");
		
		btnDeleteScene.setEnabled(false);
		
		btnDeleteScene.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = sceneJlist.getSelectedIndex();
				
				sceneList.removeElement(index);
				
				if(sceneList.size() == 0)
					btnDeleteScene.setEnabled(false);
			}
		});
		
		JButton btnAddScene = new JButton("Add Scene");
		btnAddScene.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				sceneList.addElement(new Scene(), "Scene");
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
		//AListener below due to objects not init'd
		
		GridBagConstraints gbc_btnEditScene = new GridBagConstraints();
		gbc_btnEditScene.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditScene.gridx = 2;
		gbc_btnEditScene.gridy = 0;
		panel.add(btnEditScene, gbc_btnEditScene);
		
		sceneJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane sceneListPane = new JScrollPane(sceneJlist);
		
		GridBagConstraints gbc_Scenelist = new GridBagConstraints();
		gbc_Scenelist.gridwidth = 3;
		gbc_Scenelist.insets = new Insets(0, 0, 0, 5);
		gbc_Scenelist.fill = GridBagConstraints.BOTH;
		gbc_Scenelist.gridx = 0;
		gbc_Scenelist.gridy = 1;
		panel.add(sceneListPane, gbc_Scenelist);
		
		JButton btnUp = new JButton("Move Up");
		
		btnUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = sceneJlist.getSelectedIndex();
				 
				if(index > 0) {
					sceneList.swapElement(index, index - 1);
					
					sceneJlist.setSelectedIndex(index - 1);
				}
			}
		});
		
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUp.insets = new Insets(0, 0, 5, 0);
		gbc_btnUp.gridx = 3;
		gbc_btnUp.gridy = 0;
		panel.add(btnUp, gbc_btnUp);
		
		JButton btnDown = new JButton("Move Down");
		
		btnDown.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = sceneJlist.getSelectedIndex();
				
				if(index < sceneList.size() - 1) {
					sceneList.swapElement(index, index + 1);
					
					sceneJlist.setSelectedIndex(index + 1);
				}
			}
		});
		
		GridBagConstraints gbc_btnDown = new GridBagConstraints();
		gbc_btnDown.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDown.anchor = GridBagConstraints.NORTH;
		gbc_btnDown.gridx = 3;
		gbc_btnDown.gridy = 1;
		panel.add(btnDown, gbc_btnDown);
		
		JPanel ElementBorder = new JPanel();
		TitledBorder elementTitle = new TitledBorder(null, "Element List", TitledBorder.LEADING, TitledBorder.TOP, null, null);
		TitledBorder elementEditTitle = new TitledBorder(null, "Element Editor", TitledBorder.LEADING, TitledBorder.LEFT, null, null);
		
		ElementBorder.setBorder(elementTitle);
		
		GridBagConstraints gbc_ElementBorder = new GridBagConstraints();
		gbc_ElementBorder.insets = new Insets(0, 0, 0, 5);
		gbc_ElementBorder.fill = GridBagConstraints.BOTH;
		gbc_ElementBorder.gridx = 0;
		gbc_ElementBorder.gridy = 1;
		getContentPane().add(ElementBorder, gbc_ElementBorder);
		GridBagLayout gbl_ElementBorder = new GridBagLayout();
		gbl_ElementBorder.columnWidths = new int[]{0, 0};
		gbl_ElementBorder.rowHeights = new int[]{0, 0};
		gbl_ElementBorder.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_ElementBorder.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		ElementBorder.setLayout(gbl_ElementBorder);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		ElementBorder.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JButton button = new JButton("Add Element");
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) JOptionPane.showInputDialog(null, "Select an element", "S1 VIZ", 
						JOptionPane.QUESTION_MESSAGE, null, UIWrapperManager.elementFlavoursPublic, UIWrapperManager.elementFlavoursPublic[0]);
				
				currentScene.addElement(UIWrapperManager.stringToElement(s));
			}
		});
		
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.insets = new Insets(0, 0, 5, 5);
		gbc_button.gridx = 0;
		gbc_button.gridy = 0;
		panel_1.add(button, gbc_button);
		
		JButton button_1 = new JButton("Delete Element");
		button_1.setEnabled(false);
		
		button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = currentElementList.getSelectedIndex();
				
				currentScene.getElementList().removeElement(index);
				
				if(currentScene.getElementList().size() == 0)
					button_1.setEnabled(false);
			}
		});
		
		GridBagConstraints gbc_button_1 = new GridBagConstraints();
		gbc_button_1.insets = new Insets(0, 0, 5, 5);
		gbc_button_1.gridx = 1;
		gbc_button_1.gridy = 0;
		panel_1.add(button_1, gbc_button_1);
		
		JButton button_2 = new JButton("Edit Element");
		
		button_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = currentElementList.getSelectedIndex();
				
				getContentPane().remove(AttributePanel);
				
				AttributePanel = currentScene.getElementList().getElement(index).getAttributesPanel();
				
				getContentPane().add(AttributePanel, gbc_AttributePanel);
				validate();
				repaint();
				
				elementEditTitle.setTitle("Editing " + currentScene.getElementList().getName(index));
			}
		});
		
		GridBagConstraints gbc_button_2 = new GridBagConstraints();
		gbc_button_2.insets = new Insets(0, 0, 5, 0);
		gbc_button_2.gridx = 2;
		gbc_button_2.gridy = 0;
		panel_1.add(button_2, gbc_button_2);

		currentElementList = currentScene.getElementList().getJList();
		
		JScrollPane scrollPane = new JScrollPane(currentElementList);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		panel_1.add(scrollPane, gbc_scrollPane);
		
		AttributePanel = new MarvinAttributesPanel();
		AttributePanel.setBorder(elementEditTitle);
		
		gbc_AttributePanel.gridx = 1;
		gbc_AttributePanel.gridy = 1;
		getContentPane().add(AttributePanel, gbc_AttributePanel);
		
		
		currentElementList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		//Scene edit listener
		btnEditScene.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = sceneJlist.getSelectedIndex();
				
				//Copy the elements of the selected scene into the current scene
				currentScene = sceneList.getElement(index);
				currentElementList.setModel(currentScene.getElementList().getListModel());
				elementTitle.setTitle("Editing " + sceneList.getName(index));
				repaint();
				button_1.setEnabled(true);
			}
		});
	}
}

package com.davesone.vis.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import org.marvinproject.image.test.plugin.Plugin;

import com.davesone.vis.core.Scene;
import com.davesone.vis.core.TextAndObjectList;
import com.davesone.vis.core.UIWrapperManager;
import com.davesone.vis.triggers.Element;
import com.davesone.vis.video.PluginCompatible;
import com.davesone.vis.video.plugins.PluginLoader;

import marvin.gui.MarvinAttributesPanel;

/**
 * TODO This is in desperate need of cleanup
 * @author Owner
 *
 */
public class MainFrame extends JFrame {
	
	private TextAndObjectList<Scene> sceneList;
	private Scene currentScene;//currently selected scene
	private JList currentElementList, AppliedPluginList;
	private MarvinAttributesPanel AttributePanel;
	private PluginLoader availablePlugins;
	
	private boolean unsavedChanges = false;
	
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
		availablePlugins = new PluginLoader();
		sceneList.addElement(currentScene, "Scene");
		
		JList sceneJlist = sceneList.getJList();
		setTitle("S1 VIZ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1132, 725);
		
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{369, 458, 0};
		gridBagLayout.rowHeights = new int[]{175, 180, 150, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JPanel SceneEditPanelBorder = new JPanel();
		SceneEditPanelBorder.setBorder(new TitledBorder(null, "Scene List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_SceneEditPanelBorder = new GridBagConstraints();
		gbc_SceneEditPanelBorder.fill = GridBagConstraints.BOTH;
		gbc_SceneEditPanelBorder.insets = new Insets(0, 0, 5, 5);
		gbc_SceneEditPanelBorder.gridx = 0;
		gbc_SceneEditPanelBorder.gridy = 0;
		getContentPane().add(SceneEditPanelBorder, gbc_SceneEditPanelBorder);
		GridBagLayout gbl_SceneEditPanelBorder = new GridBagLayout();
		gbl_SceneEditPanelBorder.columnWidths = new int[]{356, 0};
		gbl_SceneEditPanelBorder.rowHeights = new int[]{175, 0};
		gbl_SceneEditPanelBorder.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_SceneEditPanelBorder.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		SceneEditPanelBorder.setLayout(gbl_SceneEditPanelBorder);
		
		JPanel sceneEditPanel = new JPanel();
		GridBagConstraints gbc_sceneEditPanel = new GridBagConstraints();
		gbc_sceneEditPanel.anchor = GridBagConstraints.WEST;
		gbc_sceneEditPanel.fill = GridBagConstraints.VERTICAL;
		gbc_sceneEditPanel.gridx = 0;
		gbc_sceneEditPanel.gridy = 0;
		SceneEditPanelBorder.add(sceneEditPanel, gbc_sceneEditPanel);
		GridBagLayout gbl_sceneEditPanel = new GridBagLayout();
		gbl_sceneEditPanel.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_sceneEditPanel.rowHeights = new int[]{0, 65, 0};
		gbl_sceneEditPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_sceneEditPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		sceneEditPanel.setLayout(gbl_sceneEditPanel);
		
		JButton btnDeleteScene = new JButton("Delete Scene");
		
		JButton btnAddScene = new JButton("Add Scene");
		
		GridBagConstraints gbc_btnAddScene = new GridBagConstraints();
		gbc_btnAddScene.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddScene.gridx = 0;
		gbc_btnAddScene.gridy = 0;
		sceneEditPanel.add(btnAddScene, gbc_btnAddScene);
		
		
		GridBagConstraints gbc_btnDeleteScene = new GridBagConstraints();
		gbc_btnDeleteScene.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeleteScene.gridx = 1;
		gbc_btnDeleteScene.gridy = 0;
		sceneEditPanel.add(btnDeleteScene, gbc_btnDeleteScene);
		
		JButton btnEditScene = new JButton("Edit Scene");
		
		GridBagConstraints gbc_btnEditScene = new GridBagConstraints();
		gbc_btnEditScene.insets = new Insets(0, 0, 5, 5);
		gbc_btnEditScene.gridx = 2;
		gbc_btnEditScene.gridy = 0;
		sceneEditPanel.add(btnEditScene, gbc_btnEditScene);
		
		JScrollPane sceneListPane = new JScrollPane(sceneJlist);
		
		GridBagConstraints gbc_Scenelist = new GridBagConstraints();
		gbc_Scenelist.gridwidth = 3;
		gbc_Scenelist.insets = new Insets(0, 0, 0, 5);
		gbc_Scenelist.fill = GridBagConstraints.BOTH;
		gbc_Scenelist.gridx = 0;
		gbc_Scenelist.gridy = 1;
		sceneEditPanel.add(sceneListPane, gbc_Scenelist);
		
		JButton btnUp = new JButton("Move Up");
		
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUp.insets = new Insets(0, 0, 5, 0);
		gbc_btnUp.gridx = 3;
		gbc_btnUp.gridy = 0;
		sceneEditPanel.add(btnUp, gbc_btnUp);
		
		JButton btnDown = new JButton("Move Down");
		
		GridBagConstraints gbc_btnDown = new GridBagConstraints();
		gbc_btnDown.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDown.anchor = GridBagConstraints.NORTH;
		gbc_btnDown.gridx = 3;
		gbc_btnDown.gridy = 1;
		sceneEditPanel.add(btnDown, gbc_btnDown);
		
		
		sceneJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JPanel ElementEditPanelBorder = new JPanel();
		TitledBorder elementTitle = new TitledBorder(null, "Element List", TitledBorder.LEADING, TitledBorder.TOP, null, null);
		TitledBorder elementEditTitle = new TitledBorder(null, "Element Editor", TitledBorder.LEADING, TitledBorder.LEFT, null, null);
		
		JPanel PluginPanelBorder = new JPanel();
		PluginPanelBorder.setBorder(new TitledBorder(null, "Plugins Applied to selected Elment", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_PluginPanelBorder = new GridBagConstraints();
		gbc_PluginPanelBorder.fill = GridBagConstraints.BOTH;
		gbc_PluginPanelBorder.insets = new Insets(0, 0, 5, 0);
		gbc_PluginPanelBorder.gridx = 1;
		gbc_PluginPanelBorder.gridy = 0;
		getContentPane().add(PluginPanelBorder, gbc_PluginPanelBorder);
		GridBagLayout gbl_PluginPanelBorder = new GridBagLayout();
		gbl_PluginPanelBorder.columnWidths = new int[]{233, 166, 0};
		gbl_PluginPanelBorder.rowHeights = new int[]{28, 142, 0};
		gbl_PluginPanelBorder.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_PluginPanelBorder.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		PluginPanelBorder.setLayout(gbl_PluginPanelBorder);
		
		JButton btnDeletePlugin = new JButton("Remove Plugin");
		GridBagConstraints gbc_btnDeletePlugin = new GridBagConstraints();
		gbc_btnDeletePlugin.anchor = GridBagConstraints.NORTH;
		gbc_btnDeletePlugin.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeletePlugin.gridx = 0;
		gbc_btnDeletePlugin.gridy = 0;
		PluginPanelBorder.add(btnDeletePlugin, gbc_btnDeletePlugin);
		
		JButton btnModifySettings_1 = new JButton("Modify Settings");
		GridBagConstraints gbc_btnModifySettings_1 = new GridBagConstraints();
		gbc_btnModifySettings_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnModifySettings_1.gridx = 1;
		gbc_btnModifySettings_1.gridy = 0;
		PluginPanelBorder.add(btnModifySettings_1, gbc_btnModifySettings_1);
		
		Panel PluginPanel = new Panel();
		
		GridBagConstraints gbc_PluginPanel = new GridBagConstraints();
		gbc_PluginPanel.insets = new Insets(0, 0, 0, 5);
		gbc_PluginPanel.gridx = 0;
		gbc_PluginPanel.gridy = 1;
		PluginPanelBorder.add(PluginPanel, gbc_PluginPanel);
		
		AppliedPluginList = new JList();
		PluginPanel.add(AppliedPluginList);
		
		JPanel PluginSettingsBorder = new JPanel();
		PluginSettingsBorder.setBorder(new TitledBorder(null, "Plugin Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_PluginSettingsBorder = new GridBagConstraints();
		gbc_PluginSettingsBorder.fill = GridBagConstraints.BOTH;
		gbc_PluginSettingsBorder.gridx = 1;
		gbc_PluginSettingsBorder.gridy = 1;
		PluginPanelBorder.add(PluginSettingsBorder, gbc_PluginSettingsBorder);
		GridBagLayout gbl_PluginSettingsBorder = new GridBagLayout();
		gbl_PluginSettingsBorder.columnWidths = new int[]{166, 0};
		gbl_PluginSettingsBorder.rowHeights = new int[]{175, 0};
		gbl_PluginSettingsBorder.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_PluginSettingsBorder.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		PluginSettingsBorder.setLayout(gbl_PluginSettingsBorder);
		
		JPanel pluginSettings = new JPanel();
		GridBagConstraints gbc_pluginSettings = new GridBagConstraints();
		gbc_pluginSettings.fill = GridBagConstraints.BOTH;
		gbc_pluginSettings.gridx = 0;
		gbc_pluginSettings.gridy = 0;
		PluginSettingsBorder.add(pluginSettings, gbc_pluginSettings);
		
		ElementEditPanelBorder.setBorder(elementTitle);
		
		GridBagConstraints gbc_ElementEditPanelBorder = new GridBagConstraints();
		gbc_ElementEditPanelBorder.insets = new Insets(0, 0, 5, 5);
		gbc_ElementEditPanelBorder.fill = GridBagConstraints.BOTH;
		gbc_ElementEditPanelBorder.gridx = 0;
		gbc_ElementEditPanelBorder.gridy = 1;
		getContentPane().add(ElementEditPanelBorder, gbc_ElementEditPanelBorder);
		GridBagLayout gbl_ElementEditPanelBorder = new GridBagLayout();
		gbl_ElementEditPanelBorder.columnWidths = new int[]{0, 0};
		gbl_ElementEditPanelBorder.rowHeights = new int[]{0, 0};
		gbl_ElementEditPanelBorder.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_ElementEditPanelBorder.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		ElementEditPanelBorder.setLayout(gbl_ElementEditPanelBorder);
		
		JPanel ElementEditPanel = new JPanel();
		GridBagConstraints gbc_ElementEditPanel = new GridBagConstraints();
		gbc_ElementEditPanel.fill = GridBagConstraints.BOTH;
		gbc_ElementEditPanel.gridx = 0;
		gbc_ElementEditPanel.gridy = 0;
		ElementEditPanelBorder.add(ElementEditPanel, gbc_ElementEditPanel);
		GridBagLayout gbl_ElementEditPanel = new GridBagLayout();
		gbl_ElementEditPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_ElementEditPanel.rowHeights = new int[]{0, 0, 0};
		gbl_ElementEditPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_ElementEditPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		ElementEditPanel.setLayout(gbl_ElementEditPanel);
		
		JButton btnDeleteElement = new JButton("Delete Element");
		
		GridBagConstraints gbc_btnDeleteElement = new GridBagConstraints();
		gbc_btnDeleteElement.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeleteElement.gridx = 1;
		gbc_btnDeleteElement.gridy = 0;
		ElementEditPanel.add(btnDeleteElement, gbc_btnDeleteElement);
		
		JButton btnAddElement = new JButton("Add Element");
		
		GridBagConstraints gbc_btnAddElement = new GridBagConstraints();
		gbc_btnAddElement.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddElement.gridx = 0;
		gbc_btnAddElement.gridy = 0;
		ElementEditPanel.add(btnAddElement, gbc_btnAddElement);
		
		JButton btnEditElement = new JButton("Edit Element");
		
		GridBagConstraints gbc_btnEditElement = new GridBagConstraints();
		gbc_btnEditElement.insets = new Insets(0, 0, 5, 0);
		gbc_btnEditElement.gridx = 2;
		gbc_btnEditElement.gridy = 0;
		ElementEditPanel.add(btnEditElement, gbc_btnEditElement);

		currentElementList = currentScene.getElementList().getJList();
		currentElementList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane ElementListPane = new JScrollPane(currentElementList);
		GridBagConstraints gbc_ElementListPane = new GridBagConstraints();
		gbc_ElementListPane.fill = GridBagConstraints.BOTH;
		gbc_ElementListPane.gridwidth = 3;
		gbc_ElementListPane.insets = new Insets(0, 0, 0, 5);
		gbc_ElementListPane.gridx = 0;
		gbc_ElementListPane.gridy = 1;
		ElementEditPanel.add(ElementListPane, gbc_ElementListPane);
		
		JPanel ElementAttributeEditor = new JPanel();
		ElementAttributeEditor.setBorder(elementEditTitle);
		GridBagConstraints gbc_ElementAttributeEditor = new GridBagConstraints();
		gbc_ElementAttributeEditor.insets = new Insets(0, 0, 5, 0);
		gbc_ElementAttributeEditor.fill = GridBagConstraints.BOTH;
		gbc_ElementAttributeEditor.gridx = 1;
		gbc_ElementAttributeEditor.gridy = 1;
		getContentPane().add(ElementAttributeEditor, gbc_ElementAttributeEditor);
		GridBagLayout gbl_ElementAttributeEditor = new GridBagLayout();
		gbl_ElementAttributeEditor.columnWidths = new int[]{415, 0};
		gbl_ElementAttributeEditor.rowHeights = new int[]{0, 0};
		gbl_ElementAttributeEditor.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_ElementAttributeEditor.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		ElementAttributeEditor.setLayout(gbl_ElementAttributeEditor);
		
		AttributePanel = new MarvinAttributesPanel();
		GridBagConstraints gbc_AttributePanel = new GridBagConstraints();
		gbc_AttributePanel.gridx = 0;
		gbc_AttributePanel.gridy = 0;
		ElementAttributeEditor.add(AttributePanel, gbc_AttributePanel);
		
		JPanel PluginListBorder = new JPanel();
		PluginListBorder.setBorder(new TitledBorder(null, "Available Plugins", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_PluginListBorder = new GridBagConstraints();
		gbc_PluginListBorder.fill = GridBagConstraints.BOTH;
		gbc_PluginListBorder.insets = new Insets(0, 0, 0, 5);
		gbc_PluginListBorder.gridx = 0;
		gbc_PluginListBorder.gridy = 2;
		getContentPane().add(PluginListBorder, gbc_PluginListBorder);
		GridBagLayout gbl_PluginListBorder = new GridBagLayout();
		gbl_PluginListBorder.columnWidths = new int[]{332, 0};
		gbl_PluginListBorder.rowHeights = new int[]{129, 0};
		gbl_PluginListBorder.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_PluginListBorder.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		PluginListBorder.setLayout(gbl_PluginListBorder);
		
		Panel PluginListPanel = new Panel();
		FlowLayout flowLayout = (FlowLayout) PluginListPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		GridBagConstraints gbc_PluginListPanel = new GridBagConstraints();
		gbc_PluginListPanel.fill = GridBagConstraints.BOTH;
		gbc_PluginListPanel.gridx = 0;
		gbc_PluginListPanel.gridy = 0;
		PluginListBorder.add(PluginListPanel, gbc_PluginListPanel);
		
		JScrollPane pluginscroller = new JScrollPane();
		PluginListPanel.add(pluginscroller);
		
		JList PluginList = new JList(availablePlugins.getPluginModel());
		pluginscroller.setViewportView(PluginList);
		
		JButton btnAddToSelected = new JButton("Add");
		PluginListPanel.add(btnAddToSelected);
		
		
		btnDeleteScene.setEnabled(false);
		btnDeleteElement.setEnabled(false);
		btnAddToSelected.setEnabled(false);
		btnDeletePlugin.setEnabled(false);
		btnModifySettings_1.setEnabled(false);
		
		
		
		//Add scene Listener
		btnAddScene.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				sceneList.addElement(new Scene(), "Scene");
				btnDeleteScene.setEnabled(true);
			}
		});
		
		//Delete scene listener
		btnDeleteScene.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = sceneJlist.getSelectedIndex();
				
				sceneList.removeElement(index);
				
				if(sceneList.size() == 0)
					btnDeleteScene.setEnabled(false);
			}
		});
		
		//Scene up listener
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
		
		//Scene down listener
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
				btnDeleteElement.setEnabled(true);
			}
		});
		
		//Add element listener
		btnAddElement.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) JOptionPane.showInputDialog(null, "Select an element", "S1 VIZ", 
						JOptionPane.QUESTION_MESSAGE, null, UIWrapperManager.elementFlavoursPublic,
						UIWrapperManager.elementFlavoursPublic[0]);
				
				currentScene.addElement(UIWrapperManager.stringToElement(s));
				btnDeleteElement.setEnabled(true);
			}
		});
		
		//Delete element listener
		btnDeleteElement.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = currentElementList.getSelectedIndex();
				
				currentScene.getElementList().removeElement(index);
				
				if(currentScene.getElementList().size() == 0)
					btnDeleteElement.setEnabled(false);
			}
		});
		
		//Element Edit Listener
		btnEditElement.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = currentElementList.getSelectedIndex();
				if(!unsavedChanges) {
					
					ElementAttributeEditor.remove(AttributePanel);
					
					Element currentElement = currentScene.getElementList().getElement(index);
					
					AttributePanel = currentElement.getAttributesPanel();
					
					if(currentElement instanceof PluginCompatible) {
						DefaultListModel<String> m = new DefaultListModel<String>();
							
						for(int i = 0; i < ((PluginCompatible)currentElement).getPlugins().size(); i++) {
							m.addElement(((PluginCompatible)currentElement).getPlugins().get(i).getName());
						}
						AppliedPluginList.setModel(m);
						
						if(m.size() > 0) {
							btnDeletePlugin.setEnabled(true);
							btnModifySettings_1.setEnabled(true);
						}
						
						btnAddToSelected.setEnabled(true);
					}else {//Ensure plugins only added to compatible elements
						btnAddToSelected.setEnabled(false);
					}
					
					ElementAttributeEditor.add(AttributePanel, gbc_AttributePanel);
					validate();
					repaint();
					
					elementEditTitle.setTitle("Editing " + currentScene.getElementList().getName(index));
					
					btnEditElement.setText("Save Changes");
					btnEditElement.setBackground(Color.RED);
					unsavedChanges = true;
				}else {
					currentScene.getElementList().getElement(index).getAttributesPanel().applyValues();
					btnEditElement.setText("Edit Element");
					btnEditElement.setBackground(Color.GREEN);
					unsavedChanges = false;
					
					btnDeletePlugin.setEnabled(false);
					btnModifySettings_1.setEnabled(false);
				}
			}
		});
		
		//Add plugins button
		btnAddToSelected.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = currentElementList.getSelectedIndex();
				
				//Button should only be activated if element is PC
				//so casting without check should be safe
				PluginCompatible currentElement = (PluginCompatible)currentScene.getElementList().getElement(index);
				
			}
		});
		
		//Plugin settings modify
		btnModifySettings_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		//Plugin delete
		btnDeletePlugin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
	}
}

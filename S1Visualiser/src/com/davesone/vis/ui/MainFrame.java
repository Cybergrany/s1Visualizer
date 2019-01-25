package com.davesone.vis.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;

import com.davesone.vis.audio.AudioStreamHandler;
import com.davesone.vis.core.Localization;
import com.davesone.vis.core.Scene;
import com.davesone.vis.core.TextAndObjectList;
import com.davesone.vis.core.UIWrapperManager;
import com.davesone.vis.triggers.Element;
import com.davesone.vis.triggers.Trigger;
import com.davesone.vis.triggers.TriggerException;
import com.davesone.vis.video.PluginCompatible;
import com.davesone.vis.video.plugins.PluginContainer;
import com.davesone.vis.video.plugins.PluginLoader;

import marvin.gui.MarvinAttributesPanel;

/**
 * TODO This is in desperate need of cleanup
 * @author Owner
 *
 */
public class MainFrame extends JFrame {
	
	private TextAndObjectList<Scene> sceneList;
	private DefaultListModel<String> appliedPlugins;

	private MarvinAttributesPanel AttributePanel, pluginAttributePanel, defaultAttributePanelState;
	private OscilliscopeMonitorPanel oscilliscopePanel;
	private Scene currentScene;//currently selected scene
	private Trigger currentTrigger;
	private PluginLoader availablePlugins;
	
	private AudioStreamHandler audioHandler;//Audio
	
	//Elements that need to be accessed from listners=
	private JPanel ElementAttributeEditor, PluginSettingsBorder, audiomonitorborder;
	private JList currentElementList, AppliedPluginList, pluginList, sceneJlist;
	private TitledBorder elementTitle, elementEditTitle;
	private ButtonGroup sceneButtons, elementButtons;
	private JButton btnDeleteScene, btnAddScene, btnEditScene, btnUp, btnDown,//Scene
					btnDeletePlugin, btnModifySettings_1, btnAddToSelected,//Plugins
					btnSoundInput, btnSoundTrigger,//Sound
					btnDeleteElement, btnAddElement, btnEditElement;//Element
	private GridBagConstraints gbc_AttributePanel, gbc_pluginSettings;
	
	private boolean unsavedChanges = false, unsavedPluginChanges = false;
	
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
	
	private void init() {
		audioHandler = new AudioStreamHandler();
		
		sceneButtons = new ButtonGroup();
		elementButtons = new ButtonGroup();
		
		sceneList = new TextAndObjectList<Scene>();
		currentScene = new Scene();
		availablePlugins = new PluginLoader();
		
		btnDeleteScene = new JButton("Delete Scene");
		sceneButtons.add(btnDeleteScene);
		
		btnAddScene = new JButton("Add Scene");
		sceneButtons.add(btnAddScene);
		
		
		btnEditScene = new JButton("Edit Scene");
		sceneButtons.add(btnEditScene);
		
		AppliedPluginList = new JList();
		
		btnAddPlugin = new JButton("Add Plugin");
		panel.add(btnAddPlugin);
		panel = new JPanel();
		oscilliscopePanel = new OscilliscopeMonitorPanel();
		btnDown = new JButton("Move Down");
		sceneButtons.add(btnDown);
		
		btnUp = new JButton("Move Up");
		sceneButtons.add(btnUp);
		
		btnModifySettings_1 = new JButton("Modify Settings");
		btnAddToSelected = new JButton("Add");
		
		btnDeletePlugin = new JButton("Remove Plugin");
		panel.add(btnDeletePlugin);
		
		defaultAttributePanelState = new MarvinAttributesPanel();
		defaultAttributePanelState.addLabel("defLab", "No plugin with modifyible attributes selcted");
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		init();
		
		setResizable(false);
		
		sceneList.addElement(currentScene, "Scene");
		
		sceneJlist = sceneList.getJList();
		setTitle("S1 VIZ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1300, 725);
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{369, 458, 349, 0};
		gridBagLayout.rowHeights = new int[]{175, 180, 150, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		gbl_SceneEditPanelBorder.rowHeights = new int[]{175, 0, 0};
		gbl_SceneEditPanelBorder.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_SceneEditPanelBorder.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		SceneEditPanelBorder.setLayout(gbl_SceneEditPanelBorder);
		
		JPanel sceneEditPanel = new JPanel();
		GridBagConstraints gbc_sceneEditPanel = new GridBagConstraints();
		gbc_sceneEditPanel.insets = new Insets(0, 0, 5, 0);
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
		
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnUp.insets = new Insets(0, 0, 5, 0);
		gbc_btnUp.gridx = 3;
		gbc_btnUp.gridy = 0;
		sceneEditPanel.add(btnUp, gbc_btnUp);
		
		GridBagConstraints gbc_btnDown = new GridBagConstraints();
		gbc_btnDown.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDown.anchor = GridBagConstraints.NORTH;
		gbc_btnDown.gridx = 3;
		gbc_btnDown.gridy = 1;
		sceneEditPanel.add(btnDown, gbc_btnDown);
		
		sceneJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		elementTitle = new TitledBorder(null, "Element List", TitledBorder.LEADING, TitledBorder.TOP, null, null);
		elementEditTitle = new TitledBorder(null, "Element Editor", TitledBorder.LEADING, TitledBorder.LEFT, null, null);
		
		ElementAttributeEditor = new JPanel();
		ElementAttributeEditor.setBorder(elementEditTitle);
		GridBagConstraints gbc_ElementAttributeEditor = new GridBagConstraints();
		gbc_ElementAttributeEditor.insets = new Insets(0, 0, 5, 5);
		gbc_ElementAttributeEditor.fill = GridBagConstraints.BOTH;
		gbc_ElementAttributeEditor.gridx = 1;
		gbc_ElementAttributeEditor.gridy = 0;
		getContentPane().add(ElementAttributeEditor, gbc_ElementAttributeEditor);
		GridBagLayout gbl_ElementAttributeEditor = new GridBagLayout();
		gbl_ElementAttributeEditor.columnWidths = new int[]{415, 0};
		gbl_ElementAttributeEditor.rowHeights = new int[]{0, 0};
		gbl_ElementAttributeEditor.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_ElementAttributeEditor.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		ElementAttributeEditor.setLayout(gbl_ElementAttributeEditor);
		
		AttributePanel = new MarvinAttributesPanel();
		gbc_AttributePanel = new GridBagConstraints();
		gbc_AttributePanel.gridx = 0;
		gbc_AttributePanel.gridy = 0;
		ElementAttributeEditor.add(AttributePanel, gbc_AttributePanel);
		
		JPanel AudioSettingsBorder = new JPanel();
		AudioSettingsBorder.setBorder(new TitledBorder(null, "Audio Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_AudioSettingsBorder = new GridBagConstraints();
		gbc_AudioSettingsBorder.fill = GridBagConstraints.BOTH;
		gbc_AudioSettingsBorder.insets = new Insets(0, 0, 5, 0);
		gbc_AudioSettingsBorder.gridx = 2;
		gbc_AudioSettingsBorder.gridy = 0;
		getContentPane().add(AudioSettingsBorder, gbc_AudioSettingsBorder);
		GridBagLayout gbl_AudioSettingsBorder = new GridBagLayout();
		gbl_AudioSettingsBorder.columnWidths = new int[]{192, 0};
		gbl_AudioSettingsBorder.rowHeights = new int[]{175, 0, 0};
		gbl_AudioSettingsBorder.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_AudioSettingsBorder.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		AudioSettingsBorder.setLayout(gbl_AudioSettingsBorder);
		
		JPanel AudioSettings = new JPanel();
		GridBagConstraints gbc_AudioSettings = new GridBagConstraints();
		gbc_AudioSettings.fill = GridBagConstraints.HORIZONTAL;
		gbc_AudioSettings.insets = new Insets(0, 0, 5, 0);
		gbc_AudioSettings.anchor = GridBagConstraints.NORTH;
		gbc_AudioSettings.gridx = 0;
		gbc_AudioSettings.gridy = 0;
		AudioSettingsBorder.add(AudioSettings, gbc_AudioSettings);
		
		btnSoundInput = new JButton("Sound Input");
		AudioSettings.add(btnSoundInput);
		
		btnSoundTrigger = new JButton("Sound Trigger");
		AudioSettings.add(btnSoundTrigger);
		
		audiomonitorborder = new JPanel();
		GridBagConstraints gbc_audiomonitorborder = new GridBagConstraints();
		gbc_audiomonitorborder.gridx = 0;
		gbc_audiomonitorborder.gridy = 1;
		AudioSettingsBorder.add(audiomonitorborder, gbc_audiomonitorborder);
		audiomonitorborder.setBorder(new TitledBorder(null, "Audio Monitoring", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagLayout gbl_audiomonitorborder = new GridBagLayout();
		gbl_audiomonitorborder.columnWidths = new int[]{349, 0};
		gbl_audiomonitorborder.rowHeights = new int[]{150, 0};
		gbl_audiomonitorborder.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_audiomonitorborder.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		audiomonitorborder.setLayout(gbl_audiomonitorborder);
		
		
		GridBagConstraints gbc_audiomonitorpanel = new GridBagConstraints();
		gbc_audiomonitorpanel.fill = GridBagConstraints.BOTH;
		gbc_audiomonitorpanel.gridx = 0;
		gbc_audiomonitorpanel.gridy = 0;
		audiomonitorborder.add(oscilliscopePanel, gbc_audiomonitorpanel);

		currentElementList = currentScene.getElementList().getJList();
		currentElementList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JPanel PluginPanelBorder = new JPanel();
		PluginPanelBorder.setBorder(new TitledBorder(null, "Plugins Applied to selected Elment", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_PluginPanelBorder = new GridBagConstraints();
		gbc_PluginPanelBorder.fill = GridBagConstraints.BOTH;
		gbc_PluginPanelBorder.insets = new Insets(0, 0, 5, 5);
		gbc_PluginPanelBorder.gridx = 0;
		gbc_PluginPanelBorder.gridy = 1;
		getContentPane().add(PluginPanelBorder, gbc_PluginPanelBorder);
		GridBagLayout gbl_PluginPanelBorder = new GridBagLayout();
		gbl_PluginPanelBorder.columnWidths = new int[]{233, 166, 0};
		gbl_PluginPanelBorder.rowHeights = new int[]{28, 142, 0};
		gbl_PluginPanelBorder.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_PluginPanelBorder.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		PluginPanelBorder.setLayout(gbl_PluginPanelBorder);
		
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		PluginPanelBorder.add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		GridBagConstraints gbc_btnModifySettings_1 = new GridBagConstraints();
		gbc_btnModifySettings_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnModifySettings_1.gridx = 1;
		gbc_btnModifySettings_1.gridy = 0;
		PluginPanelBorder.add(btnModifySettings_1, gbc_btnModifySettings_1);
		
		Panel PluginPanel = new Panel();
		FlowLayout flowLayout_1 = (FlowLayout) PluginPanel.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		PluginPanel.add(AppliedPluginList);
		
		GridBagConstraints gbc_PluginPanel = new GridBagConstraints();
		gbc_PluginPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc_PluginPanel.insets = new Insets(0, 0, 0, 5);
		gbc_PluginPanel.gridx = 0;
		gbc_PluginPanel.gridy = 1;
		PluginPanelBorder.add(PluginPanel, gbc_PluginPanel);
		
		PluginSettingsBorder = new JPanel();
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
		
		pluginAttributePanel = defaultAttributePanelState;
		gbc_pluginSettings = new GridBagConstraints();
		gbc_pluginSettings.fill = GridBagConstraints.BOTH;
		gbc_pluginSettings.gridx = 0;
		gbc_pluginSettings.gridy = 0;
		PluginSettingsBorder.add(pluginAttributePanel, gbc_pluginSettings);
		
		JPanel ElementEditPanelBorder = new JPanel();
		GridBagConstraints gbc_ElementEditPanelBorder = new GridBagConstraints();
		gbc_ElementEditPanelBorder.anchor = GridBagConstraints.WEST;
		gbc_ElementEditPanelBorder.gridx = 0;
		gbc_ElementEditPanelBorder.gridy = 1;
		SceneEditPanelBorder.add(ElementEditPanelBorder, gbc_ElementEditPanelBorder);
		
		ElementEditPanelBorder.setBorder(elementTitle);
		GridBagLayout gbl_ElementEditPanelBorder = new GridBagLayout();
		gbl_ElementEditPanelBorder.columnWidths = new int[]{0, 0};
		gbl_ElementEditPanelBorder.rowHeights = new int[]{140, 0};
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
		
		btnDeleteElement = new JButton("Delete Element");
		elementButtons.add(btnDeleteElement);
		
		GridBagConstraints gbc_btnDeleteElement = new GridBagConstraints();
		gbc_btnDeleteElement.insets = new Insets(0, 0, 5, 5);
		gbc_btnDeleteElement.gridx = 1;
		gbc_btnDeleteElement.gridy = 0;
		ElementEditPanel.add(btnDeleteElement, gbc_btnDeleteElement);
		
		btnAddElement = new JButton("Add Element");
		elementButtons.add(btnAddElement);
		
		GridBagConstraints gbc_btnAddElement = new GridBagConstraints();
		gbc_btnAddElement.insets = new Insets(0, 0, 5, 5);
		gbc_btnAddElement.gridx = 0;
		gbc_btnAddElement.gridy = 0;
		ElementEditPanel.add(btnAddElement, gbc_btnAddElement);
		
		btnEditElement = new JButton("Edit Element");
		elementButtons.add(btnEditElement);
		
		GridBagConstraints gbc_btnEditElement = new GridBagConstraints();
		gbc_btnEditElement.insets = new Insets(0, 0, 5, 0);
		gbc_btnEditElement.gridx = 2;
		gbc_btnEditElement.gridy = 0;
		ElementEditPanel.add(btnEditElement, gbc_btnEditElement);
		
		JScrollPane ElementListPane = new JScrollPane(currentElementList);
		GridBagConstraints gbc_ElementListPane = new GridBagConstraints();
		gbc_ElementListPane.fill = GridBagConstraints.BOTH;
		gbc_ElementListPane.gridwidth = 3;
		gbc_ElementListPane.insets = new Insets(0, 0, 0, 5);
		gbc_ElementListPane.gridx = 0;
		gbc_ElementListPane.gridy = 1;
		ElementEditPanel.add(ElementListPane, gbc_ElementListPane);
		
		
		btnAddPlugin.setEnabled(false);
		btnModifySettings_1.setEnabled(false);
		setButtonGroupEnabled(elementButtons, false);
		btnDeleteScene.setEnabled(false);
		btnDeletePlugin.setEnabled(false);
		
		//Plugin delete
		btnDeletePlugin.addActionListener(deletePlugin);
		
		//Add element listener
		btnAddElement.addActionListener(addElement);
		
		//Plugin settings modify
		btnModifySettings_1.addActionListener(modifyPluginSettings);
		
		//Delete element listener
		btnDeleteElement.addActionListener(deleteElement);
		
		//Element Edit Listener
		btnEditElement.addActionListener(editElement);
		
		//Add plugins button
		btnAddToSelected.addActionListener(addPlugins);
		
		//Add plugin
		btnAddPlugin.addActionListener(launchPluginChooser);
		
		//Add scene Listener
		btnAddScene.addActionListener(addScene);
		
		//Delete scene listener
		btnDeleteScene.addActionListener(deleteScene);
		
		//Scene up listener
		btnUp.addActionListener(mvSceneUp);
		
		//Scene down listener
		btnDown.addActionListener(mvSceneDown);
		
		//Scene edit listener
		btnEditScene.addActionListener(editScene);
		
		//Sound trigger setup
		btnSoundTrigger.addActionListener(enterTriggerSetup);
		
		//Sound setup
		btnSoundInput.addActionListener(soundInputSetup);
		
	}
	
	/**
	 * Set boolean value of group
	 * @param b
	 */
	private void setButtonGroupEnabled(ButtonGroup b, boolean tof) {
		Enumeration<AbstractButton> e = b.getElements();
		
		while(e.hasMoreElements()) {
			e.nextElement().setEnabled(tof);
		}
	}
	
	private void showError(String text, Frame f) {
		JOptionPane.showMessageDialog(f, text, "S1 Viz Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * JDIALOGS
	 */
	
	private MarvinAttributesPanel currentTriggerPanel;
	
	private JDialog triggerChooser() {
		JDialog d = new JDialog(this, "Choose a trigger type", true);
		JButton btnConfirm = new JButton("Confirm");
		d.getContentPane().setLayout(new BorderLayout());
		final JComboBox<String> triggerList = new JComboBox<String>(UIWrapperManager.elementFlavors);
//		final JOptionPane opane = new JOptionPane("", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		
		if(currentTrigger == null) {
			currentTrigger = UIWrapperManager.stringToTrigger(triggerList.getItemAt(triggerList.getSelectedIndex()));
		}
		
		if(currentTriggerPanel == null) {
			currentTriggerPanel = currentTrigger.getAttributesPanel();
		}
		
		triggerList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				d.remove(currentTriggerPanel);
//				d.getContentPane().remove(currentTriggerPanel);
				currentTrigger = UIWrapperManager.stringToTrigger(triggerList.getItemAt(triggerList.getSelectedIndex()));
				currentTriggerPanel = currentTrigger.getAttributesPanel();
//				currentTriggerPanel = UIWrapperManager.stringToTrigger(triggerList.getItemAt(triggerList.getSelectedIndex())).getAttributesPanel();
				d.getContentPane().add(currentTriggerPanel, BorderLayout.CENTER);
//				d.getContentPane().add(currentTriggerPanel);
				d.validate();
				d.repaint();
			}
		});
		
		btnConfirm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				currentTriggerPanel.applyValues();
				d.dispose();
			}
		});
		
		d.getContentPane().add(triggerList, BorderLayout.NORTH);
		d.getContentPane().add(currentTriggerPanel, BorderLayout.CENTER);
		d.getContentPane().add(btnConfirm, BorderLayout.SOUTH);
		
		
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);//TODO temp	
		
		d.pack();
		d.setVisible(true);
		
		return d;
	}
	
	/**
	 * Adaptation of mixerchooserpanel
	 * @return
	 */
	private JDialog mixerChooser() {
		JDialog d = new JDialog(this, "Choose a stream", true);
		
		JPanel chooserPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(0, 1));
		
		ButtonGroup group = new ButtonGroup();
		
		for(Mixer.Info info: audioHandler.getMixerInfo(false, true)) {
			JRadioButton b = new JRadioButton();
			b.setText(Localization.toLocalString(info));
			buttonPanel.add(b);
			group.add(b);
			b.setActionCommand(info.toString());
			b.addActionListener(mixerChooserButtonAction);
		}
		
		chooserPanel.add(new JScrollPane(buttonPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
		
		d.getContentPane().add(chooserPanel);
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		d.pack();
		d.setVisible(true);
		
		return d;
	}
	
	private JDialog pluginChooser () {
		JDialog d = new JDialog(this, "Choose a plugin", true);
		JButton b = new JButton("Ok");
		b.addActionListener(addPlugins);
		Panel p = new Panel();
		pluginList = new JList(availablePlugins.getPluginModel());
		JScrollPane pane = new JScrollPane(pluginList);
		p.add(pane);
		
		d.getContentPane().setLayout(new BorderLayout());
		
		d.getContentPane().add(p, BorderLayout.NORTH);
		d.getContentPane().add(b, BorderLayout.EAST);
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		d.pack();
		d.setVisible(true);
		return d;
	}
	
	/**
	 * ACTION LISTENERS
	 */
	
	private ActionListener addScene = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			sceneList.addElement(new Scene(), "Scene");
			btnDeleteScene.setEnabled(true);
		}
	};
	
	private ActionListener deleteScene = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = sceneJlist.getSelectedIndex();
			
			sceneList.removeElement(index);
			
			if(sceneList.size() == 0)
				btnDeleteScene.setEnabled(false);

		}
	};
	
	private ActionListener mvSceneUp = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = sceneJlist.getSelectedIndex();
			 
			if(index > 0) {
				sceneList.swapElement(index, index - 1);
				
				sceneJlist.setSelectedIndex(index - 1);
			}
		}
	};
	
	private ActionListener mvSceneDown = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = sceneJlist.getSelectedIndex();
			
			if(index < sceneList.size() - 1) {
				sceneList.swapElement(index, index + 1);
				
				sceneJlist.setSelectedIndex(index + 1);
			}
		}
	};
	
	private ActionListener editScene = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = sceneJlist.getSelectedIndex();
			
			if(index == -1) {//Nothing selected
				showError("Please select a scene", null);
			}else {
				//Copy the elements of the selected scene into the current scene
				currentScene = sceneList.getElement(index);
				currentElementList.setModel(currentScene.getElementList().getListModel());
				elementTitle.setTitle("Editing " + sceneList.getName(index));
				repaint();
				btnDeleteElement.setEnabled(true);
				setButtonGroupEnabled(elementButtons, true);
			}
		}
	};
	
	private ActionListener addElement = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String s = (String) JOptionPane.showInputDialog(null, "Select an element", "S1 VIZ", 
					JOptionPane.QUESTION_MESSAGE, null, UIWrapperManager.elementFlavoursPublic,
					UIWrapperManager.elementFlavoursPublic[0]);
			
			currentScene.addElement(UIWrapperManager.stringToElement(s));
			btnDeleteElement.setEnabled(true);
		}
	};
	
	private ActionListener deleteElement = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = currentElementList.getSelectedIndex();
			
			currentScene.getElementList().removeElement(index);
			
			if(currentScene.getElementList().size() == 0)
				btnDeleteElement.setEnabled(false);
		}
	};
	
	private ActionListener editElement = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = currentElementList.getSelectedIndex();
			
			if(index == -1) {
				showError("Please select an element", null);
			}else {
				if(!unsavedChanges) {
					
					ElementAttributeEditor.remove(AttributePanel);
					
					Element currentElement = currentScene.getElementList().getElement(index);
					
					AttributePanel = currentElement.getAttributesPanel();
					
					if(currentElement instanceof PluginCompatible) {
//						DefaultListModel<String> m = new DefaultListModel<String>();
							
//						for(int i = 0; i < ((PluginCompatible)currentElement).getPlugins().size(); i++) {
//							m.addElement(((PluginCompatible)currentElement).getPlugins().get(i).getName());
//						}
						
						appliedPlugins = new DefaultListModel<String>();
						
						for(int i = 0; i < ((PluginCompatible)currentElement).getPlugins().size(); i++) {
							appliedPlugins.addElement(((PluginCompatible)currentElement).getPlugins().get(i).getName());
						}
						
						AppliedPluginList.setModel(appliedPlugins);
						
						if(appliedPlugins.size() > 0) {
							btnDeletePlugin.setEnabled(true);
							btnModifySettings_1.setEnabled(true);
						}
						btnAddPlugin.setEnabled(true);
						
					}else {//Ensure plugins only added to compatible elements
						btnAddPlugin.setEnabled(false);
					}
					
					ElementAttributeEditor.add(AttributePanel, gbc_AttributePanel);
					elementEditTitle.setTitle("Editing " + currentScene.getElementList().getName(index));
					
					sceneJlist.setEnabled(false);
					currentElementList.setEnabled(false);
					
					setButtonGroupEnabled(elementButtons, false);
					btnEditElement.setEnabled(true);//override
					setButtonGroupEnabled(sceneButtons, false);
					
					validate();
					repaint();
					
					
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
					setButtonGroupEnabled(elementButtons, true);
					setButtonGroupEnabled(sceneButtons, true);
					
					sceneJlist.setEnabled(true);
					currentElementList.setEnabled(true);
					PluginSettingsBorder.remove(pluginAttributePanel);
					PluginSettingsBorder.add(defaultAttributePanelState, gbc_pluginSettings);
				}
			}
		}
	};
	
	private ActionListener launchPluginChooser = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog d = pluginChooser();
		}
	};
	
	private ActionListener addPlugins = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			int elementIndex = currentElementList.getSelectedIndex();
			int selectedPluginIndex = pluginList.getSelectedIndex();
			
			//Button should only be activated if element is PlugComp
			//so casting without check should be safe
			PluginCompatible currentElement = (PluginCompatible)currentScene.getElementList().getElement(elementIndex);
			
			if(!currentElement.getPlugins().contains(availablePlugins.plugins.get(selectedPluginIndex))) {//No adding twice
				currentElement.addPlugin(availablePlugins.plugins.get(selectedPluginIndex));
				appliedPlugins.addElement(availablePlugins.plugins.get(selectedPluginIndex).getName());
				
				btnDeletePlugin.setEnabled(true);
				btnModifySettings_1.setEnabled(true);
			}
			
			
		}
	};
	
	private ActionListener modifyPluginSettings = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = AppliedPluginList.getSelectedIndex();
			PluginCompatible currentElement = (PluginCompatible)currentScene.getElementList().getElement(currentElementList.getSelectedIndex());
			PluginContainer plugin = currentElement.getPlugins().get(index);

			PluginSettingsBorder.remove(pluginAttributePanel);
			
			if (plugin.hasAttributesPanel()) {
				
				if(!unsavedPluginChanges) {
					pluginAttributePanel = plugin.getPlugin().getAttributesPanel();
					PluginSettingsBorder.add(pluginAttributePanel, gbc_pluginSettings);
					btnModifySettings_1.setBackground(Color.RED);
					btnModifySettings_1.setText("Save Changes");
					
					AppliedPluginList.setEnabled(false);
					unsavedPluginChanges = true;
					
				}else {
					plugin.getPlugin().getAttributesPanel().applyValues();
					btnModifySettings_1.setBackground(Color.GREEN);
					btnModifySettings_1.setText("Modify Settings");
					
					pluginAttributePanel = defaultAttributePanelState;
					PluginSettingsBorder.add(pluginAttributePanel, gbc_pluginSettings);
					
					AppliedPluginList.setEnabled(true);
					unsavedPluginChanges = false;
				}
			}else {
				pluginAttributePanel = defaultAttributePanelState;
				PluginSettingsBorder.add(pluginAttributePanel, gbc_pluginSettings);
			}
			PluginSettingsBorder.validate();
			PluginSettingsBorder.repaint();
		}
	};
	
	private ActionListener deletePlugin = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int index = AppliedPluginList.getSelectedIndex();
			int elementIndex = currentElementList.getSelectedIndex();

			PluginCompatible currentElement = (PluginCompatible)currentScene.getElementList().getElement(elementIndex);
			
			currentElement.removePlugin(index);
			appliedPlugins.removeElementAt(index);
			
			if(appliedPlugins.size() == 0)
				btnDeletePlugin.setEnabled(false);
		}
	};
	
	private ActionListener enterTriggerSetup = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog d = triggerChooser();
		}
	};
	
	private ActionListener mixerChooserButtonAction = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			for(Mixer.Info info : audioHandler.getMixerInfo(false, true)) {
				if(e.getActionCommand().equals(info.toString())) {
					Mixer m = AudioSystem.getMixer(info);
					try {
						audioHandler.setNewMixer(m);
						oscilliscopePanel.init(audioHandler);
						if(!oscilliscopePanel.isInitialized()) {
							oscilliscopePanel.init(audioHandler);
						}else {
							oscilliscopePanel.refreshOscilloscope();
						}
					} catch (LineUnavailableException | UnsupportedAudioFileException e1) {
						showError("Severe error while setting audio mixer", null);
					} catch (TriggerException e2) {
						showError("Failed to start visual audio monitoring", null);
					}
					break;
				}
			}
		}
	};
	
	private ActionListener soundInputSetup = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog d = mixerChooser();
		}
	};
	private JPanel panel;
	private JButton btnAddPlugin;
}

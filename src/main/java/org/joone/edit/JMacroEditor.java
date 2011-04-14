/*
 * JMacroEditor.java
 *
 * Created on 20 agosto 2002, 12.37
 */

package org.joone.edit;

import org.joone.net.*;
import org.joone.util.*;
import org.joone.log.*;
import org.joone.edit.jedit.*;
import org.joone.edit.jedit.tokenmarker.*;
import org.joone.script.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import CH.ifa.draw.util.*;
import CH.ifa.draw.framework.*;


/**
 * A text editor to edit and run BeanShell/ Groovy scripts
 * @author  PMLMAPA
 * @author  yccheok
 */
public class JMacroEditor extends javax.swing.JFrame {
    /**
     * Logger
     * */
    private static final ILogger log = LoggerFactory.getLogger(JMacroEditor.class);
    
    private JEditTextArea ta;
    private NeuralNet neuralNet;
    private char[] m_buf;
    private String m_dir;
    private String[] FileExtension = { "bsh", "groovy" };
    private String FileDescription = "BeanShell scripts (.bsh) | Groovy scripts (.groovy)";
    private String fileName = null;
    private String actualMacro = null;
    private javax.swing.DefaultListModel list;
    /** Creates new form TestJEdit */
    public JMacroEditor(NeuralNet nnet) {
        initComponents();
        setNeuralNet(nnet);
        ta = new JEditTextArea();
        ta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                taFocusLost(evt);
            }
        });
        
        ta.setTokenMarker(new JavaTokenMarker());
        jSplitPane1.setRightComponent(ta);
        Iconkit kit = Iconkit.instance();
        if (kit == null)
            throw new HJDError("Iconkit instance isn't set");
        final Image img = kit.loadImageResource(JoonEdit.DIAGRAM_IMAGES + "JooneIcon.gif");
        this.setIconImage(img);
        fillList();
        enableMacroMenuItem.setSelected(getNeuralNet().isScriptingEnabled());
        if (getNeuralNet().getMacroPlugin() instanceof MacroPlugin)
            beanShellMenuItem.setSelected(true);
        else
            groovyMenuItem.setSelected(true);
        pack();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        buttonGroup1 = new javax.swing.ButtonGroup();
        jSplitPane1 = new javax.swing.JSplitPane();
        jList1 = new javax.swing.JList();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        importMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        selectAllMenuItem = new javax.swing.JMenuItem();
        macroMenu = new javax.swing.JMenu();
        enableMacroMenuItem = new javax.swing.JCheckBoxMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        addMenuItem = new javax.swing.JMenuItem();
        removeMenuItem = new javax.swing.JMenuItem();
        renameMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        runMenuItem = new javax.swing.JMenuItem();
        setRateMenuItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        jMenuLanguage = new javax.swing.JMenu();
        beanShellMenuItem = new javax.swing.JRadioButtonMenuItem();
        groovyMenuItem = new javax.swing.JRadioButtonMenuItem();

        setTitle("JavaScript Macro Editor");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });

        jSplitPane1.setLeftComponent(jList1);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        fileMenu.setText("File");
        importMenuItem.setText("Import Macro...");
        importMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(importMenuItem);

        saveAsMenuItem.setText("Save Macro As ...");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(saveAsMenuItem);

        fileMenu.add(jSeparator1);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Close");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");
        cutMenuItem.setText("Cut");
        cutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutMenuItemActionPerformed(evt);
            }
        });

        editMenu.add(cutMenuItem);

        copyMenuItem.setText("Copy");
        copyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuItemActionPerformed(evt);
            }
        });

        editMenu.add(copyMenuItem);

        pasteMenuItem.setText("Paste");
        pasteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteMenuItemActionPerformed(evt);
            }
        });

        editMenu.add(pasteMenuItem);

        editMenu.add(jSeparator4);

        selectAllMenuItem.setText("Select All");
        selectAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllMenuItemActionPerformed(evt);
            }
        });

        editMenu.add(selectAllMenuItem);

        menuBar.add(editMenu);

        macroMenu.setText("Macro");
        macroMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                macroMenuActionPerformed(evt);
            }
        });

        enableMacroMenuItem.setText("Enable Scripting");
        enableMacroMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableMacroMenuItemActionPerformed(evt);
            }
        });

        macroMenu.add(enableMacroMenuItem);

        macroMenu.add(jSeparator3);

        addMenuItem.setText("Add...");
        addMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMenuItemActionPerformed(evt);
            }
        });

        macroMenu.add(addMenuItem);

        removeMenuItem.setText("Remove");
        removeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMenuItemActionPerformed(evt);
            }
        });

        macroMenu.add(removeMenuItem);

        renameMenuItem.setText("Rename");
        renameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                renameMenuItemActionPerformed(evt);
            }
        });

        macroMenu.add(renameMenuItem);

        macroMenu.add(jSeparator2);

        runMenuItem.setText("Run");
        runMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runMenuItemActionPerformed(evt);
            }
        });

        macroMenu.add(runMenuItem);

        setRateMenuItem.setText("set Rate...");
        setRateMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setRateMenuItemActionPerformed(evt);
            }
        });

        macroMenu.add(setRateMenuItem);

        macroMenu.add(jSeparator5);

        jMenuLanguage.setText("Language");
        buttonGroup1.add(beanShellMenuItem);
        beanShellMenuItem.setSelected(true);
        beanShellMenuItem.setText("BeanShell");
        beanShellMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beanShellMenuItemActionPerformed(evt);
            }
        });

        jMenuLanguage.add(beanShellMenuItem);

        buttonGroup1.add(groovyMenuItem);
        groovyMenuItem.setText("Groovy");
        groovyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groovyMenuItemActionPerformed(evt);
            }
        });

        jMenuLanguage.add(groovyMenuItem);

        macroMenu.add(jMenuLanguage);

        menuBar.add(macroMenu);

        setJMenuBar(menuBar);

        pack();
    }//GEN-END:initComponents
    
    private void groovyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groovyMenuItemActionPerformed
        GroovyMacroPlugin newMacroPlugin = new GroovyMacroPlugin();
        setNewMacroPlugin(newMacroPlugin);
    }//GEN-LAST:event_groovyMenuItemActionPerformed
    
    private void beanShellMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beanShellMenuItemActionPerformed
        MacroPlugin newMacroPlugin = new MacroPlugin();
        setNewMacroPlugin(newMacroPlugin);
    }//GEN-LAST:event_beanShellMenuItemActionPerformed
    
    private void setNewMacroPlugin(MacroInterface newMacroPlugin) {
        MacroInterface oldMacroPlugin = getNeuralNet().getMacroPlugin();
        newMacroPlugin.setMacroManager(oldMacroPlugin.getMacroManager());
        getNeuralNet().setMacroPlugin(newMacroPlugin);
    }
    
    private void taFocusLost(java.awt.event.FocusEvent evt) {
        // Ensure the latest edited macro take effect immediately
        // just after the user changes focus from text editing field
        // to other components.
        saveMacro();
    }
    
    private void setRateMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setRateMenuItemActionPerformed
        // Add your handling code here:
        int newRate;
        int oldRate = ((MonitorPlugin)getNeuralNet().getMacroPlugin()).getRate();
        String s_newRate = this.showInputDialog("Set Rate", "Insert the new rate's value:", Integer.toString(oldRate));
        if (s_newRate != null) {
            try {
                newRate = Integer.parseInt(s_newRate);
            } catch (NumberFormatException nfe) {
                // If the input value is invalid (not numeric) the rate is unchanged
                newRate = oldRate;
            }
            // The rate can't be negative
            if (newRate < 0)
                newRate = newRate * -1;
            ((MonitorPlugin)getNeuralNet().getMacroPlugin()).setRate(newRate);
        }
        
    }//GEN-LAST:event_setRateMenuItemActionPerformed
    
    private void renameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_renameMenuItemActionPerformed
        // Add your handling code here:
        String newName = getNewName("Rename a macro", "Insert the new name of the Macro:", actualMacro);
        if (newName != null) {
            getNeuralNet().getMacroPlugin().getMacroManager().renameMacro(actualMacro, newName);
            actualMacro = newName;
            list.setElementAt(newName, jList1.getSelectedIndex());
        }
    }//GEN-LAST:event_renameMenuItemActionPerformed
    
    private void beanShellRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beanShellRadioButtonMenuItemActionPerformed
        // TODO: remove this method
    }//GEN-LAST:event_beanShellRadioButtonMenuItemActionPerformed
    
    private void groovyRadioButtonMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groovyRadioButtonMenuItemActionPerformed
        // TODO: remove this method
    }//GEN-LAST:event_groovyRadioButtonMenuItemActionPerformed
    
    private void removeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMenuItemActionPerformed
        // Add your handling code here:
        int n = JOptionPane.showConfirmDialog(this,
                "OK to remove '" + actualMacro +"' ?",
                "Macro Editor",
                JOptionPane.YES_NO_OPTION);
        if (n == JOptionPane.YES_OPTION) {
            getNeuralNet().getMacroPlugin().getMacroManager().removeMacro(actualMacro);
            String removed = actualMacro;
            actualMacro = null;
            list.removeElement(removed);
        }
    }//GEN-LAST:event_removeMenuItemActionPerformed
    
    private void addMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMenuItemActionPerformed
        // Add your handling code here:
        String newName = getNewName("Add a macro", "Insert the name of the new Macro:", "newMacro");
        if (newName != null) {
            getNeuralNet().getMacroPlugin().getMacroManager().addMacro(newName, "");
            list.addElement(newName);
            jList1.setSelectedIndex(list.size() - 1);
        }
    }//GEN-LAST:event_addMenuItemActionPerformed
    
    /* Ask the user for a new name and loops until the name is valid */
    private String getNewName(String title, String message, String initialValue) {
        boolean ok = false;
        String errorMsg = "ERROR: The name already exists.\n";
        String newName = this.showInputDialog(title, message, initialValue);
        if (newName != null)
            while (!ok) {
            if (getNeuralNet().getMacroPlugin().getMacroManager().getMacro(newName) != null) {
                newName = this.showInputDialog(title, errorMsg+message, newName);
                if (newName == null)
                    ok = true;
            } else
                ok = true;
            }
        return newName;
    }
    
    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        saveMacro();
        // Open the new selected macro
        int index = jList1.getSelectedIndex();
        if (index > -1) {
            String name = ((String)jList1.getModel().getElementAt(index));
            String macroText = getNeuralNet().getMacroPlugin().getMacroManager().getMacro(name);
            if (macroText.trim().equals(new String("")))
                macroText = "/* " + name + " */\n";
            ta.setText(macroText);
            actualMacro = name;
            enableMenuItems(actualMacro);
        } else
            jList1.setSelectedIndex(0);
    }//GEN-LAST:event_jList1ValueChanged
    
    private void enableMenuItems(String name) {
        boolean system = getNeuralNet().getMacroPlugin().getMacroManager().isEventMacro(name);
        if (system) {
            removeMenuItem.setEnabled(false);
            renameMenuItem.setEnabled(false);
        } else {
            removeMenuItem.setEnabled(true);
            renameMenuItem.setEnabled(true);
        }
    }
    
    private void enableMacroMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enableMacroMenuItemActionPerformed
        // Add your handling code here:
        neuralNet.setScriptingEnabled(enableMacroMenuItem.isSelected());
    }//GEN-LAST:event_enableMacroMenuItemActionPerformed
    
    private void selectAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllMenuItemActionPerformed
        // Add your handling code here:
        ta.selectAll();
    }//GEN-LAST:event_selectAllMenuItemActionPerformed
    
    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
        // Add your handling code here:
        saveAs();
    }//GEN-LAST:event_saveAsMenuItemActionPerformed
    
    private void importMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importMenuItemActionPerformed
        // Add your handling code here:
        JFileChooser jfc = new JooneFileChooser();
        jfc.setDialogTitle("Macro Editor - Import File...");
        FileFilter bsh = new FileFilter() {
            public boolean accept(File checkFile) {
                // still display directories for navigation
                if (checkFile.isDirectory()) {
                    return true;
                } else {
                    boolean isFileExtensionMatch = false;
                    String[] extension = getFileExtension();
                    
                    for(int i=0; i<extension.length; i++) {
                        if(checkFile.getName().endsWith("." + extension[i])) {
                            isFileExtensionMatch = true;
                            break;
                        }
                    }
                    
                    return isFileExtensionMatch;
                }
            }
            
            public String getDescription() {
                return getFileDescription();
            }
        };
        jfc.addChoosableFileFilter(bsh);
        if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            setFileName(jfc.getSelectedFile().getAbsolutePath());
            File ff = new File(fileName);
            String txt = readFile(ff);
            if (txt != null)
                ta.setText(txt);
        }
    }//GEN-LAST:event_importMenuItemActionPerformed
    
    private void pasteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteMenuItemActionPerformed
        // Add your handling code here:
        ta.paste();
    }//GEN-LAST:event_pasteMenuItemActionPerformed
    
    private void copyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMenuItemActionPerformed
        // Add your handling code here:
        ta.copy();
    }//GEN-LAST:event_copyMenuItemActionPerformed
    
    private void cutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutMenuItemActionPerformed
        // Add your handling code here:
        ta.cut();
    }//GEN-LAST:event_cutMenuItemActionPerformed
    
    private void runMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runMenuItemActionPerformed
        // Add your handling code here:
        getNeuralNet().getMacroPlugin().runMacro(ta.getText());
    }//GEN-LAST:event_runMenuItemActionPerformed
    
    private void macroMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_macroMenuActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_macroMenuActionPerformed
    
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        saveMacro();
        this.setVisible(false);
        //System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        //System.exit(0);
        saveMacro();
    }//GEN-LAST:event_exitForm
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        NeuralNet nn = new NeuralNet();
        nn.setMacroPlugin(new MacroPlugin());
        JMacroEditor jte = new JMacroEditor(nn);
        jte.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                System.exit(0);
            }
        });
        jte.show();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addMenuItem;
    private javax.swing.JRadioButtonMenuItem beanShellMenuItem;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JCheckBoxMenuItem enableMacroMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JRadioButtonMenuItem groovyMenuItem;
    private javax.swing.JMenuItem importMenuItem;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenuLanguage;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JMenu macroMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem removeMenuItem;
    private javax.swing.JMenuItem renameMenuItem;
    private javax.swing.JMenuItem runMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem selectAllMenuItem;
    private javax.swing.JMenuItem setRateMenuItem;
    // End of variables declaration//GEN-END:variables
    
    public void setText(String newText) {
        ta.setText(newText);
    }
    
    public String getText() {
        return ta.getText();
    }
    
    /** Getter for property neuralNet.
     * @return Value of property NeuralNet.
     */
    public org.joone.net.NeuralNet getNeuralNet() {
        return neuralNet;
    }
    
    /** Setter for property neuralNet.
     * @param neuralNet New value of property NeuralNet.
     */
    public void setNeuralNet(org.joone.net.NeuralNet neuralNet) {
        this.neuralNet = neuralNet;
    }
    /**
     * Reading the content of the file.
     *
     * @param	p_file	the content of the file that is to be read.
     * @return	the content of the file.
     */
    private String readFile(File p_file) {
        String str = null;
        FileReader reader = null;
        
        try {
            reader = new FileReader(p_file);
            
            int tch = new Long(p_file.length()).intValue();
            m_buf = new char[tch];
            int nch = reader.read(m_buf);
            if (nch != -1)
                str = new String(m_buf, 0, nch);
            reader.close();
            
        } catch (FileNotFoundException fnfe) {
            log.warn("File '" + p_file + "' not found. MEssage is : " + fnfe.getMessage(),
                    fnfe) ;
            return str;
        } catch (IOException ioe) {
            log.error( "File '" + m_dir + "' input/output error. Message is : " + ioe.getMessage(),
                    ioe );
        }
        return str;
    }
    
    /** Getter for property FileDescription.
     * @return Value of property FileDescription.
     */
    public java.lang.String getFileDescription() {
        return FileDescription;
    }
    
    /** Setter for property FileDescription.
     * @param FileDescription New value of property FileDescription.
     */
    public void setFileDescription(java.lang.String FileDescription) {
        this.FileDescription = FileDescription;
    }
    
    /** Getter for property FileExtension.
     * @return Value of property FileExtension.
     */
    public java.lang.String[] getFileExtension() {
        return (String[])FileExtension.clone();
    }
    
    /** Setter for property FileExtension.
     * @param FileExtension New value of property FileExtension.
     */
    public void setFileExtension(java.lang.String[] FileExtension) {
        this.FileExtension = (String[])FileExtension.clone();
    }
    
    private void saveAs() {
        JFileChooser jfc;
        if (fileName == null)
            jfc = new JFileChooser();
        else
            jfc = new JFileChooser(new File(fileName));
        jfc.setDialogTitle("Macro Editor - Save as...");
        if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            setFileName(jfc.getSelectedFile().getAbsolutePath());
            save();
        }
    }
    
    // TO DO
    // Add some comments
    private void save() {
        try {
            FileWriter fw = new FileWriter(new File(fileName));
            fw.write(ta.getText());
            fw.flush();
            fw.close();
        } catch (IOException ioe) {
            log.warn( "IOException thrown. Message is +" + ioe.getMessage(),
                    ioe );
        }
    }
    
    /** Getter for property fileName.
     * @return Value of property fileName.
     */
    public java.lang.String getFileName() {
        return fileName;
    }
    
    /** Setter for property fileName.
     * @param fileName New value of property fileName.
     */
    public void setFileName(java.lang.String fileName) {
        this.fileName = fileName;
        if (fileName != null)
            this.setTitle(fileName);
        else
            this.setTitle("New File");
    }
    
    private void fillList() {
        Hashtable macros = getNeuralNet().getMacroPlugin().getMacroManager().getMacros();
        list = new javax.swing.DefaultListModel();
        for (Enumeration keys = macros.keys(); keys.hasMoreElements();) {
            String key = (String)keys.nextElement();
            list.addElement(key);
        }
        jList1.setModel(list);
        jList1.setSelectedIndex(0);
    }
    
    private String showInputDialog(String title, String message, String initialValue) {
        JOptionPane pane = new JOptionPane(message, JOptionPane.QUESTION_MESSAGE);
        pane.setWantsInput(true);
        pane.setInitialSelectionValue(initialValue);
        pane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = pane.createDialog(this, title);
        dialog.show();
        int selectedButton = ((Integer)pane.getValue()).intValue();
        if (selectedButton == 0) {
            Object selectedValue = pane.getInputValue();
            if(selectedValue != null)
                if(selectedValue instanceof String)
                    return ((String)selectedValue);
        }
        return null;
    }
    
    // Saves the last edited macro
    private void saveMacro() {
        if (actualMacro != null) {
            String oldText = getNeuralNet().getMacroPlugin().getMacroManager().getMacro(actualMacro);
            if (!oldText.equals(ta.getText())) {
                getNeuralNet().getMacroPlugin().getMacroManager().addMacro(actualMacro, ta.getText());
            }
        }
    }
}

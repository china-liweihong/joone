/*
 * CoordGenerator.java
 *
 * Created on 16 giugno 2003, 20.16
 */

package org.joone.samples.editor.som;
import java.awt.event.*;
import java.awt.*;
import java.util.Vector;
import java.io.*;

import org.joone.edit.*;
import org.joone.engine.*;
import CH.ifa.draw.util.*;
import CH.ifa.draw.framework.*;
/**
 *
 * @author  root
 */
class myChart extends ChartOutputSynapse {
    public javax.swing.JFrame myFrame() {
        return getFrame();
    }
}
public class ChartOutputSample extends javax.swing.JFrame {
    private Vector coords;
    private boolean alone;  // true if launched from the command prompt
    String fileName = "/tmp/coords.txt";
    
    public ChartOutputSample() {
        this(false);
    }
    
    /** Creates new form CoordGenerator */
    public ChartOutputSample(boolean main) {
        alone = main;
        initComponents();
        try {
            Iconkit kit = new Iconkit(this);
            kit = Iconkit.instance();
            Thread.sleep(3000);
            kit = Iconkit.instance();
            if (kit == null)
                throw new HJDError("Iconkit instance isn't set");
            final Image img = kit.loadImageResource(JoonEdit.DIAGRAM_IMAGES + "jooneIcon.gif");
        } catch (InterruptedException doNothing) {}
        coords = new Vector();
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                System.out.println("x="+e.getX()+" y="+e.getY());
                int[] cc = new int[2];
                cc[0] = e.getX();
                cc[1] = e.getY();
                coords.addElement(cc);
                repaint();
            }
        });
        int count = 1;
        myChart chart = new myChart();
        chart.setMaxYaxis(0.2); // The max value expected on Y axis
        chart.setMaxXaxis(1000); // The max points you want to plot on X axis
        jPanel1.add(chart.myFrame().getContentPane());
        double[] arr = new double[1];
        arr[0] = 0.1;
        Pattern patt = new Pattern();
        patt.setArray(arr);
        patt.setCount(count);
        chart.fwdPut(patt);
        setSize(640,480);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        fileMenu.setText("File");
        fileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileMenuActionPerformed(evt);
            }
        });

        newMenuItem.setText("New");
        newMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(newMenuItem);

        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(saveMenuItem);

        fileMenu.add(jSeparator1);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });

        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText("Help");
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });

        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }//GEN-END:initComponents
    
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        String help1 = new String("This application allows the user to plot a figure for recognition by a Joone neural network.");
        String help2 = new String("It is intended to test a SOM or Kohonen Network by providing an image recognition example.");
        String help3 = new String("Draw a whatever figure clicking on the drawing area, and then save the figure into a file.");
        String help4 = new String("The saved file must be used as input of the figureRecognition.ser neural network.");
        
        javax.swing.JOptionPane.showMessageDialog(this,help1+"\n"+help2+"\n"+help3+"\n"+help4);
    }//GEN-LAST:event_aboutMenuItemActionPerformed
    
    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        // Add your handling code here:
        try  {
            FileWriter fw = new FileWriter(fileName);
            for (int x=0; x < coords.size(); ++x) {
                int[] cc = (int[])coords.elementAt(x);
                fw.write(Integer.toString(cc[0])+";");
                fw.write(Integer.toString(cc[1])+"\n");
            }
            fw.close();
            System.out.println("Written "+coords.size()+" coords");
        } catch (IOException ioe)  { ioe.printStackTrace();  }
    }//GEN-LAST:event_saveMenuItemActionPerformed
    
    private void newMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuItemActionPerformed
        // Add your handling code here:
        coords = new Vector();
        repaint();
    }//GEN-LAST:event_newMenuItemActionPerformed
    
    private void fileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileMenuActionPerformed
        // Add your handling code here:
    }//GEN-LAST:event_fileMenuActionPerformed
    
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        this.exitTester();
    }//GEN-LAST:event_exitMenuItemActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        this.exitTester();
    }//GEN-LAST:event_exitForm
    
    private void exitTester() {
        if (alone)
            System.exit(0);
        else
            this.dispose();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new CoordGenerator(true).show();
    }
    
    /** Paints this component.
     * <p>
     * This method is called when the contents of the component should
     * be painted; such as when the component is first being shown or
     * is damaged and in need of repair.  The clip rectangle in the
     * <code>Graphics</code> parameter is set to the area
     * which needs to be painted.
     * Subclasses of <code>Component</code> that override this
     * method need not call <code>super.paint(g)</code>.
     * <p>
     * For performance reasons, <code>Component</code>s with zero width
     * or height aren't considered to need painting when they are first shown,
     * and also aren't considered to need repair.
     * <p>
     * <b>Note</b>: For more information on the paint mechanisms utilitized
     * by AWT and Swing, including information on how to write the most
     * efficient painting code, see
     * <a href="http://java.sun.com/products/jfc/tsc/articles/painting/index.html">Painting in AWT and Swing</a>.
     *
     * @param g the graphics context to use for painting
     * @see       #update
     * @since     JDK1.0
     *
     */
    public void paint(Graphics g) {
        super.paint(g);
        for (int x=0; x < coords.size(); ++x) {
            int[] cc = (int[])coords.elementAt(x);
            g.fillOval(cc[0], cc[1], 3, 3);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem newMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables
    
}

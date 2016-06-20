package unique.digital.student;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;

import javax.swing.JRadioButton;

import java.awt.Panel;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JDesktopPane;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.awt.Button;
import java.io.File;

public class SortApplication {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SortApplication window = new SortApplication();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public SortApplication() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(null);
        frame.setBounds(100, 100, 450, 300);
        
        JRadioButton bubbleButton = new JRadioButton("Bubble");
        bubbleButton.setBounds(23, 17, 109, 23);
        frame.getContentPane().add(bubbleButton);
        
        JRadioButton heapButton = new JRadioButton("Heap");
        heapButton.setBounds(23, 43, 109, 23);
        frame.getContentPane().add(heapButton);
        
        JRadioButton mergeButton = new JRadioButton("Merge");
        mergeButton.setBounds(23, 69, 109, 23);
        frame.getContentPane().add(mergeButton);
        
        ButtonGroup group = new ButtonGroup();
        group.add(bubbleButton);
        group.add(heapButton);
        group.add(mergeButton);
        
        JButton btnNewButton = new JButton("Select input file");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int returnVal = chooser.showOpenDialog(frame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                   System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
                }
            }
        });
        btnNewButton.setBounds(219, 17, 185, 23);
        frame.getContentPane().add(btnNewButton);
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return button.getText();
            }
        }
        return null;
    }
}

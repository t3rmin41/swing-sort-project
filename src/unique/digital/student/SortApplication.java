package unique.digital.student;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JRadioButton;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class SortApplication {

    private JFrame frame;
    private File inputFile;
    private StudentStats[] studentStatsArr = null;
    
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
        
        JRadioButton bubbleButton = new JRadioButton(SortMethod.BUBBLE.getValue());
        bubbleButton.setBounds(23, 17, 109, 23);
        frame.getContentPane().add(bubbleButton);
        
        JRadioButton heapButton = new JRadioButton(SortMethod.HEAP.getValue());
        heapButton.setBounds(23, 43, 109, 23);
        frame.getContentPane().add(heapButton);
        
        JRadioButton mergeButton = new JRadioButton(SortMethod.MERGE.getValue());
        mergeButton.setBounds(23, 69, 109, 23);
        frame.getContentPane().add(mergeButton);
        
        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(187, 85, 217, 14);
        frame.getContentPane().add(lblNewLabel);

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(167, 110, 237, 141);
        frame.getContentPane().add(scrollPane);
        
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
                   inputFile = chooser.getSelectedFile();
                }
            }
        });
        btnNewButton.setBounds(187, 17, 217, 23);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Sort students from input file");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    lblNewLabel.setText("");
                    textArea.setText("");
                    SortMethod selectedMethod = SortMethod.getMethod(getSelectedButtonText(group));
                    List<StudentStats> studentStatsList = null;
                    try {
                        studentStatsList = readStudentStatsFromFileUnsorted(inputFile);
                        studentStatsArr = studentStatsList.toArray(new StudentStats[studentStatsList.size()]);
                    } catch (NullPointerException npe) {
                        throw new NullPointerException("No input file selected");
                    }
                    switch (selectedMethod) {
                        case BUBBLE: studentStatsArr = sortBubble(studentStatsArr);
                                     break;
                        case HEAP:   studentStatsArr = sortHeap(studentStatsArr);
                                     break;
                        case MERGE:  studentStatsArr = sortMerge(studentStatsArr);
                                     break;
                    }
                    for (StudentStats stats : studentStatsArr) {
                        textArea.append("Student : " + stats.getStudentSurname() + " | score : " + stats.getScore() + "\n");
                    }
                } catch (IllegalArgumentException iae) {
                    lblNewLabel.setText("No sorting method selected");
                } catch (NullPointerException npe) {
                    lblNewLabel.setText(npe.getMessage());
                }
            }
        });
        btnNewButton_1.setBounds(187, 51, 217, 23);
        frame.getContentPane().add(btnNewButton_1);
        
        JButton btnNewButton_2 = new JButton("Save to file");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                if (chooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                    try(FileWriter fw = new FileWriter(chooser.getSelectedFile()+".txt")) {
                        fw.write(textArea.getText());
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                  }
            }
        });
        btnNewButton_2.setBounds(23, 112, 109, 23);
        frame.getContentPane().add(btnNewButton_2);

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
    
    private List<StudentStats> readStudentStatsFromFileUnsorted(File file) {
        List<StudentStats> stats = new ArrayList<StudentStats>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            for(String line; (line = br.readLine()) != null; ) {
                String[] data = line.split(",");
                stats.add(new StudentStats(data[0], new Double(data[1])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stats;
    }
    
    private StudentStats[] sortBubble(StudentStats[] studentStats) {
        StudentStats temp = new StudentStats("", 0.0);
        for (int i = 0; i < studentStats.length-1; i++ ) {
            for (int j = 1; j < studentStats.length-i; j++) {
                if (studentStats[j-1].getScore() < studentStats[j].getScore()) {
                    temp = studentStats[j-1];
                    studentStats[j-1] = studentStats[j];
                    studentStats[j] = temp;
                }
            }
        }
        return studentStats;
    }
    
    private StudentStats[] sortHeap(StudentStats[] studentStats) {
        return studentStats;
    }
    
    private StudentStats[] sortMerge(StudentStats[] studentStats) {
        return studentStats;
    }
}

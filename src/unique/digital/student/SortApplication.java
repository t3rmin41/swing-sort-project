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
import java.time.Instant;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class SortApplication {

    private JFrame frame;
    private File inputFile;
    private StudentStats[] studentStatsArr = null;
    private long startMillis = 0;
    private long stopMillis = 0; 
    
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
        frame.setBounds(100, 100, 449, 376);
        
        JRadioButton bubbleButton = new JRadioButton(SortMethod.BUBBLE.getValue());
        bubbleButton.setBounds(23, 17, 109, 23);
        frame.getContentPane().add(bubbleButton);
        
        JRadioButton heapButton = new JRadioButton(SortMethod.HEAP.getValue());
        heapButton.setBounds(23, 43, 109, 23);
        frame.getContentPane().add(heapButton);
        
        JRadioButton mergeButton = new JRadioButton(SortMethod.MERGE.getValue());
        mergeButton.setBounds(23, 69, 109, 23);
        frame.getContentPane().add(mergeButton);
        
        JLabel errorMessageLabel = new JLabel("");
        errorMessageLabel.setBounds(187, 109, 217, 14);
        frame.getContentPane().add(errorMessageLabel);
        
        JLabel timeElapsedLabel = new JLabel("");
        timeElapsedLabel.setBounds(187, 85, 217, 14);
        frame.getContentPane().add(timeElapsedLabel);

        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(187, 134, 217, 193);
        frame.getContentPane().add(scrollPane);
        
        ButtonGroup group = new ButtonGroup();
        group.add(bubbleButton);
        group.add(heapButton);
        group.add(mergeButton);
        
        JButton inputFileSelectButton = new JButton("Select input file");
        inputFileSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
                int returnVal = chooser.showOpenDialog(frame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                   inputFile = chooser.getSelectedFile();
                }
            }
        });
        inputFileSelectButton.setBounds(187, 17, 217, 23);
        frame.getContentPane().add(inputFileSelectButton);

        JButton sortButton = new JButton("Sort students from input file");
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    errorMessageLabel.setText("");
                    timeElapsedLabel.setText("");
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
                        case BUBBLE: startMillis = Instant.now().toEpochMilli();
                                     studentStatsArr = sortBubble(studentStatsArr);
                                     stopMillis = Instant.now().toEpochMilli();
                                     break;
                        case HEAP:   startMillis = Instant.now().toEpochMilli();
                                     studentStatsArr = sortHeap(studentStatsArr);
                                     stopMillis = Instant.now().toEpochMilli();
                                     break;
                        case MERGE:  startMillis = Instant.now().toEpochMilli();
                                     studentStatsArr = sortMerge(studentStatsArr);
                                     stopMillis = Instant.now().toEpochMilli();
                                     break;
                    }
                    for (int i = 0; i < studentStatsArr.length; i++) {
                        textArea.append((i+1) + " | Student : " + studentStatsArr[i].getStudentSurname() + " | score : " + studentStatsArr[i].getScore() + "\n");
                    }
                    timeElapsedLabel.setText("Sort method time : " + (stopMillis-startMillis) + " ms");
                } catch (IllegalArgumentException iae) {
                    errorMessageLabel.setText("No sorting method selected");
                } catch (NullPointerException npe) {
                    errorMessageLabel.setText(npe.getMessage());
                }
            }
        });
        sortButton.setBounds(187, 51, 217, 23);
        frame.getContentPane().add(sortButton);
        
        JButton saveToFileButton = new JButton("Save to file");
        saveToFileButton.addActionListener(new ActionListener() {
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
        saveToFileButton.setBounds(23, 112, 109, 23);
        frame.getContentPane().add(saveToFileButton);

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
        HeapSort heapSort = new HeapSort(studentStats);
        heapSort.sort();
        return heapSort.getSortedArray();
    }
    
    private StudentStats[] sortMerge(StudentStats[] studentStats) {
        //If list is empty; no need to do anything
        if (studentStats.length <= 1) {
            return studentStats;
        }
         
        //Split the array in half in two parts
        StudentStats[] first = new StudentStats[studentStats.length / 2];
        StudentStats[] second = new StudentStats[studentStats.length - first.length];
        System.arraycopy(studentStats, 0, first, 0, first.length);
        System.arraycopy(studentStats, first.length, second, 0, second.length);
         
        //Sort each half recursively
        sortMerge(first);
        sortMerge(second);
         
        //Merge both halves together, overwriting to original array
        merge(first, second, studentStats);
        return studentStats;
    }
    
    private StudentStats[] merge(StudentStats[] first, StudentStats[] second, StudentStats[] result) {
        //Index Position in first array - starting with first element
        int iFirst = 0;
         
        //Index Position in second array - starting with first element
        int iSecond = 0;
         
        //Index Position in merged array - starting with first position
        int iMerged = 0;
         
        //Compare elements at iFirst and iSecond, 
        //and move smaller element at iMerged
        while (iFirst < first.length && iSecond < second.length) {
            if (first[iFirst].getScore() > second[iSecond].getScore()) {
                result[iMerged] = first[iFirst];
                iFirst++;
            } else {
                result[iMerged] = second[iSecond];
                iSecond++;
            }
            iMerged++;
        }
        //copy remaining elements from both halves - each half will have already sorted elements
        System.arraycopy(first, iFirst, result, iMerged, first.length - iFirst);
        System.arraycopy(second, iSecond, result, iMerged, second.length - iSecond);
        return result;
    }
}

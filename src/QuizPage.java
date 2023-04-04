import org.apache.poi.ss.usermodel.Sheet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;


public class QuizPage {
    private ArrayList<Word> words = new ArrayList<>();
    private ArrayList<Word> wordAll = new ArrayList<>();
    private ArrayList<Word> wordUse = new ArrayList<>();
    private ExcelMangement DB;
    private static Color bgColor = new Color(78,78,78);
    private ArrayList<String> sheetMenber = new ArrayList<>();
    ArrayList<String> fontWord = new ArrayList<>();
    ArrayList<String> backWord = new ArrayList<>();
    private boolean comChecks = false;

    public QuizPage(){
        try {
            DB = new ExcelMangement(".\\data\\database.xlsx");
            Font font = new Font("Tahoma", Font.PLAIN,16);
            UIManager.put("Button.font", font);
            UIManager.put("Label.font", font);
            UIManager.put("TextField.font", font);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public JPanel panelS(){
        JComboBox comboBox;
        JLabel word = new JLabel("Test");
        JPanel mid = new JPanel();
        JPanel buts = new JPanel();
        JButton b1 = new JButton("1");
        JButton b2 = new JButton("2");
        JButton b3 = new JButton("3");
        JButton b4 = new JButton("4");
        b1.setBackground(Color.gray);
        b2.setBackground(Color.gray);
        b3.setBackground(Color.gray);
        b4.setBackground(Color.gray);
        b1.setPreferredSize(new Dimension(300,200));

        JPanel quizpage = new JPanel();
        JPanel content = new JPanel();
        JButton confirm = new JButton("Confirm");
        JPanel head = new JPanel();
        for(int i = 1;i<=DB.getWorkbook().getNumberOfSheets()-1;i++){
            sheetMenber.add(DB.getWorkbook().getSheetName(i));
        }
        String[] array = sheetMenber.toArray(new String[0]);
        comboBox = new JComboBox<>(array);
        comboBox.setPreferredSize(new Dimension(350,35));
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        word.setBorder(new EmptyBorder(20,0,10,0));
        word.setForeground(Color.white);
        word.setFont(new Font("Arial", Font.PLAIN, 30));
        content.setBackground(bgColor);
        mid.setBackground(bgColor);
        head.setBackground(bgColor);
        buts.setBackground(bgColor);
        quizpage.setBackground(bgColor);
        content.setBorder(new EmptyBorder(50,0,10,0));
        mid.setBorder(new EmptyBorder(30,0,10,0));
        buts.setBorder(new EmptyBorder(60,0,10,0));
        mid.setLayout(new BoxLayout(mid,BoxLayout.Y_AXIS));
        mid.add(word);
        buts.add(b1);
        buts.add(b2);
        buts.add(b3);
        buts.add(b4);
        buts.setLayout(new GridLayout(2,2,10,10));
        mid.add(buts);
        head.add(comboBox);
        head.add(confirm);
        content.add(head);
        content.add(mid);
        mid.setVisible(false);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirm.setEnabled(true);
            }
        });
        String sheetName = (String) comboBox.getSelectedItem();
        if (sheetName == null) {
            JOptionPane.showMessageDialog(null, "Please select a sheet", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Sheet sheet = DB.getWorkbook().getSheet(sheetName);
            if (sheet == null) {
                JOptionPane.showMessageDialog(null, "Selected sheet not found in the workbook", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int lastRowNum = (sheet.getLastRowNum());
                for(int i = 0; i <= lastRowNum; i++){
                    Word wordA = new Word(sheet.getRow(i).getCell(0).getStringCellValue(),sheet.getRow(i).getCell(1).getStringCellValue());
                    wordAll.add(wordA);
                }
            }
        }
        Random random = new Random();
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mid.setVisible(true);
                if(wordAll.size()>=4){
                    for(int i = 0;i<4;i++){
                        int randDom = random.nextInt(0,wordAll.size());
                        wordUse.add(wordAll.get(randDom));
                        wordAll.remove(randDom);
                        fontWord.add(wordUse.get(i).getFontWord());
                        backWord.add(wordUse.get(i).getBackWord());
                    }
                    System.out.println(fontWord.size()+"");
                    System.out.println(backWord.size()+"");
                    confirm.setEnabled(false);
                }
                else{
                    JOptionPane.showMessageDialog(null, "The word have not enough", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        for(int i = 0;i<4;i++){
            System.out.println(fontWord.get(i)+" , "+backWord.get(i));
        }
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox.getSelectedIndex() >= 0 ){
                    confirm.setEnabled(true);
                }
            }
        });
        comboBox.setSelectedIndex(-1);
        quizpage.add(content);
        return quizpage;
    }
}
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class Add {
    private ExcelMangement DB;
    private boolean comChecks = false;
    private ArrayList<String> sheetMenber = new ArrayList<>();
    public Add(){
        try {
            DB = new ExcelMangement(".\\data\\database.xlsx");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public JPanel panelS(){
        Color bgColor = new Color(78,78,78);

        for(int i = 1;i<=DB.getWorkbook().getNumberOfSheets()-1;i++){
            sheetMenber.add(DB.getWorkbook().getSheetName(i));
        }
        JPanel addPage = new JPanel();
        JCheckBox checkBoxTitle = new JCheckBox("Check Title");
        JCheckBox checkBoxFont = new JCheckBox("Check Font");
        JCheckBox checkBoxBack = new JCheckBox("Check Back");
        JButton confirmTitle = new JButton("Confirm");
        JButton confirmFont = new JButton("Confirm");
        JButton confirmBack = new JButton("Confirm");
        checkBoxTitle.setBackground(bgColor);
        checkBoxBack.setBackground(bgColor);
        checkBoxFont.setBackground(bgColor);
        checkBoxFont.setForeground(Color.white);
        checkBoxBack.setForeground(Color.white);
        checkBoxTitle.setForeground(Color.white);
        JPanel textFieldPanel = new JPanel();
        String[] array = sheetMenber.toArray(new String[0]);
        JComboBox comboBox = new JComboBox<>(array);
        JPanel comPanel = new JPanel();
        JLabel textTitle = new JLabel("Title");
        JLabel textCom = new JLabel("Titel Selection");
        JLabel textFont = new JLabel("Font Word");
        JLabel textBack = new JLabel("Back Word");
        textTitle.setForeground(Color.white);
        textFont.setForeground(Color.white);
        textBack.setForeground(Color.white);
        JPanel titlePanel = new JPanel();
        JPanel fontPanel = new JPanel();
        JPanel backPanel = new JPanel();
        addPage.setBackground(bgColor);
        textFieldPanel.setLayout(new BoxLayout(textFieldPanel, BoxLayout.Y_AXIS));
        textFieldPanel.setBackground(bgColor);

        JTextField titleIn = new JTextField();
        JTextField fontWord = new JTextField();
        JTextField backWord = new JTextField();

        titleIn.setEnabled(false);
        fontWord.setEnabled(false);
        backWord.setEnabled(false);




        titleIn.setPreferredSize(new Dimension(262, 35));
        fontWord.setPreferredSize(new Dimension(262, 35));
        backWord.setPreferredSize(new Dimension(262, 35));

        comPanel.setLayout(new BoxLayout(comPanel,BoxLayout.Y_AXIS));
        comPanel.setBorder(new EmptyBorder(20,0,20,0));
        comPanel.setBackground(bgColor);
        comPanel.setForeground(Color.WHITE);
        textCom.setForeground(Color.white);
        textCom.setBorder(new EmptyBorder(10,0,10,0));
        comPanel.add(textCom);
        comPanel.add(comboBox);
        comboBox.setPreferredSize(new Dimension(20,25));
        comboBox.setSelectedIndex(-1);

        titlePanel.setBorder(new EmptyBorder(20,0,20,0));
        titlePanel.setBackground(bgColor);
        titlePanel.add(textTitle);
        titlePanel.add(titleIn);
        titlePanel.add(checkBoxTitle);
        titlePanel.add(confirmTitle);

        fontPanel.setBorder(new EmptyBorder(20,0,20,0));
        fontPanel.setBackground(bgColor);
        fontPanel.add(textFont);
        fontPanel.add(fontWord);
        fontPanel.add(checkBoxFont);

        backPanel.setBorder(new EmptyBorder(20,0,20,0));
        backPanel.setBackground(bgColor);
        backPanel.add(textBack);
        backPanel.add(backWord);
        backPanel.add(checkBoxBack);

        textFieldPanel.add(titlePanel);
        textFieldPanel.add(comPanel);
        textFieldPanel.add(fontPanel);
        textFieldPanel.add(backPanel);
        textFieldPanel.add(confirmFont);
        textFieldPanel.setBorder(new EmptyBorder(50,0,0,0));
        addPage.add(textFieldPanel,BorderLayout.CENTER);

//        Event in side
//---------------------Check Box Event--------------------------------------------
        checkBoxTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(comChecks);
                if(checkBoxTitle.isSelected()){
                    titleIn.setEnabled(true);
                }
                else{
                    titleIn.setEnabled(false);
                    titleIn.setText("");
                }
            }
        });
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox.getSelectedIndex() >= 0){
                    comChecks = true;
                }
                else{
                    comChecks = false;
                }
            }
        });
        checkBoxFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(comChecks);
                if(checkBoxFont.isSelected() && comChecks){
                    fontWord.setEnabled(true);
                }
                else{
                    fontWord.setEnabled(false);
                    fontWord.setText("");
                }
            }
        });
        checkBoxBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(checkBoxBack.isSelected() && comChecks){
                    backWord.setEnabled(true);
                }
                else{
                    backWord.setEnabled(false);
                    backWord.setText("");
                }
            }
        });
//----------------------------------------------------------------------
//------------------------Button Event---------------------------------
        confirmTitle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(titleIn.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null,"You input in Title text field please","Error",JOptionPane.ERROR_MESSAGE);
                } else {
                    DB.addExcelSheet(titleIn.getText());
                    String[] arr = new String[DB.getWorkbook().getNumberOfSheets()-1];
                    for(int i = 1; i <= DB.getWorkbook().getNumberOfSheets()-1; i++) {
                        arr[i-1] = DB.getWorkbook().getSheetName(i);
                    }
                    comboBox.removeAllItems(); // Fix: use removeAllItems() instead of removeAll()
                    for(String s : arr) {
                        comboBox.addItem(s);
                    }
                }
            }
        });
        confirmFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fontWord.getText().equalsIgnoreCase("") && backWord.getText().equalsIgnoreCase("")) {
                    JOptionPane.showMessageDialog(null,"You input in Font Word text field please","Error",JOptionPane.ERROR_MESSAGE);
                } else {
                    String sheetName = (String) comboBox.getSelectedItem();
                    Sheet sheet = DB.getWorkbook().getSheet(sheetName);
                    int lastRowNum = 0;
                    if(sheet != null && sheet.getRow(0) == null){
                        lastRowNum = sheet.getLastRowNum();
                    }
                    else{
                        lastRowNum = sheet.getLastRowNum()+1;
                    }
                    DB.writeExcelSheet(sheetName, lastRowNum, 0, fontWord.getText());
                    DB.writeExcelSheet(sheetName, lastRowNum, 1, backWord.getText());
                }
            }

        });
//----------------------------------------------------------------------

//  End Event

        return addPage;
    }
}
class Quiz {
    public Quiz(){}
    public JPanel panelS(){
        JPanel quizpage = new JPanel();
        quizpage.setBackground(new Color(78,78,78));
        JLabel t = new JLabel();
        t.setBackground(new Color(78,78,78));
        t.setForeground(Color.white);
        t.setText("Quiz");
        quizpage.add(t);
        return quizpage;
    }
}
class Match {
    public Match(){}
    public JPanel panelS(){
        JPanel matchPage = new JPanel();
        matchPage.setBackground(new Color(78,78,78));
        JLabel t = new JLabel();
        t.setBackground(new Color(78,78,78));
        t.setForeground(Color.white);
        t.setText("Match");
        matchPage.add(t);
        return matchPage;
    }
}

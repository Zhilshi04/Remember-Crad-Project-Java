import org.apache.poi.ss.usermodel.Sheet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Word{
    private String fontWord;
    private String backWord;
    public Word(String fontWord,String backWord){
        this.fontWord = fontWord;
        this.backWord = backWord;
    }

    public String getFontWord() {
        return fontWord;
    }

    public String getBackWord() {
        return backWord;
    }

    public void setFontWord(String fontWord) {
        this.fontWord = fontWord;
    }

    public void setBackWord(String backWord) {
        this.backWord = backWord;
    }
}
public class Words {
    JFrame frame = new JFrame();
    Dimension size = frame.getSize();
    private ExcelMangement DB;
    private static Color bgColor = new Color(78,78,78);
    private ArrayList<Word> wordAll = new ArrayList<>();
    private ArrayList<String> sheetMenber = new ArrayList<>();
    private boolean comChecks = false;
    private JTable table;
    private  String[][] data;

    public Words(){
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
        JPanel content = new JPanel();
        content.setBackground(bgColor);
        content.setBorder(new EmptyBorder(30,0,10,0));
        JPanel wordPage = new JPanel();
        wordPage.setBackground(new Color(78,78,78));
        for(int i = 1;i<=DB.getWorkbook().getNumberOfSheets()-1;i++){
            sheetMenber.add(DB.getWorkbook().getSheetName(i));
        }
        String[] array = sheetMenber.toArray(new String[0]);
        comboBox = new JComboBox<>(array);
        wordPage.setBackground(bgColor);
        content.add(comboBox);
        comboBox.setPreferredSize(new Dimension(350,35));
        comboBox.setSelectedIndex(-1);

        table = new JTable();
        JButton showWords = new JButton("Show words");
        showWords.setEnabled(false);
        content.add(showWords);
        JPanel c = new JPanel();
//
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(720, 540));
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setBackground(bgColor);
//
        c.add(content);
        c.add(scrollPane);
        table.setEnabled(false);
        table.setVisible(false);
        table.setFont(new Font("Tahoma", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setPreferredScrollableViewportSize(new Dimension(420, 340));
        c.setLayout(new BoxLayout(c,BoxLayout.Y_AXIS));

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Sheet sheet =DB.getWorkbook().getSheet((String)comboBox.getSelectedItem());
                if(comboBox.getSelectedIndex() >= 0){
                    if ((sheet.getRow(0) == null || sheet.getRow(0).getCell(0) == null || sheet.getRow(0).getCell(0).getStringCellValue() == null || sheet.getRow(0).getCell(0).getStringCellValue().equals(""))){
                        showWords.setEnabled(false);
                    }
                    else {
                        showWords.setEnabled(true);
                    }
                }
                else {
                    showWords.setEnabled(false);
                }
            }
        });
        String[] columnNames = {"Clue Word", "Guess Word"};
        showWords.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sheetName = (String) comboBox.getSelectedItem();
                Sheet sheet = DB.getWorkbook().getSheet(sheetName);
                if (sheet == null) {
                    JOptionPane.showMessageDialog(null, "Selected sheet not found in the workbook", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int lastRowNum = sheet.getLastRowNum();
                data = new String[lastRowNum+1][2];
                for(int i = 0; i <= lastRowNum; i++){
                    data[i][0] = DB.ReadExcelSheet(sheetName, i, 0);
                    data[i][1] = DB.ReadExcelSheet(sheetName, i, 1);
                }
                TableModel model = new DefaultTableModel(data, columnNames);
                table.setModel(model);
                table.setVisible(true);
            }
        });

        wordPage.add(c,BorderLayout.CENTER);
        return wordPage;
    }
}
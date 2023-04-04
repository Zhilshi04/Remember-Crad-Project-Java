import org.apache.poi.ss.usermodel.Sheet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Match {
    private static Color bgColor = new Color(78,78,78);
    public static Font font = new Font("Tahoma", Font.PLAIN,16);

    private ArrayList<String> sheetMenber = new ArrayList<>();
    private ExcelMangement DB;
    private ArrayList<Word> wordUsed = new ArrayList<>();
    private ArrayList<Word> wordAll = new ArrayList<>();
    private ArrayList<Word> wordUse = new ArrayList<>();
    private int randIndexFont ;
    private int randIndexBack ;

    private int score = 0;
    public Match(){
        try{
            DB = new ExcelMangement(".\\data\\database.xlsx");
            UIManager.put("Button.font", font);
            UIManager.put("Label.font", font);
            UIManager.put("TextField.font", font);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public JPanel panelS(){
        ArrayList<JButton> buts = new ArrayList<>();
        ArrayList<String> fontWord = new ArrayList<>();
        ArrayList<String> backWord = new ArrayList<>();
        JPanel matchPage = new JPanel();
        ArrayList<JButton> btuUse = new ArrayList<>();
        JPanel content = new JPanel();
        JPanel comboboxIN = new JPanel();
        JComboBox comboBox;
        JButton confirm = new JButton("Confirm");
        for(int i = 1;i<=DB.getWorkbook().getNumberOfSheets()-1;i++){
            sheetMenber.add(DB.getWorkbook().getSheetName(i));
        }
        String[] array = sheetMenber.toArray(new String[0]);
        comboBox = new JComboBox<>(array);
        JPanel butGid = new JPanel();
        content.setBorder(new EmptyBorder(10,0,10,0));
        butGid.setBorder(new EmptyBorder(20,0,10,0));
        content.setBackground(bgColor);
        butGid.setBackground(bgColor);
        comboBox.setPreferredSize(new Dimension(720,35));
        butGid.setPreferredSize(new Dimension(700,700));
        butGid.setVisible(false);
        confirm.setEnabled(false);
        comboboxIN.setBackground(bgColor);
        for(int i = 1;i<=16;i++){
            JButton b = new JButton(i+"");
            butGid.add(b);
            b.setBackground(Color.gray);
            buts.add(b);
        }

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox.getSelectedIndex() >= 0 ){
                    confirm.setEnabled(true);
                }
            }
        });
//        Event and Word
        String sheetName = (String) comboBox.getSelectedItem();
        Sheet sheet = DB.getWorkbook().getSheet(sheetName);
        if (sheet == null) {
            JOptionPane.showMessageDialog(null, "Selected sheet not found in the workbook", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            int lastRowNum = (sheet.getLastRowNum());
            for(int i = 0; i <= lastRowNum; i++){
                Word word = new Word(sheet.getRow(i).getCell(0).getStringCellValue(),sheet.getRow(i).getCell(1).getStringCellValue());
                wordAll.add(word);
            }
        }
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wordUse.clear();
                fontWord.clear();
                backWord.clear();
                Integer[] s = {1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2};
                int x = 0;
                List<Integer> intList = Arrays.asList(s);
                Collections.shuffle(intList);
//                System.out.println(DB.getWorkbook().getSheet((String)comboBox.getSelectedItem()).getLastRowNum()+"");
                Random random = new Random();

                if(wordAll.size()>=8){
                    for(int i = 0;i<8;i++){
                        int randDom = random.nextInt(0,wordAll.size());
                        wordUse.add(wordAll.get(randDom));
                        wordAll.remove(randDom);
                        fontWord.add(wordUse.get(i).getFontWord());
                        backWord.add(wordUse.get(i).getBackWord());
                    }
                    System.out.println(fontWord.size()+"");
                    System.out.println(backWord.size()+"");
                    for(JButton b : buts){
                        if(!fontWord.isEmpty()){
                            randIndexFont = random.nextInt(fontWord.size());
                        }
                        if (!backWord.isEmpty()){
                            randIndexBack = random.nextInt(backWord.size());
                        }
                        if(intList.get(x).equals(1)){
                            b.setText(fontWord.get(randIndexFont));
                            fontWord.remove(randIndexFont);
                        }
                        else if(intList.get(x).equals(2)){
                            b.setText(backWord.get(randIndexBack));
                            backWord.remove(randIndexBack);
                        }
                        x++;
                    }
                    confirm.setEnabled(false);
                    butGid.setVisible(true);
                }
                else{
                    JOptionPane.showMessageDialog(null, "The word have not enough", "Error", JOptionPane.ERROR_MESSAGE);
                }
                for(JButton b : buts){
                    b.setEnabled(true);
                    b.setBackground(Color.gray);
                    b.setForeground(Color.black);
                }
            }
        });
        for(JButton b : buts){
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(btuUse.size()  == 1){
                        btuUse.add(b);
                        String word1 = btuUse.get(0).getText();
                        String word2 = btuUse.get(1).getText();
                        for(int i = 0;i<wordUse.size();i++){
                            if(word1 == wordUse.get(i).getBackWord() && word2 == wordUse.get(i).getFontWord()){
                                btuUse.get(0).setBackground(Color.green);
                                btuUse.get(1).setBackground(Color.green);
                                btuUse.get(1).setForeground(Color.white);
                                btuUse.get(0).setForeground(Color.white);
                                btuUse.get(0).setEnabled(false);
                                btuUse.get(1).setEnabled(false);
                                btuUse.clear();
                                score++;
                                break;
                            }
                            else if(word2 == wordUse.get(i).getBackWord() && word1 == wordUse.get(i).getFontWord()){
                                btuUse.get(0).setBackground(Color.green);
                                btuUse.get(1).setBackground(Color.green);
                                btuUse.get(1).setForeground(Color.white);
                                btuUse.get(0).setForeground(Color.white);
                                btuUse.get(0).setEnabled(false);
                                btuUse.get(1).setEnabled(false);
                                btuUse.clear();
                                score++;
                                break;
                            }
                        }
                    }
                    else if(btuUse.size() == 2){
                        btuUse.clear();
                    }
                    else{btuUse.add(b);}
                    if(score == 8){
                        confirm.setEnabled(true);
                    }
                    System.out.println("Score : "+score+"");
                    System.out.println("WordUse : "+wordUse.size()+"");
                    System.out.println("ButUse : "+btuUse.size()+"");
                }
            });
        }
//        End
        butGid.setLayout(new GridLayout(4,4,10,10));
        matchPage.setBackground(new Color(78,78,78));
        comboBox.setSelectedIndex(-1);
        comboboxIN.add(comboBox);
        comboboxIN.add(confirm);
        comboboxIN.setLayout(new BoxLayout(comboboxIN,BoxLayout.X_AXIS));
        content.add(comboboxIN);
        content.add(butGid);
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        matchPage.add(content);
        return matchPage;
    }
}
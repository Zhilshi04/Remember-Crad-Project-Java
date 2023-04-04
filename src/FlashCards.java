import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FlashCards extends JFrame{
    private JButton addButton;
    private JButton quizButton;
    private JButton wordButton;
    private JPanel mainPanel;
    private JPanel displayPanel;
    private JPanel menuPanel;
    private JButton matchButton;
    private ArrayList<JButton> buts = new ArrayList<>();
    private ExcelMangement DB;


    public FlashCards(){
        try {
             DB = new ExcelMangement(".\\data\\database.xlsx");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ArrayList<JPanel> usePanel = new ArrayList<>();
        Font font = new Font("Tahoma", Font.PLAIN,10);
        UIManager.put("Button.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("TextField.font", font);
        JPanel home = new Add().panelS();
        usePanel.add(home);
//        usePanel.add(displayPanel);
        buts.add(addButton);
        buts.add(matchButton);
        buts.add(quizButton);
        buts.add(wordButton);

        addButton.setPreferredSize(new Dimension(150, 140));
        matchButton.setPreferredSize(new Dimension(150,140));
        quizButton.setPreferredSize(new Dimension(150, 140));
        wordButton.setPreferredSize(new Dimension(150, 140));

        addButton.setBorderPainted(false);
        matchButton.setBorderPainted(false);
        quizButton.setBorderPainted(false);
        wordButton.setBorderPainted(false);

        addButton.setForeground(Color.BLACK);
        addButton.setBackground(Color.white);
        matchButton.setForeground(Color.WHITE);
        matchButton.setBackground(new Color(55,55,55));
        quizButton.setForeground(Color.WHITE);
        quizButton.setBackground(new Color(55,55,55));
        wordButton.setForeground(Color.WHITE);
        wordButton.setBackground(new Color(55,55,55));

        displayPanel.setBackground(Color.black);
        menuPanel.setBackground(Color.red);
//        displayPanel.setVisible(false);
        mainPanel.add(home);
        add(mainPanel);
//        Event Start
        for(JButton j : buts){
            j.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    j.setForeground(Color.BLACK);
                    j.setBackground(Color.white);
                    for(JButton x : buts){
                        if(x.equals(j)){
                            continue;
                        }
                        else{
                            x.setForeground(Color.WHITE);
                            x.setBackground(new Color(55,55,55));
                        }
                    }
                    if(e.getSource().equals(addButton)){
                        JPanel homePanel = new Add().panelS();
                        usePanel.get(0).setVisible(false);
                        if(usePanel.size()!=0){
                            usePanel.remove(0);
                        }
                        mainPanel.add(homePanel);
                        usePanel.add(homePanel);
                        System.out.println("Add");
                    }
                    else if(e.getSource().equals(quizButton)){
                        if(DB.getWorkbook().getNumberOfSheets() < 1){
                            JPanel quizPanel = new QuizPage().panelS();
                            usePanel.get(0).setVisible(false);
                            if(usePanel.size()!=0){
                                usePanel.remove(0);
                            }
                            mainPanel.add(quizPanel);
                            usePanel.add(quizPanel);
                            System.out.println("Quiz");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Selected sheet not found in the workbook or Sheet is not have data", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else if (e.getSource().equals(matchButton)) {
                        if(DB.getWorkbook().getNumberOfSheets() < 1){
                            JPanel MacthPanel = new Match().panelS();
                            usePanel.get(0).setVisible(false);
                            if(usePanel.size()!=0){
                                usePanel.remove(0);
                            }
                            mainPanel.add(MacthPanel);
                            usePanel.add(MacthPanel);
                            System.out.println("Macth");
                        }
                        else{
                            JOptionPane.showMessageDialog(null, "Selected sheet not found in the workbook or Sheet is not have data", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else if(e.getSource().equals(wordButton)){
                        JPanel wordPanel = new Words().panelS();
                        usePanel.get(0).setVisible(false);
                        if(usePanel.size()!=0){
                            usePanel.remove(0);
                        }
                        mainPanel.add(wordPanel);
                        usePanel.add(wordPanel);
                        System.out.println("Word");
                    }
                }
            });

        }
//        Event End
        setTitle("Flash Crad Helping Remember");
        setSize(1200,800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        new FlashCards();
    }
}
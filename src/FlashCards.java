import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FlashCards extends JFrame{
    private JButton addButton;
    private JButton quizButton;
    private JButton matchButton;
    private JPanel mainPanel;
    private JPanel displayPanel;
    private JPanel menuPanel;
    private ArrayList<JButton> buts = new ArrayList<>();


    public FlashCards(){
        ArrayList<JPanel> usePanel = new ArrayList<>();
        JPanel home = new Add().panelS();
        usePanel.add(home);
//        usePanel.add(displayPanel);
        buts.add(addButton);
        buts.add(quizButton);
        buts.add(matchButton);

        addButton.setPreferredSize(new Dimension(100, 180));
        quizButton.setPreferredSize(new Dimension(100, 180));
        matchButton.setPreferredSize(new Dimension(100, 180));

        addButton.setBorderPainted(false);
        quizButton.setBorderPainted(false);
        matchButton.setBorderPainted(false);

        addButton.setForeground(Color.BLACK);
        addButton.setBackground(Color.white);
        quizButton.setForeground(Color.WHITE);
        quizButton.setBackground(new Color(55,55,55));
        matchButton.setForeground(Color.WHITE);
        matchButton.setBackground(new Color(55,55,55));

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
                        JPanel quizPanel = new Quiz().panelS();
                        usePanel.get(0).setVisible(false);
                        if(usePanel.size()!=0){
                            usePanel.remove(0);
                        }
                        mainPanel.add(quizPanel);
                        usePanel.add(quizPanel);
                        System.out.println("Quiz");
                    }
                    else if (e.getSource().equals(matchButton)) {
                        JPanel AddPanel = new Match().panelS();
                        usePanel.get(0).setVisible(false);
                        if(usePanel.size()!=0){
                            usePanel.remove(0);
                        }
                        mainPanel.add(AddPanel);
                        usePanel.add(AddPanel);
                        System.out.println("Add");
                    }
                }
            });
        }
//        Event End
        setTitle("Flash Crad Helping Remember");
        setSize(950,540);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new FlashCards();
    }
}

import javax.swing.*;

public class Main {

    public static JLabel resultLabel;

    public static void main(String[] args) {

        JFrame mainFrame = new JFrame();
        mainFrame.setTitle("Jeu");
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        JPanel gamePanel = new JPanel();
        JLabel gamePrinciple = new JLabel("Guess the right number");
        resultLabel = new JLabel();
        JButton guessButtonOption1 = new JButton("1");
        JButton guessButtonOption2 = new JButton("2");
        JButton resetButton = new JButton("retry");

        gamePanel.add(gamePrinciple);
        gamePanel.add(guessButtonOption1);
        gamePanel.add(guessButtonOption2);
        gamePanel.add(resetButton);
        gamePanel.add(resultLabel);
        mainFrame.add(gamePanel);

        guessButtonOption1.addActionListener(e -> {
            checkValue(guessButtonOption1);
        });
        guessButtonOption2.addActionListener(e -> {
            checkValue(guessButtonOption2);
        });

        resetButton.addActionListener(e -> {
            resultLabel.setText("");
        });
    }

    public static void checkValue(JButton btn){
        if(btn.getText().equals("2")) {
            resultLabel.setText("Youhou, right number");
        } else {
            resultLabel.setText("Meh, wrong number");
        }
    }
}
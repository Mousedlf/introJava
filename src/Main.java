import Entity.Joke;

import javax.swing.*;
import java.awt.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    // Constants for Colors Fonts and Dimensions
    private static final Color BACKGROUND_COLOR = new Color(230, 240, 255);
    private static final Color TITLE_COLOR = new Color(0, 102, 204);
    private static final Color BUTTON_COLOR = new Color(153, 204, 255);
    private static final Color RESULT_TEXT_COLOR = Color.BLACK;
    private static final Dimension FRAME_SIZE = new Dimension(800, 600);
    private static final Dimension BUTTON_SIZE = new Dimension(150, 40);
    private static final Font H1_FONT = new Font("Arial", Font.BOLD, 30);
    private static final Font H2_FONT = new Font("Arial", Font.BOLD, 24);
    private static final Font RESULT_FONT = new Font("Arial", Font.PLAIN, 20);

    private static JLabel resultLabel;
    private static JTextArea jokeOutput;

    public static void main(String[] args) {

       // Main Frame with BorderLayout
        JFrame mainFrame = new JFrame("Jeux");
        mainFrame.setSize(FRAME_SIZE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        // Top Panel for Title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(BACKGROUND_COLOR);
        JLabel mainTitle = new JLabel("Guess the Right Number & Fetch a Joke");
        mainTitle.setFont(H1_FONT);
        mainTitle.setForeground(TITLE_COLOR);
        titlePanel.add(mainTitle);

        // Center Panel for both games with GridBagLayout to center each sub-panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Number Guessing Game Panel with GridBagLayout
        JPanel guessingGamePanel = new JPanel(new GridBagLayout());
        guessingGamePanel.setBackground(BACKGROUND_COLOR);

        GridBagConstraints guessingGbc = new GridBagConstraints();
        guessingGbc.insets = new Insets(5, 5, 5, 5);
        guessingGbc.gridx = 0;
        guessingGbc.gridy = 0;

        // Game title for guessing game
        JLabel guessingGameTitle = new JLabel("Guess the Right Number");
        guessingGameTitle.setFont(H2_FONT);
        guessingGameTitle.setForeground(TITLE_COLOR);
        guessingGamePanel.add(guessingGameTitle, guessingGbc);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton guessButtonOption1 = new JButton("1");
        JButton guessButtonOption2 = new JButton("2");
        guessButtonOption1.setPreferredSize(BUTTON_SIZE);
        guessButtonOption2.setPreferredSize(BUTTON_SIZE);
        buttonPanel.add(guessButtonOption1);
        buttonPanel.add(guessButtonOption2);

        guessingGbc.gridy = 1;
        guessingGamePanel.add(buttonPanel, guessingGbc);

        resultLabel = new JLabel("");
        resultLabel.setFont(RESULT_FONT);
        resultLabel.setForeground(RESULT_TEXT_COLOR);
        guessingGbc.gridy = 2;
        guessingGamePanel.add(resultLabel, guessingGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(guessingGamePanel, gbc);

        // Joke Fetching Game Panel with GridBagLayout
        JPanel jokeGamePanel = new JPanel(new GridBagLayout());
        jokeGamePanel.setBackground(BACKGROUND_COLOR);

        GridBagConstraints jokeGbc = new GridBagConstraints();
        jokeGbc.insets = new Insets(5, 5, 5, 5);
        jokeGbc.gridx = 0;
        jokeGbc.gridy = 0;

        JLabel jokeLabel = new JLabel("Fetch a Joke");
        jokeLabel.setFont(H2_FONT);
        jokeLabel.setForeground(TITLE_COLOR);
        jokeGamePanel.add(jokeLabel, jokeGbc);

        JButton jokeFetcher = new JButton("Get a joke");
        jokeFetcher.setPreferredSize(BUTTON_SIZE);
        jokeFetcher.setBackground(BUTTON_COLOR);

        jokeGbc.gridy = 1;
        jokeGamePanel.add(jokeFetcher, jokeGbc);

        jokeOutput = new JTextArea("", 3, 25);
        jokeOutput.setFont(RESULT_FONT);
        jokeOutput.setForeground(RESULT_TEXT_COLOR);
        jokeOutput.setLineWrap(true);
        jokeOutput.setWrapStyleWord(true);
        jokeOutput.setEditable(false);
        jokeOutput.setOpaque(false);

        jokeGbc.gridy = 2;
        jokeGamePanel.add(jokeOutput, jokeGbc);

        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        centerPanel.add(jokeGamePanel, gbc);

        // Adding panels to the main frame
        mainFrame.add(titlePanel, BorderLayout.NORTH);
        mainFrame.add(centerPanel, BorderLayout.CENTER);
        mainFrame.setVisible(true);


        //
        guessButtonOption1.addActionListener(e -> {
            checkValue(guessButtonOption1);
        });
        guessButtonOption2.addActionListener(e -> {
            checkValue(guessButtonOption2);
        });

        jokeFetcher.addActionListener(e -> {
            try {
                getAJoke();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void checkValue(JButton btn){
        if(btn.getText().equals("2")) {
            resultLabel.setText("Youhou, right number");
        } else {
            resultLabel.setText("Meh, wrong number");
        }
    }

    public static void getAJoke() throws Exception{
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://api.chucknorris.io/jokes/random"))
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.body());
        String jokeValue = jsonNode.get("value").asText();

        Joke joke = new Joke(jokeValue);
        jokeOutput.setText(joke.value);
    }
}
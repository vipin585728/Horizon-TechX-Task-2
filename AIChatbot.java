//TASK 3: Artificial Intelligence Chatbot

//Create a Java-based chatbot for interactive communication.

//Use Natural Language Processing (NLP) techniques.

//Implement machine learning logic or rule-based answers.

//Train the bot to respond to frequently asked questions.

//Integrate with a GUI or web interface for real-time interaction.
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class AIChatbot extends JFrame {

    // =========================================================
    // INTENT CLASS
    // Stores training data for the chatbot
    // =========================================================
    static class Intent {
        String name;
        List<String> trainingQuestions;
        String response;

        Intent(String name, String response, String... questions) {
            this.name = name;
            this.response = response;
            this.trainingQuestions = Arrays.asList(questions);
        }
    }

    // =========================================================
    // CHATBOT CLASS
    // =========================================================
    static class ChatBot {

        private List<Intent> intents;
        private Set<String> stopWords;

        ChatBot() {
            intents = new ArrayList<>();

            // NLP Stop Words
            stopWords = new HashSet<>(Arrays.asList(
                    "a", "an", "the", "is", "are", "am",
                    "i", "you", "me", "my", "your",
                    "to", "of", "in", "on", "for",
                    "and", "or", "can", "could",
                    "please", "tell", "about", "what",
                    "how", "do", "does", "this"
            ));

            // =================================================
            // TRAINING DATA / FAQ DATA
            // =================================================

            intents.add(new Intent(
                    "GREETING",
                    "Hello! 👋 Welcome to AI Chatbot. How can I help you?",
                    "hello",
                    "hi",
                    "hey",
                    "good morning",
                    "good afternoon",
                    "good evening"
            ));

            intents.add(new Intent(
                    "NAME",
                    "My name is Java AI Chatbot. You can call me JBot.",
                    "what is your name",
                    "who are you",
                    "tell me your name",
                    "your name"
            ));

            intents.add(new Intent(
                    "JAVA",
                    "Java is a high-level, object-oriented and platform-independent programming language.",
                    "what is java",
                    "tell me about java",
                    "java programming",
                    "java language"
            ));

            intents.add(new Intent(
                    "OOPS",
                    "The four main OOP concepts are Encapsulation, Inheritance, Polymorphism and Abstraction.",
                    "what is oops",
                    "oops concepts",
                    "object oriented programming",
                    "four pillars of oops"
            ));

            intents.add(new Intent(
                    "NLP",
                    "NLP stands for Natural Language Processing. It allows computers to understand and process human language.",
                    "what is nlp",
                    "natural language processing",
                    "explain nlp",
                    "nlp meaning"
            ));

            intents.add(new Intent(
                    "AI",
                    "Artificial Intelligence enables machines to perform tasks that normally require human intelligence.",
                    "what is artificial intelligence",
                    "what is ai",
                    "explain artificial intelligence",
                    "ai meaning"
            ));

            intents.add(new Intent(
                    "CHATBOT",
                    "A chatbot is a software application that communicates with users using text or voice.",
                    "what is chatbot",
                    "define chatbot",
                    "how chatbot works",
                    "chat bot meaning"
            ));

            intents.add(new Intent(
                    "INTERNSHIP",
                    "An internship provides practical experience and helps students improve their technical and professional skills.",
                    "what is internship",
                    "why internship is important",
                    "internship benefits",
                    "tell me about internship"
            ));

            intents.add(new Intent(
                    "PROJECT",
                    "This project is an AI Chatbot developed using Java, NLP preprocessing, rule-based intent matching and Swing GUI.",
                    "what is this project",
                    "tell me about this project",
                    "project details",
                    "explain project"
            ));

            intents.add(new Intent(
                    "HELP",
                    "You can ask me about Java, OOP, AI, NLP, Chatbots, internships and this project.",
                    "help me",
                    "what can you do",
                    "help",
                    "what questions can i ask"
            ));

            intents.add(new Intent(
                    "THANKS",
                    "You're welcome! 😊",
                    "thank you",
                    "thanks",
                    "thank you so much",
                    "thanks chatbot"
            ));

            intents.add(new Intent(
                    "BYE",
                    "Goodbye! 👋 Have a great day!",
                    "bye",
                    "goodbye",
                    "see you",
                    "exit"
            ));
        }

        // =====================================================
        // NLP PREPROCESSING
        // =====================================================
        private List<String> preprocess(String text) {

            // Convert to lowercase
            text = text.toLowerCase();

            // Remove special characters
            text = text.replaceAll("[^a-zA-Z0-9 ]", "");

            // Tokenization
            String[] words = text.split("\\s+");

            List<String> tokens = new ArrayList<>();

            // Stop-word removal
            for (String word : words) {
                if (!word.isEmpty() && !stopWords.contains(word)) {
                    tokens.add(word);
                }
            }

            return tokens;
        }

        // =====================================================
        // SIMILARITY / INTENT MATCHING
        // =====================================================
        private double calculateSimilarity(
                List<String> userWords,
                List<String> questionWords) {

            if (userWords.isEmpty() || questionWords.isEmpty()) {
                return 0;
            }

            Set<String> userSet = new HashSet<>(userWords);
            Set<String> questionSet = new HashSet<>(questionWords);

            Set<String> common = new HashSet<>(userSet);
            common.retainAll(questionSet);

            Set<String> total = new HashSet<>(userSet);
            total.addAll(questionSet);

            if (total.isEmpty()) {
                return 0;
            }

            // Jaccard similarity
            return (double) common.size() / total.size();
        }

        // =====================================================
        // GET RESPONSE
        // =====================================================
        public String getResponse(String userInput) {

            List<String> userWords = preprocess(userInput);

            double bestScore = 0;
            Intent bestIntent = null;

            for (Intent intent : intents) {

                for (String question : intent.trainingQuestions) {

                    List<String> questionWords = preprocess(question);

                    double score =
                            calculateSimilarity(userWords, questionWords);

                    if (score > bestScore) {
                        bestScore = score;
                        bestIntent = intent;
                    }
                }
            }

            // Minimum confidence
            if (bestIntent != null && bestScore >= 0.20) {
                return bestIntent.response;
            }

            // Rule-based keyword matching
            String text = userInput.toLowerCase();

            if (text.contains("java")) {
                return "Java is an object-oriented and platform-independent programming language.";
            }

            if (text.contains("nlp")) {
                return "NLP means Natural Language Processing.";
            }

            if (text.contains("ai")) {
                return "AI means Artificial Intelligence.";
            }

            if (text.contains("internship")) {
                return "Internship helps students gain practical industry experience.";
            }

            // Fallback response
            return "Sorry, I don't understand that yet. "
                    + "Please ask about Java, OOP, AI, NLP, Chatbot or Internship.";
        }
    }

    // =========================================================
    // GUI COMPONENTS
    // =========================================================
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private JButton clearButton;
    private JButton exitButton;

    private ChatBot bot;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================
    public AIChatbot() {

        bot = new ChatBot();

        setTitle("AI Chatbot - Java NLP Project");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        // =====================================================
        // TITLE
        // =====================================================
        JLabel title = new JLabel(
                "🤖 Artificial Intelligence Chatbot",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("Arial", Font.BOLD, 24)
        );

        mainPanel.add(title, BorderLayout.NORTH);

        // =====================================================
        // CHAT AREA
        // =====================================================
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane scrollPane =
                new JScrollPane(chatArea);

        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // INPUT PANEL
        // =====================================================
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));

        inputField = new JTextField();
        inputField.setFont(
                new Font("Arial", Font.PLAIN, 16)
        );

        sendButton = new JButton("Send");
        sendButton.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        inputPanel.add(
                inputField,
                BorderLayout.CENTER
        );

        inputPanel.add(
                sendButton,
                BorderLayout.EAST
        );

        // =====================================================
        // BUTTON PANEL
        // =====================================================
        JPanel buttonPanel = new JPanel(
                new FlowLayout(FlowLayout.CENTER)
        );

        clearButton = new JButton("Clear Chat");
        exitButton = new JButton("Exit");

        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        JPanel bottomPanel = new JPanel(
                new BorderLayout()
        );

        bottomPanel.add(
                inputPanel,
                BorderLayout.NORTH
        );

        bottomPanel.add(
                buttonPanel,
                BorderLayout.SOUTH
        );

        mainPanel.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(mainPanel);

        // =====================================================
        // WELCOME MESSAGE
        // =====================================================
        chatArea.append(
                "Bot: Hello! 👋 I am your Java AI Chatbot.\n"
                        + "Bot: Ask me anything about Java, AI, NLP, OOP, "
                        + "Chatbot or Internship.\n\n"
        );

        // =====================================================
        // SEND BUTTON EVENT
        // =====================================================
        sendButton.addActionListener(e -> sendMessage());

        // =====================================================
        // ENTER KEY EVENT
        // =====================================================
        inputField.addActionListener(e -> sendMessage());

        // =====================================================
        // CLEAR BUTTON
        // =====================================================
        clearButton.addActionListener(e -> {
            chatArea.setText("");
            chatArea.append(
                    "Bot: Chat cleared. How can I help you?\n\n"
            );
        });

        // =====================================================
        // EXIT BUTTON
        // =====================================================
        exitButton.addActionListener(e -> {
            System.exit(0);
        });
    }

    // =========================================================
    // SEND MESSAGE
    // =========================================================
    private void sendMessage() {

        String userMessage =
                inputField.getText().trim();

        if (userMessage.isEmpty()) {
            return;
        }

        // Display user message
        chatArea.append(
                "You: " + userMessage + "\n"
        );

        // Get chatbot response
        String response =
                bot.getResponse(userMessage);

        // Display bot response
        chatArea.append(
                "Bot: " + response + "\n\n"
        );

        // Clear input
        inputField.setText("");

        // Auto scroll
        chatArea.setCaretPosition(
                chatArea.getDocument().getLength()
        );
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            AIChatbot chatbot =
                    new AIChatbot();

            chatbot.setVisible(true);
        });
    }
}
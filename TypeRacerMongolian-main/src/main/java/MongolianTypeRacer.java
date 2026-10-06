import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class MongolianTypeRacer extends JFrame {

    private static final Color BG = new Color(50, 65, 73);
    private static final Color PANEL_BG = new Color(43, 45, 48);
    private static final Color TEXT = new Color(209, 209, 197);
    private static final Color MUTED = new Color(100, 102, 105);
    private static final Color ACCENT = new Color(70, 183, 20);
    private static final Color ERROR = new Color(202, 71, 84);

    private static final String DEFAULT_TEXT =
        "Хэнтий Хангай Саяны өндөр сайхан нуруунууд Хойд зүгийн чимэг болсон ой хөвч уулнууд " +
        "Мэнэн Шарга Номины өргөн их говиуд Өмнө зүгийн манлай болсон элсэн манхан далайнууд " +
        "Энэ бол миний төрсөн нутаг Монголын сайхан орон";

    private static final String OneMore_random_article = "Хоосон буунаас хорин хүн айдаг\n" +
        "Айхаа арьсанд боож, ичихээ эсгийд баглах\n" +
        "Авахдаа аварга могой, өгөхдөө өрөлбө бахь\n" +
        "Авахыг мэдвэл өгөхөө мэд, өгөхийг мэдвэл авахаа мэд\n" +
        "Авах идэхэд шунагч өнгөч зусарч хүн";

    private static final String[] PRACTICE_TEXTS = {
        DEFAULT_TEXT,
        OneMore_random_article,
        "Алсын уулсын оройд өглөөний нар тусахад талын салхи зөөлөн үлээж, адуун сүрэг тоос татуулан давхина. Нутаг минь намрын өнгөөр гоёж, голын ус тунгалагхан мяралзана.",
        "Саруул талын дээгүүр цэнхэр тэнгэр цэлмэж, саахалтын айлын яндангаас утаа суунаглана. Эхийн сэтгэл шиг дулаахан энэ нутагтаа хүн бүр хайртай.",
        "Морин хуурын аялгуу намрын салхитай нийлж, монгол гэрийн хойморт дотнохон эгшиглэнэ. Буурал түүхээ санах бүрд бахархал зүрхэнд асна.",
        "Тэнгэрийн хаяанд үүлс нүүж, талын зам алсад сунана. Зорьсон хүн зорилгодоо хүрдэг шиг хичээсэн гар хурдан бичиж сурна.",
        "Хаврын бороо шиврэхэд хээрийн өвс сэргэж, голын эрэг ногоорон амилна. Шувуудын дуу өглөөг угтаж, шинэ өдрийн ажил эхэлнэ."
    };
    private static final Random RANDOM = new Random();
    private static final String[] CLEAN_PRACTICE_TEXTS = {
        "Хэнтий Хангай Саяны өндөр сайхан нуруунууд хойд зүгийн чимэг болсон ой хөвч уулнууд Мэнэн Шарга Номины өргөн их говиуд өмнө зүгийн манлай болсон элсэн манхан далайнууд Энэ бол миний төрсөн нутаг Монголын сайхан орон",
        "Хоосон буунаас хорин хүн айдаг. Айхаа арьсанд боож, ичихээ эсгийд баглах. Авахыг мэдвэл өгөхөө мэд, өгөхийг мэдвэл авахаа мэд.",
        "Алсын уулсын оройд өглөөний нар тусахад талын салхи зөөлөн үлээж, адуун сүрэг тоос татуулан давхина. Нутаг минь намрын өнгөөр гоёж, голын ус тунгалагхан мяралзана.",
        "Саруул талын дээгүүр цэнхэр тэнгэр цэлмэж, саахалтын айлын яндангаас утаа суунаглана. Эхийн сэтгэл шиг дулаахан энэ нутагтаа хүн бүр хайртай.",
        "Морин хуурын аялгуу намрын салхитай нийлж, монгол гэрийн хойморт дотнохон эгшиглэнэ. Буурал түүхээ санах бүрд бахархал зүрхэнд асна.",
        "Тэнгэрийн хаяанд үүлс нүүж, талын зам алсад сунана. Зорьсон хүн зорилгодоо хүрдэг шиг хичээсэн гар хурдан бичиж сурна.",
        "Хаврын бороо шиврэхэд хээрийн өвс сэргэж, голын эрэг ногоорон амилна. Шинэ өдрийн ажил эхэлж, хүн бүр зорилгоо биелүүлнэ."
    };
    private static final Difficulty[] DIFFICULTIES = {
        new Difficulty("easy", 18, 28),
        new Difficulty("medium", 45, 80),
        new Difficulty("hard", 90, 130)
    };
    private static final String[] BOT_NAMES = {"bot-1", "bot-2", "bot-3"};

    private String targetText = selectRandomTargetText(null);
    private long startTime = 0;
    private boolean isRacing = false;
    private Timer wpmTimer;
    private Timer botTimer;
    private Difficulty currentDifficulty = DIFFICULTIES[0];
    private double[] botProgress = new double[BOT_NAMES.length];
    private double[] botWpm = new double[BOT_NAMES.length];
    private JButton[] modeButtons;

    private JTextPane textDisplayPane;
    private JTextField inputField;
    private JLabel timeValueLabel;
    private JLabel wpmLabel;
    private JLabel accuracyValueLabel;
    private JButton resetButton;
    private RacePanel racePanel;
    private JTextArea leaderboardArea;

    private static String selectRandomTargetText(String currentText) {
        if (CLEAN_PRACTICE_TEXTS.length == 1) {
            return CLEAN_PRACTICE_TEXTS[0];
        }

        String nextText;
        do {
            nextText = CLEAN_PRACTICE_TEXTS[RANDOM.nextInt(CLEAN_PRACTICE_TEXTS.length)];
        } while (nextText.equals(currentText));
        return nextText;
    }

    public MongolianTypeRacer() {
        setTitle("Шивцгээе");
        setSize(1020, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        initUI();
        setupListeners();
        loadLeaderboard();
    }

    private void initUI() {
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout());
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(32, 48, 28, 48));

        JPanel rootPanel = new JPanel(new BorderLayout(22, 26));
        rootPanel.setOpaque(false);
        add(rootPanel, BorderLayout.CENTER);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 22, 0));

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        titlePanel.setOpaque(false);
        JLabel markLabel = new JLabel("●");
        markLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        markLabel.setForeground(ACCENT);
        JLabel titleLabel = new JLabel("Шивцгээе");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(TEXT);
        JLabel subtitleLabel = new JLabel("монгол хэл ээ сайжруулгаая");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitleLabel.setForeground(MUTED);
        titlePanel.add(markLabel);
        titlePanel.add(titleLabel);
        titlePanel.add(subtitleLabel);

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 18, 0));
        statsPanel.setOpaque(false);
        timeValueLabel = new JLabel("0.0с", SwingConstants.CENTER);
        wpmLabel = new JLabel("0", SwingConstants.CENTER);
        accuracyValueLabel = new JLabel("100%", SwingConstants.CENTER);
        statsPanel.add(buildStatCard(timeValueLabel, "time"));
        statsPanel.add(buildStatCard(wpmLabel, "wpm"));
        statsPanel.add(buildStatCard(accuracyValueLabel, "acc"));

        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(statsPanel, BorderLayout.EAST);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 22));
        mainPanel.setOpaque(false);

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        modePanel.setOpaque(false);
        modeButtons = new JButton[DIFFICULTIES.length];
        for (int i = 0; i < DIFFICULTIES.length; i++) {
            Difficulty difficulty = DIFFICULTIES[i];
            JButton modeButton = buildModeButton(difficulty.name);
            modeButton.addActionListener(e -> setDifficulty(difficulty));
            modeButtons[i] = modeButton;
            modePanel.add(modeButton);
        }
        refreshModeButtons();

        racePanel = new RacePanel();
        racePanel.setPreferredSize(new Dimension(0, 122));
        racePanel.setBackground(BG);
        racePanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        textDisplayPane = new JTextPane();
        textDisplayPane.setEditable(false);
        textDisplayPane.setFont(new Font("Segoe UI", Font.PLAIN, 29));
        textDisplayPane.setForeground(MUTED);
        textDisplayPane.setBackground(BG);
        textDisplayPane.setMargin(new Insets(24, 12, 24, 12));
        updateTextHighlighting(0);

        JScrollPane textScrollPane = new JScrollPane(textDisplayPane);
        textScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        textScrollPane.setBackground(BG);
        textScrollPane.getViewport().setBackground(BG);

        JPanel inputPanel = new JPanel(new BorderLayout(12, 0));
        inputPanel.setOpaque(false);
        inputPanel.setBorder(new EmptyBorder(8, 0, 0, 0));
        JLabel inputTitle = new JLabel("Энд бичнэ үү:");
        inputTitle.setForeground(ACCENT);
        inputTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        styleTextField(inputField);
        inputPanel.add(inputTitle, BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);

        JPanel typingPanel = new JPanel(new BorderLayout(0, 10));
        typingPanel.setOpaque(false);
        typingPanel.add(inputPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setOpaque(false);
        centerPanel.add(modePanel, BorderLayout.NORTH);
        centerPanel.add(racePanel, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.NORTH);
        mainPanel.add(textScrollPane, BorderLayout.CENTER);
        mainPanel.add(typingPanel, BorderLayout.SOUTH);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.setBorder(new EmptyBorder(62, 8, 8, 0));
        rightPanel.setPreferredSize(new Dimension(205, 0));
        JLabel leaderboardTitle = new JLabel("leaderboard");
        leaderboardTitle.setForeground(ACCENT);
        leaderboardTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        leaderboardArea = new JTextArea();
        leaderboardArea.setEditable(false);
        leaderboardArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        leaderboardArea.setForeground(MUTED);
        leaderboardArea.setBackground(BG);
        leaderboardArea.setBorder(new EmptyBorder(14, 0, 14, 0));
        JScrollPane leaderboardScrollPane = new JScrollPane(leaderboardArea);
        leaderboardScrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        leaderboardScrollPane.setBackground(BG);
        leaderboardScrollPane.getViewport().setBackground(BG);
        resetButton = new JButton("↻");
        styleButton(resetButton);
        rightPanel.add(leaderboardTitle, BorderLayout.NORTH);
        rightPanel.add(leaderboardScrollPane, BorderLayout.CENTER);
        rightPanel.add(resetButton, BorderLayout.SOUTH);

        rootPanel.add(headerPanel, BorderLayout.NORTH);
        rootPanel.add(mainPanel, BorderLayout.CENTER);
        rootPanel.add(rightPanel, BorderLayout.EAST);
    }

    private JPanel buildStatCard(JLabel valueLabel, String caption) {
        JPanel card = new JPanel(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(0, 4, 0, 4));

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(ACCENT);
        valueLabel.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel captionLabel = new JLabel(caption, SwingConstants.CENTER);
        captionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        captionLabel.setForeground(MUTED);

        JPanel inner = new JPanel();
        inner.setOpaque(false);
        inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
        valueLabel.setAlignmentX(CENTER_ALIGNMENT);
        captionLabel.setAlignmentX(CENTER_ALIGNMENT);
        inner.add(valueLabel);
        inner.add(captionLabel);

        card.add(inner, BorderLayout.CENTER);
        return card;
    }

    private JButton buildModeButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(7, 12, 7, 12));
        return button;
    }

    private void refreshModeButtons() {
        if (modeButtons == null) {
            return;
        }

        for (int i = 0; i < modeButtons.length; i++) {
            JButton button = modeButtons[i];
            boolean selected = DIFFICULTIES[i] == currentDifficulty;
            button.setForeground(selected ? BG : MUTED);
            button.setBackground(selected ? ACCENT : PANEL_BG);
            button.setContentAreaFilled(true);
        }
    }

    private void setDifficulty(Difficulty difficulty) {
        currentDifficulty = difficulty;
        refreshModeButtons();
        resetRace();
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        field.setForeground(TEXT);
        field.setBackground(BG);
        field.setCaretColor(ACCENT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, PANEL_BG),
            BorderFactory.createEmptyBorder(8, 2, 8, 2)
        ));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI Symbol", Font.BOLD, 24));
        button.setForeground(MUTED);
        button.setBackground(BG);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
    }

    private void setupListeners() {
        inputField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                verifyTyping();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                verifyTyping();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                verifyTyping();
            }
        });

        resetButton.addActionListener(e -> showResetOptions());
        textDisplayPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                focusInput();
            }
        });
        racePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                focusInput();
            }
        });

        wpmTimer = new Timer(200, e -> updateWpmDisplay());
        botTimer = new Timer(200, e -> updateBotProgress());
    }

    private void focusInput() {
        inputField.requestFocusInWindow();
    }

    private void showResetOptions() {
        Object[] options = {"Тоглоомыг дахин эхлүүлэх", "Тоглоом болон + Онооны самбарыг шинэчилэх", "Cancel"};
        int choice = JOptionPane.showOptionDialog(
            this,
            "Сонголтоо хийнэ үү.\nДахин эхлэх нь тоглоомыг дахин эхлүүлнэ.\nОнооны самбарыг шинэчлэх. Энэ нь бүх оноог устгана.\nТа юу хийхийг хүсч байна?",
            "Reset",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );

        if (choice == 0) {
            resetRace();
        } else if (choice == 1) {
            resetRace();
            LeaderboardManager.clear();
            loadLeaderboard();
        }
    }

    private void verifyTyping() {
        String typed = inputField.getText();

        if (!isRacing && !typed.isEmpty()) {
            isRacing = true;
            startTime = System.currentTimeMillis();
            startBots();
            wpmTimer.start();
        }

        if (targetText.startsWith(typed)) {
            inputField.setBackground(BG);
            int matchedChars = typed.length();
            double progress = (double) matchedChars / targetText.length();
            racePanel.updateProgress(progress, botProgress);
            updateTextHighlighting(matchedChars);
            updateStats();

            if (typed.equals(targetText)) {
                endRace(true);
            }
        } else {
            inputField.setBackground(new Color(74, 43, 48));
            accuracyValueLabel.setText("0%");
        }
    }

    private void updateWpmDisplay() {
        if (startTime == 0) {
            return;
        }

        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed == 0) {
            return;
        }

        double minutes = elapsed / 60000.0;
        int matchedChars = inputField.getText().length();
        int wpm = (int) ((matchedChars / 5.0) / minutes);
        wpmLabel.setText(String.valueOf(wpm));
        timeValueLabel.setText(String.format("%.1fс", elapsed / 1000.0));
    }

    private void updateTextHighlighting(int completedLength) {
        textDisplayPane.setText(targetText);
        StyledDocument doc = textDisplayPane.getStyledDocument();

        SimpleAttributeSet completedStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(completedStyle, TEXT);
        StyleConstants.setBackground(completedStyle, BG);

        SimpleAttributeSet remainingStyle = new SimpleAttributeSet();
        StyleConstants.setForeground(remainingStyle, MUTED);
        StyleConstants.setBackground(remainingStyle, BG);

        doc.setCharacterAttributes(0, completedLength, completedStyle, true);
        doc.setCharacterAttributes(completedLength, targetText.length() - completedLength, remainingStyle, true);
    }

    private void startBots() {
        for (int i = 0; i < botProgress.length; i++) {
            botProgress[i] = 0.0;
            botWpm[i] = currentDifficulty.randomBotWpm();
        }
        botTimer.start();
        racePanel.updateProgress(getPlayerProgress(), botProgress);
    }

    private void updateBotProgress() {
        if (!isRacing || startTime == 0) {
            return;
        }

        double elapsedMinutes = (System.currentTimeMillis() - startTime) / 60000.0;
        for (int i = 0; i < botProgress.length; i++) {
            double botCharsTyped = botWpm[i] * 5.0 * elapsedMinutes;
            botProgress[i] = Math.min(1.0, botCharsTyped / targetText.length());
            if (botProgress[i] >= 1.0) {
                racePanel.updateProgress(getPlayerProgress(), botProgress);
                endRace(false);
                return;
            }
        }
        racePanel.updateProgress(getPlayerProgress(), botProgress);
    }

    private double getPlayerProgress() {
        return (double) inputField.getText().length() / targetText.length();
    }

    private void endRace(boolean playerWon) {
        if (playerWon) {
            endRace();
            return;
        }

        if (!isRacing) {
            return;
        }

        wpmTimer.stop();
        botTimer.stop();
        isRacing = false;
        JOptionPane.showMessageDialog(this, "Та ялагдлаа.\nMode: " + currentDifficulty.name);
        resetRace();
    }

    private void endRace() {
        wpmTimer.stop();
        botTimer.stop();
        isRacing = false;
        long elapsed = System.currentTimeMillis() - startTime;
        double minutes = elapsed / 60000.0;
        int finalWpm = (int) ((targetText.length() / 5.0) / minutes);

        wpmLabel.setText(String.valueOf(finalWpm));
        timeValueLabel.setText(String.format("%.1fс", elapsed / 1000.0));
        accuracyValueLabel.setText("100%");
        JOptionPane.showMessageDialog(this, "Баяр хүргэе! Та уралдааныг дуусгалаа.\nХурд: " + finalWpm + " WPM");

        String name = JOptionPane.showInputDialog(this, "Нэрээ оруулна уу:", "Амжилтын самбар", JOptionPane.PLAIN_MESSAGE);
        if (name != null && !name.trim().isEmpty()) {
            LeaderboardManager.save(name.trim(), finalWpm);
            loadLeaderboard();
        }
        resetRace();
    }

    private void resetRace() {
        isRacing = false;
        startTime = 0;
        if (wpmTimer.isRunning()) {
            wpmTimer.stop();
        }
        if (botTimer.isRunning()) {
            botTimer.stop();
        }
        targetText = selectRandomTargetText(targetText);
        inputField.setText("");
        inputField.setBackground(BG);
        wpmLabel.setText("0");
        timeValueLabel.setText("0.0с");
        accuracyValueLabel.setText("100%");
        botProgress = new double[BOT_NAMES.length];
        botWpm = new double[BOT_NAMES.length];
        racePanel.updateProgress(0.0, botProgress);
        updateTextHighlighting(0);
        SwingUtilities.invokeLater(this::focusInput);
    }

    private void updateStats() {
        String typed = inputField.getText();
        if (typed.isEmpty()) {
            accuracyValueLabel.setText("100%");
        } else if (targetText.startsWith(typed)) {
            accuracyValueLabel.setText("100%");
        } else {
            accuracyValueLabel.setText("0%");
        }
    }

    private void loadLeaderboard() {
        leaderboardArea.setText(LeaderboardManager.getDisplayString());
    }

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {
            MongolianTypeRacer frame = new MongolianTypeRacer();
            frame.setVisible(true);
            frame.focusInput();
        });
    }

    class RacePanel extends JPanel {
        private double playerProgress = 0.0;
        private double[] botLaneProgress = new double[BOT_NAMES.length];

        public void updateProgress(double playerProgress, double[] botProgress) {
            this.playerProgress = playerProgress;
            this.botLaneProgress = botProgress.clone();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int barWidth = Math.max(0, w - 88);
            int laneHeight = 26;
            int startY = 10;

            drawLane(g2, "you", playerProgress, startY, barWidth, ACCENT);
            for (int i = 0; i < botLaneProgress.length; i++) {
                drawLane(g2, BOT_NAMES[i], botLaneProgress[i], startY + ((i + 1) * laneHeight), barWidth, MUTED);
            }
        }

        private void drawLane(Graphics2D g2, String label, double progress, int y, int barWidth, Color racerColor) {
            int labelWidth = 54;
            int barX = labelWidth + 8;
            int barY = y + 12;
            int markerX = barX + (int) (barWidth * Math.min(1.0, Math.max(0.0, progress)));

            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g2.setColor(racerColor);
            g2.drawString(label, 4, y + 17);
            g2.setColor(PANEL_BG);
            g2.fillRoundRect(barX, barY, barWidth, 4, 4, 4);

            g2.setColor(racerColor);
            g2.fillRoundRect(barX, barY, markerX - barX, 4, 4, 4);
            g2.fillOval(markerX - 5, barY - 5, 14, 14);
        }
    }

    static class Difficulty {
        private final String name;
        private final int minBotWpm;
        private final int maxBotWpm;

        Difficulty(String name, int minBotWpm, int maxBotWpm) {
            this.name = name;
            this.minBotWpm = minBotWpm;
            this.maxBotWpm = maxBotWpm;
        }

        int randomBotWpm() {
            return minBotWpm + RANDOM.nextInt(maxBotWpm - minBotWpm + 1);
        }
    }

    static class LeaderboardManager {
        private static final String FILE_PATH = "leaderboard.txt";

        public static void save(String name, int wpm) {
            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, true), "UTF-8"))) {
                out.println(name + "," + wpm);
            } catch (IOException e) {
                System.err.println("Leaderboard save failed: " + e.getMessage());
            }
        }

        public static void clear() {
            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, false), "UTF-8"))) {
                out.print("");
            } catch (IOException e) {
                System.err.println("Leaderboard clear failed: " + e.getMessage());
            }
        }

        public static String getDisplayString() {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                return " Мэдээлэл байхгүй байна.";
            }

            List<String[]> entries = new ArrayList<>();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
                String line;
                while ((line = in.readLine()) != null) {
                    String[] tokens = line.split(",");
                    if (tokens.length == 2) {
                        entries.add(tokens);
                    }
                }
            } catch (IOException e) {
                return " Алдаа: Уншиж чадсангүй.";
            }

            entries.sort((a, b) -> Integer.compare(Integer.parseInt(b[1].trim()), Integer.parseInt(a[1].trim())));

            StringBuilder sb = new StringBuilder("  Байр | Нэр | Хурд (WPM)\n");
            sb.append("  -------------------------\n");
            int rank = 1;
            for (String[] entry : entries) {
                sb.append(String.format("   #%d  | %-6s | %s WPM\n", rank++, entry[0], entry[1]));
                if (rank > 10) {
                    break;
                }
            }
            return sb.toString();
        }
    }
}
    

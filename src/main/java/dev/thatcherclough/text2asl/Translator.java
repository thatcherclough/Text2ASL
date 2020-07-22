package dev.thatcherclough.text2asl;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import dev.thatcherclough.text2asl.player.Player;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Enumeration;

public class Translator extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel progressPanel = null;
    private SpringLayout progressPanelLayout = null;
    private String response = null;

    public Translator(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(0, 1));
        setResizable(false);
    }

    /**
     * Adds a JPanel composed of a JLabel with text {@link text}, a JTextField
     * {@link columns} columns long, and a JButton labeled 'Submit'. When either
     * enter or the submit button is pushed, the text in the JTextField is returned.
     * 
     * @param text    text to compose JLabel of
     * @param columns length of the JTextField
     * @return String contents of JTextField when either enter or the submit button
     *         is pushed
     * @throws InterruptedException
     */
    public String getInput(String text, int columns) throws InterruptedException {
        String ret = null;

        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        inputPanel.setLayout(new BorderLayout(5, 20));

        JLabel label = new JLabel(text);
        JTextField input = new JTextField();
        input.setColumns(columns);
        JButton submit = new JButton("Submit");

        KeyListener inputKeyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 10) {
                    submit.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        };
        ActionListener submitActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sentence = input.getText();
                if (!sentence.isEmpty()) {
                    input.setEditable(false);
                    input.removeKeyListener(inputKeyListener);
                    submit.removeActionListener(this);
                    response = sentence;
                }
            }
        };
        input.addKeyListener(inputKeyListener);
        submit.addActionListener(submitActionListener);

        inputPanel.add(label, BorderLayout.PAGE_START);
        inputPanel.add(input, BorderLayout.CENTER);
        inputPanel.add(submit, BorderLayout.LINE_END);

        add(inputPanel);
        refresh();
        setVisible(true);

        while (response == null)
            Thread.sleep(1);
        ret = response;
        response = null;
        return ret;
    }

    /**
     * Initiates {@link #progressPanel} and {@link #progressPanelLayout}, sets the
     * layout manager of {@link #progressPanel} to {@link #progressPanelLayout}, and
     * adds {@link progressPanel}.
     */
    public void initiateProgressPanel() {
        progressPanel = new JPanel();
        progressPanelLayout = new SpringLayout();
        progressPanel.setLayout(progressPanelLayout);
        add(progressPanel);
    }

    /**
     * Adds a JLabel with text {@link text} to {@link #progressPanel} {@link x} to
     * the right of 0,0 and {@ink y} down from 0,0.
     * 
     * @param text text to compose JLabel of
     * @param x    length to the right of 0,0 the JLabel should be added
     * @param y    length down from 0,0 the JLabel should be added
     */
    public void addToProgressPanel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        progressPanel.add(label);
        progressPanelLayout.putConstraint(SpringLayout.WEST, label, x, SpringLayout.WEST, progressPanel);
        progressPanelLayout.putConstraint(SpringLayout.NORTH, label, y, SpringLayout.NORTH, progressPanel);
        refresh();
    }

    /**
     * Initializes a new JFrame composed of a JLabel with text {@link text},
     * JRadioButtons for each element in {@link options}, and a JButton labeled
     * 'Submit'. When the submit button is pushed, returns the text of the selected
     * JRadioButton and disposes JFrame.
     * 
     * @param title   text to compose JLabel of
     * @param options ArrayList of text for each JRadioButton
     * @return String text of selected JRadioButton when the submit button is pushed
     * @throws InterruptedException
     */
    public String getRadioChoice(String title, ArrayList<String> options) throws InterruptedException {
        String ret = null;

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setLayout(new GridLayout(0, 1));

        JLabel label = new JLabel(title);
        ButtonGroup group = new ButtonGroup();
        JButton submit = new JButton("Submit");

        ActionListener submitActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                response = getSelectedButtonText(group);
            }
        };
        submit.addActionListener(submitActionListener);

        panel.add(label);
        for (String option : options) {
            JRadioButton button = new JRadioButton(option);
            group.add(button);
            panel.add(button);
        }
        panel.add(submit);

        frame.add(panel);
        frame.repaint();
        frame.pack();
        frame.setVisible(true);

        while (response == null)
            Thread.sleep(1);
        frame.dispose();
        ret = response;
        response = null;
        return ret;
    }

    /**
     * Gets the text of the selected JButton in ButtonGroup {@link group}.
     * 
     * @param group ButtonGroup to get selected JButton of
     * @return String text of the selected JButton in ButtonGroup
     */
    private String getSelectedButtonText(ButtonGroup group) {
        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected())
                return button.getText();
        }
        return null;
    }

    /**
     * Initiates a new JPanel composed of a VideoPanel to be used to play videos in
     * {@link videos}, and a JButton labeled 'Replay'. Adds this JPanel and plays
     * the videos. When the 'Replay' JButton is pushed, this method is run again.
     * 
     * @param videos paths of videos to play
     * @throws InterruptedException
     */
    public void playVideos(String[] videos) throws InterruptedException {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(640, 410));

        Player player = new Player(videos);
        JPanel videoPlayer = player.getPanel();
        JButton button = new JButton("Replay");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Thread replay = new Thread() {
                    public void run() {
                        try {
                            Text2ASL.frame.playVideos(videos);
                        } catch (Exception e) {
                            Text2ASL.error(e);
                        }
                    }
                };
                replay.start();
            }
        });

        panel.add(videoPlayer, BorderLayout.PAGE_START);
        panel.add(button, BorderLayout.SOUTH);

        clear();
        add(panel);
        refresh();
        player.playVideos();
    }

    /**
     * Clears and initiates/displays a JLabel composed of {@link text}.
     * 
     * @param text text to compose JLabel of and display
     */
    public void clearAndDisplay(String text) {
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel(text));

        clear();
        add(panel);
        refresh();
    }

    /**
     * Clears.
     */
    public void clear() {
        getContentPane().removeAll();
        repaint();
    }

    /**
     * Repaints and packs.
     */
    public void refresh() {
        repaint();
        pack();
    }
}
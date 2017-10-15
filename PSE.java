import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingWorker;



public class PSE extends JFrame {
    private final int borderX = 35;
    private final int borderY = 55;
    private final int windowX = 1000;
    private final int windowY = 650;
    private final int timelineButton1X = windowX * 2/11;
    private final int timelineButton2X = windowX * 2/11;
    private final int timelineX = windowX - timelineButton1X - timelineButton2X;
    private final int timelineY = windowY * 1/13;
    private final int timelineButton1Y = timelineY;
    private final int timelineButton2Y = timelineY;
    private final int buttonX = windowX * 1/10;
    private final int buttonY = windowY - timelineY;
    private final int gridX = windowX - buttonX;
    private final int gridY = windowY - timelineY;
    private final String f1 = "Func1";
    private final String f2 = "Func2";
    private final String f3 = "Func3";
    private final String f4 = "Func4";
    private final String f5 = "Func5";
    private final String f6 = "Func6";
    private final String f7 = "Func7";
    private final String f8 = "Func8";
    private final String f9 = "Func9";
    private final String f10 = "Func10";
    private final String f11 = "Func11";
    private final String f12 = "Func12";
    JPanel timelinePanel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            PSE prog = new PSE();
            prog.setVisible(true);
        });
    }

    public PSE() {
        initUI();
    }

    private void initUI() {
        // Main UI Window
        // -------------------------------------------------------------------------
        setTitle("PSE");
        setLayout(new FlowLayout());
        setSize(windowX+borderX, windowY+borderY);
        getContentPane().setBackground(Color.DARK_GRAY);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // TimeLine Button Panel 1
        // -------------------------------------------------------------------------
        JPanel timelineButtonPanel1 = new JPanel();
        timelineButtonPanel1.setPreferredSize(new Dimension(timelineButton1X, timelineButton1Y));
        timelineButtonPanel1.setLayout(new GridLayout(1, 2));
        timelineButtonPanel1.setBackground(Color.DARK_GRAY);
        add(timelineButtonPanel1);
        // Open Image
        JButton openButton = new JButton("Abrir");
        openButton.addActionListener((ActionEvent event) -> {
            setTitle("PSE - Abrir");
        });
        timelineButtonPanel1.add(openButton);
        // Process
        JButton processButton = new JButton("Processar");
        processButton.addActionListener((ActionEvent event) -> {
          new ProcessFunctionsWorker().execute();
        });
        timelineButtonPanel1.add(processButton);

        // TimeLine Panel
        // -------------------------------------------------------------------------
        timelinePanel = new JPanel();
        timelinePanel.setPreferredSize(new Dimension(timelineX, timelineY));
        timelinePanel.setLayout(new GridLayout(1, 10));
        timelinePanel.setBackground(Color.GRAY);
        add(timelinePanel);

        // TimeLine Button Panel 2
        // -------------------------------------------------------------------------
        JPanel timelineButtonPanel2 = new JPanel();
        timelineButtonPanel2.setPreferredSize(new Dimension(timelineButton2X, timelineButton2Y));
        timelineButtonPanel2.setLayout(new GridLayout(1, 2));
        timelineButtonPanel2.setBackground(Color.DARK_GRAY);
        add(timelineButtonPanel2);
        // Save Image
        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener((ActionEvent event) -> {
            setTitle("PSE - Guardar");
        });
        timelineButtonPanel2.add(saveButton);
        // Exit Image
        JButton exitButton = new JButton("Fechar");
        exitButton.addActionListener((ActionEvent event) -> {
            setTitle("PSE - Fechar");
            System.exit(0);
        });
        timelineButtonPanel2.add(exitButton);

        // Button Panel
        // -------------------------------------------------------------------------
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(buttonX, buttonY));
        buttonPanel.setLayout(new GridLayout(12, 1));
        buttonPanel.setBackground(Color.DARK_GRAY);
        add(buttonPanel);
        // Func1
        JButton f1Button = new JButton(f1);
        f1Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f1);
            addTimeline(f1);
        });
        buttonPanel.add(f1Button);
        // Func2
        JButton f2Button = new JButton(f2);
        f2Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f2);
            addTimeline(f2);
        });
        buttonPanel.add(f2Button);
        // Func3
        JButton f3Button = new JButton(f3);
        f3Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f3);
            addTimeline(f3);
        });
        buttonPanel.add(f3Button);
        // Func4
        JButton f4Button = new JButton(f4);
        f4Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f4);
            addTimeline(f4);
        });
        buttonPanel.add(f4Button);
        // Func5
        JButton f5Button = new JButton(f5);
        f5Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f5);
            addTimeline(f5);
        });
        buttonPanel.add(f5Button);
        // Func6
        JButton f6Button = new JButton(f6);
        f6Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f6);
            addTimeline(f6);
        });
        buttonPanel.add(f6Button);
        // Func7
        JButton f7Button = new JButton(f7);
        f7Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f7);
            addTimeline(f7);
        });
        buttonPanel.add(f7Button);
        // Func8
        JButton f8Button = new JButton(f8);
        f8Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f8);
            addTimeline(f8);
        });
        buttonPanel.add(f8Button);
        // Func9
        JButton f9Button = new JButton(f9);
        f9Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f9);
            addTimeline(f9);
        });
        buttonPanel.add(f9Button);
        // Func10
        JButton f10Button = new JButton(f10);
        f10Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f10);
            addTimeline(f10);
        });
        buttonPanel.add(f10Button);
        // Func11
        JButton f11Button = new JButton(f11);
        f11Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f11);
            addTimeline(f11);
        });
        buttonPanel.add(f11Button);
        // Func12
        JButton f12Button = new JButton(f12);
        f12Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f12);
            addTimeline(f12);
        });
        buttonPanel.add(f12Button);

        // Draw Panel
        // -------------------------------------------------------------------------
        JPanel drawPanel = new JPanel();
        drawPanel.setPreferredSize(new Dimension(gridX, gridY));
        drawPanel.setBackground(Color.LIGHT_GRAY);
        add(drawPanel);
    }

    private void addTimeline(String func) {
      JButton Button = new JButton(func);
      Button.addActionListener((ActionEvent event) -> {
          setTitle("PSE - " + func + " X");
          timelinePanel.remove(Button);
          timelinePanel.validate();
      });
      timelinePanel.add(Button);
      timelinePanel.validate();
    }
    
    public class ProcessFunctionsWorker extends SwingWorker<Integer, String> {
        @Override
        protected Integer doInBackground() throws Exception {
            setTitle("PSE - Processar");
            Component component[] = timelinePanel.getComponents();
            for (int i = 0; i < timelinePanel.getComponentCount(); i++) {
                JButton button = (JButton) component[i];
                Color defaultColor = button.getBackground();
                String defaultText = button.getText();
                int perc = 100 / timelinePanel.getComponentCount();
                button.setBackground(Color.YELLOW);
                for (int t = 1; t <= 10; t++) {
                    setTitle("PSE - Processamento - " + (i * perc + t * perc / 10) + "% - " + defaultText);
                    button.setText(defaultText + " " + t * 10 + "%");
                    button.validate();
                    try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (Exception e) {
                    }
                    button.validate();
                    timelinePanel.validate();
                    validate();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                } catch (Exception e) {
                }
                button.setText(defaultText);
                button.setBackground(defaultColor);
                button.validate();
                timelinePanel.validate();
                validate();
            }
            setTitle("PSE - Processamento - 100% - Completo");
            return 0;
        }
    }
}

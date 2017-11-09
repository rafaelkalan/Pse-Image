import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;



public class PSE extends JFrame {
    private final int borderX = 35;
    private final int borderY = 55;
    private int windowX = 1000;
    private int windowY = 650;
    private int timelineButton1X = 285;
    private int timelineButton2X = 285;
    private int timelineX = windowX - timelineButton1X - timelineButton2X;
    private int timelineY = 40;
    private int timelineButton1Y = timelineY;
    private int timelineButton2Y = timelineY;
    private int buttonX = 100;
    private int buttonY = windowY - timelineY;
    private int gridX = windowX - buttonX;
    private int gridY = windowY - timelineY;
    private JPanel timelinePanel;
    private JPanel timelineButtonPanel1;
    private JPanel timelineButtonPanel2;
    private JPanel buttonPanel;
    private JPanel drawPanel;
    private BufferedImage originalImage;
    private BufferedImage mainImage;
    private ArrayList<BufferedImage> imageHistory;
    private JLabel mainImageLabel;
    private Boolean mustProcess = true;
    // Nomes das funções
    private final String f1 = "Cinza";
    private final String f2 = "Negativo";
    private final String f3 = "Media";
    private final String f4 = "Gaussiano";
    private final String f5 = "Laplaciano";
    private final String f6 = "Sobel";
    private final String f7 = "Convolução";
    private final String f8 = "Brilho";
    private final String f9 = "Contraste";
    private final String f10 = "-";
    private final String f11 = "-";
    private final String f12 = "-";
    private final String f99 = "Resetar";
    // Argumentos das funções que precisam deles
    private int convolucaoLinhas = 3;
    private int convolucaoColunas = 3;
    private ArrayList<Integer> convolucaoPesos = new ArrayList<Integer>(Arrays.asList(1,1,1,1,1,1,1,1,1));
    private int brilhoFloat = 0;
    private int contrasteFloat = 0;

    
    
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
        // Resizing
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                Component component = (Component) evt.getSource();
                Dimension size = component.getBounds().getSize();
                windowX = (int)Math.round(size.getWidth()) - borderX;
                windowY = (int)Math.round(size.getHeight()) - borderY;
                //timelineButton1X = 180;
                //timelineButton2X = 180;
                timelineX = windowX - timelineButton1X - timelineButton2X;
                //timelineY = 50;
                timelineButton1Y = timelineY;
                timelineButton2Y = timelineY;
                //buttonX = 125;
                buttonY = windowY - timelineY;
                gridX = windowX - buttonX;
                gridY = windowY - timelineY;
                if (timelinePanel != null) {
                    timelinePanel.setPreferredSize(new Dimension(timelineX, timelineY));
                    timelinePanel.setSize(new Dimension(timelineX, timelineY));
                    timelinePanel.setMinimumSize(new Dimension(timelineX, timelineY));
                    timelinePanel.setMaximumSize(new Dimension(timelineX, timelineY));
                    timelinePanel.repaint();
                    timelinePanel.validate();
                }
                if (timelineButtonPanel1 != null) {
                    timelineButtonPanel1.setPreferredSize(new Dimension(timelineButton1X, timelineButton1Y));
                    timelineButtonPanel1.setSize(new Dimension(timelineButton1X, timelineButton1Y));
                    timelineButtonPanel1.setMinimumSize(new Dimension(timelineButton1X, timelineButton1Y));
                    timelineButtonPanel1.setMaximumSize(new Dimension(timelineButton1X, timelineButton1Y));
                    timelineButtonPanel1.repaint();
                    timelineButtonPanel1.validate();
                }
                if (timelineButtonPanel2 != null) {
                    timelineButtonPanel2.setPreferredSize(new Dimension(timelineButton2X, timelineButton2Y));
                    timelineButtonPanel2.setSize(new Dimension(timelineButton2X, timelineButton2Y));
                    timelineButtonPanel2.setMinimumSize(new Dimension(timelineButton2X, timelineButton2Y));
                    timelineButtonPanel2.setMaximumSize(new Dimension(timelineButton2X, timelineButton2Y));
                    timelineButtonPanel2.repaint();
                    timelineButtonPanel2.validate();
                }
                if (buttonPanel != null) {
                    buttonPanel.setPreferredSize(new Dimension(buttonX, buttonY));
                    buttonPanel.setSize(new Dimension(buttonX, buttonY));
                    buttonPanel.setMinimumSize(new Dimension(buttonX, buttonY));
                    buttonPanel.setMaximumSize(new Dimension(buttonX, buttonY));
                    buttonPanel.repaint();
                    buttonPanel.validate();
                }
                if (drawPanel != null) {
                    drawPanel.setPreferredSize(new Dimension(gridX, gridY));
                    drawPanel.setSize(new Dimension(gridX, gridY));
                    drawPanel.setMinimumSize(new Dimension(gridX, gridY));
                    drawPanel.setMaximumSize(new Dimension(gridX, gridY));
                    drawPanel.repaint();
                    drawPanel.validate();
                }
                component.repaint();
                component.validate();
                showImage();
        }
    }
    );

        // TimeLine Button Panel 1
        // -------------------------------------------------------------------------
        timelineButtonPanel1 = new JPanel();
        timelineButtonPanel1.setPreferredSize(new Dimension(timelineButton1X, timelineButton1Y));
        timelineButtonPanel1.setLayout(new GridLayout(1, 2));
        timelineButtonPanel1.setBackground(Color.DARK_GRAY);
        add(timelineButtonPanel1);
        // Open Image
        JButton openButton = new JButton("Abrir");
        openButton.addActionListener((ActionEvent event) -> {
            setTitle("PSE - Abrir");
            openImage();
        });
        openButton.setBackground(Color.WHITE);
        timelineButtonPanel1.add(openButton);
        // Process
        JButton processButton = new JButton("Processar");
        processButton.addActionListener((ActionEvent event) -> {
            if (mainImage != null) new ProcessFunctionsWorker().execute();
        });
        processButton.setBackground(Color.WHITE);
        timelineButtonPanel1.add(processButton);
        // View original image
        JButton originalButton = new JButton("Original");
        originalButton.addActionListener((ActionEvent event) -> {
            if (originalImage != null) {
                setTitle("PSE - Visualizando: Imagem original");
                mainImage = originalImage;
                showImage();
            }
        });
        timelineButtonPanel1.add(originalButton);

        // TimeLine Panel
        // -------------------------------------------------------------------------
        timelinePanel = new JPanel();
        timelinePanel.setPreferredSize(new Dimension(timelineX, timelineY));
        timelinePanel.setLayout(new GridLayout(1, 10));
        timelinePanel.setBackground(Color.GRAY);
        add(timelinePanel);

        // TimeLine Button Panel 2
        // -------------------------------------------------------------------------
        timelineButtonPanel2 = new JPanel();
        timelineButtonPanel2.setPreferredSize(new Dimension(timelineButton2X, timelineButton2Y));
        timelineButtonPanel2.setLayout(new GridLayout(1, 2));
        timelineButtonPanel2.setBackground(Color.DARK_GRAY);
        add(timelineButtonPanel2);
        // View result image
        JButton resultButton = new JButton("Resultado");
        resultButton.addActionListener((ActionEvent event) -> {
            if (mainImage != null) {
                if (mustProcess) {
                    new ProcessFunctionsWorker().execute();
                    while (mustProcess != false) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(1000);
                        } catch (Exception e) {}
                    }
                }
                setTitle("PSE - Visualizando: Imagem resultado");
                mainImage = imageHistory.get(imageHistory.size()-1);
                showImage();
            }
        });
        timelineButtonPanel2.add(resultButton);
        // Save Image
        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener((ActionEvent event) -> {
            setTitle("PSE - Guardar");
            saveImage();
        });
        saveButton.setBackground(Color.WHITE);
        timelineButtonPanel2.add(saveButton);
        // Exit Image
        JButton exitButton = new JButton("Fechar");
        exitButton.addActionListener((ActionEvent event) -> {
            setTitle("PSE - Fechar");
            System.exit(0);
        });
        exitButton.setBackground(Color.WHITE);
        timelineButtonPanel2.add(exitButton);

        // Button Panel
        // -------------------------------------------------------------------------
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(buttonX, buttonY));
        buttonPanel.setLayout(new GridLayout(13, 1));
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
        // Func7 (Convolução)
        JButton f7Button = new JButton(f7);
        f7Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f7);
            addTimeline(f7);
        });
        f7Button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent click) {
                if (SwingUtilities.isRightMouseButton(click)) {
                    JTextField xField = new JTextField(3);
                    JTextField yField = new JTextField(3);
                    JTextField weightField = new JTextField(9);

                    JPanel parameters = new JPanel();
                    parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
                    JPanel p1 = new JPanel();
                    p1.add(new JLabel("Número de linhas da máscara:"));
                    p1.add(xField);
                    p1.add(Box.createHorizontalStrut(15)); // a spacer
                    p1.add(new JLabel("Número de colunas da máscara:"));
                    p1.add(yField);
                    parameters.add(p1);
                    parameters.add(new JLabel("Pesos da máscara separados por vírgula:"));
                    parameters.add(weightField);
 
                    int result = JOptionPane.showConfirmDialog(null, parameters,
                            "Parâmetros do filtro de convolução", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) { 
                        try {
                            int tempLinhas = Integer.parseInt(xField.getText());
                            int tempColunas = Integer.parseInt(yField.getText());
                            if (tempLinhas%2 != 1 || tempColunas%2 != 1) {
                                JOptionPane.showMessageDialog(new JFrame(), "Número de linhas e colunas deve ser ímpar!");
                            }
                            ArrayList tempPesos = new ArrayList();
                            String stringPesos[] = weightField.getText().split(",");
                            if (stringPesos.length != tempLinhas * tempColunas) {
                            JOptionPane.showMessageDialog(new JFrame(), "Número de pesos não esta de acordo com número de linhas e colunas!");
                            }
                            convolucaoLinhas = tempLinhas;
                            convolucaoColunas = tempColunas;
                            for (int i=0; i<stringPesos.length; i++) {
                                convolucaoPesos.add(Integer.parseInt(stringPesos[i]));
                            }
                        } catch(Exception e) {
                            JOptionPane.showMessageDialog(new JFrame(), "Parâmetros inválidos!");
                        }
                    }
                }
            }
        });
        buttonPanel.add(f7Button);
        // Func8 (Brilho)
        JButton f8Button = new JButton(f8);
        f8Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f8);
            addTimeline(f8);
        });
        f8Button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent click) {
                if (SwingUtilities.isRightMouseButton(click)) {
                    JTextField xField = new JTextField(3);

                    JPanel parameters = new JPanel();
                    parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
                    parameters.add(new JLabel("Brilho(%):"));
                    parameters.add(xField);
 
                    int result = JOptionPane.showConfirmDialog(null, parameters,
                            "Parâmetros do filtro de brilho", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) { 
                        try {
                            int tempBrilho = Integer.parseInt(xField.getText());
                            brilhoFloat = tempBrilho;
                            System.out.println(brilhoFloat);
                        } catch(Exception e) {
                            JOptionPane.showMessageDialog(new JFrame(), "Parâmetros inválidos!");
                        }
                    }
                }
            }
        });
        buttonPanel.add(f8Button);
        // Func9 (Contraste)
        JButton f9Button = new JButton(f9);
        f9Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE - " + f9);
            addTimeline(f9);
        });
        f9Button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent click) {
                if (SwingUtilities.isRightMouseButton(click)) {
                    JTextField xField = new JTextField(3);

                    JPanel parameters = new JPanel();
                    parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
                    parameters.add(new JLabel("Contraste(%):"));
                    parameters.add(xField);
 
                    int result = JOptionPane.showConfirmDialog(null, parameters,
                            "Parâmetros do filtro de contraste", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) { 
                        try {
                            int tempContraste = Integer.parseInt(xField.getText());
                            contrasteFloat = tempContraste;
                            System.out.println(contrasteFloat);
                        } catch(Exception e) {
                            JOptionPane.showMessageDialog(new JFrame(), "Parâmetros inválidos!");
                        }
                    }
                }
            }
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
        // Func99
        JButton f99Button = new JButton(f99);
        f99Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE");
            resetTimeline();
        });
        f99Button.setBackground(Color.WHITE);
        buttonPanel.add(f99Button);

        // Draw Panel
        // -------------------------------------------------------------------------
        drawPanel = new JPanel();
        drawPanel.setPreferredSize(new Dimension(gridX, gridY));
        drawPanel.setBackground(Color.WHITE);
        add(drawPanel);
    }
    
    private void openImage() {
        JFileChooser imageChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Imagens PNG ou JPG", "png", "jpg");
        imageChooser.setFileFilter(filter);
        int returnVal = imageChooser.showOpenDialog(drawPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
//            System.out.println("You chose to open this file: "
//                    + imageChooser.getSelectedFile().getName());
            try {
                mainImage = ImageIO.read(imageChooser.getSelectedFile());
                originalImage = mainImage;
                showImage();
            } catch (IOException e) {
            }
        }
    }
    
    private void showImage() {
        if (mainImage != null) {
            if (mainImageLabel != null) {
                drawPanel.remove(mainImageLabel);
            }
            float drawWidth = (float)drawPanel.getSize().getWidth();
            float drawHeight = (float)drawPanel.getSize().getHeight();
            float imgWidth = mainImage.getWidth();
            float imgHeight = mainImage.getHeight();
            float imgRatio = imgWidth / imgHeight;
            float drawRatio = drawWidth / drawHeight;
            float imgWidthNew = imgWidth;
            float imgHeightNew = imgHeight;
            if (imgRatio > drawRatio) {
                imgWidthNew = drawWidth;
                imgHeightNew = imgHeight*(drawWidth/imgWidth);
            }
            else if (imgRatio < drawRatio) {
                imgWidthNew = imgWidth *(drawHeight/imgHeight);
                imgHeightNew = drawHeight;
            }
            else {
                imgWidth = drawWidth;
                imgHeight = drawHeight;
            }
            Image tempImage = mainImage.getScaledInstance(Math.round(imgWidthNew), Math.round(imgHeightNew), Image.SCALE_SMOOTH);
            mainImageLabel = new JLabel(new ImageIcon(tempImage));
            drawPanel.add(mainImageLabel, BorderLayout.CENTER);
            drawPanel.repaint();
            drawPanel.validate();
        }
    }

    private void saveImage() {
        
    }

    private void addTimeline(String func) {
      JButton Button = new JButton(func);
//      Button.addActionListener((ActionEvent event) -> {
//          setTitle("PSE - " + func + " X");
//          timelinePanel.remove(Button);
//          timelinePanel.validate();
//      });
      mustProcess = true;
      Button.addActionListener((ActionEvent event) -> {
          JButton button = (JButton)event.getSource();
          if (mustProcess) {
              new ProcessFunctionsWorker().execute();
              while (mustProcess != false) try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                } catch (Exception e) {}
          }
          setTitle("PSE - Visualizando: " + func);
          Color defaultColor = button.getBackground();
          Component component[] = timelinePanel.getComponents();
            for (int i = 0; i < timelinePanel.getComponentCount(); i++) {
                JButton testButton = (JButton) component[i];
                if (testButton == button) {
                    mainImage = imageHistory.get(i);
                    showImage();
                }
            }               
      });
      timelinePanel.add(Button);
      timelinePanel.validate();
    }
    
    private void resetTimeline() {
        if (mainImage != null) {
            mainImage = originalImage;
            showImage();
        }
        Component component[] = timelinePanel.getComponents();
        for (int i = timelinePanel.getComponentCount()-1; i>=0; i--) {
            JButton button = (JButton)component[i];
            timelinePanel.remove(button);
        }
        mustProcess = true;
        timelinePanel.repaint();
        timelinePanel.validate();
    }
    
    public class ProcessFunctionsWorker extends SwingWorker<Integer, String> {
        @Override
        protected Integer doInBackground() throws Exception {
            setTitle("PSE - Processar");
            mainImage = originalImage;
            imageHistory = new ArrayList<BufferedImage>();
            showImage();
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
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (Exception e) {}
                    button.validate();
                    timelinePanel.validate();
                    validate();
                }
                if (mainImage != null) {
                    functionChooser(defaultText);
                    imageHistory.add(mainImage);
                    showImage();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (Exception e) {}
                button.setText(defaultText);
                button.setBackground(defaultColor);
                button.validate();
                timelinePanel.validate();
                validate();
            }
            setTitle("PSE - Processamento - 100% - Completo");
            mustProcess = false;
            return 0;
        }
        
        private boolean functionChooser(String name) {
                 if (name.equals(f1)) {
                mainImage = EscalaDeCinza(mainImage);
            }
            else if (name.equals(f2)) {
                mainImage = Negativo(mainImage);
            }
            else if (name.equals(f3)) {
                mainImage = Media(mainImage);
            }
            else if (name.equals(f4)) {
                mainImage = Gaussiano(mainImage);
            }
            else if (name.equals(f5)) {
                mainImage = Laplaciano(mainImage);
            }
            else if (name.equals(f6)) {
                mainImage = Sobel(mainImage);
            }
            else if (name.equals(f7)) {
                mainImage = Convolucao(mainImage, convolucaoLinhas, convolucaoColunas, convolucaoPesos);
            } 
            else if (name.equals(f8)) {
                mainImage = Brilho(mainImage, brilhoFloat);
            } 
            else if (name.equals(f9)) {
                mainImage = Contraste(mainImage, contrasteFloat);
            }
            return true;
        }
    }
    
// ------------------------------------- INSERIR ALGORITMOS ABAIXO --------------------------------------------
// ------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------
// ------------------------------------------------------------------------------------------------------------
    
    public static BufferedImage EscalaDeCinza(BufferedImage imagem) {
        //Imagem resultante
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
        
        //pegar coluna e linha da imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();

        int media = 0;
        //laço para varrer a matriz de pixels da imagem
        for (int i = 0; i < coluna; i++) {
            for (int j = 0; j < linha; j++) {               
                //rgb recebe o valor RGB do pixel em questão                
                int rgb = imagem.getRGB(i, j);              
                int r = (int)((rgb&0x00FF0000)>>>16); //R
                int g = (int)((rgb&0x0000FF00)>>>8);  //G
                int b = (int) (rgb&0x000000FF);       //B

                //media dos valores do RGB
                //será o valor do pixel na nova imagem
                media = (r + g + b) / 3;

                //criar uma instância de Color
                Color color = new Color(media, media, media);
                //setar o valor do pixel com a nova cor
                ResultImage.setRGB(i, j, color.getRGB());
            }
        }
        return ResultImage;
    }
    
    public static BufferedImage Negativo(BufferedImage imagem) {
        //Imagem resultante
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
        
        //pegar coluna e linha da imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();
        //laço para varrer a matriz de pixels da imagem
        for (int i = 0; i < coluna; i++) {
            for (int j = 0; j < linha; j++) {  
                //rgb recebe o valor RGB do pixel em questão 
                int rgb = imagem.getRGB(i, j);               
                //a cor inversa é dado por 255 menos o valor da cor                 
                int r = 255 - (int)((rgb&0x00FF0000)>>>16);
                int g = 255 - (int)((rgb&0x0000FF00)>>>8);
                int b = 255 - (int) (rgb&0x000000FF);
                Color color = new Color(r, g, b);
                ResultImage.setRGB(i, j, color.getRGB());
            }
        }
        return ResultImage;
    }
    
    public static BufferedImage Media (BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
        
        //mascara de média
        int [][]mascaraMedia = {{1,1,1},
                                {1,1,1},
                                {1,1,1}};
        //soma dos valores da máscara
        int valorMascara = 9;
        
        //cores primarias
        int r = 0, g = 0, b = 0;
        
        //pegar coluna e linha da imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();
        
        //percorre a imagem
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                //percorre a máscara
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        //rgb = rgb do pixel
                        int rgb = imagem.getRGB(j + k, i + l);
                        //pegando os valores das cores primarias de cada pixel após a convolucao com a máscara
                        r += (mascaraMedia[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraMedia[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraMedia[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }
                }
                //dividia as cores pelo valor da máscara
                r = r / valorMascara;
                g = g / valorMascara;
                b = b / valorMascara;
                //nova cor do pixel
                Color tempColor = new Color(r, g, b);
                //setar o respectivel pixel na nova imagem
                ResultImage.setRGB(j, i, tempColor.getRGB());
                //zerar valor das cores primarias
                r = g = b = 0;
            }
        }
        ResultImage.getSubimage(1, 1, coluna-1, linha-1);
        return ResultImage;
    }
    
    public static BufferedImage Gaussiano (BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
        //mascara
        int [][]mascaraGaussiano = {{1,2,1},
                                {2,4,2},
                                {1,2,1}};
        int valorMascara = 16;

        int r = 0, g = 0, b = 0;
        //tamanho imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();
        //percorre imagem
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                //percorre mascara
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        //rgb
                        int rgb = imagem.getRGB(j + k, i + l);
                        //pegando os valores das cores primarias de cada pixel após a convolucao com a máscara
                        r += (mascaraGaussiano[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraGaussiano[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraGaussiano[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }

                }
                //dividindo as cores pelo valor da máscara
                r = r / valorMascara;
                g = g / valorMascara;
                b = b / valorMascara;
                Color tempColor = new Color(r, g, b);
                //setar novo valor do pixel na imagem resultante
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        ResultImage.getSubimage(1, 1, coluna-1, linha-1);
        return ResultImage;
    }

    public static BufferedImage Laplaciano (BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
        //mascaras
        int [][]mascaraL1 = {{0,-1,0},
                            {-1,4,-1},
                            {0,-1,0}};
        
        int [][]mascaraL2 = {{1,1,1},
                            {1,-8,1},
                            {1,1,1}};

        int r = 0, g = 0, b = 0;
        //tamanho imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();
        //percorre imagem
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                //percorre mascara
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        //rgb
                        int rgb = imagem.getRGB(j + k, i + l);
                        //pegando os valores das cores primarias de cada pixel após a convolucao com a máscara
                        r += (mascaraL1[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraL1[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraL1[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }

                }
                //arredondamento de valores
                Color tempColor = new Color(Math.min(255, Math.max(0, r)), Math.min(255, Math.max(0, g)), Math.min(255, Math.max(0, b)));
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        //percorre imagem
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
            for (int j = 1; j + 1 < imagem.getWidth(); j++) {
                //percorre mascara
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        //rgb
                        int rgb = imagem.getRGB(j + k, i + l);
                        //pegando os valores das cores primarias de cada pixel após a convolucao com a máscara
                        r += (mascaraL2[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraL2[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraL2[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }

                }
                //arredondamento de valores
                Color tempColor = new Color(Math.min(255, Math.max(0, r)), Math.min(255, Math.max(0, g)), Math.min(255, Math.max(0, b)));
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        ResultImage.getSubimage(1, 1, coluna-1, linha-1);
        return ResultImage;
    }
    
    public static BufferedImage Sobel (BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
        //mascaras
        int [][]mascaraS1 = {{-1,-2,-1},
                             {0,0,0},
                             {1,2,1}};
        int [][]mascaraS2 = {{-1,0,1},
                             {-2,0,2},
                             {-1,0,1}};

        int r = 0, g = 0, b = 0;
        //tamanho da imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();
        
        //percorre imagem
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                //percorre mascara
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        //rgb
                        int rgb = imagem.getRGB(j + k, i + l);
                        //pegando os valores das cores primarias de cada pixel após a convolucao com a máscara
                        r += (mascaraS1[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraS1[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraS1[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }

                }
                //arredondamento de valores
                Color tempColor = new Color(Math.min(255, Math.max(0, r)), Math.min(255, Math.max(0, g)), Math.min(255, Math.max(0, b)));
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        //percorrer imagem
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
            for (int j = 1; j + 1 < imagem.getWidth(); j++) {
                //Percorrer máscara
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        //RGB
                        int rgb = imagem.getRGB(j + k, i + l);
                        //pegando os valores das cores primarias de cada pixel após a convolucao com a máscara
                        r += (mascaraS2[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraS2[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraS2[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }

                }
                //Arredondamento dos valores
                Color tempColor = new Color(Math.min(255, Math.max(0, r)), Math.min(255, Math.max(0, g)), Math.min(255, Math.max(0, b)));
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        ResultImage.getSubimage(1, 1, coluna-1, linha-1);
        return ResultImage;
    }
    
    public static BufferedImage Convolucao (BufferedImage imagem,int linhas, int colunas, java.util.List <Integer> pesos) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
        if (linhas != colunas) {
            System.out.println("ERRO: A matriz deve ser quadrada e ímpar");
            return null;
        }else if (linhas % 2 == 0) {
            System.out.println("ERRO: A matriz deve ser quadrada e ímpar");
            return null;
        }else {
            //mascara de média
            int [][]mascaraConvolucao = new int [linhas][colunas];
            int ponteiro = 0;
            for (int i = 0; i < linhas; i++){
                for (int j = 0; j < colunas; j++) {
                    mascaraConvolucao[i][j] = pesos.get(ponteiro);
                    ponteiro++;
                }
            }
            //soma dos valores da máscara
            int valorMascara = 0;
            for (int i = 0; i < mascaraConvolucao.length; i++) {
                for (int j = 0; j < mascaraConvolucao[i].length; j++) {
                    valorMascara += mascaraConvolucao[i][j];   
                } 
            }

            //cores primarias
            int r = 0, g = 0, b = 0;

            //pegar coluna e linha da imagem
            int coluna = imagem.getWidth();
            int linha = imagem.getHeight();
            int inicial = (int)Math.floor(linhas/2);

            //percorre a imagem
            for (int i = inicial; i + inicial < linha; i++) {
                for (int j = inicial; j + inicial < coluna; j++) {
                    //percorre a máscara
                    for (int l = -inicial; l <= inicial; l++) {
                        for (int k = -inicial; k <= inicial; k++) {
                            //rgb = rgb do pixel
                            int rgb = imagem.getRGB(j + k, i + l);
                            //pegando os valores das cores primarias de cada pixel após a convolucao com a máscara
                            r += (mascaraConvolucao[inicial + l][inicial + k] * (int)((rgb&0x00FF0000)>>>16));
                            g += (mascaraConvolucao[inicial + l][inicial + k] * (int)((rgb&0x0000FF00)>>>8));
                            b += (mascaraConvolucao[inicial + l][inicial + k] * (int)((rgb&0x000000FF)));
                        }
                    }
                    //dividia as cores pelo valor da máscara
                    r = r / valorMascara;
                    g = g / valorMascara;
                    b = b / valorMascara;
                    //nova cor do pixel
                    Color tempColor = new Color(r, g, b);
                    //setar o respectivel pixel na nova imagem
                    ResultImage.setRGB(j, i, tempColor.getRGB());
                    //zerar valor das cores primarias
                    r = g = b = 0;
                }
            }
            ResultImage.getSubimage(inicial, inicial, coluna-inicial, linha-inicial);
        }
        return ResultImage;
    }
    
    public static BufferedImage Brilho (BufferedImage imagem, int x) {
        //imagem resultante
        float f;
        if (x < 0) {
            int tempx = 1 - (x/100);
            String temp = 0 + "." + tempx;
            f = Float.parseFloat(temp);
        }else if (x > 0 && x < 100){
            String temp = 1 + "."+(x/10)+"f";
            f = Float.parseFloat(temp); 
        } else if (x == 100) {
            f = 2;
        }else {
            String temp = x+"f";
            f = Float.parseFloat(temp);
        }
        
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);

        RescaleOp rescaleOp = new RescaleOp(f, 0, null);
        rescaleOp.filter(imagem, ResultImage); 
        
        return ResultImage;
    }
    
    public static BufferedImage Contraste (BufferedImage imagem, int x) {
        String temp = x+"f";
        float f = Float.parseFloat(temp);
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);

        RescaleOp rescaleOp = new RescaleOp(1.0f, x, null);
        rescaleOp.filter(imagem, ResultImage); 
        
        return ResultImage;
    }
}
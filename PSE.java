import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import java.util.concurrent.TimeUnit;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;



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
    private final int buttonX = windowX * 1/8;
    private final int buttonY = windowY - timelineY;
    private final int gridX = windowX - buttonX;
    private final int gridY = windowY - timelineY;
    private final String f1 = "Escala Cinza";
    private final String f2 = "Negativo";
    private final String f3 = "Media";
    private final String f4 = "Gaussiano";
    private final String f5 = "Laplaciano";
    private final String f6 = "Sobel";
    private final String f7 = "Func7";
    private final String f8 = "Func8";
    private final String f9 = "Func9";
    private final String f10 = "Func10";
    private final String f11 = "Func11";
    private final String f12 = "Func12";
    private JPanel timelinePanel;
    private JPanel drawPanel;
    private BufferedImage mainImage;
    private JLabel mainImageLabel;

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
            openImage();
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
            saveImage();
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
        drawPanel = new JPanel();
        drawPanel.setPreferredSize(new Dimension(gridX, gridY));
        drawPanel.setBackground(Color.LIGHT_GRAY);
        add(drawPanel);
    }
    
    private void openImage() {
        JFileChooser imageChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Imagens PNG ou JPG", "png", "jpg");
        imageChooser.setFileFilter(filter);
        int returnVal = imageChooser.showOpenDialog(drawPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: "
                    + imageChooser.getSelectedFile().getName());
            try {
                mainImage = ImageIO.read(imageChooser.getSelectedFile());
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
            mainImageLabel = new JLabel(new ImageIcon(mainImage));
            drawPanel.add(mainImageLabel);
            drawPanel.repaint();
            drawPanel.validate();
        }
    }

    private void saveImage() {
        
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
                functionChooser(defaultText);
                showImage();
                button.setText(defaultText);
                button.setBackground(defaultColor);
                button.validate();
                timelinePanel.validate();
                validate();
            }
            setTitle("PSE - Processamento - 100% - Completo");
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
}

import java.awt.*;
import java.awt.BasicStroke;
import java.awt.geom.Line2D;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.Vector;
import java.util.Collections;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.ToolTipManager;
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
    private JFrame histogramFrame;
    private JPanel histogramPanel;
    private BufferedImage originalImage;
    private BufferedImage mainImage;
    private BufferedImage secondImage;
    private ArrayList<BufferedImage> imageHistory;
    private JLabel mainImageLabel;
    private JLabel secondImageLabel;
    private JLabel histogramLabel;
    private Boolean mustProcess = true;
    private int lastProcessed = 0;

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
    private final String f10 = "Limiar";
    private final String f11 = "Cor";
    private final String f12 = "Interpolar";
    private final String f13 = "Linhas";
    private final String f14 = "EMQ";
    private final String f15 = "Histograma";
    private final String f16 = "Linha";
    private final String f17 = "Mediana";
    private final String f18 = "Moda";
    private final String f19 = "Mínimo";
    private final String f20 = "Máximo";
    private final String f98 = "Tam. Original";
    private final String f99 = "Resetar";

    // Descrições para os botões
    private final String opentip = "Clique para abrir uma imagem.";
    private final String openSecondtip = "Clique para abrir uma segunda imagem.";
    private final String processtip = "Clique para processar a imagem seguindo a ordem definida no timeline (à direita).";
    private final String originaltip = "Clique para mudar a visualização para a imagem original.";
    private final String resulttip = "Clique para mudar a visualização para a imagem resultada do processamento no timeline (à esquerda).";
    private final String savetip = "Clique para salvar a imagem atualmente sendo visualizada.";
    private final String quittip = "Clique para fechar o programa. (Não salva a imagem!).";
    private final String resettip = "Clique para resetar a imagem de volta à original e resetar o timeline.";
    private final String sizetip = "Mostrar tamanho original:<br>(*Clique para ligar/desligar visualização da imagem em seu tamanho original*)";
    private final String timelinetip = "Clique esquerdo para visualizar esta etapa.<br>Clique direito para remover esta etapa.";
    private final String f1tip = "Escala de Cinza:<br><br>Transforma a imagem para tons de cinza.<br><br>Geralmente usada para preparar a imagem para outros filtros / transformações.";
    private final String f2tip = "Filtro Negativo:<br><br>Inverte todos os tons da imágem.<br><br>Geralmente usado para transformar uma imagem obtida em sua forma negativa para a sua positiva (imagem normal).";
    private final String f3tip = "Filtro de Média:<br><br>Percorre a imagem substituindo cada pixel pela média de seus vizinhos.<br><br>Geralmente usado para pre-processar a imagem, removendo ruído, para melhorar o resultado de processamentos subsequentes.";
    private final String f4tip = "Filtro Gaussiano:<br><br>Percorre a imagem aplicando um efeito \"borrado\".<br><br>Geralmente usado para pre-processar a imagem, removendo ruído, para melhorar o resultado de processamentos subsequentes.";
    private final String f5tip = "Operador de Laplace:<br><br>Percorre a imagem calculando a divergěncia de gradientes, identificando áreas de mudança rápida (bordas).<br><br>Geralmente usado para detecção de bordas, usualmente após operações que reduzem ruído / suavizam a imagem.";
    private final String f6tip = "Operador de Sobel:<br><br>Percorre a imagem calculando as normais dos gradientes, identificando potenciais bordas.<br><br>Geralmente usado para detecção de bordas, usualmente após operações que reduzem ruído / suavizam a imagem. ";
    private final String f7tip = "Filtro de Convolução:<br><br>Percorre a imagem substituindo cada pixel pela média ponderada de seus vizinhos a partir de uma matriz de convolução.<br><br>Filtro de propósito geral usado quando se quer um maior controle no processamento da imagem.";
    private final String f8tip = "Filtro de Brilho:<br><br>Percorre a imagem aumentando ou reduzindo o brilho de cada pixel.<br><br>Geralmente usado para corrigir uma imagem que está muito clara ou escura, dificultando o seu processamento.";
    private final String f9tip = "Filtro de Contraste:<br><br>Percorre a imagem aumentando ou reduzindo o contraste.<br><br>Geralmente usado para corrigir uma imagem que esta muito suave ou ruidosa.";
    private final String f10tip = "Limiar Global Padrão:<br><br>Percorre a imagem para verificar a média do valor de intensidade dos pixels, e usa essa média para gerar uma nova imagem binária repartindo o pixels por esse valor. O limiar pode ser configurado para usar um valor fornecido, ao inves do valor da média.";
    private final String f11tip = "Filtro de Cor:<br><br>Percorre a imagem verificando cada pixel, gerando uma imagem binária a partir daqueles que estiverem dentro do escopo de cor permitido.<br><br>Geralmente usado quando é fácil retirar da imagem a parte desejada pela sua cor distinta.";
    private final String f12tip = "Interpolação:<br><br>Percorre a imagem e interpola os pixels para gerar uma nova imagem gerada a partir do fator de escala definido na configuração. <br><br> Geralmente usado para escalar uma imagem quando necessário.";
    private final String f13tip = "Hough Linha:<br><br>";
    private final String f14tip = "Erro Médio Quadrático:<br>(*Clique para calcular o EMQ da imagem atualmente sendo visualizada*)";
    private final String f15tip = "Histograma:<br>(*Clique para ligar/desligar visualização do Histograma*)";
    private final String f16tip = "Hough Linha:<br>(*Clique para calcular gerar a detecção de linhas pela transformada de Hough*)<br>";
    private final String f17tip = "Filtro de mediana<br>";
    private final String f18tip = "Filtro de moda<br>";
    private final String f19tip = "Filtro de mínimo<br>";
    private final String f20tip = "Filtro de máximo<br>";

    // Argumentos das funções que precisam deles
    private int convolucaoLinhas = 3;
    private int convolucaoColunas = 3;
    private ArrayList<Integer> convolucaoPesos = new ArrayList<Integer>(Arrays.asList(1, 1, 1, 1, 1, 1, 1, 1, 1));
    private int brilhoFloat = 0;
    private int contrasteFloat = 0;
    private int[] filtroRGB = {0, 0, 0, 255, 255, 255};
    private double limiarDouble = -1;
    private double interpolacaoFator = 1.0;
    private Boolean histogramOn = false;
    private Boolean scaleOff = false;

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
        setTitle("PSE Image");
        setLayout(new FlowLayout());
        setSize(windowX + borderX, windowY + borderY);
        getContentPane().setBackground(Color.DARK_GRAY);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Resizing
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                Component component = (Component) evt.getSource();
                Dimension size = component.getBounds().getSize();
                windowX = (int) Math.round(size.getWidth()) - borderX;
                windowY = (int) Math.round(size.getHeight()) - borderY;
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

        // Histogram Frame & Panel
        histogramFrame = new JFrame(f15);
        histogramFrame.setLayout(new FlowLayout());
        histogramFrame.setSize(new Dimension(310, 325));
        histogramFrame.getContentPane().setBackground(Color.DARK_GRAY);
        histogramFrame.setLocationRelativeTo(null);
        histogramFrame.setAlwaysOnTop(true);
        histogramFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - histogramFrame.getWidth();
        int y = (int) rect.getMaxY() - histogramFrame.getHeight();
        histogramFrame.setLocation(x, y);
        histogramPanel = new JPanel();
        histogramPanel.setPreferredSize(new Dimension(300, 300));
        histogramFrame.add(histogramPanel);

        // TimeLine Button Panel 1
        // -------------------------------------------------------------------------
        timelineButtonPanel1 = new JPanel();
        timelineButtonPanel1.setPreferredSize(new Dimension(timelineButton1X, timelineButton1Y));
        timelineButtonPanel1.setLayout(new GridLayout(1, 2));
        timelineButtonPanel1.setBackground(Color.GRAY);
        add(timelineButtonPanel1);

        // Open Image
        JButton openButton = new JButton("Abrir");
        openButton.setToolTipText("<html><p width=\"300\">" + opentip + "</p></html>");
        openButton.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - Abrir");
            openImage();
        });
        openButton.setBackground(Color.WHITE);
        timelineButtonPanel1.add(openButton);

         // Open Second Image
         JButton openSecondButton = new JButton("2ª");
         openSecondButton.setToolTipText("<html><p width=\"300\">" + openSecondtip + "</p></html>");
         openSecondButton.addActionListener((ActionEvent event) -> {
             setTitle("PSE Image - Abrir");
             openSecondImage();
         });
         openSecondButton.setBackground(Color.WHITE);
         timelineButtonPanel1.add(openSecondButton);
        
//        
        // Process
        JButton processButton = new JButton("Processar");
        processButton.setToolTipText("<html><p width=\"300\">" + processtip + "</p></html>");
        processButton.addActionListener((ActionEvent event) -> {
            if (mainImage != null) {
                new ProcessFunctionsWorker().execute();
            }
        });
        processButton.setBackground(Color.WHITE);
        timelineButtonPanel1.add(processButton);
        
        // View original image
        JButton originalButton = new JButton("Original");
        originalButton.setToolTipText("<html><p width=\"300\">" + originaltip + "</p></html>");
        originalButton.addActionListener((ActionEvent event) -> {
            if (originalImage != null) {
                setTitle("PSE Image - Visualizando: Imagem original");
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
        timelineButtonPanel2.setBackground(Color.GRAY);
        add(timelineButtonPanel2);
        
        // View result image
        JButton resultButton = new JButton("Resultado");
        resultButton.setToolTipText("<html><p width=\"300\">" + resulttip + "</p></html>");
        resultButton.addActionListener((ActionEvent event) -> {
            if (mainImage != null) {
                if (mustProcess) {
                    new ProcessFunctionsWorker().execute();
                    while (mustProcess != false) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(1000);
                        } catch (Exception e) {
                        }
                    }
                }
                setTitle("PSE Image - Visualizando: Imagem resultado");
                mainImage = imageHistory.get(imageHistory.size() - 1);
                showImage();
            }
        });
        timelineButtonPanel2.add(resultButton);
        
        // Save Image
        JButton saveButton = new JButton("Salvar");
        saveButton.setToolTipText("<html><p width=\"300\">" + savetip + "</p></html>");
        saveButton.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - Salvar");
            saveImage();
        });
        saveButton.setBackground(Color.WHITE);
        timelineButtonPanel2.add(saveButton);
        
        // Exit Image
        JButton exitButton = new JButton("Fechar");
        exitButton.setToolTipText("<html><p width=\"300\">" + quittip + "</p></html>");
        exitButton.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - Fechar");
            System.exit(0);
        });
        exitButton.setBackground(Color.WHITE);
        //timelineButtonPanel2.add(exitButton);
        
        // Help Image
        JButton helpButton = new JButton("Ajuda");
        helpButton.setToolTipText("<html><p width=\"300\">" + quittip + "</p></html>");
        helpButton.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - Ajuda");
            openWebpage("https://github.com/rafaelkalan/Pse-Image/blob/master/Documentation.pdf");
        });
        helpButton.setBackground(Color.WHITE);
        timelineButtonPanel2.add(helpButton);

        // Button Panel
        // -------------------------------------------------------------------------
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(buttonX, buttonY));
        buttonPanel.setLayout(new GridLayout(22, 1));
        buttonPanel.setBackground(Color.DARK_GRAY);
        add(buttonPanel);
        
        // Func1
        JButton f1Button = new JButton(f1);
        f1Button.setToolTipText("<html><p width=\"300\">" + f1tip + "</p></html>");
        f1Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f1);
            addTimeline(f1);
        });
        
        // Func2
        JButton f2Button = new JButton(f2);
        f2Button.setToolTipText("<html><p width=\"300\">" + f2tip + "</p></html>");
        f2Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f2);
            addTimeline(f2);
        });
        buttonPanel.add(f2Button);
        
        // Func3
        JButton f3Button = new JButton(f3);
        f3Button.setToolTipText("<html><p width=\"300\">" + f3tip + "</p></html>");
        f3Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f3);
            addTimeline(f3);
        });
        
        // Func4
        JButton f4Button = new JButton(f4);
        f4Button.setToolTipText("<html><p width=\"300\">" + f4tip + "</p></html>");
        f4Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f4);
            addTimeline(f4);
        });
        
        // Func5
        JButton f5Button = new JButton(f5);
        f5Button.setToolTipText("<html><p width=\"300\">" + f5tip + "</p></html>");
        f5Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f5);
            addTimeline(f5);
        });
        
        // Func6
        JButton f6Button = new JButton(f6);
        f6Button.setToolTipText("<html><p width=\"300\">" + f6tip + "</p></html>");
        f6Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f6);
            addTimeline(f6);
        });
        
        // Func7 (Convolução)
        JButton f7Button = new JButton(f7);
        f7Button.setToolTipText("<html><p width=\"300\">" + f7tip + "</p></html>");
        f7Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f7);
            addTimeline(f7);
        });
        f7Button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent click) {
                if (SwingUtilities.isLeftMouseButton(click)) {
                    JTextField xField = new JTextField(3);
                    JTextField yField = new JTextField(3);
                    JTextField weightField = new JTextField(9);
                    xField.setText("" + convolucaoLinhas);
                    yField.setText("" + convolucaoColunas);
                    weightField.setText(convolucaoPesos.toString().substring(1, convolucaoPesos.toString().length() - 1));

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
                            if (tempLinhas < 2 || tempColunas < 2) {
                                JOptionPane.showMessageDialog(new JFrame(), "Número de linhas e colunas deve ser maior que 2!");
                                return;
                            }
                            if (tempLinhas % 2 != 1 || tempColunas % 2 != 1) {
                                JOptionPane.showMessageDialog(new JFrame(), "Número de linhas e colunas deve ser ímpar!");
                                return;
                            }
                            ArrayList tempPesos = new ArrayList();
                            String stringPesos[] = weightField.getText().split(",");
                            if (stringPesos.length != tempLinhas * tempColunas) {
                                JOptionPane.showMessageDialog(new JFrame(), "Número de pesos não esta de acordo com número de linhas e colunas!");
                                return;
                            }
                            convolucaoLinhas = tempLinhas;
                            convolucaoColunas = tempColunas;
                            for (int i = 0; i < stringPesos.length; i++) {
                                convolucaoPesos.add(Integer.parseInt(stringPesos[i].trim()));
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(new JFrame(), "Parâmetros inválidos!");
                        }
                    }
                }
            }
        });
        
        // Func8 (Brilho)
        JButton f8Button = new JButton(f8);
        f8Button.setToolTipText("<html><p width=\"300\">" + f8tip + "</p></html>");
        f8Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f8);
            addTimeline(f8);
        });
        f8Button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent click) {
                if (SwingUtilities.isLeftMouseButton(click)) {
                    JTextField xField = new JTextField(3);
                    xField.setText("" + brilhoFloat);

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
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(new JFrame(), "Parâmetros inválidos!");
                        }
                    }
                }
            }
        });
        
        // Func9 (Contraste)
        JButton f9Button = new JButton(f9);
        f9Button.setToolTipText("<html><p width=\"300\">" + f9tip + "</p></html>");
        f9Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f9);
            addTimeline(f9);
        });
        f9Button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent click) {
                if (SwingUtilities.isLeftMouseButton(click)) {
                    JTextField xField = new JTextField(3);
                    xField.setText("" + contrasteFloat);

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
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(new JFrame(), "Parâmetros inválidos!");
                        }
                    }
                }
            }
        });
        
        // Func10
        JButton f10Button = new JButton(f10);
        f10Button.setToolTipText("<html><p width=\"300\">" + f10tip + "</p></html>");
        f10Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f10);
            addTimeline(f10);
        });
        f10Button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent click) {
                if (SwingUtilities.isLeftMouseButton(click)) {
                    JTextField xField = new JTextField(3);
                    xField.setText("" + limiarDouble);

                    JPanel parameters = new JPanel();
                    parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
                    parameters.add(new JLabel("Valor de intensidade para o limiar:"));
                    parameters.add(xField);

                    int result = JOptionPane.showConfirmDialog(null, parameters,
                            "Parâmetros do limiar global", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            Double tempDouble = Double.parseDouble(xField.getText());
                            if (tempDouble < 0 || tempDouble > 255) {
                                JOptionPane.showMessageDialog(new JFrame(), "Parâmetros devem estar entre 0 e 255");
                                return;
                            }
                            limiarDouble = tempDouble;
                            System.out.println(limiarDouble);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(new JFrame(), "Parâmetros inválidos!");
                        }
                    }
                }
            }
        });
        
        // Func11
        JButton f11Button = new JButton(f11);
        f11Button.setToolTipText("<html><p width=\"300\">" + f11tip + "</p></html>");
        f11Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f11);
            addTimeline(f11);
        });
        f11Button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent click) {
                if (SwingUtilities.isLeftMouseButton(click)) {
                    JTextField rMinField = new JTextField(3);
                    JTextField gMinField = new JTextField(3);
                    JTextField bMinField = new JTextField(3);
                    JTextField rMaxField = new JTextField(3);
                    JTextField gMaxField = new JTextField(3);
                    JTextField bMaxField = new JTextField(3);

                    rMinField.setText("" + filtroRGB[0]);
                    gMinField.setText("" + filtroRGB[1]);
                    bMinField.setText("" + filtroRGB[2]);
                    rMaxField.setText("" + filtroRGB[3]);
                    gMaxField.setText("" + filtroRGB[4]);
                    bMaxField.setText("" + filtroRGB[5]);

                    JPanel parameters = new JPanel();
                    parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
                    parameters.add(new JLabel("Colocar valores de RGB (0-255 para cada):"));
                    JPanel minRGB = new JPanel();
                    parameters.add(minRGB);
                    minRGB.setLayout(new FlowLayout());
                    minRGB.add(new JLabel("Min:"));
                    minRGB.add(rMinField);
                    minRGB.add(gMinField);
                    minRGB.add(bMinField);
                    JPanel maxRGB = new JPanel();
                    parameters.add(maxRGB);
                    maxRGB.setLayout(new FlowLayout());
                    maxRGB.add(new JLabel("Max:"));
                    maxRGB.add(rMaxField);
                    maxRGB.add(gMaxField);
                    maxRGB.add(bMaxField);

                    int result = JOptionPane.showConfirmDialog(null, parameters,
                            "Parâmetros do filtro de cor", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            filtroRGB = new int[6];
                            filtroRGB[0] = Integer.parseInt(rMinField.getText());
                            filtroRGB[1] = Integer.parseInt(gMinField.getText());
                            filtroRGB[2] = Integer.parseInt(bMinField.getText());
                            filtroRGB[3] = Integer.parseInt(rMaxField.getText());
                            filtroRGB[4] = Integer.parseInt(gMaxField.getText());
                            filtroRGB[5] = Integer.parseInt(bMaxField.getText());
                            for (int i = 0; i < filtroRGB.length; i++) {
                                if (filtroRGB[i] < 0 || filtroRGB[i] > 255) {
                                    JOptionPane.showMessageDialog(new JFrame(), "Parâmetros devem estar entre 0 e 255");
                                    filtroRGB[0] = 0;
                                    filtroRGB[1] = 0;
                                    filtroRGB[2] = 0;
                                    filtroRGB[3] = 255;
                                    filtroRGB[4] = 255;
                                    filtroRGB[5] = 255;
                                }
                            }
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(new JFrame(), "Parâmetros inválidos!");
                        }
                    }
                }
            }
        });
        
        // Func12
        JButton f12Button = new JButton(f12);
        f12Button.setToolTipText("<html><p width=\"300\">" + f12tip + "</p></html>");
        f12Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f12);
            addTimeline(f12);
        });
        f12Button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent click) {
                if (SwingUtilities.isLeftMouseButton(click)) {
                    JTextField xField = new JTextField(3);
                    xField.setText("" + interpolacaoFator);

                    JPanel parameters = new JPanel();
                    parameters.setLayout(new BoxLayout(parameters, BoxLayout.Y_AXIS));
                    parameters.add(new JLabel("Fator de escala para a interpolação:"));
                    parameters.add(xField);

                    int result = JOptionPane.showConfirmDialog(null, parameters,
                            "Parâmetros da interpolação", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            Double tempDouble = Double.parseDouble(xField.getText());
                            if (tempDouble <= 0) {
                                JOptionPane.showMessageDialog(new JFrame(), "Fator deve ser maior que 0");
                                return;
                            }
                            interpolacaoFator = tempDouble;
                            System.out.println(interpolacaoFator);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(new JFrame(), "Parâmetros inválidos!");
                        }
                    }
                }
            }
        });
        
        // Func14 (EMQ)
        JButton f14Button = new JButton(f14);
        f14Button.setToolTipText("<html><p width=\"300\">" + f14tip + "</p></html>");
        f14Button.addActionListener((ActionEvent event) -> {
            if (mainImage != null) {
                setTitle("PSE Image - " + f14);
                JOptionPane.showMessageDialog(new JFrame(), calculoEMQ(originalImage, mainImage));
            }
        });
        
        // Func15 (Histograma)
        JToggleButton f15Button = new JToggleButton(f15);
        f15Button.setToolTipText("<html><p width=\"300\">" + f15tip + "</p></html>");
        f15Button.addItemListener((ItemEvent event) -> {
            int state = event.getStateChange();
            if (state == ItemEvent.SELECTED) {
                histogramOn = true;
                setTitle("PSE Image - " + f15);
                histogramFrame.setVisible(true);
                showImage();
            } else {
                histogramOn = false;
                setTitle("PSE Image");
                histogramFrame.setVisible(false);
            }
        });
        
        // Func13
        JButton f13Button = new JButton(f13);
        f13Button.setToolTipText("<html><p width=\"300\">" + f13tip + "</p></html>");
        f13Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f13);
            addTimeline(f13);
        });
        
        // Func98 (Tamanho Original)
        JToggleButton f98Button = new JToggleButton(f98);
        f98Button.setToolTipText("<html><p width=\"300\">" + sizetip + "</p></html>");
        f98Button.addItemListener((ItemEvent event) -> {
            int state = event.getStateChange();
            if (state == ItemEvent.SELECTED) {
                scaleOff = true;
                setTitle("PSE Image - " + f98);
                showImage();
            } else {
                scaleOff = false;
                setTitle("PSE Image");
                showImage();
            }
        });
        
        // Func16
        JButton f16Button = new JButton(f16);
        f16Button.setToolTipText("<html><p width=\"300\">" + f16tip + "</p></html>");
        f16Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f16);
            addTimeline(f16);
        });

        // Func17 (Mediana)
        JButton f17Button = new JButton(f17);
        f17Button.setToolTipText("<html><p width=\"300\">" + f17tip + "</p></html>");
        f17Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f17);
            addTimeline(f17);
        });

        // Func18 (Moda)
        JButton f18Button = new JButton(f18);
        f18Button.setToolTipText("<html><p width=\"300\">" + f18tip + "</p></html>");
        f18Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f18);
            addTimeline(f18);
        });

        // Func19 (Mínimo)
        JButton f19Button = new JButton(f19);
        f19Button.setToolTipText("<html><p width=\"300\">" + f19tip + "</p></html>");
        f19Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f19);
            addTimeline(f19);
        });
        

        // Func20 (Máximo)
        JButton f20Button = new JButton(f20);
        f20Button.setToolTipText("<html><p width=\"300\">" + f20tip + "</p></html>");
        f20Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image - " + f20);
            addTimeline(f20);
        });


        // Func99
        JButton f99Button = new JButton(f99);
        f99Button.setToolTipText("<html><p width=\"300\">" + resettip + "</p></html>");
        f99Button.addActionListener((ActionEvent event) -> {
            setTitle("PSE Image");
            resetTimeline();
        });
        f99Button.setBackground(Color.WHITE);

        // Add function buttons in desired order
        buttonPanel.add(f1Button); // Cinza
        buttonPanel.add(f2Button); // Negativo
        buttonPanel.add(f9Button); // Contraste
        buttonPanel.add(f8Button); // Brilho
        buttonPanel.add(f3Button); // Media
        buttonPanel.add(f12Button);// Interpolação
        buttonPanel.add(f7Button); // Convolução
        buttonPanel.add(f4Button); // Gaussiano
        buttonPanel.add(f5Button); // Laplaciano
        buttonPanel.add(f6Button); // Sobel
        buttonPanel.add(f13Button);// Hough Linha
        buttonPanel.add(f16Button);// Hough Círculo
        buttonPanel.add(f10Button);// Limiar
        buttonPanel.add(f11Button);// Cor
        buttonPanel.add(f14Button);// EMQ
        buttonPanel.add(f98Button);// Tamanho original
        buttonPanel.add(f15Button);// Histograma
        buttonPanel.add(f16Button);// HoughLine
        buttonPanel.add(f17Button);// Mediana
        buttonPanel.add(f18Button);// Moda
        buttonPanel.add(f19Button);// Mínimo
        buttonPanel.add(f20Button);// Máximo
        buttonPanel.add(f99Button);// Resetar

        // Draw Panel
        // -------------------------------------------------------------------------
        drawPanel = new JPanel();
        drawPanel.setPreferredSize(new Dimension(gridX, gridY));
        drawPanel.setLayout(new BorderLayout());
        drawPanel.setBackground(Color.GRAY);
        add(drawPanel);

        // Tooltip configuration
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
    }

    public static void openWebpage(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openImage() {
        JFileChooser imageChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Arquivos de imagem", "png", "jpg", "jpeg");
        imageChooser.setFileFilter(filter);
        int returnVal = imageChooser.showOpenDialog(drawPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        //    System.out.println("You chose to open this file: "
                //    + imageChooser.getSelectedFile().getName());
            try {
                mainImage = ImageIO.read(imageChooser.getSelectedFile());
                originalImage = mainImage;
                showImage();
                setTitle("PSE Image - " + imageChooser.getName(imageChooser.getSelectedFile()));
                resetTimeline();
            } catch (IOException e) {
            }
        }
    }

        private void openSecondImage() {
        JFileChooser imageChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Arquivos de imagem", "png", "jpg", "jpeg");
        imageChooser.setFileFilter(filter);
        int returnVal = imageChooser.showOpenDialog(drawPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
        //    System.out.println("You chose to open this file: "
                //    + imageChooser.getSelectedFile().getName());
            try {
                secondImage = ImageIO.read(imageChooser.getSelectedFile());
                // originalImage = mainImage;
                showSecondImage();
                setTitle("PSE Image - " + imageChooser.getName(imageChooser.getSelectedFile()));
                resetTimeline();
            } catch (IOException e) {
            }
        }
    }

    private void showImage() {
        if (mainImage != null) {
            if (mainImageLabel != null) {
                drawPanel.remove(mainImageLabel);
            }
            float drawWidth = (float) drawPanel.getSize().getWidth();
            float drawHeight = (float) drawPanel.getSize().getHeight();
            float imgWidth = mainImage.getWidth();
            float imgHeight = mainImage.getHeight();
            float imgRatio = imgWidth / imgHeight;
            float drawRatio = drawWidth / drawHeight;
            float imgWidthNew = imgWidth;
            float imgHeightNew = imgHeight;
            if (imgRatio > drawRatio) {
                imgWidthNew = drawWidth;
                imgHeightNew = imgHeight * (drawWidth / imgWidth);
            } else if (imgRatio < drawRatio) {
                imgWidthNew = imgWidth * (drawHeight / imgHeight);
                imgHeightNew = drawHeight;
            } else {
                imgWidth = drawWidth;
                imgHeight = drawHeight;
            }
            Image tempImage = mainImage.getScaledInstance(Math.round(imgWidthNew), Math.round(imgHeightNew), Image.SCALE_SMOOTH);
            if (!scaleOff) {
                mainImageLabel = new JLabel(new ImageIcon(tempImage));
            } else {
                mainImageLabel = new JLabel(new ImageIcon(mainImage));
            }
            drawPanel.add(mainImageLabel, BorderLayout.CENTER);
            drawPanel.repaint();
            drawPanel.validate();
            if (histogramOn) {
                drawWidth = (float) histogramPanel.getSize().getWidth();
                drawHeight = (float) histogramPanel.getSize().getHeight();
                BufferedImage mainHistogram = Histograma(mainImage);
                imgWidth = mainHistogram.getWidth();
                imgHeight = mainHistogram.getHeight();
                imgRatio = imgWidth / imgHeight;
                drawRatio = drawWidth / drawHeight;
                imgWidthNew = imgWidth;
                imgHeightNew = imgHeight;
                if (imgRatio > drawRatio) {
                    imgWidthNew = drawWidth;
                    imgHeightNew = imgHeight * (drawWidth / imgWidth);
                } else if (imgRatio < drawRatio) {
                    imgWidthNew = imgWidth * (drawHeight / imgHeight);
                    imgHeightNew = drawHeight;
                } else {
                    imgWidth = drawWidth;
                    imgHeight = drawHeight;
                }
            //    tempImage = mainHistogram.getScaledInstance(Math.round(imgWidthNew), Math.round(imgHeightNew), Image.SCALE_SMOOTH);
                tempImage = mainHistogram.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                if (histogramLabel != null) {
                    histogramPanel.remove(histogramLabel);
                }
                histogramLabel = new JLabel(new ImageIcon(tempImage));
                histogramPanel.add(histogramLabel, BorderLayout.WEST);
                histogramPanel.repaint();
                histogramPanel.validate();
            }
        }
    }

 private void showSecondImage() {
        if (secondImage != null) {
            if (secondImageLabel != null) {
                drawPanel.remove(secondImageLabel);
            }
            float drawWidth = (float) drawPanel.getSize().getWidth();
            float drawHeight = (float) drawPanel.getSize().getHeight();
            float imgWidth = secondImage.getWidth();
            float imgHeight = secondImage.getHeight();
            float imgRatio = imgWidth / imgHeight;
            float drawRatio = drawWidth / drawHeight;
            float imgWidthNew = imgWidth;
            float imgHeightNew = imgHeight;
            if (imgRatio > drawRatio) {
                imgWidthNew = drawWidth;
                imgHeightNew = imgHeight * (drawWidth / imgWidth);
            } else if (imgRatio < drawRatio) {
                imgWidthNew = imgWidth * (drawHeight / imgHeight);
                imgHeightNew = drawHeight;
            } else {
                imgWidth = drawWidth;
                imgHeight = drawHeight;
            }
            Image tempImage = secondImage.getScaledInstance(Math.round(imgWidthNew), Math.round(imgHeightNew), Image.SCALE_SMOOTH);
            if (!scaleOff) {
                secondImageLabel = new JLabel(new ImageIcon(tempImage));
            } else {
                secondImageLabel = new JLabel(new ImageIcon(mainImage));
            }
            drawPanel.add(secondImageLabel, BorderLayout.EAST);
            drawPanel.repaint();
            drawPanel.validate();

            //parte do histograma
            if (histogramOn) {
                drawWidth = (float) histogramPanel.getSize().getWidth();
                drawHeight = (float) histogramPanel.getSize().getHeight();
                BufferedImage mainHistogram = Histograma(mainImage);
                imgWidth = mainHistogram.getWidth();
                imgHeight = mainHistogram.getHeight();
                imgRatio = imgWidth / imgHeight;
                drawRatio = drawWidth / drawHeight;
                imgWidthNew = imgWidth;
                imgHeightNew = imgHeight;
                if (imgRatio > drawRatio) {
                    imgWidthNew = drawWidth;
                    imgHeightNew = imgHeight * (drawWidth / imgWidth);
                } else if (imgRatio < drawRatio) {
                    imgWidthNew = imgWidth * (drawHeight / imgHeight);
                    imgHeightNew = drawHeight;
                } else {
                    imgWidth = drawWidth;
                    imgHeight = drawHeight;
                }
            //    tempImage = mainHistogram.getScaledInstance(Math.round(imgWidthNew), Math.round(imgHeightNew), Image.SCALE_SMOOTH);
                tempImage = mainHistogram.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                if (histogramLabel != null) {
                    histogramPanel.remove(histogramLabel);
                }
                histogramLabel = new JLabel(new ImageIcon(tempImage));
                histogramPanel.add(histogramLabel, BorderLayout.CENTER);
                histogramPanel.repaint();
                histogramPanel.validate();
            }
        }
    }


    private void saveImage() {
        if (mainImage == null) {
            return;
        }
        JFileChooser imageChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Imagens PNG", "png");
        imageChooser.setFileFilter(filter);
        int returnVal = imageChooser.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String filePath = imageChooser.getSelectedFile().toString();
            if (!filePath.endsWith(".png")) {
                filePath += ".png";
            }
            File file = new File(filePath);
            try {
                ImageIO.write(mainImage, "png", file);
                setTitle("PSE Image - " + file.getName() + " salvo");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void addTimeline(String func) {
        JButton button = new JButton(func);
        mustProcess = true;
        button.setToolTipText("<html><p width=\"300\">" + timelinetip + "</p></html>");
        button.addActionListener((ActionEvent event) -> {
            JButton sourceButton = (JButton) event.getSource();
            if (mainImage == null) {
                return;
            }
            if (mustProcess) {
                new ProcessFunctionsWorker().execute();
            } else {
                setTitle("PSE Image - Visualizando: " + func);
                Color defaultColor = sourceButton.getBackground();
                Component components[] = timelinePanel.getComponents();
                for (int i = 0; i < timelinePanel.getComponentCount(); i++) {
                    JButton testButton = (JButton) components[i];
                    if (testButton == sourceButton) {
                        mainImage = imageHistory.get(i);
                        showImage();
                    }
                }
            }
        });
        button.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent click) {
                if (SwingUtilities.isRightMouseButton(click)) {
                    mustProcess = true;
                    setTitle("PSE Image - " + func + " X");
                    Component component[] = timelinePanel.getComponents();
                    for (int i = 0; i < timelinePanel.getComponentCount(); i++) {
                        JButton testButton = (JButton) component[i];
                        if (testButton == button) {
                            if (i < lastProcessed) {
                                lastProcessed = i;
                            }
                            break;
                        }
                    }
                    timelinePanel.remove(button);
                    timelinePanel.validate();
                    if (timelinePanel.getComponentCount() < 1) {
                        resetTimeline();
                    }
                }
            }
        });
        timelinePanel.add(button);
        timelinePanel.validate();
    }

    private void resetTimeline() {
        if (mainImage != null) {
            mainImage = originalImage;
            showImage();
        }
        Component component[] = timelinePanel.getComponents();
        for (int i = timelinePanel.getComponentCount() - 1; i >= 0; i--) {
            JButton button = (JButton) component[i];
            timelinePanel.remove(button);
        }
        mustProcess = true;
        lastProcessed = 0;
        timelinePanel.repaint();
        timelinePanel.validate();
    }

    public class ProcessFunctionsWorker extends SwingWorker<Integer, String> {

        @Override
        protected Integer doInBackground() throws Exception {
            setTitle("PSE Image - Processar");
            mainImage = (lastProcessed == 0) ? originalImage : imageHistory.get(lastProcessed - 1);
            imageHistory = (lastProcessed == 0) ? new ArrayList<BufferedImage>() : imageHistory;
            showImage();
            Component component[] = timelinePanel.getComponents();
            for (int i = lastProcessed; i < timelinePanel.getComponentCount(); i++) {
                JButton button = (JButton) component[i];
                Color defaultColor = button.getBackground();
                String defaultText = button.getText();
                int perc = 100 / timelinePanel.getComponentCount();
                button.setBackground(Color.YELLOW);
                for (int t = 1; t <= 10; t++) {
                    setTitle("PSE Image - Processamento - " + (i * perc + t * perc / 10) + "% - " + defaultText);
                    button.setText(defaultText + " " + t * 10 + "%");
                    button.validate();
                    try {
                        TimeUnit.MILLISECONDS.sleep(50);
                    } catch (Exception e) {
                    }
                    button.validate();
                    timelinePanel.validate();
                    validate();
                }
                if (mainImage != null) {
                    functionChooser(defaultText);
                    imageHistory.add(i, mainImage);
                    showImage();
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(300);
                } catch (Exception e) {
                }
                button.setText(defaultText);
                button.setBackground(defaultColor);
                button.validate();
                timelinePanel.validate();
                validate();
                lastProcessed = i + 1;
            }
            setTitle("PSE Image - Processamento - 100% - Completo");
            mustProcess = false;
            return 0;
        }

        private boolean functionChooser(String name) {
            if (name.equals(f1)) {
                mainImage = EscalaDeCinza(mainImage);
            } else if (name.equals(f2)) {
                mainImage = Negativo(mainImage);
            } else if (name.equals(f3)) {
                mainImage = Media(mainImage);
            } else if (name.equals(f4)) {
                mainImage = Gaussiano(mainImage);
            } else if (name.equals(f5)) {
                mainImage = Laplaciano(mainImage);
            } else if (name.equals(f6)) {
                mainImage = Sobel(mainImage);
            } else if (name.equals(f7)) {
                mainImage = Convolucao(mainImage, convolucaoLinhas, convolucaoColunas, convolucaoPesos);
            } else if (name.equals(f8)) {
                mainImage = Brilho(mainImage, brilhoFloat);
            } else if (name.equals(f9)) {
                mainImage = Contraste(mainImage, contrasteFloat);
            } else if (name.equals(f10)) {
                if (limiarDouble != -1) {
                    mainImage = Limiar(mainImage, limiarDouble);
                } else {
                    mainImage = LGP(mainImage);
                }
            } else if (name.equals(f11)) {
                mainImage = Segmentacao(mainImage, filtroRGB);
            } else if (name.equals(f12)) {
                mainImage = Interpolacao(mainImage, interpolacaoFator);
            } else if (name.equals(f16)) {
                mainImage = HoughTransformLine(mainImage);
            } else if (name.equals(f17)) {
                mainImage = Mediana(mainImage);
            } else if (name.equals(f18)) {
                mainImage = Moda(mainImage);
            } else if (name.equals(f19)) {
                mainImage = Minimo(mainImage);
            } else if (name.equals(f20)) {
                mainImage = Maximo(mainImage);
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
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);

        //pegar coluna e linha da imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();

        int media = 0;
        //laço para varrer a matriz de pixels da imagem
        for (int i = 0; i < coluna; i++) {
            for (int j = 0; j < linha; j++) {
                //rgb recebe o valor RGB do pixel em questão                
                int rgb = imagem.getRGB(i, j);
                int r = (int) ((rgb & 0x00FF0000) >>> 16); //R
                int g = (int) ((rgb & 0x0000FF00) >>> 8);  //G
                int b = (int) (rgb & 0x000000FF);       //B

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
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);

        //pegar coluna e linha da imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();
        //laço para varrer a matriz de pixels da imagem
        for (int i = 0; i < coluna; i++) {
            for (int j = 0; j < linha; j++) {
                //rgb recebe o valor RGB do pixel em questão 
                int rgb = imagem.getRGB(i, j);
                //a cor inversa é dado por 255 menos o valor da cor                 
                int r = 255 - (int) ((rgb & 0x00FF0000) >>> 16);
                int g = 255 - (int) ((rgb & 0x0000FF00) >>> 8);
                int b = 255 - (int) (rgb & 0x000000FF);
                Color color = new Color(r, g, b);
                ResultImage.setRGB(i, j, color.getRGB());
            }
        }
        return ResultImage;
    }

    public static BufferedImage Media(BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);

        //mascara de média
        int[][] mascaraMedia = {{1, 1, 1},
                                {1, 1, 1},
                                {1, 1, 1}};
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
                        r += (mascaraMedia[1 + l][1 + k] * (int) ((rgb & 0x00FF0000) >>> 16));
                        g += (mascaraMedia[1 + l][1 + k] * (int) ((rgb & 0x0000FF00) >>> 8));
                        b += (mascaraMedia[1 + l][1 + k] * (int) ((rgb & 0x000000FF)));
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
        ResultImage.getSubimage(1, 1, coluna - 1, linha - 1);
        return ResultImage;
    }

    public static BufferedImage Moda(BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);
        ArrayList<Integer> colors = new ArrayList();

        //cores primarias
        int r = 0, g = 0, b = 0, p = 0;
        int rgb = 0;

        //pegar coluna e linha da imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();

        // percorre todos os pixels da imagem
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                //percorre a máscara
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        //rgb = rgb do pixel
                        rgb = imagem.getRGB(j + k, i + l);
                        colors.add(rgb);
                    }
                }

                // depois de pegar os pixels na mascara, ordena as intensidades
                Collections.sort(colors);

                //define a intensidade que mais repete
                int[] count = new int[colors.get(colors.size()-1)];
                int maior = 0;
                
                // contar as intensidades
                for (int c = 0; c < colors.size(); c++) {
                    count[colors.get(c)]++;
                }

                // achar o maior numero de ocorrencias
                maior = count[0];
                for (int c = 1; c < count.length; c++) {
                    if (maior < count[c]) {
                        maior = count[c];
                    }
                }

                // achar a intensidade de maior numero de ocorrencias
                for (p = 0; p < count.length; p++) {
                    if (count[p] == maior) {
                        break;
                    }
                }

                int cor = colors.get(p);
                r = ((cor & 0x00FF0000) >>> 16);
                g = ((cor & 0x0000FF00) >>> 8);
                b = (cor & 0x000000FF);

                // nova cor do pixel
                Color tempColor = new Color(r, g, b);
                
                // setar o respectivel pixel na nova imagem
                ResultImage.setRGB(j, i, tempColor.getRGB());
                
                // zerar valor das cores primarias e limpar lista de intensidades
                r = g = b = p = 0;
                colors.clear();
            }
        }

        ResultImage.getSubimage(1, 1, coluna - 1, linha - 1);
        return ResultImage;
    }

    public static BufferedImage Mediana(BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);
        ArrayList<Integer> colors = new ArrayList();

        //cores primarias
        int r = 0, g = 0, b = 0;
        int rgb = 0;

        //pegar coluna e linha da imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();

        // percorre todos os pixels da imagem
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                //percorre a máscara
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        //rgb = rgb do pixel
                        rgb = imagem.getRGB(j + k, i + l);
                        colors.add(rgb);
                    }
                }

                // depois de pegar os pixels na mascara, ordena as intensidades
                Collections.sort(colors);

                // define qual intensidade escolher e decompoe em RGB//
                int posicao = (int)(colors.size()/2);
                int cor = colors.get(posicao);
                r = ((cor & 0x00FF0000) >>> 16);
                g = ((cor & 0x0000FF00) >>> 8);
                b = (cor & 0x000000FF);

                // nova cor do pixel
                Color tempColor = new Color(r, g, b);
                
                // setar o respectivel pixel na nova imagem
                ResultImage.setRGB(j, i, tempColor.getRGB());
                
                // zerar valor das cores primarias e limpar lista de intensidades
                r = g = b = 0;
                colors.clear();
            }
        }

        ResultImage.getSubimage(1, 1, coluna - 1, linha - 1);
        return ResultImage;
    }

    public static BufferedImage Minimo(BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);
        ArrayList<Integer> colors = new ArrayList();

        //cores primarias
        int r = 0, g = 0, b = 0;
        int rgb = 0;

        //pegar coluna e linha da imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();

        // percorre todos os pixels da imagem
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                //percorre a máscara
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        //rgb = rgb do pixel
                        rgb = imagem.getRGB(j + k, i + l);
                        colors.add(rgb);
                    }
                }

                // depois de pegar os pixels na mascara, ordena as intensidades
                Collections.sort(colors);

                // define qual intensidade escolher e decompoe em RGB
                int cor = colors.get(0);
                r = ((cor & 0x00FF0000) >>> 16);
                g = ((cor & 0x0000FF00) >>> 8);
                b = (cor & 0x000000FF);

                // nova cor do pixel
                Color tempColor = new Color(r, g, b);
                
                // setar o respectivel pixel na nova imagem
                ResultImage.setRGB(j, i, tempColor.getRGB());
                
                // zerar valor das cores primarias e limpar lista de intensidades
                r = g = b = 0;
                colors.clear();
            }
        }

        ResultImage.getSubimage(1, 1, coluna - 1, linha - 1);
        return ResultImage;
    }



    public static BufferedImage Maximo(BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);
        ArrayList<Integer> colors = new ArrayList();

        //cores primarias
        int r = 0, g = 0, b = 0;
        int rgb = 0;

        //pegar coluna e linha da imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();

        // percorre todos os pixels da imagem
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                //percorre a máscara
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        //rgb = rgb do pixel
                        rgb = imagem.getRGB(j + k, i + l);
                        colors.add(rgb);
                    }
                }

                // depois de pegar os pixels na mascara, ordena as intensidades
                Collections.sort(colors);

                // define qual intensidade escolher e decompoe em RGB
                int posicao = colors.size() - 1;
                int cor = colors.get(posicao);
                r = ((cor & 0x00FF0000) >>> 16);
                g = ((cor & 0x0000FF00) >>> 8);
                b = (cor & 0x000000FF);

                // nova cor do pixel
                Color tempColor = new Color(r, g, b);
                
                // setar o respectivel pixel na nova imagem
                ResultImage.setRGB(j, i, tempColor.getRGB());
                
                // zerar valor das cores primarias e limpar lista de intensidades
                r = g = b = 0;
                colors.clear();
            }
        }

        ResultImage.getSubimage(1, 1, coluna - 1, linha - 1);
        return ResultImage;
    }


    public static BufferedImage Gaussiano(BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);
        //mascara
        int[][] mascaraGaussiano = {{1, 2, 1},
        {2, 4, 2},
        {1, 2, 1}};
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
                        r += (mascaraGaussiano[1 + l][1 + k] * (int) ((rgb & 0x00FF0000) >>> 16));
                        g += (mascaraGaussiano[1 + l][1 + k] * (int) ((rgb & 0x0000FF00) >>> 8));
                        b += (mascaraGaussiano[1 + l][1 + k] * (int) ((rgb & 0x000000FF)));
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
        ResultImage.getSubimage(1, 1, coluna - 1, linha - 1);
        return ResultImage;
    }

    public static BufferedImage Laplaciano(BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);
        //mascaras
        int[][] mascaraL1 = {{0, -1, 0},
        {-1, 4, -1},
        {0, -1, 0}};

        int[][] mascaraL2 = {{1, 1, 1},
        {1, -8, 1},
        {1, 1, 1}};

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
                        r += (mascaraL1[1 + l][1 + k] * (int) ((rgb & 0x00FF0000) >>> 16));
                        g += (mascaraL1[1 + l][1 + k] * (int) ((rgb & 0x0000FF00) >>> 8));
                        b += (mascaraL1[1 + l][1 + k] * (int) ((rgb & 0x000000FF)));
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
                        r += (mascaraL2[1 + l][1 + k] * (int) ((rgb & 0x00FF0000) >>> 16));
                        g += (mascaraL2[1 + l][1 + k] * (int) ((rgb & 0x0000FF00) >>> 8));
                        b += (mascaraL2[1 + l][1 + k] * (int) ((rgb & 0x000000FF)));
                    }

                }
                //arredondamento de valores
                Color tempColor = new Color(Math.min(255, Math.max(0, r)), Math.min(255, Math.max(0, g)), Math.min(255, Math.max(0, b)));
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        ResultImage.getSubimage(1, 1, coluna - 1, linha - 1);
        return ResultImage;
    }

    public static BufferedImage Sobel(BufferedImage imagem) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);
        //mascaras
        int[][] mascaraS1 = {{-1, -2, -1},
        {0, 0, 0},
        {1, 2, 1}};
        int[][] mascaraS2 = {{-1, 0, 1},
        {-2, 0, 2},
        {-1, 0, 1}};

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
                        r += (mascaraS1[1 + l][1 + k] * (int) ((rgb & 0x00FF0000) >>> 16));
                        g += (mascaraS1[1 + l][1 + k] * (int) ((rgb & 0x0000FF00) >>> 8));
                        b += (mascaraS1[1 + l][1 + k] * (int) ((rgb & 0x000000FF)));
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
                        r += (mascaraS2[1 + l][1 + k] * (int) ((rgb & 0x00FF0000) >>> 16));
                        g += (mascaraS2[1 + l][1 + k] * (int) ((rgb & 0x0000FF00) >>> 8));
                        b += (mascaraS2[1 + l][1 + k] * (int) ((rgb & 0x000000FF)));
                    }

                }
                //Arredondamento dos valores
                Color tempColor = new Color(Math.min(255, Math.max(0, r)), Math.min(255, Math.max(0, g)), Math.min(255, Math.max(0, b)));
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        ResultImage.getSubimage(1, 1, coluna - 1, linha - 1);
        return ResultImage;
    }

    public static BufferedImage Convolucao(BufferedImage imagem, int linhas, int colunas, java.util.List<Integer> pesos) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);
        if (linhas != colunas) {
            System.out.println("ERRO: A matriz deve ser quadrada e ímpar");
            return null;
        } else if (linhas % 2 == 0) {
            System.out.println("ERRO: A matriz deve ser quadrada e ímpar");
            return null;
        } else {
            //mascara de média
            int[][] mascaraConvolucao = new int[linhas][colunas];
            int ponteiro = 0;
            for (int i = 0; i < linhas; i++) {
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
            int inicial = (int) Math.floor(linhas / 2);

            //percorre a imagem
            for (int i = inicial; i + inicial < linha; i++) {
                for (int j = inicial; j + inicial < coluna; j++) {
                    //percorre a máscara
                    for (int l = -inicial; l <= inicial; l++) {
                        for (int k = -inicial; k <= inicial; k++) {
                            //rgb = rgb do pixel
                            int rgb = imagem.getRGB(j + k, i + l);
                            //pegando os valores das cores primarias de cada pixel após a convolucao com a máscara
                            r += (mascaraConvolucao[inicial + l][inicial + k] * (int) ((rgb & 0x00FF0000) >>> 16));
                            g += (mascaraConvolucao[inicial + l][inicial + k] * (int) ((rgb & 0x0000FF00) >>> 8));
                            b += (mascaraConvolucao[inicial + l][inicial + k] * (int) ((rgb & 0x000000FF)));
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
            ResultImage.getSubimage(inicial, inicial, coluna - inicial, linha - inicial);
        }
        return ResultImage;
    }

    public static BufferedImage Brilho(BufferedImage imagem, int x) {
        //imagem resultante
        float f;
        if (x < 0) {
            int tempx = 1 - (x / 100);
            String temp = 0 + "." + tempx;
            f = Float.parseFloat(temp);
        } else if (x > 0 && x < 100) {
            String temp = 1 + "." + (x / 10) + "f";
            f = Float.parseFloat(temp);
        } else if (x == 100) {
            f = 2;
        } else if (x == 0) {
            f = 1;
        } else {
            String temp = x + "f";
            f = Float.parseFloat(temp);
        }

        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);

        RescaleOp rescaleOp = new RescaleOp(f, 0, null);
        rescaleOp.filter(imagem, ResultImage);

        return ResultImage;
    }

    public static BufferedImage Contraste(BufferedImage imagem, int x) {
        String temp = -x + "f";
        float f = Float.parseFloat(temp);
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);

        RescaleOp rescaleOp = new RescaleOp(1.0f, -x, null);
        rescaleOp.filter(imagem, ResultImage);

        return ResultImage;
    }

    public static double log10(double x) {
        // calcular o valor de log10 que será usado no calculo do PSNR
        return Math.log(x) / Math.log(10);
    }

    public static String calculoEMQ(BufferedImage imagem, BufferedImage imagem2) {

        BufferedImage ResultImage = imagem2;
        imagem = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);
        //ResultImage = new BufferedImage(ResultImage.getColorModel(), ResultImage.copyData(null), ResultImage.getColorModel().isAlphaPremultiplied(), null);

        //pegar linha e coluna da imagem
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();

        int sinal = 0, ruido = 0, mse = 0;
        int sR = 0;
        int sG = 0;
        int sB = 0;
        int rR = 0;
        int rG = 0;
        int rB = 0;
        double peak = 0, snr = 0;

        for (int i = 0; i < coluna; i++) {
            for (int j = 0; j < linha; j++) {
                // pegar os valores de cada pixel da imagem original e da imagem resultante, respectivamente

                int rgb = imagem.getRGB(i, j);
                int r = (int) ((rgb & 0x00FF0000) >>> 16); //R
                int g = (int) ((rgb & 0x0000FF00) >>> 8);  //G
                int b = (int) (rgb & 0x000000FF);       //B
                int rgbResult = ResultImage.getRGB(i, j);
                //Caclulo do sinal e do ruido
                sR = sR + r * rgbResult;
                sG = sG + g * rgbResult;
                sB = sB + b * rgbResult;
                sinal = (sR + sG + sB) / 3;

                rR = rR + (r - rgbResult) * (r - rgbResult);
                rG = rG + (g - rgbResult) * (g - rgbResult);
                rB = rB + (b - rgbResult) * (b - rgbResult);
                ruido = (rR + rG + rB) / 3;
                //Calculo do pico da imagem
                if (peak < rgb) {
                    peak = rgb;
                }
            }
        }
        //calculo do Erro Medio Quadratico
        mse = mse + (int) Math.pow(ruido, 2) / (linha * coluna);
        peak += 10 * log10((255 * 255) / mse);
//        System.out.println("MSE:" + mse);
//        System.out.println("PSNR(max=" + 255 + "): " + peak);
        String result = "MSE:" + mse + "\nPSNR(max=" + 255 + "): " + peak;
        return result;
    }

    //-------------------------------Limiar Global Padrão----------------------------
    public static BufferedImage LGP(BufferedImage imagem) {

        //Cria a imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);

        //instancia as variaveis de (r,g,b) e as variaveis auxiliares para o calculo da média
        int r = 0, g = 0, b = 0, mediar, mediag, mediab, totalpixel;
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
            for (int j = 1; j + 1 < imagem.getWidth(); j++) {
                //rgb
                int rgb = imagem.getRGB(j, i);
                //percorre a imagem somando os (r,g,b)'s
                r += (int) ((rgb & 0x00FF0000) >>> 16);
                g += (int) ((rgb & 0x0000FF00) >>> 8);
                b += (int) ((rgb & 0x000000FF));

            }
        }
        //calculo da media para r,g e b
        totalpixel = imagem.getHeight() * imagem.getWidth();
        mediar = Math.round(r / totalpixel);
        mediag = Math.round(g / totalpixel);
        mediab = Math.round(b / totalpixel);
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
            for (int j = 1; j + 1 < imagem.getWidth(); j++) {
                //rgb
                int rgb = imagem.getRGB(j, i);
                //percorre a imagem aplicando o limiar binario
                r = (int) ((rgb & 0x00FF0000) >>> 16);
                if (r < mediar) {         //se menor que a media  = 0
                    r = 0;
                } else if (r > mediar) {       //senão = 255
                    r = 255;
                }
                g = (int) ((rgb & 0x0000FF00) >>> 8);
                if (g < mediag) {         //se menor que a media  = 0
                    g = 0;
                } else if (g > mediag) {       //senão = 255
                    g = 255;
                }
                b = (int) ((rgb & 0x000000FF));
                if (b < mediab) {         //se menor que a media  = 0
                    b = 0;
                } else if (b >= mediab) {      //senão = 255
                    b = 255;
                }
                //nova cor do pixel
                Color tempColor = new Color(r, g, b);
                //setar o respectivel pixel na nova imagem
                ResultImage.setRGB(j, i, tempColor.getRGB());

            }
        }
        return ResultImage;
    }

    // Limiar com parâmtero
    public static BufferedImage Limiar(BufferedImage imagem, double t) { //Limiar Recebendo Parametro

        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);
        int r = 0, g = 0, b = 0, mediar, mediag, mediab, totalpixel;
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
            for (int j = 1; j + 1 < imagem.getWidth(); j++) {
                //rgb
                int rgb = imagem.getRGB(j, i);

                //percorrer imagem
                r += (int) ((rgb & 0x00FF0000) >>> 16);
                g += (int) ((rgb & 0x0000FF00) >>> 8);
                b += (int) ((rgb & 0x000000FF));

            }
        }
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
            for (int j = 1; j + 1 < imagem.getWidth(); j++) {
                //rgb
                int rgb = imagem.getRGB(j, i);

                //percorrer imagem
                r = (int) ((rgb & 0x00FF0000) >>> 16);
                if (r < t) {
                    r = 0;
                } else if (r >= t) {
                    r = 255;
                }
                g = (int) ((rgb & 0x0000FF00) >>> 8);
                if (g < t) {
                    g = 0;
                } else if (g >= t) {
                    g = 255;
                }
                b = (int) ((rgb & 0x000000FF));
                if (b < t) {
                    b = 0;
                } else if (b >= t) {
                    b = 255;
                }
                //nova cor do pixel
                Color tempColor = new Color(r, g, b);
                //setar o respectivel pixel na nova imagem
                ResultImage.setRGB(j, i, tempColor.getRGB());

            }
        }
        return ResultImage;
    }

    //----------------------------------------------------------------------
    //----------------------- Maior Valor ---------------------------------------
    public static int maxVal(int[] a) { // Pega o maior valor dado um Array de inteiros
        int b = 0;
        for (int i = 0; i < a.length; i++) { //Percorre o array
            if (a[i] > b) {         //Se achar um valor maior que o anterior
                b = a[i];      //Armazena o novo maior valor
            }
        }
        return b;
    }

    //------------------------- Histograma -------------------------------
    public static BufferedImage Histograma(BufferedImage imagem) {

        //Cria a imagem resultante
        BufferedImage ResultImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        //Instancia variaveis para auxiliar as configurações do grafico de histograma
        int width = imagem.getWidth();      // lagura
        int height = imagem.getHeight();    // altura
        int padding = 40;           // espaçamento para a régua
        int labelPadding = 40;          // espaçamento para o texto
        Color lineColor = new Color(44, 102, 230, 180);     // Cor da Linha
        Color pointColor = new Color(100, 100, 100, 180);   // Cor do Ponto
        Color gridColor = new Color(200, 200, 200, 200);    // Cor do Grid
        final Stroke GRAPH_STROKE = new BasicStroke(4f);    // Espessura da Linha
        int pointWidth = 8;                 // Espessura do Ponto
        int numberYDivisions = 10;              // Numero Máximo de Divisões/Linhas em Y
        int[] scores = new int[6];              // Array que armazena as intensidades de cada intervalo
        for (int i = 0; i < scores.length; i++) {             // {0-50, 50-100, 100-200, 200-255}
            scores[i] = 0;
        }

        int r = 0, g = 0, b = 0;                 // Variaveis R, G , B
        //Fazer as intensidades
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
            for (int j = 1; j + 1 < imagem.getWidth(); j++) {
                //rgb
                int rgb = (int) imagem.getRGB(j, i);
                r = (int) ((rgb & 0x00FF0000) >>> 16);
                g = (int) ((rgb & 0x0000FF00) >>> 8);
                b = (int) ((rgb & 0x000000FF));

                // Para cada intervalo incrementa a intensidade
                if (r <= 50 || g <= 50 || b <= 50) {     // 0-50
                    scores[0]++;
                } else if ((r > 50 && r <= 100) || (g > 50 && g <= 100) || (b > 50 && b <= 100)) { // 50-100
                    scores[2]++;
                } else if ((r > 100 && r <= 150) || (g > 100 && g <= 150) || (b > 100 && b <= 150)) { // 100-150
                    scores[3]++;
                } else if ((r > 150 && r <= 200) || (g > 150 && g <= 200) || (b > 150 && b <= 200)) { // 150-200
                    scores[4]++;
                } else if ((r > 200 || g > 200 || b > 200)) { // 200-255
                    scores[5]++;
                }
            }
        }

        width = 300;
        height = 300;
        Graphics2D g2 = ResultImage.createGraphics();   // Cria um gráfico dentro da Imagem Resultante
        g2.setColor(Color.WHITE);           // Torna o fundo da imagem branco
        g2.fillRect(0, 0, width, height);            //Para o tamanho da imagem
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        double xScale = ((double) width - (2 * padding) - labelPadding) / (scores.length - 1);  // Escala de X
        double yScale = ((double) height - 2 * padding - labelPadding) / (maxVal(scores) - 0);  // Escala de Y

        ArrayList<Point> graphPoints = new ArrayList<Point>();    // Cria uma Lista de Pontos

        for (int i = 0; i < scores.length; i++) {
            int x1 = (int) (i * xScale + padding + labelPadding);
            int y1 = (int) ((maxVal(scores) - scores[i]) * yScale + padding);
            graphPoints.add(new Point(x1, y1));
        }

        g2.setColor(Color.WHITE);   // Trona o fundo do Gráfico Branco
        g2.fillRect(padding + labelPadding, padding, width - (2 * padding) - labelPadding, height - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);   // Torna o texto preto

        // Cria grid lines para o eixo y.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = height - ((i * (height - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
            int y1 = y0;

            if (scores.length > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, width - padding, y1);
                g2.setColor(Color.BLACK);
                String yLabel = ((int) (maxVal(scores) / numberYDivisions)) * i + "";
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);

            }

            g2.drawLine(x0, y0, x1, y1);
        }

        // e para o eixo x
        int[] labels = {0, 50, 100, 150, 200, 255};
        for (int i = 0; i < scores.length; i++) {
            if (scores.length > 1) {
                int x0 = i * (width - padding * 2 - labelPadding) / (scores.length - 1) + padding + labelPadding;
                int x1 = x0;
                int y0 = height - padding - labelPadding;
                int y1 = y0 - pointWidth;

                if ((i % ((int) ((scores.length / 20.0)) + 1)) == 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(x0, height - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = labels[i] + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }
        }

        // cria os eixos x e y  
        g2.drawLine(padding + labelPadding, height - padding - labelPadding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, height - padding - labelPadding, width - padding, height - padding - labelPadding);
        // e desenha as linhas até os pontos
        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);

        for (int i = 0; i < graphPoints.size(); i++) {
            int x1 = graphPoints.get(i).x;
            int y1 = graphPoints.get(i).y;
            int x2 = i * (width - padding * 2 - labelPadding) / (scores.length - 1) + padding + labelPadding;
            int y2 = height - padding - labelPadding;
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);

        for (int i = 0; i < graphPoints.size(); i++) {
            int x = graphPoints.get(i).x - pointWidth / 2;
            int y = graphPoints.get(i).y - pointWidth / 2;
            int ovalW = pointWidth;
            int ovalH = pointWidth;
            g2.fillOval(x, y, ovalW, ovalH);
        }

        return ResultImage;
    }

    public static BufferedImage Segmentacao(BufferedImage imagem, int[] array) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage(imagem.getColorModel(), imagem.copyData(null), imagem.getColorModel().isAlphaPremultiplied(), null);

        //System.out.println("1");
        int r = 0, g = 0, b = 0;
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();

        int rgb;

        Color branco = new Color(255, 255, 255);
        Color preto = new Color(0, 0, 0);

        ///*
        int r_valormin = array[0], r_valormax = array[3];
        int g_valormin = array[1], g_valormax = array[4];
        int b_valormin = array[2], b_valormax = array[5];
        //*/

        //percorrer imagem
        for (int i = 0; i < linha; i++) {
            for (int j = 0; j < coluna; j++) {

                rgb = imagem.getRGB(j, i);
                r = (int) ((rgb & 0x00FF0000) >>> 16);
                g = (int) ((rgb & 0x0000FF00) >>> 8);
                b = (int) ((rgb & 0x000000FF));

                if (r >= r_valormin && r <= r_valormax) {
                    if (g >= g_valormin && g <= g_valormax) {
                        if (b >= b_valormin && b <= b_valormax) {
                            ResultImage.setRGB(j, i, branco.getRGB());
                        } else {
                            ResultImage.setRGB(j, i, preto.getRGB());
                        }
                    } else {
                        ResultImage.setRGB(j, i, preto.getRGB());
                    }
                } else {
                    ResultImage.setRGB(j, i, preto.getRGB());
                }
            }
        }
        return ResultImage;
    }

    public static BufferedImage Interpolacao(BufferedImage imagem, double scaleFactor) {
        int newWidth = (int)Math.round(imagem.getWidth() * scaleFactor);
        int newHeight = (int)Math.round(imagem.getHeight() * scaleFactor);
        BufferedImage result = new BufferedImage(newWidth, newHeight, imagem.getType());
        Graphics2D g = result.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(imagem, 0, 0, newWidth, newHeight, 0, 0, imagem.getWidth(),
                imagem.getHeight(), null);
        g.dispose();
        return result;
    }

    public static BufferedImage HoughTransformLine(BufferedImage imagem) {
        try {

            HoughTransformLine HTL = new HoughTransformLine(imagem);
            Vector<HoughLine> lines = HTL.getLines(4);
            HTL.drawHoughLines(lines);
            imagem = (BufferedImage) HTL.getImage();

        } catch (Exception e) {
            System.out.println("Erro! " + e.getMessage());
            e.printStackTrace();
        }
        return imagem;
    }
}

class HoughTransformLine extends Thread {

    // The size of the neighbourhood in which to search for other local maxima 
    final int neighbourhoodSize = 4;

    // How many discrete values of theta shall we check? 
    final int maxTheta = 180;

    // Using maxTheta, work out the step 
    final double thetaStep = Math.PI / maxTheta;

    // the width and height of the image 
    protected int width, height;

    // the hough array 
    protected int[][] houghArray;

    // the coordinates of the centre of the image 
    protected float centerX, centerY;

    // the height of the hough array 
    protected int houghHeight;

    // double the hough height (allows for negative numbers) 
    protected int doubleHeight;

    // the number of points that have been added 
    protected int numPoints;
    protected BufferedImage BFimage;

    // cache of values of sin and cos for different theta values. Has a significant performance improvement. 
    private double[] sinCache;
    private double[] cosCache;

    public HoughTransformLine(BufferedImage image) {
        initialise(image.getWidth(), image.getHeight());
        image = addPoints(image);
        setImage(image);
    }

    public void setImage(BufferedImage BFimage) {
        BFimage = this.BFimage;
    }

    public BufferedImage getImage() {
        return BFimage;
    }

    /**
     * Initialises the hough transform. The dimensions of the input image are
     * needed in order to initialise the hough array.
     *
     * @param width The width of the input image
     * @param height The height of the input image
     */
    public HoughTransformLine(int width, int height) {
        initialise(width, height);
    }

    /**
     * Initialises the hough array. Called by the constructor so you don't need
     * to call it yourself, however you can use it to reset the transform if you
     * want to plug in another image (although that image must have the same
     * width and height)
     */
    public void initialise(int width, int height) {
        this.width = width;
        this.height = height;

        // Calculate the maximum height the hough array needs to have 
        houghHeight = (int) (Math.sqrt(2) * Math.max(height, width)) / 2;

        // Double the height of the hough array to cope with negative r values 
        doubleHeight = 2 * houghHeight;

        // Create the hough array 
        houghArray = new int[maxTheta][doubleHeight];

        // Find edge points and vote in array 
        centerX = width / 2;
        centerY = height / 2;

        // Count how many points there are 
        numPoints = 0;

        // cache the values of sin and cos for faster processing 
        sinCache = new double[maxTheta];
        cosCache = sinCache.clone();
        for (int t = 0; t < maxTheta; t++) {
            double realTheta = t * thetaStep;
            sinCache[t] = Math.sin(realTheta);
            cosCache[t] = Math.cos(realTheta);
        }
    }

    /**
     * Adds points from an image. The image is assumed to be greyscale black and
     * white, so all pixels that are not black are counted as edges. The image
     * should have the same dimensions as the one passed to the constructor.
     */
    public BufferedImage addPoints(BufferedImage image) {

        // Now find edge points and update the hough array 
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                // Find non-black pixels 
                if ((image.getRGB(x, y) & 0x000000ff) != 0) {
                    addPoint(x, y);
                }
            }
        }
        return image;
    }

    /**
     * Adds a single point to the hough transform. You can use this method
     * directly if your data isn't represented as a buffered image.
     */
    public void addPoint(int x, int y) {

        // Go through each value of theta 
        for (int t = 0; t < maxTheta; t++) {

            //Work out the r values for each theta step 
            int r = (int) (((x - centerX) * cosCache[t]) + ((y - centerY) * sinCache[t]));

            // this copes with negative values of r 
            r += houghHeight;

            if (r < 0 || r >= doubleHeight) {
                continue;
            }

            // Increment the hough array 
            houghArray[t][r]++;
        }

        numPoints++;
    }

    public Vector<HoughLine> getLines(int n) {
        return (getLines(n, 0));
    }

    public Vector<HoughLine> getLines(int n, int threshold) {

        // Initialise the vector of lines that we'll return 
        Vector<HoughLine> lines = new Vector<HoughLine>(20);

        // Only proceed if the hough array is not empty 
        if (numPoints == 0) {
            return lines;
        }

        // Search for local peaks above threshold to draw 
        for (int t = 0; t < maxTheta; t++) {
            loop:
            for (int r = neighbourhoodSize; r < doubleHeight - neighbourhoodSize; r++) {

                // Only consider points above threshold 
                if (houghArray[t][r] > threshold) {

                    int peak = houghArray[t][r];

                    // Check that this peak is indeed the local maxima 
                    for (int dx = -neighbourhoodSize; dx <= neighbourhoodSize; dx++) {
                        for (int dy = -neighbourhoodSize; dy <= neighbourhoodSize; dy++) {
                            int dt = t + dx;
                            int dr = r + dy;
                            if (dt < 0) {
                                dt = dt + maxTheta;
                            } else if (dt >= maxTheta) {
                                dt = dt - maxTheta;
                            }
                            if (houghArray[dt][dr] > peak) {
                                // found a bigger point nearby, skip 
                                continue loop;
                            }
                        }
                    }

                    // calculate the true value of theta 
                    double theta = t * thetaStep;

                    // add the line to the vector 
                    lines.add(new HoughLine(theta, r, width, height, houghArray[t][r]));
                }
            }
        }
        Collections.sort(lines, Collections.reverseOrder());
        lines.setSize(n);

        return lines;
    }

    public void fitLine(HoughLine l) {
    }

    public void drawHoughLines(Vector<HoughLine> lines) {
        if (lines != null) {
            //stroke(255, 0, 0);

            for (int j = 0; j < lines.size(); j++) {
                HoughLine l = lines.elementAt(j);
                //line(l.x1, l.y1, l.x2, l.y2);
                //System.out.println(j+ " " + l.score);
            }
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}

class HoughLine extends Line2D.Float implements Comparable<HoughLine> {

    protected double theta;
    protected double r;
    protected int score;

    /**
     * Initialises the hough line
     */
    public HoughLine(double theta, double r, int width, int height, int score) {
        this.theta = theta;
        this.r = r;
        this.score = score;

        // During processing h_h is doubled so that -ve r values 
        int houghHeight = (int) (Math.sqrt(2) * Math.max(height, width)) / 2;

        // Find edge points and vote in array 
        float centerX = width / 2;
        float centerY = height / 2;

        // Draw edges in output array 
        double tsin = Math.sin(theta);
        double tcos = Math.cos(theta);

        if (theta < Math.PI * 0.25 || theta > Math.PI * 0.75) {
            int x1 = 0, y1 = 0;
            int x2 = 0, y2 = height - 1;

            x1 = (int) ((((r - houghHeight) - ((y1 - centerY) * tsin)) / tcos) + centerX);
            x2 = (int) ((((r - houghHeight) - ((y2 - centerY) * tsin)) / tcos) + centerX);

            setLine(x1, y1, x2, y2);
        } else {
            int x1 = 0, y1 = 0;
            int x2 = width - 1, y2 = 0;

            y1 = (int) ((((r - houghHeight) - ((x1 - centerX) * tcos)) / tsin) + centerY);
            y2 = (int) ((((r - houghHeight) - ((x2 - centerX) * tcos)) / tsin) + centerY);

            setLine(x1, y1, x2, y2);
        }
    }

    public int compareTo(HoughLine o) {
        return (this.score - o.score);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Map;
import java.util.TreeMap;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
 
/**
 *
 * @author deboraalmeida
 */
class Circle{
 
   private int centerX;
 
   private int centerY;
   public Circle (int inX, int inY){
       centerX = inX;
       centerY = inY;
   }
 
    public int getCenterX(){
     return centerX;
    }

    public void setCenterX(int centerX){
        this.centerX = centerX;
    }
 
    public int getCenterY(){
        return centerY;
    }
 
    public void setCenterY(int centerY){
        this.centerY = centerY;
    }
 
    @Override
 
    public String toString(){
        return "Circle [centerX=" + centerX + ", centerY=" + centerY + "]";
    }
}
class Exibicao {
    //Exibição
    // SADO
    public static void exibirImagem(BufferedImage imagem) {
        ImageIcon icon = new ImageIcon(imagem);
        JLabel imagemLabel = new JLabel(icon);
        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout());
        contentPane.add(new JScrollPane(imagemLabel));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
 
    public static void exibirImagem(BufferedImage imagem, BufferedImage imagem2) {
        ImageIcon icon = new ImageIcon(imagem);
        JLabel imagemLabel = new JLabel(icon);
        ImageIcon icon2 = new ImageIcon(imagem2);
        JLabel imagemLabel2 = new JLabel(icon2);
        JFrame frame = new JFrame();
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout());
        contentPane.add(new JScrollPane(imagemLabel));
        contentPane.add(new JScrollPane(imagemLabel2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 680);
        frame.setVisible(true);
    }
}
public class PID {

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
   
    public static BufferedImage Convolucao (BufferedImage imagem,int linhas, int colunas, List <Integer> pesos) {
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
   
    public static BufferedImage Brilho (BufferedImage imagem, float x) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
 
        RescaleOp rescaleOp = new RescaleOp(x, 0, null);
        rescaleOp.filter(imagem, ResultImage);
       
        return ResultImage;
    }
   
    public static BufferedImage Contraste (BufferedImage imagem, float x) {
        //imagem resultante
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
 
        RescaleOp rescaleOp = new RescaleOp(1.0f, x, null);
        rescaleOp.filter(imagem, ResultImage);
       
        return ResultImage;
    }
 
//----------------------------------------------------------------------
    public static BufferedImage LGP (BufferedImage imagem) { //Limiar Global Padrão
        
        //imagem resultante
            BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
        int r = 0, g = 0, b = 0, mediar, mediag, mediab, totalpixel;
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
                for (int j = 1; j + 1 < imagem.getWidth(); j++) {
            //rgb
                    int rgb = imagem.getRGB(j, i);
    
            //percorrer imagem
                    r += (int)((rgb&0x00FF0000)>>>16);
                    g += (int)((rgb&0x0000FF00)>>>8);
                    b += (int)((rgb&0x000000FF));
    
            }
        }
        totalpixel = imagem.getHeight() * imagem.getWidth();
        mediar = Math.round(r/ totalpixel);
        mediag = Math.round(g/ totalpixel);
        mediab = Math.round(b/ totalpixel);
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
                for (int j = 1; j + 1 < imagem.getWidth(); j++) {
                    //rgb
                    int rgb = imagem.getRGB(j, i);
    
                    //percorrer imagem
                    r = (int)((rgb&0x00FF0000)>>>16);
            if(r < mediar){
                r = 0; 
            }else if(r > mediar){
                r = 255;
            }
                    g = (int)((rgb&0x0000FF00)>>>8);
                    if(g < mediag){
                        g = 0;
                    }else if(g > mediag){
                        g = 255;
                    }
                    b = (int)((rgb&0x000000FF));
                    if(b < mediab){
                        b = 0;
                    }else if(b >= mediab){
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
    public static int maxVal(int[] a){
        int b = 0;
        for(int i = 1; i < a.length; i++){
            if(a[i] >  b){
                b = a[i];
            }
        }
        return b;
    }
//----------------------------------------------------------------------
    public static BufferedImage Histograma (BufferedImage imagem) {
    
                //imagem resultante
        BufferedImage ResultImage = new BufferedImage (imagem.getWidth(),imagem.getHeight(),BufferedImage.TYPE_INT_RGB);
        //BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
    
        int width = imagem.getWidth();
            int height = imagem.getHeight();
            int padding = 40;
            int labelPadding = 40;
        Color lineColor = new Color(44, 102, 230, 180);
            Color pointColor = new Color(100, 100, 100, 180);
            Color gridColor = new Color(200, 200, 200, 200);
            final Stroke GRAPH_STROKE = new BasicStroke(4f);
            int pointWidth = 8;
            int numberYDivisions = 10;
        int[] scores = new int[6];
        for(int i = 0; i < scores.length; i++){
            scores[i] = 0;
        }
        int r = 0, g =0, b = 0;
            //Fazer os scores
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
                for (int j = 1; j + 1 < imagem.getWidth(); j++) {
                    //rgb
                    int rgb = (int)imagem.getRGB(j, i);
            r = (int)((rgb&0x00FF0000)>>>16);
                    g = (int)((rgb&0x0000FF00)>>>8);
                    b = (int)((rgb&0x000000FF));
    
            if (r <= 50 ||
                g <= 50 ||
                b <= 50){
                scores[0]++;
            }else if( (r > 50 && r <= 100) ||
                (g > 50 && g <= 100) ||
                (b > 50 && b <= 100) ){
                scores[2]++;
            }else if((r > 100 && r <= 150) ||
                (g > 100 && g <= 150) ||
                (b > 100 && b <= 150) ){
                scores[3]++;   
            }else if((r > 150 && r <= 200) ||
                (g > 150 && g <= 200) ||
                (b > 150 && b <= 200) ){
                scores[4]++;
            }else if ((r > 200 ||
                            g > 200 ||
                            b > 200)){
                scores[5]++;
            }
        
                }
            }      
        for(int i =0; i < scores.length; i++){
            System.out.println(scores[i]);
        }  
        Graphics2D g2 = ResultImage.createGraphics();   // <--
        g2.setColor(Color.WHITE);
        g2.fillRect ( 0, 0, width, height );
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    
        double xScale = ((double) width - (2 * padding) - labelPadding) / (scores.length - 1);
        double yScale = ((double) height - 2 * padding - labelPadding) / (maxVal(scores) - 0);
    
        List<Point> graphPoints = new ArrayList<>();
            for (int i = 0; i < scores.length; i++) {
                int x1 = (int) (i * xScale + padding + labelPadding);
                int y1 = (int) ((maxVal(scores) - scores[i]) * yScale + padding);
                graphPoints.add(new Point(x1, y1));
        }
    
        g2.setColor(Color.WHITE);
            g2.fillRect(padding + labelPadding, padding, width - (2 * padding) - labelPadding, height - 2 * padding - labelPadding);
        g2.setColor(Color.BLACK);
    
        // create hatch marks and grid lines for y axis.
            for (int i = 0; i < numberYDivisions + 1; i++) {
                int x0 = padding + labelPadding;
                int x1 = pointWidth + padding + labelPadding;
                int y0 = height - ((i * (height - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
                int y1 = y0;
                if (scores.length > 0) {
                    g2.setColor(gridColor);
                    g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, width - padding, y1);
                    g2.setColor(Color.BLACK);
                // String yLabel = ((int) ((0 + (maxVal(scores) - 0) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
                    String yLabel = ((int) (maxVal(scores) / numberYDivisions)) * i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(yLabel);
                    g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
    
                }
    
        
        
                g2.drawLine(x0, y0, x1, y1);
            }
    
            // and for x axis
        int []labels = {0, 50, 100, 150, 200, 255};
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
    
        // create x and y axes
            g2.drawLine(padding + labelPadding, height - padding - labelPadding, padding + labelPadding, padding);
            g2.drawLine(padding + labelPadding, height - padding - labelPadding, width - padding, height - padding - labelPadding);
    
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
   
//----------------------------------------------------------------------
    
    public int[][] findEdges(int[][] sourceArray){
    
            double deltaSquaredThreshold = 800; // if dx^2 + dy^2 > threshold, call it an edge.

            int[][] edgeArray = ImageManager.createGrayscaleArrayOfSize(sourceArray.length-1,sourceArray[0].length-1);
            // TODO: insert your code here.

            for (int i = 1; i < sourceArray.length - 1; i++){
    
                for (int j = 1; j < sourceArray[0].length - 1; j++){
    
                    int Dx = sourceArray[i][j] - sourceArray[i - 1][j];
    
                    int Dy = sourceArray[i][j] - sourceArray[i][j - 1];
    
                    int magnitude = (int) (Math.pow(Dx, 2) + Math.pow(Dy, 2));
    
                    if (magnitude > 800){
    
                        edgeArray[i][j] = 255;
    
                    }else{
    
                        edgeArray[i][j] = 0;
                    }
                }
            }
            return edgeArray;
    }
 
    public ArrayList<Circle> findBestCircles(int [][] houghArray){
 
        int maxNumCirlces = 10; //the most circles you're hoping to get (adjust this)
 
        int votesThreshold = 60; // the minimum number of votes required to count as a "found" circle.
 
        int annihilationRadius = 15; // after you find a maximum in the hough array (and presumably do something with it),
 
        // wipe out all the votes within this radius of the vote winner to zero, so that
 
        // you are ready to get the next maximum. (Finding two maxima within a couple of pixels
 
        // is unlikely to be useful and more likely to be natural/rounding error.)
 
        int[][] houghCopy = ImageManager.deepCopyArray(houghArray);
 
        ArrayList<Circle> listOfCircles = new ArrayList<Circle>();
 
        int max = 0;
        int maxX = 0;
        int maxY = 0;
 
        while (listOfCircles.size() < maxNumCirlces){
            max = 0;
            maxX = 0;
            maxY = 0;
 
            for (int i = 1; i < houghArray.length; i++){
                 for (int j = 1; j < houghArray[0].length; j++){
                     if (houghArray[i][j] > max){
                       max = houghArray[i][j];
                       maxX = j;
                       maxY = i;
                    }
                }
            }
            if (max > votesThreshold){
 
                listOfCircles.add(new Circle(maxX,maxY));
 
            }else{
 
                break;
            }
       
            for (int j = maxX -15; j < maxX + 15; j++){
                for (int i = maxY - 15; i < maxY + 15; i++){
                    int Dx = maxX - j;
                    int Dy = maxY - i;
                    int magnitude = (int)(Math.pow(Dx, 2) + Math.pow(Dy, 2));
                    if(0 < annihilationRadius){
                        houghArray[i][j] = 0;
                    }
                }
            }
        }      
 
        return listOfCircles;
    }
 
    public int[][][] buildResult(int [][][] RGBSource, ArrayList<Circle> circleList){
 
        int[][][] result = ImageManager.deepCopyArray(RGBSource);
 
        // for each location in the circle list, set the corresponding pixel in result to be red (255,0,0).
        return result;
 
    }
 
    public int[][] normalizeArrayTo255(int[][] unnormalized){
 
        int max =0;
        for (int i = 1; i < unnormalized.length; i++){
            for (int j = 1; j < unnormalized[0].length; j++){
                if (unnormalized[i][j] > max){
                  max = unnormalized[i][j];
                }
            }
        }
 
        if (max == 0){
            throw new RuntimeException("Could not normalize the array to 0 to 255; array was empty.");
        }
        int [][] normalized = new int[unnormalized.length][unnormalized[0].length];
        for (int i = 1; i < unnormalized.length; i++){
            for (int j = 1; j < unnormalized[0].length; j++){
                normalized[i][j] = (unnormalized[i][j]*255)/max;
            }
        }
        return normalized;
    }
 
    public BufferedImage generateHough (BufferedImage imagem){
 
        BufferedImage sourceImg = new BufferedImage(imagem.getWidth(),imagem.getHeight(), BufferedImage.TYPE_INT_RGB), edgeImg, houghImg = new BufferedImage (imagem.getWidth(),imagem.getHeight(),BufferedImage.TYPE_INT_RGB);
 
        int r = 0, g = 0, b = 0, rgb;
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
            for (int j = 1; j + 1 < imagem.getWidth(); j++) {
            //rgb
                rgb = imagem.getRGB(j, i);
            //percorrer imagem
                r = (int)((rgb&0x00FF0000)>>>16);
                g = (int)((rgb&0x0000FF00)>>>8);
                b = (int)((rgb&0x000000FF));
                //nova cor do pixel
                Color tempColor = new Color(r, g, b);
                //setar o respectivel pixel na nova imagem
                sourceImg.setRGB(j, i, tempColor.getRGB());
            }
        }

        edgeImg = EscalaDeCinza(sourceImg);
        int TARGET_RADIUS = 27;
 
        for (int i = 1; i < edgeImg.getHeight(); i++){
            for (int j = 1; j < edgeImg.getWidth(); j++){
                //rgb
                rgb = edgeImg.getRGB(j, i);
                //percorrer imagem
                r = (int)((rgb&0x00FF0000)>>>16);
                g = (int)((rgb&0x0000FF00)>>>8);
                b = (int)((rgb&0x000000FF));
 
                if (r == 255 || g == 255 || b == 255){
                    for (int k = i - 27; k < i + 28; k++){
                        for (int l = j - 27; l < j + 28; l++){
                            if (k >= 0 && k < edgeImg.getHeight()){
                                if (l >= 0 && l < edgeImg.getWidth()){
 
                                    int X = i - k;
                                    int Y = j - l;
                                    int magnitude = (int)(Math.pow(X, 2) + Math.pow(Y, 2));

                                    if ((int)Math.sqrt(magnitude) == 27){
                                        //nova cor do pixel
                                        Color tempColor;
                                        if(r == 255 || g == 255 || b == 255 ){
                                            tempColor = new Color(r, g, b);
                                        } else {
                                            tempColor = new Color((r+1), (g), (b));
                                        }
                                        //Color tempColor = new Color((r+1), (g+1), (b+1));
                                        //setar o respectivel pixel na nova imagem
                                        houghImg.setRGB(k, l, tempColor.getRGB());
                                    }
                                }
                            }
 
                        }
 
                    }
 
                }
 
            }
 
        }
      // ArrayList<Circle> foundCircles = findBestCircles(houghImg);
       return houghImg;
    }
    
/// ----- Codigo pra endtender  --- transformada de um FDP DO CARALHO chamado Hough linhas
    
        private XYCoord point;
        private int angleInDegrees;
        private double angleInRadians;
        private int numOfRows;
        private int numOfCols;
        private int minimumVal;
        private int maximumVal;
        private int[][] houghAry;
        
        // construtor desativado tirar tudo ai de dentro e fazer algo com sa porra
        public HoughTransform(ImageProcessing imgProObj) {
            numOfRows = 180;
            // transformar linha abaixo pra get witdth and heigth de buffer img
            numOfCols = (int)Math.sqrt(Math.pow(imgProObj.getNumRows(), 2) + Math.pow(imgProObj.getNumCols(), 2));
            // atribute some value to it or pass it
            minimumVal = imgProObj.getMinVal();
            // atribute a value to it or pass it
            maximumVal = imgProObj.getMaxVal();

            System.out.println("This is numOfCols " + numOfCols);
            
            // Novo array de hough
            houghAry = new int[180][numOfCols];
            
            // Definição de valores as variaveis
            angleInDegrees = 0;
            angleInRadians = 0.0;
            point = new XYCoord(0,0);
            
        }
        
        // Calcula a distancia entre uma coordenadas x e y  e retorna seu valor
        public double computeDistance(double angle, XYCoord pt) {
            double t = computeT(angle,pt);
            return Math.sqrt((Math.pow(pt.getXCoordinate(), 2)) + (Math.pow(pt.getYCoordinate(), 2))) * (Math.cos(t));
        }
        
        // trem doido
        public double computeT(double radians, XYCoord pt) {
            return (radians - Math.atan(pt.getYCoordinate()/pt.getXCoordinate()) - (Math.PI/2));
        }
        
        // determina o cabeçalho da imagem
        public void determineHeader() {
                minimumVal = 9999;
                maximumVal = -9999;
            for(int i = 0; i < numOfRows; ++i) {
                for(int j = 0; j < numOfCols; ++j) {
                    if(houghAry[i][j] < minimumVal) 
                        minimumVal = houghAry[i][j];
                    if(houghAry[i][j] > maximumVal)
                        maximumVal = houghAry[i][j];
                }//for columns
            }//for rows
        }
        
        // todos metodos abaixo até o proximo comentario apenas uteis pra comunicação entre classes
        public int getAngleInDeg() { return angleInDegrees; }
        
        public double getAngleInRad() { return angleInRadians; }
        
        public int getNumOfRows() { return numOfRows; }
        
        public int getNumOfCols() { return numOfCols; }
        
        public int getMinimumVal() { return minimumVal; }
        
        public int getMaximumVal() { return maximumVal; }
        
        public int getHoughAryVal(int rowIndex, int colIndex) {
            return houghAry[rowIndex][colIndex];
        }
        
        // Transforma TUTOOOOOO VAI CHESSUS -- alterar para bufferimage
        public void executeHoughTransform(ImageProcessing image) {
            int distance = 0;
            
            // pegar valor do bufferimage
            for(int row = 0; row < image.getNumRows(); ++row) {
                //pegar valor do buffer img
                for(int col = 0; col < image.getNumCols(); ++col) {
                    // funçao dentro do if retorna valor posição x,y e faz validação
                    if(image.getImgArrVal(row,col) > 0) {
                       
                        angleInDegrees = 0;
                        point.setCoord(row, col);
                        point.printPoint();

                        while(angleInDegrees < 179) {
                            angleInRadians = (angleInDegrees) * (Math.PI/180);
                            distance = Math.abs((int)computeDistance(angleInRadians,point));
                            //System.out.println("This is the distance " + distance);
                            houghAry[angleInDegrees][distance]++;
                            angleInDegrees++;
                        }
                        angleInDegrees = 0;
                    }//if
                }//for columns
            }//for rows	
        }
        
        // printa a img trocar para um metodo que gera img usando bufferimg -- ignorar
        public void prettyPrint(String outputFile) {
            try {
                PrintWriter printToFile = new PrintWriter(new File(outputFile));
                for(int row = 0; row < numOfRows; ++row) {
                    for(int col = 0; col < numOfCols; ++col) {
                        if(houghAry[row][col] > 0 && houghAry[row][col] < 10) 
                            printToFile.print(houghAry[row][col] + " ");
                        else if(houghAry[row][col] > 9)
                            printToFile.print(houghAry[row][col] + "  ");
                            else 
                            printToFile.print(" ");
                    }
                    printToFile.println();
                }
                printToFile.flush();
                printToFile.close();
            } catch(Exception ioe) {
                System.out.println(ioe);
            }
        }
        
        private int numRows;
        private int numCols;
        private int minVal;
        private int maxVal;
        private int[][] imgAry;
        
        /*public ImageProcessing(String inputFile) {
            try {
                Scanner readInput = new Scanner(new File(inputFile));
                numRows = readInput.nextInt();
                numCols = readInput.nextInt();
                minVal  = readInput.nextInt();
                maxVal  = readInput.nextInt();
                imgAry = new int[numRows][numCols];
                readInput.close();
            } catch(IOException ioe) {
                System.out.println(ioe);
            }
        }*/
        
        public void loadImage(String inputFile) {
            int pixel = -1;
            try{
                Scanner readInput = new Scanner(new File(inputFile));
                
                //To skip the header
                for(int i = 0; i < 4; ++i)
                    pixel = readInput.nextInt();
                
                for(int i = 0; i < numRows; ++i) {
                    for(int j = 0; j < numCols; ++j) {
                        pixel = readInput.nextInt();
                        imgAry[i][j] = pixel;
                    }
                }
                readInput.close();
            }catch(IOException ioe) {
                System.out.println(ioe);
            }
        }
        
        public int getNumRows() { return numRows; }
        
        public int getNumCols() { return numCols; }
        
        public int getMinVal() { return minVal; }
        
        public int getMaxVal() { return maxVal; }
        
        public int getImgArrVal(int rowIndex, int colIndex) {
            return imgAry[rowIndex][colIndex];
        }
    // -------------------------------- fim do que tem pra entender ------------

///----------------------------------------------------------------------

/// --- codigo pra entender hough circulos
class HoughTransformCircles {
    // Variaveis do Baralho
    /* Max and minimal radius of the circles we want to find */
    private final int minRadius         = 30;
    private final int maxRadius         = 130;
    
    /* Max and minimal center x coordinates for the given circles */
    private final int minXCoord          = -200;
    private final int maxXCoord          = 200;
      
    /* Max and minimal center y coordinates for the given circles */
    private final int minYCoord          = -200;
    private final int maxYCoord          = 200;
    
    /* The size of a cell according to the Hough Transform algorithm */
    private final int radiusSize        = 6;
    private final int cellSize          = 6;

    /* How close could two circles lie be without being the same circle? */
    private final int minCircleDistance = 20;
    private final int minRadiusDiff     = 20;

    /* View related */
    private final int pointSize         = 4;
    private final int height            = 800;
    private final int width             = 800;

    private AccumulatorWrapper pixels   = null;
    private ArrayList<Point> data       = new ArrayList<Point>();
    private ArrayList<Circle> circles   = null;
    private HashMap<Circle, ArrayList<Point>> container = new HashMap<Circle, ArrayList<Point>>();

    /**
        Runs the Hough Transform algorithm and renders the result on a canvas
        @args[0] File containing data points
    */
    // Exemplo do main apagar sa porra e chamar usando bufferimg
    public static void main(String[] args) throws IllegalArgumentException { 
        if(args.length == 0){
            throw new IllegalArgumentException();
        }
        
        HoughTransform h = new HoughTransform(args[0]);
        h.execute();
        h.showCanvas();
    }

    /**
        @filePath Path to file containing points
    */
    public HoughTransform(String filePath) {
        Scanner sc = null;

        try {
            sc = new Scanner(new File(filePath));
        } catch(FileNotFoundException e) { }

        if(sc == null) {
            System.out.println("Something went wrong!");
        } else {
            while(sc.hasNext()){
                String[] points = sc.next().split(",");
                int x = Integer.parseInt(points[0]);
                int y = Integer.parseInt(points[1]);
                this.data.add(new Point(x, y));
            }
        }
        this.pixels = new AccumulatorWrapper(this.minXCoord,this.maxXCoord,this.minYCoord,this.maxYCoord,this.minRadius,this.maxRadius,this.cellSize,this.radiusSize);
    }

    /**
        Executes the Hough Transform algorithm
        Populates this.pixels for later use by this.showCanvas()
    */
    public void execute(){
        // For every point given by the user
        for (Point point : this.data) {
            // Find every circle which @point.getX() and @point.getY() lies on
            ArrayList<Circle> circles = this.getCircles(point);

            // For every found circle
            for (Circle circle : circles) {
                // Give the cell which contains @circle.getX(), @circle.getY() and @circle.getRadius() a +1
                this.pixels.increment(circle.getX(), circle.getY(), circle.getRadius());
            }
        }
        // acha possiveis candidados a circulos
        this.circles = this.filterNeighbors(this.pixels.getCandidates());
        ArrayList<Point> consensusSet = new ArrayList<Point>();

        // define posições onde o circulo vai ser desenhado
        for (Circle circle : this.circles) {
            for (Point point : this.data) {
                double x1 = circle.getX() + this.cellSize / 2.0;
                double y1 = circle.getY() + this.cellSize / 2.0;
                double offset = this.getOffset(point, x1, y1);

                if(Math.abs(circle.getRadius() - offset) <= (this.radiusSize / 2.0)){
                    consensusSet.add(point);
                }

                double x2 = circle.getX() - this.cellSize / 2.0;
                double y2 = circle.getY() + this.cellSize / 2.0;
                offset = this.getOffset(point, x2, y2);

                if(Math.abs(circle.getRadius() - offset) <= (this.radiusSize / 2.0)){
                    consensusSet.add(point);
                }

                double x3 = circle.getX() + this.cellSize / 2.0;
                double y3 = circle.getY() - this.cellSize / 2.0;
                offset = this.getOffset(point, x3, y3);

                if(Math.abs(circle.getRadius() - offset) <= (this.radiusSize / 2.0)){
                    consensusSet.add(point);
                }


                double x4 = circle.getX() - this.cellSize / 2.0;
                double y4 = circle.getY() - this.cellSize / 2.0;
                offset = this.getOffset(point, x4, y4);

                if(Math.abs(circle.getRadius() - offset) <= (this.radiusSize / 2.0)){
                    consensusSet.add(point);
                }
            }

            this.container.put(circle, consensusSet);
        }
    }

    private double getOffset(Point point, double x2, double y2) {
        double x1 = point.getX();
        double y1 = point.getY();
        
        //Pythagorean theorem
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    } 
    /**
        Render view based on this.pixels
    */
    public void showCanvas() throws IllegalArgumentException {
        final ArrayList<Circle> circles = this.circles;
        final int width                 = this.width;
        final int height                = this.height;
        final int pointSize             = this.pointSize;
        final double offsetWidth        = this.width / 2.0;
        final double offsetHeight       = this.height / 2.0;
        final ArrayList<Point> data     = this.data;
        final HashMap<Circle, ArrayList<Point>> container = this.container;
        JFrame frame                    = new JFrame();

        if(circles.isEmpty()){
            throw new IllegalArgumentException("No circles were found, have you tried running the execute method?");
        }

        frame.add(new Canvas(){
            @Override
            public void paint(Graphics g){
                double offsetWidth = this.getWidth() / 2.0;
                double offsetHeight = this.getHeight() / 2.0;
                g.translate((int) Math.round(offsetWidth), (int) Math.round(offsetHeight));
                for(Point point : data){
                    g.drawOval(
                        (int) (point.getX() + pointSize / 2.0 + 0.5),
                        (int) (point.getY() + pointSize / 2.0 + 0.5), 
                        pointSize, 
                        pointSize
                    );
                }
                
                for(Circle circle : container.keySet()){
                    double highestRadius = -1;
                    double smallestRadius = Double.POSITIVE_INFINITY;

                    for(Point point : container.get(circle)){
                        g.setColor(Color.GREEN);
                        g.drawOval(
                            (int) (point.getX() + pointSize / 2.0 + 0.5),
                            (int) (point.getY() + pointSize / 2.0 + 0.5), 
                            pointSize, 
                            pointSize
                        );

                        double distance = Math.sqrt(
                            Math.pow(point.getX() - circle.getX(), 2) + 
                            Math.pow(point.getY() - circle.getY(), 2)
                        );

                        if(distance > highestRadius) {
                            highestRadius = distance;
                        }

                        if(distance < smallestRadius) {
                            smallestRadius = distance;
                        }
                    }

                    g.setColor(Color.GREEN);
                    g.drawOval(
                        (int) (circle.getX() - highestRadius + 0.5),
                        (int) (circle.getY() - highestRadius + 0.5),
                        (int) (2 * highestRadius), 
                        (int) (2 * highestRadius)
                    );

                    System.out.println(highestRadius);
                    System.out.println(highestRadius);
                    g.setColor(Color.BLUE);
                    g.drawOval(
                        (int) (circle.getX() - smallestRadius + 0.5),
                        (int) (circle.getY() - smallestRadius + 0.5),
                        (int) (2 * smallestRadius), 
                        (int) (2 * smallestRadius)
                    );
                }
            }
        });
        frame.setSize(this.width, this.height);
        frame.setVisible(true);
    }

    /*
        Find every circle that has @point.getY() and @point.getX() as its center
    */
    private ArrayList<Circle> getCircles(Point point){
        ArrayList<Circle> circles = new ArrayList<Circle>();
        double x                  = point.getX();
        double y                  = point.getY();

        for (int r = this.minRadius; r < this.maxRadius; r++) {
            for (int b = this.minYCoord; b < this.maxYCoord; b++) {
                double res = -1 * b * b + 2 * b * y + r * r - y * y;
                if(res < 0) continue;

                double a1 = x - Math.sqrt(res);
                double a2 = x + Math.sqrt(res);

                if(this.minXCoord > a1) continue;
                if(this.maxXCoord < a1) continue;

                int a11 = (int) Math.round(a1);
                int a22 = (int) Math.round(a2);
                if(a11 < this.minXCoord || a11 > this.maxXCoord) continue;
                if(a22 < this.minXCoord || a22 > this.maxXCoord) continue;

                circles.add(new Circle(a11, b, r));
                circles.add(new Circle(a22, b, r));
            }
        }
        return circles;
    }

    /* 
         Removes circles that are too similar to nearby circles
		 Max distance between two circles is defined by {this.minCircleDistance}
		 Max difference between two radii defined by {this.minRadiusDiff}
    */
    private ArrayList<Circle> filterNeighbors(ArrayList<CircleContainer> circles){
        Circle currCircle                                = null;
        ArrayList<CircleContainer> foundCircleContainers = new ArrayList<CircleContainer>();
        ArrayList<Circle> foundCircles                   = new ArrayList<Circle>();
        double distance                                  = -1;
        boolean run                                      = true;
        int bestIndex                                    = 0;

        for(CircleContainer container : circles){
            currCircle = container.getCircle();
            for (int i = 0; i < foundCircleContainers.size(); i++) {
                CircleContainer foundCircleContainer = foundCircleContainers.get(i);
                Circle prevCircle = foundCircleContainer.getCircle();

                // The distance between two circles
                distance = Math.sqrt(
                    Math.pow(currCircle.getX() - prevCircle.getX(), 2)
                    +
                    Math.pow(currCircle.getY() - prevCircle.getY(), 2)
                );

                // Diffrence in length between two circles
                int rDiff = Math.abs(prevCircle.getRadius() - currCircle.getRadius());

                // Are the two circles similar?
                if(distance < this.minCircleDistance && rDiff < this.minRadiusDiff) {
                    // Should we swap the current circle with the one we found now?
                    if(container.getCount() > foundCircleContainer.getCount()) {
                        bestIndex = i;
                        run = true;
                    }

                    // Do not add this circle to the list of good circles
                    run = false;
                }
            }

            if(run){
                foundCircleContainers.add(bestIndex, container);
            }

            run = true;
        }
        
        for (CircleContainer p : foundCircleContainers) {
            foundCircles.add(p.getCircle());
        }
        return foundCircles;
    }
}
/// ---------------------------------------------------------

    public static void main(String[] args) throws IOException{
        try {
            //carrega nova imagem
            BufferedImage imagem = ImageIO.read(new File("teste5.jpg"));
            //instancia um filtro e aplica a escala de cinza
            PID filtro = new PID();
            BufferedImage nova = filtro.generateHough(imagem);
 
            if (nova != null) {
                ImageIO.write(nova,"png",new File("imagem2.jpg"));
                //aqui apenas para demonstração,
                //carreguei novamente as duas imagemns para exibi-las dentro de um JFrame
                imagem = ImageIO.read(new File("teste5.jpg"));
                BufferedImage imagem2 = ImageIO.read(new File("imagem2.jpg"));
                Exibicao show = new Exibicao();
                show.exibirImagem(imagem, imagem2);
                System.out.println("Filtro aplicado com sucesso!");
            }
        }catch(IOException e){
            System.out.println("Erro! Verifique se o arquivo especificado existe e tente novamente.");
        }
        catch(Exception e){
            System.out.println("Erro! " + e.getMessage());
        e.printStackTrace();
        }    
    }
   
}
class ImageManager{
 
    public static final int RED = 0;
 
    public static final int GREEN = 1;
 
    public static final int BLUE = 2;
 
    // ================================== CREATE ARRAYS  ===============================
 
   
 
    public static int[][][] createRGBArrayOfSize(int rows, int columns){
        return new int[rows][columns][3];
    }
 
    public static int [][] createGrayscaleArrayOfSize(int rows, int columns){
       return new int[rows][columns];
    }
 
    public static BufferedImage createBufferedImageOfSize(int width, int height){
        return new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
    }
 
    // ================================== DEEP COPY ARRAYS ==============================
 
   
 
    public static int[][][] deepCopyArray(int[][][] source){
 
        int[][][] result = new int[source.length][source[0].length][source[0][0].length];
 
        for (int r=0; r < source.length; r++){
 
            for (int c=0; c<source[0].length; c++){
 
                for (int z=0; z<source[0][0].length; z++){
 
                    result[r][c][z] = source[r][c][z];
                }
            }    
        }
        return result;
 
    }
 
    public static int[][] deepCopyArray(int[][] source){
 
        int[][] result = new int[source.length][source[0].length];
 
        for (int r=0; r < source.length; r++){
 
            for (int c=0; c<source[0].length; c++){
 
                result[r][c] = source[r][c];
            }
        }
        return result;
     }
  
    static BufferedImage deepCopy(BufferedImage bi) {
 
         ColorModel cm = bi.getColorModel();
 
         boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
 
         WritableRaster raster = bi.copyData(null);
 
         return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
 
    }
 
    // ==================================  COLOR DEPTH CONVERSIONS =====================
 
   
 
    public static int[][] toGrayArray(int[][][] colorArray){
 
        int[][] grays = new int[colorArray.length][colorArray[0].length];
         for (int r = 0; r<colorArray.length; r++){
             for (int c=0; c<colorArray[0].length; c++){
                 grays[r][c] = (colorArray[r][c][0]+colorArray[r][c][1]+colorArray[r][c][2])/3;
            }
        }
         return grays;
    }
 
    public static int[][][] toColorArray(int[][] grayArray){
 
        int [][][] colors = new int[grayArray.length][grayArray[0].length][3];
         for (int r = 0; r<grayArray.length; r++){
             for (int c=0; c<grayArray[0].length; c++){
                 for (int z=0; z<3; z++){
                     colors[r][c][z] = grayArray[r][c];
                }
            }
        }
         return colors;
     }
    // ==================================  LOAD IMAGES AND ARRAYS  ======================
 
   
 
    public static int[][][] RGBArrayFromFile(String filename){
 
        return RGBArrayFromImage(loadImage(filename));
 
    }
 
    public static int[][][] RGBArrayFromFile(File file){
         return RGBArrayFromImage(loadImage(file));
    }
 
    public static int[][] grayscaleArrayFromFile(String filename){
        return grayscaleArrayFromImage(loadImage(filename));
    }
 
    public static int[][] grayscaleArrayFromFile(File file){
         return grayscaleArrayFromImage(loadImage(file));
     }
  
    public static BufferedImage loadImage(String filename){
         File theFile = new File(filename);
         return loadImage(theFile);
    }
 
    public static BufferedImage loadImage(File file){
 
        BufferedImage sourceImage = null;
         try{
            if (file.canRead()){
                sourceImage = ImageIO.read(file);
             }else{
                throw new RuntimeException("Could not open file.");
            }
        }catch (IOException e){
             e.printStackTrace();
         }
        return sourceImage;
    }
 
    // ====================================  IMAGE TO ARRAY ================================
    public static int[][][] RGBArrayFromImage(BufferedImage source){
 
        int [][][] rgbArray = new int [source.getHeight()][source.getWidth()][3];
         System.out.println(source.getHeight()+","+source.getWidth());
         for (int r=0; r<source.getHeight(); r++){
             for (int c=0; c<source.getWidth(); c++){
                 rgbArray[r][c][0] = (source.getRGB(c, r) >> 16)& 255;
                 rgbArray[r][c][1] = (source.getRGB(c, r) >> 8)& 255;
                 rgbArray[r][c][2] = (source.getRGB(c, r) >> 0)& 255;
             }
        }
        return rgbArray;
     }
    
     public static int[][] grayscaleArrayFromImage(BufferedImage source){
 
        int [][] grayArray = new int [source.getHeight()][source.getWidth()];
         for (int r=0; r<source.getHeight(); r++){
             for (int c=0; c<source.getWidth(); c++){
                 grayArray[r][c] = (((source.getRGB(c, r) )& 255) + ((source.getRGB(c, r) >> 8)& 255) + ((source.getRGB(c, r) >> 16)& 255))/3;
            }
        }
         return grayArray;
    }
 
    // =====================================  ARRAY TO IMAGE =============================
  
    public static BufferedImage ImageFromArray(int [][][] inArray){ // RGB version
 
        int width = inArray[0].length;
         int height = inArray.length;
         BufferedImage destination = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
         for (int r=0; r<height; r++){
             for (int c=0; c<width; c++){
                 destination.setRGB(c, r, (inArray[r][c][2])+(inArray[r][c][1]<<8)+(inArray[r][c][0]<<16));
             }
        }
        return destination;
     }
 
    public static BufferedImage ImageFromArray(int [][] inArray){ //grayscale version
 
        int width = inArray[0].length;
 
        int height = inArray.length;
 
        BufferedImage destination = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
 
        for (int r=0; r<height; r++){
 
            for (int c=0; c<width; c++){
 
                destination.setRGB(c, r, (inArray[r][c])+(inArray[r][c]<<8)+(inArray[r][c]<<16));
            }
        }
 
        return destination;
 
    }
 
   
    // ====================================  SAVE ===================================
 
   
 
   
 
    public static void saveImage(int[][][] inArray, String filename) throws IOException{
         saveImage(ImageFromArray(inArray),filename);
     }
 
   
 
    public static void saveImage(int[][] inArray, String filename) throws IOException{
         saveImage(ImageFromArray(inArray),filename);
     }
 
   
 
    public static void saveImage(BufferedImage image, String filename) throws IOException{
 
        System.out.println(filename);
 
        int prev = -1;
 
        while (filename.indexOf(".",prev+1)>-1){
 
            System.out.println(prev);
 
            prev = filename.indexOf(".", prev+1);
 
        }
 
       
 
        if (prev == -1){
 
            throw new RuntimeException("Attempted to save a file with out a suffix.");
        }
 
        String suffix = filename.substring(prev+1).toLowerCase();
 
        if (!suffix.equals("png") && !suffix.equals("jpg") && !suffix.equals("gif") && !suffix.equals("jpeg")){
 
            throw new RuntimeException("Invalid suffix: \""+suffix+"\"");
        }
       
 
        File outputfile = new File(filename);
 
        ImageIO.write(image, suffix, outputfile);
 
    }
}
 
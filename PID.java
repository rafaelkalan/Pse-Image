/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pid;

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

/**
 *
 * @author deboraalmeida
 */

class Exibicao {
    //Exibição 
    //NÃO SERÁ USADO
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
    
    public static void main(String[] args) throws IOException{
        try {
            int x = 50;
            //float x = 1.5f;
            List<Integer> pesos = new ArrayList<Integer>();
            
            pesos.add(2);
            pesos.add(2);
            pesos.add(2);
            pesos.add(2);
            
            pesos.add(2);
            pesos.add(2);
            pesos.add(2);
            pesos.add(2);
            
            pesos.add(2);
            pesos.add(2);
            pesos.add(2);
            pesos.add(2);
            
            pesos.add(2);
            pesos.add(2);
            pesos.add(2);
            pesos.add(2);

            //carrega nova imagem
            BufferedImage imagem = ImageIO.read(new File("lena.jpg"));
            //instancia um filtro e aplica a escala de cinza
            PID filtro = new PID();
            BufferedImage nova = filtro.Brilho(imagem,x);
            if (nova != null) {
                ImageIO.write(nova,"jpg",new File("imagem2.jpg"));
                //aqui apenas para demonstração,
                //carreguei novamente as duas imagemns para exibi-las dentro de um JFrame
                imagem = ImageIO.read(new File("lena.jpg"));
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
        }    
    }
    
}

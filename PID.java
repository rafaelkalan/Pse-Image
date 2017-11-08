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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

/**
 *
 * @author deboraalmeida
 */

class Exibicao {
    //Exibição
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
    //FILTRO
    //método de aplicação do filtro escala de cinza
    //recebe como parâmetro uma imagemm
    public static BufferedImage escalaDeCinza(BufferedImage imagem) {
        //pegar coluna e linha da imagemm
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
                imagem.setRGB(i, j, color.getRGB());
            }
        }
        return imagem;
    }
    public static BufferedImage negativo(BufferedImage imagem) {
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();
        for (int i = 0; i < coluna; i++) {
            for (int j = 0; j < linha; j++) {               
                int rgb = imagem.getRGB(i, j);               
                //a cor inversa é dado por 255 menos o valor da cor                 
                int r = 255 - (int)((rgb&0x00FF0000)>>>16);
                int g = 255 - (int)((rgb&0x0000FF00)>>>8);
                int b = 255 - (int) (rgb&0x000000FF);
                Color color = new Color(r, g, b);
                imagem.setRGB(i, j, color.getRGB());
            }
        }
        return imagem;
    }
    
    public static BufferedImage Media (BufferedImage imagem) {
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
        int [][]mascaraMedia = {{1,1,1},
                                {1,1,1},
                                {1,1,1}};

        int valorMascara = 9;

        int r = 0, g = 0, b = 0;
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();
        
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        
                        int rgb = imagem.getRGB(j + k, i + l);

                        r += (mascaraMedia[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraMedia[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraMedia[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }

                }
                r = r / valorMascara;
                g = g / valorMascara;
                b = b / valorMascara;
                Color tempColor = new Color(r, g, b);
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        ResultImage.getSubimage(1, 1, coluna-1, linha-1);
        return ResultImage;
    }
    
    public static BufferedImage Gaussiano (BufferedImage imagem) {
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
        int [][]mascaraGaussiano = {{1,2,1},
                                {2,4,2},
                                {1,2,1}};
        int valorMascara = 16;

        int r = 0, g = 0, b = 0;
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        
                        int rgb = imagem.getRGB(j + k, i + l);

                        r += (mascaraGaussiano[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraGaussiano[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraGaussiano[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }

                }
                r = r / valorMascara;
                g = g / valorMascara;
                b = b / valorMascara;
                Color tempColor = new Color(r, g, b);
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        ResultImage.getSubimage(1, 1, coluna-1, linha-1);
        return ResultImage;
    }

    public static BufferedImage Laplaciano (BufferedImage imagem) {
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
       
        int [][]mascaraL1 = {{0,-1,0},
                            {-1,4,-1},
                            {0,-1,0}};
        
        int [][]mascaraL2 = {{1,1,1},
                            {1,-8,1},
                            {1,1,1}};

        int r = 0, g = 0, b = 0;
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();
        
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        
                        int rgb = imagem.getRGB(j + k, i + l);

                        r += (mascaraL1[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraL1[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraL1[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }

                }
                
                Color tempColor = new Color(Math.min(255, Math.max(0, r)), Math.min(255, Math.max(0, g)), Math.min(255, Math.max(0, b)));
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
            for (int j = 1; j + 1 < imagem.getWidth(); j++) {
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        
                        int rgb = imagem.getRGB(j + k, i + l);

                        r += (mascaraL2[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraL2[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraL2[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }

                }
                
                Color tempColor = new Color(Math.min(255, Math.max(0, r)), Math.min(255, Math.max(0, g)), Math.min(255, Math.max(0, b)));
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        ResultImage.getSubimage(1, 1, coluna-1, linha-1);
        return ResultImage;
    }
    
    public static BufferedImage Sobel (BufferedImage imagem) {
        BufferedImage ResultImage = new BufferedImage (imagem.getColorModel(),imagem.copyData(null),imagem.getColorModel().isAlphaPremultiplied(),null);
        int [][]mascaraS1 = {{-1,-2,-1},
                             {0,0,0},
                             {1,2,1}};
        int [][]mascaraS2 = {{-1,0,1},
                             {-2,0,2},
                             {-1,0,1}};

        int r = 0, g = 0, b = 0;
        int coluna = imagem.getWidth();
        int linha = imagem.getHeight();
        
        for (int i = 1; i + 1 < linha; i++) {
            for (int j = 1; j + 1 < coluna; j++) {
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        
                        int rgb = imagem.getRGB(j + k, i + l);

                        r += (mascaraS1[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraS1[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraS1[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }

                }
                
                Color tempColor = new Color(Math.min(255, Math.max(0, r)), Math.min(255, Math.max(0, g)), Math.min(255, Math.max(0, b)));
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        for (int i = 1; i + 1 < imagem.getHeight(); i++) {
            for (int j = 1; j + 1 < imagem.getWidth(); j++) {
                for (int l = -1; l <= 1; l++) {
                    for (int k = -1; k <= 1; k++) {
                        
                        int rgb = imagem.getRGB(j + k, i + l);

                        r += (mascaraS2[1 + l][1 + k] * (int)((rgb&0x00FF0000)>>>16));
                        g += (mascaraS2[1 + l][1 + k] * (int)((rgb&0x0000FF00)>>>8));
                        b += (mascaraS2[1 + l][1 + k] * (int)((rgb&0x000000FF)));
                    }

                }
                
                Color tempColor = new Color(Math.min(255, Math.max(0, r)), Math.min(255, Math.max(0, g)), Math.min(255, Math.max(0, b)));
                ResultImage.setRGB(j, i, tempColor.getRGB());
                r = g = b = 0;
            }
        }
        ResultImage.getSubimage(1, 1, coluna-1, linha-1);
        return ResultImage;
    }
    
    public static void main(String[] args) throws IOException{
        try {
            //carrega nova imagem
            BufferedImage imagem = ImageIO.read(new File("lena.jpg"));
            //instancia um filtro e aplica a escala de cinza
            PID filtro = new PID();
            ImageIO.write(filtro.Media(imagem),"jpg",new File("imagem2.jpg"));

            //aqui apenas para demonstração,
            //carreguei novamente as duas imagemns para exibi-las dentro de um JFrame
            imagem = ImageIO.read(new File("lena.jpg"));
            BufferedImage imagem2 = ImageIO.read(new File("imagem2.jpg"));
            Exibicao show = new Exibicao();
            show.exibirImagem(imagem, imagem2);
            System.out.println("Filtro aplicado com sucesso!");
        }catch(IOException e){
            System.out.println("Erro! Verifique se o arquivo especificado existe e tente novamente.");
        }
        catch(Exception e){
            System.out.println("Erro! " + e.getMessage());
        }    
    }
    
}

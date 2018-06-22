/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageProcessing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 *
 * @author Guilherme
 */
public class ImagemZoom extends javax.swing.JFrame {

    int pointsTaken, x1, x2, y1, y2, xmin, xmax, ymin, ymax;
    ArrayList<Ponto> trackerPoints;
    int realImageW, realImageH;
    BufferedImage imageOpened;
    Icon backup;
    ImageProcessor processorZoom;
    AppFrame mainFrame;
    Graphics2D g;
    int pps;

    /**
     * Creates new form ImagemZoom
     */
    public ImagemZoom(AppFrame mainFrame) {
        initComponents();
        trackerPoints = new ArrayList();
        pointsTaken = 0;
        processorZoom = new ImageProcessor(mainFrame);
        this.mainFrame = mainFrame;
    }

    public void recebeImagem(ImageIcon dest, int realImageHeight, int realImageWidht, int xzoom, int yzoom, int ps) {
        try {
            //System.out.println(dest);
            this.setLocationRelativeTo(null);
            //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            this.setSize(xzoom + 75, yzoom + 75);
            image.setSize(xzoom, yzoom);
            dest.setImage(dest.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
            Icon ico = dest;
            backup = dest;
            image.setIcon(ico);
            this.realImageH = realImageHeight;
            this.realImageW = realImageWidht;
            imageOpened = new BufferedImage(
                    image.getIcon().getIconWidth(),
                    image.getIcon().getIconHeight(),
                    BufferedImage.TYPE_3BYTE_BGR);

            g = (Graphics2D) imageOpened.createGraphics();
            // paint the Icon to the BufferedImage.
            dest.paintIcon(null, g, 0, 0);
            pps = ps;
            //g.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Error trying to open the image");
        }
    }

    public void filterAplly(int xini, int xfin, int yini, int yfin, int r, int g, int b) {
        if (pps == 3) {
            xini= xini* (int)((double) image.getHeight() / realImageH);
            xfin= xfin*(int)((double) image.getHeight()/ realImageH);
            yini= yini*(int)((double) image.getWidth() / realImageW);
            yfin= yfin*(int)((double) image.getWidth() / realImageW);
            Mat mat = new Mat(imageOpened.getHeight(), imageOpened.getWidth(), CvType.CV_8UC3);
            byte[] data = ((DataBufferByte) imageOpened.getRaster().getDataBuffer()).getData();
            mat.put(0, 0, data);
            ShowWindow.mostra("Original Image", mat, 0, 0);
            for (int x = 0; x < imageOpened.getHeight(); x++) {
                for (int y = 0; y < imageOpened.getWidth(); y++) {

                    if (x >= xini && x <= xfin && y >= yini && y <= yfin) {
                        //System.out.println("MDS PINTA DE OUTRA COR");
                        double temp[] = mat.get(x, y);
                        //Criterio do FILTRO = 165
                        //se acima do filtro, seta pra true white 255
                        if (temp[0] >= g && temp[1] >= b && temp[2] >= r) {
                            mat.put(x, y, 255, 255, 255);
                            //se não true black 0
                        } else {
                           mat.put(x, y, 0, 0, 0);
                        }
                    } else {
                        mat.put(x, y, 0, 0, 255);
                    }
                }
            }
            ShowWindow.mostra("Calculated Image", mat, 650, 0);
        }
    }
     

    



    public void setAreaUsed(int xfr, int xfn, int yfr, int yfn) {
        if (pps == 2) {
            int xi = (int) (xfr * ((double) image.getWidth() / realImageW));
            int xf = (int) (xfn * ((double) image.getHeight() / realImageH));
            int yi = (int) (yfr * ((double) image.getWidth() / realImageW));
            int yf = (int) (yfn * ((double) image.getHeight() / realImageH));
            g.setColor(Color.red);
            g.setStroke(new BasicStroke(2));
            g.drawLine(xi, yi, xf, yi);
            g.drawLine(xf, yi, xf, yf);
            g.drawLine(xf, yf, xi, yf);
            g.drawLine(xi, yf, xi, yi);
            g.dispose();
            ImageIcon imgIco = new ImageIcon(imageOpened);
            imgIco.setImage(imgIco.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
            Icon ico = imgIco;
            image.setIcon(ico);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        image = new javax.swing.JLabel();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        image.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imageMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imageMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                imageMouseReleased(evt);
            }
        });
        getContentPane().add(image, new java.awt.GridBagConstraints());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void imageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageMouseClicked
        // TODO add your handling code here:
        if (pps == 0) {
            if (pointsTaken < 4) {
                int x = evt.getX();//(int) ((double) evt.getX() * ((double) realImageW / image.getWidth()));
                int y = evt.getY();//(int) ((double) evt.getY() * ((double) realImageH / image.getHeight()));

                Ponto aux = new Ponto(x, y);
                trackerPoints.add(aux);
                pointsTaken++;
            }

            if (pointsTaken >= 4) {
                //Calcula area dentro dos pontos
                Ponto p1, p2, p3, p4;
                ArrayList<Integer> xs = new ArrayList();
                ArrayList<Integer> ys = new ArrayList();
                ArrayList<Ponto> esquerda = new ArrayList();
                ArrayList<Ponto> direita = new ArrayList();
                for (int i = 0; i < trackerPoints.size(); i++) {
                    xs.add(trackerPoints.get(i).getX());
                    ys.add(trackerPoints.get(i).getY());
                }
                Collections.sort(xs);
                Collections.sort(ys);
                int count = 0;
                for (int i = 0; i < trackerPoints.size(); i++) {
                    for (int k = 0; k < trackerPoints.size(); k++) {
                        if (xs.get(i) == trackerPoints.get(k).getX()) {
                            if (count < 2) {
                                esquerda.add(trackerPoints.get(k));
                                count++;
                            } else {
                                direita.add(trackerPoints.get(k));
                            }
                        }
                    }

                }
                if (esquerda.get(0).getY() > esquerda.get(1).getY()) {
                    p4 = esquerda.get(0);
                    p1 = esquerda.get(1);
                } else {
                    p4 = esquerda.get(1);
                    p1 = esquerda.get(0);
                }
                if (direita.get(0).getY() > direita.get(1).getY()) {
                    p3 = direita.get(0);
                    p2 = direita.get(1);
                } else {
                    p3 = direita.get(1);
                    p2 = direita.get(0);
                }
                g.setColor(Color.green);
                g.setStroke(new BasicStroke(2));
                g.drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                g.drawLine(p2.getX(), p2.getY(), p3.getX(), p3.getY());
                g.drawLine(p3.getX(), p3.getY(), p4.getX(), p4.getY());
                g.drawLine(p4.getX(), p4.getY(), p1.getX(), p1.getY());
                g.dispose();
                ImageIcon imgIco = new ImageIcon(imageOpened);
                imgIco.setImage(imgIco.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
                Icon ico = imgIco;
                image.setIcon(ico);

                int choice = JOptionPane.showConfirmDialog(null, "This is the correct area?", "Area confirmation", JOptionPane.YES_NO_OPTION);

                boolean freeToDo = false;
                switch (choice) {
                    case 0:
                        freeToDo = true;
                        this.setVisible(false);

                        p1.setX((int) ((double) p1.getX() * ((double) realImageW / image.getWidth())));
                        p1.setY((int) ((double) p1.getY() * ((double) realImageH / image.getHeight())));
                        p2.setX((int) ((double) p2.getX() * ((double) realImageW / image.getWidth())));
                        p2.setY((int) ((double) p2.getY() * ((double) realImageH / image.getHeight())));
                        p3.setX((int) ((double) p3.getX() * ((double) realImageW / image.getWidth())));
                        p3.setY((int) ((double) p3.getY() * ((double) realImageH / image.getHeight())));
                        p4.setX((int) ((double) p4.getX() * ((double) realImageW / image.getWidth())));
                        p4.setY((int) ((double) p4.getY() * ((double) realImageH / image.getHeight())));

                        int aux;
                        aux = p1.getX();
                        p1.setX(p1.getY());
                        p1.setY(aux);
                        aux = p2.getX();
                        p2.setX(p2.getY());
                        p2.setY(aux);
                        aux = p3.getX();
                        p3.setX(p3.getY());
                        p3.setY(aux);
                        aux = p4.getX();
                        p4.setX(p4.getY());
                        p4.setY(aux);
                        break;
                    case 1:
                        freeToDo = false;
                        image.setIcon(backup);

                        imageOpened = new BufferedImage(
                                image.getIcon().getIconWidth(),
                                image.getIcon().getIconHeight(),
                                BufferedImage.TYPE_3BYTE_BGR);
                        g = (Graphics2D) imageOpened.createGraphics();
                        backup.paintIcon(null, g, 0, 0);

                        break;
                }
                if (freeToDo) {

                    double distmed = Math.sqrt(Math.pow(p2.getY() - p4.getY(), 2) + Math.pow(p2.getX() - p4.getX(), 2));
                    double distp1p2 = Math.sqrt(Math.pow(p2.getY() - p1.getY(), 2) + Math.pow(p2.getX() - p1.getX(), 2));
                    double distp2p3 = Math.sqrt(Math.pow(p3.getY() - p2.getY(), 2) + Math.pow(p3.getX() - p2.getX(), 2));
                    double distp3p4 = Math.sqrt(Math.pow(p4.getY() - p3.getY(), 2) + Math.pow(p4.getX() - p3.getX(), 2));
                    double distp4p1 = Math.sqrt(Math.pow(p4.getY() - p1.getY(), 2) + Math.pow(p4.getX() - p1.getX(), 2));

                    int areaAmostra = (int) (((double) (distp1p2 * distp4p1) / 2.00) + ((double) (distp2p3 * distp3p4) / 2.00));

                    mainFrame.setNPixelsSampleField(areaAmostra);
                    //} 
                    this.setVisible(false);
                }
                pointsTaken = 0;
                trackerPoints.clear();
            }
    }//GEN-LAST:event_imageMouseClicked
    }
    private void imageMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageMouseReleased
        // TODO add your handling code here:
        if (pps == 1 || pps == 2) {
            x2 = evt.getX();
            y2 = evt.getY();
            if (x1 >= x2) {
                xmin = x2;
                xmax = x1;
            } else {
                xmin = x1;
                xmax = x2;
            }
            if (y1 >= y2) {
                ymin = y2;
                ymax = y1;
            } else {
                ymin = y1;
                ymax = y2;
            }

            g.setColor(Color.green);
            g.setStroke(new BasicStroke(2));
            g.drawLine(xmin, ymin, xmin, ymax);
            g.drawLine(xmin, ymax, xmax, ymax);
            g.drawLine(xmax, ymax, xmax, ymin);
            g.drawLine(xmax, ymin, xmin, ymin);
            g.dispose();
            ImageIcon imgIco = new ImageIcon(imageOpened);
            imgIco.setImage(imgIco.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
            Icon ico = imgIco;
            image.setIcon(ico);
            int choice = JOptionPane.showConfirmDialog(null, "This is the correct area?", "Area confirmation", JOptionPane.YES_NO_OPTION);
            boolean freeToDo = false;
            switch (choice) {
                case 0:
                    freeToDo = true;
                    this.setVisible(false);

                    int aux;
                    aux = x1;
                    x1 = y1;
                    y1 = aux;

                    aux = x2;
                    x2 = y2;
                    y2 = aux;

                    x2 = (int) (x2 * (realImageH) / image.getHeight());
                    y2 = (int) (y2 * (realImageW) / image.getWidth());

                    x1 = (int) (x1 * (realImageH) / image.getHeight());
                    y1 = (int) (y1 * (realImageW) / image.getWidth());

                    if (x1 >= x2) {
                        xmin = x2;
                        xmax = x1;
                    } else {
                        xmin = x1;
                        xmax = x2;
                    }
                    if (y1 >= y2) {
                        ymin = y2;
                        ymax = y1;
                    } else {
                        ymin = y1;
                        ymax = y2;
                    }

                    try {
                        if (pps == 1) {
                            mainFrame.confirmArea = true;
                            mainFrame.trackerXy.add(new Coordenada(xmin, xmax, ymin, ymax));
                            double temp[] = processorZoom.getRGBMedia(mainFrame.trackerXy.get(0), mainFrame.directory);
                            mainFrame.trackerXy.clear();
                            mainFrame.r = (int) temp[2];
                            mainFrame.g = (int) temp[0];
                            mainFrame.b = (int) temp[1];

                            mainFrame.setRcolorField("" + (int) temp[2]);
                            mainFrame.setGcolorField("" + (int) temp[0]);
                            mainFrame.setBcolorField("" + (int) temp[1]);
                            mainFrame.getMediaRGB = false;
                            mainFrame.setFilterColor(temp);
                        } else if (pps == 2) {

                            mainFrame.confirmArea = true;
                            mainFrame.setComputeButtonEnable(true);
                            mainFrame.setCoord(ymin + "", ymax + "", xmin + "", xmax + "");

                        }

                    } catch (Exception e) {

                    }
                    this.setVisible(false);
                    break;

                case 1:
                    freeToDo = false;
                    image.setIcon(backup);

                    imageOpened = new BufferedImage(
                            image.getIcon().getIconWidth(),
                            image.getIcon().getIconHeight(),
                            BufferedImage.TYPE_3BYTE_BGR);
                    g = (Graphics2D) imageOpened.createGraphics();
                    backup.paintIcon(null, g, 0, 0);

                    break;
            }
        }
    }//GEN-LAST:event_imageMouseReleased

    private void imageMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imageMousePressed
        // TODO add your handling code here:
        if (pps == 1 || pps == 2) {
            x1 = evt.getX();
            y1 = evt.getY();
        }

    }//GEN-LAST:event_imageMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel image;
    // End of variables declaration//GEN-END:variables

}

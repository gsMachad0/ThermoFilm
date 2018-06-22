/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageProcessing;

import java.util.ArrayList;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author Guilherme
 */
public class ImageProcessor {

    ShowWindow window;
    String dataBaseAdress;
    AppFrame frame;

    public ImageProcessor(AppFrame x) {
        this.dataBaseAdress = "C:\\Users\\Guilherme\\Documents\\NetBeansProjects\\JavaApplication2\\src\\database\\";
        frame = x;
        window = new ShowWindow();
    }

    public void atualizaFiltro(Mat images, int r, int g, int b) {
        int filter[] = new int[3];

        for (int x = 0; x < images.rows(); x++) {
            for (int y = 0; y < images.cols(); y++) {
                double temp[] = images.get(x, y);

                images.put(x, y, 0, 0, 0);

            }
        }
    }

    public String getDataBaseAdress() {
        return dataBaseAdress;
    }

    public void previewCut(int xmin, int xmax, int ymin, int ymax, String directory, int r, int g, int b, boolean justView) {
        //if (xmin >= 0 && xmin <= 320 && ymin >= 0 && ymin <= 240
        //    && xmax >= 0 && xmax <= 320 && ymax >= 0 && ymax <= 240) {

        Mat image = Imgcodecs.imread(directory);

        for (int x = 0; x < image.rows(); x++) {
            for (int y = 0; y < image.cols(); y++) {

                if (x >= xmin && x <= xmax && y >= ymin && y <= ymax) {
                    if (!justView) {
                        double temp[] = image.get(x, y);
                        //Criterio do FILTRO = 165
                        //se acima do filtro, seta pra true white 255
                        if (temp[0] > g && temp[1] > b && temp[2] > r) {
                            image.put(x, y, 255, 255, 255);
                            //se não true black 0
                        } else {
                            image.put(x, y, 0, 0, 0);
                        }
                    }
                } else {
                    image.put(x, y, 0, 0, 255);
                }
            }
        }

        ShowWindow.mostra("Área Selecionada", image, 0, 0);
        //} else {
        //   JOptionPane.showMessageDialog(frame, "Por favor insira valores válidos.");
        //}
    }

    public void computSamples(ArrayList<Coordenada> coords, String directory, int r, int g, int b) {
        Resultado result = new Resultado(coords.size());
        Mat image = new Mat();
        ArrayList<Mat> images = new ArrayList();
        int qtdBrancos[] = new int[coords.size()];
        for (int l = 0; l < coords.size(); l++) {
            image = Imgcodecs.imread(directory);
            images.add(image);
            qtdBrancos[l] = 0;

        }

        for (int k = 0; k < images.size(); k++) {
            int xmin = coords.get(k).getxMin();
            int xmax = coords.get(k).getxMax();
            int ymin = coords.get(k).getyMin();
            int ymax = coords.get(k).getyMax();
            for (int x = 0; x < images.get(k).rows(); x++) {
                for (int y = 0; y < images.get(k).cols(); y++) {

                    if (x >= xmin && x <= xmax && y >= ymin && y <= ymax) {
                        double temp[] = images.get(k).get(x, y);
                        //Criterio do FILTRO = 165
                        //se acima do filtro, seta pra true white 255
                        if (temp[0] >= g && temp[1] >= b && temp[2] >= r) {
                            images.get(k).put(x, y, 255, 255, 255);
                            //se não true black 0
                        } else {
                            images.get(k).put(x, y, 0, 0, 0);
                        }
                    } else {
                        images.get(k).put(x, y, 0, 0, 255);
                    }
                }
            }
        }
        

        //Conta quantida de pixels acima do filtro
        for (int k = 0; k < images.size(); k++) {
            for (int i = 0; i < images.get(k).rows(); i++) {
                for (int j = 0; j < images.get(k).cols(); j++) {
                    double temp[] = images.get(k).get(i, j);
                    if (temp[0] >= g && temp[1] >= b && temp[2] >= r) {
                        qtdBrancos[k]++;
                        

                    }
                }

            }
            frame.setnPixelFiltrado(qtdBrancos[k]);
            frame.setPrcBioSample(Math.abs((double) (((double) qtdBrancos[k] * 100) / (double) frame.getAreaSample()) - 100));
        }

    }

    

    public double[] getRGBMedia(Coordenada coord, String directory) {
        double r = 0;
        double b = 0;
        double g = 0;
        double temp[] = {0, 0, 0};
        Mat image = Imgcodecs.imread(directory);
        for (int x = coord.getxMin(); x <= coord.getxMax(); x++) {
            for (int y = coord.getyMin(); y <= coord.getyMax(); y++) {
                temp = image.get(x, y);
                r += temp[2];
                g += temp[0];
                b += temp[1];
            }
        }
        int total = (coord.getxMax() - coord.getxMin()) * (coord.getyMax() - coord.getyMin());

        r = r / total;
        g = g / total;
        b = b / total;
        if (frame.getSenseOn()) {
            if (!frame.senseDirect) {
                temp[2] = r - frame.getSlider();
                temp[0] = g - frame.getSlider();
                temp[1] = b - frame.getSlider();
            } else {
                temp[2] = r + frame.getSlider();
                temp[0] = g + frame.getSlider();
                temp[1] = b + frame.getSlider();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (temp[i] > 255) {
                temp[i] = 255;
            }
            if (temp[i] < 0) {
                temp[i] = 0;
            }
        }

        return temp;
    }

    public void closePrev() {
        ShowWindow.closeWindow();

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imageProcessing;

/**
 *
 * @author Guilherme
 */
public class Coordenada {
    int xMax;
    int xMin;
    int yMax;
    int yMin;
    
    public Coordenada(int xmin, int xmax, int ymin, int ymax){
        xMax=xmax;
        xMin=xmin;
        yMax=ymax;
        yMin=ymin;
    }
    
    public void setCoord(int xmin, int xmax, int ymin, int ymax){
        this.xMax=xmax;
        this.xMin=xmin;
        this.yMax=ymax;
        this.yMin=ymin;
    }

    public int getxMax() {
        return xMax;
    }

    public int getxMin() {
        return xMin;
    }

    public int getyMax() {
        return yMax;
    }

    public int getyMin() {
        return yMin;
    }
    
}

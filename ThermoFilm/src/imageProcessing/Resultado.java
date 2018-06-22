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
public class Resultado {
    
    int[] numAmostras;
    int[] qtdBranco;
    String prcResult;
    
    double prcDiferenca;
    public Resultado(int nAmostras){
        numAmostras = new int[nAmostras];
        qtdBranco = new int[nAmostras];
        for (int i = 0; i < qtdBranco.length; i++) {
            qtdBranco[i] = 0;
        }
       
    }
    
    public void setResultado(int index, int qtdWhite){
        qtdBranco[index]= qtdWhite;
    }
    
    
}

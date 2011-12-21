/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package si.feri.mui.igra.connectfour;

/**
 *
 * @author Neo101L
 */
public class Polje {
    private int[][] polje = new int[Igra.VRSTICE][Igra.STOLPCI];
    private int[] zadnjaPoteza;
    private int zadnjiIgralec;
    
    public Polje() {
        pobrisi();
    }
    
    public int[][] getPolje() {
        return polje;
    }
    
    public void oznaci(int vrstica, int stolpec, int igralec) {
        polje[vrstica][stolpec] = igralec;
        zadnjiIgralec = igralec;
    }
    
    public void pobrisi() {
        for (int i = 0; i < polje.length; i++) {
            for (int j = 0; j < polje[0].length; j++) {
                polje[i][j] = Igra.PRAZNO;
            }
        }
    }
}

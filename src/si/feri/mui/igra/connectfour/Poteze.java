/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package si.feri.mui.igra.connectfour;

/**
 *
 * @author Neo101L
 */
public class Poteze {
    public int[][] polje;
    public int[] poteza;
    
    public Poteze(int[][] polje, int[] poteza) {
        this.polje = polje;
        this.poteza = poteza;
    }
    
    public void print() {
        Igra.izpisiPolje(polje);
        System.out.println("Poteza: " + poteza[0] + " " + poteza[1]);
    }
    
}

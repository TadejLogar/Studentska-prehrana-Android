/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package si.feri.mui.igra.connectfour;

/**
 *
 * @author Neo101L
 */
public class Poteza {
    public int ocena;
    public int[] poteza;
    
    public Poteza(int ocena, int[] poteza) {
        if (ocena > Igra.INF) ocena = Igra.INF;
        if (ocena < -Igra.INF) ocena = -Igra.INF;
        this.ocena = ocena;
        this.poteza = poteza;
    }

    public void print() {
        System.out.print("ocena: " + ocena);
        if (poteza != null) {
            System.out.print(", poteza: " + (poteza[0] + 1) + " " + (poteza[1] + 1));
        }
        System.out.println();
    }

    public void obrniOceno() {
        ocena = -ocena;
    }
}

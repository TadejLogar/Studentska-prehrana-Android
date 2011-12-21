package si.feri.mui.igra.tictactoe;

public class OcenitvenaFunkcija {
    private int[][] polje;
    
    /*
     * Nastavi polje nad katerem se bo izvedla ocenitvena funkcija
     */
    public OcenitvenaFunkcija(int[][] polje) {
        this.polje = polje;
    }
    
    /*
     * Ocenitvena funkcija za izbranega igralca
     * 
     * Križec +1
     * Krožec -1
     * Neodločeno 0
     * 
     * Vrstice, stolpci, diagonale
     */
    public int oceni(int igralec) {
        //igralec = Igra.MAX;
        
        // igralec na potezi
        int max = ocenitvenaFunkcijaIgralec(igralec);
        if (max > Igra.INF / 2) return Igra.INF;
        
        // nasprotnik
        int min = ocenitvenaFunkcijaIgralec(Igra.nasprotnik(igralec));
        if (min > Igra.INF / 2) return -Igra.INF;
        
        int ocena = max - min;
        
        return ocena;
    }    
    
    private int ocenitvenaFunkcijaIgralec(int igralec) {
        int ocena = 0;
        ocena += ocenitvenaFunkcijaVrtica(0, igralec) + ocenitvenaFunkcijaVrtica(1, igralec) + ocenitvenaFunkcijaVrtica(2, igralec);
        ocena += ocenitvenaFunkcijaStolpec(0, igralec) + ocenitvenaFunkcijaStolpec(1, igralec) + ocenitvenaFunkcijaStolpec(2, igralec);
        ocena += ocenitvenaFunkcijaDiagonala1(igralec) + ocenitvenaFunkcijaDiagonala2(igralec);
        return ocena;
    }
    
    private int zmaga(boolean zmaga) {
        if (zmaga)
            return Igra.INF;
        else
            return 1;
    }
    
    private int ocenitvenaFunkcijaVrtica(int vrstica, int igralec) {
        boolean zmaga = true;
        for (int i = 0; i < Igra.VELIKOST; i++) {
            int vrednost = polje[vrstica][i];
            if (!(vrednost == igralec || vrednost == Igra.PRAZNO)) {
                return 0;
            }
            if (vrednost != igralec) {
                zmaga = false;
            }
        }
        return zmaga(zmaga);
    }
    
    private int ocenitvenaFunkcijaStolpec(int stolpec, int igralec) {
        boolean zmaga = true;
        for (int i = 0; i < Igra.VELIKOST; i++) {
            int vrednost = polje[i][stolpec];
            if (!(vrednost == igralec || vrednost == Igra.PRAZNO)) {
                return 0;
            }
            if (vrednost != igralec) {
                zmaga = false;
            }
        }
        return zmaga(zmaga);
    }
    
    private int ocenitvenaFunkcijaDiagonala1(int igralec) {
        boolean zmaga = true;
        for (int i = 0; i < Igra.VELIKOST; i++) {
            int vrednost = polje[i][i];
            if (!(vrednost == igralec || vrednost == Igra.PRAZNO)) {
                return 0;
            }
            if (vrednost != igralec) {
                zmaga = false;
            }
        }
        return zmaga(zmaga);
    }
    
    private int ocenitvenaFunkcijaDiagonala2(int igralec) {
        boolean zmaga = true;
        for (int i = 0; i < Igra.VELIKOST; i++) {
            int vrednost = polje[i][Igra.VELIKOST - 1 - i];
            if (!(vrednost == igralec || vrednost == Igra.PRAZNO)) {
                return 0;
            }
            if (vrednost != igralec) {
                zmaga = false;
            }            
        }
        return zmaga(zmaga);
    }
}

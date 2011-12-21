/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package si.feri.mui.igra.connectfour;

import java.util.Vector;

/**
 *
 * @author Neo101L
 */
public class OcenitvenaFunkcija {
    private int[][] polje;
    
    private final int NAČIN_VRSTICE = 1;
    private final int NAČIN_STOLPCI = 2;
    private final int NAČIN_DIAGONALE1 = 3;
    private final int NAČIN_DIAGONALE2 = 4;
    
    public boolean izpisi = false;
    
    /*
     * Nastavi polje nad katerem se bo izvedla ocenitvena funkcija
     */
    public OcenitvenaFunkcija(int[][] polje) {
        this.polje = polje;
    }
    
    /*
     * Ocenitvena funkcija za izbranega igralca
     * 
     * Vrstice, stolpci, diagonale
     */
    public int oceni(int igralec) {
        // igralec na potezi
        int max = ocenitvenaFunkcijaIgralec(igralec);
        if (max == Igra.INF) return Igra.INF;
        
        // nasprotnik
        int min = ocenitvenaFunkcijaIgralec(Igra.nasprotnik(igralec));
        if (max == Igra.INF) return -Igra.INF;
        
        int ocena = max - min;
        
        /*System.out.println();
        System.out.println("******ZAČETEK");
        Igra.izpisiPolje(polje);
        System.out.println("Max " + max);
        System.out.println("Min " + min);
        System.out.println("******KONEC");*/
        
        return ocena;
    }

	private int ocenitvenaFunkcijaIgralec(int igralec) {
        int[][] p = Igra.kopiraj(polje);
        int ocena = 0;
        
        int vrstica = ocenitvenaFunkcijaVrstica(igralec, p);
        if (vrstica == Igra.INF) return vrstica;
        
        int stolpec = ocenitvenaFunkcijaStolpec(igralec, p);
        if (stolpec == Igra.INF) return stolpec;
        
        int diagonala1 = ocenitvenaFunkcijaDiagonala1(igralec, p);
        if (diagonala1 == Igra.INF) return diagonala1;
        
        int diagonala2 = ocenitvenaFunkcijaDiagonala2(igralec, p);
        if (diagonala2 == Igra.INF) return diagonala2;
        
        if (izpisi) {
            System.out.println();
            System.out.println("vrstica " + vrstica);
            System.out.println("stolpec " + stolpec);
            System.out.println("diagonala1 " + diagonala1);
            System.out.println("diagonala2 " + diagonala2);
        }
        
        ocena += vrstica;
        ocena += stolpec;
        ocena += diagonala1;
        ocena += diagonala2;
        
        return ocena;
    }
    
    /*
     * int[][] poljeOcenitev: Trenutna matrika
     * int način: Načini (vrstice, stolpci, diagonale)
     * int i: Indeks vrstice
     * int j: Indeks stolpca
     * int k: Povečanje indeksa
     */
    /*private int vrednostMesta(int[][] poljeOcenitev, int način, int i, int j, int k) {
        if (način == NAČIN_VRSTICE) {
            return poljeOcenitev[i][j+k];
        } else if (način == NAČIN_STOLPCI) {
            return poljeOcenitev[i+k][j];
        } else if (način == NAČIN_DIAGONALE1) {
            return poljeOcenitev[i+k][j+k];
        } else if (način == NAČIN_DIAGONALE2) {
            return poljeOcenitev[i+k][j-k];
        }
        return 0;
    }*/
    
    private int ocenitvenaFunkcijaVse(int igralec, int[][] poljeOcenitev, int način) {
        int prvaLength = polje.length;
        int drugaLength = polje[0].length;
        
        if (način == NAČIN_VRSTICE) {
            drugaLength -= 3;
        } else if (način == NAČIN_STOLPCI) {
            prvaLength -= 3;
        } else if (način == NAČIN_DIAGONALE1) {
            prvaLength -= 3;
            drugaLength -= 3;
        }
        
        int tocke = 0;
        for (int i = 0; i < prvaLength; i++) {
            for (int j = 0; j < drugaLength; j++) {
                int trenutneTocke = 0;
                int praznaVrstica = -1;
                int praznaStolpec = -1;
                try {
                    for (int k = 0; k < Igra.DOLZINA; k++) {
                        int vrednostMesta = 0;
                        
                        int a = -1;
                        int b = -1;
                        if (način == NAČIN_VRSTICE) {
                            a = i;
                            b = j + k;
                        } else if (način == NAČIN_STOLPCI) {
                            a = i + k;
                            b = j;
                        } else if (način == NAČIN_DIAGONALE1) {
                            a = i + k;
                            b = j + k;
                        } else if (način == NAČIN_DIAGONALE2) {
                            a = i + k;
                            b = j - k;
                        }

                        if (a >= 0 && a < Igra.VRSTICE && b >= 0 && b < Igra.STOLPCI) {
                            vrednostMesta = poljeOcenitev[a][b];
                        } else {
                            trenutneTocke = 0;
                            break;
                        }
                        
                        if (vrednostMesta == igralec) {
                            trenutneTocke++;
                            // Če je polje označeno pomeni, da smo ga že enkrat šteli (zaradi njega so se povečale točke trenutnega igralca), zato se ga ne šteje še enkrat
                        } else if (vrednostMesta == Igra.OZNAČENO) {
                            trenutneTocke--;
                            //trenutneTocke = 0;
                            //break;
                        } else if (vrednostMesta == Igra.PRAZNO) {
                            praznaVrstica = a;
                            praznaStolpec = b;
                        } else if (vrednostMesta == igralec * -1) { // Če ima je v tem okno nasprotnik, potem tu zmaga ni več mogoča
                            trenutneTocke = 0;
                            break;
                        }
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    trenutneTocke = 0;
                }

                if (trenutneTocke == 1) {
                    //tocke += 1;
                } else if (trenutneTocke == 2) {
                    tocke += 4;
                } else if (trenutneTocke == 3) {
                    tocke += 10;
                    if (praznaVrstica != -1)
                        poljeOcenitev[praznaVrstica][praznaStolpec] = Igra.OZNAČENO;
                } else if (trenutneTocke == 4) {
                	return Igra.INF;
                }
                
            }
        }
        return tocke;
    }
    
    private int ocenitvenaFunkcijaStolpec(int igralec, int[][] polje) {
        return ocenitvenaFunkcijaVse(igralec, polje, NAČIN_STOLPCI);
    }
    
    private int ocenitvenaFunkcijaVrstica(int igralec, int[][] polje) {
        return ocenitvenaFunkcijaVse(igralec, polje, NAČIN_VRSTICE);
    }
    
    private int ocenitvenaFunkcijaDiagonala1(int igralec, int[][] polje) {
        return ocenitvenaFunkcijaVse(igralec, polje, NAČIN_DIAGONALE1);
    }
    
    private int ocenitvenaFunkcijaDiagonala2(int igralec, int[][] polje) {
        return ocenitvenaFunkcijaVse(igralec, polje, NAČIN_DIAGONALE2);
    }
}

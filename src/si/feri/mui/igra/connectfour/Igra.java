/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package si.feri.mui.igra.connectfour;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Neo101L
 */
public class Igra {
    //public static final int VELIKOST = 8;
    public static final int VRSTICE = 6;
    public static final int STOLPCI = 7;
    
    public static final int INF = 1000; // neskončno
    public static final int DOLZINA = 4; // za 4 v vrsto
    
    public static final int PRAZNO = 0;
    public static final int KROŽEC = -1;
    public static final int KRIŽEC = 1;
    public static final int OZNAČENO = 2; // Za ocenitveno funkcijo (da ne šteje enega polja večkrat)
    
    public static int MAX = KRIŽEC;
    public static int MIN = KROŽEC;
    
    protected int[][] polje = new int[VRSTICE][STOLPCI];
    protected int globina;
    protected int igralec;
    
    public static int igralecRačunalnik;
    
    public static int razvrstitev = 0;
    
    public Igra() {
        globina = 0;
        igralec = KROŽEC;
    }
    
    /*
     * Označi
     */
    public void oznaci(int vrstica, int stolpec, int igralec) {
        polje[vrstica][stolpec] = igralec;
    }
    
    public int oznaci(int stolpec, int igralec) {
        for (int i = 0; i < VRSTICE; i++) {
            if ((i != VRSTICE - 1 && polje[i][stolpec] == PRAZNO && polje[i + 1][stolpec] != PRAZNO) || (i == VRSTICE - 1 && polje[i][stolpec] == PRAZNO)) {
                polje[i][stolpec] = igralec;
                return i;
            }
        }
        
        return -1;
    }
    
    /*
     * Neodločen izid
     */
    /*public boolean neodloceno() {
        for (int i = 0; i < polje.length; i++) for (int j = 0; j < polje.length; j++) if (polje[i][j] == PRAZNO) return false;
        return true;
    }*/
    
    public String getIgralecNaPotezi() {
        if (igralec == KROŽEC) {
            return "krožec";
        } else {
            return "križec";
        }
    }
    
    public String igralecStr(int igralec) {
        if (igralec == KROŽEC) {
            return "krožec";
        } else {
            return "križec";
        }
    }    
    
    /*
     * Zamenja igralca na potezi
     */
    public void zamenjajIgralca() {
        if (igralec == KRIŽEC)
            igralec = KROŽEC;
        else
            igralec = KRIŽEC;
    }
    
    /*
     * Izpiše polje trnutnega stanja igre
     */
    public void izpisiPolje() {
        for (int i = 0; i < STOLPCI; i++) {
            System.out.print((i + 1) + " ");
        }
        System.out.println();
        
        for (int i = 0; i < VRSTICE; i++) {
            for (int j = 0; j < STOLPCI; j++) {
                char oznaka = ' ';
                int mesto = polje[i][j];
                if (mesto == PRAZNO) {
                    oznaka = '_';
                } else if (mesto == KROŽEC) {
                    oznaka = 'O';
                } else if (mesto == KRIŽEC) {
                    oznaka = 'X';
                }
                System.out.print(oznaka + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    /*
     * Vrne nasprotnega igralca
     */
    public static int nasprotnik(int igralec) {
        if (igralec == KRIŽEC)
            return KROŽEC;
        else if (igralec == KROŽEC)
            return KRIŽEC;
        else
            return -1; // Napaka
    }
    
    public int oceni(int[][] polje, int igralec) {
        OcenitvenaFunkcija of = new OcenitvenaFunkcija(kopiraj(polje));
        return of.oceni(igralec);
    }
    
    public static int[][] kopiraj(int[][] p) {
        int[][] novo = new int[p.length][p[0].length];
        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p[i].length; j++) {
                novo[i][j] = p[i][j];
            }
        }
        return novo;
    }
    
    public static int[] kopiraj(int[] p) {
        if (p == null) return null;
        int[] novo = new int[p.length];
        for (int i = 0; i < p.length; i++) {
            novo[i] = p[i];
        }
        return novo;
    }
    
    protected int[] naslednjaPozicija(int[] start) {
        if (start[0] == VRSTICE - 1 && start[1] == STOLPCI - 1) return null;

        int[] novo = kopiraj(start);
        novo[1]++;
        if (novo[1] == STOLPCI) {
            novo[0]++;
            novo[1] = 0;
        }
        return novo;
    }
    
    protected boolean jeList(int[][] p) {
        OcenitvenaFunkcija of = new OcenitvenaFunkcija(p); 
        return Math.abs(of.oceni(KRIŽEC)) >= INF || Math.abs(of.oceni(KROŽEC)) >= INF || polnaMatrika(p);
    }
    
    public boolean polnaMatrika(int[][] p) {
        for (int i = 0; i < p.length; i++)
            for (int j = 0; j < p[0].length; j++)
                if (p[i][j] == PRAZNO) return false;
        return true;
    }
    
    public Poteza zmagovalnaPoteza;
    //public int zacetnaGlobina;
    public boolean first = true;
    
    /*
     * Vhodi:
     * P - trenutni položaj
     * ig - igralec na potezi
     * d - preostala globina
     * alfa - spodnja meja ocene
     * beta - zgornja meja ocene
     * 
     * Izhodi:
     * ocena - minimaks vrednost drevesa
     * poteza - najboljša naslednja poteza
     */
    public Poteza negamaksAlfaBeta2(int[][] trenutnoPolje, int igralec, int preostalaGlobina, int alfa, int beta) {
        if (preostalaGlobina == 0 || jeList(trenutnoPolje)) {
            return new Poteza(oceni(trenutnoPolje, igralec), null);
        }
        int ocena = -INF;
        int[] poteza = null;

        // Preveri, ali lahko računalnik zmaga
        Vector<Poteze> seznamPotez = getSeznamPotez(trenutnoPolje, igralec);
        if (first) {
            for (Poteze p : seznamPotez) {
                int ocena0 = oceni(p.polje, igralec);
                if (ocena0 == INF) {
                	return new Poteza(ocena0, p.poteza);
                }
            }
            first = false;
        }
        for (int i = 0; i < seznamPotez.size(); i++) {
            Poteze p = seznamPotez.get(i);
            Poteza r = negamaksAlfaBeta2(kopiraj(p.polje), nasprotnik(igralec), preostalaGlobina - 1, -beta, -alfa);
            r.obrniOceno();
            if (r.ocena > ocena) {
                ocena = r.ocena;
                poteza = kopiraj(p.poteza);
                if (ocena > alfa) {
                    alfa = ocena;
                    if (alfa >= beta) {
                        return new Poteza(ocena, poteza);
                    }
                }
            }
        }
        return new Poteza(ocena, kopiraj(poteza));
    }
    
    protected Poteze naslednjaPoteza(int[][] p, int igralec, int[] start) {
        for (int i = start[0]; i < VRSTICE; i++) {
            for (int j = start[1]; j < STOLPCI; j++) {
                if (p[i][j] == PRAZNO) {
                    int[][] novoPolje = kopiraj(p);
                    novoPolje[i][j] = igralec;
                    return new Poteze(kopiraj(novoPolje), new int[] {i, j});
                }
            }
        }
        return null;
    }
    
    protected Vector<Poteze> getSeznamPotez(int[][] p, int igralec) {
        Vector<Poteze> seznamPotez = new Vector<Poteze>();
        for (int i = 0; i < STOLPCI; i++) {
            for (int j = 0; j < VRSTICE; j++) {
                if ((j != VRSTICE - 1 && p[j][i] == PRAZNO && p[j + 1][i] != PRAZNO) || (j == VRSTICE - 1 && p[j][i] == PRAZNO)) {
                    int[][] novoPolje = kopiraj(p);
                    novoPolje[j][i] = igralec;
                    seznamPotez.add(new Poteze(novoPolje, new int[] {j, i}));
                }
            }
        }
        return seznamPotez;
    }

    public static void izpisiPolje(int[][] trenutniPolozaj) {
        System.out.println("***");
        for (int i = 0; i < trenutniPolozaj.length; i++) {
            for (int j = 0; j < trenutniPolozaj[0].length; j++) {
                char oznaka = ' ';
                if (trenutniPolozaj[i][j] == PRAZNO) {
                    oznaka = '_';
                } else if (trenutniPolozaj[i][j] == KROŽEC) {
                    oznaka = 'O';
                } else if (trenutniPolozaj[i][j] == KRIŽEC) {
                    oznaka = 'X';
                }
                System.out.print(oznaka);
            }
            System.out.println();
        }
        System.out.println();
    }

    public int getGlobina() {
        int n = 0;
        for (int i = 0; i < VRSTICE; i++) for (int j = 0; j < STOLPCI; j++) if (polje[i][j] == PRAZNO) n++;
        return n;
    }
    
    public void preberi(int[] t) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = "";
        try {
            str = br.readLine();
            System.out.println("**" + str);
            String[] str2 = str.split(" ");
            t[0] = Integer.parseInt(str2[0]);
            t[1] = Integer.parseInt(str2[1]);
        } catch (IOException ex) {
            Logger.getLogger(Igra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void preberi1(int[] t) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = "";
        try {
            str = br.readLine();
            System.out.println("**" + str);
            t[0] = Integer.parseInt(str);
        } catch (IOException ex) {
            Logger.getLogger(Igra.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

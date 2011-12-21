package si.feri.mui.igra.tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import si.feri.mui.igra.connectfour.Poteza;
import si.feri.mui.igra.connectfour.Poteze;

/**
 *
 * @author Neo101L
 */
public class Igra {
    public static final int VELIKOST = 3;
    public static final int VRSTICE = 3;
    public static final int STOLPCI = 3;
    public static final int INF = 1000; // neskončno
    
    public static final int PRAZNO = 0;
    public static final int KROŽEC = -1;
    public static final int KRIŽEC = 1;
    
    public static int MAX = KRIŽEC;
    public static int MIN = KROŽEC;
    
    protected int[][] polje = new int[3][3];
    protected int globina;
    protected int igralec;
    
    protected int[] _start;
    
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
    
    /*
     * Preveri zmago igralca
     */
    public boolean preveri(int igralec) {
        // Vrstice
        for (int i = 0; i < polje.length; i++) {
            if (polje[i][0] == igralec && polje[i][1] == igralec && polje[i][2] == igralec) return true;
        }
        
        // Stolpci
        for (int i = 0; i < polje.length; i++) {
            if (polje[0][i] == igralec && polje[1][i] == igralec && polje[2][i] == igralec) return true;
        }        
        
        // Diagonale
        if (polje[0][0] == igralec && polje[1][1] == igralec && polje[2][2] == igralec) return true;
        if (polje[0][2] == igralec && polje[1][1] == igralec && polje[2][0] == igralec) return true;
        
        return false;
    }
    
    /*
     * Neodločen izid
     */
    public boolean neodloceno() {
        for (int i = 0; i < polje.length; i++) for (int j = 0; j < polje.length; j++) if (polje[i][j] == PRAZNO) return false;
        return true;
    }
    
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
        for (int i = 0; i < polje.length; i++) {
            for (int j = 0; j < polje.length; j++) {
                char oznaka = ' ';
                if (polje[i][j] == PRAZNO) {
                    oznaka = '_';
                } else if (polje[i][j] == KROŽEC) {
                    oznaka = 'O';
                } else if (polje[i][j] == KRIŽEC) {
                    oznaka = 'X';
                }
                System.out.print(oznaka);
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
        int[][] novo = new int[p.length][p.length];
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
        if (start[0] == 2 && start[1] == 2) return null;
        int[] novo = kopiraj(start);
        novo[1]++;
        if (novo[1] == 3) {
            novo[0]++;
            novo[1] = 0;
        }
        return novo;
    }
    
    public boolean preveriZmago(int[][] p, int igralec) {
        // Vrstice
        if (p[0][0] == igralec && p[0][1] == igralec && p[0][2] == igralec) return true;
        if (p[1][0] == igralec && p[1][1] == igralec && p[1][2] == igralec) return true;
        if (p[2][0] == igralec && p[2][1] == igralec && p[2][2] == igralec) return true;
        
        // Stolpci
        if (p[0][0] == igralec && p[1][0] == igralec && p[2][0] == igralec) return true;
        if (p[0][1] == igralec && p[1][1] == igralec && p[2][1] == igralec) return true;
        if (p[0][2] == igralec && p[1][2] == igralec && p[2][2] == igralec) return true;
        
        // Diagonale
        if (p[0][0] == igralec && p[1][1] == igralec && p[2][2] == igralec) return true;
        if (p[0][2] == igralec && p[1][1] == igralec && p[2][0] == igralec) return true;
        
        return false;
    }
    
    protected boolean jeList(int[][] p) {
        boolean list = preveriZmago(p, KROŽEC) || preveriZmago(p, KRIŽEC) || neodloceno(p);
        return list;
    }
    
    public boolean neodloceno(int[][] p) {
        for (int i = 0; i < p.length; i++) for (int j = 0; j < p.length; j++) if (p[i][j] == PRAZNO) return false;
        return true;
    }
    
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
        if (jeList(trenutnoPolje) || preostalaGlobina == 0) {
            return new Poteza(oceni(trenutnoPolje, igralec), null);
        }
        int ocena = -INF;
        int[] poteza = null;

        Vector<Poteze> seznamPotez = getSeznamPotez(trenutnoPolje, igralec);
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
        for (int i = start[0]; i < VELIKOST; i++) {
            for (int j = start[1]; j < VELIKOST; j++) {
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
        for (int i = 0; i < VELIKOST; i++) {
            for (int j = 0; j < VELIKOST; j++) {
                if (p[i][j] == PRAZNO) {
                    int[][] novoPolje = kopiraj(p);
                    novoPolje[i][j] = igralec;
                    seznamPotez.add(new Poteze(novoPolje, new int[] {i, j}));
                }
            }
        }
        return seznamPotez;
    }

    public static void izpisiPolje(int[][] trenutniPolozaj) {
        System.out.println("***");
        for (int i = 0; i < trenutniPolozaj.length; i++) {
            for (int j = 0; j < trenutniPolozaj.length; j++) {
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
        for (int i = 0; i < VELIKOST; i++) for (int j = 0; j < VELIKOST; j++) if (polje[i][j] == PRAZNO) n++;
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
}


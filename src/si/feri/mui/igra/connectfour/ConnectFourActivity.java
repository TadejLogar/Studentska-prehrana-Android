package si.feri.mui.igra.connectfour;

import si.feri.projekt.studentskaprehrana.R;
import si.feri.projekt.studentskaprehrana.activity.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class ConnectFourActivity extends MainActivity {
	public int pozicijaStolpec = -1;
	ImageButton[][] matrika;
	
	int vrstice = 6;
	int stolpci = 7;
	int igralecNaPotezi = 0;
	
	TextView msg;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        msg = (TextView) findViewById(R.id.msg);
        start();
    }
    
    public void novaIgraClick(View v) {
        finish();
    	Intent i = new Intent(this, si.feri.mui.igra.connectfour.ConnectFourActivity.class);
    	startActivity(i);
    }
    
    public void novaIgraKKClick(View v) {
        finish();
    	Intent i = new Intent(this, si.feri.mui.igra.tictactoe.TicTacToeActivity.class);
    	startActivity(i);
    }
     
    public void start() {
    	setContentView(R.layout.game_matrix_layout);
        matrika = new ImageButton[vrstice][stolpci];
        
        TableLayout tlMatrika = (TableLayout) findViewById(R.id.tableLayoutMatrika);
        for (int i = 0; i < vrstice; i++) {
	        TableRow tr = new TableRow(this);
	        tlMatrika.addView(tr);
	        for (int j = 0; j < stolpci; j++) {
	        	final int vrstica = i;
	        	final int stolpec = j;
	        	final ImageButton ib = new ImageButton(this);
	            ib.setAdjustViewBounds(true);
	            ib.setImageResource(R.drawable.blank);
	            ib.setMaxHeight(60);
	            ib.setScaleType(ScaleType.CENTER_INSIDE);
	            
	            matrika[i][j] = ib;
	            
	        	ib.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						pozicijaStolpec = stolpec;
						//Toast.makeText(Main.this, vrstica + " " + stolpec, Toast.LENGTH_LONG).show();
					}
				});
	        	tr.addView(ib);
	        }
        }
        
        NIgra ni = new NIgra(this, matrika, mHandler, msgHandler);
        ni.start();
    }
    
    Handler mHandler = new Handler() {
    	public void handleMessage(Message msg) {
    		Element e = (Element) msg.obj;
    		int v = e.vrstica;
    		int s = e.stolpec;
    		if (e.vrsta == Igra.KROŽEC) {
    			matrika[v][s].setImageResource(R.drawable.o);
    		} else {
    			matrika[v][s].setImageResource(R.drawable.x);
    		}
    	}
	};
	
    Handler msgHandler = new Handler() {
    	public void handleMessage(Message m) {
    		String str = (String) m.obj;
    		msg = (TextView) findViewById(R.id.msg);
    		msg.setText(str);
    	}
	};
}



 class NIgra extends Thread {
	 private ConnectFourActivity main;
	 ImageButton[][] matrika;
	 Handler mHandler;
	 Handler msgHandler;
	 
	 public NIgra(ConnectFourActivity main, ImageButton[][] matrika, Handler mHandler, Handler msgHandler) {
		 this.main = main;
		 this.matrika = matrika;
		 this.mHandler = mHandler;
		 this.msgHandler = msgHandler;
	 }
	 
	 private void setText(String str) {
         Message msg = new Message();
         msg.obj = str;
         msgHandler.sendMessage(msg);
	 }
	 
	 public void run() {
        Igra igra = new Igra();
        igra.MAX = Igra.KROŽEC;
        igra.MIN = Igra.KRIŽEC;

        int igralec = Igra.KROŽEC;
        Igra.igralecRačunalnik = igralec;
        //igra.zacetnaGlobina = 2;
        
        int prvi = 1; // prvi na potezi, 1 = človek
        
        for (int i = 0; ; i++) {
        	
        	// Človek
        	if (i > 0 || (i == 0 && prvi == 1)) {
	            setText("Si na potezi");
	            while (main.pozicijaStolpec == -1) {
	            	try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }

	            int vrstica = igra.oznaci(main.pozicijaStolpec, Igra.KRIŽEC);
	            
	            if (vrstica != -1) {
		            Message msg2 = new Message();
		            msg2.obj = new Element(vrstica, main.pozicijaStolpec, Igra.KRIŽEC);
		            mHandler.sendMessage(msg2);
	            }
	            
	            main.pozicijaStolpec = -1;
	            igra.izpisiPolje();
	            
	            if (igra.polnaMatrika(igra.polje)) {
	            	setText("Neodločeno");
	            	return;
	            } else if (igra.oceni(igra.polje, Igra.KRIŽEC) == Igra.INF) {
	            	setText("Zmagal si");
	            	return;
	            }
        	}


        	// Računalnik
        	setText("Na potezi je računalnik");
            int globina = 2;
            igra.first = true;
            Poteza p = null;
            if (i == 0 && prvi != 1) {
            	p = new Poteza(0, new int[] { igra.VRSTICE - 1, igra.STOLPCI / 2});
            } else {
            	p = igra.negamaksAlfaBeta2(igra.polje, igralec, globina, -igra.INF, igra.INF);
            }
            igra.oznaci(p.poteza[0], p.poteza[1], igralec);
            Message msg = new Message();
            msg.obj = new Element(p.poteza[0], p.poteza[1], igralec);
            mHandler.sendMessage(msg);
            System.out.println(igra.igralecStr(igralec) + ", vrstica: " + (p.poteza[0] + 1) + ", stolpec: " + (p.poteza[1] + 1));
            igra.izpisiPolje();
            int ocena = igra.oceni(igra.polje, igralec);
            if (igra.polnaMatrika(igra.polje)) {
            	setText("Neodločeno");
            	return;            	
            } else if (ocena == Igra.INF) {
            	setText("Zmagal je računalnik");
            	return;
            }

        }
    }
}
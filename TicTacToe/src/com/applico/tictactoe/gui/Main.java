package com.applico.tictactoe.gui;



import java.util.Random;

import com.applico.tictactoe.R;

import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends ActionBarActivity {

	public Button[][] tile = new Button[3][3];
	public Button btn_close,btn_new;
	public TextView txt_turn;
	int[][] tile_val = new int[3][3];
	int turn;
	private Handler mHandler = new Handler();
	
	/*private static Main instance = new Main( );

	private Main(){
		 
	}
	   
	/* Static 'instance' method 
	public static Main getInstance( ) {
		return instance;
	}*/

	   
	   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		initializeButtons();
		initializeTileValues();
		turn=0;
		txt_turn=(TextView)findViewById(R.id.txt_turn);
		
		Toast.makeText(Main.this,"Begin! You are cross (X).", Toast.LENGTH_SHORT).show();
		
		tile[0][0].setOnClickListener(new OnClickListener() {
			 
		     @Override
		     public void onClick(View v) {
		    	 if(tile_val[0][0]==-1)
		    	 {
		    		 tile_val[0][0]=1;
		    		 tile[0][0].setText("X");
		    		 tile[0][0].setEnabled(false);
		    		 turn++;
		    		 disableAllButtons();
		    		 txt_turn.setText("AI Turn");
		    		 checkProgress();
		    	 }
		     }
		});
		
		tile[0][1].setOnClickListener(new OnClickListener() {
			 
		     @Override
		     public void onClick(View v) {
		    	 if(tile_val[0][1]==-1)
		    	 {
		    		 tile_val[0][1]=1;
		    		 tile[0][1].setText("X");
		    		 tile[0][1].setEnabled(false);
		    		 turn++;
		    		 disableAllButtons();
		    		 txt_turn.setText("AI Turn");
		    		 checkProgress();
		    	 }
		     }
		});
		
		tile[0][2].setOnClickListener(new OnClickListener() {
			 
		     @Override
		     public void onClick(View v) {
		    	 if(tile_val[0][2]==-1)
		    	 {
		    		 tile_val[0][2]=1;
		    		 tile[0][2].setText("X");
		    		 tile[0][2].setEnabled(false);
		    		 turn++;
		    		 disableAllButtons();
		    		 txt_turn.setText("AI Turn");
		    		 checkProgress();
		    	 }
		     }
		});
		
		tile[1][0].setOnClickListener(new OnClickListener() {
			 
		     @Override
		     public void onClick(View v) {
		    	 if(tile_val[1][0]==-1)
		    	 {
		    		 tile_val[1][0]=1;
		    		 tile[1][0].setText("X");
		    		 tile[1][0].setEnabled(false);
		    		 turn++;
		    		 disableAllButtons();
		    		 txt_turn.setText("AI Turn");
		    		 checkProgress();
		    	 }
		     }
		});
		
		tile[1][1].setOnClickListener(new OnClickListener() {
			 
		     @Override
		     public void onClick(View v) {
		    	 if(tile_val[1][1]==-1)
		    	 {
		    		 tile_val[1][1]=1;
		    		 tile[1][1].setText("X");
		    		 tile[1][1].setEnabled(false);
		    		 turn++;
		    		 disableAllButtons();
		    		 txt_turn.setText("AI Turn");
		    		 checkProgress();
		    	 }
		     }
		});
		
		tile[1][2].setOnClickListener(new OnClickListener() {
			 
		     @Override
		     public void onClick(View v) {
		    	 if(tile_val[1][2]==-1)
		    	 {
		    		 tile_val[1][2]=1;
		    		 tile[1][2].setText("X");
		    		 tile[1][2].setEnabled(false);
		    		 turn++;
		    		 disableAllButtons();
		    		 txt_turn.setText("AI Turn");
		    		 checkProgress();
		    	 }
		     }
		});
		
		tile[2][0].setOnClickListener(new OnClickListener() {
			 
		     @Override
		     public void onClick(View v) {
		    	 if(tile_val[2][0]==-1)
		    	 {
		    		 tile_val[2][0]=1;
		    		 tile[2][0].setText("X");
		    		 tile[2][0].setEnabled(false);
		    		 turn++;
		    		 disableAllButtons();
		    		 txt_turn.setText("AI Turn");
		    		 checkProgress();
		    	 }
		     }
		});
		
		tile[2][1].setOnClickListener(new OnClickListener() {
			 
		     @Override
		     public void onClick(View v) {
		    	 if(tile_val[2][1]==-1)
		    	 {
		    		 tile_val[2][1]=1;
		    		 tile[2][1].setText("X");
		    		 tile[2][1].setEnabled(false);
		    		 turn++;
		    		 disableAllButtons();
		    		 txt_turn.setText("AI Turn");
		    		 checkProgress();
		    	 }
		     }
		});
		
		tile[2][2].setOnClickListener(new OnClickListener() {
			 
		     @Override
		     public void onClick(View v) {
		    	 if(tile_val[2][2]==-1)
		    	 {
		    		 tile_val[2][2]=1;
		    		 tile[2][2].setText("X");
		    		 tile[2][2].setEnabled(false);
		    		 turn++;
		    		 disableAllButtons();
		    		 txt_turn.setText("AI Turn");
		    		 checkProgress();
		    	 }
		     }
		});
		
		btn_new.setOnClickListener(new OnClickListener() {
			 
		     @Override
		     public void onClick(View v) {
		    	 refreshAllButtons();
		    	 turn=0;
		     }
		});
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void checkProgress()
	{
		boolean result=false;
		for(int i=0;i<3;i++)
		{
			int count=0;
			for(int j=0;j<3;j++)
			{
				if(tile_val[i][j]==1)
					count++;
				else if(tile_val[i][j]==0)
					count--;
			}
			if(count==3)
			{
				result=true;
				disableAllButtons();
				Toast.makeText(Main.this,"Congratulations! You won!", Toast.LENGTH_SHORT).show();
				txt_turn.setText("Congratulations! You won!");
			}
			else if(count==-3)
			{
				result=true;
				disableAllButtons();
				Toast.makeText(Main.this,"Nice try! You lose :(", Toast.LENGTH_SHORT).show();
				txt_turn.setText("Nice try! You lose :(");
			}
		}
		for(int j=0;j<3;j++)
		{
			int count=0;
			for(int i=0;i<3;i++)
			{
				if(tile_val[i][j]==1)
					count++;
				else if(tile_val[i][j]==0)
					count--;
			}
			if(count==3)
			{
				result=true;
				disableAllButtons();
				Toast.makeText(Main.this,"Congratulations! You won!", Toast.LENGTH_SHORT).show();
				txt_turn.setText("Congratulations! You won!");
			}
			else if(count==-3)
			{
				result=true;
				disableAllButtons();
				Toast.makeText(Main.this,"Nice try! You lose :(", Toast.LENGTH_SHORT).show();
				txt_turn.setText("Nice try! You lose :(");
			}
		}
		if(tile_val[0][0]==1 && tile_val[1][1]==1 && tile_val[2][2]==1)
		{
			result=true;
			disableAllButtons();
			Toast.makeText(Main.this,"Congratulations! You won!", Toast.LENGTH_SHORT).show();
			txt_turn.setText("Congratulations! You won!");
		}
		else if(tile_val[0][0]==0 && tile_val[1][1]==0 && tile_val[2][2]==0)
		{
			result=true;
			disableAllButtons();
			Toast.makeText(Main.this,"Nice try! You lose :(", Toast.LENGTH_SHORT).show();
			txt_turn.setText("Nice try! You lose :(");
		}
		
		if(!result && turn==9)
		{
			Toast.makeText(Main.this,"Draw! Good game.", Toast.LENGTH_SHORT).show();
			txt_turn.setText("Draw! Good game.");
		}
		nextAITurn();
	}
	public void nextAITurn()
	{
		
		Random rand = new Random();
		if(turn%2==1 && turn!=9)
		{
			mHandler.postDelayed(new Runnable() {
	            public void run() {
	            	Random rand = new Random();
	            	boolean move=false;
	            	do
	    			{
	    				
	    				int x = rand.nextInt(3);
	    				int y = rand.nextInt(3);
	    				if(tile_val[x][y]==-1)
	    				{
	    					move=true;
	    					tile_val[x][y]=0;
	    					tile[x][y].setText("O");
	    		    		tile[x][y].setEnabled(false);
	    		    		turn++;
	    		    		txt_turn.setText("Your Turn");
	    		    		enableButtons();
	    		    		
	    		    		checkProgress();
	    				}
	    			}while(move==false);
	            }
	        }, (rand.nextInt(2)+1)*1000);
			
		}
	}
	
	
	public void initializeButtons()
	{
		tile[0][0]=(Button) findViewById(R.id.btn_00);
		tile[0][1]=(Button) findViewById(R.id.btn_01);
		tile[0][2]=(Button) findViewById(R.id.btn_02);
		tile[1][0]=(Button) findViewById(R.id.btn_10);
		tile[1][1]=(Button) findViewById(R.id.btn_11);
		tile[1][2]=(Button) findViewById(R.id.btn_12);
		tile[2][0]=(Button) findViewById(R.id.btn_20);
		tile[2][1]=(Button) findViewById(R.id.btn_21);
		tile[2][2]=(Button) findViewById(R.id.btn_22);
		btn_new=(Button) findViewById(R.id.btn_new);
		btn_close=(Button) findViewById(R.id.btn_close);
	}
	
	public void refreshAllButtons()
	{
		enableAllButtons();
		
		tile[0][0].setText("");
		tile[0][1].setText("");
		tile[0][2].setText("");
		tile[1][0].setText("");
		tile[1][1].setText("");
		tile[1][2].setText("");
		tile[2][0].setText("");
		tile[2][1].setText("");
		tile[2][2].setText("");
		initializeTileValues();
	}
	
	public void enableAllButtons()
	{
		tile[0][0].setEnabled(true);
		tile[0][1].setEnabled(true);
		tile[0][2].setEnabled(true);
		tile[1][0].setEnabled(true);
		tile[1][1].setEnabled(true);
		tile[1][2].setEnabled(true);
		tile[2][0].setEnabled(true);
		tile[2][1].setEnabled(true);
		tile[2][2].setEnabled(true);
	}
	
	public void enableButtons()
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(tile_val[i][j]==-1)
					tile[i][j].setEnabled(true);
			}
		}
	}
	
	public void disableAllButtons()
	{
		tile[0][0].setEnabled(false);
		tile[0][1].setEnabled(false);
		tile[0][2].setEnabled(false);
		tile[1][0].setEnabled(false);
		tile[1][1].setEnabled(false);
		tile[1][2].setEnabled(false);
		tile[2][0].setEnabled(false);
		tile[2][1].setEnabled(false);
		tile[2][2].setEnabled(false);
	}
	
	/*Main.this.runOnUiThread(new Runnable() {
        public void run() {
            AlertDialog.Builder builder = new AlertDialog.Builder(Main.this,R.style.AlertDialogTheme);
            builder.setTitle("Username Missing");
            builder.setMessage("Please enter your username")  
                   .setCancelable(false)
                   .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                    	   
                       }
                   });                     
            AlertDialog alert = builder.create();
            alert.show();               
        }
        
    });*/
	public void initializeTileValues()
	{
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				tile_val[i][j]=-1;
	}
}

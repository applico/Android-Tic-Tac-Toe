package com.applico.tictactoe.gui;



import java.util.Random;

import com.applico.tictactoe.R;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Color;
import android.graphics.Typeface;
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
	int[][] tile_val_temp = new int[3][3];
	Typeface typeFace;
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
		typeFace=Typeface.createFromAsset(getAssets(),"fonts/DHF Story Brush.ttf");
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
		
		
		btn_close.setOnClickListener(new OnClickListener() {
			 
		     @Override
		     public void onClick(View v) {
		    	finish();
		 		System.exit(0);
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
		int winner=0;
		winner=findWinner();
		if(winner==-1 && turn<9)
		{
			mHandler.postDelayed(new Runnable() {
	            public void run() {
	            	nextAITurn(tile_val);
	            }
	        }, 2000);
			
		}
	}
	private int findWinner() {
		boolean result=false;
		int winner=-1;
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
				tile[i][0].setTextColor(Color.parseColor("#57d5ff"));
				tile[i][1].setTextColor(Color.parseColor("#57d5ff")); 
				tile[i][2].setTextColor(Color.parseColor("#57d5ff")); 
				disableAllButtons();
				Toast.makeText(Main.this,"Congratulations! You won!", Toast.LENGTH_SHORT).show();
				txt_turn.setText("Congratulations! You won!");
				winner=1;
			}
			else if(count==-3)
			{
				result=true;
				tile[i][0].setTextColor(Color.parseColor("#ff5757"));
				tile[i][1].setTextColor(Color.parseColor("#ff5757")); 
				tile[i][2].setTextColor(Color.parseColor("#ff5757")); 
				disableAllButtons();
				Toast.makeText(Main.this,"Nice try! You lose :(", Toast.LENGTH_SHORT).show();
				txt_turn.setText("Nice try! You lose :(");
				winner=0;
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
				tile[0][j].setTextColor(Color.parseColor("#57d5ff"));
				tile[1][j].setTextColor(Color.parseColor("#57d5ff")); 
				tile[2][j].setTextColor(Color.parseColor("#57d5ff")); 
				disableAllButtons();
				Toast.makeText(Main.this,"Congratulations! You won!", Toast.LENGTH_SHORT).show();
				txt_turn.setText("Congratulations! You won!");
				winner=1;
			}
			else if(count==-3)
			{
				result=true;
				tile[0][j].setTextColor(Color.parseColor("#ff5757"));
				tile[1][j].setTextColor(Color.parseColor("#ff5757")); 
				tile[2][j].setTextColor(Color.parseColor("#ff5757"));
				disableAllButtons();
				Toast.makeText(Main.this,"Nice try! You lose :(", Toast.LENGTH_SHORT).show();
				txt_turn.setText("Nice try! You lose :(");
				winner=0;
			}
		}
		if(tile_val[0][0]==1 && tile_val[1][1]==1 && tile_val[2][2]==1)
		{
			result=true;
			tile[0][0].setTextColor(Color.parseColor("#57d5ff"));
			tile[1][1].setTextColor(Color.parseColor("#57d5ff")); 
			tile[2][2].setTextColor(Color.parseColor("#57d5ff")); 
			disableAllButtons();
			Toast.makeText(Main.this,"Congratulations! You won!", Toast.LENGTH_SHORT).show();
			txt_turn.setText("Congratulations! You won!");
			winner=1;
		}
		else if(tile_val[0][0]==0 && tile_val[1][1]==0 && tile_val[2][2]==0)
		{
			result=true;
			tile[0][0].setTextColor(Color.parseColor("#ff5757"));
			tile[1][1].setTextColor(Color.parseColor("#ff5757")); 
			tile[2][2].setTextColor(Color.parseColor("#ff5757"));
			disableAllButtons();
			Toast.makeText(Main.this,"Nice try! You lose :(", Toast.LENGTH_SHORT).show();
			txt_turn.setText("Nice try! You lose :(");
			winner=0;
		}
		
		if(tile_val[0][2]==1 && tile_val[1][1]==1 && tile_val[2][0]==1)
		{
			result=true;
			tile[0][2].setTextColor(Color.parseColor("#57d5ff"));
			tile[1][1].setTextColor(Color.parseColor("#57d5ff")); 
			tile[2][0].setTextColor(Color.parseColor("#57d5ff")); 
			disableAllButtons();
			Toast.makeText(Main.this,"Congratulations! You won!", Toast.LENGTH_SHORT).show();
			txt_turn.setText("Congratulations! You won!");
			winner=1;
		}
		else if(tile_val[0][2]==0 && tile_val[1][1]==0 && tile_val[2][0]==0)
		{
			result=true;
			tile[0][2].setTextColor(Color.parseColor("#ff5757"));
			tile[1][1].setTextColor(Color.parseColor("#ff5757")); 
			tile[2][0].setTextColor(Color.parseColor("#ff5757"));
			disableAllButtons();
			Toast.makeText(Main.this,"Nice try! You lose :(", Toast.LENGTH_SHORT).show();
			txt_turn.setText("Nice try! You lose :(");
			winner=0;
		}
		
		if(!result && turn==9)
		{
			Toast.makeText(Main.this,"Draw! Good game.", Toast.LENGTH_SHORT).show();
			txt_turn.setText("Draw! Good game.");
		}
		
		return winner;
	}
	
	public int checkWin()
	{
		boolean result=false;
		int winner=-1;
		for(int i=0;i<3;i++)
		{
			int count=0;
			for(int j=0;j<3;j++)
			{
				if(tile_val_temp[i][j]==1)
					count++;
				else if(tile_val_temp[i][j]==0)
					count--;
			}
			if(count==3)
			{
				result=true;
				winner=1;
			}
			else if(count==-3)
			{
				result=true;
				winner=0;
			}
		}
		for(int j=0;j<3;j++)
		{
			int count=0;
			for(int i=0;i<3;i++)
			{
				if(tile_val_temp[i][j]==1)
					count++;
				else if(tile_val_temp[i][j]==0)
					count--;
			}
			if(count==3)
			{
				result=true;
				winner=1;
			}
			else if(count==-3)
			{
				result=true;
				winner=0;
			}
		}
		if(tile_val_temp[0][0]==1 && tile_val_temp[1][1]==1 && tile_val_temp[2][2]==1)
		{
			result=true;
			winner=1;
		}
		else if(tile_val_temp[0][0]==0 && tile_val_temp[1][1]==0 && tile_val_temp[2][2]==0)
		{
			result=true;
			winner=0;
		}
		
		if(tile_val_temp[0][2]==1 && tile_val_temp[1][1]==1 && tile_val_temp[2][0]==1)
		{
			result=true;
			winner=1;
		}
		else if(tile_val_temp[0][2]==0 && tile_val_temp[1][1]==0 && tile_val_temp[2][0]==0)
		{
			result=true;
			winner=0;
		}
		
		if(!result && turn==9)
		{
			winner=-1;
		}
		
		return winner;
	}


	public void nextAITurn(int[][] tiles_tval)
	{
		for(int i=0; i<tile_val.length; i++)
			  for(int j=0; j<tile_val[i].length; j++)
			    tiles_tval[i][j]=tile_val[i][j];
		
		Random rand = new Random();
		if(turn%2==1 && turn!=9)
		{
			int winner = findWinner();
			if(winner==-1 && turn < 9)
			{
				
				for(int i=0;i<3;i++)
				{
					for(int j=0;j<3;j++)
					{
						if(tile_val[i][j]==-1 && turn%2==1)
			            {
			            	for(int a=0; a<3; a++)
			      			  for(int b=0; b<3; b++)
			      				tile_val_temp[a][b]=tile_val[a][b];
			            	tile_val_temp[i][j]=0;
			            	if(checkWin()==0)
			            	{
			            		tile_val[i][j]=0;
			            		tile[i][j].setText("O");
		    		    		tile[i][j].setEnabled(false);
		    		    		turn++;
		    		    		txt_turn.setText("Your Turn");
		    		    		enableButtons();
		    		    		checkProgress();
			            	}
			            }
					}
					
				}
				
				for(int i=0;i<3;i++)
				{
					for(int j=0;j<3;j++)
					{
						if(tile_val[i][j]==-1 && turn%2==1)
			            {
			            	for(int a=0; a<3; a++)
			      			  for(int b=0; b<3; b++)
			      				tile_val_temp[a][b]=tile_val[a][b];
			            	tile_val_temp[i][j]=1;
			            	if(checkWin()==1)
			            	{
			            		tile_val[i][j]=0;
			            		tile[i][j].setText("O");
		    		    		tile[i][j].setEnabled(false);
		    		    		turn++;
		    		    		txt_turn.setText("Your Turn");
		    		    		enableButtons();
		    		    		checkProgress();
			            	}
			            }
					}
					
				}
				
				
				/*for(int i=0;i<3;i++)
				{
					for(int j=0;j<3;j++)
					{
						int row = 3;
						int col = 3;
						
						int index = i - 1;
					    for (int k = -1; k < 2; k++) {
					        if (index % row > 0 && ((j + k)  % col) > 0) {
					            if(tile_val[index][j + k]==-1 && turn%2==1)
					            {
					            	for(int a=0; a<3; a++)
					      			  for(int b=0; b<3; b++)
					      				tile_val_temp[a][b]=tile_val[a][b];
					            	tile_val_temp[index][j + k]=1;
					            	if(checkWin()==1)
					            	{
					            		tile_val[index][j + k]=0;
					            		tile[index][j + k].setText("O");
				    		    		tile[index][j + k].setEnabled(false);
				    		    		turn++;
				    		    		txt_turn.setText("Your Turn");
				    		    		enableButtons();
				    		    		checkProgress();
					            	}
					            }
					        }
					    }


					    // In the current Column
					    index = i;

					    // Increment is 2 as we don't want (i, j)
					    for (int k = -1; k < 2;k++) {            
					        if (index % row > 0 && ((j + k)  % col) > 0) {
					        	if(tile_val[index][j + k]==-1 && turn%2==1)
					            {
					            	for(int a=0; a<3; a++)
					      			  for(int b=0; b<3; b++)
					      				tile_val_temp[a][b]=tile_val[a][b];
					            	tile_val_temp[index][j + k]=1;
					            	if(checkWin()==1)
					            	{
					            		tile_val[index][j + k]=0;
					            		tile[index][j + k].setText("O");
				    		    		tile[index][j + k].setEnabled(false);
				    		    		turn++;
				    		    		txt_turn.setText("Your Turn");
				    		    		enableButtons();
				    		    		checkProgress();
					            	}
					            }
					        }
					    }

					    // To the right of current Column
					    index = i + 1;
					    for (int k = -1; k < 2; k++) {
					        if (index % row > 0 && ((j + k)  % col) > 0) {
					        	if(tile_val[index][j + k]==-1 && turn%2==1)
					            {
					            	for(int a=0; a<3; a++)
					      			  for(int b=0; b<3; b++)
					      			    tile_val_temp[a][b]=tile_val[a][b];
					            	tile_val_temp[index][j + k]=1;
					            	if(checkWin()==1)
					            	{
					            		tile_val[index][j + k]=0;
					            		tile[index][j + k].setText("O");
				    		    		tile[index][j + k].setEnabled(false);
				    		    		turn++;
				    		    		txt_turn.setText("Your Turn");
				    		    		enableButtons();
				    		    		checkProgress();
					            	}
					            }
					        }

					    }
					}
				}*/
			}
		}
		if(turn%2==1 && turn!=9)
		{
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
		
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				tile[i][j].setTypeface(typeFace);
			}
		}
		
		btn_new=(Button) findViewById(R.id.btn_new);
		btn_close=(Button) findViewById(R.id.btn_close);
	}
	
	public void refreshAllButtons()
	{
		enableAllButtons();
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				tile[i][j].setText("");
				tile[i][j].setTextColor(Color.parseColor("#ffffff"));
			}
		}
		txt_turn.setText("Your Turn");
		initializeTileValues();
	}
	
	public void enableAllButtons()
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				tile[i][j].setEnabled(true);
			}
		}
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
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				tile[i][j].setEnabled(false);
			}
		}
	}
	
	
	public void initializeTileValues()
	{
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				tile_val[i][j]=-1;
	}
}

package sk.maverick.harsha.tictactoe;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;

public class MainActivity extends Activity {

    ImageButton image[][] = new ImageButton[3][3];
    Button newgame;
    String Tag = "sk.maverick.harsha.tictactoe.MainActivity";
    char winner, array[];
    public int count = 0, temp_index;
    private char selected;
    boolean gameDone = false;

    AI computer;
    Scorer score = new Scorer();
    
     Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            selectPicture(image[temp_index / 3][temp_index % 3]);
            array[temp_index] = selected;
            checkForWin();
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            init();                                     //Initializing the image buttons
            setOnClick();                               //Setting on click listeners for the image buttons

    }

    private void setOnClick() {

     image[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                humanMove(image[0][0], 0);

            }
        });

        image[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                humanMove(image[0][1], 1);
            }
        });

        image[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                humanMove(image[0][2], 2);


            }
        });


        image[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                humanMove(image[1][0],3);


            }
        });

        image[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                humanMove(image[1][1], 4);

            }
        });

        image[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                humanMove(image[1][2], 5);

            }
        });


        image[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                humanMove(image[2][0], 6);

            }
        });

        image[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                humanMove(image[2][1], 7);

            }
        });

        image[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                humanMove(image[2][2], 8);

            }
        });


        newgame.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

              onCreate(Bundle.EMPTY);

            }

        });





    }       //setOnclick Listener

    private void humanMove(ImageButton img, int index) {

       synchronized (this) {
           count = count + 1;
           selectPicture(img);
           array[index] = selected;
           Log.v("Human Move", "Move made " + index);
           checkForWin();

           if (count < 8) {
               computerMove();
           }
       }
    }


    public void computerMove() {
            count = count+1;
        Runnable run = new Runnable() {
            @Override
            public void run() {
                temp_index= computer.aiMove(array, count);
                Log.v("Computer Move ", "" + temp_index);
            }

        };

        handle.sendEmptyMessage(0);
        Thread aiRequestThread = new Thread(run);
        aiRequestThread.start();

    }

    private void checkForWin() {

        //Toast.makeText(this,"" + count, Toast.LENGTH_SHORT).show();

            if (gameDone != true) {

                if (array[0] == 'x' & array[1] == 'x' & array[2] == 'x') {
                    Toast.makeText(this, "first X row win", Toast.LENGTH_SHORT).show();
                    winner = 'x';
                    gameDone = true;
                }
                if (array[0] == 'o' & array[1] == 'o' & array[2] == 'o') {
                    Toast.makeText(this, "first row O win", Toast.LENGTH_SHORT).show();
                    winner = 'o';
                    gameDone = true;
                }

                if (array[3] == 'x' & array[4] == 'x' & array[5] == 'x') {
                    Toast.makeText(this, "second row X win", Toast.LENGTH_SHORT).show();
                    winner = 'x';
                    gameDone = true;
                }
                if (array[3] == 'o' & array[4] == 'o' & array[5] == 'o') {
                    Toast.makeText(this, "second row O win", Toast.LENGTH_SHORT).show();
                    winner = 'o';
                    gameDone = true;
                }

                if (array[6] == 'x' & array[7] == 'x' & array[8] == 'x') {
                    Toast.makeText(this, "third row X win", Toast.LENGTH_SHORT).show();
                    winner = 'x';
                    gameDone = true;
                }
                if (array[6] == 'o' & array[7] == 'o' & array[8] == 'o') {
                    Toast.makeText(this, "third row O win", Toast.LENGTH_SHORT).show();
                    winner = 'o';
                    gameDone = true;
                }


                if (array[0] == 'x' & array[3] == 'x' & array[6] == 'x') {
                    Toast.makeText(this, "first column X win", Toast.LENGTH_SHORT).show();
                    winner = 'x';
                    gameDone = true;

                }
                if (array[0] == 'o' & array[3] == 'o' & array[6] == 'o') {
                    Toast.makeText(this, "first column O win", Toast.LENGTH_SHORT).show();
                    winner = 'o';
                    gameDone = true;

                }

                if ((array[1] == 'x' & array[4] == 'x' & array[7] == 'x')) {
                    Toast.makeText(this, "second column X win", Toast.LENGTH_SHORT).show();
                    winner = 'x';
                    gameDone = true;
                }

                if (array[1] == 'o' & array[4] == 'o' & array[7] == 'o') {
                    Toast.makeText(this, "second column O win", Toast.LENGTH_SHORT).show();
                    winner = 'o';
                    gameDone = true;
                }


                if (array[2] == 'x' & array[5] == 'x' & array[8] == 'x') {
                    Toast.makeText(this, "third column X win", Toast.LENGTH_SHORT).show();
                    winner = 'x';
                    gameDone = true;
                }
                if (array[2] == 'o' & array[5] == 'o' & array[8] == 'o') {
                    Toast.makeText(this, "third column O win", Toast.LENGTH_SHORT).show();
                    winner = 'o';
                    gameDone = true;
                }

                //Diagonal check
                if (array[0] == 'o' & array[4] == 'o' & array[8] == 'o') {
                    Toast.makeText(this, "Diagonal O win", Toast.LENGTH_SHORT).show();
                    winner = 'o';
                    gameDone = true;
                }
                if (array[2] == 'o' & array[4] == 'o' & array[6] == 'o') {
                    Toast.makeText(this, "Diagonal O win", Toast.LENGTH_SHORT).show();
                    winner = 'o';
                    gameDone = true;
                }

                if (array[0] == 'x' & array[4] == 'x' & array[8] == 'x') {
                    Toast.makeText(this, "Diagonal X win", Toast.LENGTH_SHORT).show();
                    winner = 'x';
                    gameDone = true;
                }
                if (array[2] == 'x' & array[4] == 'x' & array[6] == 'x') {
                    Toast.makeText(this, "Diagonal x win", Toast.LENGTH_SHORT).show();
                    winner = 'x';
                    gameDone = true;
                }


            }
            if (gameDone == true) {

                Dialog dialog = new Dialog(this);
                if (winner == 'x') {
                    dialog.setContentView(R.layout.winner);
                    score.player++;
                    gameDone =false;
                }
                if (winner == 'o') {
                    dialog.setContentView(R.layout.winner1);
                    score.AI++;
                    gameDone =false;
                }
                dialog.setTitle("Hell yeah!");
                dialog.show();


            }
            else if(count == 9){

            Dialog dialog = new Dialog(this);
            dialog.setTitle("Draw!");
            dialog.show();
            Toast.makeText(this, "Draw!!", Toast.LENGTH_SHORT).show();

        }

    }


    public void selectPicture(ImageButton img) {

    if (count % 2 == 0) {
        img.setImageResource(R.drawable.toe);
        selected = 'o';

    } else {
        img.setImageResource(R.drawable.cross);
        selected = 'x';

    }
    img.setClickable(false);

}


    // Initializing
    private void init() {

        count = 0;
        array =  new char[9];
        gameDone = false;
        computer = new AI();


        image[0][0] = (ImageButton) findViewById(R.id.row_00);
        image[0][1] = (ImageButton) findViewById(R.id.row_01);
        image[0][2] = (ImageButton) findViewById(R.id.row_02);

        image[1][0] = (ImageButton) findViewById(R.id.row_10);
        image[1][1] = (ImageButton) findViewById(R.id.row_11);
        image[1][2] = (ImageButton) findViewById(R.id.row_12);

        image[2][0] = (ImageButton) findViewById(R.id.row_20);
        image[2][1] = (ImageButton) findViewById(R.id.row_21);
        image[2][2] = (ImageButton) findViewById(R.id.row_22);

        newgame = (Button) findViewById(R.id.button);




        for(int i=0; i<3; i++)
        {
            for (int j=0; j<3; j++)
            {
                image[i][j].setClickable(true);
            }
        }

        for(int i=0; i<9;i++){
            array[i] = 'i';
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putCharArray("array", array);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        array = savedInstanceState.getCharArray("array");
        super.onRestoreInstanceState(savedInstanceState);
        updateImgView();
    }

    private void updateImgView() {
        String temp;
        int index =0;
        for(int i=0; i<3; i++)
        {
          for (int j=0; j<3; j++)
          {
              if(array[index] == 'x')
              {
                image[i][j].setImageResource(R.drawable.cross);
              }
              else if(array[index] == 'o')
              {
                  image[i][j].setImageResource(R.drawable.toe);
              }
            index++;
          }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.score) {

            Toast.makeText(this, "Player's score " + score.player, Toast.LENGTH_SHORT ).show();
            Toast.makeText(this, "AI's score " + score.AI, Toast.LENGTH_SHORT ).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

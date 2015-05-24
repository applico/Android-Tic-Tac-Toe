package justinvalcarcel.com.tictactoevalcarcel;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private String button1, button2, button3, button4, button5, button6, button7, button8, button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resetBoard();
        final Button startButton, but1, but2, but3, but4, but5, but6, but7, but8, but9;

        startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGame();
                TextView textview = (TextView)findViewById(R.id.status);
                textview.setText("");

            }
        });

        but1 = (Button) findViewById(R.id.button1);
        but1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        TextView tbut1 = (TextView)findViewById(R.id.button1);
                                        tbut1.setText("X");
                                        button1 = "x";
                                        checkGame();
                                        AI();
                                        checkGame();
                                        but1.setClickable(false);
                                    }

                                }
        );

        but2 = (Button) findViewById(R.id.button2);
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tbut2 = (TextView)findViewById(R.id.button2);
                tbut2.setText("X");
                button2 = "x";
                checkGame();
                AI();
                checkGame();
                but2.setClickable(false);}
        });

        but3 = (Button) findViewById(R.id.button3);
        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tbut3 = (TextView)findViewById(R.id.button3);
                tbut3.setText("X");
                button3 = "x";
                checkGame();
                AI();
                checkGame();
                but3.setClickable(false);}
        });

        but4 = (Button) findViewById(R.id.button4);
        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tbut4 = (TextView)findViewById(R.id.button4);
                tbut4.setText("X");
                button4 = "x";
                checkGame();
                AI();
                checkGame();
                but4.setClickable(false);}
        });

        but5 = (Button) findViewById(R.id.button5);
        but5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tbut5 = (TextView)findViewById(R.id.button5);
                tbut5.setText("X");
                button5 = "x";
                checkGame();
                AI();
                checkGame();
                but5.setClickable(false);}
        });

        but6 = (Button) findViewById(R.id.button6);
        but6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tbut6 = (TextView)findViewById(R.id.button6);
                tbut6.setText("X");
                button6 = "x";
                checkGame();
                AI();
                checkGame();
                but6.setClickable(false);}
        });

        but7 = (Button) findViewById(R.id.button7);
        but7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tbut7 = (TextView)findViewById(R.id.button7);
                tbut7.setText("X");
                button7 = "x";
                checkGame();
                AI();
                checkGame();
                but7.setClickable(false);}
        });

        but8 = (Button) findViewById(R.id.button8);
        but8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tbut8 = (TextView)findViewById(R.id.button8);
                tbut8.setText("X");
                button8 = "x";
                checkGame();
                AI();
                checkGame();
                but8.setClickable(false);}
        });

        but9 = (Button) findViewById(R.id.button9);
        but9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tbut9 = (TextView)findViewById(R.id.button9);
                tbut9.setText("X");
                button9 = "x";
                checkGame();
                AI();
                checkGame();
                but9.setClickable(false);}
        });


    }

    public void resetBoard(){
        button1 = button2= button3= button4= button5= button6= button7= button8= button9 = "blank";

    }

    public void checkGame(){
        if (button1== "x" && button2 == "x" && button3 == "x" ||
                button4== "x" && button5 == "x" && button6 == "x" ||
                button7== "x" && button8 == "x" && button9 == "x" ||
                button1== "x" && button4 == "x" && button7 == "x" ||
                button2== "x" && button5 == "x" && button8 == "x" ||
                button3== "x" && button6 == "x" && button9 == "x" ||
                button1== "x" && button5 == "x" && button9 == "x" ||
                button3== "x" && button5 == "x" && button7 == "x") {
            TextView textview = (TextView) findViewById(R.id.status);
            textview.setText("You Win!");
            TextView textview2 = (TextView) findViewById(R.id.start_button);
            textview2.setText("Play Again?");
            resetBoard();        }
        else if (button1== "o" && button2 == "o" && button3 == "o" ||
                button4== "o" && button5 == "o" && button6 == "o" ||
                button7== "o" && button8 == "o" && button9 == "o" ||
                button1== "o" && button4 == "o" && button7 == "o" ||
                button2== "o" && button5 == "o" && button8 == "o" ||
                button3== "o" && button6 == "o" && button9 == "o" ||
                button1== "o" && button5 == "o" && button9 == "o" ||
                button3== "o" && button5 == "o" && button7 == "o") {
            TextView textview = (TextView) findViewById(R.id.status);
            textview.setText("You Lose :(");
            TextView textview2 = (TextView) findViewById(R.id.start_button);
            textview2.setText("Play Again?");
            resetBoard();        }
        else if (button1 != "blank" && button2 != "blank" && button3 != "blank" &&
                button4 != "blank" && button5 != "blank" && button6 != "blank" &&
                button7 != "blank" && button8 != "blank" && button9 != "blank" ){
            TextView textview = (TextView) findViewById(R.id.status);
            textview.setText("Tie Game!");
            TextView textview2 = (TextView) findViewById(R.id.start_button);
            textview2.setText("Play Again?");
        }

    }

    public void newGame(){
        Toast.makeText(getApplicationContext(), "Start!", Toast.LENGTH_SHORT).show();
        TextView tbut1 = (TextView)findViewById(R.id.button1);
        tbut1.setText("");
        TextView tbut2 = (TextView)findViewById(R.id.button2);
        tbut2.setText("");
        TextView tbut3 = (TextView)findViewById(R.id.button3);
        tbut3.setText("");
        TextView tbut4 = (TextView)findViewById(R.id.button4);
        tbut4.setText("");
        TextView tbut5 = (TextView)findViewById(R.id.button5);
        tbut5.setText("");
        TextView tbut6 = (TextView)findViewById(R.id.button6);
        tbut6.setText("");
        TextView tbut7 = (TextView)findViewById(R.id.button7);
        tbut7.setText("");
        TextView tbut8 = (TextView)findViewById(R.id.button8);
        tbut8.setText("");
        TextView tbut9 = (TextView)findViewById(R.id.button9);
        tbut9.setText("");

        tbut1.setClickable(true);
        tbut2.setClickable(true);
        tbut3.setClickable(true);
        tbut4.setClickable(true);
        tbut5.setClickable(true);
        tbut6.setClickable(true);
        tbut7.setClickable(true);
        tbut8.setClickable(true);
        tbut9.setClickable(true);

        resetBoard();

    }


    public void AI(){


        if(button2 == "blank" &&
                (button1=="x" && button3 == "x" ||
                        button5 =="x" && button8=="x")) {
            button2 = "o";
            TextView tbut2 = (TextView)findViewById(R.id.button2);
            tbut2.setText("o");
            tbut2.setClickable(false);

        }
        else if (button4 == "blank" &&
                (button1=="x" && button7 == "x" ||
                        button5 =="x" && button6 == "x")){
            button4 = "o";
            TextView tbut4 = (TextView)findViewById(R.id.button4);
            tbut4.setText("o");
            tbut4.setClickable(false);
        }
        else if (button5 == "blank" &&
                (button1=="x" && button9 == "x" ||
                        button3 == "x" && button7 == "x" ||
                        button2 == "x" && button8 == "x" ||
                        button4 == "x" && button6 == "x")){
            button5 = "o";
            TextView tbut5 = (TextView)findViewById(R.id.button5);
            tbut5.setText("o");
            tbut5.setClickable(false);
        }
        else if (button1 =="blank" &&
                (button2=="x" && button3 == "x" ||
                        button4 == "x" && button7 == "x"||
                        button5 == "x" && button9 == "x")){
            button1 = "o";
            TextView tbut1 = (TextView)findViewById(R.id.button1);
            tbut1.setText("o");
            tbut1.setClickable(false);
        }
        else if (button3 =="blank" &&
                (button2=="x" && button1 == "x" ||
                        button6 == "x" && button9 == "x"||
                        button7== "x" && button5 == "x")){
            button3 = "o";
            TextView tbut3 = (TextView)findViewById(R.id.button3);
            tbut3.setText("o");
            tbut3.setClickable(false);
        }
        else if (button6 =="blank" &&
                (button3=="x" && button9 == "x" ||
                        button4 == "x" && button5 == "x")){
            button6 = "o";
            TextView tbut6 = (TextView)findViewById(R.id.button6);
            tbut6.setText("o");
            tbut6.setClickable(false);
        }
        else if (button7 =="blank" &&
                (button1=="x" && button4 == "x") ||
                (button8 == "x" && button9 == "x"||
                        button3 == "x" && button5 == "x")
                ){
            button7 = "o";
            TextView tbut7 = (TextView)findViewById(R.id.button7);
            tbut7.setText("o");
            tbut7.setClickable(false);
        }
        else if (button8 =="blank" &&
                (button7=="x" && button9 == "x" ||
                        button2 == "x" && button5 == "x")){
            button8 = "o";
            TextView tbut8 = (TextView)findViewById(R.id.button8);
            tbut8.setText("o");
            tbut8.setClickable(false);
        }
        else if (button9 =="blank" &&
                (button8=="x" && button7 == "x" ||
                        button3 == "x" && button6 == "x"||
                        button1 =="x" && button5 =="x")){
            button9 = "o";
            TextView tbut9 = (TextView)findViewById(R.id.button9);
            tbut9.setText("o");
            tbut9.setClickable(false);
        }
        else{
            if (button2 == "blank"){
                button2 = "o";
                TextView tbut2 = (TextView)findViewById(R.id.button2);
                tbut2.setText("o");
                tbut2.setClickable(false);
            }
            else if (button3 == "blank"){
                button3 = "o";
                TextView tbut3 = (TextView)findViewById(R.id.button3);
                tbut3.setText("o");
                tbut3.setClickable(false);
            }
            else if (button4 == "blank"){
                button4 = "o";
                TextView tbut4 = (TextView)findViewById(R.id.button4);
                tbut4.setText("o");
                tbut4.setClickable(false);
            }
            else if (button5 == "blank"){
                button5 = "o";
                TextView tbut5 = (TextView)findViewById(R.id.button5);
                tbut5.setText("o");
                tbut5.setClickable(false);
            }
            else if (button6 == "blank"){
                button6 = "o";
                TextView tbut6 = (TextView)findViewById(R.id.button6);
                tbut6.setText("o");
                tbut6.setClickable(false);
            }
            else if (button7 == "blank"){
                button7 = "o";
                TextView tbut7 = (TextView)findViewById(R.id.button7);
                tbut7.setText("o");
                tbut7.setClickable(false);
            }
            else if (button8 == "blank"){
                button8 = "o";
                TextView tbut8 = (TextView)findViewById(R.id.button8);
                tbut8.setText("o");
                tbut8.setClickable(false);
            }
            else if (button9 == "blank"){
                button9 = "o";
                TextView tbut9 = (TextView)findViewById(R.id.button9);
                tbut9.setText("o");
                tbut9.setClickable(false);
            }
            else if (button1 == "blank"){
                button1 = "o";
                TextView tbut1 = (TextView)findViewById(R.id.button1);
                tbut1.setText("o");
                tbut1.setClickable(false);
            }


        }

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.vishesh.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.exit;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

        int[][] board=new int[3][3];
        Random rand = new Random();
        List<Point> availablePoints;


        public boolean isGameOver() {
            //Game is over is someone has won, or board is full (draw)
            return (hasXWon() || hasOWon() || getAvailableStates().isEmpty());
        }

        public boolean hasXWon() {
            if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
                //System.out.println("X Diagonal Win");
                return true;
            }
            for (int i = 0; i < 3; ++i) {
                if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                        || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                    // System.out.println("X Row or Column win");
                    return true;
                }
            }
            return false;
        }

        public boolean hasOWon() {
            if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
                // System.out.println("O Diagonal Win");
                return true;
            }
            for (int i = 0; i < 3; ++i) {
                if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
                        || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
                    //  System.out.println("O Row or Column win");
                    return true;
                }
            }

            return false;
        }

        public List<Point> getAvailableStates() {
            availablePoints = new ArrayList<>();
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    if (board[i][j] == 0) {
                        availablePoints.add(new Point(i, j));
                    }
                }
            }
            return availablePoints;
        }

        public void placeAMove(Point point, int player) {
            board[point.x][point.y] = player;   //player = 1 for X, 2 for O
        }

        public Point returnBestMove() {
            int MAX = -100000;
            int best = -1;

            for (int i = 0; i < rootsChildrenScores.size(); ++i) {
                if (MAX < rootsChildrenScores.get(i).score) {
                    MAX = rootsChildrenScores.get(i).score;
                    best = i;
                }
            }
            return rootsChildrenScores.get(best).point;
        }

        public void updateBoard(Point p, int temp) {

        if(p.x==0&&p.y==0)
        {
            if(temp==2)
            {
                one.setImageResource(R.drawable.xsquare);
            }
            if(temp==1)
            {
                one.setImageResource(R.drawable.osquare);
            }
        }
        if(p.x==0&&p.y==1)
        {
            if(temp==2)
            {
                two.setImageResource(R.drawable.xsquare);
            }
            if(temp==1)
            {
                two.setImageResource(R.drawable.osquare);
            }
        }
        if(p.x==0&&p.y==2)
        {
            if(temp==2)
            {
                three.setImageResource(R.drawable.xsquare);
            }
            if(temp==1)
            {
                three.setImageResource(R.drawable.osquare);
            }
        }
        if(p.x==1&&p.y==0)
        {
            if(temp==2)
            {
                four.setImageResource(R.drawable.xsquare);
            }
            if(temp==1)
            {
                four.setImageResource(R.drawable.osquare);
            }
        }
        if(p.x==1&&p.y==1)
        {
            if(temp==2)
            {
                five.setImageResource(R.drawable.xsquare);
            }
            if(temp==1)
            {
                five.setImageResource(R.drawable.osquare);
            }
        }
        if(p.x==1&&p.y==2)
        {
            if(temp==2)
            {
                six.setImageResource(R.drawable.xsquare);
            }
            if(temp==1)
            {
                six.setImageResource(R.drawable.osquare);
            }
        }
        if(p.x==2&&p.y==0)
        {
            if(temp==2)
            {
                seven.setImageResource(R.drawable.xsquare);
            }
            if(temp==1)
            {
                seven.setImageResource(R.drawable.osquare);
            }
        }
        if(p.x==2&&p.y==1)
        {
            if(temp==2)
            {
                eight.setImageResource(R.drawable.xsquare);
            }
            if(temp==1)
            {
                eight.setImageResource(R.drawable.osquare);
            }
        }
        if(p.x==2&&p.y==2)
        {
            if(temp==2)
            {
                nine.setImageResource(R.drawable.xsquare);
            }
            if(temp==1)
            {
                nine.setImageResource(R.drawable.osquare);
            }
        }
    }

        public int returnMin(List<Integer> list) {
            int min = Integer.MAX_VALUE;
            int index = -1;
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i) < min) {
                    min = list.get(i);
                    index = i;
                }
            }
            return list.get(index);
        }

        public int returnMax(List<Integer> list) {
            int max = Integer.MIN_VALUE;
            int index = -1;
            for (int i = 0; i < list.size(); ++i) {
                if (list.get(i) > max) {
                    max = list.get(i);
                    index = i;
                }
            }
            return list.get(index);
        }

        List<PointsAndScores> rootsChildrenScores;

        public void displayResult() {
            String r;
            if (hasXWon()) {
                r="Unfortunately, you lost!";
            } else if (hasOWon()) {
                r="You win!";
            } else {
                r="It's a draw!";
            }

            new AlertDialog.Builder(this)
                    .setTitle("GAME OVER")
                    .setMessage(r)
                    .setPositiveButton("New Game", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            clearBoard();
                            if(temp==1)
                            {
                                Point p = new Point(rand.nextInt(3), rand.nextInt(3));
                                placeAMove(p, 1);
                                updateBoard(p,tempInverse(temp));
                            }
                        }
                    })
                    .show();

        }

        public void clearBoard() {
            board=new int[3][3];
            one.setImageResource(R.drawable.blank);
            two.setImageResource(R.drawable.blank);
            three.setImageResource(R.drawable.blank);
            four.setImageResource(R.drawable.blank);
            five.setImageResource(R.drawable.blank);
            six.setImageResource(R.drawable.blank);
            seven.setImageResource(R.drawable.blank);
            eight.setImageResource(R.drawable.blank);
            nine.setImageResource(R.drawable.blank);
//            onCreate(Bundle.EMPTY);

        }

        public void callMinimax(int depth, int turn){
            rootsChildrenScores = new ArrayList<>();
            minimax(depth, turn);
        }

        public int minimax(int depth, int turn) {

            if (hasXWon()) return +1;
            if (hasOWon()) return -1;

            List<Point> pointsAvailable = getAvailableStates();
            if (pointsAvailable.isEmpty()) return 0;

            List<Integer> scores = new ArrayList<>();

            for (int i = 0; i < pointsAvailable.size(); ++i) {
                Point point = pointsAvailable.get(i);

                if (turn == 1) { //X's turn select the highest from below minimax() call
                    placeAMove(point, 1);
                    int currentScore = minimax(depth + 1, 2);
                    scores.add(currentScore);

                    if (depth == 0)
                        rootsChildrenScores.add(new PointsAndScores(currentScore, point));

                } else if (turn == 2) {//O's turn select the lowest from below minimax() call
                    placeAMove(point, 2);
                    scores.add(minimax(depth + 1, 1));
                }
                board[point.x][point.y] = 0; //Reset this point
            }
            return turn == 1 ? returnMax(scores) : returnMin(scores);
        }

        private Switch playerSelector;

        int temp=2;
        ImageView one;
        ImageView two;
        ImageView three;
        ImageView four;
        ImageView five;
        ImageView six;
        ImageView seven;
        ImageView eight;
        ImageView nine;
//        TextView result;
        ImageView restart;
        ImageView help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        result= (TextView) findViewById(R.id.Result);
        playerSelector = (Switch) findViewById(R.id.playerSelection);
        playerSelector.setChecked(false);
        //attach a listener to check for changes in state


        playerSelector.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    clearBoard();
                    temp=1;
                    Point p = new Point(rand.nextInt(3), rand.nextInt(3));
                    placeAMove(p, 1);
                    updateBoard(p,tempInverse(temp));
                }
                else
                {
                    clearBoard();
                }
            }
        });


        one = (ImageView)findViewById(R.id.position00);
        two = (ImageView)findViewById(R.id.position01);
        three = (ImageView)findViewById(R.id.position02);
        four = (ImageView)findViewById(R.id.position10);
        five = (ImageView)findViewById(R.id.position11);
        six = (ImageView)findViewById(R.id.position12);
        seven = (ImageView)findViewById(R.id.position20);
        eight = (ImageView)findViewById(R.id.position21);
        nine = (ImageView)findViewById(R.id.position22);
        restart = (ImageView)findViewById(R.id.restartGame);
        help=(ImageView)findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Rules")
                        .setMessage("'X'  -  Always Plays First \n'O'  -  Plays Second")
                        .setPositiveButton("Got It", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                clearBoard();
                if(temp==1)
                {
                    Point p = new Point(rand.nextInt(3), rand.nextInt(3));
                    placeAMove(p, 1);
                    updateBoard(p,tempInverse(temp));
                }

            }
        });


        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        boolean flag=true;


        if(board[0][0]==0)
        {
            if(v.getId()==one.getId())
            {
                Point point = new Point(0, 0);
                placeAMove(point, 2);
                updateBoard(point,temp);

                if(isGameOver()){
                    displayResult();
                    flag=false;
                }

                if(flag)
                {
                    if(!isGameOver())
                    {
                        callMinimax(0, 1);
                        for (PointsAndScores pas : rootsChildrenScores) {
                            System.out.println("Point: " + pas.point + " Score: " + pas.score);
                        }
                        point=returnBestMove();
                        placeAMove(point, 1);
                        updateBoard(point,tempInverse(temp));

                    }
                    if(isGameOver()){
                        displayResult();
                    }
                }

            }

        }



        if(board[0][1]==0)
        {
            if(v.getId()==two.getId())
            {
                Point point = new Point(0, 1);
                placeAMove(point, 2);
                updateBoard(point,temp);

                if(isGameOver()){
                    displayResult();
                    flag=false;
                }

                if(flag)
                {
                    if(!isGameOver())
                    {
                        callMinimax(0, 1);
                        for (PointsAndScores pas : rootsChildrenScores) {
                            System.out.println("Point: " + pas.point + " Score: " + pas.score);
                        }
                        point=returnBestMove();
                        placeAMove(point, 1);
                        updateBoard(point,tempInverse(temp));

                    }
                    if(isGameOver()){
                        displayResult();
                    }
                }
            }
        }



        if(board[0][2]==0)
        {
            if(v.getId()==three.getId())
            {
                Point point = new Point(0, 2);
                placeAMove(point, 2);
                updateBoard(point,temp);

                if(isGameOver()){
                    displayResult();
                    flag=false;
                }

                if(flag)
                {
                    if(!isGameOver())
                    {
                        callMinimax(0, 1);
                        for (PointsAndScores pas : rootsChildrenScores) {
                            System.out.println("Point: " + pas.point + " Score: " + pas.score);
                        }
                        point=returnBestMove();
                        placeAMove(point, 1);
                        updateBoard(point,tempInverse(temp));

                    }
                    if(isGameOver()){
                        displayResult();
                    }
                }
            }

        }



        if(board[1][0]==0)
        {
            if(v.getId()==four.getId())
            {
                Point point = new Point(1, 0);
                placeAMove(point, 2);
                updateBoard(point,temp);

                if(isGameOver()){
                    displayResult();
                    flag=false;
                }

                if(flag)
                {
                    if(!isGameOver())
                    {
                        callMinimax(0, 1);
                        for (PointsAndScores pas : rootsChildrenScores) {
                            System.out.println("Point: " + pas.point + " Score: " + pas.score);
                        }
                        point=returnBestMove();
                        placeAMove(point, 1);
                        updateBoard(point,tempInverse(temp));

                    }
                    if(isGameOver()){
                        displayResult();
                    }
                }
            }
        }



        if(board[1][1]==0)
        {
            if(v.getId()==five.getId())
            {
                Point point = new Point(1, 1);
                placeAMove(point, 2);
                updateBoard(point,temp);

                if(isGameOver()){
                    displayResult();
                    flag=false;
                }

                if(flag)
                {
                    if(!isGameOver())
                    {
                        callMinimax(0, 1);
                        for (PointsAndScores pas : rootsChildrenScores) {
                            System.out.println("Point: " + pas.point + " Score: " + pas.score);
                        }
                        point=returnBestMove();
                        placeAMove(point, 1);
                        updateBoard(point,tempInverse(temp));

                    }
                    if(isGameOver()){
                        displayResult();
                    }
                }
            }
        }



        if(board[1][2]==0)
        {
            if(v.getId()==six.getId())
            {
                Point point = new Point(1, 2);
                placeAMove(point, 2);
                updateBoard(point,temp);

                if(isGameOver()){
                    displayResult();
                    flag=false;
                }

                if(flag)
                {
                    if(!isGameOver())
                    {
                        callMinimax(0, 1);
                        for (PointsAndScores pas : rootsChildrenScores) {
                            System.out.println("Point: " + pas.point + " Score: " + pas.score);
                        }
                        point=returnBestMove();
                        placeAMove(point, 1);
                        updateBoard(point,tempInverse(temp));

                    }
                    if(isGameOver()){
                        displayResult();
                    }
                }
            }
        }



        if(board[2][0]==0)
        {
            if(v.getId()==seven.getId())
            {
                Point point = new Point(2, 0);
                placeAMove(point, 2);
                updateBoard(point,temp);

                if(isGameOver()){
                    displayResult();
                    flag=false;
                }

                if(flag)
                {
                    if(!isGameOver())
                    {
                        callMinimax(0, 1);
                        for (PointsAndScores pas : rootsChildrenScores) {
                            System.out.println("Point: " + pas.point + " Score: " + pas.score);
                        }
                        point=returnBestMove();
                        placeAMove(point, 1);
                        updateBoard(point,tempInverse(temp));

                    }
                    if(isGameOver()){
                        displayResult();
                    }
                }
            }
        }



        if(board[2][1]==0)
        {
            if(v.getId()==eight.getId())
            {
                Point point = new Point(2, 1);
                placeAMove(point, 2);
                updateBoard(point,temp);

                if(isGameOver()){
                    displayResult();
                    flag=false;
                }

                if(flag)
                {
                    if(!isGameOver())
                    {
                        callMinimax(0, 1);
                        for (PointsAndScores pas : rootsChildrenScores) {
                            System.out.println("Point: " + pas.point + " Score: " + pas.score);
                        }
                        point=returnBestMove();
                        placeAMove(point, 1);
                        updateBoard(point,tempInverse(temp));

                    }
                    if(isGameOver()){
                        displayResult();
                    }
                }
            }
        }


        if(board[2][2]==0)
        {
            if(v.getId()==nine.getId())
            {
                Point point = new Point(2, 2);
                placeAMove(point, 2);
                updateBoard(point,temp);

                if(isGameOver()){
                    displayResult();
                    flag=false;
                }

                if(flag)
                {
                    if(!isGameOver())
                    {
                        callMinimax(0, 1);
                        for (PointsAndScores pas : rootsChildrenScores) {
                            System.out.println("Point: " + pas.point + " Score: " + pas.score);
                        }
                        point=returnBestMove();
                        placeAMove(point, 1);
                        updateBoard(point,tempInverse(temp));

                    }
                    if(isGameOver()){
                        displayResult();
                    }
                }

            }

        }




    }

    public int tempInverse(int temp) {
        if (temp==1)
            return 2;
        else return 1;
    }

    public class Point {

        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "[" + x + ", " + y + "]";
        }
    }

    public class PointsAndScores {

        int score;
        Point point;

        PointsAndScores(int score, Point point) {
            this.score = score;
            this.point = point;
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

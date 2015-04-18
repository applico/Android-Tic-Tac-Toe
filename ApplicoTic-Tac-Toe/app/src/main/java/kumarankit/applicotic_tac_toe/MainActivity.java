package kumarankit.applicotic_tac_toe;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private TicTacToe mGame;
    private Button mBoardButtons[], mNewGame;

    private TextView mInfoTextView, mUserCount, mTieCount, mAICount;

    private int mUserCounter = 0, mTieCounter=0, mAICounter=0;

    private boolean mUserFirst = true, mGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mGame = new TicTacToe();
        startNewGame();
    }
    //-----------<Start a new game and set player 1>-------------------
    private void startNewGame() {
        mGame.clearBoard();

        for (int i = 0; i < mBoardButtons.length; i++) {
            mBoardButtons[i].setText("");
            mBoardButtons[i].setEnabled(true);
            mBoardButtons[i].setOnClickListener(new ButtonClickListener(i));
        }
        if (mUserFirst)
        {
            mInfoTextView.setText(R.string.user_first);
            mUserFirst = false;
        }
        else
        {
            mInfoTextView.setText(R.string.ai_turn);
            int move = mGame.getAIMove();
            setMove(mGame.AI, move);
            mUserFirst = true;
        }
        mNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNewGame();
            }
        });
        mGameOver = false;
        mNewGame.setVisibility(View.INVISIBLE);

    }

    //-----------<set a players move to UI>-------------------
    private void setMove(char player, int location)
    {
        mGame.setMove(player, location);
        mBoardButtons[location].setEnabled(false);
        mBoardButtons[location].setText(String.valueOf(player));
        if (player == mGame.USER_)
            mBoardButtons[location].setTextColor(Color.GREEN);
        else
            mBoardButtons[location].setTextColor(Color.RED);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        if (id == R.id.new_game)
            startNewGame();
        if (id == R.id.exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    //-----------<Initializes the UI>-------------------
    private void init()
    {
        mBoardButtons= new Button[mGame.getBoardSize()];
        mBoardButtons[0] = (Button) findViewById(R.id.one);
        mBoardButtons[1] = (Button) findViewById(R.id.two);
        mBoardButtons[2] = (Button) findViewById(R.id.three);
        mBoardButtons[3] = (Button) findViewById(R.id.four);
        mBoardButtons[4] = (Button) findViewById(R.id.five);
        mBoardButtons[5] = (Button) findViewById(R.id.six);
        mBoardButtons[6] = (Button) findViewById(R.id.seven);
        mBoardButtons[7] = (Button) findViewById(R.id.eight);
        mBoardButtons[8] = (Button) findViewById(R.id.nine);

        mNewGame = (Button) findViewById(R.id.bNewGame);

        mInfoTextView = (TextView) findViewById(R.id.information);
        mUserCount = (TextView) findViewById(R.id.humanCount);
        mTieCount = (TextView) findViewById(R.id.tiesCount);
        mAICount = (TextView) findViewById(R.id.androidCount);

        mUserCount.setText(Integer.toString(mUserCounter));
        mTieCount.setText(Integer.toString(mTieCounter));
        mAICount.setText(Integer.toString(mAICounter));

    }
    //-----------<Button click listener for TIC TAC TOE>-------------------
    private class ButtonClickListener implements View.OnClickListener
    {
        int location;

        public ButtonClickListener(int location)
        {
            this.location = location;
        }

        public void onClick(View view)
        {
            if (!mGameOver)
            {
                if (mBoardButtons[location].isEnabled())
                {
                    setMove(mGame.USER_, location);

                    int winner = mGame.checkWinner();

                    if (winner == 0)
                    {
                        mInfoTextView.setText(R.string.ai_turn);
                        int move = mGame.getAIMove();
                        setMove(mGame.AI, move);
                        winner = mGame.checkWinner();
                    }

                    if (winner == 0)
                        mInfoTextView.setText(R.string.user_turn);
                    else if (winner == 1)
                    {
                        mInfoTextView.setText(R.string.result_tie);
                        mTieCounter++;
                        mTieCount.setText(Integer.toString(mTieCounter));
                        mGameOver = true;
                        mNewGame.setVisibility(View.VISIBLE);
                    }
                    else if (winner == 2)
                    {
                        mInfoTextView.setText(R.string.result_user);
                        mUserCounter++;
                        mUserCount.setText(Integer.toString(mUserCounter));
                        mGameOver = true;
                        mNewGame.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        mInfoTextView.setText(R.string.result_ai);
                        mAICounter++;
                        mAICount.setText(Integer.toString(mAICounter));
                        mGameOver = true;
                        mNewGame.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
    }
}

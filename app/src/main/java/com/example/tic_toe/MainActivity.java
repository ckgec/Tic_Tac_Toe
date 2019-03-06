package com.example.tic_toe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons=new Button[3][3]; //For 9 buttons
    private boolean player1Turn=true; //Supposing 1st Player Got Chance
    private int roundCount; //Used to count the no. of turns
    private int player1Points;
    private int player2Points;
    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1=findViewById(R.id.p1);
        textViewPlayer2=findViewById(R.id.p2);
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                String buttonID="button_"+i +j; //to match with the format of id's of buttons i.e., button_00,button_11,etc
                int resID=getResources().getIdentifier(buttonID,"id",getPackageName());//used to extract id of all button in format "R.id.button_00"
                buttons[i][j]=findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
        Button buttonReset=findViewById(R.id.reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset_all();
            }
        });
    }



    @Override
    public void onClick(View v) {
        if(!((Button) v).getText().toString().equals("")) // if the button is already pressed then don't do anything
        {
            return;
        }

        if(player1Turn)
        {
            ((Button)v).setText("X");
        }

        else
        {
            ((Button)v).setText("0");
        }
        roundCount++;
         if(checkForWin()){
             if(player1Turn)
             {
                 player1Wins();
             }
             else
             {
                 player2Wins();
             }
         }
         else if(roundCount==9){
             draw();
         }
         else
             player1Turn=!player1Turn;
    }

    private boolean checkForWin(){
        String[][] field=new String[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                field[i][j]=buttons[i][j].getText().toString();
            }
        }

        for(int i=0;i<3;i++){
            if(field[i][0].equals(field[i][1]) && (field[i][0].equals(field[i][2])) && !field[i][0].equals(""))//For Column Checking
            {
            return true;
            }
        }

        for(int i=0;i<3;i++){
            if(field[0][i].equals(field[1][i]) && (field[0][i].equals(field[2][i])) && !field[0][i].equals(""))//For Row Checking
            {
                return true;
            }
        }

        if(field[0][0].equals(field[1][1]) && (field[0][0].equals(field[2][2])) && !field[0][0].equals(""))//For Diagonal
        {
            return true;
        }

        if(field[0][2].equals(field[1][1]) && (field[0][2].equals(field[2][0])) && !field[0][2].equals(""))//For Diagonal
        {
            return true;
        }

        return false;
    }
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this,"Player 1 Wins",Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this,"Player 2 Wins",Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this,"Draw",Toast.LENGTH_LONG).show();
        resetBoard();
    }

    private void resetBoard() {
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++){
                buttons[i][j].setText("");
            }
        }
        roundCount=0;
        player1Turn=true;
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player1: "+player1Points);
        textViewPlayer2.setText("Player2: "+player2Points);
    }

    private void reset_all() {
        resetBoard();
        player1Points=0;
        player2Points=0;
        updatePointsText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                Intent about=new Intent(this,About.class);
                startActivity(about);
        }
        return true;
    }
}

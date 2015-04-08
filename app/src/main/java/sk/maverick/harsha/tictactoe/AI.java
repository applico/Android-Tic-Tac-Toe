package sk.maverick.harsha.tictactoe;

import android.util.Log;
import android.widget.ImageButton;
import java.util.Random;

/**
 * Created by Harsha on 4/7/2015.
 */
public class AI {

    MainActivity object = new MainActivity();
    boolean flag = false;

    public int aiMove(char array[], int count){

    int index=0;

        if(count<3) {
                    while (true) {
                        index = new Random().nextInt(9);
                        if (array[index] == 'i') {
                            break;
                        }
                    }
            }
        else if(count == 3)
        {
            index = defenseMove(array);

                if(index ==0 & flag == false){
                    index = attackMove(array);

                        if(index ==0 & flag == false){

                            //random move
                                int i=0;
                                while(true)
                                {
                                    if(array[i] == 'i' )
                                    {
                                        index=i;
                                        i++;
                                        break;
                                    }
                                }

                        }

                }

        }
        else if(count>3 & count<9){
            index = attackMove(array);


            if(index ==0 & flag == false){
                index = defenseMove(array);

                if(index ==0 & flag == false){

                    //random move
                    int i=0;
                    while(true)
                    {
                        if(array[i] == 'i' )
                        {
                            index=i;
                            i++;
                            break;
                        }
                    }

                }

            }
        }






         /*
                //Defense Moves

                if(array[0] == 'x' & array[1] == 'x' & array[2] == 'i')
                    index= 2;

                if(array[0] == 'x' & array[2] == 'x' & array[1] == 'i')
                    index = 1;

                if(array[1] == 'x' & array[2] == 'x' & array[0] == 'i')
                    index = 0;


                if(array[3] == 'x' & array[4] == 'x' & array[5] == 'i')
                    index = 5;

                if(array[3] == 'x' & array[5] == 'x' & array[4] == 'i')
                    index = 4;

                if(array[4] == 'x' & array[5] == 'x' & array[3] == 'i')
                    index = 3;

                if(array[6] == 'x' & array[7] == 'x' & array[8] == 'i')
                    index = 8;

                if(array[6] == 'x' & array[8] == 'x' & array[7] == 'i' )
                    index = 7;

                if(array[7] == 'x' & array[8] == 'x' & array[6] == 'i')
                    index = 6;



                if( array[0] == 'x' & array[3] == 'x' & array[6] == 'i' )
                    index =  6;

                if( array[0] == 'x' & array[6] == 'x' & array[3] == 'i' )
                    index =  3;

                if ( array[3] == 'x' & array[6] == 'x' & array[0] == 'i')
                    index =  0;


                if ( array[1] == 'x' & array[4] == 'x' & array[7] == 'i' )
                    index =  7;

                if ( array[1] == 'x' & array[7] == 'x' & array[4] == 'i')
                    index =  4;

                if ( array[4] == 'x' & array[7] == 'x' & array[1] == 'i' )
                    index =  1;


                if ( array[2] == 'x' & array[5] == 'x' & array[8] == 'i')
                    index =  8;

                if ( array[2] == 'x' & array[8] == 'x' & array[5] == 'i')
                    index =  5;

                if ( array[5] == 'x' & array[8] == 'x' & array[2] == 'i' )
                    index =  2;




                if ( array[0] == 'x' & array[4] == 'x' & array[8] == 'i' )
                    index =  8;

                if ( array[0] == 'x' & array[8] == 'x' & array[4] == 'i' )
                    index =  4;

                if ( array[4] == 'x' & array[8] == 'x' & array[0] == 'i' )
                    index =  0;


                if ( array[2] == 'x' & array[4] == 'x' & array[6] == 'i')
                    index =  6;

                if ( array[2] == 'x' & array[6] == 'x' & array[4] == 'i' )
                    index =  4;

                if ( array[4] == 'x' & array[6] == 'x' & array[2] == 'i')
                    index =  2;

            }

        */

        return index;
    }

    private int defenseMove(char[] array) {
        int index = 0;

        if(array[0] == 'x' & array[1] == 'x' & array[2] == 'i')
            index= 2;

        else if(array[0] == 'x' & array[2] == 'x' & array[1] == 'i')
            index = 1;

        else if(array[1] == 'x' & array[2] == 'x' & array[0] == 'i'){index = 0; flag =true; }



        else if(array[3] == 'x' & array[4] == 'x' & array[5] == 'i')
            index = 5;

        else if(array[3] == 'x' & array[5] == 'x' & array[4] == 'i')
            index = 4;

        else if(array[4] == 'x' & array[5] == 'x' & array[3] == 'i')
            index = 3;

        else if(array[6] == 'x' & array[7] == 'x' & array[8] == 'i')
            index = 8;

        else if(array[6] == 'x' & array[8] == 'x' & array[7] == 'i' )
            index = 7;

        else if(array[7] == 'x' & array[8] == 'x' & array[6] == 'i')
            index = 6;



        else if( array[0] == 'x' & array[3] == 'x' & array[6] == 'i' )
            index =  6;

        else if( array[0] == 'x' & array[6] == 'x' & array[3] == 'i' )
            index =  3;

        else if ( array[3] == 'x' & array[6] == 'x' & array[0] == 'i'){index = 0; flag =true; }



        else if ( array[1] == 'x' & array[4] == 'x' & array[7] == 'i' )
            index =  7;

        else if ( array[1] == 'x' & array[7] == 'x' & array[4] == 'i')
            index =  4;

        else if ( array[4] == 'x' & array[7] == 'x' & array[1] == 'i' )
            index =  1;


        else if ( array[2] == 'x' & array[5] == 'x' & array[8] == 'i')
            index =  8;

        else if ( array[2] == 'x' & array[8] == 'x' & array[5] == 'i')
            index =  5;

        else if ( array[5] == 'x' & array[8] == 'x' & array[2] == 'i' )
            index =  2;



        else if ( array[0] == 'x' & array[4] == 'x' & array[8] == 'i' )
            index =  8;

        else if ( array[0] == 'x' & array[8] == 'x' & array[4] == 'i' )
            index =  4;

        else if ( array[4] == 'x' & array[8] == 'x' & array[0] == 'i' ){index = 0; flag =true; }



        else if ( array[2] == 'x' & array[4] == 'x' & array[6] == 'i')
            index =  6;

        else if ( array[2] == 'x' & array[6] == 'x' & array[2] == 'i' )
            index =  4;

        else if ( array[4] == 'x' & array[6] == 'x' & array[2] == 'i')
            index =  2;

        return index;

    }

    public int attackMove(char array[]){
        int index = 0;

        if(array[0] == 'o' & array[1] == 'o' & array[2] == 'i')
            index= 2;

        else if(array[0] == 'o' & array[2] == 'o' & array[1] == 'i')
            index = 1;

        else if(array[1] == 'o' & array[2] == 'o' & array[0] == 'i'){index = 0; flag =true; }



        else if(array[3] == 'o' & array[4] == 'o' & array[5] == 'i')
            index = 5;

        else if(array[3] == 'o' & array[5] == 'o' & array[4] == 'i')
            index = 4;

        else if(array[4] == 'o' & array[5] == 'o' & array[3] == 'i')
            index = 3;

        else if(array[6] == 'o' & array[7] == 'o' & array[8] == 'i')
            index = 8;

        else if(array[6] == 'o' & array[8] == 'o' & array[7] == 'i' )
            index = 7;

        else if(array[7] == 'o' & array[8] == 'o' & array[6] == 'i')
            index = 6;



        else if( array[0] == 'o' & array[3] == 'o' & array[6] == 'i' )
            index =  6;

        else if( array[0] == 'o' & array[6] == 'o' & array[3] == 'i' )
            index =  3;

        else if ( array[3] == 'o' & array[6] == 'o' & array[0] == 'i'){index = 0; flag =true; }



        else if ( array[1] == 'o' & array[4] == 'o' & array[7] == 'i' )
            index =  7;

        else if ( array[1] == 'o' & array[7] == 'o' & array[4] == 'i')
            index =  4;

        else if ( array[4] == 'o' & array[7] == 'o' & array[1] == 'i' )
            index =  1;


        else if ( array[2] == 'o' & array[5] == 'o' & array[8] == 'i')
            index =  8;

        else if ( array[2] == 'o' & array[8] == 'o' & array[5] == 'i')
            index =  5;

        else if ( array[5] == 'o' & array[8] == 'o' & array[2] == 'i' )
            index =  2;



        else if ( array[0] == 'o' & array[4] == 'o' & array[8] == 'i' )
            index =  8;

        else if ( array[0] == 'o' & array[8] == 'o' & array[4] == 'i' )
            index =  4;

        else if ( array[4] == 'o' & array[8] == 'o' & array[0] == 'i' ){index = 0; flag =true; }



        else if ( array[2] == 'o' & array[4] == 'o' & array[6] == 'i')
            index =  6;

        else if ( array[2] == 'o' & array[6] == 'o' & array[2] == 'i' )
            index =  4;

        else if ( array[4] == 'o' & array[6] == 'o' & array[2] == 'i')
            index =  2;

        return index;

    }

}

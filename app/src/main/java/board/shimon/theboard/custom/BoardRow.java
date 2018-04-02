package board.shimon.theboard.custom;

import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shimon on 01/04/2018.
 */

public class BoardRow {
    //for the text in the buttons
    private String b1,b2,b3,b4,b5;
    //save the factors for each button
    private List<Integer> factors1,factors2,factors3,factors4,factors5;
    //save the button for change the Background
    Button button1,button2,button3,button4,button5;

    /**
     * object for one row in the view
     * @param start number as int
     */
    public BoardRow(int start){
        //create 5 number start from start value
        b1 = String.valueOf(start);
        b2 = String.valueOf(start+1);
        b3 = String.valueOf(start+2);
        b4 = String.valueOf(start+3);
        b5 = String.valueOf(start+4);

        //calc and save the factors
        factors1 = factorsOf(start);
        factors2 = factorsOf(start + 1);
        factors3 = factorsOf(start + 2);
        factors4 = factorsOf(start + 3 );
        factors5 = factorsOf(start + 4 );
    }

    /**
     * calc the factor of the input number
     * @param val
     * @return
     */
    private List<Integer> factorsOf (int val) {
        List<Integer> factors  = new ArrayList<Integer>();
        for(int i=2; i <= val/2; i++)
        {
            if(val % i == 0)
            {
                factors.add(i);
            }
        }
        return factors;
    }

    public Button getButton1() {
        return button1;
    }

    public void setButton1(Button button1) {
        this.button1 = button1;
    }

    public Button getButton2() {
        return button2;
    }

    public void setButton2(Button button2) {
        this.button2 = button2;
    }

    public Button getButton3() {
        return button3;
    }

    public void setButton3(Button button3) {
        this.button3 = button3;
    }

    public Button getButton4() {
        return button4;
    }

    public void setButton4(Button button4) {
        this.button4 = button4;
    }

    public Button getButton5() {
        return button5;
    }

    public void setButton5(Button button5) {
        this.button5 = button5;
    }

    public List<Integer> getFactors1() {
        return factors1;
    }

    public void setFactors1(List<Integer> factors1) {
        this.factors1 = factors1;
    }

    public List<Integer> getFactors2() {
        return factors2;
    }

    public void setFactors2(List<Integer> factors2) {
        this.factors2 = factors2;
    }

    public List<Integer> getFactors3() {
        return factors3;
    }

    public void setFactors3(List<Integer> factors3) {
        this.factors3 = factors3;
    }

    public List<Integer> getFactors4() {
        return factors4;
    }

    public void setFactors4(List<Integer> factors4) {
        this.factors4 = factors4;
    }

    public List<Integer> getFactors5() {
        return factors5;
    }

    public void setFactors5(List<Integer> factors5) {
        this.factors5 = factors5;
    }

    public String getB1() {
        return b1;
    }

    public void setB1(String b1) {
        this.b1 = b1;
    }

    public String getB2() {
        return b2;
    }

    public void setB2(String b2) {
        this.b2 = b2;
    }

    public String getB3() {
        return b3;
    }

    public void setB3(String b3) {
        this.b3 = b3;
    }

    public String getB4() {
        return b4;
    }

    public void setB4(String b4) {
        this.b4 = b4;
    }

    public String getB5() {
        return b5;
    }

    public void setB5(String b5) {
        this.b5 = b5;
    }
}

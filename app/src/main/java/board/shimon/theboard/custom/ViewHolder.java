package board.shimon.theboard.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.tooltip.Tooltip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import board.shimon.theboard.R;

/**
 * Created by shimon on 02/04/2018.
 */

class ViewHolder extends RecyclerView.ViewHolder {
    RecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    View itemView;
    private boolean isSpeakButtonLongPressed;
    //static for handle the scroll reset
    static ArrayList<Button> listOfReletedButton;
    Context applicationContext;

    ViewHolder(View itemView, RecyclerAdapter recyclerAdapter, RecyclerView recyclerView) {
        super(itemView);
        this.recyclerAdapter = recyclerAdapter;
        this.recyclerView = recyclerView;
        this.itemView = itemView;
    }

    /**
     * handle long click for all the buttons
     * save all the buttton that we change them the Background for later rest
     * change only button that are on the screen when long press in happened
     * @param v
     * @param testedList the factor of the clicked tile
     */
    private void handleLongClick(View v, List<Integer> testedList){
        v.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.border_green));
        int visibleItemCount = recyclerView.getChildCount();
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        isSpeakButtonLongPressed = true;
        listOfReletedButton = new ArrayList<Button>();

        //go over all the button on the screen and check if we need to chabge the Background of them
        for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++){
            BoardRow boardRowToComper = recyclerAdapter.myList.get(i);
            checkAndChangeBackgroundIfNeed(testedList, applicationContext,boardRowToComper.getFactors1(),boardRowToComper.getButton1());
            checkAndChangeBackgroundIfNeed(testedList, applicationContext,boardRowToComper.getFactors2(),boardRowToComper.getButton2());
            checkAndChangeBackgroundIfNeed(testedList, applicationContext,boardRowToComper.getFactors3(),boardRowToComper.getButton3());
            checkAndChangeBackgroundIfNeed(testedList, applicationContext,boardRowToComper.getFactors4(),boardRowToComper.getButton4());
            checkAndChangeBackgroundIfNeed(testedList, applicationContext,boardRowToComper.getFactors5(),boardRowToComper.getButton5());
        }
    }

    private void handleOnTouchRelease(View v, MotionEvent event, List<Integer> factors1, String number ){
        v.onTouchEvent(event);
        // We're only interested in when the button is released.
        if (event.getAction() == MotionEvent.ACTION_UP ) {
            // We're only interested in anything if our speak button is currently pressed.
            if (isSpeakButtonLongPressed) {
                // Do something when the button is released.
                isSpeakButtonLongPressed = false;
                v.setBackgroundDrawable((Drawable) v.getTag());
                Tooltip.Builder builder = new Tooltip.Builder(v, R.style.Tooltip)
                        .setCancelable(true)
                        .setDismissOnClick(false)
                        .setCornerRadius(20f)
                        .setGravity(Gravity.TOP)
                        .setText("Factors (excluding 1 and "+number+") for:"+number+"\n"+factors1.toString().replaceAll("[\\[\\],]"," "));
                builder.show();
                for (Button b : listOfReletedButton) {
                    b.setBackgroundDrawable((Drawable) b.getTag());
                }
                listOfReletedButton.clear();
            }
        }
    }

    public void setDividedList(final Context applicationContext, final BoardRow boardRow) {
        this.applicationContext = applicationContext;
        boardRow.setButton1((Button) itemView.findViewById(R.id.Button1));
        boardRow.setButton2((Button) itemView.findViewById(R.id.Button2));
        boardRow.setButton3((Button) itemView.findViewById(R.id.Button3));
        boardRow.setButton4((Button) itemView.findViewById(R.id.Button4));
        boardRow.setButton5((Button) itemView.findViewById(R.id.Button5));
        boardRow.getButton1().setText(boardRow.getB1());
        boardRow.getButton2().setText(boardRow.getB2());
        boardRow.getButton3().setText(boardRow.getB3());
        boardRow.getButton4().setText(boardRow.getB4());
        boardRow.getButton5().setText(boardRow.getB5());
        setButtonStyle(boardRow.getButton1(),boardRow.getFactors1(),boardRow.getB1(),applicationContext, boardRow);
        setButtonStyle(boardRow.getButton2(),boardRow.getFactors2(),boardRow.getB2(),applicationContext, boardRow);
        setButtonStyle(boardRow.getButton3(),boardRow.getFactors3(),boardRow.getB3(),applicationContext, boardRow);
        setButtonStyle(boardRow.getButton4(),boardRow.getFactors4(),boardRow.getB4(),applicationContext, boardRow);
        setButtonStyle(boardRow.getButton5(),boardRow.getFactors5(),boardRow.getB5(),applicationContext, boardRow);

        boardRow.getButton1().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongClick(v,boardRow.getFactors1());
                return true;
            }
        });
        boardRow.getButton1().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleOnTouchRelease(v,event,boardRow.getFactors1(),boardRow.getB1());
                return true;
            }
        });
        boardRow.getButton2().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongClick(v,boardRow.getFactors2());
                return true;
            }
        });
        boardRow.getButton2().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleOnTouchRelease(v,event,boardRow.getFactors2(),boardRow.getB2());
                return true;
            }
        });
        boardRow.getButton3().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongClick(v,boardRow.getFactors3());
                return false;
            }
        });
        boardRow.getButton3().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleOnTouchRelease(v,event,boardRow.getFactors3(),boardRow.getB3());
                return true;
            }
        });
        boardRow.getButton4().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongClick(v,boardRow.getFactors4());
                return false;
            }
        });
        boardRow.getButton4().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleOnTouchRelease(v,event,boardRow.getFactors4(),boardRow.getB4());
                return true;
            }
        });
        boardRow.getButton5().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongClick(v,boardRow.getFactors5());
                return false;
            }
        });
        boardRow.getButton5().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleOnTouchRelease(v,event,boardRow.getFactors5(),boardRow.getB5());
                return true;
            }
        });
    }

    private void checkAndChangeBackgroundIfNeed(List<Integer> testList, Context applicationContext, List<Integer> factors,Button buttonToChange) {
        Set<Integer> fullDivided = new HashSet<Integer>();
        fullDivided.addAll(factors);
        fullDivided.addAll(testList);
        if(fullDivided.size() != factors.size() + testList.size()){
            buttonToChange.setBackgroundDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.border_green));
            listOfReletedButton.add(buttonToChange);
        }
    }

    private void setButtonStyle(Button button1, List<Integer> factors, String number, Context applicationContext, BoardRow boardRow) {
        if(factors.size() == 0 && !(number.equals("0") || number.equals("1"))){
            Drawable drawable = ContextCompat.getDrawable(applicationContext, R.drawable.border_red);
            button1.setBackgroundDrawable(drawable);
            button1.setTag(drawable);
        }
        else
        {
            Drawable drawable = ContextCompat.getDrawable(applicationContext, R.drawable.border);
            button1.setBackgroundDrawable(drawable);
            button1.setTag(drawable);
        }
    }
}
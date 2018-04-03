package board.shimon.theboard.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import board.shimon.theboard.R;

/**
 * Created by shimon on 02/04/2018.
 */

class ViewHolder extends RecyclerView.ViewHolder {
    private final RecyclerAdapter recyclerAdapter;
    private final RecyclerView recyclerView;
    private final View itemView;
    private boolean isSpeakButtonLongPressed;
    //static for handle the scroll reset
    public static ArrayList<Button> listOfReletedButton;
    private Context applicationContext;
    private static PopupWindow popupWindow;

    ViewHolder(View itemView, RecyclerAdapter recyclerAdapter, RecyclerView recyclerView) {
        super(itemView);
        this.recyclerAdapter = recyclerAdapter;
        this.recyclerView = recyclerView;
        this.itemView = itemView;
    }

    /**
     * handle long click for all the buttons
     * save all the button that we change them the Background for later rest
     * change only button that are on the screen when long press in happened
     * ans show the popup window
     * @param v
     * @param testedList the factor of the clicked tile
     */
    private void handleLongClick(View v, List<Integer> testedList , String number ){
        //show the popup window and set the text
        LayoutInflater layoutInflater= (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.popup_windows, null);
        TextView textViewHeader = container.findViewById(R.id.textViewMessage);
        textViewHeader.setText( String.format( "Factors (excluding 1 and %s) for: %s", number, number ) );
        textViewHeader.measure(0, 0);
        TextView textViewFactors = container.findViewById(R.id.textViewFactors);
        //for remove '[' ']' ',' replace to space and remove first space
        textViewFactors.setText(testedList.toString().replaceAll("[\\[\\],]"," ").replaceFirst(" ",""));
        popupWindow = new PopupWindow(container,textViewHeader.getMeasuredWidth()+30,LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (int)v.getX() , (int)((View)v.getParent()).getY() + 30);


        v.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.border_green));
        int visibleItemCount = recyclerView.getChildCount();
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        isSpeakButtonLongPressed = true;
        listOfReletedButton = new ArrayList<Button>();
        listOfReletedButton.add( (Button) v );
        //go over all the button on the screen and check if we need to change the Background of them
        for (int i = firstVisibleItem; i < firstVisibleItem + visibleItemCount; i++){
            BoardRow boardRowToCompare = recyclerAdapter.myList.get(i);
            checkAndChangeBackgroundIfNeed(testedList, applicationContext,boardRowToCompare.getFactors1(),boardRowToCompare.getButton1());
            checkAndChangeBackgroundIfNeed(testedList, applicationContext,boardRowToCompare.getFactors2(),boardRowToCompare.getButton2());
            checkAndChangeBackgroundIfNeed(testedList, applicationContext,boardRowToCompare.getFactors3(),boardRowToCompare.getButton3());
            checkAndChangeBackgroundIfNeed(testedList, applicationContext,boardRowToCompare.getFactors4(),boardRowToCompare.getButton4());
            checkAndChangeBackgroundIfNeed(testedList, applicationContext,boardRowToCompare.getFactors5(),boardRowToCompare.getButton5());
        }
    }

    /**
     * for handle the end of the long click if long click is happened
     * and set te Background to the default Background on the buttons that change
     * hide the popup window
     * @param v
     * @param event
     */
    private void handleOnTouchRelease(View v, MotionEvent event){
        v.onTouchEvent(event);

        // check if this action up .
        if (event.getAction() == MotionEvent.ACTION_UP ) {
            // to see if the button is press.
            if (isSpeakButtonLongPressed) {
                ViewHolder.hidePopupWindow();

                isSpeakButtonLongPressed = false;
                v.setBackground((Drawable) v.getTag());

                //back to default Background
                for (Button b : listOfReletedButton) {
                    b.setBackground((Drawable) b.getTag());
                }
                //clear the tile list that change
                listOfReletedButton.clear();
            }
        }
    }

    /**
     * init all the information that we need for the boar row
     * save the buttons
     * set the text of the buttons
     * set the Background and default Background in tag
     * set Listener for click and long click end
     * @param applicationContext
     * @param boardRow
     */
    public void initBoardRow(final Context applicationContext, final BoardRow boardRow) {
        this.applicationContext = applicationContext;
        //save the buttons
        boardRow.setButton1((Button) itemView.findViewById(R.id.Button1));
        boardRow.setButton2((Button) itemView.findViewById(R.id.Button2));
        boardRow.setButton3((Button) itemView.findViewById(R.id.Button3));
        boardRow.setButton4((Button) itemView.findViewById(R.id.Button4));
        boardRow.setButton5((Button) itemView.findViewById(R.id.Button5));
        //set the text of the buttons
        boardRow.getButton1().setText(boardRow.getB1());
        boardRow.getButton2().setText(boardRow.getB2());
        boardRow.getButton3().setText(boardRow.getB3());
        boardRow.getButton4().setText(boardRow.getB4());
        boardRow.getButton5().setText(boardRow.getB5());
        //set the Background and default Background in tag
        setButtonStyle(boardRow.getButton1(),boardRow.getFactors1(),boardRow.getB1(),applicationContext);
        setButtonStyle(boardRow.getButton2(),boardRow.getFactors2(),boardRow.getB2(),applicationContext);
        setButtonStyle(boardRow.getButton3(),boardRow.getFactors3(),boardRow.getB3(),applicationContext);
        setButtonStyle(boardRow.getButton4(),boardRow.getFactors4(),boardRow.getB4(),applicationContext);
        setButtonStyle(boardRow.getButton5(),boardRow.getFactors5(),boardRow.getB5(),applicationContext);
        //set Listener for click and long click end
        boardRow.getButton1().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongClick(v,boardRow.getFactors1(),boardRow.getB1());
                return true;
            }
        });
        boardRow.getButton1().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleOnTouchRelease(v,event);
                return true;
            }
        });
        boardRow.getButton2().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongClick(v,boardRow.getFactors2(),boardRow.getB2());
                return true;
            }
        });
        boardRow.getButton2().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleOnTouchRelease(v,event);
                return true;
            }
        });
        boardRow.getButton3().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongClick(v,boardRow.getFactors3(),boardRow.getB3());
                return false;
            }
        });
        boardRow.getButton3().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleOnTouchRelease(v,event);
                return true;
            }
        });
        boardRow.getButton4().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongClick(v,boardRow.getFactors4(),boardRow.getB4());
                return false;
            }
        });
        boardRow.getButton4().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleOnTouchRelease(v,event);
                return true;
            }
        });
        boardRow.getButton5().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongClick(v,boardRow.getFactors5(),boardRow.getB5());
                return false;
            }
        });
        boardRow.getButton5().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                handleOnTouchRelease(v,event);
                return true;
            }
        });
    }

    /**
     * check if we have one or more related factors
     * if we have set the Background to green and add this button to the list of button change
     * @param testList
     * @param applicationContext
     * @param factors
     * @param buttonToChange
     */
    private void checkAndChangeBackgroundIfNeed(List<Integer> testList, Context applicationContext, List<Integer> factors,Button buttonToChange) {
        Set<Integer> fullDivided = new HashSet<Integer>();
        fullDivided.addAll(factors);
        fullDivided.addAll(testList);
        if(fullDivided.size() != factors.size() + testList.size()){
            buttonToChange.setBackground(ContextCompat.getDrawable(applicationContext, R.drawable.border_green));
            listOfReletedButton.add(buttonToChange);
        }
    }

    /**
     * set and determine the Background and default Background in tag
     * @param button1
     * @param factors
     * @param number
     * @param applicationContext
     */
    private void setButtonStyle(Button button1, List<Integer> factors, String number, Context applicationContext) {
        if(factors.size() == 0 && !(number.equals("0") || number.equals("1"))){
            Drawable drawable = ContextCompat.getDrawable(applicationContext, R.drawable.border_red);
            button1.setBackground(drawable);
            button1.setTag(drawable);
        }
        else
        {
            Drawable drawable = ContextCompat.getDrawable(applicationContext, R.drawable.border);
            button1.setBackground(drawable);
            button1.setTag(drawable);
        }
    }

    /**
     * hide the popup window if exist
     */
    public static void hidePopupWindow() {
        if(popupWindow != null)
            popupWindow.dismiss();
    }
}
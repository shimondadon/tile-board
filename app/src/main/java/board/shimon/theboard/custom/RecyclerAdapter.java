package board.shimon.theboard.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.tooltip.Tooltip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import board.shimon.theboard.R;

/**
 * Created by shimon on 01/04/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    //the list of the rows
    protected List<BoardRow> myList;
    private Context applicationContext;
    private RecyclerView recyclerView;

    public RecyclerAdapter(List<BoardRow> myList, Context applicationContext, RecyclerView recyclerView) {
        this.myList = myList;
        this.applicationContext = applicationContext;
        this.recyclerView = recyclerView;
    }

    /**
     * create costume ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.board_row, parent, false);
        return new ViewHolder(view,this,recyclerView);
    }

    /**
     * Bind ViewHolder by position set all the important information
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BoardRow boardRow = myList.get(position);
        holder.setDividedList(applicationContext,boardRow);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}

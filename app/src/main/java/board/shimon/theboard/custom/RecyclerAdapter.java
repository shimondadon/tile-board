package board.shimon.theboard.custom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import board.shimon.theboard.R;

/**
 * Created by shimon on 01/04/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
    //the list of the rows
    final List<BoardRow> myList;
    private final Context applicationContext;
    private final RecyclerView recyclerView;

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
        holder.initBoardRow(applicationContext,boardRow);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}

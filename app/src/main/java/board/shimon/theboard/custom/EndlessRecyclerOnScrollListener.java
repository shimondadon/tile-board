package board.shimon.theboard.custom;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
//    public static String TAG = EndlessRecyclerOnScrollListener.class.getSimpleName();

    /**
     * number of item in the list after last load
     */
    private int mPreviousTotal = 0;
    /**
     * True if we are still waiting for the last set of data to load.
     */
    private boolean mLoading = true;

    private RecyclerAdapter recyclerAdapter;

    /**
     * save the recyclerAdapter for refresh the view if scroll
     * @param recyclerAdapter
     */
    public EndlessRecyclerOnScrollListener(RecyclerAdapter recyclerAdapter) {
        this.recyclerAdapter = recyclerAdapter;
    }

    /**
     * refresh the view if scroll and you have press long click on a tile and remove the selections
     * @param recyclerView
     * @param newState
     */
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if(newState == 1 && ViewHolder.listOfReletedButton != null && !ViewHolder.listOfReletedButton.isEmpty()){
            recyclerAdapter.notifyDataSetChanged();
            ViewHolder.listOfReletedButton.clear();
        }
    }

    /**
     * load a new data if you scroll to the end of the data the loaded
     * @param recyclerView
     * @param dx
     * @param dy
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = recyclerView.getLayoutManager().getItemCount();
        int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

        //set to True if we are still waiting for the last set of data to load.
        //else save the new totalItemCount as prev
        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
            }
        }
        int visibleThreshold = 5;
        //if we got to the end of the list with Threshold of 50
        //for we start load before the end
        if (!mLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)+50) {
            // End has been reached
            onLoadMore();
            mLoading = true;
        }
    }

    public abstract void onLoadMore();
}
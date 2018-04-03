package board.shimon.theboard;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import board.shimon.theboard.custom.BoardRow;
import board.shimon.theboard.custom.EndlessRecyclerOnScrollListener;
import board.shimon.theboard.custom.RecyclerAdapter;

public class MainActivity extends AppCompatActivity{
    private List<BoardRow> mStringList;
    private RecyclerAdapter recyclerAdapter;
    private int mLoadedItems = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);

        recyclerView.setLayoutManager(linearLayoutManager);
        //this for remove the spaces between the rows
        recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, 0));
        mStringList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(mStringList,getApplicationContext(),recyclerView);
        recyclerView.setAdapter(recyclerAdapter);
        addDataToList();

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                addDataToList();
            }
        });
    }

    /**
     * add new board row to the view 500 line each time
     */
    private void addDataToList() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            for (int i = 0; i <= 500; i++) {
                mStringList.add(new BoardRow(mLoadedItems));
                mLoadedItems += 5;
            }
            recyclerAdapter.notifyDataSetChanged();
            }
        }, 10);

    }
}

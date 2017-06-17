package comluoexample.pulltorefresh_test2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements PullToRefreshListener{

    private PullToRefreshRecyclerView pullToRefreshRV;
    private RecyclerViewAdapter adapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPullToRefresh();
    }


    public void initPullToRefresh(){

        list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("PullToRefreshLayout"+i);
        }

        pullToRefreshRV = (PullToRefreshRecyclerView) findViewById(R.id.pullToRefreshRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        pullToRefreshRV.setLayoutManager(layoutManager);

        adapter = new RecyclerViewAdapter(list);
        pullToRefreshRV.setAdapter(adapter);

        //添加HeaderView
        View headView = View.inflate(this, R.layout.layout_head_view, null);
        pullToRefreshRV.addHeaderView(headView);
        //添加FooterView
        View footerView = View.inflate(this, R.layout.layout_foot_view, null);
        pullToRefreshRV.addFooterView(footerView);

        //是否开启下拉刷新功能
        pullToRefreshRV.setPullRefreshEnabled(true);
       //是否开启上拉加载功能
        pullToRefreshRV.setLoadingMoreEnabled(true);
        //设置是否显示上次刷新的时间
        pullToRefreshRV.displayLastRefreshTime(true);
        //设置刷新回调
        pullToRefreshRV.setPullToRefreshListener(this);
        //主动触发下拉刷新操作
        pullToRefreshRV.onRefresh();
    }

    @Override
    public void onRefresh() {
        pullToRefreshRV.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshRV.setRefreshComplete();
                //模拟没有数据的情况
               // list.clear();
                adapter.notifyDataSetChanged();
            }
        }, 3000);
    }

    @Override
    public void onLoadMore() {
        pullToRefreshRV.postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshRV.setLoadMoreComplete();
                //模拟加载数据的情况
                int size = list.size();
                for (int i = size; i < size + 4; i++) {
                    list.add("" + i + i + i + i);
                }
                adapter.notifyDataSetChanged();
            }
        }, 3000);
    }
}

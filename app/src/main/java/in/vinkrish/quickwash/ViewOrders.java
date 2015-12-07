package in.vinkrish.quickwash;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.vinkrish.quickwash.data.Order;
import in.vinkrish.quickwash.data.QuickWashCRUD;

/**
 * Created by vinkrish on 06/12/15.
 */
public class ViewOrders extends android.support.v4.app.Fragment{
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_orders, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        List<Order> orderList = QuickWashCRUD.getOrder(getActivity());

        adapter = new MyRecyclerAdapter(getActivity(), orderList);
        mRecyclerView.setAdapter(adapter);

        return view;
    }

}

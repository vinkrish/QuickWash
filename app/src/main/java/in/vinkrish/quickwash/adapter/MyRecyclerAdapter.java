package in.vinkrish.quickwash.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.vinkrish.quickwash.R;
import in.vinkrish.quickwash.data.Order;

/**
 * Created by vinkrish on 07/12/15.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {
    private Context context;
    private List<Order> orderList;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_row, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Order orderItem = orderList.get(i);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");

        customViewHolder.dateTV.setText(orderItem.getDate());
        customViewHolder.serviceTV.setText(orderItem.getService());
        customViewHolder.nameTV.setText(orderItem.getName());
        customViewHolder.mobileTV.setText(orderItem.getMobile());
        customViewHolder.addressTV.setText(orderItem.getAddress());
        customViewHolder.pincodeTV.setText(orderItem.getPincode());

        customViewHolder.dateTV.setTypeface(tf);
        customViewHolder.nameTV.setTypeface(tf);
        customViewHolder.mobileTV.setTypeface(tf);
        customViewHolder.addressTV.setTypeface(tf);
        customViewHolder.pincodeTV.setTypeface(tf);
    }

    @Override
    public int getItemCount() {
        return (null != orderList ? orderList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView dateTV;
        TextView serviceTV;
        TextView nameTV;
        TextView mobileTV;
        TextView addressTV;
        TextView pincodeTV;

        CustomViewHolder(View view) {
            super(view);

            this.dateTV = (TextView) view.findViewById(R.id.date_tv);
            this.serviceTV = (TextView) view.findViewById(R.id.service_tv);
            this.nameTV = (TextView) view.findViewById(R.id.name_tv);
            this.mobileTV = (TextView) view.findViewById(R.id.mobile_tv);
            this.addressTV = (TextView) view.findViewById(R.id.address_tv);
            this.pincodeTV = (TextView) view.findViewById(R.id.pincode_tv);
        }

    }

}

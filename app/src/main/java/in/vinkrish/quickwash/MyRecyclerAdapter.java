package in.vinkrish.quickwash;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.vinkrish.quickwash.data.Order;

/**
 * Created by vinkrish on 07/12/15.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder> {
    private List<Order> orderList;
    private Context mContext;
    private LayoutInflater inflater;

    public MyRecyclerAdapter(Context context, List<Order> orderList) {
        this.orderList = orderList;
        this.mContext = context;
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

        customViewHolder.dateTV.setText(orderItem.getDate());
        customViewHolder.serviceTV.setText(orderItem.getService());
        customViewHolder.nameTV.setText(orderItem.getName());
        customViewHolder.mobileTV.setText(orderItem.getMobile());
        customViewHolder.addressTV.setText(orderItem.getAddress());
        customViewHolder.pincodeTV.setText(orderItem.getPincode());
    }

    @Override
    public int getItemCount() {
        return (null != orderList ? orderList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView dateTV;
        protected TextView serviceTV;
        protected TextView nameTV;
        protected TextView mobileTV;
        protected TextView addressTV;
        protected TextView pincodeTV;

        public CustomViewHolder(View view) {
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

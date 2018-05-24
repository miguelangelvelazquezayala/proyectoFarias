package mx.niluxer.store.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mx.niluxer.store.R;
import mx.niluxer.store.data.model.Orders;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    private List<Orders> mItems;
    private Context mContext;
    private OrdersItemListener mItemListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        public TextView tvOrdersMetodoPago, tvOrdersFecha,tvOrdersCostoTotal;
        public ImageView ivOrdersImage;
        OrdersItemListener mItemListener;

        public ViewHolder(View itemView, OrdersItemListener postItemListener) {
            super(itemView);
            tvOrdersMetodoPago  = (TextView) itemView.findViewById(R.id.tvOrdersMetodoPago);
            tvOrdersFecha = (TextView) itemView.findViewById(R.id.tvOrdersFecha);
            tvOrdersCostoTotal = (TextView) itemView.findViewById(R.id.tvOrdersCostoTotal);


            this.mItemListener = postItemListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Orders item = getItem(getAdapterPosition());
            this.mItemListener.onOrdersClick(item.getId());

            notifyDataSetChanged();
        }

        @Override
        public boolean onLongClick(View v) {
            Orders item = getItem(getAdapterPosition());
            this.mItemListener.onOrdersLongClick(item);

            //notifyDataSetChanged();
            return true;
        }
    }

    public InfoAdapter(Context context, List<Orders> posts, OrdersItemListener itemListener) {
        mItems = posts;
        mContext = context;
        mItemListener = itemListener;
    }

    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View OrdersView = inflater.inflate(R.layout.info_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(OrdersView, this.mItemListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(InfoAdapter.ViewHolder holder, int position) {

        Orders item = mItems.get(position);
        TextView textView1 = holder.tvOrdersMetodoPago;
        TextView textView2 = holder.tvOrdersFecha;
        TextView textView3 = holder.tvOrdersCostoTotal;

        textView1.setText(item.getPaymentMethod());
        textView2.setText(item.getDatePaid());
        textView3.setText(item.getTotal());


        //Picasso.get().load(item.getImages().get(0).getSrc().replace("https", "http")).transform(new CropCircleTransformation()).resize(150, 150).into(imageView);


        /*OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        Picasso picasso = new Picasso.Builder(mContext)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
        picasso.get().load("http://192.168.1.66/~niluxer/wordpress/wp-content/uploads/2018/05/long-sleeve-tee-2.jpg").into(holder.ivOrdersImage, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateOrders(List<Orders> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    private Orders getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface OrdersItemListener {
        void onOrdersClick(long id);
        void onOrdersLongClick(Orders Orders);
    }
}

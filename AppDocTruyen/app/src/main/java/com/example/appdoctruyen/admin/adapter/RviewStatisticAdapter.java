package com.example.appdoctruyen.admin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.admin.model.ItemStatisticRview;

import java.util.ArrayList;
import java.util.List;

public class RviewStatisticAdapter extends RecyclerView.Adapter<RviewStatisticAdapter.HomeViewHolder>{
    // list la data cho r_view
    private List<ItemStatisticRview> list;
    // ItemListener de lang nghe su kien chon
    private ItemListener itemListener;
    // contructor
    public RviewStatisticAdapter() {
        list= new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    // ham xu ly khi list data thay doi
    public void setList(List<ItemStatisticRview> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public ItemStatisticRview getItem(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rview_thongkedoanhthu,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        ItemStatisticRview item = list.get(position);
        holder.date.setText( item.getDate());
        holder.price.setText("Tổng thu :" + item.getPrice() +" vnđ");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView date,price;
        public HomeViewHolder(@NonNull View view) {
            super(view);
            date = view.findViewById(R.id.tv_date);
            price = view.findViewById(R.id.tv_price);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null){
                itemListener.onItemClick(view,getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}


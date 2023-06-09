package com.example.appdoctruyen.admin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.admin.model.ItemSubcriptionRview;

import java.util.ArrayList;
import java.util.List;

public class RViewSubcriptionAdapter extends RecyclerView.Adapter<RViewSubcriptionAdapter.HomeViewHolder>{
    // list la data cho r_view
    private List<ItemSubcriptionRview> list;
    // ItemListener de lang nghe su kien chon
    private ItemListener itemListener;
    // contructor
    public RViewSubcriptionAdapter() {
        list= new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    // ham xu ly khi list data thay doi
    public void setList(List<ItemSubcriptionRview> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public ItemSubcriptionRview getItem(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rview_subcription,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        ItemSubcriptionRview item = list.get(position);
        holder.month.setText("Thời hạn " + item.getMonth() + " tháng");
        holder.price.setText("Giá :" + item.getPrice() +" vnđ");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView month,price;
        public HomeViewHolder(@NonNull View view) {
            super(view);
            month = view.findViewById(R.id.tv_month);
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
package com.example.appdoctruyen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.Chap;
import com.example.appdoctruyen.model.Viewed;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterHistory extends RecyclerView.Adapter<RecycleViewAdapterHistory.HomeViewHolder> {
    private List<Viewed> list;
    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }
    public RecycleViewAdapterHistory(){
        list=new ArrayList<>();
    }

    public void setList(List<Viewed> list) {
        this.list = list;
        notifyDataSetChanged();

    }
    public Viewed getItem(int position){
        return list.get(position);
    }
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemhistory,parent,false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Viewed v =list.get(position);
        holder.name.setText(v.getName());
        holder.date.setText(v.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemListener{
        void onItemClick(View view, int position);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name,date;
        public HomeViewHolder(@NonNull View view) {
            super(view);
            name=view.findViewById(R.id.tvchap);
            date=view.findViewById(R.id.tvdate);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(itemListener!=null){
                itemListener.onItemClick(view,getAdapterPosition());
            }
        }
    }
}

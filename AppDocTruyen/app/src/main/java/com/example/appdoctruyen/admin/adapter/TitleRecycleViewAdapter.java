package com.example.appdoctruyen.admin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.Genre;
import com.example.appdoctruyen.model.Title;

import java.util.List;

public class TitleRecycleViewAdapter extends RecyclerView.Adapter<TitleRecycleViewAdapter.ViewHolder>{
    private List<Title> list;
    private ItemListener itemListener;

    public void setList(List<Title> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public Title getTitle(int position){
        return list.get(position);
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_recycleview_title_manager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Title title=list.get(position);
        holder.name.setText(title.getName());
        holder.des.setText(title.getDes());
        if(title.isFee()){
            holder.free.setText("Free");
        }
        else{
            holder.free.setText("Not Free");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, des, free;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.itemRecycleViewTitleName);
            des=itemView.findViewById(R.id.itemRecycleViewTitleDes);
            free=itemView.findViewById(R.id.itemRecycleViewTitleFree);
            itemView.setOnClickListener(this);
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

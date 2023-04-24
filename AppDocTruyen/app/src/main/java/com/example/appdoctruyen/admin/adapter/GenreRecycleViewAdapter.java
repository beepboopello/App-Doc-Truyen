package com.example.appdoctruyen.admin.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreRecycleViewAdapter extends RecyclerView.Adapter<GenreRecycleViewAdapter.ViewHolder>{
    private List<Genre> list;
    private ItemListener itemListener;

    public GenreRecycleViewAdapter() {
        list=new ArrayList<>();
    }
    public Genre getGenre(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycleview_genre_manager, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Genre genre=list.get(position);
        holder.name.setText(genre.getName());
        holder.des.setText(genre.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Genre> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, des;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name_genre);
            des=itemView.findViewById(R.id.des_genre);
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

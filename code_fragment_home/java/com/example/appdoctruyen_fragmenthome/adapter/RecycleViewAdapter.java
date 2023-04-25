package com.example.appdoctruyen_fragmenthome.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen_fragmenthome.R;
import com.example.appdoctruyen_fragmenthome.model.Book;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.HomeViewHolder> {
    private List<Book> list;
    private ItemListener itemListener;
    @NonNull
    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public RecycleViewAdapter(List<Book> list) {
        this.list=list;
    }

    public Book getItem(int posititon){
        return list.get(posititon);
    }

    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itembook,parent,false);

        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        Book book=list.get(position);

        holder.name.setText(book.getName());
        holder.free.setText(book.getFree()?"Miễn phí":"Trả phí");//phai kiem tra nguoi dung da dang ky hay chua
        holder.totalViews.setText(String.valueOf(book.getTotalViews()));
        holder.totalLikes.setText(String.valueOf(book.getTotalLikes()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView name,free,totalViews,totalLikes;
        public HomeViewHolder(@NonNull View view) {
            super(view);
            name=view.findViewById(R.id.tvbook);
            free=view.findViewById(R.id.tvfree);
            totalViews=view.findViewById(R.id.tvread);
            totalLikes=view.findViewById(R.id.tvlike);

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

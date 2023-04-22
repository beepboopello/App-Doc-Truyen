package com.example.appdoctruyen.adapter;

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

public class RecycleViewAdapterGenreList extends RecyclerView.Adapter<RecycleViewAdapterGenreList.HomeViewHolder> {
        private List<Genre> list;
        private ItemListener itemListener;

        public void setItemListener(ItemListener itemListener) {
                this.itemListener = itemListener;
                }
        public RecycleViewAdapterGenreList(){
                list=new ArrayList<>();
                }

        public void setList(List<Genre> list) {
                this.list = list;
                notifyDataSetChanged();

                }
        public Genre getItem(int position){
                return list.get(position);
                }
        public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemgenre,parent,false);

                return new HomeViewHolder(view);
                }

        @Override
        public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        //        holder.itemView.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                Context context = view.getContext();
        //                Intent intent = new Intent(context , ChapActivity.class);
        //                context.startActivity(intent);
        //            }
        //        });
                Genre genre =list.get(position);
                holder.name.setText(genre.getName());

                }

        @Override
        public int getItemCount() {
                return list.size();
                }

        public interface ItemListener{
            void onItemClick(View view, int position);
        }

        public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView name;

            public HomeViewHolder(@NonNull View view) {
                super(view);
                name = view.findViewById(R.id.tvgenre);

                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (itemListener != null) {
                    itemListener.onItemClick(view, getAdapterPosition());
                }
            }
        }
}


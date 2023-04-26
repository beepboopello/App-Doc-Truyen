package com.example.appdoctruyen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appdoctruyen.R;
import com.example.appdoctruyen.model.Chap;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapterChapList extends RecyclerView.Adapter<RecycleViewAdapterChapList.HomeViewHolder> {
    private List<Chap> list;
    private ItemListener itemListener;

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }
    public RecycleViewAdapterChapList(){
        list=new ArrayList<>();
    }

    public void setList(List<Chap> list) {
        this.list = list;
        notifyDataSetChanged();

    }
    public Chap getItem(int position){
        return list.get(position);
    }
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.itemchapter,parent,false);

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
        Chap chap =list.get(position);
        holder.name.setText(chap.getName());
        holder.views.setText("Lượt xem : " + chap.getViews());
        holder.number.setText(chap.getNumber());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemListener{
        void onItemClick(View view, int position);
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView name, number, views;
        public HomeViewHolder(@NonNull View view) {
            super(view);
            name=view.findViewById(R.id.tvchap);
            number = view.findViewById(R.id.number);
            views = view.findViewById(R.id.views);


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

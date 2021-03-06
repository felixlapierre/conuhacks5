package com.example.tunein;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchResultItem extends RecyclerView.Adapter<SearchResultItem.MyViewHolder> {
    private List<String> dataset;
    private View.OnClickListener listener;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    public SearchResultItem(List<String> dataset, View.OnClickListener onclick) {
        this.dataset = dataset;
        this.listener = onclick;
    }

    public SearchResultItem.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView v = new TextView(parent.getContext());
        v.setOnClickListener(listener);
        //TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


}

package com.example.finalcalculatorapp;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ExpressionAdapter extends RecyclerView.Adapter<ExpressionAdapter.ViewHolder> {

    private final List<ExpressionItem> items;
    private final OnItemClickListener listener;

    public ExpressionAdapter(List<ExpressionItem> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.explistentry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnItemClickListener {
        void onItemClick(ExpressionItem item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView expressionTextView;
        TextView valueTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expressionTextView = itemView.findViewById(R.id.exp);
            valueTextView = itemView.findViewById(R.id.value);
        }

        public void bind(final ExpressionItem item, final OnItemClickListener listener) {
            expressionTextView.setText(item.getExpression());
            valueTextView.setText(item.getValue());

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}

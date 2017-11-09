package com.dinuscxj.example.ppdtestdemo;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dinuscxj.example.R;
import com.dinuscxj.example.model.OpenProjectModel;
import com.ppd.refreshhelper.adapter.RecyclerListAdapter;

public  class ItemViewHolder extends RecyclerListAdapter.ViewHolder<OpenProjectModel> {
        private final TextView mTvTitle;
        private final TextView mTvContent;
        private final TextView mTvAuthor;

        private final LinearLayout mLlContentPanel;

        public ItemViewHolder(@NonNull ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item, parent, false));

            mTvTitle = (TextView) itemView.findViewById(R.id.title);
            mTvContent = (TextView) itemView.findViewById(R.id.content);
            mTvAuthor = (TextView) itemView.findViewById(R.id.author);

            mLlContentPanel = (LinearLayout) itemView.findViewById(R.id.content_panel);
        }

        @Override
        public void bind(final OpenProjectModel item, int position) {
            mTvTitle.setText(item.getTitle());
            mTvContent.setText(item.getContent());
            mTvAuthor.setText(item.getAuthor());

            mLlContentPanel.setBackgroundColor(Color.parseColor(item.getColor()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), item.getTitle(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }
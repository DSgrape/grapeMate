package com.example.grape;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class commentAdapter extends RecyclerView.Adapter<commentAdapter.ViewHolder> {
    public static ArrayList<comment> items = new ArrayList<>();
    private Context context;

    public commentAdapter(ArrayList<comment> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.comment, parent, false);
        return new ViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(@NonNull commentAdapter.ViewHolder holder, int position) {
        comment item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nickname;
        private TextView content;
        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            nickname = itemView.findViewById(R.id.commnet_name);// 닉네임
            content = itemView.findViewById(R.id.tv_comment);   // 내용
            //작성시간 createAt
        }

        void setItem(comment data){
            nickname.setText(data.getNickname());
            content.setText(data.getContent());
            // 작성시간 createAt 필요
        }
    }
}

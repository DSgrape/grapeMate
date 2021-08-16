package com.example.grape;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ViewHolder> {
    public static ArrayList<chat> items = new ArrayList<>();
    private Context context;
    /*
        public chatListAdapter(ArrayList<ChattingRoom> items, Context context) {
            this.items = items;
            this.context = context;
        }
    */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.chat, parent, false);
        return new ViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        chat item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView chatContent;
        private FrameLayout layout;
        private Context context;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context=context;
            chatContent = itemView.findViewById(R.id.chat_content);// 내용
            layout=itemView.findViewById(R.id.chat_layout);//레이아웃->위치조정

        }

        void setItem(chat data){
            if(data.getType()==0) {
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.FILL_PARENT);
                params.gravity = Gravity.RIGHT;
                params.rightMargin=15;

                chatContent.setLayoutParams(params);
                chatContent.setText(data.getChatContent());
            }else if(data.getType()==1){
                chatContent.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.yourchat));
                chatContent.setText(data.getChatContent());
            }
        }
    }
}
package com.example.grape;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class chatListAdapter extends RecyclerView.Adapter<chatListAdapter.ViewHolder> {
    public static ArrayList<ChattingRoom> items = new ArrayList<>();
    private Context context;

    public chatListAdapter(ArrayList<ChattingRoom> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.chating_list_room, parent, false);
        return new ViewHolder(itemView,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChattingRoom item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, chat;
        private de.hdodenhof.circleimageview.CircleImageView image;
        private ChattingRoom data;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            chat = itemView.findViewById(R.id.tv_chat);

            //클릭 이벤트
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity main = (MainActivity) context;
                    main.toChatting(data.getNickname(), data.getCategory(), data.getTitle(), data.getPostId());
                }
            });

        }

        void setItem(ChattingRoom data){
            this.data = data;

            chat.setText("\u003c" + data.getCategory() + "\u003e " + data.getTitle());  //<카테고리>타이틀
        }
    }
}
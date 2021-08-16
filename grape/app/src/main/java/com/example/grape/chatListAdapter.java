package com.example.grape;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class chatListAdapter extends RecyclerView.Adapter<chatListAdapter.ViewHolder> {
    public static ArrayList<ChattingRoom> items = new ArrayList<>();
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
        private TextView name;
        private de.hdodenhof.circleimageview.CircleImageView image;
        private ChattingRoom data;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_chat);// 닉네임

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity main = (MainActivity) context;
                    main.toChatting(data.getChatName(), data.getCategory(), data.getTitle(), data.getPostId());
                }
            });

            FirebaseDatabase.getInstance().getReference().child("grapeMate/UserAccount").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    items.clear();
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                        //items.add(dataSnapshot.getValue(chat.class));
                        // users 안에 아이디 가져와야함
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        void setItem(ChattingRoom data){
            this.data=data;
            name.setText(data.getChatName());
            //image.set????(data.getImage());
        }
    }
}
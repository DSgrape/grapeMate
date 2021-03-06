package com.example.grape;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chatting extends Fragment {
    ImageButton back;
    ImageButton finish;
    TextView chatName;
    TextView title;
    TextView type;
    TextView tv_nickname;
    TextView ChatName;

    EditText etSendMessage;
    ImageButton btnSendMessage;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private chatAdapter adapter;

    private ArrayList<chat> items = new ArrayList<>();

    DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("grapeMate/chat");

    String nickname;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String postId;
    String firstDestinationUid = "";
    String chatRoomUid;

    boolean isMe = false;
    boolean not = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.chatting, container, false);


        ChatName=v.findViewById(R.id.ChatName);
        ChatName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProDialog proDialog=new ProDialog(getContext());
                proDialog.setCanceledOnTouchOutside(true);
                proDialog.setCancelable(true);
                proDialog.show();
            }
        });


        //????????? ?????? ?????? ????????????
        Bundle bundle = getArguments();
        chatName = v.findViewById(R.id.ChatName);
        chatName.setText(bundle.getString("ChatName"));

        //????????????, ??????
        type = v.findViewById(R.id.tv_chat_type);
        title = v.findViewById(R.id.tv_chat_title);
        tv_nickname = v.findViewById(R.id.ChatName);

        // bundle : ????????? / ?????? / postId
        type.setText(bundle.getString("category"));
        title.setText(bundle.getString("title"));
        postId = bundle.getString("postId");

        Log.e("postId",postId);
        // destinationUid ???????????????
        FirebaseDatabase.getInstance().getReference("grapeMate/post").
                child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // ????????? ID ?????????
                firstDestinationUid = snapshot.child("id").getValue().toString();
                nickname = snapshot.child("nickname").getValue().toString();
                tv_nickname.setText(nickname);

                Log.e("nickname", nickname);
                Log.e("firstDestinationUid", firstDestinationUid);
                Log.e("firstDestinationUid2", uid);

                if(uid.equals(firstDestinationUid)) {
                    // ???????????? ????????? ???????????? ???
                    // ???????????? ????????? ???????????? ?????? -> ??????????????? ???????????? ???
                    isMe = true;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        etSendMessage = v.findViewById(R.id.et_send_message);
        btnSendMessage = v.findViewById(R.id.btn_send_message);

        //?????? ?????? ??????????????????
        recyclerView = v.findViewById(R.id.chatting_recycle);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        Map<String, Boolean> users = new HashMap<String, Boolean>();
        Map<String, Boolean> users2 = new HashMap<String, Boolean>();
        users.put("?????????1", true);
        users2.put("?????????2", true);
        adapter.items.add(new chat("chatKey", "postId", new chat.Comment(), new chat.Users("uid", "destinationUid")));
        adapter.items.add(new chat("chatKey", "postId", new chat.Comment(), new chat.Users("uid", "destinationUid2")));

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat c = new chat();
                c.setUsers(new chat.Users(uid, firstDestinationUid));
                c.setPostId(postId);

                if(chatRoomUid == null) {
                    // ???????????? ?????? ???
                    Log.e("??????", "??????");
                    btnSendMessage.setEnabled(false);
                    databaseRef.child("chatroom").push().setValue(c).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkChatRoom();
                            // ???????????? ?????? ???

                            Toast.makeText(getContext(),"???????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // ???????????? ?????? ???
                    chat.Comment comments = new chat.Comment();
                    comments.uid = uid;
                    comments.message = etSendMessage.getText().toString();
                    databaseRef.child("chatroom").child(chatRoomUid).child("comments").push().setValue(comments);

                    checkChatRoom();
                    adapter.notifyDataSetChanged(); // ???????????? ????????????
                    recyclerView.setAdapter(adapter);
                    etSendMessage.setText("");

                }

            }
        });



        //????????????
        back = v.findViewById(R.id.back_chatting);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showMyChat();
            }
        });

        //?????? ?????????
        finish = v.findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("???????????? ??????????????????????");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //????????? ?????? ????????????
                        Toast.makeText(getContext(), "?????????????????????.", Toast.LENGTH_SHORT).show();
                        ((MainActivity)getActivity()).showMyChat();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "?????????????????????.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        // ????????? ???????????? ???????????? ????????? ??????
        checkChatRoom();

        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }



        return v;
    }

    private void loadChatList(DataSnapshot dataSnapshot) {
        items.clear();

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            // ????????? ????????????
            chat c = new chat();
            c.setChatId(snapshot.getKey());
            c.setComments(new chat.Comment(snapshot.child("uid").getValue().toString(), snapshot.child("message").getValue().toString()));
            items.add(c);
        }
        adapter = new chatAdapter(items, getContext());

        adapter.notifyDataSetChanged(); // ???????????? ????????????
        recyclerView.setAdapter(adapter);
    }

    void checkChatRoom() {
        databaseRef.child("chatroom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item : snapshot.getChildren()) {
                    Log.e("??????", "??????");
                    chat c = item.getValue(chat.class);

                    if(c.users.destinationUid.equals(c.users.uid)) {
                        Toast.makeText(getContext(), "???????????? ????????? ??? ????????????.", Toast.LENGTH_SHORT).show();
                        databaseRef.child("chatroom").child(item.getKey()).removeValue();
                        not = true;
                    }

                    Log.e("postId",postId);
                    Log.e("postId",c.getPostId());
                    Log.e("id", firstDestinationUid);
                    Log.e("id", c.users.destinationUid);
                    Log.e("uid", uid);
                    Log.e("uid", c.users.uid);
                    Log.e("isMe", String.valueOf(isMe));

                    if(postId.equals(c.getPostId())) {
                        if(isMe) {
                            // db??? ???, ?????? id??? ??????????????? ????????? ????????? ?????? id??? ?????? ?????????
                            c.users.destinationUid = c.users.uid;
                            c.users.uid = uid;
                            FirebaseDatabase.getInstance().getReference("grapeMate/UserAccount").child(c.users.destinationUid).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    tv_nickname.setText(snapshot.child("nickname").getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            }


                        chatRoomUid  = item.getKey();
                        btnSendMessage.setEnabled(true);

                        loadChatList(snapshot.child(chatRoomUid).child("comments"));

                        recyclerView.setAdapter(adapter);
                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                        isMe = false;
                        adapter.notifyDataSetChanged(); // ???????????? ????????????
                        if(chatRoomUid != null) {
                            not = false;
                            break;
                        }
                    }
                }
                if(not) {
                    ((MainActivity)getActivity()).showMyChat();
                    not = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
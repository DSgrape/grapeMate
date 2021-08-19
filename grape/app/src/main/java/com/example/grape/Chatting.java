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


        //번들로 채팅 대상 이름받기
        Bundle bundle = getArguments();
        chatName = v.findViewById(R.id.ChatName);
        chatName.setText(bundle.getString("ChatName"));

        //카테고리, 제목
        type = v.findViewById(R.id.tv_chat_type);
        title = v.findViewById(R.id.tv_chat_title);
        tv_nickname = v.findViewById(R.id.ChatName);

        // bundle : 글종류 / 제목 / postId
        type.setText(bundle.getString("category"));
        title.setText(bundle.getString("title"));
        postId = bundle.getString("postId");

        Log.e("postId",postId);
        // destinationUid 받아와야함
        FirebaseDatabase.getInstance().getReference("grapeMate/post").
                child(postId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // 글쓴이 ID 받아옴
                firstDestinationUid = snapshot.child("id").getValue().toString();
                nickname = snapshot.child("nickname").getValue().toString();
                tv_nickname.setText(nickname);

                Log.e("nickname", nickname);
                Log.e("firstDestinationUid", firstDestinationUid);
                Log.e("firstDestinationUid2", uid);

                if(uid.equals(firstDestinationUid)) {
                    // 글쓴이가 채팅방 들어왔을 때
                    // 글쓴이가 채팅방 확인하는 방법 -> 채팅룸에서 들어왔을 때
                    isMe = true;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        etSendMessage = v.findViewById(R.id.et_send_message);
        btnSendMessage = v.findViewById(R.id.btn_send_message);

        //채팅 내용 리사이클러뷰
        recyclerView = v.findViewById(R.id.chatting_recycle);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        Map<String, Boolean> users = new HashMap<String, Boolean>();
        Map<String, Boolean> users2 = new HashMap<String, Boolean>();
        users.put("채팅러1", true);
        users2.put("채팅러2", true);
        adapter.items.add(new chat("chatKey", "postId", new chat.Comment(), new chat.Users("uid", "destinationUid")));
        adapter.items.add(new chat("chatKey", "postId", new chat.Comment(), new chat.Users("uid", "destinationUid2")));

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chat c = new chat();
                c.setUsers(new chat.Users(uid, firstDestinationUid));
                c.setPostId(postId);

                if(chatRoomUid == null) {
                    // 채팅방이 없을 때
                    Log.e("실행", "실행");
                    btnSendMessage.setEnabled(false);
                    databaseRef.child("chatroom").push().setValue(c).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            checkChatRoom();
                            // 채팅방이 있을 때

                            Toast.makeText(getContext(),"채팅방이 생성되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // 채팅방이 있을 때
                    chat.Comment comments = new chat.Comment();
                    comments.uid = uid;
                    comments.message = etSendMessage.getText().toString();
                    databaseRef.child("chatroom").child(chatRoomUid).child("comments").push().setValue(comments);

                    checkChatRoom();
                    adapter.notifyDataSetChanged(); // 변경사항 나타내기
                    recyclerView.setAdapter(adapter);
                    etSendMessage.setText("");

                }

            }
        });



        //메인으로
        back = v.findViewById(R.id.back_chatting);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).showMyChat();
            }
        });

        //체팅 나가기
        finish = v.findViewById(R.id.finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("채팅방을 나가시겠습니까?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //체팅방 삭제 넣어야됨
                        Toast.makeText(getContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                        ((MainActivity)getActivity()).showMyChat();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog=builder.create();
                alertDialog.show();

            }
        });

        // 기존에 만들어진 채팅방이 있는지 검사
        checkChatRoom();


        return v;
    }

    private void loadChatList(DataSnapshot dataSnapshot) {
        items.clear();

        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            // 아이템 불러오기
            chat c = new chat();
            c.setChatId(snapshot.getKey());
            c.setComments(new chat.Comment(snapshot.child("uid").getValue().toString(), snapshot.child("message").getValue().toString()));
            items.add(c);
        }
        adapter = new chatAdapter(items, getContext());

        adapter.notifyDataSetChanged(); // 변경사항 나타내기
        recyclerView.setAdapter(adapter);
    }

    void checkChatRoom() {
        databaseRef.child("chatroom").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item : snapshot.getChildren()) {
                    Log.e("실행", "실행");
                    chat c = item.getValue(chat.class);

                    if(c.users.destinationUid.equals(c.users.uid)) {
                        Toast.makeText(getContext(), "본인과는 대화할 수 없습니다.", Toast.LENGTH_SHORT).show();
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
                            // db와 나, 상대 id가 반대이므로 채팅방 내에서 잠깐 id를 서로 스위치
                            c.users.destinationUid = c.users.uid;
                            c.users.uid = uid;
                        }

                        chatRoomUid  = item.getKey();
                        btnSendMessage.setEnabled(true);

                        loadChatList(snapshot.child(chatRoomUid).child("comments"));

                        recyclerView.setAdapter(adapter);
                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                        isMe = false;
                        adapter.notifyDataSetChanged(); // 변경사항 나타내기
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
package com.example.pingponggame;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pingponggame.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RankingActivity extends AppCompatActivity {
    ImageButton btn_backRanking;
    RecyclerView recyclerView;
    List<score> listUser = new ArrayList<>();
    viewRankingAdapter rankingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        btn_backRanking = (ImageButton) findViewById(R.id.btn_backRanking);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_ranking);

        // Load dữ liệu từ Firebase
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        load_arr_user();
        btn_backRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RankingActivity.this, HomePageActivity.class);
                startActivity(intent);
            }
        });

    }

    private void load_arr_user() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference scoresRef = database.getReference("scores");

        scoresRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listUser.clear(); // Xóa dữ liệu cũ (nếu có)

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    score playerScore = snapshot.getValue(score.class);
                    listUser.add(playerScore);
                }
                Collections.sort(listUser, new Comparator<score>() {
                    @Override
                    public int compare(score o1, score o2) {
                        return Integer.compare(o2.getScore(), o1.getScore());
                    }
                });
                rankingAdapter = new viewRankingAdapter(listUser);
                recyclerView.setAdapter(rankingAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
                Toast.makeText(getApplicationContext(), "Lỗi khi đọc dữ liệu từ Firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

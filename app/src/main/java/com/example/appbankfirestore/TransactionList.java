package com.example.appbankfirestore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class TransactionList extends AppCompatActivity {
    RecyclerView listtransactions;
    AdapterTransaction recyclerAdapter;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_list);
        listtransactions = findViewById(R.id.rvlisttransactions);
        listtransactions.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        //listtransactions.setHasFixedSize(true);
        db = FirebaseFirestore.getInstance();
        Query query = db.collection("transaction").orderBy("transaction_number");
        FirestoreRecyclerOptions<Transaction> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Transaction>()
                .setQuery(query, Transaction.class).build();

        //Pasar datos al adaptador
        recyclerAdapter = new AdapterTransaction(firestoreRecyclerOptions);
        recyclerAdapter.notifyDataSetChanged();
        listtransactions.setAdapter(recyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }
}
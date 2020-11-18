package com.example.appbankfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    EditText password, user;
    Button login;
    ImageView imageView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String iduser;
    String itemUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        password = findViewById(R.id.etpassword);
        user = findViewById(R.id.etuser);
        login = findViewById(R.id.btnlogin);
        imageView = findViewById(R.id.imageView);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCheck();
            }
        });
    }

    private void loginCheck() {
        db.collection("client")
                .whereEqualTo("user", user.getText().toString())
                .whereEqualTo("password", password.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    itemUser = document.getId();
                                    iduser = document.getString("user");
                                }
                                Intent send = new Intent(getApplicationContext(), TransactionPage.class);
                                send.putExtra("user", iduser);
                                send.putExtra("itemUser", itemUser);
                                startActivity(send);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrecto",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
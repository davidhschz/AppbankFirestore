package com.example.appbankfirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.getInteger;
import static java.lang.Integer.parseInt;

public class TransactionPage extends AppCompatActivity {
    TextView balance, hour, currentuserbalance, useraccountnumber, targetbalance, targetaccountnumber, itemtarget, prueba, itemUser;
    EditText targetAccount, amount;
    Button logout, transfer, cancel, list, verifyAccount;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Account currentUser = new Account();
    Account targetUser = new Account();
    boolean targetCheck = false;
    boolean checkedTarget = false;
    String user;
    String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_page);
        balance = findViewById(R.id.tvbalance);
        hour = findViewById(R.id.tvhour);
        targetAccount = findViewById(R.id.ettargetaccount);
        amount = findViewById(R.id.etamount);
        logout = findViewById(R.id.btnlogout);
        transfer = findViewById(R.id.btntransfer);
        cancel = findViewById(R.id.btncancel);
        list = findViewById(R.id.btnlisttransactions);
        currentuserbalance = findViewById(R.id.currentuserbalance);
        useraccountnumber = findViewById(R.id.useraccountnumber);
        targetbalance = findViewById(R.id.targetbalance);
        targetaccountnumber = findViewById(R.id.targetaccountnumber);
        itemtarget = findViewById(R.id.itemtarget);
        verifyAccount = findViewById(R.id.verifyAccount);
        prueba = findViewById(R.id.prueba);
        itemUser = findViewById(R.id.itemuser);
        user = getIntent().getStringExtra("user");
        Date date = new Date();
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        hour.setText(dateFormat.format(date));
        searchAccount();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TransactionList.class));
            }
        });

        verifyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTargetAccount();
            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAccount();
                checkedTarget = searchTargetAccount();
                if (checkedTarget){
                    int amountOp = parseInt(amount.getText().toString());
                    int balanceOrigin = parseInt(currentuserbalance.getText().toString());
                    String accountNumberOrigin = useraccountnumber.getText().toString();
                    int balanceTarget = parseInt(targetbalance.getText().toString());
                    String accountNumberTarget = targetaccountnumber.getText().toString();
                    int substractValue = balanceOrigin - amountOp;
                    if (substractValue < 10000 || amountOp >= balanceOrigin || balanceOrigin <= 10000){
                        Toast.makeText(TransactionPage.this, "Saldo insuficiente", Toast.LENGTH_SHORT).show();
                    } else {
                        int updatedBalanceOrigin = balanceOrigin - amountOp;
                        int updatedBalanceTarget = balanceTarget + amountOp;
                        String updatedBalancedTargetParsed =String.valueOf(updatedBalanceTarget);
                        String updatedBalancedOriginParsed =String.valueOf(updatedBalanceOrigin);
                        prueba.setText(itemUser.getText().toString());
                        updateTransactionUser(updatedBalancedOriginParsed);
                        updateTransactionTarget(updatedBalancedTargetParsed);
                        insertTransaction(accountNumberOrigin, accountNumberTarget);
                    }
                } else {
                    Toast.makeText(TransactionPage.this, "Transacción rechazada", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void pruebaxd() {
        Toast.makeText(this, targetUser.getBalance().toString(), Toast.LENGTH_SHORT).show();
    }

    private void updateTransactionTarget(String updatedBalancedTargetParsed) {
        Map<String, Object> updateAccount = new HashMap<>();
        updateAccount.put("balance", updatedBalancedTargetParsed);
        String itemTarget = itemtarget.getText().toString();

        db.collection("account").document(itemTarget).update("balance", updatedBalancedTargetParsed)
                //.set(updateAccount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private void updateTransactionUser(String updatedBalancedOriginParsed) {
        Map<String, Object> updateAccount = new HashMap<>();
        updateAccount.put("balance", updatedBalancedOriginParsed);
        String itemTarget = itemUser.getText().toString();

        db.collection("account").document(itemTarget).update("balance", updatedBalancedOriginParsed)
                //.set(updateAccount)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    /*private void updateTransactionApproved(int updatedBalanceOrigin, int updatedBalanceTarget) {
        int updatedBalance = 0;
        String item;
        int i;
        boolean sw = true;
        for ( i = 0; i <= 1; i++){
            if (i == 0){
                updatedBalance =  updatedBalanceOrigin;
                item = itemUser.getText().toString();
                Toast.makeText(this, "llegó2", Toast.LENGTH_SHORT).show();
            } else {
                updatedBalance =  updatedBalanceTarget;
                item = itemtarget.getText().toString();
                Toast.makeText(this, "Llego3", Toast.LENGTH_SHORT).show();
            }
            Map<String, Object> updateAccount = new HashMap<>();
            updateAccount.put("balance", updatedBalance);

            db.collection("account").document(item)
                    .set(updateAccount)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(TransactionPage.this, "llegó", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TransactionPage.this, "No llegó", Toast.LENGTH_SHORT).show();
                        }
                    });
    }*/


    private void insertTransaction(String accountNumberOrigin, String accountNumberTarget) {
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        Map<String, Object> transactionData = new HashMap<>();
        transactionData.put("amount", amount.getText().toString());
        transactionData.put("date", formattedDate);
        transactionData.put("origin_account_number", accountNumberOrigin);
        transactionData.put("target_account_number", accountNumberTarget);
        transactionData.put("transaction_number", "1");
        
        db.collection("transaction")
                .add(transactionData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(TransactionPage.this, "Transacción realizada correctamente", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        
                    }
                });

    }

    private boolean searchTargetAccount() {
        db.collection("account")
                .whereEqualTo("accountnumber", targetAccount.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    targetUser.setAccountNumber(document.getString("accountnumber"));
                                    targetUser.setBalance(document.getString("balance"));
                                    itemtarget.setText(document.getId());
                                }
                                pruebaxd();
                                targetaccountnumber.setText(targetUser.getAccountNumber());
                                targetbalance.setText(targetUser.getBalance());
                                if (!currentUser.getAccountNumber().equals(targetUser.getAccountNumber())){
                                    Toast.makeText(TransactionPage.this, "Cuenta ok", Toast.LENGTH_SHORT).show();
                                    targetCheck = true;
                                } else {
                                    targetCheck = false;
                                    Toast.makeText(TransactionPage.this, "No se puede consignar a la cuenta origen", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                targetCheck = false;
                                Toast.makeText(getApplicationContext(),"Número de cuenta no existe",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        return targetCheck;
    }

    private void searchAccount() {
        db.collection("account")
                .whereEqualTo("user", user)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    currentUser.setAccountNumber(document.getString("accountnumber"));
                                    currentUser.setBalance(document.getString("balance"));
                                    itemUser.setText(document.getId());
                                }
                                balance.setText("Saldo: $" + currentUser.getBalance());
                                currentuserbalance.setText(currentUser.getBalance());
                                useraccountnumber.setText(currentUser.getAccountNumber());
                            }
                        }
                    }
                });
    }
}
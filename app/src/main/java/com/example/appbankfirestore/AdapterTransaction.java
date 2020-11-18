package com.example.appbankfirestore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.w3c.dom.Text;

public class AdapterTransaction extends FirestoreRecyclerAdapter<Transaction, AdapterTransaction.viewHolder> {

    public AdapterTransaction(@NonNull FirestoreRecyclerOptions<Transaction> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull Transaction model) {
        holder.transactionnumber.setText("Número de transacción: " + model.getTransaction_number());
        holder.originaccount.setText("Cuenta origen: " + model.getOrigin_account_number());
        holder.targetaccount.setText("Cuenta destino: " + model.getTarget_account_number());
        holder.date.setText(model.getDate());
        holder.amount.setText("Valor: " + model.getAmount());
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction,null,false);
        return new viewHolder(view);
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView transactionnumber, originaccount, targetaccount, date, amount;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            transactionnumber = itemView.findViewById(R.id.tvtransactionnumberl);
            originaccount = itemView.findViewById(R.id.tvoriginaccountl);
            targetaccount = itemView.findViewById(R.id.tvtargetaccountl);
            date = itemView.findViewById(R.id.tvdatel);
            amount = itemView.findViewById(R.id.tvamountl);
        }
    }
}

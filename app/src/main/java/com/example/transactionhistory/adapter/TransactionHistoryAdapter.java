package com.example.transactionhistory.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transactionhistory.R;
import com.example.transactionhistory.pojo.TransactionPojo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionViewholder> {

    private ArrayList<TransactionPojo> pojos;
    private Context context;

    public TransactionHistoryAdapter(ArrayList<TransactionPojo> pojos, Context context) {
        this.pojos = pojos;
        this.context = context;
    }

    @NonNull
    @Override
    public TransactionViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false);
        return new TransactionViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewholder holder, int i) {
        String date = convertLongToDate((pojos.get(i).getTxnDate()));

        if (pojos.get(i) != null) {
            holder.trnDate.setText(date);
            holder.amount.setText(Html.fromHtml("Amount: <b>" + pojos.get(i).getAmount() + "</b>"));
            holder.cardType.setText(pojos.get(i).getCardType());
            holder.reference.setText("Ref no: " + pojos.get(i).getReference());

            if (pojos.get(i).getStatus().equals("AUTHORIZED"))
                holder.status.setImageResource(R.drawable.authorized);
            else
                holder.status.setImageResource(R.drawable.fail);
        } else {
            Toast.makeText(context, "No data in JSON file", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return pojos.size();
    }

    public String convertLongToDate(String value) {
        long val = Long.parseLong(value);
        Date date = new Date(val);
        SimpleDateFormat df2 = new SimpleDateFormat("dd-M-yyyy hh:mm:s");
        String dateText = df2.format(date);
        return dateText;
    }

    public class TransactionViewholder extends RecyclerView.ViewHolder {

        TextView trnDate, amount, reference, cardType;
        ImageView status;

        public TransactionViewholder(@NonNull View itemView) {
            super(itemView);
            trnDate = itemView.findViewById(R.id.tv_trn_date);
            amount = itemView.findViewById(R.id.tv_amount);
            reference = itemView.findViewById(R.id.tv_reference);
            cardType = itemView.findViewById(R.id.tv_card_type);
            status = itemView.findViewById(R.id.iv_status);
        }
    }
}

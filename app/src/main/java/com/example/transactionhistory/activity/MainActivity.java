package com.example.transactionhistory.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.transactionhistory.R;
import com.example.transactionhistory.adapter.TransactionHistoryAdapter;
import com.example.transactionhistory.pojo.TransactionPojo;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView transactionList;
    private TransactionHistoryAdapter adapter;
    private ArrayList<TransactionPojo> transactionPojoArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transactionList = findViewById(R.id.transaction_list);

        String returnJsonString = loadJSONFromAsset(this);

        transactionPojoArrayList = getTransactionPojoArrayList(returnJsonString);

        adapter = new TransactionHistoryAdapter(transactionPojoArrayList, this);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        transactionList.setLayoutManager(manager);
        transactionList.setAdapter(adapter);
    }

    public ArrayList<TransactionPojo> getTransactionPojoArrayList(String jsonString) {
        ArrayList<TransactionPojo> arrayList = new ArrayList<>();
        String returnJsonString = loadJSONFromAsset(this);

        Gson gson = new Gson();
        try {
            JSONArray jsonArray = new JSONArray(returnJsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                arrayList.add(gson.fromJson(object.toString(), TransactionPojo.class));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("transactions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}

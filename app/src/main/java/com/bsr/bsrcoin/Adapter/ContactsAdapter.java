package com.bsr.bsrcoin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bsr.bsrcoin.Models.AddRequestModel;
import com.bsr.bsrcoin.Models.contacts;
import com.bsr.bsrcoin.MysqlConst.Constants;
import com.bsr.bsrcoin.R;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<contacts> contactslArrayList;
    public ContactsAdapter(Context context, ArrayList<contacts> addRequestModelArrayList) {
        this.context = context;
        this.contactslArrayList = addRequestModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.contact_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            contacts modl = contactslArrayList.get(position);
            holder.name.setText(modl.name);
            holder.phone.setText(modl.number);
    }

    @Override
    public int getItemCount() {
        return contactslArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name,phone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            phone = itemView.findViewById(R.id.tvphone);
            name = itemView.findViewById(R.id.tvname);
        }
    }
}


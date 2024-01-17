package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.Message;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapterMessages extends RecyclerView.Adapter<MyAdapterMessages.ViewHolder> {
    private List<Message> messages;
    private Context context;
    ProgressBar progressBarBuy;

    // Constructor para recibir la lista de objetos
    public MyAdapterMessages(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message object = messages.get(position);

        holder.message.setText(object.message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        public ViewHolder(View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.textViewMessage);
            progressBarBuy = itemView.findViewById(R.id.progressBarBuy);
        }
    }
}

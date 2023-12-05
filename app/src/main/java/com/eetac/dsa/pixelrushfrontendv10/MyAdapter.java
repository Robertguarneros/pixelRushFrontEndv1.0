package com.eetac.dsa.pixelrushfrontendv10;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;

import androidx.recyclerview.widget.RecyclerView;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<StoreObject> storeObjects;

    // Constructor para recibir la lista de objetos
    public MyAdapter(List<StoreObject> storeObjects) {
        this.storeObjects = storeObjects;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StoreObject object = storeObjects.get(position);

        holder.lineName.setText(object.articleName);
        holder.lineID.setText("ID: "+object.objectID);
        holder.lineDescription.setText(object.description);
        holder.linePrice.setText("$"+object.price);
        holder.buttonBuy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Log.i("POSICION DEL ADAPTER", "----------------------------"+position);

                //Add item to this user
                //Necesitamos recuper el username a partir del sharepreferences

                //MIRAR de arreglarlo
                String storedUsername = getStoredUsername();

                Call<Void> callAddItemToUser = PixelRushService.addItemToUser(username,storeObjects.get(position).getObjectID());
                callAddItemToUser.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            System.out.println("Item added successfully");
                        } else {
                            System.out.println("Error: " + response.code() + " " + response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        System.out.println("Error: "+t.getMessage());
                    }
                });


            }
        });
    }

    @Override
    public int getItemCount() {
        return storeObjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView lineName;
        TextView lineDescription;
        TextView lineID;
        TextView linePrice;
        Button buttonBuy;

        public ViewHolder(View itemView) {
            super(itemView);
            lineName = itemView.findViewById(R.id.textViewArticleName);
            lineDescription = itemView.findViewById(R.id.textViewDescription);
            lineID = itemView.findViewById(R.id.textViewID);
            linePrice = itemView.findViewById(R.id.textViewPrice);
            buttonBuy = itemView.findViewById(R.id.buttonBuy);
        }
    }
}

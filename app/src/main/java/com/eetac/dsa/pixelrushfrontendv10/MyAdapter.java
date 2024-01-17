package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.OwnedObjects;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<StoreObject> storeObjects;
    private List<OwnedObjects> ownedObjects;
    private Context context;
    ProgressBar progressBarBuy;

    // Constructor para recibir la lista de objetos
    public MyAdapter(List<StoreObject> storeObjects, Context context) {
        this.storeObjects = storeObjects;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StoreObject object = storeObjects.get(position);

        holder.lineName.setText(object.getArticleName());
        holder.lineDescription.setText(object.getDescription());
        holder.linePrice.setText("$"+object.getPrice());
        // Assuming image url
        String imageUrl = object.getArticlePhoto();

        // Use Picasso to load the image into the ImageView
        Picasso.get().load(imageUrl).into(holder.imageStoreObject);

        // Check if the object is in the ownedObjects list
        boolean isOwned = isObjectOwned(object.getObjectID());

        // Disable the button if the object is owned
        holder.buttonBuy.setEnabled(!isOwned);
        if (isOwned){holder.buttonBuy.setText("Owned");}
        holder.buttonBuy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarBuy.setVisibility(View.VISIBLE);

                int position = holder.getAdapterPosition();
                Log.i("POSICION DEL ADAPTER", "----------------------------"+position);

                // Retrieve the username from SharedPreferences
                String storedUsername = SharedPreferencesUtil.getStoredUsername(context);
                PixelRushService pixelRushService = PixelRushService.retrofit.create(PixelRushService.class);//creating interface

                Call<Void> callAddItemToUser = pixelRushService.addItemToUser(storedUsername,storeObjects.get(position).getObjectID());
                callAddItemToUser.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        progressBarBuy.setVisibility(View.GONE);


                        if (response.isSuccessful()) {
                            System.out.println("Purchase Successful");
                            Toast.makeText(context, "Purchase Successful", Toast.LENGTH_SHORT).show();
                            holder.buttonBuy.setText("Owned");
                            holder.buttonBuy.setEnabled(false);

                        } else {
                            System.out.println("Error: " + response.code() + " " + response.message());
                            Toast.makeText(context,"Error"+response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        progressBarBuy.setVisibility(View.GONE);

                        System.out.println("Error: "+t.getMessage());
                        Toast.makeText(context,"Error"+t.getMessage(),Toast.LENGTH_SHORT).show();
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
        ImageView imageStoreObject;

        public ViewHolder(View itemView) {
            super(itemView);
            lineName = itemView.findViewById(R.id.textViewArticleName);
            lineDescription = itemView.findViewById(R.id.textViewDescription);
            linePrice = itemView.findViewById(R.id.textViewPrice);
            buttonBuy = itemView.findViewById(R.id.buttonBuy);
            progressBarBuy = itemView.findViewById(R.id.progressBarBuy);
            imageStoreObject = itemView.findViewById(R.id.imageStoreObject);
        }
    }
    // Helper method to check if an object is owned
    private boolean isObjectOwned(String objectId) {
        if (ownedObjects != null) {
            for (OwnedObjects ownedObject : ownedObjects) {
                if (ownedObject.getObjectID().equals(objectId)) {
                    return true;
                }
            }
        }
        return false;
    }
    public void setOwnedObjectsList(List<OwnedObjects> ownedObjects) {
        // Set the list of owned objects
        this.ownedObjects = ownedObjects;

        // Notify the adapter that the dataset has changed
        notifyDataSetChanged();
    }
}

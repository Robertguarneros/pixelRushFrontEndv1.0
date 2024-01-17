package com.eetac.dsa.pixelrushfrontendv10;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.Badge;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAdapterBadges extends RecyclerView.Adapter<MyAdapterBadges.ViewHolder> {
    private List<Badge> badges;
    private Context context;

    // Constructor para recibir la lista de objetos
    public MyAdapterBadges(List<Badge> badges, Context context) {
        this.badges = badges;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.badge_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Badge object = badges.get(position);

        holder.name.setText(object.name);

        // Assuming object.avatar is the URL of the image
        String imageUrl = object.avatar;

        // Use Picasso to load the image into the ImageView
        Picasso.get().load(imageUrl).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return badges.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewBadgeName);
            avatar = itemView.findViewById(R.id.imageBadge);
        }
    }
}

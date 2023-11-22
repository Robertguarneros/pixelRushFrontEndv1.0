package com.eetac.dsa.pixelrushfrontendv10;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.eetac.dsa.pixelrushfrontendv10.backEndClasses.StoreObject;
import java.util.List;

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

        public ViewHolder(View itemView) {
            super(itemView);
            lineName = itemView.findViewById(R.id.textViewArticleName);
            lineDescription = itemView.findViewById(R.id.textViewDescription);
            lineID = itemView.findViewById(R.id.textViewID);
            linePrice = itemView.findViewById(R.id.textViewPrice);
        }
    }
}

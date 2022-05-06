package com.example.szakdolgozat;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
public class ProductAdapter extends RecyclerView.Adapter <ProductAdapter.ImageViewHolder> implements Filterable {
    private Context mContext;
    private List<Product> mProduct;
    private List<Product> mProcutAll;
    private static final String LOG_TAG = "";
    private String storetype;

    public ProductAdapter(Context context, List<Product> items) {
        this.mContext = context;
        this.mProduct = items;
        this.mProcutAll = items;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
//        String url = "https://firebasestorage.googleapis.com/v0/b/stikeezapp.appspot.com/o/uploads%2F1647721104934.jpg?alt=media&token=07d94266-c320-4beb-90da-2212c10141e2";
        Product uploadCurrent = mProduct.get(position);
        holder.mitem_name.setText(uploadCurrent.getName());
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.mimageView);
        holder.mitem_price.setText(uploadCurrent.getPrice());
        holder.mitem_location.setText(uploadCurrent.getLocation());
        holder.mshortdescript.setText(uploadCurrent.getShortdescript());
        holder.mbuttan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialog = DialogPlus.newDialog(holder.mimageView.getContext())
                        .setContentHolder(new ViewHolder(R.layout.dialog_item))
                        .setExpanded(true, 1200)
                        .create();

                View v = dialog.getHolderView();

                TextView dname = v.findViewById(R.id.name);
                TextView dprice = v.findViewById(R.id.price);
                TextView dlocation = v.findViewById(R.id.location);
                TextView ldscript = v.findViewById(R.id.longdescript);
                TextView dconnect = v.findViewById(R.id.contactEmail);
                TextView dstore = v.findViewById(R.id.store);
                Button cancel = v.findViewById(R.id.btnCancel);



                dname.setText(uploadCurrent.getName());
                dprice.setText(uploadCurrent.getPrice());
                dlocation.setText(uploadCurrent.getLocation());
                ldscript.setText(uploadCurrent.getLongdescript());
                dconnect.setText(uploadCurrent.getContact());
                dstore.setText(uploadCurrent.getStore());

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mProduct.size();
    }

    @Override
    public Filter getFilter() {
        return searchFilter;
    }

    private Filter searchFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Product> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if(charSequence == null || charSequence.length() == 0) {
                results.count = mProcutAll.size();
                results.values = mProcutAll;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Product item : mProcutAll) {
                    if(item.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mProduct = (List)filterResults.values;
            notifyDataSetChanged();
        }
    };

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public TextView mitem_name;
        public ImageView mimageView;
        public TextView mitem_price;
        public TextView mitem_location;
        public TextView mshortdescript;
        public Button  mbuttan;


        public ImageViewHolder(View itemView) {
            super(itemView);

            mitem_name = itemView.findViewById(R.id.item_name);
            mimageView = itemView.findViewById(R.id.imageView);
            mitem_price = itemView.findViewById(R.id.item_price);
            mitem_location = itemView.findViewById(R.id.item_location);
            mshortdescript = itemView.findViewById(R.id.shortdescript);
            mbuttan = itemView.findViewById(R.id.bttn_check);





        }


    }
}

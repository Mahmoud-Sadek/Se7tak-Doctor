package com.sadek.se7takdoctor.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.fragment.RegisterFragment;
import com.sadek.se7takdoctor.model.SpecialtyModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ClinicPhotoAdatpter extends RecyclerView.Adapter<ClinicPhotoAdatpter.ViewHolder> {

    private Context context;

    private List<String> data;

    public ClinicPhotoAdatpter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_clinic_photo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RegisterFragment.doctorModel.setSpecialty(data.get(position));
//                ((Activity)context).onBackPressed();
//            }
//        });

        Picasso.with(context).load(data.get(position)).into(holder.photoIV);


    }

    @Override
    public int getItemCount() {

        return data.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.photoIV)
        ImageView photoIV;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

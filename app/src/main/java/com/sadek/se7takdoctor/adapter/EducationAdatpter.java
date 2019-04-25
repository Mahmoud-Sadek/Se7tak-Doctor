package com.sadek.se7takdoctor.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.activity.MainActivity;
import com.sadek.se7takdoctor.fragment.RegisterFragment;
import com.sadek.se7takdoctor.model.DegreeDoctor;
import com.sadek.se7takdoctor.model.SpecialtyModel;
import com.sadek.se7takdoctor.utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EducationAdatpter extends RecyclerView.Adapter<EducationAdatpter.ViewHolder> {

    private Context context;

    private List<DegreeDoctor> data;

    public EducationAdatpter(Context context, List<DegreeDoctor> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_education, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putSerializable(Common.FIREBASE_ABOUT_DOCTOR, data.get(position));
                ((MainActivity) context).switchToPage(5, bundle, R.string.about);
            }
        });
        holder.degreeTV.setText(data.get(position).getDegreeEN());
        holder.universtyTV.setText(data.get(position).getCollegeAR());
        holder.yearTV.setText(data.get(position).getYear());



    }

    @Override
    public int getItemCount() {

        return data.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.degreeTV)
        TextView degreeTV;
        @BindView(R.id.universtyTV)
        TextView universtyTV;
        @BindView(R.id.yearTV)
        TextView yearTV;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

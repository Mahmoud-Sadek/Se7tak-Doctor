package com.sadek.se7takdoctor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.activity.MainActivity;
import com.sadek.se7takdoctor.model.Patient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PatientAdatpter extends RecyclerView.Adapter<PatientAdatpter.ViewHolder> {

    private Context context;

    private List<Patient> data;

    public PatientAdatpter(Context context, List<Patient> data) {
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("model", data.get(position));
                ((MainActivity) context).switchToPage(11, bundle, R.string.about);
            }
        });
        holder.patientNameTV.setText(data.get(position).getName());
        holder.patientIdTV.setText(data.get(position).getPatientID() != null ? "#" + data.get(position).getPatientID() : "");
        holder.patientPhoneTV.setText(data.get(position).getPhone());


    }

    @Override
    public int getItemCount() {

        return data.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.patientNameTV)
        TextView patientNameTV;
        @BindView(R.id.patientIdTV)
        TextView patientIdTV;
        @BindView(R.id.patientPhoneTV)
        TextView patientPhoneTV;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

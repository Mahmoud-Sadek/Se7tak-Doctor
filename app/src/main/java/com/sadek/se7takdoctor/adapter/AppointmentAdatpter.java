package com.sadek.se7takdoctor.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sadek.se7takdoctor.R;
import com.sadek.se7takdoctor.firebase.FireDatabase;
import com.sadek.se7takdoctor.model.Order;
import com.sadek.se7takdoctor.utils.Common;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AppointmentAdatpter extends RecyclerView.Adapter<AppointmentAdatpter.ViewHolder> {

    private Context context;
    private List<Order> data;

    FireDatabase fireDatabase;

    public AppointmentAdatpter(Context context, List<Order> data) {
        this.context = context;
        this.data = data;
        fireDatabase = new FireDatabase(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        holder.patientNameTV.setText(data.get(position).getUserName());
        holder.patientPhoneTV.setText(data.get(position).getPhoneNumber());
        holder.order_date_TV.setText(data.get(position).getOrderDate());
        holder.order_time_TV.setText(data.get(position).getOrderTime());
        holder.docotr_examinations_TV.setText(data.get(position).getExamination());

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + data.get(position).getPhoneNumber()));
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrderStatus(data.get(position), Common.ORDER_STATUS_REJECTED);
            }
        });

        if (data.get(position).getStuatus().equals(Common.ORDER_STATUS_PENDING)) {
            holder.check_in_btn.setVisibility(View.GONE);
            holder.no_show_btn.setVisibility(View.GONE);
            holder.accept_btn.setVisibility(View.VISIBLE);
            holder.refuse_btn.setVisibility(View.VISIBLE);
        } else if (data.get(position).getStuatus().equals(Common.ORDER_STATUS_ACCEPTED)) {
            holder.check_in_btn.setVisibility(View.VISIBLE);
            holder.no_show_btn.setVisibility(View.VISIBLE);
            holder.accept_btn.setVisibility(View.GONE);
            holder.refuse_btn.setVisibility(View.GONE);
        } else {
            holder.check_in_btn.setVisibility(View.GONE);
            holder.no_show_btn.setVisibility(View.GONE);
            holder.accept_btn.setVisibility(View.GONE);
            holder.refuse_btn.setVisibility(View.GONE);
        }
        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrderStatus(data.get(position), Common.ORDER_STATUS_ACCEPTED);
            }
        });
        holder.refuse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrderStatus(data.get(position), Common.ORDER_STATUS_REJECTED);
            }
        });
        holder.check_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrderStatus(data.get(position), Common.ORDER_STATUS_COMPLETED);
            }
        });
        holder.no_show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editOrderStatus(data.get(position), Common.ORDER_STATUS_CANCELED);
            }
        });


    }

    private void editOrderStatus(Order order, String orderStatusRejected) {
        fireDatabase.EditAppointment(order, orderStatusRejected, new FireDatabase.ResultCallback() {
            @Override
            public void onCallback(boolean success) {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {

        return data.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.patient_name_TV)
        TextView patientNameTV;
        @BindView(R.id.patient_phone_TV)
        TextView patientPhoneTV;
        @BindView(R.id.order_date_TV)
        TextView order_date_TV;
        @BindView(R.id.order_time_TV)
        TextView order_time_TV;
        @BindView(R.id.docotr_examinations_TV)
        TextView docotr_examinations_TV;
        @BindView(R.id.call)
        ImageView call;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.accept_btn)
        TextView accept_btn;
        @BindView(R.id.refuse_btn)
        TextView refuse_btn;
        @BindView(R.id.check_in_btn)
        TextView check_in_btn;
        @BindView(R.id.no_show_btn)
        TextView no_show_btn;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

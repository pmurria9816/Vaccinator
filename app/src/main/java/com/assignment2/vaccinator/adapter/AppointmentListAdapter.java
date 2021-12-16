package com.assignment2.vaccinator.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment2.vaccinator.R;
import com.assignment2.vaccinator.models.Appointment;

import java.util.List;

public class AppointmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Appointment> appointmentList;

    //Constructor to take appointment List
    public AppointmentListAdapter(List<Appointment> appointmentList) {
        this.appointmentList = appointmentList;
    }

    //Creating view holder class.
    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;

        public TextView tvApptTime;

        public TextView tvHospital;

        public TextView tvVaccine;

        public TextView tvAge;

        //Constructor to initialize the declared UI components.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvApptTime = itemView.findViewById(R.id.tvApptTime);
            tvHospital = itemView.findViewById(R.id.tvHospital);
            tvVaccine = itemView.findViewById(R.id.tvVaccine);
            tvAge = itemView.findViewById(R.id.tvAge);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate student grade layout using layout inflater.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        // Set the values for each item in the list.
        Appointment appointment = appointmentList.get(position);
        ((ViewHolder) holder).tvName.setText(appointment.getFirstName().concat(" ").concat(appointment.getLastName()));
        ((ViewHolder) holder).tvApptTime.setText(String.valueOf(appointment.getSlot()));
        ((ViewHolder) holder).tvHospital.setText(appointment.getHospital());
        ((ViewHolder) holder).tvVaccine.setText(appointment.getVaccine());
        ((ViewHolder) holder).tvAge.setText(String.valueOf(appointment.getAge()));
    }

    //Returns student grade list count.
    @Override
    public int getItemCount() {
        return appointmentList.size();
    }
}
package com.assignment2.vaccinator.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment2.vaccinator.AppointmentFragment;
import com.assignment2.vaccinator.R;
import com.assignment2.vaccinator.models.Appointment;

import java.util.List;

public class AppointmentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Appointment> appointmentList;
    private FragmentManager manager;

    //Constructor to take appointment List
    public AppointmentListAdapter(List<Appointment> appointmentList, FragmentManager manager) {
        this.appointmentList = appointmentList;
        this.manager = manager;
    }

    //Creating view holder class.
    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName,tvApptTime,tvHospital,tvVaccine,tvAge;
        Button editApt;

        //Constructor to initialize the declared UI components.
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvApptTime = itemView.findViewById(R.id.tvApptTime);
            tvHospital = itemView.findViewById(R.id.tvHospital);
            tvVaccine = itemView.findViewById(R.id.tvVaccine);
            tvAge = itemView.findViewById(R.id.tvAge);
            editApt = itemView.findViewById(R.id.edit_apt_btn);
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
        String[] dateInfo = String.valueOf(appointment.getSlot()).split("0");
        ((ViewHolder) holder).tvApptTime.setText(dateInfo[0].concat(" ").concat(appointment.getTime()));
        ((ViewHolder) holder).tvHospital.setText(appointment.getHospital());
        ((ViewHolder) holder).tvVaccine.setText(appointment.getVaccine());
        ((ViewHolder) holder).tvAge.setText(String.valueOf(appointment.getAge()));
        ((ViewHolder) holder).editApt.setOnClickListener(view -> {
            Bundle arguments = new Bundle();
            arguments.putSerializable ("isUpdate", true);
            arguments.putSerializable("appointment",appointment);
            AppointmentFragment appointmentFragment = new AppointmentFragment();
            appointmentFragment.setArguments(arguments);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.viewAppointment_container, appointmentFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    //Returns student grade list count.
    @Override
    public int getItemCount() {
        return appointmentList.size();
    }
}
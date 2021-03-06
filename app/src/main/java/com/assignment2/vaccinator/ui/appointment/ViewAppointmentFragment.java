package com.assignment2.vaccinator.ui.appointment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment2.vaccinator.R;
import com.assignment2.vaccinator.adapter.AppointmentListAdapter;
import com.assignment2.vaccinator.database.DatabaseHandler;
import com.assignment2.vaccinator.models.Appointment;

import java.util.ArrayList;
import java.util.List;

public class ViewAppointmentFragment extends Fragment {

    private List<Appointment> appointmentList;

    //Declaring objects to be used
    private RecyclerView recyclerView;

    //Database handler
    private DatabaseHandler dbHandler;

    SharedPreferences pref; // 0 - for private mode

    /**
     * This method is called when view is created. We are performing all the initialization in this method.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Init
        dbHandler = DatabaseHandler.getInstance(getContext());
        pref = getContext().getSharedPreferences("preferences", 0);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointmentlist, container, false);

        //Initializing recycler view and appointment list
        appointmentList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rvAppointments);

        //Bind the list adapter to recycler view
        bindListAdapter();
        return view;
    }

    /**
     * This method will bind the appointment list adapter to the recycler view.
     */
    public void bindListAdapter() {
        //Create and set layout manager for recycler view.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //Create and set list adapter for recycler view.
        AppointmentListAdapter appointmentListAdapter = new AppointmentListAdapter(getAppointmentList(),getParentFragmentManager());
        recyclerView.setAdapter(appointmentListAdapter);

        //if any data changes in the list, recycler view is informed.
        appointmentListAdapter.notifyDataSetChanged();
    }

    public List<Appointment>  getAppointmentList() {
        //Get the appointments from database.
        appointmentList = dbHandler.getAppointments(pref.getInt("userId", -1));

        //If appointment list is blank, show no records found.
        if (appointmentList.isEmpty()) {
            Toast.makeText(getActivity(), "No Records Found", Toast.LENGTH_SHORT).show();
        }
        return appointmentList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
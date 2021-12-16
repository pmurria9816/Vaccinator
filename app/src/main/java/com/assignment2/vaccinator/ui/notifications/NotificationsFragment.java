package com.assignment2.vaccinator.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.assignment2.vaccinator.R;
import com.assignment2.vaccinator.adapter.AppointmentListAdapter;
import com.assignment2.vaccinator.database.UserAppointmentDatabaseHandler;
import com.assignment2.vaccinator.databinding.FragmentNotificationsBinding;
import com.assignment2.vaccinator.models.Appointment;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    private List<Appointment> appointmentList;

    private FragmentNotificationsBinding binding;

    //Declaring objects to be used
    private RecyclerView recyclerView;

    private UserAppointmentDatabaseHandler dbHandler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        //initialize database handler.
        dbHandler = new UserAppointmentDatabaseHandler(getContext());

        //Initializing recycler view and student grade list
        appointmentList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.rvAppointments);

        //Find the list adapter to recycler view
        bindListAdapter();

        return root;
    }

    public void bindListAdapter() {
        //Create and set layout manager for recycler view.
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Create and set list adapter for recycler view.
        AppointmentListAdapter studentGradeListAdapter = new AppointmentListAdapter(getAppointmentList());
        recyclerView.setAdapter(studentGradeListAdapter);

        //if any data changes in the list, recycler view is informed.
        studentGradeListAdapter.notifyDataSetChanged();
    }

    public List<Appointment>  getAppointmentList() {
        //Get all the appointments from database.
        appointmentList = dbHandler.getAppointments(1);
        if (appointmentList.isEmpty()) {
            Toast.makeText(getActivity(), "No Records Found", Toast.LENGTH_SHORT).show();
        }
        return appointmentList;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
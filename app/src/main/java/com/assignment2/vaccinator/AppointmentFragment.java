package com.assignment2.vaccinator;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Calendar;


public class AppointmentFragment extends Fragment {

    EditText hospitalInput,dateText;
    private DatePickerDialog picker;
    Spinner vaccine,timeSlot;
    int selectedVaccine,selectedTimeslot;
    private final String[] vaccines = {"Covaxin", "Pfizer", "Moderna","J&J Janssen"};
    private final String[] timeSlots = {"10:00 am", "11:00 am", "12:00 pm","1:00 pm","2:00 pm","3:00 pm","4:00 pm"};

    public AppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_appointment, container, false);
        Bundle arguments = getArguments();
        String hospitalName = arguments.getString("hospital");
        hospitalInput= v.findViewById(R.id.hos_name_input);
        hospitalInput.setText(hospitalName);
        vaccine = v.findViewById(R.id.vaccineList);
        timeSlot = v.findViewById(R.id.timeSpinner);
        dateText = v.findViewById(R.id.dateText);

        dateText.setInputType(InputType.TYPE_NULL);

        // Click Listener for Date editText
        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cal = Calendar.getInstance();
                int dayJoin = cal.get(Calendar.DAY_OF_MONTH);
                int monthJoin = cal.get(Calendar.MONTH);
                int yearJoin = cal.get(Calendar.YEAR);

                picker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String dateStr = day+"/"+(month+1)+"/"+year;
                        dateText.setText(dateStr);
                    }
                },yearJoin,monthJoin,dayJoin);

                picker.show();
            }
        } );


        // Data Adapter for Screen Spinner
        ArrayAdapter dataAdapter  = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,vaccines);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the dataAdapter on the Spinner
        vaccine.setAdapter(dataAdapter);
        // On Selected Listener for Spinner
        vaccine.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedVaccine = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter timeDataAdapter  = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,timeSlots);
        timeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSlot.setAdapter(timeDataAdapter);

        timeSlot.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTimeslot = i;
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }
}
package com.assignment2.vaccinator.ui.appointment;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.assignment2.vaccinator.R;
import com.assignment2.vaccinator.database.DatabaseHandler;
import com.assignment2.vaccinator.models.Appointment;

import java.util.Calendar;

/**
 * This fragment is used to book appointment for the user.
 */
public class AppointmentFragment extends Fragment {

    //Declaring variables.
    private EditText hospitalInput,dateText,firstname,lastname,age,email;

    private DatePickerDialog picker;

    private Spinner vaccine,timeSlot;

    private int selectedVaccine,selectedTimeslot;

    private Button bookAppointment;

    private DatabaseHandler dbHandler;

    private Appointment oldAppointment;

    private static final String[] vaccines = {"Covaxin", "Pfizer", "Moderna","J&J Janssen"};

    private static final String[] timeSlots = {"10:00 am", "11:00 am", "12:00 pm","1:00 pm","2:00 pm","3:00 pm","4:00 pm"};

    // Required empty public constructor
    public AppointmentFragment() {
    }

    /**
     * This method is called on fragmnet create.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * This Method is called when view is created.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_appointment, container, false);

        // Using bundle for arguments.
        Bundle arguments = getArguments();
        String hospitalName = arguments.getString("hospital");
        Boolean isUpdate = arguments.getBoolean("isUpdate");

        //Init
        hospitalInput= v.findViewById(R.id.hos_name_input);
        hospitalInput.setText(hospitalName);
        vaccine = v.findViewById(R.id.vaccineList);
        timeSlot = v.findViewById(R.id.timeSpinner);
        dateText = v.findViewById(R.id.dateText);
        bookAppointment = v.findViewById(R.id.book_btn);
        firstname = v.findViewById(R.id.first_name_input);
        lastname = v.findViewById(R.id.last_name_input);
        age = v.findViewById(R.id.age_input);
        email = v.findViewById(R.id.email_input);
        dbHandler = DatabaseHandler.getInstance(getContext());
        dateText.setInputType(InputType.TYPE_NULL);

        //Using the same fragment to edit the appointment.
        if(isUpdate) {
            oldAppointment = (Appointment) arguments.getSerializable("appointment");
            hospitalInput.setText(oldAppointment.getHospital());
            firstname.setText(oldAppointment.getFirstName());
            lastname.setText(oldAppointment.getLastName());
            age.setText(String.valueOf(oldAppointment.getAge()));
            email.setText(oldAppointment.getEmail());
            bookAppointment.setText("Edit Appointment");
        }

        // Click Listener for Date editText
        dateText.setOnClickListener(view -> {
            final Calendar cal = Calendar.getInstance();
            int dayJoin = cal.get(Calendar.DAY_OF_MONTH);
            int monthJoin = cal.get(Calendar.MONTH);
            int yearJoin = cal.get(Calendar.YEAR);

            //Date picker
            picker = new DatePickerDialog(getContext(), (datePicker, year, month, day) -> {
                String dateStr = day+"/"+(month+1)+"/"+year;
                dateText.setText(dateStr);
            },yearJoin,monthJoin,dayJoin);
            picker.show();
        });

        //On click listener for book appointment.
        bookAppointment.setOnClickListener(view -> {
            Appointment appointment = new Appointment();
            appointment.setUser(getUserId());
            appointment.setHospital(hospitalInput.getText().toString());
            appointment.setFirstName(firstname.getText().toString());
            appointment.setLastName(lastname.getText().toString());
            appointment.setEmail(email.getText().toString());
            appointment.setAge(Integer.parseInt(age.getText().toString()));
            appointment.setTime(timeSlots[selectedTimeslot]);
            appointment.setVaccine(vaccines[selectedVaccine]);
            appointment.setSlot(dateText.getText().toString());

            boolean flag;

            //on Appointment update.
            if(isUpdate){
                flag = dbHandler.updateAppointment(appointment,oldAppointment.getId());
            }else {
                flag = dbHandler.addAppointment(appointment);
            }
            // Check for success.
            if(flag){
                Toast.makeText(getContext(),"Appointment Booked Successfully.",Toast.LENGTH_SHORT).show();
                resetForm();
            } else {
                Toast.makeText(getContext(),"Appointment Booking Failed.",Toast.LENGTH_SHORT).show();
            }
        });

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
                //No Action
            }
        });

        ArrayAdapter timeDataAdapter  = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,timeSlots);
        timeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSlot.setAdapter(timeDataAdapter);

        //Time slot selected listener.
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

    /**
     * This method is used to reset the form.
     */
    private void resetForm() {
        firstname.setText("");
        lastname.setText("");
        age.setText("");
        email.setText("");
        dateText.setText("");
    }

    /**
     * get user id from shared preferences.
      * @return
     */
    private int getUserId (){
        SharedPreferences pref = getContext().getSharedPreferences("preferences", 0); // 0 - for private mode
        return pref.getInt("userId", -1); // getting Integer
    }
}
package com.assignment2.vaccinator.ui.dashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.assignment2.vaccinator.R;
import com.assignment2.vaccinator.databinding.FragmentDashboardBinding;
import com.assignment2.vaccinator.models.Registration;
import com.assignment2.vaccinator.models.Sites;
import com.assignment2.vaccinator.models.TopBlock;
import com.assignment2.vaccinator.models.Vaccination;

/**
 * This fragment contains the dashboard for the application.
 */
public class DashboardFragment extends Fragment {

    //Declaring variables.
    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    //Declaring textviews
    private TextView tvTotalVax, tvDose1Vax, tvDose2Vax, tvTotalTodayVax,
            tvTotalCenters, tvGovtCenters,tvPrivateCenters,
            tvTotalRegs, tv18Regs, tv45Regs, tvTodayRegs,tvUsername;

    /**
     * This method is called when view is created. We are performing all the initialization in this method.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Get the root view.
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //Find and init the textviews using the resource id.
        tvTotalVax = root.findViewById(R.id.tv_total_vax_val);
        tvTotalTodayVax = root.findViewById(R.id.tv_vax_today_val);
        tvDose1Vax = root.findViewById(R.id.tv_vax_dose1_val);
        tvDose2Vax = root.findViewById(R.id.tv_vax_dose2_val);
        tvTotalCenters = root.findViewById(R.id.tv_total_sites_val);
        tvGovtCenters= root.findViewById(R.id.tv_total_sites_govt_val);
        tvPrivateCenters = root.findViewById(R.id.tv_total_sites_pvt_val);
        tvTotalRegs = root.findViewById(R.id.tv_total_reg_val);
        tv18Regs = root.findViewById(R.id.tv_total_reg_age18_val);
        tv45Regs = root.findViewById(R.id.tv_total_reg_age45_val);
        tvTodayRegs = root.findViewById(R.id.tv_total_reg_today_val);
        tvUsername = root.findViewById(R.id.loggedUser);

        //Fetch shared preferences.
        SharedPreferences pref = getContext().getSharedPreferences("preferences", 0); // 0 - for private mode

        // Showing user details on the dashboard.
        tvUsername.setText(("Hello, ").concat(pref.getString("username", "User")));

        //Lambda expression to fetch and map response data.
        // Using the live data model and observe. ig there are any change in the data
        // this method is called and UI is refreshed.
        dashboardViewModel.getCowinResponse().observe(getViewLifecycleOwner(), data -> mapResponseData(data));

        return root;
    }

    /**
     * Utility method to map API response model to the view.
     * @param topBlock
     */
    private void mapResponseData(TopBlock topBlock) {
        //Total Vax data
        Vaccination vaccination = topBlock.getVaccination();
        tvTotalVax.setText(String.valueOf(vaccination.getTotal()));
        tvTotalTodayVax.setText(String.valueOf(vaccination.getToday()));
        tvDose1Vax.setText(String.valueOf(vaccination.getTotalDose1()));
        tvDose2Vax.setText(String.valueOf(vaccination.getTotalDose2()));

        //Total Sites data
        Sites sites = topBlock.getSites();
        tvTotalCenters.setText(String.valueOf(sites.getTotal()));
        tvGovtCenters.setText(String.valueOf(sites.getGovt()));
        tvPrivateCenters.setText(String.valueOf(sites.getPvt()));

        //Total Registration data
        Registration registration = topBlock.getRegistration();
        tvTotalRegs.setText(String.valueOf(registration.getTotal()));
        tv18Regs.setText(String.valueOf(registration.getCitizenBetween18And45()));
        tv45Regs.setText(String.valueOf(registration.getCitizenAbove45()));
        tvTodayRegs.setText(String.valueOf(registration.getToday()));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
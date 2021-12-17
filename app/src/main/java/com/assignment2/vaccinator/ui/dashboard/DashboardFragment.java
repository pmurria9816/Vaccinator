package com.assignment2.vaccinator.ui.dashboard;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.assignment2.vaccinator.R;
import com.assignment2.vaccinator.databinding.FragmentDashboardBinding;
import com.assignment2.vaccinator.models.Registration;
import com.assignment2.vaccinator.models.Sites;
import com.assignment2.vaccinator.models.TopBlock;
import com.assignment2.vaccinator.models.Vaccination;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;

    private TextView tvTotalVax, tvDose1Vax, tvDose2Vax, tvTotalTodayVax,
            tvTotalCenters, tvGovtCenters,tvPrivateCenters,
            tvTotalRegs, tv18Regs, tv45Regs, tvTodayRegs,tvUsername;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

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

        dashboardViewModel.getCowinResponse().observe(getViewLifecycleOwner(), (Observer<TopBlock>) data -> {
            mapResponseData(data);
        });

        SharedPreferences pref = getContext().getSharedPreferences("preferences", 0); // 0 - for private mode

        tvUsername.setText(("Hello, ").concat(pref.getString("username", "User")));

        return root;
    }

    private void mapResponseData(TopBlock topBlock) {
        //Total Vax
        Vaccination vaccination = topBlock.getVaccination();
        tvTotalVax.setText(String.valueOf(vaccination.getTotal()));
        tvTotalTodayVax.setText(String.valueOf(vaccination.getToday()));
        tvDose1Vax.setText(String.valueOf(vaccination.getTotalDose1()));
        tvDose2Vax.setText(String.valueOf(vaccination.getTotalDose2()));

        //Total Sites
        Sites sites = topBlock.getSites();
        tvTotalCenters.setText(String.valueOf(sites.getTotal()));
        tvGovtCenters.setText(String.valueOf(sites.getGovt()));
        tvPrivateCenters.setText(String.valueOf(sites.getPvt()));

        //Total Registration
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
package com.ezerski.vladislav.easyenglish.ui.tests.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ezerski.vladislav.easyenglish.R;
import com.ezerski.vladislav.easyenglish.ui.main.activity.MainActivity;
import com.ezerski.vladislav.easyenglish.ui.profile.ProfileActivity;

public class TestResultFragment extends Fragment implements View.OnClickListener {

    private TextView tvTestResult;
    private int correctCount = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result_test, container, false);
        tvTestResult = view.findViewById(R.id.tv_test_result);
        Button btnExitToMain = view.findViewById(R.id.btn_go_to_main);
        btnExitToMain.setOnClickListener(this);
        Button btnExitToProfile = view.findViewById(R.id.btn_go_to_profile);
        btnExitToProfile.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            correctCount = bundle.getInt("result");
        }
        tvTestResult.setText(correctCount + " / " + 5 + " верно");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go_to_main:
                startActivity(new Intent(getContext(), MainActivity.class));
                break;
            case R.id.btn_go_to_profile:
                startActivity(new Intent(getContext(), ProfileActivity.class));
//                Fragment fragment = new ProfileFragment();
//                if (getFragmentManager() != null) {
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.fl_main_content_container, fragment);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//                }
                break;
        }
    }
}

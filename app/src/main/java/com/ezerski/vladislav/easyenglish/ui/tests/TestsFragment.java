package com.ezerski.vladislav.easyenglish.ui.tests;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ezerski.vladislav.easyenglish.R;

public class TestsFragment extends Fragment implements View.OnClickListener {

    private TextView tvTests;

    public TestsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tests, container, false);
        tvTests = view.findViewById(R.id.tv_tests);
        return view;
    }

    @Override
    public void onClick(View view) {

    }
}

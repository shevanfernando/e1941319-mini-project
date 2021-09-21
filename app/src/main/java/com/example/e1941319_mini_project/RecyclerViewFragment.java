package com.example.e1941319_mini_project;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e1941319_mini_project.dto.FetchPackageDataDTO;

public class RecyclerViewFragment extends Fragment {

    private static FetchPackageDataDTO packageData;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public static RecyclerViewFragment newInstance(FetchPackageDataDTO data) {
        packageData = data;
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }
}
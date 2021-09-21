package com.example.e1941319_mini_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.e1941319_mini_project.dto.FetchPackageDataDTO;
import com.example.e1941319_mini_project.model.Package;

import java.util.List;
import java.util.Objects;

public class RecyclerViewFragment extends Fragment {

    private static FetchPackageDataDTO packageData;
    @SuppressLint("StaticFieldLeak")
    private static Context context;
    private static List<Package> packageList;
    private static UserType loginUser;
    private static StatusType[] statusArray;

    public RecyclerViewFragment() {
    }

    public static RecyclerViewFragment newInstance(Context cnxt, List<Package> packages, UserType loginUserType, @Nullable StatusType[] status) {
        context = cnxt;
        packageList = packages;
        loginUser = loginUserType;
        statusArray = status;
        return new RecyclerViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        PackageCardAdapter adapter = new PackageCardAdapter(context, packageList, loginUser, statusArray);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
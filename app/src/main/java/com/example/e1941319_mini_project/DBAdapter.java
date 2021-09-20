package com.example.e1941319_mini_project;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.e1941319_mini_project.dto.LoginDTO;
import com.example.e1941319_mini_project.dto.LoginResponseDTO;
import com.example.e1941319_mini_project.dto.PackageDTO;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBAdapter {
    private final FirebaseFirestore FIREBANSEFIRESTORE;

    public DBAdapter() {
        this.FIREBANSEFIRESTORE = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<LoginResponseDTO> login(LoginDTO loginDTO) {
        MutableLiveData<LoginResponseDTO> loginRequest = new MutableLiveData<>();
        FIREBANSEFIRESTORE.collection("users").whereEqualTo("username", loginDTO.getUsername()).whereEqualTo("password", loginDTO.getPassword()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (!task.getResult().isEmpty()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        loginRequest.postValue(new LoginResponseDTO(UserType.valueOf((String) document.get("accountType")), true));
                    }
                } else {
                    loginRequest.postValue(new LoginResponseDTO(null, false));
                }
            } else {
                Log.e("Login Activity", "Error getting documents: ", task.getException());
            }
        });
        return loginRequest;
    }


    public MutableLiveData<Boolean> addNewPackage(AddPackageActivity add, PackageDTO packageDTO) {
        MutableLiveData<Boolean> isNewPackageAdd = new MutableLiveData<>();
        Map<String, Object> pkg = new HashMap<>();
        pkg.put("customerId", packageDTO.getCustomerId());
        pkg.put("deliveryAddress", packageDTO.getDeliveryAddress());
        pkg.put("description", packageDTO.getDescription());

        IdSequenceGenerator sequenceGenerator = new IdSequenceGenerator(FIREBANSEFIRESTORE, "packages");
        sequenceGenerator.generateId().observe(add, res -> {
            if (res != null) {
                pkg.put("id", res.getId());

                FIREBANSEFIRESTORE.collection("packages").document(res.getDocumentId()).set(pkg).addOnSuccessListener(aVoid -> {
                    Log.d("Add Package Activity", "DocumentSnapshot successfully written!");
                    isNewPackageAdd.postValue(true);
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Add Package Activity", "Error writing document", e);
                        isNewPackageAdd.postValue(false);
                    }
                });
            }
        });

        return isNewPackageAdd;
    }

    public MutableLiveData<List<String>> getAllCustomers() {
        MutableLiveData<List<String>> customers = new MutableLiveData<>();

        FIREBANSEFIRESTORE.collection("users").whereEqualTo("accountType", "USER").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (!task.getResult().isEmpty()) {
                    List<String> customerList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        customerList.add(document.getId());
                    }
                    customers.postValue(customerList);
                } else {
                    customers.postValue(null);
                }
            } else {
                Log.e("Login Activity", "Error getting documents: ", task.getException());
            }
        });

        return customers;
    }
}

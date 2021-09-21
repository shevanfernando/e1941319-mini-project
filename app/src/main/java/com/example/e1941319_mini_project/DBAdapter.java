package com.example.e1941319_mini_project;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.e1941319_mini_project.dto.FetchPackageDataDTO;
import com.example.e1941319_mini_project.dto.LoginDTO;
import com.example.e1941319_mini_project.dto.LoginResponseDTO;
import com.example.e1941319_mini_project.dto.PackageDTO;
import com.example.e1941319_mini_project.model.Package;
import com.example.e1941319_mini_project.model.Status;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class DBAdapter {
    private final FirebaseFirestore FIREBANSEFIRESTORE;

    public DBAdapter() {
        this.FIREBANSEFIRESTORE = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<LoginResponseDTO> login(@NonNull LoginDTO loginDTO) {
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


    @SuppressLint("SimpleDateFormat")
    public MutableLiveData<Boolean> addNewPackage(@NonNull AddPackageActivity add, @NonNull PackageDTO packageDTO) {
        MutableLiveData<Boolean> isNewPackageAdd = new MutableLiveData<>();

        IdSequenceGenerator sequenceGenerator = new IdSequenceGenerator(FIREBANSEFIRESTORE, "status_history");
        sequenceGenerator.generateId().observe(add, res -> {
            if (res != null) {
                Map<String, Object> status = new HashMap<>();
                status.put(
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                        packageDTO.getCurrentStatus());
                status.put("id", res.getId());
                FIREBANSEFIRESTORE.collection("status_history").document(res.getDocumentId()).set(status).addOnCompleteListener(aVoid -> {
                    IdSequenceGenerator sequenceGenerator1 = new IdSequenceGenerator(FIREBANSEFIRESTORE, "packages");
                    sequenceGenerator1.generateId().observe(add, result -> {
                        if (result != null) {
                            Map<String, Object> pkg = new HashMap<>();
                            pkg.put("customerId", packageDTO.getCustomerId());
                            pkg.put("deliveryAddress", packageDTO.getDeliveryAddress());
                            pkg.put("description", packageDTO.getDescription());
                            pkg.put("currentStatus", packageDTO.getCurrentStatus());
                            pkg.put("id", result.getId());
                            pkg.put("statusHistoryId", res.getDocumentId());

                            FIREBANSEFIRESTORE.collection("packages").document(result.getDocumentId()).set(pkg).addOnSuccessListener(aVoid2 -> {
                                Log.d("Add Package Activity", "DocumentSnapshot successfully written!");
                                isNewPackageAdd.postValue(true);
                            }).addOnFailureListener(e -> {
                                Log.w("Add Package Activity", "Error writing document", e);
                                isNewPackageAdd.postValue(false);
                            });
                        }
                    });
                }).addOnFailureListener(e -> {
                    Log.w("Add Package Activity", "Error writing document", e);
                    isNewPackageAdd.postValue(false);
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
                Log.e("Add Package Activity", "Error getting documents: ", task.getException());
            }
        });

        return customers;
    }

    public MutableLiveData<FetchPackageDataDTO> getAllPackages(@NonNull String TAG, @Nullable String username) {
        MutableLiveData<FetchPackageDataDTO> data = new MutableLiveData<>();

        Task<QuerySnapshot> querySnapshotTask;

        if (username != null) {
            querySnapshotTask = FIREBANSEFIRESTORE.collection("packages").whereEqualTo("customerId", username).get();
        } else {
            querySnapshotTask = FIREBANSEFIRESTORE.collection("packages").get();
        }

        querySnapshotTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (!task.getResult().isEmpty()) {
                    List<String> packageIdList = new ArrayList<>();
                    List<Package> packageData = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        packageIdList.add(document.getId());

                        List<Status> statusHistoryList = new ArrayList<>();

                        FIREBANSEFIRESTORE.collection("status_history").document(Objects.requireNonNull(document.getString("statusHistoryId"))).get().addOnCompleteListener(task2 -> {
                            if (task2.isSuccessful()) {
                                if (task2.getResult().exists()) {
                                    for (Map.Entry<String, Object> entry : task2.getResult().getData().entrySet()) {
                                        if (!entry.getKey().equals("id")) {
                                            statusHistoryList.add(new Status(entry.getKey(), (String) entry.getValue()));
                                        }
                                    }
                                }
                            }
                        });

                        packageData.add(new Package(document.getId(), document.getString("customerId"), document.getString("deliveryAddress"), document.getString("description"), document.getString("currentStatus"), statusHistoryList));
                    }
                    data.postValue(new FetchPackageDataDTO(packageIdList, packageData));
                } else {
                    data.postValue(null);
                }
            } else {
                Log.e(TAG, "Error getting documents: ", task.getException());
            }
        });

        return data;
    }
}

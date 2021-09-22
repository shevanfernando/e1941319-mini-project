package com.example.e1941319_mini_project;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.e1941319_mini_project.dto.FetchPackageDataDTO;
import com.example.e1941319_mini_project.dto.LoginAndRegisterDTO;
import com.example.e1941319_mini_project.dto.LoginAndRegisterResponseDTO;
import com.example.e1941319_mini_project.dto.PackageDTO;
import com.example.e1941319_mini_project.dto.StatusUpdateDTO;
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

public class DBAdapter {
    private final FirebaseFirestore FIREBANSEFIRESTORE;

    public DBAdapter() {
        this.FIREBANSEFIRESTORE = FirebaseFirestore.getInstance();
    }

    public MutableLiveData<LoginAndRegisterResponseDTO> login(@NonNull LoginAndRegisterDTO loginAndRegisterDTO) {
        MutableLiveData<LoginAndRegisterResponseDTO> loginRequest = new MutableLiveData<>();
        FIREBANSEFIRESTORE.collection("users").whereEqualTo("username", loginAndRegisterDTO.getUsername()).whereEqualTo("password", loginAndRegisterDTO.getPassword()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (!task.getResult().isEmpty()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        loginRequest.postValue(new LoginAndRegisterResponseDTO(UserType.valueOf((String) document.get("accountType")), document.getId(), true));
                    }
                } else {
                    loginRequest.postValue(new LoginAndRegisterResponseDTO(null, null, false));
                }
            } else {
                Log.e("Login Activity", "Error getting documents: ", task.getException());
                loginRequest.postValue(new LoginAndRegisterResponseDTO(null, null, false));
            }
        });
        return loginRequest;
    }

    public MutableLiveData<LoginAndRegisterResponseDTO> register(@NonNull UserRegistrationActivity reg, @NonNull LoginAndRegisterDTO registerData) {
        MutableLiveData<LoginAndRegisterResponseDTO> registerRequest = new MutableLiveData<>();
        IdSequenceGenerator sequenceGenerator = new IdSequenceGenerator(FIREBANSEFIRESTORE, "users");

        sequenceGenerator.generateId().observe(reg, res -> {
            if (res != null) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", res.getId());
                map.put("username", registerData.getUsername());
                map.put("password", registerData.getPassword());
                map.put("accountType", UserType.USER);

                FIREBANSEFIRESTORE.collection("users").whereEqualTo("username", registerData.getUsername()).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (Objects.requireNonNull(task.getResult()).size() == 0) {
                            FIREBANSEFIRESTORE.collection("users").document(res.getDocumentId()).set(map).addOnCompleteListener(task2 -> {
                                if (task.isSuccessful()) {
                                    Log.d("User Registration Activity", "New user successfuly added");
                                    registerRequest.postValue(new LoginAndRegisterResponseDTO(UserType.USER, res.getDocumentId(), true));
                                }
                            }).addOnFailureListener(e -> {
                                Log.e("User Registration Activity", "Error writing document", e);
                            });
                        } else {
                            registerRequest.postValue(new LoginAndRegisterResponseDTO(null, null, false));
                        }
                    }
                }).addOnFailureListener(e -> {
                    Log.e("User Registration Activity", "Error writing document", e);
                    registerRequest.postValue(new LoginAndRegisterResponseDTO(null, null, false));
                });
            }
        });

        return registerRequest;
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
                                Log.d("Add Package Activity", "New package successfully added!");
                                isNewPackageAdd.postValue(true);
                            }).addOnFailureListener(e -> {
                                Log.e("Add Package Activity", "Error writing document", e);
                                isNewPackageAdd.postValue(false);
                            });
                        }
                    });
                }).addOnFailureListener(e -> {
                    Log.e("Add Package Activity", "Error writing document", e);
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
            Log.e("DB_Adapter", username);
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
                                            statusHistoryList.add(new Status(entry.getKey(), StatusType.valueOf(entry.getValue().toString())));
                                        }
                                    }
                                }
                            }
                        });

                        packageData.add(new Package(document.getId(), document.getString("customerId"), document.getString("deliveryAddress"), document.getString("description"), StatusType.valueOf(document.getString("currentStatus")), document.getString("statusHistoryId"), statusHistoryList));
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

    private Integer _calculatePackageId(String packageId) {
        String[] s = packageId.split("_");
        int i = Integer.parseInt(s[1]) - 10010;
        return i;
    }

    public MutableLiveData<Boolean> updatePackageStatus(StatusUpdateDTO statusUpdateDTO) {
        MutableLiveData<Boolean> isPackageStatusUpdate = new MutableLiveData<>();
        Map<String, Object> map = new HashMap<>();
        map.put("currentStatus", statusUpdateDTO.getStatus());
        FIREBANSEFIRESTORE.collection("packages").document(statusUpdateDTO.getPackageId()).update(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Map<String, Object> status = new HashMap<>();
                status.put(
                        new SimpleDateFormat("yyyy-MM-dd").format(new Date()),
                        statusUpdateDTO.getStatus());
                FIREBANSEFIRESTORE.collection("status_history").document(statusUpdateDTO.getStatusHistoryId()).update(status);
                Log.d("Package_Single_View_Activity", "Package status update successfully.");
                isPackageStatusUpdate.postValue(true);
            } else {
                Log.d("Package_Single_View_Activity", "Package status update failed.");
                isPackageStatusUpdate.postValue(false);
            }
        }).addOnFailureListener(e -> {
            Log.e("Update Package Status", "Error updating document", e);
            isPackageStatusUpdate.postValue(false);
        });
        return isPackageStatusUpdate;
    }
}

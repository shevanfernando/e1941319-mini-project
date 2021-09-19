package com.example.e1941319_mini_project;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.e1941319_mini_project.model.SequenceGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class IdSequenceGenerator {

    private final FirebaseFirestore FIREBANSEFIRESTORE;
    private final String COLLECTIONNAME;
    private final String TAG = "Id_Sequence_Generator";

    public IdSequenceGenerator(FirebaseFirestore FIREBANSEFIRESTORE, String COLLECTIONNAME) {
        this.FIREBANSEFIRESTORE = FIREBANSEFIRESTORE;
        this.COLLECTIONNAME = COLLECTIONNAME;
    }

    public MutableLiveData<SequenceGenerator> generateId() {
        MutableLiveData<SequenceGenerator> id = new MutableLiveData<>();
        FIREBANSEFIRESTORE.collection(COLLECTIONNAME).orderBy("id", Query.Direction.DESCENDING).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().isEmpty()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String[] currentId = document.getId().split("_");
                            id.postValue(new SequenceGenerator(String.format("%s_%07d", currentId[0], (Integer.parseInt(currentId[1]) + 1)),
                                    ((Long) document.get("id")) + 1));
                        }
                    } else {
                        id.postValue(null);
                    }
                } else {
                    Log.e(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        return id;
    }
}

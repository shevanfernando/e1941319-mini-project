package com.example.e1941319_mini_project;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.e1941319_mini_project.model.Package;
import com.example.e1941319_mini_project.model.Status;

public class ProductCardFragment extends Fragment {
    private static Package packageData;

    public ProductCardFragment() {
    }

    public static ProductCardFragment newInstance(Package pck) {
        packageData = pck;
        return new ProductCardFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_card, container, false);

        CardView cardView = view.findViewById(R.id.base_card_view);
        ImageButton expand_button = view.findViewById(R.id.expand_button);
        LinearLayout hiddenView = view.findViewById(R.id.hidden_view);

        TextView crd_pkg_heading = view.findViewById(R.id.crd_pkg_heading);
        TextView crd_pkg_address_label = view.findViewById(R.id.crd_pkg_address_label);
        TextView crd_pkg_description = view.findViewById(R.id.crd_pkg_description);

        crd_pkg_heading.setText(packageData.getPackageId());
        crd_pkg_address_label.setText(packageData.getDeliveryAddress());
        crd_pkg_description.setText(packageData.getDescription());

        expand_button.setOnClickListener(view1 -> {
            if (hiddenView.getVisibility() == View.VISIBLE) {
                TransitionManager.beginDelayedTransition(cardView,
                        new AutoTransition());
                hiddenView.setVisibility(View.GONE);
                expand_button.setImageResource(R.drawable.ic_expand_more);
                hiddenView.removeAllViews();
            } else {
                TransitionManager.beginDelayedTransition(cardView,
                        new AutoTransition());
                hiddenView.setVisibility(View.VISIBLE);
                for (Status status : packageData.getStatus()) {
                    View view2 = inflater.inflate(R.layout.package_status, container, false);
                    TextView pkg_status_date = view2.findViewById(R.id.pkg_status_date);
                    TextView pkg_status_label = view2.findViewById(R.id.pkg_status_label);

                    pkg_status_date.setText(status.getDate());
                    pkg_status_label.setText(status.getStatus());

                    hiddenView.addView(view2);
                }
                expand_button.setImageResource(R.drawable.ic_expand_less);
            }
        });
        return view;
    }
}
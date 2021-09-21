package com.example.e1941319_mini_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e1941319_mini_project.model.Package;
import com.example.e1941319_mini_project.model.Status;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PackageCardAdapter extends RecyclerView.Adapter<PackageCardAdapter.PackageViewHolder> {

    private final Context context;
    private final List<Package> packageList;
    private ViewGroup parent;

    public PackageCardAdapter(Context context, List<Package> packageList) {
        this.context = context;
        this.packageList = packageList;
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_product_card, parent, false);
        return new PackageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        Package pkg = packageList.get(position);

        holder.crd_pkg_heading.setText(pkg.getPackageId());
        holder.crd_pkg_address_label.setText(pkg.getDeliveryAddress());
        holder.crd_pkg_description.setText(pkg.getDescription());
        if (pkg.getStatus() != null) {
            holder.expand_button.setOnClickListener(view1 -> {
                if (holder.hiddenView.getVisibility() == View.VISIBLE) {
                    TransitionManager.beginDelayedTransition(holder.cardView,
                            new AutoTransition());
                    holder.hiddenView.setVisibility(View.GONE);
                    holder.expand_button.setImageResource(R.drawable.ic_expand_more);
                    holder.hiddenView.removeAllViews();
                } else {
                    TransitionManager.beginDelayedTransition(holder.cardView,
                            new AutoTransition());
                    holder.hiddenView.setVisibility(View.VISIBLE);

                    Collections.sort(pkg.getStatus(), (status, status2) -> {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            return Objects.requireNonNull(dateFormat.parse(status.getDate())).compareTo(dateFormat.parse(status2.getDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return 0;
                    });

                    for (Status status : pkg.getStatus()) {
                        View view2 = LayoutInflater.from(context).inflate(R.layout.package_status, parent, false);
                        TextView pkg_status_date = view2.findViewById(R.id.pkg_status_date);
                        TextView pkg_status_label = view2.findViewById(R.id.pkg_status_label);

                        pkg_status_date.setText(status.getDate());
                        pkg_status_label.setText(status.getStatus());

                        holder.hiddenView.addView(view2);
                    }
                    holder.expand_button.setImageResource(R.drawable.ic_expand_less);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }

    public static class PackageViewHolder extends RecyclerView.ViewHolder {

        TextView crd_pkg_heading, crd_pkg_address_label, crd_pkg_description;
        CardView cardView;
        LinearLayout hiddenView;
        ImageButton expand_button;

        public PackageViewHolder(@NonNull View itemView) {
            super(itemView);
            crd_pkg_heading = itemView.findViewById(R.id.crd_pkg_heading);
            crd_pkg_address_label = itemView.findViewById(R.id.crd_pkg_address_label);
            crd_pkg_description = itemView.findViewById(R.id.crd_pkg_description);
            expand_button = itemView.findViewById(R.id.expand_button);
            cardView = itemView.findViewById(R.id.base_card_view);
            hiddenView = itemView.findViewById(R.id.hidden_view);
        }
    }
}

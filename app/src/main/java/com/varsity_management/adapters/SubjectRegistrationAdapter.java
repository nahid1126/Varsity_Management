package com.varsity_management.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;
import com.varsity_management.R;
import com.varsity_management.model.SubjectModel;
import com.varsity_management.model.SubjectName;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectRegistrationAdapter extends RecyclerView.Adapter<SubjectRegistrationAdapter.ViewHolder> {
    private static final String TAG = "SubjectOfferAdapter";
    private List<SubjectModel> subjectModelList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_semester_registration, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectRegistrationAdapter.ViewHolder holder, int position) {
        SubjectModel subjectModel = subjectModelList.get(position);

        double totalCredit = 0.0;
        holder.txtSemesterName.setText(subjectModel.getSemester());
        holder.txtSemester.setText(subjectModel.getSemester());
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilderCredit = new StringBuilder();
        for (SubjectName subjectName : subjectModel.getSubjectNameList()) {
            stringBuilder.append(subjectName.getSubCodeName() + "\n");
            stringBuilderCredit.append(subjectName.getCredit() + "\n");
            totalCredit += subjectName.getCredit();
        }
        holder.txtSubjectName.setText(stringBuilder);
        holder.txtTotalCredit.setText("Total Credit: " + totalCredit);
        holder.txtTotal.setText(String.valueOf(totalCredit));
        holder.txtSubjectCredit.setText(String.valueOf(stringBuilderCredit));

        holder.btnAdd.setOnClickListener(view -> Toast.makeText(view.getContext(), "clicked", Toast.LENGTH_SHORT).show());
        
        holder.foldingCell.setOnClickListener(view -> holder.foldingCell.toggle(false));
    }

    @Override
    public int getItemCount() {
        if (subjectModelList == null) {
            return 0;
        } else {
            return subjectModelList.size();
        }
    }

    public void setSubjectModelList(List<SubjectModel> subjectModelList) {
        this.subjectModelList = subjectModelList;
        notifyDataSetChanged();
        Log.d(TAG, "setSubjectModelList: " + this.subjectModelList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtSemesterName)
        TextView txtSemesterName;


        @BindView(R.id.txtTotalCredit)
        TextView txtTotalCredit;

        @BindView(R.id.foldingCell)
        FoldingCell foldingCell;

        @BindView(R.id.txtSubjectName)
        TextView txtSubjectName;

        @BindView(R.id.txtSubjectCredit)
        TextView txtSubjectCredit;

        @BindView(R.id.txtTotal)
        TextView txtTotal;

        @BindView(R.id.txtSemester)
        TextView txtSemester;

        @BindView(R.id.btnAdd)
        TextView btnAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

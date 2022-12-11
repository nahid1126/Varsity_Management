package com.varsity_management.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.varsity_management.R;
import com.varsity_management.model.SubjectModel;
import com.varsity_management.model.SubjectName;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectOfferAdapter extends RecyclerView.Adapter<SubjectOfferAdapter.ViewHolder> {
    private static final String TAG = "SubjectOfferAdapter";
    private List<SubjectModel> subjectModelList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dummy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectOfferAdapter.ViewHolder holder, int position) {
        SubjectModel subjectModel = subjectModelList.get(position);

        holder.txtSemesterName.setText(subjectModel.getSemester());
        StringBuilder stringBuilder = new StringBuilder();
        for(SubjectName subjectName : subjectModel.getSubjectNameList()) {
            stringBuilder.append(subjectName.getSubCodeName()+"\n");
        }
        holder.txtTotalCredit.setText(stringBuilder);

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
        @BindView(R.id.txtSem)
        TextView txtSemesterName;


        @BindView(R.id.txtCr)
        TextView txtTotalCredit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

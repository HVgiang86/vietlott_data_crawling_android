package com.example.vietlottdatacrawl.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vietlottdatacrawl.R;
import com.example.vietlottdatacrawl.databinding.SessionItemLayoutBinding;
import com.example.vietlottdatacrawl.model.PrizeDrawSession;

import java.util.List;
import java.util.Locale;

public class Max3DResultAdapter extends RecyclerView.Adapter<Max3DResultAdapter.ViewHolder> {
    //Final Variable
    private static final int RESULT_FOUND_VIEW_TYPE = 10;
    private static final int RESULT_NOT_FOUND_VIEW_TYPE = 11;
    private final Context context;

    //Item List Dataset
    private List<PrizeDrawSession> sessionList;

    public Max3DResultAdapter(List<PrizeDrawSession> sessionList, Context context) {
        this.sessionList = sessionList;
        this.context = context;
    }

    public void setSessionList(List<PrizeDrawSession> sessionList) {
        this.sessionList = sessionList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        SessionItemLayoutBinding binding;
        public ObservableField<String> id = new ObservableField<>();
        public ObservableField<String> dateStr = new ObservableField<>();
        public ObservableArrayList<String> prizeNumber = new ObservableArrayList<>();

        public ViewHolder(SessionItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.view = binding.getRoot();
        }

        public void setBinding(String id, String dateStr, Byte[] prizeNumber) {
            if (binding.getItemView() == null)
                binding.setItemView(this);

            for (int i = 0; i < 60; i++) {
                String s = String.format(Locale.US, "%d", prizeNumber[i]);
                this.prizeNumber.add(s);
            }
            this.id.set(id);
            this.dateStr.set(dateStr);

            binding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SessionItemLayoutBinding binding;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (viewType == RESULT_NOT_FOUND_VIEW_TYPE)
            binding = DataBindingUtil.inflate(inflater,R.layout.result_not_found_layout, parent, false);
        else
            binding = DataBindingUtil.inflate(inflater,R.layout.session_item_layout, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (getItemViewType(position) != RESULT_NOT_FOUND_VIEW_TYPE) {
            PrizeDrawSession session = sessionList.get(position);
            String drawSessionId = "Kỳ quay số: #" + session.getId();
            String dateStr = session.getDateString();
            Byte[] prizeNumber = session.getPrizeNumber();

            holder.setBinding(drawSessionId,dateStr,prizeNumber);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (sessionList.size() == 0) {
            return RESULT_NOT_FOUND_VIEW_TYPE;
        }
        return RESULT_FOUND_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        if (sessionList.size() == 0)
            return 1;
        return sessionList.size();
    }
}

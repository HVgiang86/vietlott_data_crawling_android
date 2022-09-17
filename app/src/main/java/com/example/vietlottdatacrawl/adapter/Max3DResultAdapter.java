package com.example.vietlottdatacrawl.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vietlottdatacrawl.R;
import com.example.vietlottdatacrawl.model.PrizeDrawSession;

import java.util.List;

public class Max3DResultAdapter extends RecyclerView.Adapter<Max3DResultAdapter.ViewHolder> {
    private List<PrizeDrawSession>  sessionList;
    private final Context context;
    private final int[] textViewIdArray = new int[60];
    private static final int RESULT_FOUND_VIEW_TYPE = 10;
    private static final int RESULT_NOT_FOUND_VIEW_TYPE = 11;

    public Max3DResultAdapter(Context context) {
        this.context = context;
        convertTextViewIdToIdArray();
    }

    public void setSessionList(List<PrizeDrawSession> sessionList) {
        this.sessionList = sessionList;
    }

    public Max3DResultAdapter(List<PrizeDrawSession> sessionList, Context context) {
        this.sessionList = sessionList;
        this.context = context;
        convertTextViewIdToIdArray();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.view = itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;
        if (viewType == RESULT_NOT_FOUND_VIEW_TYPE)
            v = inflater.inflate(R.layout.result_not_found_layout,parent,false);
        else
             v = inflater.inflate(R.layout.session_item_layout,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (getItemViewType(position) != RESULT_NOT_FOUND_VIEW_TYPE) {
            PrizeDrawSession session = sessionList.get(position);
            Byte[] prizeNumber = session.getPrizeNumber();
            TextView dateTextView = holder.view.findViewById(R.id.session_date);
            TextView idTextView = holder.view.findViewById(R.id.session_id);

            dateTextView.setText(session.getDateString());
            String drawSessionId = "Kỳ quay số: #" + session.getId();
            idTextView.setText(drawSessionId);
            for (int i = 1; i <= 60; ++i) {
                int id = getTextViewId(i);
                TextView textView = holder.view.findViewById(id);
                textView.setText(prizeNumber[i-1].toString());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (sessionList.size() == 0)  {
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

    private void convertTextViewIdToIdArray() {
        int i = 0;
        textViewIdArray[i++] = R.id.prize_number_1;
        textViewIdArray[i++] = R.id.prize_number_2;
        textViewIdArray[i++] = R.id.prize_number_3;
        textViewIdArray[i++] = R.id.prize_number_4;
        textViewIdArray[i++] = R.id.prize_number_5;
        textViewIdArray[i++] = R.id.prize_number_6;
        textViewIdArray[i++] = R.id.prize_number_7;
        textViewIdArray[i++] = R.id.prize_number_8;
        textViewIdArray[i++] = R.id.prize_number_9;
        textViewIdArray[i++] = R.id.prize_number_10;
        textViewIdArray[i++] = R.id.prize_number_11;
        textViewIdArray[i++] = R.id.prize_number_12;
        textViewIdArray[i++] = R.id.prize_number_13;
        textViewIdArray[i++] = R.id.prize_number_14;
        textViewIdArray[i++] = R.id.prize_number_15;
        textViewIdArray[i++] = R.id.prize_number_16;
        textViewIdArray[i++] = R.id.prize_number_17;
        textViewIdArray[i++] = R.id.prize_number_18;
        textViewIdArray[i++] = R.id.prize_number_19;
        textViewIdArray[i++] = R.id.prize_number_20;
        textViewIdArray[i++] = R.id.prize_number_21;
        textViewIdArray[i++] = R.id.prize_number_22;
        textViewIdArray[i++] = R.id.prize_number_23;
        textViewIdArray[i++] = R.id.prize_number_24;
        textViewIdArray[i++] = R.id.prize_number_25;
        textViewIdArray[i++] = R.id.prize_number_26;
        textViewIdArray[i++] = R.id.prize_number_27;
        textViewIdArray[i++] = R.id.prize_number_28;
        textViewIdArray[i++] = R.id.prize_number_29;
        textViewIdArray[i++] = R.id.prize_number_30;
        textViewIdArray[i++] = R.id.prize_number_31;
        textViewIdArray[i++] = R.id.prize_number_32;
        textViewIdArray[i++] = R.id.prize_number_33;
        textViewIdArray[i++] = R.id.prize_number_34;
        textViewIdArray[i++] = R.id.prize_number_35;
        textViewIdArray[i++] = R.id.prize_number_36;
        textViewIdArray[i++] = R.id.prize_number_37;
        textViewIdArray[i++] = R.id.prize_number_38;
        textViewIdArray[i++] = R.id.prize_number_39;
        textViewIdArray[i++] = R.id.prize_number_40;
        textViewIdArray[i++] = R.id.prize_number_41;
        textViewIdArray[i++] = R.id.prize_number_42;
        textViewIdArray[i++] = R.id.prize_number_43;
        textViewIdArray[i++] = R.id.prize_number_44;
        textViewIdArray[i++] = R.id.prize_number_45;
        textViewIdArray[i++] = R.id.prize_number_46;
        textViewIdArray[i++] = R.id.prize_number_47;
        textViewIdArray[i++] = R.id.prize_number_48;
        textViewIdArray[i++] = R.id.prize_number_49;
        textViewIdArray[i++] = R.id.prize_number_50;
        textViewIdArray[i++] = R.id.prize_number_51;
        textViewIdArray[i++] = R.id.prize_number_52;
        textViewIdArray[i++] = R.id.prize_number_53;
        textViewIdArray[i++] = R.id.prize_number_54;
        textViewIdArray[i++] = R.id.prize_number_55;
        textViewIdArray[i++] = R.id.prize_number_56;
        textViewIdArray[i++] = R.id.prize_number_57;
        textViewIdArray[i++] = R.id.prize_number_58;
        textViewIdArray[i++] = R.id.prize_number_59;
        textViewIdArray[i] = R.id.prize_number_60;
    }
    
    private int getTextViewId(int textViewIndex) {
        return textViewIdArray[textViewIndex-1];
    }
}

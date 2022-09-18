package com.example.vietlottdatacrawl.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vietlottdatacrawl.R;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    public interface SectionCallback {
        boolean isSectionHeader(int pos);
        String getSectionHeaderContentString(int pos);
    }

    private final Context context;
    private final int headerOffset;
    private final SectionCallback callback;
    private View headerView = null;
    TextView contentTextView;
    int thisHeaderViewPos = 0;

    public ItemDecoration(Context context, int headerOffset, SectionCallback callback) {
        this.context = context;
        this.headerOffset = headerOffset;
        this.callback = callback;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        if (callback.isSectionHeader(pos)) {
            outRect.top = headerOffset;
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (headerView == null) {
            headerView = inflateHeader(parent);
            contentTextView = headerView.findViewById(R.id.pinned_header_text);
            fixLayoutSize(headerView,parent);
        }

        String previousMonthYear = "";
        for(int i=0;i<parent.getChildCount();i++)
        {
            View child = parent.getChildAt(i);
            int childPos = parent.getChildAdapterPosition(child);
            String monthYear = callback.getSectionHeaderContentString(childPos);
            contentTextView.setText(monthYear);

            if(!monthYear.equals(previousMonthYear) || callback.isSectionHeader(childPos))
            {
                thisHeaderViewPos = childPos;
                drawHeader(c,child,headerView);
                previousMonthYear = monthYear;
            }
        }
    }

    private void drawHeader(Canvas c, View child, View headerView) {
        c.save();
        c.translate(0,child.getTop()-headerView.getHeight());
        headerView.draw(c);
        c.restore();
    }

    private View inflateHeader(RecyclerView parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflater.inflate(R.layout.pinned_header_layout,parent,false);
    }

    public void fixLayoutSize(View view, ViewGroup viewGroup)
    {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(viewGroup.getWidth(),View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(viewGroup.getHeight(),View.MeasureSpec.UNSPECIFIED);
        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,viewGroup.getPaddingLeft()+viewGroup.getPaddingRight(),view.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,viewGroup.getPaddingTop()+viewGroup.getPaddingBottom(),view.getLayoutParams().height);

        view.measure(childWidth,childHeight);
        view.layout(0,0,view.getMeasuredWidth(),view.getMeasuredHeight());
    }
}

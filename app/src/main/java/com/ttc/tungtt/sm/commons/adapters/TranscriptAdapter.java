package com.ttc.tungtt.sm.commons.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ttc.tungtt.sm.R;
import com.ttc.tungtt.sm.models.TranscriptModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Sat, 03 Oct 2020 - 09:47
 */
public class TranscriptAdapter extends RecyclerView.Adapter<TranscriptAdapter.ViewHolder> {

    @Retention(RetentionPolicy.SOURCE)
    public @interface RESULT_VIEW_TYPE {
        int EDIT_TEXT = 1;
        int TEXT_VIEW = 0;
    }

    private Context mContext;
    private List<TranscriptModel> mTranscriptList;
    private int mResultViewType;
    private OnTranscriptListener mListener;

    public TranscriptAdapter(Context context,
                             List<TranscriptModel> transcriptList,
                             int resultViewType,
                             OnTranscriptListener listener) {
        this.mContext = context;
        this.mTranscriptList = transcriptList;
        this.mResultViewType = resultViewType;
        this.mListener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_transcript, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TranscriptModel item = mTranscriptList.get(position);
        holder.nameTextView.setText(item.getSubject().getName());
        switch (mResultViewType) {
            case RESULT_VIEW_TYPE.EDIT_TEXT:
                holder.resultEditText.setVisibility(View.VISIBLE);
                holder.resultTextView.setVisibility(View.GONE);
                holder.resultEditText.setKeyListener(DigitsKeyListener.getInstance(true, true));
                holder.resultEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                            if (mListener != null) {
                                mListener.onChangeResult(position, editable.toString());
                            }
                    }
                });
                holder.resultEditText.setText(String.valueOf(item.getResult()));
                break;
            case RESULT_VIEW_TYPE.TEXT_VIEW:
                holder.resultEditText.setVisibility(View.GONE);
                holder.resultTextView.setVisibility(View.VISIBLE);
                holder.resultTextView.setText(String.valueOf(item.getResult()));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mTranscriptList == null ? 0 : mTranscriptList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView nameTextView;
        @BindView(R.id.et_result)
        EditText resultEditText;
        @BindView(R.id.tv_result)
        TextView resultTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnTranscriptListener {
        void onChangeResult(int position, String result);
    }
}

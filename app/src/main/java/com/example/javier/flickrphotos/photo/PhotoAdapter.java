package com.example.javier.flickrphotos.photo;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.javier.flickrphotos.R;
import com.example.javier.flickrphotos.model.photo.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Javier on 20/12/2017.
 */

public class PhotoAdapter extends RecyclerView
        .Adapter<PhotoAdapter
        .DataObjectHolder> {

    private Context mContext;
    private List<Item> mResult;
    private static PhotoAdapter.ClickListener sClickListener;

    static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        @BindView(R.id.photo_item_name_textView)
        TextView mName;

        @BindView(R.id.photo_item_imageView)
        CircleImageView mImageView;

        @BindView(R.id.photo_item_constraintLayout)
        ConstraintLayout mConstraintLayout;

        DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mConstraintLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            sClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
    public void setOnItemClickListener(PhotoAdapter.ClickListener myClickListener) {
        this.sClickListener = myClickListener;
    }

    public PhotoAdapter(Context context, List<Item> example) {
        mContext = context;
        mResult = example;
    }

    @Override
    public PhotoAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);

        PhotoAdapter.DataObjectHolder dataObjectHolder = new PhotoAdapter.DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.DataObjectHolder holder, int position) {

        holder.mName.setText(mResult.get(position).getTitle());
        Glide.with(mContext).load(mResult.get(position).getMedia().getM()).into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    interface ClickListener {
        void onItemClick(int position, View view);
    }
}

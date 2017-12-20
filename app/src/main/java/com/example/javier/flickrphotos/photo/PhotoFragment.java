package com.example.javier.flickrphotos.photo;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.javier.flickrphotos.R;
import com.example.javier.flickrphotos.model.photo.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

/**
 * Created by Javier on 20/12/2017.
 */

public class PhotoFragment extends Fragment implements PhotoContract.View {

    @BindView(R.id.photo_fragment_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.photo_fragment_retry_button)
    Button mRetry;

    @BindView(R.id.photo_fragment_constraintLayout)
    ConstraintLayout mRelativeLayout;

    @BindView(R.id.photo_fragment_recycler_view)
    RecyclerView mRecyclerView;

    PhotoAdapter mAdapter;

    PhotoContract.Presenter mPresenter;

    public static PhotoFragment newInstance() {
        return new PhotoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.photo_fragment, container, false);
        ButterKnife.bind(this, view);
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void setPresenter(PhotoContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showPhotos(List<Item> items) {
        mRecyclerView.setHasFixedSize(true);

        final int spanCount = getResources().getInteger(R.integer.grid_columns);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        mAdapter = new PhotoAdapter(getActivity(), items);
        mRecyclerView.setAdapter(mAdapter);
        //mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void showError() {
        mProgressBar.setVisibility(View.GONE);
        mRetry.setVisibility(View.VISIBLE);
        Snackbar.make(mRelativeLayout, getActivity().getResources().getText(R.string.error_server).toString(), LENGTH_LONG).show();
        mRetry.setText(getString(R.string.retry));
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (!active) {
            mRetry.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @OnClick(R.id.photo_fragment_retry_button)
    public void onClick(){
        setLoadingIndicator(false);
        mPresenter.fetch();
    }
}

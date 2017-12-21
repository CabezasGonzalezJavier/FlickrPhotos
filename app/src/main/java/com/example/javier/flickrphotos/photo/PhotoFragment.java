package com.example.javier.flickrphotos.photo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
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

public class PhotoFragment extends Fragment implements PhotoContract.View, PhotoAdapter.ClickListener {

    List<Item> mListItems;
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
        mListItems = items;
        mRecyclerView.setHasFixedSize(true);

        final int spanCount = getResources().getInteger(R.integer.grid_columns);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), spanCount));
        mAdapter = new PhotoAdapter(getActivity(), items);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
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
    public void onClick() {
        setLoadingIndicator(false);
        mPresenter.fetch();
    }


    public Point onWindowFocusChanged(View view) {
        int[] location = new int[2];

        view.getLocationOnScreen(location);
        // Initialize the Point with x, and y positions
        Point point = new Point();
        point.x = location[0];
        point.y = location[1];

        return point;

    }

    @Override
    public void onItemClick(int position, View view) {

        showPopup(onWindowFocusChanged(view), mListItems.get(position).getMedia().getM());
    }

    private void showPopup(Point p, String url) {

        Rect rectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int StatusBarHeight = rectangle.top;

        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int popupWidth = display.getWidth();
        int popupHeight = (display.getHeight() - StatusBarHeight);

        // Inflate the popup_layout.xml
        ConstraintLayout viewGroup = getActivity()
                .findViewById(R.id.popup_constraintLayout);
        LayoutInflater layoutInflater = (LayoutInflater) getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_layout, viewGroup);

        // Creating the PopupWindow
        final PopupWindow popup = new PopupWindow(getActivity());
        popup.setContentView(layout);
        popup.setWidth(popupWidth);
        popup.setHeight(popupHeight);
        popup.setFocusable(true);
        popup.setAnimationStyle(R.style.PopupWindowAnimation);

        // Some offset to align the popup a bit to the right, and a bit down,
        // relative to button's position.

        int OFFSET_X = 0;
        int OFFSET_Y = 0;
        // Clear the default translucent background
        popup.setBackgroundDrawable(new BitmapDrawable());
        // Displaying the popup at the specified location, + offsets.
        popup.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y
                + OFFSET_Y);

        ImageView imageView = layout.findViewById(R.id.popup_imageView);
        Glide.with(getActivity()).load(url).into(imageView);
        // Getting a reference to Close button, and close the popup when
        // clicked.
        Button close = layout.findViewById(R.id.popup_button);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });

    }

}

package com.example.javier.flickrphotos.photo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.javier.flickrphotos.PhotosApplication;
import com.example.javier.flickrphotos.R;
import com.example.javier.flickrphotos.model.RemoteDataSource;
import com.example.javier.flickrphotos.utils.scheduler.BaseSchedulerProvider;

import javax.inject.Inject;

import static com.example.javier.flickrphotos.utils.Utils.addFragmentToActivity;

public class PhotoActivity extends AppCompatActivity {
    @Inject
    RemoteDataSource mRemoteDataSource;

    @Inject
    BaseSchedulerProvider mSchedulerProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_activity);
        initializeDagger();
        initFragment();
    }

    private void initializeDagger() {
        PhotosApplication app = (PhotosApplication) getApplication();
        app.getAppComponent().inject(this);
    }

    private void initFragment () {
        PhotoFragment photoFragment = (PhotoFragment) getSupportFragmentManager().
                findFragmentById(R.id.photo_activity_contentFrame);

        if (photoFragment == null) {
            photoFragment = photoFragment.newInstance();
            addFragmentToActivity(getSupportFragmentManager(),
                    photoFragment, R.id.photo_activity_contentFrame);
        }

        new PhotoPresenter(mRemoteDataSource, photoFragment, mSchedulerProvider);

    }
}

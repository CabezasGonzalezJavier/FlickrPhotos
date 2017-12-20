package com.example.javier.flickrphotos.photo;

import android.support.annotation.NonNull;

import com.example.javier.flickrphotos.model.RemoteDataSource;
import com.example.javier.flickrphotos.model.photo.Example;
import com.example.javier.flickrphotos.utils.scheduler.BaseSchedulerProvider;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.example.javier.flickrphotos.utils.Constants.API_KEY;
import static com.example.javier.flickrphotos.utils.Constants.FORMAT;
import static com.example.javier.flickrphotos.utils.Constants.METHOD;
import static com.example.javier.flickrphotos.utils.Constants.NO_JSON_CALL_BACK;
import static com.example.javier.flickrphotos.utils.Constants.PER_PAGE;
import static com.example.javier.flickrphotos.utils.Constants.TAGS;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Javier on 20/12/2017.
 */

public class PhotoPresenter implements PhotoContract.Presenter {

    @NonNull
    private PhotoContract.View mView;

    @NonNull
    private BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeSubscription mSubscriptions;

    @NonNull
    private RemoteDataSource mRemoteDataSource;

    public PhotoPresenter(@NonNull RemoteDataSource remoteDataSource, @NonNull PhotoContract.View view, @NonNull BaseSchedulerProvider provider) {
        this.mRemoteDataSource = checkNotNull(remoteDataSource, "remoteDataSource");
        this.mView = checkNotNull(view, "view cannot be null!");
        this.mSchedulerProvider = checkNotNull(provider, "schedulerProvider cannot be null");

        mSubscriptions = new CompositeSubscription();

        mView.setPresenter(this);
    }


    @Override
    public void subscribe() {
        fetch();
    }

    @Override
    public void unSubscribe() {

    }

    @Override
    public void fetch() {
        mView.setLoadingIndicator(true);
        Subscription subscription = mRemoteDataSource.getPhotoRx(METHOD, API_KEY, TAGS, PER_PAGE, FORMAT, NO_JSON_CALL_BACK)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe((Example example) -> {
                            mView.setLoadingIndicator(false);
                            mView.showPhotos(example.getItems());
                        },
                        (Throwable error) -> {
                            try {
                                mView.showError();
                            } catch (Throwable t) {
                                throw new IllegalThreadStateException();
                            }

                        },
                        () -> {
                        });

        mSubscriptions.add(subscription);
    }
}

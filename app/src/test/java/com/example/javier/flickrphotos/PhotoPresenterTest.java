package com.example.javier.flickrphotos;

import com.example.javier.flickrphotos.model.RemoteDataSource;
import com.example.javier.flickrphotos.model.photo.Example;
import com.example.javier.flickrphotos.photo.PhotoContract;
import com.example.javier.flickrphotos.photo.PhotoPresenter;
import com.example.javier.flickrphotos.utils.scheduler.BaseSchedulerProvider;
import com.example.javier.flickrphotos.utils.scheduler.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import rx.Observable;

import static com.example.javier.flickrphotos.utils.Constants.API_KEY;
import static com.example.javier.flickrphotos.utils.Constants.FORMAT;
import static com.example.javier.flickrphotos.utils.Constants.METHOD;
import static com.example.javier.flickrphotos.utils.Constants.NO_JSON_CALL_BACK;
import static com.example.javier.flickrphotos.utils.Constants.PER_PAGE;
import static com.example.javier.flickrphotos.utils.Constants.TAGS;
import static org.mockito.Mockito.when;

/**
 * Created by Javier on 20/12/2017.
 */

public class PhotoPresenterTest {

    @Mock
    private PhotoContract.View mView;

    @Mock
    private BaseSchedulerProvider mSchedulerProvider;

    @Mock
    private RemoteDataSource mRemoteDataSource;

    PhotoPresenter mPresenter;
    Example mResult;

    @Before
    public void setup() {

        mResult = new Example("accurate");


        MockitoAnnotations.initMocks(this);
        mSchedulerProvider = new ImmediateSchedulerProvider();
        mPresenter = new PhotoPresenter(mRemoteDataSource, mView, mSchedulerProvider);
    }

    @Test
    public void fetchData() {
        when(mRemoteDataSource.getPhotoRx(METHOD, API_KEY, TAGS, PER_PAGE, FORMAT, NO_JSON_CALL_BACK))
                .thenReturn(rx.Observable.just(mResult));

        mPresenter.fetch();

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).setLoadingIndicator(true);
        inOrder.verify(mView).setLoadingIndicator(false);
        inOrder.verify(mView).showPhotos(mResult.getItems());
    }

    @Test
    public void fetchError() {

        when(mRemoteDataSource.getPhotoRx(METHOD, API_KEY, TAGS, PER_PAGE, FORMAT, NO_JSON_CALL_BACK))
                .thenReturn(Observable.error(new Throwable("An error has occurred!")));

        mPresenter.fetch();

        InOrder inOrder = Mockito.inOrder(mView);
        inOrder.verify(mView).setLoadingIndicator(true);
        inOrder.verify(mView).showError();
    }
}

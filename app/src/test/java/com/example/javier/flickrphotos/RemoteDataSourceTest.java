package com.example.javier.flickrphotos;

import com.example.javier.flickrphotos.model.RemoteDataSource;
import com.example.javier.flickrphotos.model.photo.Example;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.observers.TestSubscriber;

import static com.example.javier.flickrphotos.utils.Constants.API_KEY;
import static com.example.javier.flickrphotos.utils.Constants.FORMAT;
import static com.example.javier.flickrphotos.utils.Constants.METHOD;
import static com.example.javier.flickrphotos.utils.Constants.NO_JSON_CALL_BACK;
import static com.example.javier.flickrphotos.utils.Constants.PER_PAGE;
import static com.example.javier.flickrphotos.utils.Constants.TAGS;
import static com.example.javier.flickrphotos.utils.Constants.URL_BASE;

/**
 * Created by Javier on 20/12/2017.
 */

public class RemoteDataSourceTest {

    Example mResult;
    MockWebServer mMockWebServer;
    TestSubscriber<Example> mSubscriber;

    @Before
    public void setUp() {


        mResult = new Example("accurate");

        mMockWebServer = new MockWebServer();
        mSubscriber = new TestSubscriber<>();
    }

    @Test
    public void serverCallWithError() {
        //Given
        String url = "dfdf/";
        mMockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(mResult)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mMockWebServer.url(url))
                .build();
        RemoteDataSource remoteDataSource = new RemoteDataSource(retrofit);

        //When
        remoteDataSource.getPhotoRx(METHOD, API_KEY, TAGS, PER_PAGE, FORMAT, NO_JSON_CALL_BACK).subscribe(mSubscriber);

        //Then
        mSubscriber.assertNoErrors();
        mSubscriber.assertCompleted();
    }

    @Test
    public void severCallWithSuccessful() {
        //Given
        mMockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(mResult)));
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mMockWebServer.url(URL_BASE))
                .build();
        RemoteDataSource remoteDataSource = new RemoteDataSource(retrofit);

        //When
        remoteDataSource.getPhotoRx(METHOD, API_KEY, TAGS, PER_PAGE, FORMAT, NO_JSON_CALL_BACK).subscribe(mSubscriber);

        //Then
        mSubscriber.assertNoErrors();
        mSubscriber.assertCompleted();
    }

}

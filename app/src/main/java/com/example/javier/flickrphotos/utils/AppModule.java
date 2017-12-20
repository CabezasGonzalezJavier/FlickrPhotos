package com.example.javier.flickrphotos.utils;

import com.example.javier.flickrphotos.PhotosApplication;
import com.example.javier.flickrphotos.model.RemoteDataSource;
import com.example.javier.flickrphotos.utils.scheduler.BaseSchedulerProvider;
import com.example.javier.flickrphotos.utils.scheduler.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.javier.flickrphotos.utils.Constants.URL_BASE;

/**
 * Created by Javier on 20/12/2017.
 */
@Module
public class AppModule {

    PhotosApplication mPhotosApplication;

    public AppModule(PhotosApplication photosApplication) {
        mPhotosApplication = photosApplication;
    }

    @Singleton
    @Provides
    RemoteDataSource provideRemoteDataSource() {
        return new RemoteDataSource(new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build());
    }

    @Singleton
    @Provides
    BaseSchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }
}

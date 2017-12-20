package com.example.javier.flickrphotos.model;

import com.example.javier.flickrphotos.model.photo.Example;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by Javier on 20/12/2017.
 */

public class RemoteDataSource implements Service {

    private Service api;

    public RemoteDataSource(Retrofit retrofit) {


        this.api = retrofit.create(Service.class);
    }

    @Override
    public Observable<Example> getPhotoRx(String format) {
        return api.getPhotoRx(format);
    }

}

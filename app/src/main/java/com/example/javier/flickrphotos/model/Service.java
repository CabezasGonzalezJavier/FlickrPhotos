package com.example.javier.flickrphotos.model;

import com.example.javier.flickrphotos.model.photo.Example;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Javier on 20/12/2017.
 */

public interface Service {
    @GET("photos_public.gne")
    Observable<Example> getPhotoRx(@Query("format") String format);
}

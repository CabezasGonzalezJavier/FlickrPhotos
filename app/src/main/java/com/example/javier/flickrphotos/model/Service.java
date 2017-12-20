package com.example.javier.flickrphotos.model;

import com.example.javier.flickrphotos.model.photo.Example;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Javier on 20/12/2017.
 */

public interface Service {
    @GET("rest/")
    Observable<Example> getPhotoRx(@Query("method") String method, @Query("api_key") String apiKey, @Query("tags") String tags, @Query("per_page") String perPage, @Query("format") String format, @Query("nojsoncallback")String nojsoncallback);
}

package com.example.javier.flickrphotos.utils;

import com.example.javier.flickrphotos.photo.PhotoActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Javier on 20/12/2017.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(PhotoActivity mainActivity);

}

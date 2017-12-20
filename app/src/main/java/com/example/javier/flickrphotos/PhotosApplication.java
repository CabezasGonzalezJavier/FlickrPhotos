package com.example.javier.flickrphotos;

import android.app.Application;

import com.example.javier.flickrphotos.utils.AppComponent;
import com.example.javier.flickrphotos.utils.AppModule;
import com.example.javier.flickrphotos.utils.DaggerAppComponent;

/**
 * Created by Javier on 20/12/2017.
 */

public class PhotosApplication extends Application {
    AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}

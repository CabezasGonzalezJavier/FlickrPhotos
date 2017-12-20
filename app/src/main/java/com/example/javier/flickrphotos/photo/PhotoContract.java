package com.example.javier.flickrphotos.photo;

import com.example.javier.flickrphotos.model.photo.Item;
import com.example.javier.flickrphotos.utils.BasePresenter;
import com.example.javier.flickrphotos.utils.BaseView;

import java.util.List;

/**
 * Created by Javier on 20/12/2017.
 */

public interface PhotoContract {

    interface Presenter extends BasePresenter {
        void fetch();
    }

    interface View extends BaseView<Presenter> {

        void showPhotos(List<Item> item);

        void showError();

        void setLoadingIndicator(boolean active);

    }
}

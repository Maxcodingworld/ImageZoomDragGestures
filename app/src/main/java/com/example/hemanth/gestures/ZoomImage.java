package com.example.hemanth.gestures;

/**
 * Created by hemanth on 9/28/15.
 */
public class ZoomImage {

    String image;
    float zoomScale = 0 ;
    float translationScale = 0 ;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getZoomScale() {
        return zoomScale;
    }

    public void setZoomScale(float zoomScale) {
        this.zoomScale = zoomScale;
    }

    public float getTranslationScale() {
        return translationScale;
    }

    public void setTranslationScale(float translationScale) {
        this.translationScale = translationScale;
    }
}

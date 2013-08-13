package no.rokaas;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Browser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public Browser(String homePage) {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        webEngine.load(homePage);
        //add the web view to the scene
        getChildren().add(browser);
    }

    public String getLocation() {
        return webEngine.getLocation();
    }

    public void load(String location) {
        webEngine.load(location);
    }

    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    @Override protected double computePrefWidth(double height) {
        return 1100;
    }

    @Override protected double computePrefHeight(double width) {
        return 800;
    }
}

package org.HuellaCarbono.view;

public enum Scenes {
    Primary("view/primary.fxml"),
    Secondary("view/secondary.fxml");

    private String url;
    Scenes(String url) {
        this.url = url;
    }
    public String getURL() {
        return url;
    }
}

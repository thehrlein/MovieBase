package tobiapplications.com.moviebase.utils;

public class Store {

    private static Store instance;
    private int screenWidth;

    private Store() {

    }

    public static Store getInstance() {
        if (instance == null) {
            instance = new Store();
        }
        return instance;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }
}

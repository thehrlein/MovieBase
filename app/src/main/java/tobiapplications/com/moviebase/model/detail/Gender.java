package tobiapplications.com.moviebase.model.detail;

/**
 * Created by Tobias on 17.06.2017.
 */

public class Gender {

    private int genderId;
    private Boolean isMale;
    private String name;

    public Gender(int genderId) {
        this.genderId = genderId;
        if (genderId == 1) {
            createMale();
        } else if (genderId == 2) {
            createFemale();
        } else {
            createUnknown();
        }
    }

    private void createUnknown() {
        isMale = null;
        name = "unknown";
    }

    private void createFemale() {
        isMale = false;
        name = "female";
    }

    private void createMale() {
        isMale = true;
        name = "male";
    }

    public int getGenderId() {
        return genderId;
    }

    public boolean isMale() {
        return isMale;
    }

    public String getName() {
        return name;
    }
}

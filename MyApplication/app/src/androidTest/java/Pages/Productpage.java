package Pages;

import androidx.test.uiautomator.UiCollection;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import static Component.Constants.PRODUCT_TITLE_RESOURCE_ID;
import static Component.Constants.MEDIUM_TIMEOUT_VALUE;

public class Productpage {

    public static String getTitleFromProductDescription() throws UiObjectNotFoundException {
        UiObject object = new UiCollection(new UiSelector().resourceId(PRODUCT_TITLE_RESOURCE_ID));
        object.waitForExists(MEDIUM_TIMEOUT_VALUE);
        if (object.exists()) {
            return object.getText();
        }
        return null;
    }
}

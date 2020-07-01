package Pages;

import androidx.test.uiautomator.*;
import Component.Constants;

import java.util.ArrayList;
import java.util.List;

import static Component.Constants.*;
import static Component.Constants.MEDIUM_TIMEOUT_VALUE;

public class Homepage {
    public static void clickSearchBoxAndType(UiDevice uiDevice, String text, String resourceId) throws UiObjectNotFoundException {
        UiObject object = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        if (object.waitForExists(MEDIUM_TIMEOUT_VALUE)) {
            object.click();
            object.setText(text);
        }
    }

    public static void selectItemByIndexFromSuggestionList(UiDevice uiDevice, int index) throws UiObjectNotFoundException {
        UiObject object = new UiCollection(new UiSelector().resourceId(SUGGEST_LIST_RESOURCE_ID).childSelector(new UiSelector().className(VIEW_GROUP).index(index)));
        if (object.waitForExists(MEDIUM_TIMEOUT_VALUE)) {
            object.click();
            object.waitUntilGone(MEDIUM_TIMEOUT_VALUE);
        }
    }

    public static List<String> getSuggestionListItems(UiDevice uiDevice, String text) throws UiObjectNotFoundException {
        List<String> itemsList = new ArrayList<>();
        UiCollection uiCollection = new UiCollection(new UiSelector().resourceId(Constants.SUGGEST_LIST_RESOURCE_ID));
        for (int i = 0; i < uiCollection.getChildCount(); i++) {
            itemsList.add(uiDevice.findObject(new UiSelector().className(Constants.VIEW_GROUP).childSelector(new UiSelector().className(Constants.TEXT_VIEW_CLASS)).index(i)).getText());
        }
        return itemsList;
    }

    public static void selectItemByVisibleTextFromSuggestionList(String text) throws UiObjectNotFoundException {
        UiObject object = new UiCollection(new UiSelector().resourceId(SUGGEST_TEXT_RESOURCE_ID).text(text));
        if (object.isEnabled()) {
            object.clickAndWaitForNewWindow(MEDIUM_TIMEOUT_VALUE);
        }
    }


}

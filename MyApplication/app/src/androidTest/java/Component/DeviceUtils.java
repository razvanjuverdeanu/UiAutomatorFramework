package Component;

import android.os.RemoteException;
import android.view.accessibility.AccessibilityWindowInfo;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.*;

import static Component.Constants.*;

public class DeviceUtils {

    public static void clickHome(UiDevice uiDevice) {
        uiDevice.pressHome();
    }

    public static void wakeUpDevice(UiDevice uiDevice) throws RemoteException {
        uiDevice.wakeUp();
    }

    public static boolean isScreenOn(UiDevice uiDevice) throws RemoteException {
        return uiDevice.isScreenOn();
    }

    public static boolean pressBack(UiDevice uiDevice) {
        return uiDevice.pressBack();
    }

    public static void clearAllApps(UiDevice uiDevice) throws UiObjectNotFoundException, RemoteException {
        uiDevice.pressRecentApps();
        UiObject noItems = uiDevice.findObject(new UiSelector().className(TEXT_VIEW_CLASS).text(NO_RECENT_ITEMS));
        if (noItems.waitForExists(VERY_SHORT_TIMEOUT_VALUE)) {
            DeviceUtils.clickHome(uiDevice);
        } else {
            UiObject clearAllBtn = uiDevice.findObject(new UiSelector().text(CLEAR_ALL));
            if (clearAllBtn.exists()) {
                clearAllBtn.click();
            } else {
                UiCollection collection = new UiCollection(new UiSelector().className(SCROLL_VIEW_CLASS));
                int size = collection.getChildCount();
                for (int i = size-1 ; i >= 0 ; i--) {
                    UiObject obj = uiDevice.findObject(new UiSelector().resourceId(TASK_VIEW_BAR).childSelector(new UiSelector().resourceId(DISMISS_TASK)));
                    if(obj.waitForExists(SHORT_TIMEOUT_VALUE)){
                        obj.click();
                    }
                }
                DeviceUtils.clickHome(uiDevice);
            }
        }
    }

    public static void scrollUp(UiDevice uiDevice, int startX, int startY, int endX, int endY, int steps ){
        int startss =  uiDevice.getDisplayWidth()/3;
        uiDevice.drag(startX,startY,endX,endY,steps);
    }

    public static boolean isKeyboardOpened() {
        for (AccessibilityWindowInfo window : InstrumentationRegistry.getInstrumentation().getUiAutomation().getWindows()) {
            if (window.getType() == AccessibilityWindowInfo.TYPE_INPUT_METHOD) {
                return true;
            }
        }
        return false;
    }
}

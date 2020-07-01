package Pages;

import Component.DeviceUtils;
import androidx.test.uiautomator.*;
import java.util.*;
import static Component.Constants.*;

public class SearchPage {

    public static String getProductTitleByIndexFromProductsList(int index) throws UiObjectNotFoundException {
        UiCollection uiCollection = new UiCollection(new UiSelector().resourceId(RECYCLER_VIEW_RESOURCE_ID));
        uiCollection.waitForExists(MEDIUM_TIMEOUT_VALUE);
        int listSize = uiCollection.getChildCount();
        if (index < listSize) {
            UiObject uiObject = uiCollection.getChild(new UiSelector().resourceId(PRODUCT_TILE_RESOURCE_ID).index(index).childSelector(new UiSelector().className(LINEAR_LAYOUT_CLASS)));
            if (uiObject.waitForExists(MEDIUM_TIMEOUT_VALUE)) {
                return uiObject.getChild(new UiSelector().resourceId(PRODUCT_RESOURCE_ID).childSelector(new UiSelector().resourceId(ITEM_TITLE_RESOURCE_ID))).getText();
            } else {
                throw new UiObjectNotFoundException("Object  not found");
            }
        } else {
            throw new IndexOutOfBoundsException("Index is greater than list");
        }
    }

    public static void selectProductByIndex(int index) throws UiObjectNotFoundException {
        UiCollection uiCollection = new UiCollection(new UiSelector().resourceId(RECYCLER_VIEW_RESOURCE_ID));
        if (index < uiCollection.getChildCount()) {
            UiObject object = uiCollection.getChild(new UiSelector().resourceId(PRODUCT_TILE_RESOURCE_ID).index(index));
            if (object.isEnabled()) {
                object.click();
            }
        } else {
            throw new UiObjectNotFoundException("Object not found");
        }
    }

    public static void clickFilterButton(UiDevice uiDevice) throws UiObjectNotFoundException {
        waitSearchContainerToBePopulated();
        UiObject object = uiDevice.findObject(new UiSelector().resourceId(FILTER_RESOURCE_ID));
        if (object.waitForExists(MEDIUM_TIMEOUT_VALUE)) {
            object.clickAndWaitForNewWindow(MEDIUM_TIMEOUT_VALUE);
        } else {
            throw new UiObjectNotFoundException("Object not found");
        }
    }

    public static void clickApplyFilterButton(UiDevice uiDevice) throws UiObjectNotFoundException {
        UiObject object = uiDevice.findObject(new UiSelector().resourceId(APPLY_BUTTON_RESOURCE_ID));
        if (object.waitForExists(SHORT_TIMEOUT_VALUE)) {
            object.clickAndWaitForNewWindow(MEDIUM_TIMEOUT_VALUE);
        } else {
            throw new UiObjectNotFoundException("Object not found");
        }
    }

    public static void setPriceLimit(UiDevice uiDevice, String resourceId, Integer priceValue) throws UiObjectNotFoundException {
        UiObject object = uiDevice.findObject(new UiSelector().resourceId(resourceId));
        if (object.waitForExists(SHORT_TIMEOUT_VALUE)) {
            object.clickAndWaitForNewWindow(SHORT_TIMEOUT_VALUE);
            object.setText(priceValue.toString());
        } else {
            throw new UiObjectNotFoundException("Object not found");
        }
    }

    public static void selectSortFilter(UiDevice uiDevice, String sortFilter) throws UiObjectNotFoundException {
        UiObject object = uiDevice.findObject(new UiSelector().resourceId(ID_SORT_TITLE_RESOURCE_ID).text(sortFilter));
        if (object.waitForExists(SHORT_TIMEOUT_VALUE)) {
            object.click();
        } else {
            throw new UiObjectNotFoundException("Object not found");
        }
    }

    public static List<String> getVisibleProductsPrice(UiDevice uiDevice) throws UiObjectNotFoundException {
        HashMap<String, String> productDetails = new HashMap();
        List<String> priceList = new ArrayList<>();
        UiCollection uiCollection = new UiCollection(new UiSelector().resourceId(RECYCLER_VIEW_RESOURCE_ID));
        uiCollection.waitForExists(MEDIUM_TIMEOUT_VALUE);
        int collectionSize = uiCollection.getChildCount();
        for (int i = 0; i < collectionSize; i++) {
            if (isProductDetailObjectVisible(uiDevice, i, PRICE) && isProductDetailObjectVisible(uiDevice, i, TITLE)) {
                String title = getProductDetailByIndex(uiDevice, i, TITLE);
                String price = getProductDetailByIndex(uiDevice, i, PRICE);
                if (!productDetails.containsKey(title)) {
                    productDetails.put(title, price);
                }
            }
        }
        for (Map.Entry<String, String> entry : productDetails.entrySet()) {
            priceList.add(entry.getValue());
        }
        return priceList;
    }

    public static List<String > getProductsPriceAfterScrollingNumberOfTimes(UiDevice uiDevice, Integer numberOfScrolls) throws UiObjectNotFoundException {
        List<String> productsPrice = getVisibleProductsPrice(uiDevice);
        DeviceUtils.scrollUp(uiDevice, 540, 1340, 540, 210, 100);
        productsPrice.addAll(getVisibleProductsPrice(uiDevice));
        for (int i = 0; i < numberOfScrolls; i++) {
            DeviceUtils.scrollUp(uiDevice, 540, 1100, 540, 210, 100);
            productsPrice.addAll(getVisibleProductsPrice(uiDevice));
        }
        return productsPrice;
    }

    public static UiObject getObjectOfProductDetailSelectedByIndex(UiDevice uiDevice, Integer index, String option) {
        switch (option) {
            case PRICE:
                return uiDevice.findObject(new UiSelector().resourceId(PRODUCT_TILE_RESOURCE_ID).index(index).
                        childSelector(new UiSelector().className(LINEAR_LAYOUT_CLASS).index(0).
                                childSelector(new UiSelector().resourceId(PRODUCT_RESOURCE_ID).
                                        childSelector(new UiSelector().resourceId(ITEM_PRICE_RESOURCE_ID)))));
            case TITLE:
                return uiDevice.findObject(new UiSelector().resourceId(PRODUCT_TILE_RESOURCE_ID).index(index).
                        childSelector(new UiSelector().className(LINEAR_LAYOUT_CLASS).index(0).
                                childSelector(new UiSelector().resourceId(PRODUCT_RESOURCE_ID).
                                        childSelector(new UiSelector().resourceId(ITEM_TITLE_RESOURCE_ID)))));
            default:
                return null;
        }
    }

    public static String getProductDetailByIndex(UiDevice uiDevice, Integer index, String option) throws UiObjectNotFoundException {
        switch (option) {
            case PRICE:
                return Objects.requireNonNull(getObjectOfProductDetailSelectedByIndex(uiDevice, index, PRICE)).getText();
            case TITLE:
                return Objects.requireNonNull(getObjectOfProductDetailSelectedByIndex(uiDevice, index, TITLE)).getText();
            default:
                return null;
        }
    }

    public static boolean isProductDetailObjectVisible(UiDevice uiDevice, Integer index, String option) {
        UiObject object = getObjectOfProductDetailSelectedByIndex(uiDevice, index, option);
        if (object.exists()) {
            return true;
        }
        return false;
    }

    public static void waitSearchContainerToBePopulated(){
        UiCollection uiCollection = new UiCollection(new UiSelector().resourceId(RECYCLER_VIEW_RESOURCE_ID));
        uiCollection.waitForExists(LONG_TIMEOUT_VALUE);
    }
}

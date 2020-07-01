package com.example.Tests;


import Component.DeviceUtils;
import Component.Utils;
import Pages.*;
import android.os.RemoteException;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.Test;


import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static Component.Constants.*;
import static Component.Utils.generateRandomNumber;
import static junit.framework.TestCase.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class Tests {

    private UiDevice uiDevice;
    private String SEARCHED_TEXT = "xbox";
    private String RAKUTEN_APP = "Rakuten Global Market";
    private Integer MIN_PRICE_VALUE = 5;
    private Integer MAX_PRICE_VALUE = 10;
    private List<String> searchList = Arrays.asList("xbox", "iphone", "camera");

    @Before
    public void setUp() throws RemoteException, UiObjectNotFoundException {
        this.uiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        if (!DeviceUtils.isScreenOn(uiDevice)) {
            DeviceUtils.wakeUpDevice(uiDevice);
        }
        DeviceUtils.clickHome(uiDevice);
        DeviceUtils.clearAllApps(uiDevice);
        UiObject2 app = uiDevice.findObject(By.text(RAKUTEN_APP));
        app.clickAndWait(Until.newWindow(), LONG_TIMEOUT_VALUE);
    }

    @Ignore
    @org.junit.Test
    public void checkProductTitleTest() throws UiObjectNotFoundException {

        Homepage.clickSearchBoxAndType(uiDevice, SEARCHED_TEXT, HOMEPAGE_SEARCH_TEXT);
        // String item = Utils.getSuggestionListItems(uiDevice, SEARCHED_TEXT).get(2);
        // Utils.selectItemByVisibleTextFromSuggestionList("glasses");
        Homepage.selectItemByIndexFromSuggestionList(uiDevice, 0);
        String itemTitleFromList = SearchPage.getProductTitleByIndexFromProductsList(1);
        SearchPage.selectProductByIndex(1);
        assertTrue(itemTitleFromList.equalsIgnoreCase(Productpage.getTitleFromProductDescription()));
    }

    @Test
    public void checkMinimPriceFilterTest() throws UiObjectNotFoundException {

        Homepage.clickSearchBoxAndType(uiDevice, searchList.get(generateRandomNumber(searchList.size())), HOMEPAGE_SEARCH_TEXT);
        Homepage.selectItemByIndexFromSuggestionList(uiDevice, 0);

        SearchPage.clickFilterButton(uiDevice);
        SearchPage.setPriceLimit(uiDevice, MIN_PRICE_RESOURCE_ID, MIN_PRICE_VALUE);
        SearchPage.selectSortFilter(uiDevice, LOWEST_PRICE);
        SearchPage.clickApplyFilterButton(uiDevice);

        List<String> stringList = SearchPage.getVisibleProductsPrice(uiDevice);
        List<Double> priceListAsInteger = Utils.getPriceListAsNumber(stringList);
        Collections.sort(priceListAsInteger);

        assertTrue(priceListAsInteger.get(0) > MIN_PRICE_VALUE);
    }

    @Test
    public void checkMaximPriceFilterTest() throws UiObjectNotFoundException {

        Homepage.clickSearchBoxAndType(uiDevice, SEARCHED_TEXT, HOMEPAGE_SEARCH_TEXT);
        Homepage.selectItemByIndexFromSuggestionList(uiDevice, 0);

        SearchPage.clickFilterButton(uiDevice);
        SearchPage.setPriceLimit(uiDevice, MAX_PRICE_RESOURCE_ID, MAX_PRICE_VALUE);
        SearchPage.selectSortFilter(uiDevice, HIGHEST_PRICE);
        SearchPage.clickApplyFilterButton(uiDevice);

        List<String> stringList = SearchPage.getProductsPriceAfterScrollingNumberOfTimes(uiDevice, 5);
        List<Double> priceListAsInteger = Utils.getPriceListAsNumber(stringList);

        Collections.sort(priceListAsInteger);
        System.out.println("ordered list --------->" + priceListAsInteger);
        assertTrue(priceListAsInteger.get(priceListAsInteger.size() - 1) < MAX_PRICE_VALUE);
    }
}

package Component;

import androidx.test.uiautomator.UiDevice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    public static List<Double> getPriceListAsNumber(List<String> list) {
        List<Double> priceList = new ArrayList<>();
        for (String value : list) {
            int e = value.indexOf("€");
            int p = value.indexOf("(");
            double number = Double.parseDouble(value.substring(e + 1, p - 1).replace(",", ".").trim());
            priceList.add(number);
        }
        return priceList;
    }

    public static int generateRandomNumber(int maxNumber) {
        Random random = new Random();
        return random.nextInt(maxNumber ) ;
    }

}

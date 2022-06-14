package org.filmland.catalog.utils;

/**
 * {@link CurrencyConverterUtils} used for currency related handling
 *  We are saving the currencies in EUROCENT format in database like 1 EUR becomes 100CENTS
 *  Different currencies can have various possible minor units to handle this created CurrencyConverterUtils.
 *  In future can be extended to exchange currencies from USD -> EUR or local currency of user willing to
 *  subscribe based on today's exchange rate
 */
public class CurrencyConverterUtils {
    enum CURRENCIES {

        USD("USD",100),
        EUR("EUR",100);
        String code;
        int basePoints;

        CURRENCIES(String code, int basePoints) {
            this.code = code;
            this.basePoints = basePoints;
        }


    }


    /**
     * Converts EUROCENT like format to EURO.CENT format
     * @param fromCurrency input currency code
     * @param price input price to convert
     * @return converted Euro.Cent format price
     */
    public static double convertPrice(String fromCurrency, long price) {
        switch (fromCurrency) {
            case "USD":
                return (double) price /CURRENCIES.USD.basePoints;
            default:
                return (double) price/CURRENCIES.EUR.basePoints;
        }
    }
}

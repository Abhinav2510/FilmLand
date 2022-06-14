package org.filmland.catalog.utils;

import org.junit.Assert;
import org.junit.Test;

public class CurrencyConverterUtilssUnitTests {
    private static final double DELTA = 1e-15;

    @Test
    public void currencyConversionTest(){
        Assert.assertEquals(CurrencyConverterUtils.convertPrice(CurrencyConverterUtils.CURRENCIES.USD.code,500),5,DELTA);
    }
}

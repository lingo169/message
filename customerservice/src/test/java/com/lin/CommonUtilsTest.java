package com.lin;

import com.lin.common.utils.CommonUtils;
import org.junit.Assert;
import org.junit.Test;

public class CommonUtilsTest {

    @Test
    public void isPhoneTest() {
        String mobile = "13434343968";
        boolean b = CommonUtils.isPhone(mobile);
        Assert.assertEquals(b,true);
    }
}

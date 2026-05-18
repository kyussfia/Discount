package com.kyussfia.discounts;

import com.kyussfia.prototype.ProductCategory;

/**
 * A voucher for a maximum 5 pounds off the overall spend in the sandwich category
 */
public class Maximum5PoundOffOnSandWitch extends SetAmountVoucherPerCategory {

    public Maximum5PoundOffOnSandWitch() {
        super(ProductCategory.SANDWICH, 5);
    }
}

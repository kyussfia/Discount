package com.kyussfia.discounts;

import com.kyussfia.prototype.Cart;
import com.kyussfia.prototype.Discount;
import com.kyussfia.prototype.ProductCategory;

/**
 * A voucher for a maximum N pounds off the overall spend in the C category
 */
public class SetAmountVoucherPerCategory implements Discount {

    private final ProductCategory category;
    private double amount;

    public SetAmountVoucherPerCategory(ProductCategory category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    @Override
    public void accept(Cart cart) {
        cart
            .content()
            .stream()
            .filter(p -> p.getCategory().equals(this.category))
            .forEach(sw -> {
                if (!this.hasVoucher()) {
                    return;
                }
                final double spent = Math.min(sw.getPrice(), this.amount);
                sw.setPrice(sw.getPrice() - spent);
                this.amount -= spent;
            });
    }

    private boolean hasVoucher() {
        return this.amount > 0;
    }
}

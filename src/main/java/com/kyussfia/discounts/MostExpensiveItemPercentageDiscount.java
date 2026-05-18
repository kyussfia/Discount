package com.kyussfia.discounts;

import com.kyussfia.prototype.Cart;
import com.kyussfia.prototype.Discount;
import com.kyussfia.prototype.Product;

import java.util.Comparator;

/**
 * P% off for the most expensive cart item
 */
public class MostExpensiveItemPercentageDiscount implements Discount {

    private final int percentage;

    public MostExpensiveItemPercentageDiscount(int percentage) {
        this.percentage = percentage;
    }

    private double getDiscountMultiplier() {
        return 1 - (double) this.percentage / 100;
    }

    @Override
    public void accept(Cart cart) {
        cart
            .content()
            .stream()
            .max(Comparator.comparing(Product::getPrice))
            .ifPresent(product -> product.setPrice(product.getPrice() * this.getDiscountMultiplier()));
    }
}

package com.kyussfia.prototype;

import java.util.List;
import java.util.function.Consumer;

public record Cart(List<Product> content) implements Consumer<Discount> {

    public void add(Product product) {
        this.content.add(product);
    }

    public void accept(List<Discount> discounts) {
        discounts.forEach(this);
    }

    @Override
    public void accept(Discount discount) {
        discount.accept(this);
    }

    @Override
    public String toString() {
        return "Cart" + this.content;
    }
}

package com.kyussfia;

import com.kyussfia.discounts.Maximum5PoundOffOnSandWitch;
import com.kyussfia.discounts.MostExpensiveItemPercentageDiscount;
import com.kyussfia.discounts.SetAmountVoucherPerCategory;
import com.kyussfia.prototype.Cart;
import com.kyussfia.prototype.Discount;
import com.kyussfia.prototype.Product;
import com.kyussfia.prototype.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DiscountTest {

    @Test
    void testInitialDiscount() {
        Cart cart = new Cart(new ArrayList<>());
        cart.add(new Product("Ham Sandwich", 3.0, 1, 3.0, ProductCategory.SANDWICH));
        cart.add(new Product("Cheese Sandwich", 3.0, 2, 1.5, ProductCategory.SANDWICH));
        cart.add(new Product("Orange Juice", 1.85, 1, 1.85, ProductCategory.DRINK));
        cart.add(new Product("Apple Juice", 5.0, 2, 2.5, ProductCategory.DRINK));

        Discount d1 = _ -> {};

        cart.accept(List.of(d1));

        Assertions.assertEquals(3.0, cart.content().get(0).getPrice());
        Assertions.assertEquals(3.0, cart.content().get(1).getPrice());
        Assertions.assertEquals(1.85, cart.content().get(2).getPrice());
        Assertions.assertEquals(5.0, cart.content().get(3).getPrice());
    }

    @Test
    void testPercentageDiscount() {
        Cart cart = new Cart(new ArrayList<>());
        cart.add(new Product("Ham Sandwich", 3.0, 1, 3.0, ProductCategory.SANDWICH));
        cart.add(new Product("Cheese Sandwich", 3.0, 2, 1.5, ProductCategory.SANDWICH));
        cart.add(new Product("Orange Juice", 1.85, 1, 1.85, ProductCategory.DRINK));
        cart.add(new Product("Apple Juice", 5.0, 2, 2.5, ProductCategory.DRINK));

        cart.accept(new MostExpensiveItemPercentageDiscount(30));

        Assertions.assertEquals(3.0, cart.content().get(0).getPrice());
        Assertions.assertEquals(3.0, cart.content().get(1).getPrice());
        Assertions.assertEquals(1.85, cart.content().get(2).getPrice());
        Assertions.assertEquals(3.5, cart.content().get(3).getPrice());
    }

    @Test
    void testAmountVoucherDiscount() {
        Cart cart = new Cart(new ArrayList<>());
        cart.add(new Product("Ham Sandwich", 3.0, 1, 3.0, ProductCategory.SANDWICH));
        cart.add(new Product("Cheese Sandwich", 3.0, 2, 1.5, ProductCategory.SANDWICH));
        cart.add(new Product("Orange Juice", 1.85, 1, 1.85, ProductCategory.DRINK));
        cart.add(new Product("Apple Juice", 5.0, 2, 2.5, ProductCategory.DRINK));

        cart.accept(new SetAmountVoucherPerCategory(ProductCategory.DRINK, 2.0));

        Assertions.assertEquals(3.0, cart.content().get(0).getPrice());
        Assertions.assertEquals(3.0, cart.content().get(1).getPrice());
        Assertions.assertEquals(0.0, cart.content().get(2).getPrice());
        Assertions.assertEquals(4.85, cart.content().get(3).getPrice());
    }

    @Test
    void testMaximum5PoundOffOnSandWitchDiscount() {
        Cart cart = new Cart(new ArrayList<>());
        cart.add(new Product("Ham Sandwich", 3.0, 1, 3.0, ProductCategory.SANDWICH));
        cart.add(new Product("Cheese Sandwich", 3.0, 2, 1.5, ProductCategory.SANDWICH));
        cart.add(new Product("Orange Juice", 1.85, 1, 1.85, ProductCategory.DRINK));
        cart.add(new Product("Apple Juice", 5.0, 2, 2.5, ProductCategory.DRINK));

        cart.accept(new Maximum5PoundOffOnSandWitch());

        Assertions.assertEquals(0.0, cart.content().get(0).getPrice());
        Assertions.assertEquals(1.0, cart.content().get(1).getPrice());
        Assertions.assertEquals(1.85, cart.content().get(2).getPrice());
        Assertions.assertEquals(5.0, cart.content().get(3).getPrice());
    }

    @Test
    void testDiscounts() {
        Cart cart = new Cart(new ArrayList<>());
        cart.add(new Product("Ham Sandwich", 3.0, 1, 3.0, ProductCategory.SANDWICH));
        cart.add(new Product("Cheese Sandwich", 3.0, 2, 1.5, ProductCategory.SANDWICH));
        cart.add(new Product("Orange Juice", 1.85, 1, 1.85, ProductCategory.DRINK));
        cart.add(new Product("Apple Juice", 5.0, 2, 2.5, ProductCategory.DRINK));

        Discount d1 = new MostExpensiveItemPercentageDiscount(30);
        Discount d2 = new Maximum5PoundOffOnSandWitch();

        cart.accept(List.of(d1, d2));

        Assertions.assertEquals(0.0, cart.content().get(0).getPrice());
        Assertions.assertEquals(1.0, cart.content().get(1).getPrice());
        Assertions.assertEquals(1.85, cart.content().get(2).getPrice());
        Assertions.assertEquals(3.5, cart.content().get(3).getPrice());
    }
}

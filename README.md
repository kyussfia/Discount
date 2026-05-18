# Discount
## Introduction
This repository contains a solution for a live coding assignment given during a **TESCO** job interview in 2026.  
The task was to design and implement a flexible discount engine that can apply different types of promotions to a shopping cart.
## Description
At Tesco over the years we have developed a rich selection of discounts to benefit our customers.
As the discount system got very complex, we would like to re-write it from scratch (what could go wrong?).
Your goal today is to implement an MVP of the new system – apply the relevant discounts to customer's cart.
You will be given a cart with products and their prices, as well as a list of discounts that might apply to the cart.
Please calculate the discounted prices for items in the cart.
Keep in mind that other kinds of discounts will be added to the system in the future.

Example, given a cart like this:
```yaml
- product:
  name: Ham Sandwich
  category: sandwich
  unit price: 3 pounds
  count: 1
  price: 3 pounds

- product:
  name: Cheese Sandwich
  category: sandwich
  unit price: 1.5 pounds
  count: 2
  price: 3 pounds

- product:
  name: Orange Juice
  category: drink
  unit price: 1.85 pound
  count: 1
  price: 1.85 pound

- product:
  name: Apple Juice
  category: drink
  unit price: 2.5 pounds
  count: 2
  price: 5.0 pounds
```
And those discounts:
- MostExpensiveItemPercentageDiscount: 30% off for the most expensive cart item
- SetAmountVoucherPerCategory: voucher for a maximum 5 pounds off the overall spend in the sandwich category

Please return a discounted shopping cart:
```yaml
- product:
  name: Ham Sandwich
  category: sandwich
  unit price: 3 pounds
  count: 1
  price: 0 pounds

- product:
  name: Cheese Sandwich
  category: sandwich
  unit price: 1.5 pounds
  count: 2
  price: 1 pound

- product:
  name: Orange Juice
  category: drink
  unit price: 1.85 pound
  count: 1
  price: 1.85 pound

- product:
  name: Apple Juice
  category: drink
  unit price: 2.5 pounds
  count: 2
  price: 3.5 pounds
```
---

## 📦 Overview

The engine supports:
- **Percentage discount** on the most expensive item in the cart.
- **Fixed‑amount voucher** per product category (e.g., £2 off drinks).
- **Max‑amount voucher** for a specific category (e.g., max £5 off sandwiches).
- Combining multiple discounts sequentially.

All discounts are implemented as `Consumer<Cart>` (the `Discount` interface) and can be applied in any order.

---

## 🚀 Setup & Running

### Prerequisites
- Java 17 or later
- JUnit 5 (the test suite uses `org.junit.jupiter.api`)

### Build & Test
No external build tool is required – the code compiles with plain `javac`.  
To compile and run the tests manually:

```bash
# Compile all .java files (assuming standard directory layout)
javac -d out $(find . -name "*.java")

# Run the test class (JUnit Platform Console Launcher needed for standalone execution)
# Alternatively, use your IDE or a build tool (Maven/Gradle) with JUnit 5.
```

### Example Usage

```java
Cart cart = new Cart(new ArrayList<>());
cart.add(new Product("Ham Sandwich", 3.0, 1, 3.0, ProductCategory.SANDWICH));
cart.add(new Product("Orange Juice", 1.85, 1, 1.85, ProductCategory.DRINK));

// Apply 30% off the most expensive item
cart.accept(new MostExpensiveItemPercentageDiscount(30));

// Then apply a max £5 off sandwiches
cart.accept(new Maximum5PoundOffOnSandWitch());
```

---

## 🧠 Solution Concept & TDD Walkthrough

The implementation was driven by **Test‑Driven Development (TDD)**.  
Each discount type was introduced by writing a failing test first, then implementing the minimal logic to make it pass, and finally refactoring.

### 1. Defining the Core Contract
The first test (`testInitialDiscount`) used an empty discount (`d1 = _ -> {}`).  
This forced us to define:
- `Cart` – a mutable container holding a list of `Product`s.
- `Discount` – a `@FunctionalInterface` extending `Consumer<Cart>`.
- `Product` – with fields `price`, `category`, etc.

The contract became clear: `Discount.accept(Cart)` modifies the cart **in place**.

### 2. Percentage Discount on Most Expensive Item
Test `testPercentageDiscount` expected the item with the highest price to be reduced by 30%.  
Implementing `MostExpensiveItemPercentageDiscount` required:
- Finding the max product by price using `Comparator`.
- Applying a multiplier (`1 - percentage/100`).
- **Important:** The price is mutated directly – a deliberate choice to keep the design simple and avoid deep copying.

### 3. Fixed‑Amount Voucher per Category
Test `testAmountVoucherDiscount` applied a £2 voucher on `DRINK` products.  
The implementation (`SetAmountVoucherPerCategory`) introduced a **stateful discount** – the remaining voucher amount decreases as it is spent on matching products.  
This led to a generalization: a voucher can cover multiple items in the same category until its value is exhausted.

### 4. Specialized Voucher – Max £5 off Sandwiches
`Maximum5PoundOffOnSandWitch` simply extends `SetAmountVoucherPerCategory` with `SANDWICH` and amount `5`.  
No extra logic – a clean demonstration of **inheritance** and **reusability**.

### 5. Combining Discounts
The final test (`testDiscounts`) applied both a percentage discount and the sandwich voucher.  
Because the cart is mutable and discounts are applied sequentially, the order matters.  
The test shows that the most expensive item (Apple Juice) is first reduced by 30% (from £5 to £3.5), then the sandwich voucher reduces the sandwiches (Ham & Cheese) by up to £5.

---

## 🔧 Interesting Generalisations

- **`Discount` as `Consumer<Cart>`** – Allows using `List<Discount>.forEach(cart::accept)` and makes the engine easily extensible.
- **Stateful discount logic** – `SetAmountVoucherPerCategory` tracks remaining amount, enabling a single voucher to discount multiple products in a category.
- **No external framework** – Pure Java + JUnit, showing understanding of core language features (records, streams, functional interfaces).
- **Separation of concerns** – `Cart` only holds products and applies discounts; `Product` contains mutable price (to simplify discount application).  
  An alternative design would be immutable with a `applyDiscounts()` returning a new cart – but the chosen approach matches the iterative, test‑driven spirit of the assignment.

---

## ✅ What Was Learned

- TDD naturally produces **loosely coupled**, **testable** components.
- Starting with the contract (`Cart` + `Discount`) makes the rest of the implementation straightforward.
- Sequential discount application is simple but forces awareness of **order dependencies** (e.g., percentage discount before voucher might change which item is “most expensive”).
- The solution is ready to be extended with new discount types (e.g., “buy one get one free”, “percentage off total”) without modifying existing code.

---

## 📄 License

This code is provided for portfolio purposes as part of a job interview process.  
Feel free to use it as a reference.
---

*Submitted for TESCO interview – kyussfia - 2026*

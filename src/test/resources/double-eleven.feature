@double_eleven
Feature: E-commerce Double Eleven Bulk-Discount Promotion
  As a shopper
  I want the system to apply a 20 % discount to every group of 10 identical items
  So that I can enjoy Double Eleven savings on bulk purchases

  #
  # 規則說明
  #   • 同一種商品，每買滿 10 件，該 10 件可享 8 折 (20 % off)。
  #   • 不足 10 件者按照原價計算。
  #

  Scenario: 12 identical items — one discounted group, two at full price
    Given the Double Eleven promotion is active
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | 襪子          | 12       | 100       |
    Then the order summary should be:
      | originalAmount | discount | totalAmount |
      | 1200           | 200      | 1000        |
    And the customer should receive:
      | productName | quantity |
      | 襪子          | 12       |

  Scenario: 27 identical items — two discounted groups, seven at full price
    Given the Double Eleven promotion is active
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | 襪子          | 27       | 100       |
    Then the order summary should be:
      | originalAmount | discount | totalAmount |
      | 2700           | 400      | 2300        |
    And the customer should receive:
      | productName | quantity |
      | 襪子          | 27       |

  Scenario: Ten different items — no discount because items are not identical
    Given the Double Eleven promotion is active
    When a customer places an order with:
      | productName | quantity | unitPrice |
      | 商品A        | 1        | 100       |
      | 商品B        | 1        | 100       |
      | 商品C        | 1        | 100       |
      | 商品D        | 1        | 100       |
      | 商品E        | 1        | 100       |
      | 商品F        | 1        | 100       |
      | 商品G        | 1        | 100       |
      | 商品H        | 1        | 100       |
      | 商品I        | 1        | 100       |
      | 商品J        | 1        | 100       |
    Then the order summary should be:
      | originalAmount | discount | totalAmount |
      | 1000           | 0        | 1000        |
    And the customer should receive:
      | productName | quantity |
      | 商品A        | 1        |
      | 商品B        | 1        |
      | 商品C        | 1        |
      | 商品D        | 1        |
      | 商品E        | 1        |
      | 商品F        | 1        |
      | 商品G        | 1        |
      | 商品H        | 1        |
      | 商品I        | 1        |
      | 商品J        | 1        |

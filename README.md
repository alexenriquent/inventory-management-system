# Inventory Management System
UQR sells various types of electronics goods, designed and developed by UQ staffs and students. To ensure its products can reach various parts of Queensland, UQR has multiple retail stores of various sizes, at different locations. Your task is to create a system that uses historical customer behaviour data from a store, to decide what type of items to order/return and how many, so that the store makes the most profit.

Each retail store of UQR can be classified into one of five different classes of store, based on their capacities. The 5 types of stores, along with their stocking capacities and maximum ordering capabilities are:
* Tiny store. This store sells up to 2 types of items and can stock at most 3 items.
It can order at most 2 items per week and can return at most 1 item per week.
* Small store. This store sells up to 2 types of items and can stock up to 8 items. It can order at most 3 items per week and can return at most 2 items per week.
* Medium store. This store sells up to 3 types of items and can stock up to 8 items. It can order at most 3 items per week and can return at most 2 items per week.
* Large store. This store sells up to 5 types of items and can stock up to 10 items. It can order at most 4 items per week and can return at most 2 items per week.
* Mega store. This store sells up to 7 types of items and can stock up to 20 items. It can order at most 5 items per week and can return at most 3 items per week.

Each store can order items at no cost. Whenever an item is sold, the store keeps 75% of the payment and passes the rest to UQR headquarter. However, when a store returns an item, it must pay half of the price of the item to UQR headquarter.

To simplify the problem, UQR assumes:
* Stocking is done while the store is closed (Saturday-Sunday). Each store can only order the stocks once per week, which is on Saturday morning. The stock will be delivered and arrange on the shelves during the weekend, before it opens again for next week’s operation.
* All items ordered will be delivered in good condition, on time. Also, the quality of the goods will not degrade over time.
* When the order/return operation causes the store to have more items than its capacity, UQR will automatically cut the store’s order prior to delivery, so that there will be no excess items in the store. Assuming the types of items are indexed from 1, the items will be cut ascendingly, from items with the lowest index first. This cut will cost the store a penalty fee of $F per item cut.
* The buying habit of the customers are independent between one another. Also, the buying habit of the customers of one store is independent from those of other stores. However, for each type of items in the store, the amount of items the customer buys do depend on the amount that is available in the store right after the most recent replenishment.
* Within the week, the total number of items per type of goods that the customers of a store want to buy is at most equivalent to the total number of items that the store can stock.
* The performance of the store is measured weekly, right after the store is closed for the week.
* The inventory system should assume that the store will remain open forever. Although for simplicity in computing the actual profit gained, the system will be tested on a finite number of weeks.

Given the stochastic model of the customers’ behavior, the type of store, and information about the available items in the store immediately before the shopping order is made, the inventory management system should decide which types of items and how many should be ordered and returned, so that the store gains the maximum profit possible. Profit is defined as the income the store gets (i.e., 75% of the total payment received from customer) minus the sum of the cost of returning the items and the penalty fee when items are being cut from the order list by UQR headquarter.

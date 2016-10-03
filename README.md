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

## Input and Output format
**Input format**. The input is a single .txt file, containing all the information about the store, the types and price of the items that can be sold at the store, and the store’s customers’ behaviour. The format of the input file is as follows.

1. The first line is the store’s type, i.e., tiny, small, medium, large, or mega.
2. The second line is the discount factor.
3. The third line is the number of weeks that the inventory system will be tested.
4. The fourth line is the penalty fee F.
5. We assume that the type of items that can be sold in this store is indexed from 1 to T, where T is the maximum number of types of items that can be sold in this store. The fifth line then consists of T positive integers, separated by a white space. The first integer represents the price of item-type-1, the second represents the price of item-type-2, etc.
6. The sixth line represent the amount of items per type prior to the first replenishment. This line consists of T positive integers, where the first integer represents the amount of item-type-1, the second represents the amount of item- type-2, etc.
7. The subsequent lines represent the customers’ behaviour. This behaviour is represented as T probability matrices. Each matrix represents the custormers’ behaviour in buying a particular type of items. In particular, suppose P<sub>i</sub> is the probability matrix that corresponds to type-i (i ∈ [1, T]). Then, the element e<sub>jk</sub> at row-j (j ∈ [0, M]) and column-k (k ∈ [0, M]) of P<sub>i</sub> represents the conditional probability that in a week, the total requests for type-i is k items, given there are j items of type-i at the beginning of the week (right after the stocks are replenished). The notation M is the maximum number of items that can be stocked in the store. Note that the indexing for the matrices starts from 0 (i.e., to represent no such type of item), which means each matrix is of size (M+1)<sup>2</sup>. The list of probability matrices are written in the input file, starting at line-7: Line-7 to line-(7+M): represent the rows of P<sub>1</sub>. The numbers in each line (i.e., the columns) are separated by a white space. Each probability value has at most 3 decimal digits. Line-(7+(M+1)·(T-1)) to line-(6+(M+1)·T): represent the rows of P<sub>T</sub> in a sequential order.

**Output format.** Your program should output the state, the order list, and the return list at each week. The format is as follows:

1. The first line is the number of weeks the inventory system is being tested. Let’s denote this as N. The output file consists of 3N+2 lines.
2. Line-2 represents the stocks in the store right before the first replenishment. It consists of T positive integers, where each integer is separated by a white space. The i<sup>th</sup> integer represents the number of items of type-i.
3. Line-3 represents the customers’ request list for week-1. It consists of T positive integers, where each integer is separated by a white space. The i<sup>th</sup> integer represents the number of items of type-i being requested.
4. Line-4 represents order list at the end of week-1. It consists of T positive integers, where each integer is separated by a white space. The i<sup>th</sup> integer represents the number of items of type-i being ordered.
5. Line-5 represents return list at the end of week-1. It consists of T positive integers, where each integer is separated by a white space. The i<sup>th</sup> integer represents the number of items of type-i being returned.
6. Line-6 represents the customers’ request list for week-2. It consists of T positive integers, where each integer is separated by a white space. The i<sup>th</sup> integer represents the number of items of type-i being requested.
7. ⋮
8. Line-3N-2 represents order list at the end of week-(N-1). It consists of T positive integers, where each integer is separated by a white space. The i<sup>th</sup> integer represents the number of items of type-i being ordered.
9. Line-3N-1 represents return list at the end of week-(N-1). It consists of T positive integers, where each integer is separated by a white space. The i<sup>th</sup> integer represents the number of items of type-i being returned.
10. Line-3N represents the customers’ request list for week-N. It consists of T positive integers, where each integer is separated by a white space. The i<sup>th</sup> integer represents the number of items of type-i being requested.

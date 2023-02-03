
## Overview

Example app based on Access Northwind data model for retail of an online bookstore.

* Customers, Orders, Products
* Region, Territories, Fulfillment Centers

### Size
Entities: 16
Screens: 36
Classes: 260
Tests: 40

### 1. Login as admin

* Tenant
* Possible Users

* when logging in first time, test data is generated
* open orders (see list of generated orders)
* logout


### 2. Login as jessica

* new customer

Alexa
Porter
alexa.porter@gmail.com
637 Britannia Drive
94591
Vallejo
CA

* open customer > map

* create order
* search for alexa
* create order line 1
* search for 'ar'
* select 2 units

* create order line 2
* search for 'be'

* save order
* selected newly created order

### 3. Login as melissa

#### Mark as Confirmed

* go to orders
* search for 'al'
* select 'Alexa Porter'
* select order -> confirm
* select different fulfillment center
* ok

#### Mark as 'In Delivery'

* select Order under confirmed orders
* select 'Mark as In Delivery'
* click ok
* click Track delivery


#### Perform Fill-Up Request

* open order to remember product
* open products > products
* 

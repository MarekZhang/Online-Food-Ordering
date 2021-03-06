# Online-Food-Ordering-System

## Project Description
Customers can order food, make a payment on their phones through the WeChat App. They can also cancel an order and refund their transaction.

To log in to the admin site, scanning the WeChat QR code is used for authentication. When an order is placed, a popup window and alert audio will notify the owner. The restaurant owner can modify product details (including display pictures, price, stock, status, descriptions, etc.) and product categories. After the food is delivered, the order can be completed by the restaurant owner.

A simple Redis distributed lock is designed using SETNX and GETSET command to handle the potential high concurrency situation.

## Product

Responsive design
<p>
    <img src="demo%20presentation/food%20ordering%20presentation.gif" width="600">
<p> 


Resturant owner admin site
<p>
    <img src="demo%20presentation/vendor%20admin%20site%20presentation.gif" width="600">
<p> 


Product screenshot
<p>
    <img src="demo%20presentation/weChatApp&VendorAdmin.png" width="600">
<p>

## Tech Stack
1. Java 8
2. Spring Boot 1.5.2(Freemarker, Websocket, JPA, Junit)
3. WeChat authorization, payment, open platform API
4. MySQL
5. Redis
6. JQuery
7. Bootstrap
8. CSS3
9. HTML 5

## Tools
- [IntelliJ IDEA 2020.1](https://www.jetbrains.com/idea/)
- [Transmit 5](https://panic.com/transmit/)
- [Charles](https://www.charlesproxy.com/latest-release/download.do)
- [POSTMAN](https://www.postman.com/)

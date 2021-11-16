# ShopPal

## Description

ShopPal is an e-commerce based android application which follows MVC architechture.
E-commerce or electronic commerce actually means the use of an electronic medium for commercial transactions, but it is commonly used to refer to selling products and services over the internet to consumers or other businesses.
With the advent of internet, e-commerce has become popular. There are a lot of e-commerce applications in market now. Flipkart and amazon are one of the most popular android e-commerce application in indian market.

ShopPal implements firebase authentication, firebase cloud firestore, firebase storage and firebase realtime database. It uses firebase authentication to login and register user. User profile details are stored in firebase cloud firestore. All the products and status of placed orders are stored in firebase realtime database. Firebase storage is being used to store user profile images. Room persistence library is being used for local caching and glide library is used for image loading and caching.

So, the primary motivation in building this application is to make use of firebase technologies, room persistence library and glide library to build e-commerce app.


## Application Screenshots

* Splash Screen:

* The user will be presented with the authentication screen from which the user can login, register and reset password.

  - **Login Screen:** The user can login by entering proper login credentials. 

  - **Register Screen:** A new account can be created by entering all the necessary details.

  - **Forgot Password Screen:**  Password can be reset by sending reset email link after entering your account's email address.

* After authentication or registeration of user, the user will be presented with Dashboard Screen which contains options to choose shopping items, cart items and status of orders placed.

  - **Shopping Items Screen:** The user can choose shopping item to buy or add to cart. It has search functionality to search a particular product. It also displays various item categories to get items of a particular category.
  
  - **Item Overview Screen:** It shows details of the shopping item. The user can buy or add to cart.
  
  - **Cart Items Screen:** It implements cart functionality.
  
  - **Select Address Screen:** The user can select address to buy product item.

  - **Add Address Screen:** The user can add new address that can be selected by the user to buy product item/items.

  - **Checkout Screen:** It shows details of the product/products to be bought, recipent address, items reciept and payment mode. The user can confirm details to place order.
  
  - **Order Status Items Screen:** It shows status of various orders placed.
  
  - **Order Item Screen:** It shows details of the order placed. It contains all the necessary details of the order.

* Settings Screen: The user can edit profile details, see profile details and logout in the settings screen after clicking on settings icon on the dashboard.

* User Profile Details Screen: The user can edit profile details in the user profile details screen.


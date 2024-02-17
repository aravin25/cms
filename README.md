# cms
Credit Card Management System

Entities: 
	
  User
	Attributes: UserID, Name, Address, Email, Phone, Type, Category 
	
  Account
	Attributes: AccountID, UserID (FK), Balance, OpenDate, Status 
	
  CreditCard
	Attributes: CardNumber, AccountID (FK), ExpiryDate, CVV, CreditLimit, ActivationStatus 
	
  Transaction
	Attributes: TransactionID, CardNumber(FK), Amount, Date,  MerchantID
	
  Admin
	Attributes: AllTransaction,AllAccount,allCreditCard

Relationships (Microservice Architecture)  
	Customers to Accounts
	One-to-one: A customer can have multiple accounts, but each account is linked to one customer. 
	
  Accounts to CreditCards
	One-to-one: An account can have multiple credit cards, but each credit card is linked to one account. 
	
  CreditCards to Transactions
	One-to-Many: A credit card can have multiple transactions, but each transaction is made with one credit card. 
	
  Transactions to Merchants
	Many-to-One: Multiple transactions can occur at a single merchant, but each transaction is made at one merchant.

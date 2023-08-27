# My Personal Project

## Personal Banking Application

The application will be similar to our everyday banking application like *Scotia*, *TD* and *the RBC Royal Bank*.
But more functionalities are integrated into a single interface: it can not only store the clients' financial 
situation (income, expenses, debts, credit report and score etc.) but also calculate and offer mortgage based on 
current interest rate and borrowers' credit report.

Anyone who wish to save money for future emergency and education, or simply set up a virtual bank account for
their children to forster a good saving habit can make use of this application. 

I personally love keeping track of my daily expenses and also interested in loan and mortgage which I might encounter 
later in my life. I would love to have an app to simulate the whole money saving and borrowing process.



## User Stories:
- As a user, I want to be able to create multiple bank accounts with custom name and ID. And get access with IDs.
- As a user, I want to be able to have a **Chequing** account that let me deposit money.
- As a user, I want to have a **Saving** account that get money transferred from chequing account.
- As a user, I want to be able to access all my income and **activity records**.
- As a user, I want to be able to check my current **credit score** based on income and spending habits.
- As a user, I want to be able to get a quote of **mortgage** or loan at current interest rate.
- As a user, I want to save my transaction list and balance to file
- As a user, I want to load my transaction list and balance from file
- As a user, when I quit the application, I want to be given the option to save my file.


## Instructions for Grader:

- To log in test user, use the ID: 1234
- You can generate the first required event related to adding Accounts by registering new account.
- You can generate the second required event by completing a withdrawal, deposit, or transfer operation and create transaction history.
- You can see the view the list of Transaction histories by clicking on Transaction in the account menu.
- You can locate my visual component in the Account and Menu page.
- You can save the state of my application by clicking the Save operation in the Menu or by having the option when quitting the ATM.
- You can reload the state of my application by clicking the Load operation in the Menu.

# Phase 4: Task 2
Event Log:
Tue Apr 11 01:27:48 PDT 2023
Account Created for Kai

Tue Apr 11 01:27:57 PDT 2023
Account Created for test1

Tue Apr 11 01:28:02 PDT 2023
test1 deposited $1223.0

Tue Apr 11 01:28:04 PDT 2023
test1 withdrew $32.0


This shows a sample Event log when you quit ATM when I registered a new account and completed two transactions to that ATM account.
It shows a description of who created the account and how much was deposited or withdrawn to/from which account. If nothing displays in the log when the user quits, it means that the user has not either deposited money,
withdrawn money, or transferred money between accounts so there would be nothing to log in this case.

# Phase 4: Task 3
When considering my project, I believe that I successfully managed cohesion and coupling in my design. Specifically,
my Account class exhibited strong cohesion as it focused solely on methods related to the account itself, 
such as managing balance operations. This high level of cohesion facilitated code maintenance by allowing me to 
concentrate on a single class when making modifications. Additionally, I achieved low coupling by avoiding the use of 
code from one module in another, such as between my Transaction object class and my Account object class.

I recognize that I could improve my code through refactoring. In particular, 
I would like to eliminate the duplication in my GUI, which currently makes the class cumbersome and difficult to 
maintain. To achieve this, I plan to create methods that combine repeated code, making my application more streamlined 
and readable. By doing so, I will be able to more easily locate specific methods without needing to navigate through 
numerous instances of duplicated code.
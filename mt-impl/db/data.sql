-- Inserts for account
insert into account (NAME, OPNG_BAL, CRNT_BAL, ACCT_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values 
('Wallet', 0, 0, 'CASH', 1, now(), now()),
('HDFC-BLR', 0, 0, 'BANK', 1, now(), now()),
('ICICI-BLR', 0, 0, 'BANK', 1, now(), now()),
('ICICI-PUN', 0, 0, 'BANK', 1, now(), now());

insert into account (NAME, OPNG_BAL, CRNT_BAL, ACCT_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values 
('Sodexo', 0, 0, 'CASH', 1, now(), now());

-- Inserts for CATEGORY
insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Vehicle', null, (select id from account where name = 'Wallet'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values 
('Fuel', (select id from category p where p.name = 'Vehicle'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Service', (select id from category p where p.name = 'Vehicle'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Parking & Tolls', (select id from category p where p.name = 'Vehicle'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Maintenance', (select id from category p where p.name = 'Vehicle'), (select id from account where name = 'Wallet'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Charity', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Debt', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Loan', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Credit Card', (select id from category p where p.name = 'Debt'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now());

insert into category (NAME, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values 
('Eating Out', (select id from account where name = 'Wallet'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Entertainment', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Movies', (select id from category p where p.name = 'Entertainment'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Games', (select id from category p where p.name = 'Entertainment'), (select id from account where name = 'Wallet'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Family', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('House', (select id from category p where p.name = 'Family'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Child Care', (select id from category p where p.name = 'Family'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Medical', (select id from category p where p.name = 'Family'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Misc', (select id from category p where p.name = 'Family'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Fitness', (select id from category p where p.name = 'Family'), (select id from account where name = 'Wallet'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Home', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Brokerage', (select id from category p where p.name = 'Home' and PRNT_CAT_ID is null), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Deposit', (select id from category p where p.name = 'Home' and PRNT_CAT_ID is null), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Maid', (select id from category p where p.name = 'Home' and PRNT_CAT_ID is null), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Maintenance', (select id from category p where p.name = 'Home' and PRNT_CAT_ID is null), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Rent', (select id from category p where p.name = 'Home' and PRNT_CAT_ID is null), (select id from account where name = 'Wallet'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Household', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Food', (select id from category p where p.name = 'Household'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Milk', (select id from category p where p.name = 'Household'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Grocery', (select id from category p where p.name = 'Household'), (select id from account where name = 'Wallet'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Insurance', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Vehicle', (select id from category p where p.name = 'Insurance'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Home', (select id from category p where p.name = 'Insurance'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Life', (select id from category p where p.name = 'Insurance'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Other', (select id from category p where p.name = 'Insurance'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Shopping', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Clothes', (select id from category p where p.name = 'Shopping'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Electronics', (select id from category p where p.name = 'Shopping'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Furniture', (select id from category p where p.name = 'Shopping'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Other', (select id from category p where p.name = 'Shopping'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Footware', (select id from category p where p.name = 'Shopping' and p.prnt_cat_id is null), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Tax', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Income Tax', (select id from category p where p.name = 'Tax'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Property Tax', (select id from category p where p.name = 'Tax'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Zakaath', (select id from category p where p.name = 'Tax'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Other', (select id from category p where p.name = 'Tax'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Travel', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Air Transportation', (select id from category p where p.name = 'Travel'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Ground Transportation', (select id from category p where p.name = 'Travel'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Lodging', (select id from category p where p.name = 'Travel'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Other', (select id from category p where p.name = 'Travel'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Utility', null, (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Cable TV', (select id from category p where p.name = 'Utility'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Mobile', (select id from category p where p.name = 'Utility'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Electrical', (select id from category p where p.name = 'Utility'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Internet', (select id from category p where p.name = 'Utility'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('LPG', (select id from category p where p.name = 'Utility'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Newspaper', (select id from category p where p.name = 'Utility'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Water', (select id from category p where p.name = 'Utility'), (select id from account where name = 'Wallet'), 'E', 1, now(), now()),
('Other', (select id from category p where p.name = 'Utility'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now()),
('Trash', (select id from category p where p.name = 'Utility'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now());
insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Hair Dressing', (select id from category p where p.name = 'Utility'), (select id from account where name = 'Wallet'), 'E', 1, now(), now());

insert into category (NAME, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values 
('Salary', (select id from account where name = 'HDFC-BLR'), 'I', 1, now(), now());

insert into category (NAME, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values 
('Interest', (select id from account where name = 'HDFC-BLR'), 'I', 1, now(), now());

insert into category (NAME, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values 
('Deposit', (select id from account where name = 'HDFC-BLR'), 'I', 1, now(), now());

insert into category (NAME, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values 
('Tax Refund', (select id from account where name = 'HDFC-BLR'), 'I', 1, now(), now());

insert into category (NAME, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values 
('Other', (select id from account where name = 'HDFC-BLR'), 'I', 1, now(), now());

insert into category (NAME, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values 
('Sodexo', (select id from account where name = 'Wallet'), 'I', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Baby', null, (select id from account where name = 'Wallet'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Medical', (select id from category where name = 'Baby'), (select id from account where name = 'Wallet'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Shopping', (select id from category where name = 'Baby'), (select id from account where name = 'Wallet'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Salma', (select id from category where name = 'Loan'), (select id from account where name = 'HDFC-BLR'), 'E', 1, now(), now());

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Salma Pocket Money', null, (select id from account where name = 'Wallet'), 'E', 1, now(), now());
-- Inserts for TRANSACTION
insert into transaction (ON_DATE, AMOUNT, CAT_ID, ACCT_ID, DESCRIPTION, CREATED_DATE, MODIFIED_DATE) values 
(now(), 250.00, (select id from category p where p.name = 'Goods'), (select id from account where name = 'Wallet'), 'Fruits', now(), now());

insert into setting (name, description, value, type) values 
('history_report_for', 'No. of Months History Report', '5', 'int'),
('date_format', 'Date Format', 'dd-MM-yyyy h:mm a', 'string');

insert into setting (name, description, value, type) values 
('no_of_top_expenses', 'No. of Top Expenses', '5', 'int'),
('no_of_top_incomes', 'No. of Top Incomes', '3', 'int');

insert into category (NAME, PRNT_CAT_ID, DFLT_ACCT_ID, TRAN_TYPE, DISPLAY, CREATED_DATE, MODIFIED_DATE) values
('Transfer', null, (select id from account where name = 'Wallet'), 'T', 1, now(), now());

-- Reporting:
select on_date, sum(amount) from transaction where on_date between '2014-03-01' and '2014-03-31' group by day(on_date);

select month(t.on_date), c.tran_type, a.name, sum(t.amount) from transaction t, category c, account a where t.cat_id = c.id and 
t.acct_id = a.id and date(t.on_date) between '2014-01-01' and '2014-03-31' group by a.name,month(t.on_date),c.tran_type order by t.on_date;

-- Shows the Income & Expense during particular period
select a.name, sum(case when c.tran_type = 'E' then t.amount end) as exp, sum(case when c.tran_type = 'I' then t.amount end) as inc from 
transaction t, category c, account a where t.cat_id = c.id and t.acct_id = a.id and t.on_date between '2014-02-01' and '2014-02-31' 
group by a.name,month(t.on_date) order by t.on_date;

insert into MT_USER (NAME, USERNAME, PASSWORD, CREATED_DATE, MODIFIED_DATE) values
('Maqbool', 'maqbool', 'maqbool', now(), now()),
('Admin', 'admin', 'admin', now(), now());

insert into MT_USER_ROLE (ROLE_ID, USER_ID, CREATED_DATE, MODIFIED_DATE) values
('U', (select id from MT_USER where name = 'maqbool'), now(), now()),
('U', (select id from MT_USER where name = 'admin'), now(), now()),
('A', (select id from MT_USER where name = 'admin'), now(), now());


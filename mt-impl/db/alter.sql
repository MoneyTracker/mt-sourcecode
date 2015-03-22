-- add user_id 
alter table account add user_id int(20) NOT NULL;
update account set user_id = (select id from mt_user where name = 'Demo');
alter table account 
add constraint fk_account_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );

alter table bnk_stmnt add user_id int(20) NOT NULL;
update bnk_stmnt set user_id = (select id from mt_user where name = 'Demo');
alter table bnk_stmnt 
add constraint fk_bnk_stmnt_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );
-- 
alter table bnk_stmnt_col_map add user_id int(20) NOT NULL;
update bnk_stmnt_col_map set user_id = (select id from mt_user where name = 'Demo');
alter table bnk_stmnt_col_map 
add constraint fk_bnk_stmnt_col_map_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );

alter table bnk_stmnt_data_field add user_id int(20) NOT NULL;
update bnk_stmnt_data_field set user_id = (select id from mt_user where name = 'Demo');
alter table bnk_stmnt_data_field 
add constraint fk_bnk_stmnt_data_field_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );

alter table bnk_stmnt_data_map add user_id int(20) NOT NULL;
update bnk_stmnt_data_map set user_id = (select id from mt_user where name = 'Demo');
alter table bnk_stmnt_data_map 
add constraint fk_bnk_stmnt_data_map_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );

alter table budget add user_id int(20) NOT NULL;
update budget set user_id = (select id from mt_user where name = 'Demo');
alter table budget 
add constraint fk_budget_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );

alter table budget_item add user_id int(20) NOT NULL;
update budget_item set user_id = (select id from mt_user where name = 'Demo');
alter table budget_item 
add constraint fk_budget_item_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );

alter table category add user_id int(20) NOT NULL;
update category set user_id = (select id from mt_user where name = 'Demo');
alter table category 
add constraint fk_category_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );

alter table budget add user_id int(20) NOT NULL;
update budget set user_id = (select id from mt_user where name = 'Demo');
alter table budget 
add constraint fk_budget_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );

alter table future_transaction add user_id int(20) NOT NULL;
update future_transaction set user_id = (select id from mt_user where name = 'Demo');
alter table future_transaction 
add constraint fk_future_transaction_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );

alter table imported_transaction add user_id int(20) NOT NULL;
update imported_transaction set user_id = (select id from mt_user where name = 'Demo');
alter table imported_transaction 
add constraint fk_imported_transaction_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );

alter table setting add user_id int(20) NOT NULL;
update setting set user_id = (select id from mt_user where name = 'Demo');
alter table setting 
add constraint fk_setting_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );

alter table transaction add user_id int(20) NOT NULL;
update transaction set user_id = (select id from mt_user where name = 'Demo');
alter table transaction 
add constraint fk_transaction_user_id FOREIGN KEY (USER_ID) REFERENCES mt_user (ID );
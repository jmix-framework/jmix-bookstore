insert into BOOKSTORE_USER
(ID, VERSION, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, ACTIVE, TIME_ZONE_ID)
values ('163036a2-8a06-af22-4aa7-f612dda4ae85', 1, 'mike', '{bcrypt}$2a$10$BTrOsOBdZfrshL0Ylts8SeiFds761g6LNjgEVsbdYD9OMslbL0PFq', 'Mike', 'Holloway', 'mike@jmix-bookstore.com', true, null);
insert into BOOKSTORE_USER
(ID, VERSION, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, ACTIVE, TIME_ZONE_ID)
values ('16f70b54-03d4-4e04-e10a-32806d2dbe05', 1, 'oliver', '{bcrypt}$2a$10$iaZ4uHzsvPGLifLssqKysejS8VFyLFnWzlZmtKSyk.08ZUDNOjJ/S', 'Oliver', 'Bolick', 'oliver@jmix-bookstore.com', true, null);
insert into BOOKSTORE_USER
(ID, VERSION, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, ACTIVE, TIME_ZONE_ID)
values ('3118e34b-728f-d738-029c-8b0f6c7c616c', 1, 'andrey', '{bcrypt}$2a$10$NQwH3nS7YNvq34CjkFpKu.1l8v07CQ7nadxKOO0.FpLpcdwwuSDQe', 'Andrey', 'Weber', 'andrey@jmix-bookstore.com', true, null);
insert into BOOKSTORE_USER
(ID, VERSION, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, ACTIVE, TIME_ZONE_ID)
values ('420d3119-0c1a-def2-b723-ee21cd06f798', 1, 'william', '{bcrypt}$2a$10$5qZClvNcDk0JuthDvL/IyeuTcwaQw145BuK6Mzxro.BOEuDb0SfXC', 'William', 'Linville', 'william@jmix-bookstore.com', true, null);
insert into BOOKSTORE_USER
(ID, VERSION, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, ACTIVE, TIME_ZONE_ID)
values ('956cacb5-4d2e-37d3-8040-577bf69da158', 1, 'nicole', '{bcrypt}$2a$10$7a.nfmMBKRcFs1/PlL9heuaWP67admiA0ivY2hTSfq.O7Y3C9sM16', 'Nicole', 'Berry', 'nicole@jmix-bookstore.com', true, null);
insert into BOOKSTORE_USER
(ID, VERSION, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, ACTIVE, TIME_ZONE_ID)
values ('aae2a4e6-9629-15e1-4295-bd196eab48e6', 2, 'emma', '{bcrypt}$2a$10$l247NBzTjQauyLP/0xWcP.wPT1Vqo1YAbfAOuajtn4Q9u209bIcyG', 'Emma', 'McAlister', 'emma@jmix-bookstore.com', true, null);
insert into BOOKSTORE_USER
(ID, VERSION, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, ACTIVE, TIME_ZONE_ID)
values ('cb419dac-5c9f-b021-3ce1-c436a45177dd', 1, 'adrian', '{bcrypt}$2a$10$rNbo0JkyAPEGO6HwaSa9LuBqbb56RnlJKaEOhqMPhXecjNTrmWEX.', 'Adrian', 'Adams', 'adrian@jmix-bookstore.com', true, null);
insert into BOOKSTORE_USER
(ID, VERSION, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL, ACTIVE, TIME_ZONE_ID)
values ('fc01dd20-f357-66cb-9a42-5e386c031756', 1, 'sophia', '{bcrypt}$2a$10$CmqTD/tqXOUmPoYBa98PCerGsTRi9yZsiEvJHx4q175YgWxSjnY..', 'Sophia', 'Burnett', 'sophia@jmix-bookstore.com', true, null);



insert into SEC_ROLE_ASSIGNMENT
(ID, VERSION, CREATE_TS, CREATED_BY, UPDATE_TS, UPDATED_BY, DELETE_TS, DELETED_BY, USERNAME, ROLE_CODE, ROLE_TYPE)
values ('2cccc3e4-67c5-f8e6-3ead-0d4cd50bb793', 1, '2022-10-26 08:16:28', 'admin', '2022-10-26 08:16:28', null, null, null, 'william', 'ui-minimal', 'resource');
insert into SEC_ROLE_ASSIGNMENT
(ID, VERSION, CREATE_TS, CREATED_BY, UPDATE_TS, UPDATED_BY, DELETE_TS, DELETED_BY, USERNAME, ROLE_CODE, ROLE_TYPE)
values ('3d10899b-166b-8b27-ae03-d273c239f9ca', 1, '2022-10-26 08:16:28', 'admin', '2022-10-26 08:16:28', null, null, null, 'william', 'procurement-employee', 'resource');
insert into SEC_ROLE_ASSIGNMENT
(ID, VERSION, CREATE_TS, CREATED_BY, UPDATE_TS, UPDATED_BY, DELETE_TS, DELETED_BY, USERNAME, ROLE_CODE, ROLE_TYPE)
values ('5a43a094-27a6-8f75-1232-8ccdf6bb411b', 1, '2022-10-26 08:16:28', 'admin', '2022-10-26 08:16:28', null, null, null, 'william', 'bpm-process-actor', 'resource');

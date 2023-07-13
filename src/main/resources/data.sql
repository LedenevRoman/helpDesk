insert into USERS (id, email, password, role, first_name, last_name) values (1, 'user1_mogilev@yopmail.com', '$2a$04$Paw65eAQHGYrjGqnd2A7nucDqnLeE3MCpuIQiMi.mb.3m9YQUDcVW', 'EMPLOYEE', 'Ivan', 'Ivanov');
insert into USERS (id, email, password, role, first_name, last_name) values (2, 'user2_mogilev@yopmail.com', '$2a$04$Paw65eAQHGYrjGqnd2A7nucDqnLeE3MCpuIQiMi.mb.3m9YQUDcVW', 'EMPLOYEE', 'Petr', 'Petrov');
insert into USERS (id, email, password, role, first_name, last_name) values (3, 'manager1_mogilev@yopmail.com', '$2a$04$rEF3ut.KmOtjuLkCUAf97.O6Ous2WMTMf/6KoyWAWiMq891C1hjMS', 'MANAGER', 'Sidr', 'Sidorov');
insert into USERS (id, email, password, role, first_name, last_name) values (4, 'manager2_mogilev@yopmail.com', '$2a$04$rEF3ut.KmOtjuLkCUAf97.O6Ous2WMTMf/6KoyWAWiMq891C1hjMS', 'MANAGER', 'Valeriy', 'Valeriev');
insert into USERS (id, email, password, role, first_name, last_name) values (5, 'engineer1_mogilev@yopmail.com', '$2a$04$3tLwHyGKjH5mDHzt443Cye3IBJ598Tg3OZm0FRbG6Vieq4GezdkOG', 'ENGINEER', 'Anton', 'Antonov');
insert into USERS (id, email, password, role, first_name, last_name) values (6, 'engineer2_mogilev@yopmail.com', '$2a$04$3tLwHyGKjH5mDHzt443Cye3IBJ598Tg3OZm0FRbG6Vieq4GezdkOG', 'ENGINEER', 'Nikolay', 'Nikolaev');

insert into categories (id, name) values (1, 'Application & Services');
insert into categories (id, name) values (2, 'Benefits & Paper Work');
insert into categories (id, name) values (3, 'Hardware & Software');
insert into categories (id, name) values (4, 'People Management');
insert into categories (id, name) values (5, 'Security & Access');
insert into categories (id, name) values (6, 'Workplaces & Facilities');

insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test1', 'NEW', 'CRITICAL', null , null, '3', '1', 'any string', '2021-08-2', '2030-11-1');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test2', 'NEW', 'HIGH', null, null, '3', '1', 'any string', '2021-08-3', '2030-11-2');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test3', 'NEW', 'AVERAGE', null, null, '4', '2', 'any string', '2021-08-4', '2030-11-3');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test4', 'DRAFT', 'LOW', null, null, '4', '2', 'any string', '2021-08-5', '2030-11-4');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test5', 'DRAFT', 'CRITICAL', null, null, '2', '3', 'any string', '2021-08-6', '2030-11-5');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test6', 'APPROVED', 'HIGH', '4', null, '2', '3', 'any string', '2021-08-7', '2030-11-6');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test7', 'APPROVED', 'AVERAGE', '4', null, '1', '4', 'any string', '2021-08-2', '2030-11-7');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test8', 'DECLINED', 'LOW', '4', null, '1', '4', 'any string', '2021-08-2', '2030-11-8');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test9', 'DECLINED', 'CRITICAL', '4', null, '1', '4', 'any string', '2021-08-2', '2030-11-9');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test10', 'IN_PROGRESS', 'HIGH', '4', '5', '1', '5', 'any string', '2021-08-2', '2030-11-10');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test11', 'IN_PROGRESS', 'AVERAGE', '4', '5', '1', '5', 'any string', '2021-08-2', '2030-11-11');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test12', 'DONE', 'LOW', '4', '5', '1', '5', 'any string', '2021-08-2', '2030-11-12');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test13', 'DONE', 'CRITICAL', '4', '5', '1', '6', 'any string', '2021-08-2', '2030-11-13');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test14', 'IN_PROGRESS', 'HIGH', '4', '6', '1', '6', 'any string', '2021-08-2', '2030-11-14');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test15', 'DONE', 'AVERAGE', '6', '6', '1', '6', 'any string', '2021-08-2', '2030-11-15');

insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test16', 'NEW', 'LOW', null , null, '3', '1', 'any string', '2021-08-2', '2030-11-16');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test17', 'NEW', 'CRITICAL', null, null, '3', '1', 'any string', '2021-08-2', '2030-11-17');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test18', 'NEW', 'HIGH', null, null, '4', '2', 'any string', '2021-08-2', '2030-11-18');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test19', 'DRAFT', 'AVERAGE', null, null, '4', '2', 'any string', '2021-08-2', '2030-11-19');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test20', 'DRAFT', 'LOW', null, null, '1', '3', 'any string', '2021-08-2', '2030-11-19');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test21', 'APPROVED', 'CRITICAL', '4', null, '1', '3', 'any string', '2021-08-2', '2030-11-18');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test22', 'APPROVED', 'HIGH', '4', null, '2', '4', 'any string', '2021-08-2', '2030-11-17');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test23', 'DECLINED', 'AVERAGE', '4', null, '2', '4', 'any string', '2021-08-2', '2030-11-16');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test24', 'DECLINED', 'LOW', '4', null, '2', '4', 'any string', '2021-08-2', '2030-11-15');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test25', 'IN_PROGRESS', 'CRITICAL', '4', '5', '2', '5', 'any string', '2021-08-2', '2030-11-14');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test26', 'IN_PROGRESS', 'HIGH', '4', '5', '2', '5', 'any string', '2021-08-2', '2030-11-13');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test27', 'DONE', 'AVERAGE', '4', '5', '2', '5', 'any string', '2021-08-2', '2030-11-12');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test28', 'DONE', 'LOW', '4', '5', '2', '6', 'any string', '2021-08-2', '2030-11-11');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test29', 'IN_PROGRESS', 'CRITICAL', '4', '6', '2', '6', 'any string', '2021-08-2', '2030-11-11');
insert into tickets (name, status, urgency, approver_id, assignee_id, owner_id, category_id, description, created_on, desired_resolution_date) values ('test30', 'DONE', 'HIGH', '6', '6', '2', '6', 'any string', '2021-08-2', '2030-11-10');



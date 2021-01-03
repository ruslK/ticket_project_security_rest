INSERT INTO roles (description)
VALUES ('Admin'),
       ('Manager'),
       ('Employee');


INSERT INTO users (first_name, last_name, user_name, phone, password, enabled, role_id, gender)
values ('Mikaa', 'Submit', 'Mikaa@submit', '4564564545', 'password', true, 2, 'MALE'),
       ('Alex', 'Someone', 'Alex@Someone', '4564564545', 'password', true,  2, 'MALE'),
       ('Sara', 'Jones', 'Sara@Jones', '4564564545', 'password', true,  2, 'FEMALE');


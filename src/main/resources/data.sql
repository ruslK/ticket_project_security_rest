INSERT INTO roles (description)
VALUES ('Admin'),
       ('Manager'),
       ('Employee');


INSERT INTO users (first_name, last_name, user_name, phone, password, enabled, role_id, gender)
values ('Mikaa', 'Submit', 'Mikaa@submit', '4564564545', 'password', true, 1, 'MALE'),
       ('Alex', 'Someone', 'Alex@Someone', '4564564545', 'password', true, 2, 'MALE'),
       ('Sara', 'Jones', 'Sara@Jones', '4564564545', 'password', true, 3, 'FEMALE');


INSERT INTO projects (project_name, project_code, start_date, end_date,
                      project_details, project_status, user_id, complete_count, in_complete_count)
values ('Selenium', 'SDET-001', '05/05/2020', '07/05/2020', 'SDET Course', 'OPEN', 1, 0, 0),
       ('JAVA_001', 'SDET-002', '05/05/2020', '07/05/2020', 'SDET Course', 'OPEN', 1, 0, 0);

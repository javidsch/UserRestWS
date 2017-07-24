--Dummy user-list to populate database.
INSERT INTO USERS (id, user_name, first_name, last_name, sex, birth_date, single, family_members_count, email, salary, version) 
VALUES  (1, 'naveen', 'Naveen', 'Andrews', 'male', DATE '1969-01-17', true, 2, 'naveen@abc.com', 5000.50, 1), 
        (2, 'matthew1', 'Matthew', 'Fox', 'male', DATE '1966-07-14', false, 3, 'matthew14@abc.com', 6000.20, 1), 
        (3, 'maggie21', 'Maggie', 'Grace', 'female', DATE '1983-09-21', true, 6, 'maggie21@abc.com', 8000.70, 1), 
        (4, 'yunjin', 'Yunjin', 'Kim', 'female', DATE '1973-11-07', false, 2, 'yunjin07@abc.com', 5720.50, 1), 
        (5, 'jorge', 'Jorge', 'Garcia', 'male', DATE '1973-04-28', true, 7, 'jorge.garcia@abc.com', 7030.145, 1),
        (6, 'josh', 'Josh', 'Holloway', 'male', DATE '1969-07-20', false, 5, 'josh1969@abc.com', 6080.20, 1), 
        (7, 'eva1969', 'Evangeline', 'Lilly', 'female', DATE '1969-08-03', false, 3, 'evangeline@abc.com', 8090.70, 1), 
        (8, 'dominic', 'Dominic', 'Monaghan', 'male', DATE '1976-12-08', false, 9, 'dominic@abc.com', 7010.45, 1), 
        (9, 'terry15', 'Terry', 'O''Quinn', 'male', DATE '1952-07-15', false, 3, 'terry15@abc.com', 7330.145, 1), 
        (10, 'sonya', 'Sonya', 'Walger', 'female', DATE '1976-06-06', false, 4, 'sonya.walger@abc.com', 9560.145, 1);
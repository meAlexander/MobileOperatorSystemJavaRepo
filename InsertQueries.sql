INSERT INTO admins (admin_name, admin_pass)
VALUES ("admin1", "123456A"),
("admin2", "123456B"),
("admin3", "123456C");

INSERT INTO clients (client_name, client_pass)
VALUES ("Dimitar", "123456@dD"),
("Georgi", "123456@eE"),
("Ivan", "123456@fF"),
("Martin", "123456@gG");

INSERT INTO services (service_name, service_limit, bill)
VALUES ("MB", 2000, 9.99),
("MB", 3000, 12.99),
("SMS", 500, 7.99),
("MIN", 10000, 18.99),
("MIN", 1000, 8.99);

INSERT INTO contracts (signing_date, expiry_date, payment_date, phone, client_id)
VALUES ("2019-02-01", "2021-02-01", 17, "0898433559", 1),
("2020-05-08", "2022-05-08", 18, "0895684879", 3),
("2018-10-15", "2020-10-15", 8, "0896488246", 3),
("2020-06-24", "2022-06-24", 19, "0895489829", 2);

INSERT INTO contracts_services (contract_id, service_id, consumption)
VALUES (1, 2, 2500),
(1, 3, 420),
(2, 4, 420),
(4, 4, 420),
(3, 3, 420),
(3, 1, 1250);

-- INSERT INTO debtors (contract_id, contract_status)
-- VALUES (1, "paid"),
-- (2, "not paid"),
-- (3, "paid"),
-- (4, "not paid");
INSERT INTO admins (admin_name, admin_pass)
VALUES ("admin1", "123456A"),
("admin2", "123456B"),
("admin3", "123456C");

INSERT INTO clients (client_name, client_pass)
VALUES ("Dimitar", "123456D"),
("Georgi", "123456E"),
("Ivan", "123456F"),
("Martin", "123456G");

INSERT INTO services (service_name, service_limit, bill)
VALUES ("MB", 2000, 9.99),
("MB", 3000, 12.99),
("SMS", 500, 7.99),
("MIN", 10000, 18.99),
("MIN", 1000, 8.99);

INSERT INTO contracts (signing_date, expiry_date, payment_date, phone, client_id)
VALUES ("2019-02-01", "2021-02-01", 10, "0898433559", 1),
("2020-05-08", "2022-05-08", 15, "0895684879", 3),
("2018-10-15", "2020-10-15", 8, "0896488246", 3),
("2020-06-24", "2022-06-24", 23, "0895489829", 2);

INSERT INTO contracts_services (contract_id, service_id, consumption)
VALUES (1, 2, 2500),
(1, 3, 420),
(3, 1, 1250);
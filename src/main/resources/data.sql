insert into security.roles (role) values ('ROLE_ADMIN');
insert into security.roles (role) values ('ROLE_USER');

insert into security.users (first_name, last_name, country, password) VALUES ('kiran', 'ghale', 'nepal', '$2a$10$hO2ds9mnaeCKkY0vxKp1l.gRb.Xcg1WuloXNjIoIfh6QR8WqQECPy');

insert into security.users_roles (user_id,  roles_id) VALUES (1, 1);
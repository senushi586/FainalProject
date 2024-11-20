DROP DATABASE IF EXISTS fruityFizz;

CREATE DATABASE fruityFizz;

USE fruityFizz;

CREATE TABLE employee(
                         id VARCHAR(10) PRIMARY KEY,
                         name VARCHAR(50),
                         role VARCHAR(50),
                         mobile VARCHAR(20),
                         email VARCHAR(100)
);

CREATE TABLE user(
                     username VARCHAR(50) PRIMARY KEY,
                     password VARCHAR(50),
                     title VARCHAR(50) ,
                     e_mail VARCHAR(100),
                     u_id VARCHAR(10),
                     CONSTRAINT FOREIGN KEY (u_id) REFERENCES employee (id) on Delete Cascade on Update Cascade


);

CREATE TABLE customer(
                         id VARCHAR(10) PRIMARY KEY,
                         name VARCHAR(50),
                         address VARCHAR(100),
                         mobile VARCHAR(20),
                         email VARCHAR(100),
                         dob  DATE
);



CREATE TABLE supplier(
                         id VARCHAR(10) PRIMARY KEY,
                         company_name VARCHAR(100),
                         location VARCHAR(100),
                         mobile VARCHAR(20),
                         email VARCHAR(100)
);

CREATE TABLE item(
                     id VARCHAR(10) PRIMARY KEY,
                     name VARCHAR(50),
                     category VARCHAR(100),
                     brand VARCHAR(50),
                     unit_price DECIMAL(10,2),
                     qty INT(6)
);

CREATE TABLE supplier_detail(
                                i_id VARCHAR(10),
                                s_id VARCHAR(10),
                                qty INT(10),
                                price DOUBLE(10,2),
                                date Date,

                                CONSTRAINT FOREIGN KEY (i_id) REFERENCES item (id) on Delete Cascade on Update Cascade,
                                CONSTRAINT FOREIGN KEY (s_id) REFERENCES supplier (id) on Delete Cascade on Update Cascade
);

CREATE TABLE orders(
                       id VARCHAR(10) PRIMARY KEY,
                       c_id VARCHAR(10),
                       date DATE,
                       payment DOUBLE(10,2),

                       CONSTRAINT FOREIGN KEY (c_id) REFERENCES customer (id) on Delete Cascade on Update Cascade
);

CREATE TABLE order_detail(
                             order_id VARCHAR(10),
                             item_id VARCHAR(10),
                             description VARCHAR(10),
                             qty INT(6),
                             unit_price DECIMAL(10,2),
                             total DECIMAL(10,2),

                             CONSTRAINT FOREIGN KEY (order_id) REFERENCES orders (id) on Delete Cascade on Update Cascade,
                             CONSTRAINT FOREIGN KEY (item_id) REFERENCES item (id) on Delete Cascade on Update Cascade
);

CREATE TABLE booking(
                        id VARCHAR(10) PRIMARY KEY,
                        c_id VARCHAR(10),
                        nic VARCHAR(20),
                        date DATE,
                        table_name VARCHAR(50),

                        CONSTRAINT FOREIGN KEY (c_id) REFERENCES customer (id) on Delete Cascade on Update Cascade
);

CREATE TABLE booking_details(
                                i_id VARCHAR(10),
                                b_id VARCHAR(10),

                                CONSTRAINT FOREIGN KEY (i_id) REFERENCES item (id) on Delete Cascade on Update Cascade,
                                CONSTRAINT FOREIGN KEY (b_id) REFERENCES booking (id) on Delete Cascade on Update Cascade
);

CREATE TABLE delivery(
                         id VARCHAR(10) PRIMARY KEY,
                         order_id VARCHAR(10),
                         emp_id VARCHAR(10),
                         status VARCHAR(50),
                         location VARCHAR(50),
                         date DATE,

                         CONSTRAINT FOREIGN KEY (order_id) REFERENCES orders (id) on Delete Cascade on Update Cascade,
                         CONSTRAINT FOREIGN KEY (emp_id) REFERENCES employee (id) on Delete Cascade on Update Cascade
);



INSERT INTO employee VALUES('E001','Kamal','Employee','0912234189','ex1@email.com');

INSERT INTO user values('admin','1234','owner','dilnuka1234@gmail.com','E001');



show tables;

select item_id,SUM(qty)as orderCount from order_detail group by item_id order by ordercount desc limit 5;

select*from customer;

/*select item.name,item.unit_price,order_detail.qty,(item.unit_price*order_detail.qty) AS value,orders.payment
FROM order_detail
         INNER JOIN orders
                    ON order_detail.order_id=orders.id
         INNER JOIN item
                    ON item.id=order_detail.item_id
WHERE orders.id=(select max(orders.id ) FROM orders)
ORDER BY order_detail.order_id desc;*/







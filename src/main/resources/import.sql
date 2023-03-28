insert into tb_user(first_Name,last_Name, cpf, address, password, email, phone, sign_Up_Date) values ('John','Wick', '123.456.789-12', 'Baker Street', '123456789', 'john@email.com', '(11)91234-5678', '27/10/1999')
insert into tb_post(title, description, image, create_Date, thread_Open, user_id) values ('Have some food here!', 'Rice, beans and chocolates!', 'https://example.com/image.jpg', '27/10/2000', TRUE, 1);
insert into tb_comment(description, create_Date, post_id, user_id) VALUES ('hey, i need it - Hope', '27/10/1999', 1, 1);
insert into tb_comment(description, create_Date, post_id, user_id) VALUES ('hey, i need i - Hope', '27/10/1999', 1, 1);
insert into tb_comment(description, create_Date, post_id, user_id) VALUES ('hey, i need it  Hope', '27/10/1999', 1, 1);
insert into tb_user(first_Name,last_Name, cpf, address, password, email, phone, sign_Up_Date) values ('John','Wick', '123.456.789-12', 'Baker Street', '123456789', 'john@email.com', '(11)91234-5678', '27/10/2021')
insert into tb_user(first_Name,last_Name, cpf, address, password, email, phone, sign_Up_Date) values ('Rangi','Kyoshi', '987.654.321-10', 'Palm Street', '123456789@', 'rangyshi@email.com', '(13)98765-4321', '27/10/2022')
insert into tb_post(title, description, image, create_Date, thread_Open, user_id) values ('Have some food here!', 'Rice, beans and chocolates!', 'https://example.com/image.jpg', '27/10/2000', TRUE, 1);
insert into tb_post(title, description, image, create_Date, thread_Open, user_id) values ('Im Hungry!', 'Lets stop to food!', 'http://www.vaipradisney.com/blog/wp-content/uploads/2017/03/pods-avatar-pandora.gif', '27/10/2022', TRUE, 2);
insert into tb_comment(description, create_Date, post_id, user_id) VALUES ('hey, i need it - Hope', '27/10/2021', 1, 1);
insert into tb_comment(description, create_Date, post_id, user_id) VALUES ('Lets talk', '27/10/2021', 2, 1);
insert into tb_comment(description, create_Date, post_id, user_id) VALUES ('I have chocolates', '27/10/2022', 2, 2);
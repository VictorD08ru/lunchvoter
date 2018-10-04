Voting system for deciding where to have lunch
==============================================
REST API backend application using Java, Maven, Spring (MVC, Data, Security, Test), Hibernate, Jackson, JUnit5, HSQLDB. 

Voting system for deciding where to have lunch.

 - 2 types of users: admin and regular users
 - Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 - Menu changes each day (admins do the updates)
 - Users can vote on which restaurant they want to have lunch at
 - Only one vote counted per user
 - If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed
    
Each restaurant provides new menu each day.

**login:password**

`admin@gmail.com:admin
user0@yandex.ru:password0
user1@yandex.ru:password1
user2@yandex.ru:password2
user3@yandex.ru:password3
user4@yandex.ru:password4
user5@yandex.ru:password5
user6@yandex.ru:password6
user7@yandex.ru:password7
user8@yandex.ru:password8
user9@yandex.ru:password9`


Create field in your OS environment (or profile file) named 'LUNCHVOTER_ROOT' which value path from root to this project's directory (for example, "$HOME/projects/lunchvoter" or "C:/projects/lunchvoter").
DBMS and logging depend on LUNCHVOTER_ROOT.


**User1 get his profile**

`curl -v -u user1@yandex.ru:password1 http://localhost:8080/lunchvoter/profile`

**User2 delete himself**

`curl -v -X DELETE -u user2@yandex.ru:password2 http://localhost:8080/lunchvoter/profile`

**Admin gets all users**

`curl -v -u admin@gmail.com:admin http://localhost:8080/lunchvoter/admin/users`

**Admin gets User1**

`curl -v -u admin@gmail.com:admin http://localhost:8080/lunchvoter/admin/users/10001`

**Admin creates new User**

`curl -v -X POST -d '{ "id": null, "name": "newUser", "email": "newUser@yandex.ru", "password": "blablabla", "roles":["ROLE_USER"]}' -H 'Content-Type:application/json;charset=UTF-8' -u admin@gmail.com:admin http://localhost:8080/lunchvoter/admin/users`

**Admin updates User4**

`curl -v -X PUT -d '{"name":"updUser", "email":"updated@mail.ru", "password":"updated", "roles":["ROLE_USER"]}'  -H 'Content-Type:application/json;charset=UTF-8' -u admin@gmail.com:admin  http://localhost:8080/lunchvoter/admin/users/10004`

**Admin deletes User5**

`curl -v -X DELETE -u admin@gmail.com:admin  http://localhost:8080/lunchvoter/admin/users/10005`

**Any user can get all restraunts**

`curl -v -u user1@yandex.ru:password1 http://localhost:8080/lunchvoter/restaurants`

**Any user can get restaurant "Столовая №1"**

`curl -v -u user3@yandex.ru:password3 http://localhost:8080/lunchvoter/restaurants/10011`

**Admin can delete any restaurant** 

`curl -v -X DELETE -u admin@gmail.com:admin http://localhost:8080/lunchvoter/restaurants/10012`

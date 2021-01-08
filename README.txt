1) rabbitmq - https://github.com/YourNorth/JavaLab5Sem/tree/master/rabbitmq
    по второй ссылке вариант с fanout немного лучше
2) rabbitmq with direct, topic exchanges - https://github.com/YourNorth/JavaLab5Sem/tree/master/rabbitmq_extra_func
    рефакторинга ноль(
3) hateoas - https://github.com/YourNorth/jobboard/tree/master/Job_Board
    взял немного модифицированный проект с летней практики, добавил какое-то подобие логики
4) annotation - https://github.com/YourNorth/JavaLab5Sem/tree/master/annotation/src/main/java/com/itis/spring_boot/active_lead/annotation
    взял чужой код
5) mongoDb - репозитория нет, по понятным причинам, прикрепил код по созданию:
    "use jobboard
     db.company.insert({
      img: "/img/svg_icon/5.svg",
      name: "Twitterworks"
     });
     db.country.insert({name: "USA"});
     db.user.insert({
      email : "monagon786@wgraj.com",
      password : "$2a$10$h8qE0WCVkugr1vs6omSTbuxgjIG2B/8H9Qz0CLtgkcuRcP1nhSQi6",
     });
     db.company.update({name: "Twitterworks"}, {$set: {owner:
     ObjectId("507f191e810c19729de860e4")}});
     db.company.update({name: "Twitterworks"}, {$set: {country:
     ObjectId("507f191e810c19729de860e3")}});
     db.user.update({email: "monagon786@wgraj.com"}, {$set: {country:
     ObjectId("507f191e810c19729de860e4")}});
    "
6)   -
7)   -
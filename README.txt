1) rabbitmq - https://github.com/YourNorth/JavaLab5Sem/tree/master/rabbitmq
    по второй ссылке вариант с fanout немного лучше
2) rabbitmq with direct, topic exchanges - https://github.com/YourNorth/JavaLab5Sem/tree/master/rabbitmq_extra_func
    рефакторинга ноль(
3) hateoas - https://github.com/YourNorth/jobboard/tree/master/Job_Board
    взял немного модифицированный проект с летней практики, добавил какое-то подобие логики
4) annotation - https://github.com/YourNorth/JavaLab5Sem/tree/master/annotation/src/main/java/com/itis/spring_boot/active_lead/annotation
    взял чужой код, свой довести до рабочего состояния так и не смог
5) mongoDb - репозитория нет, по понятным причинам, прикрепил код по созданию:
    "
    use jobboard
    db.company.insertOne({
        img: "/img/svg_icon/5.svg",
        name: "Twitterworks"
    });
    db.country.insertOne({name: "USA"});
    db.user.insertOne({
        email : "monagon786@wgraj.com",
        password : "$2a$10$h8qE0WCVkugr1vs6omSTbuxgjIG2B/8H9Qz0CLtgkcuRcP1nhSQi6",
    });
    db.company.updateOne({name: "Twitterworks"}, {$set: {owner:
        ObjectId("5ff83d6435fbad3ae24b81f5")}});
    db.company.updateOne({name: "Twitterworks"}, {$set: {country:
        ObjectId("5ff83d5d35fbad3ae24b81f4")}});
    db.user.updateOne({email: "monagon786@wgraj.com"}, {$set: {country:
        ObjectId("5ff83d5d35fbad3ae24b81f4")}});
    "
6)   https://github.com/YourNorth/JavaLab5Sem/tree/master/mongodb
7)   https://github.com/YourNorth/JavaLab5Sem/tree/master/querydsl

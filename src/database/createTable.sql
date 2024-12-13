create table users
(
    id                 serial primary key,
    account_id         int unique,
    user_id            int unique,
    reputation         int,
    user_type          varchar(20),
    accept_rate        int,
    display_name       varchar(500),
    link               varchar(500)
);


create table questions
(
    id                 serial primary key,
    question_id        int unique,
    title              varchar(500),
    link               varchar(500),
    is_answered        boolean,
    view_count         int,
    accepted_answer_id int,
    answer_count       int,
    score              int,
    last_activity_date timestamp,
    creation_date      timestamp,
    last_edit_date     timestamp,
    owner_id           int,
    body               text,
    down_vote_count    int,
    up_vote_count      int,
    foreign key (owner_id) references users(user_id)
);

create table answers
(
    id                   serial primary key,
    answer_id            int unique,
    is_accepted          boolean,
    score                int,
    community_owned_date timestamp,
    last_activity_date   timestamp,
    creation_date        timestamp,
    last_edit_date       timestamp,
    question_id          int,
    owner_id             int,
    body                 text,
    foreign key (question_id) references questions(question_id),
    foreign key (owner_id) references users(user_id)
);



create table comments
(
    id                 serial primary key,
    comment_id         int unique,
    score              int,
    creation_date      timestamp,
    owner_id           int,
    reply_to_id        int,
    question_id        int,
    answer_id          int,
    edited             boolean,
    body               text,
    foreign key (question_id) references questions(question_id),
    foreign key (answer_id) references answers(answer_id),
    foreign key (owner_id) references users(user_id),
    foreign key (reply_to_id) references users(user_id)
);

create table tags
(
    tag_id             serial primary key,
    tag_name           varchar(500) unique
);

create table question_tag
(
    question_id int,
    tag_id int,
    primary key (question_id, tag_id),
    foreign key (question_id) references questions(question_id),
    foreign key (tag_id) references tags(tag_id)
)

-- DROP TABLE comments;
-- DROP TABLE answers;
-- DROP TABLE questions;
-- DROP TABLE users;

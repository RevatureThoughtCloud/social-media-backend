INSERT INTO users (id, email, password, first_name, last_name, user_name) VALUES (
    1,
    'abe@rev.com',
    'password',
    'Abraham',
    'Barboza',
    'abarboza'
);

INSERT INTO users (id, email, password, first_name, last_name, user_name) VALUES (
    2,
    'aj@rev.com',
    'password',
    'AJ',
    'Barea',
    'ajbarea'
);

INSERT INTO users (id, email, password, first_name, last_name, user_name) VALUES (
    3,
    'elian@rev.com',
    'password',
    'Elian',
    'Felix',
    'efelix'
);

INSERT INTO users (id, email, password, first_name, last_name, user_name) VALUES (
    4,
    'julio@rev.com',
    'password',
    'Julio',
    'Canales',
    'jcanales'
);

INSERT INTO users (id, email, password, first_name, last_name, user_name) VALUES (
    5,
    'seth@rev.com',
    'password',
    'Seth',
    'Evry',
    'sethevry'
);

insert into users (id, first_name, last_name, email, user_name, password) values (6, 'Kalindi', 'Grasser', 'kgrasser0@chronoengine.com', 'kgrasser0', 'password');
insert into users (id, first_name, last_name, email, user_name, password) values ( 7,'Gaby', 'Kase', 'gkase1@sfgate.com', 'gkase1', 'password');

insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (4,1);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (4,6);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (2,7);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (7,6);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (1,5);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (1,7);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (7,4);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (4,3);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (5,7);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (7,5);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (3,1);

insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (6,7);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (3,2);

insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (6,4);

insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (4,5);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (1,4);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (3,5);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (1,2);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (3,7);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (3,4);

insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (7,3);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (5,6);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (2,6);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (3,6);
insert into follows (FOLLOWED_ID , FOLLOWING_ID) values (4,7);

INSERT INTO posts (post_type, text, author_id, like_count) VALUES (
    0,
    'I''m so excited to be the first user of ThoughtCloud social!!',
    1,
    0
);

INSERT INTO posts (image_url, post_type, text, author_id, like_count) VALUES (
    'https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg',
    0,
    'Wow!! Look at this incredible tree I saw today!!',
    2,
    0
);

INSERT INTO posts (image_url, post_type, text, author_id, like_count) VALUES (
    'https://t3.ftcdn.net/jpg/02/87/90/92/360_F_287909239_K8bMpb0M9oUyOI8zrdqpKPQnIlC73WfD.jpg',
    0,
    'Check out this beautiful view from my morning walk',
    5,
    0
);

INSERT INTO posts (post_type, text, author_id, like_count) VALUES (
    0,
    'This site blows; Twitter is better',
    4,
    0
);

INSERT INTO posts (image_url, post_type, text, author_id, like_count) VALUES (
    'https://previews.123rf.com/images/lenanichizhenova/lenanichizhenova1707/lenanichizhenova170700205/82512191-bored-husband-waiting-for-wife.jpg',
    0,
    'I cant believe my wife made this stupid account for me...',
    3,
    0
);
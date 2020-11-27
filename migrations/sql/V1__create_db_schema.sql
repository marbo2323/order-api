
create table "user" (
  id serial,
  username varchar(128) not null,
  password varchar(64) not null,
  first_name varchar(128) not null,
  last_name varchar(128) not null,
  email varchar(128) not null,
  constraint user_pk primary key (id),
  constraint user_username_uk unique (username),
  constraint user_email_uk unique (email)
);


create table user_role (
	id serial,
	user_id integer not null,
	role_name varchar(16) not null,
	constraint user_role_pk primary key (id),
	constraint user_role_user_fk foreign key (user_id) references "user"(id),
	constraint user_role_uk unique (user_id, role_name)
);

create table product (
    id serial,
    name varchar(16) not null,
    description varchar(500),
    constraint product_pk primary key (id),
    constraint product_name_uk unique (name)
);


create table user_order (
    id serial,
    user_id integer not null,
    product_id integer not null,
    status varchar(16) not null,
    created_timestamp timestamp default current_timestamp not null,
    constraint user_order_pk primary key (id),
    constraint user_order_user_fk foreign key (user_id) references "user"(id),
    constraint user_order_product_fk foreign key (product_id) references product(id),
    constraint user_order_uk unique(user_id, product_id)
);
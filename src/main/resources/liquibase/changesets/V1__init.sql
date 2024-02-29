CREATE TYPE roles AS ENUM ('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR');
CREATE TYPE status AS ENUM ('REVIEW', 'NEW', 'ACTIVE', 'CLOSED');

create table if not exists users
(
    id       bigint primary key GENERATED ALWAYS AS IDENTITY,
    username text,
    password text  not null,
    email    text  not null unique,
    role     roles not null,
    rating   smallint,
    image    text
);

create table if not exists lots_groups
(
    id           bigint primary key GENERATED ALWAYS AS IDENTITY,
    creator_id   bigint    not null,
    name         text      not null,
    description  text      not null,
    created_time timestamp not null default current_timestamp,
    constraint fk_lots_groups_user foreign key (creator_id) references users (id)
);

create table if not exists categories
(
    id        bigint primary key GENERATED ALWAYS AS IDENTITY,
    name      text not null,
    parent_id bigint,
    constraint fk_categories_parent foreign key (parent_id) references categories (id)
);

create table if not exists lots
(
    id            bigint primary key GENERATED ALWAYS AS IDENTITY,
    group_id      bigint,
    seller_id     bigint         not null,
    category_id   bigint         not null,
    name          text           not null,
    description   text           not null,
    bid_increment decimal(10, 2) not null,
    start_bid     decimal(10, 2) not null,
    last_bid      decimal(10, 2),
    total_bids    integer,
    start_time    timestamp      not null,
    end_time      timestamp      not null,
    status        status         not null,
    constraint fk_lots_users foreign key (seller_id) references users (id),
    constraint fk_lots_group foreign key (group_id) references lots_groups (id),
    constraint fk_lots_category foreign key (category_id) references categories (id)
);

create table if not exists lots_images
(
    id     bigint primary key GENERATED ALWAYS AS IDENTITY,
    lot_id bigint not null,
    image  text   not null,
    constraint fk_images_lot foreign key (lot_id) references lots (id) on delete cascade on update no action
);

create table if not exists bids
(
    id       bigint primary key GENERATED ALWAYS AS IDENTITY,
    lot_id   bigint                              not null,
    user_id  bigint                              not null,
    bid      decimal(10, 2)                      not null,
    bid_time timestamp default current_timestamp not null,
    constraint fk_bids_lot foreign key (lot_id) references lots (id),
    constraint fk_bids_user foreign key (user_id) references users (id)
);

create table if not exists transactions
(
    id        bigint primary key GENERATED ALWAYS AS IDENTITY,
    lot_id    bigint                              not null,
    seller_id bigint                              not null,
    buyer_id  bigint                              not null,
    amount    decimal(10, 2)                      not null,
    deal_time timestamp default current_timestamp not null,
    constraint fk_transactions_lot foreign key (lot_id) references lots (id),
    constraint fk_transactions_seller foreign key (seller_id) references users (id),
    constraint fk_transactions_buyer foreign key (buyer_id) references users (id)
);

create table if not exists messages
(
    id           bigint primary key GENERATED ALWAYS AS IDENTITY,
    lot_id       bigint                              not null,
    sender_id    bigint                              not null,
    recipient_id bigint                              not null,
    message      text                                not null,
    send_time    timestamp default current_timestamp not null,
    constraint fk_messages_lot foreign key (lot_id) references lots (id),
    constraint fk_messages_sender foreign key (sender_id) references users (id),
    constraint fk_messages_recipient foreign key (recipient_id) references users (id)
);

create table if not exists tracking
(
    user_id bigint not null,
    lot_id  bigint not null,
    primary key (user_id, lot_id),
    constraint fk_tracking_user foreign key (user_id) references users (id),
    constraint fk_tracking_lot foreign key (lot_id) references lots (id)
);

create table if not exists comments
(
    id        bigint primary key GENERATED ALWAYS AS IDENTITY,
    parent_id bigint,
    lot_id    bigint    not null,
    user_id   bigint    not null,
    comment   text      not null,
    send_time timestamp not null default current_timestamp,
    constraint fk_comments_parent foreign key (parent_id) references comments (id),
    constraint fk_comments_lot foreign key (lot_id) references lots (id),
    constraint fk_comments_user foreign key (user_id) references users (id)
);


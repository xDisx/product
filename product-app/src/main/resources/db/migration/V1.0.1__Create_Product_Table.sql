create table if not exists "xdisx-product".products (
    "id" numeric(10) primary key,
    "product_name" varchar(255) not null ,
    "description" text not null,
    "created" timestamp not null ,
    "modified" timestamp not null ,
    "version" bigint not null
    );

create table if not exists "xdisx-product".product_compatibility  (
     "id" numeric(10) primary key,
    "device_type" VARCHAR(100) not null ,
    "product_id" numeric(10) not null ,
    "created" timestamp not null ,
    "modified" timestamp not null ,
    "version" bigint not null,
    FOREIGN KEY (product_id) REFERENCES products(id)
    );

create table if not exists "xdisx-product".duration_options(
    "id" numeric(10) primary key,
    "product_id" numeric(10) not null ,
    "years" numeric(10) not null ,
    "price" numeric(10, 2) not null ,
    "created" timestamp not null ,
    "modified" timestamp not null ,
    "version" bigint not null,
    FOREIGN KEY (product_id) REFERENCES products(id)

    );

CREATE SEQUENCE if not exists product_id_seq START 1;
CREATE SEQUENCE if not exists compatibility_id_seq START 1;
CREATE SEQUENCE if not exists duration_id_seq START 1;

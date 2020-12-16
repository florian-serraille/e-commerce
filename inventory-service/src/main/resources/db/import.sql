-- executed on startup if Hibernate creates the schema from scratch (that is, if the ddl-auto property is set to create or create-drop).

insert into PRODUCT
values (1, 'First product', 10.0);
SELECT nextval('PRODUCT_SEQ');

insert into PRODUCT
values (2, 'Second product', 20.5);
SELECT nextval('PRODUCT_SEQ');

insert into PRODUCT
select 1,
       'First product',
       10.0
where not exists(
        select 1 from PRODUCT where id = 1
    );
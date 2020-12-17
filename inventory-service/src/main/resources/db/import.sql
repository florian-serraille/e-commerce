insert into PRODUCT
values ((SELECT nextval('PRODUCT_SEQ')),
        'A product',
        10.0);

insert into PRODUCT
values ((SELECT nextval('PRODUCT_SEQ')),
        'Another product',
        20.5);
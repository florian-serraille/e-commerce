insert into PRODUCT (ID, NAME, UNIT_PRICE, BRAND_ID, CATEGORY_ID)
values ((SELECT nextval('PRODUCT_SEQ')),
        'A product',
        1,
        1,
        10.0);

insert into PRODUCT (ID, NAME, UNIT_PRICE, BRAND_ID, CATEGORY_ID)
values ((SELECT nextval('PRODUCT_SEQ')),
        'Another product',
        1,
        1,
        20.5);
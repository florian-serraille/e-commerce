insert into BRAND (ID, NAME)
values ((SELECT nextval('BRAND_SEQ')), 'A brand');

insert into CATEGORY (ID, NAME)
values ((SELECT nextval('CATEGORY_SEQ')), 'A category');

insert into PRODUCT (ID, NAME, UNIT_PRICE, BRAND_ID, CATEGORY_ID)
values ((SELECT nextval('PRODUCT_SEQ')),
        'A product',
        10.0,
        1,
        1);

insert into PRODUCT (ID, NAME, UNIT_PRICE, BRAND_ID, CATEGORY_ID)
values ((SELECT nextval('PRODUCT_SEQ')),
        'Another product',
        20.5,
        1,
        1);
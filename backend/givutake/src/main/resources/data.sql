-- Insert initial region data
INSERT INTO region (region_name, created_date, modified_date)
VALUES
('서울특별시', NOW(), NOW()),
('부산광역시', NOW(), NOW()),
('대구광역시', NOW(), NOW()),
('인천광역시', NOW(), NOW()),
('광주광역시', NOW(), NOW()),
('대전광역시', NOW(), NOW()),
('울산광역시', NOW(), NOW()),
('세종특별자치시', NOW(), NOW()),
('경기도', NOW(), NOW()),
('충청북도', NOW(), NOW()),
('충청남도', NOW(), NOW()),
('전라남도', NOW(), NOW()),
('경상북도', NOW(), NOW()),
('경상남도', NOW(), NOW()),
('제주특별자치도', NOW(), NOW()),
('강원특별자치도', NOW(), NOW()),
('전북특별자치도', NOW(), NOW());

-- Insert initial category data
INSERT INTO categories (category_name, category_type, created_date, modified_date)
values
    ('지역상품권', 0, now(), now()),
    ('농축산물', 0, now(), now()),
    ('수산물', 0, now(), now()),
    ('가공식품', 0, now(), now()),
    ('공예품', 0, now(), now()),
    ('재난재해', 1, now(), now()),
    ('지역기부', 1, now(), now());
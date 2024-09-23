-- Check if data has already been initialized
CREATE TABLE IF NOT EXISTS initialization_flag (initialized BOOLEAN);

-- Only insert data if it hasn't been initialized
INSERT INTO region (region_name, created_date, modified_date)
SELECT * FROM (
                  SELECT '서울특별시' AS region_name, NOW() AS created_date, NOW() AS modified_date
                  UNION ALL SELECT '부산광역시', NOW(), NOW()
                  UNION ALL SELECT '대구광역시', NOW(), NOW()
                  UNION ALL SELECT '인천광역시', NOW(), NOW()
                  UNION ALL SELECT '광주광역시', NOW(), NOW()
                  UNION ALL SELECT '대전광역시', NOW(), NOW()
                  UNION ALL SELECT '울산광역시', NOW(), NOW()
                  UNION ALL SELECT '세종특별자치시', NOW(), NOW()
                  UNION ALL SELECT '경기도', NOW(), NOW()
                  UNION ALL SELECT '충청북도', NOW(), NOW()
                  UNION ALL SELECT '충청남도', NOW(), NOW()
                  UNION ALL SELECT '전라남도', NOW(), NOW()
                  UNION ALL SELECT '경상북도', NOW(), NOW()
                  UNION ALL SELECT '경상남도', NOW(), NOW()
                  UNION ALL SELECT '제주특별자치도', NOW(), NOW()
                  UNION ALL SELECT '강원특별자치도', NOW(), NOW()
                  UNION ALL SELECT '전북특별자치도', NOW(), NOW()
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM initialization_flag WHERE initialized = TRUE);

-- Insert initial category data
INSERT INTO categories (category_name, category_type, created_date, modified_date)
SELECT * FROM (
                  SELECT '지역상품권' AS category_name, 0 AS category_type, NOW() AS created_date, NOW() AS modified_date
                  UNION ALL SELECT '농축산물', 0, NOW(), NOW()
                  UNION ALL SELECT '수산물', 0, NOW(), NOW()
                  UNION ALL SELECT '가공식품', 0, NOW(), NOW()
                  UNION ALL SELECT '공예품', 0, NOW(), NOW()
                  UNION ALL SELECT '재난재해', 1, NOW(), NOW()
                  UNION ALL SELECT '지역기부', 1, NOW(), NOW()
              ) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM initialization_flag WHERE initialized = TRUE);

-- Mark data as initialized if not already done
INSERT INTO initialization_flag (initialized)
SELECT TRUE
    WHERE NOT EXISTS (SELECT 1 FROM initialization_flag WHERE initialized = TRUE);
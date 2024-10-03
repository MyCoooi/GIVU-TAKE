package com.accepted.givutake.gift.repository;

import com.accepted.givutake.payment.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftPercentageRepository extends JpaRepository<Orders, Integer> {
    @Query(nativeQuery = true, value =
            """
            WITH OrderStats AS (
                SELECT 
                    o.gift_idx,
                    g.category_idx,
                    c.category_name,
                    u.is_male,
                    FLOOR(DATEDIFF(CURDATE(), u.birth) / 365) AS age,
                    COUNT(*) OVER() AS total_orders
                FROM Orders o
                JOIN Users u ON o.user_idx = u.user_idx
                JOIN Gifts g ON o.gift_idx = g.gift_idx
                JOIN Categories c ON g.category_idx = c.category_idx
            )
            SELECT 
                'category' AS stat_type,
                category_name AS name,
                COUNT(*) AS count,
                COUNT(*) / MAX(total_orders) * 100 AS percentage
            FROM OrderStats
            GROUP BY category_idx, category_name
    
            UNION ALL
    
            SELECT 
                'gender' AS stat_type,
                CASE WHEN is_male THEN 'Male' ELSE 'Female' END AS name,
                COUNT(*) AS count,
                COUNT(*) / MAX(total_orders) * 100 AS percentage
            FROM OrderStats
            GROUP BY is_male
    
            UNION ALL
    
            SELECT 
                'age' AS stat_type,
                CASE 
                    WHEN age < 30 THEN '20s'
                    WHEN age < 40 THEN '30s'
                    WHEN age < 50 THEN '40s'
                    WHEN age < 60 THEN '50s'
                    ELSE '60+'
                END AS name,
                COUNT(*) AS count,
                COUNT(*) / MAX(total_orders) * 100 AS percentage
            FROM OrderStats
            GROUP BY 
                CASE 
                    WHEN age < 30 THEN '20s'
                    WHEN age < 40 THEN '30s'
                    WHEN age < 50 THEN '40s'
                    WHEN age < 60 THEN '50s'
                    ELSE '60+'
                END
            """
    )
    List<Object[]> getOverallGiftStatistics();

    @Query(nativeQuery = true, value =
            """
            WITH OrderStats AS (
                SELECT 
                    o.gift_idx,
                    g.gift_name,
                    u.is_male,
                    FLOOR(DATEDIFF(CURDATE(), u.birth) / 365) AS age,
                    COUNT(*) OVER() AS total_orders
                FROM Orders o
                JOIN Users u ON o.user_idx = u.user_idx
                JOIN Gifts g ON o.gift_idx = g.gift_idx
                WHERE o.gift_idx = :giftIdx
            )
            SELECT 
                'gift' AS stat_type,
                gift_name AS name,
                COUNT(*) AS count,
                100.0 AS percentage
            FROM OrderStats
            GROUP BY gift_idx, gift_name
    
            UNION ALL
    
            SELECT 
                'gender' AS stat_type,
                CASE WHEN is_male THEN 'Male' ELSE 'Female' END AS name,
                COUNT(*) AS count,
                COUNT(*) / MAX(total_orders) * 100 AS percentage
            FROM OrderStats
            GROUP BY is_male
    
            UNION ALL
    
            SELECT 
                'age' AS stat_type,
                CASE 
                    WHEN age < 30 THEN '20s'
                    WHEN age < 40 THEN '30s'
                    WHEN age < 50 THEN '40s'
                    WHEN age < 60 THEN '50s'
                    ELSE '60+'
                END AS name,
                COUNT(*) AS count,
                COUNT(*) / MAX(total_orders) * 100 AS percentage
            FROM OrderStats
            GROUP BY 
                CASE 
                    WHEN age < 30 THEN '20s'
                    WHEN age < 40 THEN '30s'
                    WHEN age < 50 THEN '40s'
                    WHEN age < 60 THEN '50s'
                    ELSE '60+'
                END
            """
    )
    List<Object[]> getGiftStatisticsByGiftId(@Param("giftIdx") Integer giftIdx);
}

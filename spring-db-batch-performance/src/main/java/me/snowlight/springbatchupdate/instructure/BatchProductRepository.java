package me.snowlight.springbatchupdate.instructure;

import me.snowlight.springbatchupdate.domain.Product;
import me.snowlight.springbatchupdate.domain.ProductRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class BatchProductRepository implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public BatchProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public void saveAll(List<Product> products) {
        String sql = "INSERT INTO product (title, created_ts, price)  VALUES (?, ? ,?)";

        jdbcTemplate.batchUpdate(sql,
                products,
                100,
                (ps, product) -> {
                    ps.setString(1, product.getTitle());
                    ps.setTimestamp(2, Timestamp.valueOf(product.getCreatedTs()));
                    ps.setBigDecimal(3, product.getPrice());
                }
        );
    }
}

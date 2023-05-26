package me.snowlight.springbatchupdate.instructure;

import lombok.RequiredArgsConstructor;
import me.snowlight.springbatchupdate.domain.Product;
import me.snowlight.springbatchupdate.domain.ProductRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SimpleProductRepository implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public void saveAll(List<Product> products) {

        String sql = "insert into product (title, created_ts, price) values (?, ?, ?)";
        for (Product product : products) {
            this.jdbcTemplate.update(sql,
                ps -> {
                    ps.setString(1, product.getTitle());
                    ps.setTimestamp(2, Timestamp.valueOf(product.getCreatedTs()));
                    ps.setBigDecimal(3, product.getPrice());
                }
            );
        }
    }
}

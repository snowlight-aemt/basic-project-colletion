package me.snowlight.springbatchupdate.domain;

import java.util.List;

public interface ProductRepository {
    void saveAll(List<Product>products);
}

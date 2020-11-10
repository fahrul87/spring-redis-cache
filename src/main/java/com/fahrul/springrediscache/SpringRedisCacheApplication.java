package com.fahrul.springrediscache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fahrul.springrediscache.model.Product;
import com.fahrul.springrediscache.repository.ProductDao;

@SpringBootApplication
@RestController
@RequestMapping("/product")
@EnableCaching
public class SpringRedisCacheApplication {
	
	@Autowired
    private ProductDao dao;

    @PostMapping
    public Product save(@RequestBody Product product) {
        return dao.save(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return dao.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id",value = "Product",unless = "#result.price > 1000")
    public Product findProduct(@PathVariable int id) {
        return dao.findProductById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id",value = "Product")
    public String remove(@PathVariable int id) {
        return dao.deleteProduct(id);
    }

	public static void main(String[] args) {
		SpringApplication.run(SpringRedisCacheApplication.class, args);
	}

}

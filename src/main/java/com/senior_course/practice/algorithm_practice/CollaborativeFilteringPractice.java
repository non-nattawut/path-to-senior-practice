package com.senior_course.practice.algorithm_practice;

import java.util.*;

public class CollaborativeFilteringPractice {
    public static void main(String[] args) {
        Product product1 = new Product(UUID.randomUUID(), "Product1", null);
        Product product2 = new Product(UUID.randomUUID(), "Product2", List.of(product1));
        Product product3 = new Product(UUID.randomUUID(), "Product3", List.of(product1));
        Product product4 = new Product(UUID.randomUUID(), "Product4", List.of(product2, product3));
        Product product5 = new Product(UUID.randomUUID(), "Product5", List.of(product3, product4));
        Product product6 = new Product(UUID.randomUUID(), "Product6", List.of(product2));
        Product product7 = new Product(UUID.randomUUID(), "Product7", List.of(product6));
        Product product8 = new Product(UUID.randomUUID(), "Product8", List.of(product5, product7));

        List<Product> results = getRelatedProductBy(product8, 10);
        System.out.println("============================");
        for (Product result : results) {
            System.out.println(result);
        }
    }

    public static List<Product> getRelatedProductBy(Product product, Integer max) {
        List<Product> result = new ArrayList<>();
        Set<Product> resultSet = new HashSet<>();

        if (product != null && product.relatedProducts() != null){
            result.addAll(product.relatedProducts());
            resultSet.addAll(product.relatedProducts());
        }

        for (int i = 0; i < result.size() && result.size() < max; i++) {
            List<Product> relatedProducts = result.get(i).relatedProducts();
            if (relatedProducts == null) continue;
            for (Product temp : relatedProducts) {
                if (temp != null && !resultSet.contains(temp)) {
                    result.add(temp);
                    resultSet.add(temp);
                }
            }
        }

        return result;
    }

    record Product(UUID id, String name, List<Product> relatedProducts) {
    }
}
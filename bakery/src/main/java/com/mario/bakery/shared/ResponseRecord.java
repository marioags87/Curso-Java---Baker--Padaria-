package com.mario.bakery.shared;

import com.mario.bakery.model.Product;

public record ResponseRecord(Product product, String message) {
    
}

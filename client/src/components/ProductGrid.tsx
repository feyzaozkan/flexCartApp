import React from "react";
import { Card, CardContent, Typography, CardActions, Button } from "@mui/material";

interface Product {
    id: string;
    name: string;
    price: number;
    category: string;
}

interface ProductGridProps {
    products: Product[];
    onAddToCart: (product: Product) => void;
}

const ProductGrid: React.FC<ProductGridProps> = ({ products, onAddToCart }) => {
    return (
        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 p-1 overflow-auto max-h-[80vh] rounded-lg">
            {products.map((product) => (
                <Card key={product.id} className="flex flex-col justify-between" style={{backgroundColor: "#f2d3ab"}}>
                    <CardContent>
                        <Typography variant="h6" component="div">
                            {product.name}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            {product.category}
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            ${product.price.toFixed(2)}
                        </Typography>
                    </CardContent>
                    <CardActions>
                        <Button
                            style={{backgroundColor: "#8b6d9c", color: "#fbf5ef"}}
                            size="small"
                            variant="contained"
                            fullWidth
                            onClick={() => onAddToCart(product)}
                        >
                            Add to Cart
                        </Button>
                    </CardActions>
                </Card>
            ))}
        </div>
    );
};

export default ProductGrid;
import { Component, OnInit } from '@angular/core';
import { CartService } from 'src/app/service/Cart/cart.service';
import { OrderService } from 'src/app/service/Order/order.service';
import { CartItem } from 'src/app/models/cart-item.model';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  userId: number = 0;

  constructor(private cartService: CartService, private orderService: OrderService, private router: Router) { }

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    this.cartItems = this.cartService.getCart();
    const user = JSON.parse(localStorage.getItem('user')!);
    this.userId = user.userId;
  }

  removeFromCart(foodId: number): void {
    this.cartService.removeFromCart(foodId);
    this.loadCart();
  }

  getTotalPrice(): number {
    return this.cartItems.reduce((total, item) => total + item.food.price * item.quantity, 0);
  }

  getTotalPrice1(items: CartItem[]): number {
    return items.reduce((total, item) => total + item.food.price * item.quantity, 0);
  }

  async finalizeOrder() {
    const processedRestaurantIds: Set<number> = new Set();
    const ordersToSend = [];

    try {
      for (const item of this.cartItems) {
        const restaurantId = item.food.restaurantId.restaurantId;

        if (!processedRestaurantIds.has(restaurantId)) {
          const itemsForRestaurant = this.cartItems.filter(cartItem => cartItem.food.restaurantId.restaurantId === restaurantId);
          const totalAmount = this.getTotalPrice1(itemsForRestaurant);

          const orderDto: any = {
            userId: this.userId,
            restaurantId: restaurantId,
            totalAmount: totalAmount,
            status: 'Pending',
            orderItems: itemsForRestaurant.map(cartItem => ({
              menuItemId: cartItem.food.itemId,
              quantity: cartItem.quantity,
              price: cartItem.food.price
            }))
          };

          console.log('Order DTO:', JSON.stringify(orderDto, null, 2)); // Ispis u konzoli

          ordersToSend.push(orderDto);
          processedRestaurantIds.add(restaurantId);
        }
      }

      // Send all orders
      for (const orderDto of ordersToSend) {
        let response = await firstValueFrom(this.orderService.createOrder(orderDto));
        if (response === "Error creating order.") {
          throw new Error("Error creating order.");
        }
      }

      alert('Order(s) successfully created.');
      this.cartService.clearCart();
      this.cartService.initializeCartForUser(this.userId);
      this.cartItems = this.cartService.getCart();
      // this.router.navigate(['/order-confirmation']);
    } catch (error) {
      console.error('Failed to create order:', error);
      alert('Failed to create order. Please try again.');
    }
  }
}

import { Injectable } from '@angular/core';
import { CartItem } from 'src/app/models/cart-item.model';
import { Food } from 'src/app/models/food.model';


@Injectable({
  providedIn: 'root'
})
export class CartService {
  private storageKey = 'cart';
  private userId: number = 0;

  constructor() {}

  initializeCartForUser(userId: number): void {
    this.userId = userId;
    const cartKey = `${this.storageKey}-${this.userId}`;
    if (!localStorage.getItem(cartKey)) {
      localStorage.setItem(cartKey, JSON.stringify([]));
    }
  }

  getCart(): CartItem[] {
    const cart = localStorage.getItem(`${this.storageKey}-${this.userId}`);
    return cart ? JSON.parse(cart) : [];
  }

  saveCart(cart: CartItem[]): void {
    localStorage.setItem(`${this.storageKey}-${this.userId}`, JSON.stringify(cart));
  }

  addToCart(food: Food, quantity: number): void {
    const cart = this.getCart();
    const index = cart.findIndex(item => item.food.id === food.id);
    if (index >= 0) {
      cart[index].quantity += quantity;
    } else {
      cart.push({ food, quantity });
    }
    this.saveCart(cart);
  }

  removeFromCart(foodId: number): void {
    let cart = this.getCart();
    cart = cart.filter(item => item.food.id !== foodId);
    this.saveCart(cart);
  }

  clearCart(): void {
    localStorage.removeItem(`${this.storageKey}-${this.userId}`);
  }
}

import { Restaurant } from "../entities/Restaurant";

export interface Food {
  itemId: number;
  name: string;
  description: string;
  price: number;
  image: string; // Base64 string
  createdAt: Date;
  restaurantId: Restaurant;
  quantity: number |null; // Dodato za prikaz količine
}
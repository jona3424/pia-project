export interface Food {
  id: number;
  name: string;
  description: string;
  price: number;
  image: string; // Base64 string
  createdAt: Date;
  restaurantId: number;
  quantity: number; // Dodato za prikaz količine
}
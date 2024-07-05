export class Restaurant{
        restaurantId: number;
        name: string;
        type: string;
        address: string;
        description: string;
        contactPerson: string;
        phoneNumber: string;
        email: string;
        createdAt: string;
        reservationsList: any[];
        reviewsList: any[];
        menuItemsList: any[];
        ordersList: any[];
        restaurantTablesList: any[];

        constructor(data: any) {
            this.restaurantId = data.restaurantId;
            this.name = data.name;
            this.type = data.type;
            this.address = data.address;
            this.description = data.description;
            this.contactPerson = data.contactPerson;
            this.phoneNumber = data.phoneNumber;
            this.email = data.email;
            this.createdAt = data.createdAt;
            this.reservationsList = data.reservationsList;
            this.reviewsList = data.reviewsList;
            this.menuItemsList = data.menuItemsList;
            this.ordersList = data.ordersList;
            this.restaurantTablesList = data.restaurantTablesList;
        }
}
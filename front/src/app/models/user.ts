import { Fav } from "./fav"

export class User{
  _id: string = ""
  firstname: string = ""
  lastname: string = ""
  username: string = ""
  password: string = ""
  favourites: Fav[] = []
}

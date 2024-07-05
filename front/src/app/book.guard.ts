import { CanActivateFn } from '@angular/router';

export const bookGuard: CanActivateFn = (route, state) => {
  if(
    localStorage.getItem("logged")!=null
  ) return true;
  else {
    alert("No")
    return false;
  }
};

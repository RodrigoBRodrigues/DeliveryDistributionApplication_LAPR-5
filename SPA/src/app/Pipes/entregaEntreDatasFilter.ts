import { Pipe, PipeTransform } from '@angular/core';
import { Entrega } from '../Model/entrega';

@Pipe({
name: 'searchEntreDatas',
pure: false
})
export class EntregaEntreDatasFilter implements PipeTransform {

  transform(value: Entrega[], date1: string, date2: string) {
    if(!date1){
      return value;
}
    console.log(new Date(date1).toDateString());
    console.log(new Date(date2).toDateString());
    return value.filter((entrega)=> new Date(entrega.data).getTime() >= new Date(date1).getTime() && new Date(entrega.data).getTime() <= new Date(date2).getTime())
}


}

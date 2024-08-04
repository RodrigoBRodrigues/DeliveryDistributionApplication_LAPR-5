import { Pipe, PipeTransform } from '@angular/core';
import { Entrega } from '../Model/entrega';

@Pipe({
name: 'searchEntreDataMenor',
pure: false
})
export class EntregaDataMenorFilter implements PipeTransform {

  transform(value: Entrega[], date2: string) {
    if(!date2){
      return value;
}
    console.log(date2)
    return value.filter((entrega)=> entrega.data <= new Date(date2))
}


}

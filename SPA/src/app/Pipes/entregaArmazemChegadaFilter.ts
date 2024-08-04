import { Pipe, PipeTransform } from '@angular/core';
import { Entrega } from '../Model/entrega';

@Pipe({
name: 'searchArmazemChegada',
pure: false
})
export class EntregaArmazemChegadaFilter implements PipeTransform {

  transform(value: Entrega[], armazem: string) {
    if(!armazem){
      return value;
}
    console.log(armazem)
    return value.filter((entrega)=> entrega.armazemDesignacao == armazem)

}

}

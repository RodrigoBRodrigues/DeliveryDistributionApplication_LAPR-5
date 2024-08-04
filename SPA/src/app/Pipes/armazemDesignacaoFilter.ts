import { Pipe, PipeTransform } from '@angular/core';
import { Armazem } from '../Model/armazem';

@Pipe({
name: 'searchArmazemDesignacao',
pure: false
})
export class ArmazemDesignacaoPipe implements PipeTransform {

  transform(value: Armazem[], designacao: string) {
    if(!designacao){
      return value;
}
    console.log(designacao)
    return value.filter((armazem)=> armazem.designacao.toLowerCase().startsWith(designacao.toLowerCase()))
   
}

}
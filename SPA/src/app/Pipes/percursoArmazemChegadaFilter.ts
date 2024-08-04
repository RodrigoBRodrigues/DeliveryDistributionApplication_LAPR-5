import { Pipe, PipeTransform } from '@angular/core';
import { Percurso } from '../Model/percurso';

@Pipe({
name: 'searchPercursoArmazemChegada',
pure: false
})

export class PercursoArmazemChegadaFilter implements PipeTransform {

  transform(value: Percurso[], armazem: string) {
    if(!armazem){
      return value;
}
    console.log(armazem)
    return value.filter((percurso)=> percurso.armazemChegada == armazem)

}

}
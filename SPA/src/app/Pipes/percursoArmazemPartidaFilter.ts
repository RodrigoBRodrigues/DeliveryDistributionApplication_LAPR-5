import { Pipe, PipeTransform } from '@angular/core';
import { Percurso } from '../Model/percurso';

@Pipe({
name: 'searchPercursoArmazemPartida',
pure: false
})
export class PercursoArmazemPartidaFilter implements PipeTransform {

  transform(value: Percurso[], armazem: string) {
    if(!armazem){
      return value;
}
    console.log(armazem)
    return value.filter((percurso)=> percurso.armazemPartida == armazem)

}

}
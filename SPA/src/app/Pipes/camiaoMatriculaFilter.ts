import { Pipe, PipeTransform } from '@angular/core';
import { Camiao} from '../Model/camiao';

@Pipe({
name: 'searchCamiaoMatricula',
pure: false
})
export class CamiaoMatriculaPipe implements PipeTransform {

    transform(value: Camiao[], matricula: string) {
      if(!matricula){
        return value;
  }
      console.log(matricula)
      return value.filter((camiao)=> camiao.matricula.toLowerCase().startsWith(matricula.toLowerCase()))
      
     
  }
  
}
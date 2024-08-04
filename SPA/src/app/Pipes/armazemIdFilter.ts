import { Pipe, PipeTransform } from '@angular/core';
import { Armazem } from '../Model/armazem';

@Pipe({
name: 'searchArmazemId',
pure: false
})
export class ArmazemIdPipe implements PipeTransform {

  transform(value: Armazem[], id: string) {
    if(!id){
      return value;
}
    console.log(id)
    return value.filter((armazem)=> armazem.id == id)
   
}

}
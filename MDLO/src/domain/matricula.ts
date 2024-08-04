import { ValueObject } from "../core/domain/ValueObject";
import { Result } from "../core/logic/Result";
import { Guard } from "../core/logic/Guard";

interface matriculaProps {
    value:string
}

export class Matricula extends ValueObject<matriculaProps>{
    get value (): string {
        return this.props.value;
      }

      private constructor (props: matriculaProps) {
        super(props);
      }

      public static create (matricula: string): Result<Matricula> {
        
        const guardResult = Guard.againstNullOrUndefined(matricula, 'matricula');
        let regExp2021= new RegExp('[A-Z]{2}[0-9]{2}[A-Z]{2}');
        regExp2021.test(matricula);
        let regExp2005= new RegExp('[0-9]{2}-[A-Z]{2}-[0-9]{2}');
        regExp2005.test(matricula);
        let regExp1992= new RegExp('[0-9]{2}-[0-9]{2}-[A-Z]{2}');
        regExp1992.test(matricula);
        let regExp1992Antes= new RegExp('[A-Z]{2}-[0-9]{2}-[0-9]{2}');
        regExp1992Antes.test(matricula);
        if(regExp2021.test(matricula)==true||regExp2005.test(matricula)==true||regExp1992.test(matricula)==true||regExp1992Antes.test(matricula)===true ){

        
        

        if (!guardResult.succeeded) {
          return Result.fail<Matricula>(guardResult.message);
        } else {
          return Result.ok<Matricula>(new Matricula({ value: matricula }))
        }
        }else return Result.fail<Matricula>(guardResult.message);
      }
}
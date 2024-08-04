import { ValueObject } from "../core/domain/ValueObject";
import { Result } from "../core/logic/Result";
import { Guard } from "../core/logic/Guard";

interface dadosCamiaoProps {
    tara: number;
    capacidade:number;
    carga:number;
    autonomia:number;
    tempo:number; /*minutos*/
}

export class dadosCamiao extends ValueObject<dadosCamiaoProps> {
    getValue: dadosCamiao;

    

    get tara(): number {
        return this.props.tara;
    }
    get capacidade():number{
        return this.props.capacidade;
    }

    get carga():number{
        return this.props.carga;
    }

    get autonomia():number{
        return this.props.autonomia;
    }

    get tempo():number{
        return this.props.tempo;
    }

    set tara(value:number){
        this.tara=value;
      }

      set capacidade(value:number){
        this.capacidade=value;
      }

      set carga(value:number){
        this.carga=value;
      }
      set autonomia(value:number){
        this.autonomia=value;
      }
      set tempo(value:number){
        this.tempo=value;
      }
    public constructor(props: dadosCamiaoProps) {
        super(props);
    }
    public static  create(tara:number,capacidade:number,carga:number,autonomia:number,tempo:number): Result<dadosCamiao> {
        const guardedProps = [
            { argument: tara, argumentName: 'tara' },
            { argument: capacidade, argumentName: 'capacidade' },
            { argument: carga, argumentName: 'carga' },
            { argument: autonomia, argumentName: 'autonomia' },
            { argument: tempo, argumentName: 'tempo' }
          ];

          const guardResult = Guard.againstNullOrUndefinedBulk(guardedProps);
        if(!guardResult.succeeded){
            return Result.fail<dadosCamiao>(guardResult.message);
        }else{
            return Result.ok<dadosCamiao>(new dadosCamiao({tara:tara,capacidade:capacidade,carga:carga,autonomia:autonomia,tempo:tempo}));
        }
       
    }
   

}

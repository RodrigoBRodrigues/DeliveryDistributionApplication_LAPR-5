/* eslint-disable prettier/prettier */
/* eslint-disable @typescript-eslint/adjacent-overload-signatures */
import PlaneamentoController from '../controllers/planeamentoController';
import { AggregateRoot } from '../core/domain/AggregateRoot';
import { UniqueEntityID } from '../core/domain/UniqueEntityID';
import { Guard } from '../core/logic/Guard';
import { Result } from '../core/logic/Result';
import { ICamiaoDTO } from '../dto/ICamiaoDTO';
import { IPlaneamentoDTO } from '../dto/IPlaneamentoDTO';
import { CamiaoId } from './camiaoID';
import { dadosCamiao } from './dadosCamiao';
import { Matricula } from './matricula';
import { PlaneamentoId } from './planeamentoID';

interface PlaneamentoProps {
  dia: number;

  camiao: string;

  curso: string[];
}

export class Planeamento extends AggregateRoot<PlaneamentoProps> {
  get id(): UniqueEntityID {
    return this._id;
  }

  get planeamentoID(): PlaneamentoId {
    return new PlaneamentoId(this.planeamentoID.toValue());
  }

  get dia() {
    return this.props.dia;
  }

  get camiao() {
    return this.props.camiao;
  }

  get curso() {
    return this.props.curso;
  }

  set dia(value: number) {
    this.props.dia = value;
  }

  set camiao(value: string) {
    this.props.camiao = value;
  }
  set curso(value: string[]) {
    this.props.curso = value;
  }

  private constructor(props: PlaneamentoProps, id?: UniqueEntityID) {
    super(props, id);
  }

  /* public static create(props:CamiaoProps,id?:UniqueEntityID) :Result<Camiao>{
        const guardResult = Guard.againstNullOrUndefined(props.dados,'dados');
        if (!guardResult.succeeded) {
            return Result.fail<Camiao>(guardResult.message)
          }
          else {
            const camiao = new Camiao({
              ...props
            }, id);

            return Result.ok<Camiao>(camiao);
          }

    }*/
  public static create(planeamentoDTO: IPlaneamentoDTO, id?: UniqueEntityID): Result<Planeamento> {
    const planeamento = new Planeamento({
      dia: planeamentoDTO.dia,
      camiao: planeamentoDTO.camiao,
      curso: planeamentoDTO.curso,
    });

    return Result.ok<Planeamento>(planeamento);
  }
}

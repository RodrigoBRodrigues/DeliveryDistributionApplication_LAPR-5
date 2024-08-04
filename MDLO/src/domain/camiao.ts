/* eslint-disable @typescript-eslint/adjacent-overload-signatures */
import { AggregateRoot } from '../core/domain/AggregateRoot';
import { UniqueEntityID } from '../core/domain/UniqueEntityID';
import { Guard } from '../core/logic/Guard';
import { Result } from '../core/logic/Result';
import { ICamiaoDTO } from '../dto/ICamiaoDTO';
import { CamiaoId } from './camiaoID';
import { dadosCamiao } from './dadosCamiao';
import { Matricula } from './matricula';

interface CamiaoProps {
  matricula: Matricula;
  caracteristica: string;
  dados: dadosCamiao;
  ativo: boolean;
}

export class Camiao extends AggregateRoot<CamiaoProps> {
  get id(): UniqueEntityID {
    return this._id;
  }

  get camiaoID(): CamiaoId {
    return new CamiaoId(this.camiaoID.toValue());
  }

  get matricula(): Matricula {
    return this.props.matricula;
  }

  get dados() {
    return this.props.dados;
  }

  get caracteristica() {
    return this.props.caracteristica;
  }
  get ativo() {
    return this.props.ativo;
  }

  set caracteristica(value: string) {
    this.props.caracteristica = value;
  }

  set tara(value: number) {
    this.props.dados.tara = value;
  }
  set dados(value: dadosCamiao) {
    this.props.dados = value;
  }
  set ativo(value: boolean) {
    this.props.ativo = value;
  }

  private constructor(props: CamiaoProps, id?: UniqueEntityID) {
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
  public static create(camiaoDTO: ICamiaoDTO, id?: UniqueEntityID): Result<Camiao> {
    const matriculaProps = Matricula.create(camiaoDTO.matricula).getValue();

    const dadosCamiaoProps = new dadosCamiao({
      tara: camiaoDTO.tara,
      capacidade: camiaoDTO.capacidade,
      carga: camiaoDTO.carga,
      autonomia: camiaoDTO.autonomia,
      tempo: camiaoDTO.tempo,
    });

    const camiao = new Camiao(
      {
        matricula: matriculaProps,
        caracteristica: camiaoDTO.caracteristica,
        dados: dadosCamiaoProps,
        ativo: camiaoDTO.ativo,
      },
      id,
    );

    return Result.ok<Camiao>(camiao);
  }
}

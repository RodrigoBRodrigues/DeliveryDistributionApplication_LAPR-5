import { Container } from 'typedi';

import { Mapper } from '../core/infra/Mapper';

import { ICamiaoDTO } from '../dto/ICamiaoDTO';

import { Camiao } from '../domain/camiao';
import { UniqueEntityID } from '../core/domain/UniqueEntityID';
import { dadosCamiao } from '../domain/dadosCamiao';
import { ICamiaoPersistence } from '../dataschema/ICamiaoPersistence';
import { Document, Model } from 'mongoose';

export class CamiaoMap extends Mapper<Camiao> {
  public static toDTO(Camiao: Camiao): ICamiaoDTO {
    return {
      id: Camiao.id.toString(),
      matricula: Camiao.matricula.value,
      caracteristica: Camiao.caracteristica,
      tara: Camiao.dados.tara,
      capacidade: Camiao.dados.capacidade,
      carga: Camiao.dados.carga,
      autonomia: Camiao.dados.autonomia,
      tempo: Camiao.dados.tempo,
      ativo: Camiao.ativo,
    } as ICamiaoDTO;
  }

  /*public static async  toDomain (raw: any): Promise<Camiao> {

    const dadosOrError = dadosCamiao.create(raw.tara,raw.capacidade,raw.carga,raw.autonomia,raw.tempo);


    const CamiaoOrError = Camiao.create(
      raw,

    );

    CamiaoOrError.isFailure ? console.log(CamiaoOrError.error) : '';

    return CamiaoOrError.isSuccess ? CamiaoOrError.getValue() : null;
  }*/

  public static toDomain(camiao: any | Model<ICamiaoPersistence & Document>): Camiao {
    const camiaoOrError = Camiao.create(camiao, new UniqueEntityID(camiao.domainId));
    camiaoOrError.isFailure ? console.log(camiaoOrError.error) : '';
    return camiaoOrError.isSuccess ? camiaoOrError.getValue() : null;
  }

  public static toPersistence(Camiao: Camiao): any {
    const a = {
      domainId: Camiao.id.toString(),
      matricula: Camiao.matricula.value,
      caracteristica: Camiao.caracteristica,
      tara: Camiao.dados.tara,
      capacidade: Camiao.dados.capacidade,
      carga: Camiao.dados.carga,
      autonomia: Camiao.dados.autonomia,
      tempo: Camiao.dados.tempo,
      ativo: Camiao.ativo,
    };
    return a;
  }
}

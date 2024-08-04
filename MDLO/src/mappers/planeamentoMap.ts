import { Container } from 'typedi';

import { Mapper } from '../core/infra/Mapper';

import { IPlaneamentoDTO } from '../dto/IPlaneamentoDTO';

import { Planeamento } from '../domain/planeamento';
import { UniqueEntityID } from '../core/domain/UniqueEntityID';

import { IPlaneamentoPersistence } from '../dataschema/IPlaneamentoPersistence';
import { Document, Model } from 'mongoose';

export class PlaneamentoMap extends Mapper<Planeamento> {
  public static toDTO(Planeamento: Planeamento): IPlaneamentoDTO {
    return {
      id: Planeamento.id.toString(),
      dia: Planeamento.dia,
      camiao: Planeamento.camiao,
      curso: Planeamento.curso,
    } as IPlaneamentoDTO;
  }

  /*public static async  toDomain (raw: any): Promise<Planeamento> {

    const dadosOrError = dadosPlaneamento.create(raw.tara,raw.capacidade,raw.carga,raw.autonomia,raw.tempo);


    const PlaneamentoOrError = Planeamento.create(
      raw,

    );

    PlaneamentoOrError.isFailure ? console.log(PlaneamentoOrError.error) : '';

    return PlaneamentoOrError.isSuccess ? PlaneamentoOrError.getValue() : null;
  }*/

  public static toDomain(planeamento: any | Model<IPlaneamentoPersistence & Document>): Planeamento {
    const PlaneamentoOrError = Planeamento.create(planeamento, new UniqueEntityID(planeamento.domainId));
    PlaneamentoOrError.isFailure ? console.log(PlaneamentoOrError.error) : '';
    return PlaneamentoOrError.isSuccess ? PlaneamentoOrError.getValue() : null;
  }

  public static toPersistence(Planeamento: Planeamento): any {
    const a = {
      domainId: Planeamento.id.toString(),
      dia: Planeamento.dia,
      camiao: Planeamento.camiao,
      curso: Planeamento.curso,
    };
    return a;
  }
}

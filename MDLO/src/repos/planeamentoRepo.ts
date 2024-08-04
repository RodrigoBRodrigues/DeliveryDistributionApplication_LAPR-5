/* eslint-disable prettier/prettier */
import { Service, Inject } from 'typedi';

import { Document, FilterQuery, Model } from 'mongoose';
import { IPlaneamentoPersistence } from '../dataschema/IPlaneamentoPersistence';

import IPlaneamentoRepo from '../services/IRepos/IPlaneamentoRepo';
import { Planeamento } from '../domain/planeamento';

import { PlaneamentoMap } from '../mappers/planeamentoMap';

import { PlaneamentoId } from '../domain/planeamentoID';
import { Matricula } from '../domain/matricula';

@Service()
export default class PlaneamentoRepo implements IPlaneamentoRepo {
  private models: any;

  constructor(
    @Inject('planeamentoSchema') private PlaneamentoSchema: Model<IPlaneamentoPersistence & Document>,
    @Inject('logger') private logger,
  ) {}

  private createBaseQuery(): any {
    return {
      where: {},
    };
  }

  public async exists(Planeamento: Planeamento): Promise<boolean> {
    const idX = Planeamento.id instanceof PlaneamentoId ? (<PlaneamentoId>Planeamento.id).toValue() : Planeamento.id;

    const query = { domainId: idX };
    const PlaneamentoDocument = await this.PlaneamentoSchema.findOne(query);

    return !!PlaneamentoDocument === true;
  }

  public async save(Planeamento: Planeamento): Promise<Planeamento> {
    const query = { id: Planeamento.id.toString() };

    const PlaneamentoDocument = await this.PlaneamentoSchema.findOne(query);

    try {
      if (PlaneamentoDocument === null) {
        const rawPlaneamento: any = PlaneamentoMap.toPersistence(Planeamento);

        const PlaneamentoCreated = await this.PlaneamentoSchema.create(rawPlaneamento);

        return PlaneamentoMap.toDomain(PlaneamentoCreated);
      } else {
        PlaneamentoDocument.id = Planeamento.id;
        PlaneamentoDocument.dia = Planeamento.dia;
        PlaneamentoDocument.camiao = Planeamento.camiao;
        PlaneamentoDocument.curso = Planeamento.curso;

        await PlaneamentoDocument.save();

        return Planeamento;
      }
    } catch (err) {
      throw err;
    }
  }
  public async create(Planeamento: Planeamento): Promise<Planeamento> {
    const query = { id: Planeamento.id.toString() };

    const PlaneamentoDocument = await this.PlaneamentoSchema.findOne(query);

    try {
      if (PlaneamentoDocument === null) {
        const rawPlaneamento: any = PlaneamentoMap.toPersistence(Planeamento);

        const PlaneamentoCreated = await this.PlaneamentoSchema.create(rawPlaneamento);

        return PlaneamentoMap.toDomain(PlaneamentoCreated);
      } else {
        return null;
      }
    } catch (err) {
      throw err;
    }
  }

  public async deletePlaneamentoByMatricula(matricula: string): Promise<Planeamento> {
    const query = { matricula: matricula };
    const PlaneamentoRecord = await this.PlaneamentoSchema.findOne(
      query as FilterQuery<IPlaneamentoPersistence & Document>,
    );

    if (PlaneamentoRecord === null) {
      return null;
    } else {
      PlaneamentoRecord.delete();

      return PlaneamentoMap.toDomain(PlaneamentoRecord);
    }
  }

  public async findById(PlaneamentoId: PlaneamentoId | string): Promise<Planeamento> {
    const query = { domainId: PlaneamentoId };
    const PlaneamentoRecord = await this.PlaneamentoSchema.findOne(
      query as FilterQuery<IPlaneamentoPersistence & Document>,
    );

    if (PlaneamentoRecord != null) {
      return PlaneamentoMap.toDomain(PlaneamentoRecord);
    } else return null;
  }

  public async findAll(): Promise<Planeamento[]> {
    const query = {};
    const PlaneamentoRecord = await this.PlaneamentoSchema.find(
      query as FilterQuery<IPlaneamentoPersistence & Document>,
    );

    if (PlaneamentoRecord != null) {
      const array: Planeamento[] = [];
      PlaneamentoRecord.forEach(element => {
        const elemnto = PlaneamentoMap.toDomain(element);

        array.push(elemnto);
      });
      return array;
    } else return null;
  }
}

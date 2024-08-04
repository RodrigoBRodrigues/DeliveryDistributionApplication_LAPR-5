import { Service, Inject } from 'typedi';

import { Document, FilterQuery, Model } from 'mongoose';
import { IPercursoPersistence } from '../dataschema/IPercursoPersistence';

import IPercursoRepo from "../services/IRepos/IPercursoRepo";
import { Percurso } from "../domain/Percursos/percurso";

import { PercursoMap } from "../mappers/percursoMap";


@Service()
export default class PercursoRepo implements IPercursoRepo {
  private models: any;

  constructor(
    @Inject('percursoSchema') private PercursoSchema : Model<IPercursoPersistence & Document>,
    @Inject('logger') private logger
  ) { }

  private createBaseQuery (): any {
    return {
      where: {},
    }
  }

  public async exists (percurso: Percurso): Promise<boolean> {

    const idX = percurso.id;

    const query = { domainId: idX};
    const PercursoDocument = await this.PercursoSchema.findOne( query );

    return !!PercursoDocument === true;
  }

  public async save (Percurso: Percurso): Promise<Percurso> {
    const query = { domainId: Percurso.id.toString() };

    const PercursoDocument = await this.PercursoSchema.findOne( query );

    try {
      if (PercursoDocument === null ) {
        const rawPercurso: any = PercursoMap.toPersistence(Percurso);

        const PercursoCreated = await this.PercursoSchema.create(rawPercurso);

        return PercursoMap.toDomain(PercursoCreated);
      } else {
        PercursoDocument.armazemPartida = Percurso.armazemPartida;
        PercursoDocument.armazemChegada= Percurso.armazemChegada;
        PercursoDocument.distancia = Percurso.distancia;
        PercursoDocument.tempo = Percurso.tempo;
        PercursoDocument.energiaGasta = Percurso.energiaGasta;
        PercursoDocument.tempoExtra = Percurso.tempoExtra;
        await PercursoDocument.save();

        return Percurso;
      }
    } catch (err) {
      throw err;
    }
  }

  public async findById (percursoId: string | string): Promise<Percurso> {
    const query = { _id: percursoId};
    const percursoRecord = await this.PercursoSchema.findOne( query as FilterQuery<IPercursoPersistence & Document> );

    if( percursoRecord != null) {
      return PercursoMap.toDomain(percursoRecord);
    }
    else
      return null;
  }

  public async findEntreArmazens(startPoint:string, endPoint:string): Promise<Percurso[]> {
    const query = { startPoint: startPoint, endPoint };
    const PercursoRecord= await this.PercursoSchema.find(query as FilterQuery<IPercursoPersistence & Document>);

    if(PercursoRecord!=null){
      let array:  Percurso[] = [];
      PercursoRecord.forEach(async (element)=>{
        let perc = await PercursoMap.toDomain(element);

        array.push(perc)
      });
      ////////////////////////////
      return array;
    }else return null;
  }

  public async findPrimeiroEntreArmazens(startPoint:string, endPoint:string): Promise<Percurso> {
    const query = { startPoint: startPoint, endPoint };
    const PercursoRecord= await this.PercursoSchema.find(query as FilterQuery<IPercursoPersistence & Document>);

    if(PercursoRecord!=null){
      let array:  Percurso[] = [];
      PercursoRecord.forEach(async (element)=>{
        let perc = await PercursoMap.toDomain(element);

        array.push(perc)
      });
      ////////////////////////////
      let Perc = array.shift();
      return Perc;
    }else return null;
  }

  public async findAll(): Promise<Percurso[]> {

    const query={};
    const percursoRecord= await this.PercursoSchema.find(query as FilterQuery<IPercursoPersistence & Document>);

    if(percursoRecord!=null){
      let array:  Percurso[] = [];
      percursoRecord.forEach((element)=>{
        let elemento = PercursoMap.toDomain(element);
        array.push(elemento);
      });
      return array;
    }else return null;
  }


  async deletePercurso(startPoint:string,endPoint:string): Promise<Percurso> {
    const query = {  startPoint: startPoint, endPoint};
    const PercursoRecord = await this.PercursoSchema.findOne( query as FilterQuery<IPercursoPersistence & Document> );
      if (PercursoRecord === null ) {
        return null;
      } else {
        PercursoRecord.delete();
        return PercursoMap.toDomain(PercursoRecord);
    }
  }

  async create(Percurso: Percurso): Promise<Percurso> {
    const query = { PercursoId: Percurso.percursoId.toString() };
    const PercursoDocument = await this.PercursoSchema.findOne( query );

    try {
      if (PercursoDocument === null ) {

        const rawPercurso: any = PercursoMap.toPersistence(Percurso);

        const PercursoCreated = await this.PercursoSchema.create(rawPercurso);

        return PercursoMap.toDomain(PercursoCreated);
      } else {
        return null;
      }
    } catch (err) {
      throw err;
    }
  }
}

import { Service, Inject } from 'typedi';

import { Document, FilterQuery, Model } from 'mongoose';
import { ICamiaoPersistence } from '../dataschema/ICamiaoPersistence';

import ICamiaoRepo from "../services/IRepos/ICamiaoRepo";
import { Camiao } from "../domain/camiao";

import { CamiaoMap } from "../mappers/CamiaoMap";

import { CamiaoId } from '../domain/camiaoID';
import { Matricula } from '../domain/matricula';

@Service()
export default class CamiaoRepo implements ICamiaoRepo {
  private models: any;

  constructor(
    @Inject('camiaoSchema') private CamiaoSchema : Model<ICamiaoPersistence & Document>,
    @Inject('logger') private logger
  ) { }

  private createBaseQuery (): any {
    return {
      where: {},
    }
  }

  public async exists (camiao: Camiao): Promise<boolean> {

    const idX = camiao.id instanceof CamiaoId ? (<CamiaoId>camiao.id).toValue() : camiao.id;

    const query = { domainId: idX};
    const CamiaoDocument = await this.CamiaoSchema.findOne( query );

    return !!CamiaoDocument === true;
  }

  public async save (Camiao: Camiao): Promise<Camiao> {



    const query = { matricula: Camiao.matricula.value.toString() };

    const CamiaoDocument = await this.CamiaoSchema.findOne( query  );


    try {
      if (CamiaoDocument === null ) {
        const rawCamiao: any = CamiaoMap.toPersistence(Camiao);

        const CamiaoCreated = await this.CamiaoSchema.create(rawCamiao);

        return CamiaoMap.toDomain(CamiaoCreated);
      } else {
        CamiaoDocument.id=Camiao.id;
        CamiaoDocument.caracteristica = Camiao.caracteristica;
        CamiaoDocument.tara = Camiao.dados.tara;
        CamiaoDocument.capacidade = Camiao.dados.capacidade;
        CamiaoDocument.carga = Camiao.dados.carga;
        CamiaoDocument.autonomia = Camiao.dados.autonomia;
        CamiaoDocument.tempo=Camiao.dados.tempo;
        CamiaoDocument.ativo=Camiao.ativo;
        await CamiaoDocument.save();

        return Camiao;
      }
    } catch (err) {

      throw err;
    }

  }
  public async create (Camiao: Camiao): Promise<Camiao> {



    const query = { matricula: Camiao.matricula.toString() };

    const CamiaoDocument = await this.CamiaoSchema.findOne( query );

    try {
      if (CamiaoDocument === null ) {

        const rawCamiao: any = CamiaoMap.toPersistence(Camiao);

        const CamiaoCreated = await this.CamiaoSchema.create(rawCamiao);

        return CamiaoMap.toDomain(CamiaoCreated);
      } else {
        return null;
      }
    } catch (err) {
      throw err;
    }

  }

  public async deleteCamiaoByMatricula (matricula: string): Promise<Camiao> {



    const query = { matricula: matricula};
    const camiaoRecord = await this.CamiaoSchema.findOne( query as FilterQuery<ICamiaoPersistence & Document> );


      if (camiaoRecord === null ) {
        return null;
      } else {
        camiaoRecord.delete();



        return CamiaoMap.toDomain(camiaoRecord);


    }
  }

  public async findById (camiaoId: CamiaoId | string): Promise<Camiao> {
    const query = { domainId: camiaoId};
    const camiaoRecord = await this.CamiaoSchema.findOne( query as FilterQuery<ICamiaoPersistence & Document> );

    if( camiaoRecord != null) {
      return CamiaoMap.toDomain(camiaoRecord);
    }
    else
      return null;
  }

  public async findByCaracteristica(caracteristica: string): Promise<Camiao> {
    const query = { caracteristica: caracteristica };
    const camiaoRecord = await this.CamiaoSchema.findOne( query );
    if( camiaoRecord != null) {
        return CamiaoMap.toDomain(camiaoRecord);
      }
      else
        return null;
  }

  public async findAll(): Promise<Camiao[]> {

    const query={};
    const camiaoRecord= await this.CamiaoSchema.find(query as FilterQuery<ICamiaoPersistence & Document>);

    if(camiaoRecord!=null){
      let array:  Camiao[] = [];
      camiaoRecord.forEach((element)=>{
        let elemnto = CamiaoMap.toDomain(element);

        array.push(elemnto)
      });
      return array;
    }else return null;
  }

  public async findByMatricula (matricula: Matricula | string): Promise<Camiao> {

    const query = { matricula: matricula};
    const camiaoRecord = await this.CamiaoSchema.findOne( query as FilterQuery<ICamiaoPersistence & Document> );

    if( camiaoRecord != null) {
      return CamiaoMap.toDomain(camiaoRecord);
    }
    else
      return null;
  }
}

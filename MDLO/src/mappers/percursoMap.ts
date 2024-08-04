import { UniqueEntityID } from '../core/domain/UniqueEntityID';

import { Mapper } from "../core/infra/Mapper";
import { Percurso } from '../domain/Percursos/percurso';
import { IPercursoDTO } from '../dto/IPercursoDTO';
import { IPercursoPersistence } from '../dataschema/IPercursoPersistence';
import { Document, Model } from 'mongoose';



export class PercursoMap extends Mapper<Percurso> {

  public static toDTO( percurso: Percurso): IPercursoDTO {
    return {
      id: percurso.id.toString(),
      armazemPartida: percurso.armazemPartida,
      armazemChegada  : percurso.armazemChegada,
      distancia: percurso.distancia,
      tempo: percurso.tempo,
      energiaGasta: percurso.energiaGasta,
      tempoExtra: percurso.tempoExtra,
    } as IPercursoDTO;
  }

  public static armazemPartida( percurso: IPercursoDTO): string {
    return percurso.armazemPartida;   
  }

  public static armazemChegada( percurso: IPercursoDTO): string {
    return percurso.armazemChegada;   
  }

  
    public static toDomain(percurso: any | Model<IPercursoPersistence & Document>):Percurso{
    
      const percursoOrError=Percurso.create(percurso,new UniqueEntityID(percurso.domainId));
      percursoOrError.isFailure ? console.log(percursoOrError.error): '';
      return percursoOrError.isSuccess ? percursoOrError.getValue(): null;
  
    }
  
  

  public static toPersistence (percurso: Percurso): any {
    const a = {
      domainId: percurso.id.toString(),
      armazemPartida: percurso.armazemPartida,
      armazemChegada: percurso.armazemChegada,
      distancia: percurso.distancia,
      tempo: percurso.tempo,
      energiaGasta: percurso.energiaGasta,
      tempoExtra: percurso.tempoExtra,
    }
    return a;
  }
}
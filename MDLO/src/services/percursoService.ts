import { Service, Inject } from 'typedi';
import config from "../../config";
import {IPercursoDTO} from "../dto/IPercursoDTO";
import { Percurso } from "../domain/Percursos/percurso";
import { PercursoId } from "../domain/Percursos/percursoId";
import IPercursoRepo from '../services/IRepos/IPercursoRepo';
import IPercursoService from './IServices/IPercursoService';
import { Result } from "../core/logic/Result";
import { PercursoMap } from "../mappers/percursoMap";
import https = require('https');
import fetch = require('node-fetch');


@Service()
export default class PercursoService implements IPercursoService {
  constructor(
      @Inject(config.repos.percurso.name) private PercursoRepo : IPercursoRepo
  ) {}

  httpsAgent = new https.Agent({
    rejectUnauthorized: false,
  });

  public async getPercurso( PercursoId: string): Promise<Result<IPercursoDTO>> {
    try {
      const Percurso = await this.PercursoRepo.findById(PercursoId);

      if (Percurso === null) {
        return Result.fail<IPercursoDTO>("Percurso not found");
      }
      else {
        const PercursoDTOResult = PercursoMap.toDTO( Percurso ) as IPercursoDTO;
        return Result.ok<IPercursoDTO>( PercursoDTOResult )
        }
    } catch (e) {
      throw e;
    }
  }


  public async createPercurso(PercursoDTO: IPercursoDTO): Promise<Result<IPercursoDTO>> {

    /*
    const armazemPartida = PercursoMap.armazemPartida(PercursoDTO);

    const response1 = await fetch("https://localhost:5001/api/armazens/".concat(armazemPartida), {
      method: 'GET',
      agent: this.httpsAgent,
    });

    let json1 = await response1.json();
    const data1 = JSON.stringify(json1);
    const result1 = data1.charAt(7) + data1.charAt(8) + data1.charAt(9);

    if (result1 != armazemPartida) {
      return Result.fail<IPercursoDTO>("Armazém de Partida não existe.");
    }

    const armazemChegada = PercursoMap.armazemChegada(PercursoDTO);
    const response2 = await fetch("https://localhost:5001/api/armazens/".concat(armazemChegada), {
      method: 'GET',
      agent: this.httpsAgent,
    });

    let json2 = await response2.json();
    const data2 = JSON.stringify(json2);
    const result2 = data2.charAt(7) + data2.charAt(8) + data2.charAt(9);

    if (result2 != armazemChegada) {
      return Result.fail<IPercursoDTO>("Armazém de Chegada não existe.");
    }

    if ((await this.PercursoRepo.findPrimeiroEntreArmazens(result1, result2)).armazemChegada.length != 0){
      return Result.fail<IPercursoDTO>("O Percurso já existe no sistema.");
    }*/

    try {

      const PercursoOrError = await Percurso.create( PercursoDTO );

      if (PercursoOrError.isFailure) {

        return Result.fail<IPercursoDTO>("PercursoOrError.errorValue()");
      }

      const PercursoResult = PercursoOrError.getValue();
      //
      await this.PercursoRepo.save(PercursoResult);

      const PercursoDTOResult = PercursoMap.toDTO( PercursoResult ) as IPercursoDTO;
      return Result.ok<IPercursoDTO>( PercursoDTOResult )
    } catch (e) {
      throw e;
    }}

  public async getPercursoList(): Promise<Result<IPercursoDTO[]>> {

      try{
        const Percursos = await this.PercursoRepo.findAll();
        if(Percursos ===null){
          return Result.fail<IPercursoDTO[]>("Percursos not found");
        }else{
          let array:IPercursoDTO[] = [];
          Percursos.forEach((element)=>{
            array.push(PercursoMap.toDTO(element))
          });
          return Result.ok<IPercursoDTO[]>(array);
        }
      }catch(e){
        throw e;
      }
  }

  public async getPercursoListByArmazemPartida(armazemPartida: string): Promise<Result<IPercursoDTO[]>> {

    try{
      const Percursos = await this.PercursoRepo.findAll();
      if(Percursos ===null){
        return Result.fail<IPercursoDTO[]>("Percursos not found");
      }else{
        let array:IPercursoDTO[] = [];
        Percursos.forEach((element)=>{
          if(element.armazemPartida == armazemPartida){
            //console.log(element);
          array.push(PercursoMap.toDTO(element))
        }});

        return Result.ok<IPercursoDTO[]>(array);
      }
    }catch(e){
      throw e;
    }
}

public async getPercursosEntreArmazens(startPoint: string, endPoint: string): Promise<Result<IPercursoDTO[]>> {
  try{
    const Percursos = await this.PercursoRepo.findAll();
    if(Percursos ===null){
      return Result.fail<IPercursoDTO[]>("Não foram encontrados percursos entre o armazém " + startPoint + "e " + endPoint);
    }else{
      let array:IPercursoDTO[] = [];
      Percursos.forEach((element)=>{
        if(element.armazemPartida == startPoint && element.armazemChegada == endPoint){
        array.push(PercursoMap.toDTO(element))
      }});

      return Result.ok<IPercursoDTO[]>(array);
    }
  }catch(e){
    throw e;
  }
}

    async deletePercurso(startPoint: string, endPoint: string): Promise<Result<IPercursoDTO>> {
      try {
        const Percurso = await this.PercursoRepo.deletePercurso(startPoint,endPoint);

        if (Percurso === null) {
          return Result.fail<IPercursoDTO>("Percurso not found");
        }
        else {
          const PercursoDTOResult = PercursoMap.toDTO( Percurso ) as IPercursoDTO;
          return Result.ok<IPercursoDTO>( PercursoDTOResult )
          }
      } catch (e) {
        throw e;
      }
    }
    
    /*public async updatePercurso(PercursoDTO: IPercursoDTO, percursoId: PercursoId): Promise<Result<IPercursoDTO>> {
      try {
        const Percurso = await this.PercursoRepo.findById(percursoId.id.toString());
        
        if (Percurso === null) {
          return Result.fail<IPercursoDTO>("Percurso not found");
        }
        else {
          Percurso.armazemChegada = PercursoDTO.armazemChegada;
          Percurso.armazemPartida = PercursoDTO.armazemPartida;
          Percurso.distancia = PercursoDTO.distancia;
          Percurso.tempo = PercursoDTO.tempo;
          Percurso.energiaGasta = PercursoDTO.energiaGasta;
          Percurso.tempoExtra = PercursoDTO.energiaGasta;
          
          await this.PercursoRepo.save(Percurso);
    
          const PercursoDTOResult = PercursoMap.toDTO( Percurso ) as IPercursoDTO;
          return Result.ok<IPercursoDTO>( PercursoDTOResult )
          }
      } catch (e) {
        throw e;
      }
    }*/

    public async getPrimeiroPercursoEntreArmazens(startPoint: string, endPoint: string): Promise<Result<IPercursoDTO>> {
      try{
        const Percursos = await this.PercursoRepo.findAll();
        if(Percursos ===null){
          return Result.fail<IPercursoDTO>("Não foram encontrados percursos entre o armazém " + startPoint + "e " + endPoint);
        }else{
          let array:IPercursoDTO[] = [];
          Percursos.forEach((element)=>{
            if(element.armazemPartida == startPoint && element.armazemChegada == endPoint){
            array.push(PercursoMap.toDTO(element))
          }});
    
          return Result.ok<IPercursoDTO>(array[1]);
        }
      }catch(e){
        throw e;
      }
    }

    public async updatePercurso(PercursoDTO: IPercursoDTO,/* startPoint: string, endPoint: string*/): Promise<Result<IPercursoDTO>> {
      try {
        const Percurso = await (await this.PercursoRepo.findEntreArmazens(PercursoDTO.armazemPartida, PercursoDTO.armazemChegada)).shift();

        if (Percurso === null) {
          return Result.fail<IPercursoDTO>("Percurso not found");
        }
        else {
          
          Percurso.armazemPartida = PercursoDTO.armazemPartida;
          Percurso.armazemChegada = PercursoDTO.armazemChegada;
          Percurso.distancia = PercursoDTO.distancia;
          Percurso.tempo = PercursoDTO.tempo;
          Percurso.energiaGasta = PercursoDTO.energiaGasta;
          Percurso.tempoExtra = PercursoDTO.tempoExtra;
          
          await this.PercursoRepo.save(Percurso);
    
          const PercursoDTOResult = PercursoMap.toDTO( Percurso ) as IPercursoDTO;
          return Result.ok<IPercursoDTO>( PercursoDTOResult )
          }
      } catch (e) {
        throw e;
      }
    }
}



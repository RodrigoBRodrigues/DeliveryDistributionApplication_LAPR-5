import { Result } from "../../core/logic/Result";
import { PercursoId } from "../../domain/Percursos/percursoId";
import {IPercursoDTO} from "../../dto/IPercursoDTO";

export default interface IPercursoService  {
  createPercurso(PercursoDTO: IPercursoDTO): Promise<Result<IPercursoDTO>>;


  getPercurso (PercursoId: string): Promise<Result<IPercursoDTO>>;
  getPercursoList(): Promise<Result<IPercursoDTO[]>>;
  deletePercurso(startPoint:string,endPoint:string):Promise<Result<IPercursoDTO>>;
  getPercursosEntreArmazens(startPoint:string,endPoint:string): Promise<Result<IPercursoDTO[]>>;
  getPercursoListByArmazemPartida(armazemPartida: string): Promise<Result<IPercursoDTO[]>>;
  getPrimeiroPercursoEntreArmazens(startPoint:string,endPoint:string): Promise<Result<IPercursoDTO>>;
  updatePercurso(PercursoDTO: IPercursoDTO/*, startPoint:string,endPoint:string*/) :Promise<Result<IPercursoDTO>>;
}

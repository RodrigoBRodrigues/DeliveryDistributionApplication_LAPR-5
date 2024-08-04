import { Repo } from "../../core/infra/Repo";
import { Percurso } from "../../domain/Percursos/percurso";


export default interface IPercursoRepo extends Repo<Percurso> {
	save(percurso: Percurso): Promise<Percurso>;
	findById (id: string): Promise<Percurso>;
	findAll():Promise<Percurso[]>;
	deletePercurso(startPoint:string,endPoint:string):Promise<Percurso>;
	create(Percurso: Percurso): Promise<Percurso>;
	findEntreArmazens(startPoint:string, endPoint:string): Promise<Percurso[]>;
	findPrimeiroEntreArmazens(startPoint:string, endpoint:string): Promise<Percurso>;
}

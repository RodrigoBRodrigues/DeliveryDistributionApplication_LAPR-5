import { Repo } from "../../core/infra/Repo";
import { Camiao } from "../../domain/camiao";


export default interface ICamiaoRepo extends Repo<Camiao> {
	save(Camiao: Camiao): Promise<Camiao>;
	findByCaracteristica(caracteristica:string): Promise<Camiao>;
	findById (id: string): Promise<Camiao>;
	findAll():Promise<Camiao[]>;
	findByMatricula(matricula:string): Promise<Camiao>;
	deleteCamiaoByMatricula(matricula:string):Promise<Camiao>;
	create(Camiao: Camiao): Promise<Camiao>;
}
  
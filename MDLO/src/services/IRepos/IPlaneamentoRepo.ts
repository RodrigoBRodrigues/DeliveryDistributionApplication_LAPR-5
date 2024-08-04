/* eslint-disable prettier/prettier */
import { Repo } from "../../core/infra/Repo";
import { Planeamento } from "../../domain/planeamento";


export default interface IPlaneamentoRepo extends Repo<Planeamento> {
	save(Planeamento: Planeamento): Promise<Planeamento>;
	findAll():Promise<Planeamento[]>;
	create(Planeamento: Planeamento): Promise<Planeamento>;
    findById (id: string): Promise<Planeamento>;
}
  
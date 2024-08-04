/* eslint-disable prettier/prettier */
import { Result } from "../../core/logic/Result";
import {IPlaneamentoDTO} from "../../dto/IPlaneamentoDTO";

export default interface IPlaneamentoService  {
  createPlaneamento(PlaneamentoDTO: IPlaneamentoDTO): Promise<Result<IPlaneamentoDTO>>;


  getPlaneamento (PlaneamentoId: string): Promise<Result<IPlaneamentoDTO>>;

  getPlaneamentos(): Promise<Result<IPlaneamentoDTO[]>>;

}
